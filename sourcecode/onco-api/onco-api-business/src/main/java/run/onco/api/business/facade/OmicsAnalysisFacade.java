package run.onco.api.business.facade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import run.onco.api.business.message.InquiryOmics;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface OmicsAnalysisFacade {

	public JsonNode getRnaSeqListForClustergrammer(InquiryOmics inquiryOmics) throws JsonProcessingException, IOException; 
	
	public JsonNode getInteractions(InquiryOmics inquiryOmics) throws JsonProcessingException, IOException;
	
}
