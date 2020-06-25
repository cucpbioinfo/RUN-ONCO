package run.onco.api.business.facade.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.api.business.dto.OmicsDto;
import run.onco.api.business.dto.RnaSeqDto;
import run.onco.api.business.facade.OmicsAnalysisFacade;
import run.onco.api.business.facade.RnaSeqFacade;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.common.constants.ConfigurationConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class OmicsAnalysisFacadeImpl implements OmicsAnalysisFacade {

	private final static Logger logger = Logger.getLogger(OmicsAnalysisFacadeImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private RnaSeqFacade rnaSeqFacade;

	private String getString(String key) {
		return env.getProperty(key);
	}

	@Override
	public JsonNode getRnaSeqListForClustergrammer(InquiryOmics input) throws JsonProcessingException, IOException {
		
		List<OmicsDto<RnaSeqDto>> omicsDtoList = rnaSeqFacade.getRnaSeqListForClustergrammer(input);
		RestTemplate restTemplate = new RestTemplate();
		String uri = getString(ConfigurationConstants.CLUSTERGRAMMER_ENDPOINT_URL);
		String res = restTemplate.postForObject(uri, omicsDtoList, String.class);
		return parse(res);
	}

	@Override
	public JsonNode getInteractions(InquiryOmics inquiryOmics) throws JsonProcessingException, IOException {
		
		RestTemplate restTemplate = new RestTemplate();
		String uri = getString(ConfigurationConstants.STRINGDB_ENDPOINT_URL);
		String res = restTemplate.postForObject(uri, inquiryOmics, String.class);
		logger.debug(String.format("O:--Get Interactions--:res/%s", res));
		return parse(res);
	}
	
	private JsonNode parse(String json) throws JsonProcessingException, IOException {
		JsonFactory factory = new JsonFactory();

		ObjectMapper mapper = new ObjectMapper(factory);
		JsonNode rootNode = mapper.readTree(json);

		Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
		while (fieldsIterator.hasNext()) {

			Map.Entry<String, JsonNode> field = fieldsIterator.next();
			System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
		}
		
		return rootNode;
	}
}
