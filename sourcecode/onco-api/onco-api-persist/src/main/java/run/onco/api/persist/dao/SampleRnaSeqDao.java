package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbTSampleRnaSeq;

/**
 * 
 * @author Neda Peyronne
 *
 */
public interface SampleRnaSeqDao {

	public List<TbTSampleRnaSeq> getRnaSeqListByPatientId(final Long patientId);
	
	public int countSampleRnaSeqUsingQuery(final String status);
	
	public List<TbTSampleRnaSeq> listPaginatedSampleRnaSeqUsingQuery(final String status, final int startIndex, final int fetchSize);
	
	public List<TbTSampleRnaSeq> getSampleRnaSeqListByBiospecimens(final Long[] ids);

	public List<TbTSampleRnaSeq> getSampleRnaSeqListBySampleIds(Long[] ids);
	
	public List<TbTSampleRnaSeq> getActiveSampleRnaSeqList();
	
	public boolean findDuplicateUploadSampleRnaSeq(final Long biospecimenId);
}
