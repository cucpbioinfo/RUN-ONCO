package run.onco.api.service;

import java.util.List;

import run.onco.api.persist.entity.TbTSampleRnaSeq;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface SampleRnaSeqService {

	public void uploadSampleRnaSeq(TbTSampleRnaSeq sampleRnaSeq);
	
	public TbTSampleRnaSeq getSampleRnaSeqById(final Long sampleId);
	
	public List<TbTSampleRnaSeq> getSampleRnaSeqListByPatientId(final Long patientId);
	
	public int countSampleRnaSeqUsingQuery(final String status);
	
	public List<TbTSampleRnaSeq> listPaginatedSampleRnaSeqUsingQuery(final String status, final int startIndex, final int fetchSize);
	
	public List<TbTSampleRnaSeq> getSampleRnaSeqListByBiospecimens(Long[] ids);
	
	public List<TbTSampleRnaSeq> getSampleRnaSeqListBySampleIds(Long[] ids);
	
	public List<TbTSampleRnaSeq> getActiveSampleRnaSeqList();
	
	public boolean findDuplicateUploadSampleRnaSeq(final Long biospecimenId);
}
