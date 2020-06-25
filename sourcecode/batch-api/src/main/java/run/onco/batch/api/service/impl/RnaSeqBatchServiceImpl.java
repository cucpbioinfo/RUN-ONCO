package run.onco.batch.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.ConfigurationConstants;
import run.onco.api.common.rnaseq.RnaSeqFileReader;
import run.onco.api.common.rnaseq.model.RnaSeqBean;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.FileUtil;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTRnaSeq;
import run.onco.api.persist.entity.TbTSampleRnaSeq;
import run.onco.api.service.AttachmentService;
import run.onco.api.service.RnaSeqService;
import run.onco.api.service.SampleRnaSeqService;
import run.onco.batch.api.service.RnaSeqBatchService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class RnaSeqBatchServiceImpl implements RnaSeqBatchService {
	
	private final static Logger logger = Logger.getLogger(RnaSeqBatchServiceImpl.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private SampleRnaSeqService sampleRnaSeqService;
	
	@Autowired
	private RnaSeqService rnaSeqService;
	
	@Autowired
	private AttachmentService attachmentService;

	private String getString(String key) {
		return env.getProperty(key);
	}
	
	@Override
	public void importRnaSeqFiles() throws IOException {
		
		logger.info("I:--START--:--Import RnaSeq Files--");
		
		String status = AppConstants.STATUS_DRAFT;
		int startIndex = 0;
		int fetchSize = 2;
		
		int totalRecords = sampleRnaSeqService.countSampleRnaSeqUsingQuery(status);
		
		if (totalRecords > 0) {
					
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqService.listPaginatedSampleRnaSeqUsingQuery(status, startIndex, fetchSize);
					
			logger.debug(String.format("O:--Get SampleRnaSeqList--:sampleRnaSeqList size/%s", !AppUtil.isObjectEmpty(sampleRnaSeqList) ? sampleRnaSeqList.size() : 0));
			
			for (TbTSampleRnaSeq sampleRnaSeq : sampleRnaSeqList) {
				
				Long attachmentId = sampleRnaSeq.getAttachment().getId();
				TbTAttachment attachment = attachmentService.getAttachmentById(attachmentId);
				
				if (attachment != null) {
					String relativePath = String.format("%s/%s", attachment.getFilePath(), attachment.getFileName());
					String absolutePath = String.format("%s%s", getString(ConfigurationConstants.DATA_FILE_PATH), relativePath);
					
					logger.debug(String.format("O:--Get Attachment--:relativePath/%s:absolutePath/%s", relativePath, absolutePath));
					
					byte[] byteArray = FileUtil.readBytesFromFile(absolutePath);
					
					List<RnaSeqBean> rnaSeqBeanList = RnaSeqFileReader.parse(byteArray);
					List<TbTRnaSeq> rnaSeqList = new ArrayList<TbTRnaSeq>();
					
					if (rnaSeqBeanList != null && rnaSeqBeanList.size() > 0) {
						
						for (RnaSeqBean rnaSeqBean : rnaSeqBeanList)  {
							
							TbTRnaSeq rnaSeq = new TbTRnaSeq();
							rnaSeq.setGeneId(rnaSeqBean.getGeneId());
							rnaSeq.setGeneName(rnaSeqBean.getGeneName());
							rnaSeq.setRef(rnaSeqBean.getRef());
							rnaSeq.setStrand(rnaSeqBean.getStrand());
							rnaSeq.setStart(rnaSeqBean.getStart());
							rnaSeq.setEnd(rnaSeqBean.getEnd());
							rnaSeq.setCoverage(rnaSeqBean.getCoverage());
							rnaSeq.setFpkm(rnaSeqBean.getFpkm());
							rnaSeq.setTpm(rnaSeqBean.getTpm());
							rnaSeq.setSampleRnaSeq(sampleRnaSeq);
							rnaSeqList.add(rnaSeq);
						}
					}
					
					rnaSeqService.saveRnaSeqList(sampleRnaSeq, rnaSeqList);
				}
			}
		}
		
		logger.info("O:--SUCCESS--:--Import RnaSeq Files--");
	}
}
