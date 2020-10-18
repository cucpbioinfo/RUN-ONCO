package com.squareup.okhttp.logging;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.http.HttpEngine;

import okio.Buffer;
import okio.BufferedSource;

public class HttpLoggingInterceptor implements Interceptor {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HttpLoggingInterceptor.class);

	private volatile Level level = Level.NONE;

	public HttpLoggingInterceptor() {
	}

	public enum Level {
		NONE, BASIC, HEADERS, BODY
	}

	/** Change the level at which this interceptor logs. */
	public HttpLoggingInterceptor setLevel(Level level) {
		if (level == null)
			throw new NullPointerException("level == null. Use Level.NONE instead.");
		this.level = level;
		return this;
	}

	public Level getLevel() {
		return level;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Level level = this.level;

		Request request = chain.request();
		if (level == Level.NONE) {
			return chain.proceed(request);
		}

		boolean logBody = level == Level.BODY;
		boolean logHeaders = logBody || level == Level.HEADERS;

		RequestBody requestBody = request.body();
		boolean hasRequestBody = requestBody != null;

		Connection connection = chain.connection();
		Protocol protocol = connection != null ? connection.getProtocol() : Protocol.HTTP_1_1;
		String requestStartMessage = "--> " + request.method() + ' ' + request.httpUrl() + ' ' + protocol(protocol);
		if (!logHeaders && hasRequestBody) {
			requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
		}
		logger.debug(requestStartMessage);

		if (logHeaders) {
			if (hasRequestBody) {
				// Request body headers are only present when installed as a network
				// interceptor. Force
				// them to be included (when available) so there values are known.
				if (requestBody.contentType() != null) {
					logger.debug("Content-Type: " + requestBody.contentType());
				}
				if (requestBody.contentLength() != -1) {
					logger.debug("Content-Length: " + requestBody.contentLength());
				}
			}

			Headers headers = request.headers();
			for (int i = 0, count = headers.size(); i < count; i++) {
				String name = headers.name(i);
				// Skip headers from the request body as they are explicitly logged above.
				if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
					logger.debug(name + ": " + headers.value(i));
				}
			}

			if (!logBody || !hasRequestBody) {
				logger.debug("--> END " + request.method());
			} else if (bodyEncoded(request.headers())) {
				logger.debug("--> END " + request.method() + " (encoded body omitted)");
			} else {
				Buffer buffer = new Buffer();
				requestBody.writeTo(buffer);

				Charset charset = StandardCharsets.UTF_8;
				MediaType contentType = requestBody.contentType();
				if (contentType != null) {
					contentType.charset(StandardCharsets.UTF_8);
				}

				logger.debug("");
				logger.debug("Requese: {}", buffer.readString(charset));

				logger.debug("--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)");
			}
		}

		long startNs = System.nanoTime();
		Response response = chain.proceed(request);
		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

		ResponseBody responseBody = response.body();
		logger.debug("<-- " + protocol(response.protocol()) + ' ' + response.code() + ' ' + response.message() + " (" + tookMs + "ms"
				+ (!logHeaders ? ", " + responseBody.contentLength() + "-byte body" : "") + ')');

		if (logHeaders) {
			Headers headers = response.headers();
			for (int i = 0, count = headers.size(); i < count; i++) {
				logger.debug(headers.name(i) + ": " + headers.value(i));
			}

			if (!logBody || !HttpEngine.hasBody(response)) {
				logger.debug("<-- END HTTP");
			} else if (bodyEncoded(response.headers())) {
				logger.debug("<-- END HTTP (encoded body omitted)");
			} else {
				BufferedSource source = responseBody.source();
				source.request(Long.MAX_VALUE); // Buffer the entire body.
				Buffer buffer = source.buffer();

				Charset charset = StandardCharsets.UTF_8;
				MediaType contentType = responseBody.contentType();
				if (contentType != null) {
					charset = contentType.charset(StandardCharsets.UTF_8);
				}

				if (responseBody.contentLength() != 0) {
					logger.debug("");
					logger.debug("Response: {}", buffer.clone().readString(charset));
				}

				logger.debug("<-- END HTTP (" + buffer.size() + "-byte body)");
			}
		}

		return response;
	}

	private boolean bodyEncoded(Headers headers) {
		String contentEncoding = headers.get("Content-Encoding");
		return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
	}

	private static String protocol(Protocol protocol) {
		return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
	}

}
