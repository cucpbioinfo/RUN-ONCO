package run.onco.api.connector.client.oncokb.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import run.onco.api.common.utils.NumberUtil;
import run.onco.api.connector.client.constants.ConnectorConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Component
public class EndpointUtil {
	
	private static Environment env;
	
	@Autowired(required = true)
	public void setEnvironment(Environment environment) {
		EndpointUtil.env = environment;
	}
	
	public static int getConnectionTimeout() {
		return NumberUtil.tryParseInt(env.getProperty(ConnectorConstants.ONCOKB_CONNECTION_TIMEOUT));
	}
	
	public static int getReadTimeout(){
		return NumberUtil.tryParseInt(env.getProperty(ConnectorConstants.ONCOKB_READ_TIMEOUT));
	}

	public static URL getEndpoint() {
		try {
			return new URL(null, env.getProperty(ConnectorConstants.ONCOKB_ENPOINT_URL), new URLStreamHandler() {
				@Override
				protected URLConnection openConnection(URL url) throws IOException {
					URL setupUrl = new URL(url.toString());
					HttpURLConnection urlConnection = (HttpURLConnection) setupUrl.openConnection();
					urlConnection.setConnectTimeout(NumberUtil.tryParseInt(env.getProperty(ConnectorConstants.ONCOKB_CONNECTION_TIMEOUT)));
					return urlConnection;
				}
			});

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
