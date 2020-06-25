package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTRnaSeq;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface RnaSeqDao {

	public List<TbTRnaSeq> getRnaSeqListBySampleId(final Long sampleId);

	public int countRnaSeqUsingQuery(Map<String, Object> criteria);

	public List<TbTRnaSeq> listPaginatedRnaSeqUsingQuery(Map<String, Object> criteria);

	public List<Object[]> getRnaSeqListForClustergrammer(final Long sampleId, final Integer cancerGeneGrpId);

	public List<Object[]> getRnaSeqListForIdeogram(final Long sampleId, final Long patientId, final Integer cancerGeneGrpId);

	public void deleteRnaSeqList(final Long sampleId);

}
