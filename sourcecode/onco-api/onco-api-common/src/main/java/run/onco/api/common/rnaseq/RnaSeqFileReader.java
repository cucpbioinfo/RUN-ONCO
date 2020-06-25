package run.onco.api.common.rnaseq;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import run.onco.api.common.rnaseq.model.RnaSeqBean;
import run.onco.api.common.utils.NumberUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class RnaSeqFileReader {

	private final static Logger logger = Logger.getLogger(RnaSeqFileReader.class);

	public static List<RnaSeqBean> parse(byte[] initialArray) throws IOException {

		InputStream is = null;
		BufferedReader bfReader = null;
		
		List<RnaSeqBean> rnaSeqList = new ArrayList<RnaSeqBean>();

		try {
			is = new ByteArrayInputStream(initialArray);
            bfReader = new BufferedReader(new InputStreamReader(is));
            bfReader.readLine(); // this will read the first line
            String temp = null;
            
            while((temp = bfReader.readLine()) != null) {
            		
            		String[] splited = temp.split("\\s+");
            		
            		if(splited != null && splited.length > 0) {
	            		RnaSeqBean rnaSeq = new RnaSeqBean();
	            		rnaSeq.setGeneId(splited[0]);
	            		rnaSeq.setGeneName(splited[1]);
	            		rnaSeq.setRef(splited[2]);
	            		rnaSeq.setStrand(splited[3]);
	            		rnaSeq.setStart(NumberUtil.tryParseInt(splited[4]));
	            		rnaSeq.setEnd(NumberUtil.tryParseInt(splited[5]));
	            		rnaSeq.setCoverage(NumberUtil.parseNumber(splited[6]));
	            		rnaSeq.setFpkm(NumberUtil.parseNumber(splited[7]));
	            		rnaSeq.setTpm(NumberUtil.parseNumber(splited[8]));
	            		rnaSeqList.add(rnaSeq);
            		}
            }
            
            return rnaSeqList;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception ex) {

			}
		}
		
		return null;
	}
}
