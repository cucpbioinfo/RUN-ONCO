package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTRnaSeq;
import run.onco.api.persist.entity.TbTSampleRnaSeq;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface RnaSeqService {

//	public void uploadSampleRnaSeq(TbTSampleRnaSeq sampleRnaSeq);
	
	public void deleteRnaSeq(TbTSampleRnaSeq sampleRnaSeq);
	
//	public TbTSampleRnaSeq getSampleRnaSeqById(final Long sampleId);
	
//	public List<TbTSampleRnaSeq> getSampleRnaSeqListByPatientId(final Long patientId);
	
	public int countRnaSeqUsingQuery(Map<String, Object> criteria);
	
	public List<TbTRnaSeq> listPaginatedRnaSeqUsingQuery(Map<String, Object> criteria);
	
//	public int countSampleRnaSeqUsingQuery(final String status);
//	
//	public List<TbTSampleRnaSeq> listPaginatedSampleRnaSeqUsingQuery(final String status, final int startIndex, final int fetchSize);
	
	public void saveRnaSeqList(TbTSampleRnaSeq sampleRnaSeq, List<TbTRnaSeq> rnaSeqList); 
	
	public List<Object[]> getRnaSeqListForClustergrammer(final Long sampleId, final Integer cancerGeneGrpId);
	
//	public List<TbTSampleRnaSeq> getSampleRnaSeqListByBiospecimens(Long[] ids);
	
//	public List<Object[]> getRnaSeqListForIdeogram(final Long sampleId, final Long patientId, final Integer cancerGeneGrpId);
	
//	public List<TbTSampleRnaSeq> getSampleRnaSeqListBySampleIds(Long[] ids);
//	
//	public List<TbTSampleRnaSeq> getActiveSampleRnaSeqList();
//	
//	public boolean findDuplicateUploadSampleRnaSeq(final Long biospecimenId);
}
