package run.onco.api.common.vcf;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import run.onco.api.common.constants.VcfConstants;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.common.vcf.model.VariantAnnotationBean;
import run.onco.api.common.vcf.model.VariantCallBean;
import run.onco.api.common.vcf.model.VariantResultBean;
import run.onco.parser.vcf.VcfParser;
import run.onco.parser.vcf.model.VcfSample;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class VcfFileReader {

	private final static Logger logger = Logger.getLogger(VcfFileReader.class);

	public static List<VariantCallBean> parse(byte[] initialArray) throws IOException {
		Reader targetReader = new InputStreamReader(new ByteArrayInputStream(initialArray));
		BufferedReader reader = new BufferedReader(targetReader);

		List<VariantCallBean> variantCallList = new ArrayList<VariantCallBean>();

		VcfParser parser = new VcfParser.Builder().fromReader(reader).parseWith((metadata, position, sampleData) -> {
			VariantCallBean variantCallBean = new VariantCallBean();

			String chrom = position.getChromosome();
			variantCallBean.setChromosome(chrom);

			long pos = position.getPosition();
			variantCallBean.setPosition(pos);

			String ref = position.getRef();
			variantCallBean.setRefAllele(ref);

			if (position.getAltBases() != null) {
				String altBase = position.getAltBases().get(0);
				variantCallBean.setAltAllele(altBase);
			}

			if (position.getQuality() != null) {
				BigDecimal qual = position.getQuality();
				variantCallBean.setQuality(qual);
			}

			if (position.getInfo(VcfConstants.INFO_FIELD_ECNT) != null
					&& !".".equals(position.getInfo(VcfConstants.INFO_FIELD_ECNT).get(0))) {
				double ecnt = Double.parseDouble(position.getInfo(VcfConstants.INFO_FIELD_ECNT).get(0));
				variantCallBean.setEcnt(new BigDecimal(ecnt));
			}

			if (position.getInfo(VcfConstants.INFO_FIELD_HCNT) != null
					&& !".".equals(position.getInfo(VcfConstants.INFO_FIELD_HCNT).get(0))) {
				double hcnt = Double.parseDouble(position.getInfo(VcfConstants.INFO_FIELD_HCNT).get(0));
				variantCallBean.setHcnt(new BigDecimal(hcnt));
			}

			if (position.getInfo(VcfConstants.INFO_FIELD_MAX_ED) != null
					&& !".".equals(position.getInfo(VcfConstants.INFO_FIELD_MAX_ED).get(0))) {
				double maxEd = Double.parseDouble(position.getInfo(VcfConstants.INFO_FIELD_MAX_ED).get(0));
				variantCallBean.setMaxEd(new BigDecimal(maxEd));
			}

			if (position.getInfo(VcfConstants.INFO_FIELD_MIN_ED) != null
					&& !".".equals(position.getInfo(VcfConstants.INFO_FIELD_MIN_ED).get(0))) {
				double minEd = Double.parseDouble(position.getInfo(VcfConstants.INFO_FIELD_MIN_ED).get(0));
				variantCallBean.setMinEd(new BigDecimal(minEd));
			}

			if (position.getInfo(VcfConstants.INFO_FIELD_NLOD) != null
					&& !".".equals(position.getInfo(VcfConstants.INFO_FIELD_NLOD).get(0))) {
				double nlod = Double.parseDouble(position.getInfo(VcfConstants.INFO_FIELD_NLOD).get(0));
				variantCallBean.setNlod(new BigDecimal(nlod));
			}

			if (position.getInfo(VcfConstants.INFO_FIELD_TLOD) != null
					&& !".".equals(position.getInfo(VcfConstants.INFO_FIELD_TLOD).get(0))) {
				double tlod = Double.parseDouble(position.getInfo(VcfConstants.INFO_FIELD_TLOD).get(0));
				variantCallBean.setTlod(new BigDecimal(tlod));
			}

			// logger.info(String.format(
			// "O:--[1]Vcf
			// Parser--:chr1/%s:position/%s:ref/%s:alt/%s:qual/%d:ecnt/%s:hcnt/%s:maxEd/%s:minEd/%s:nlod/%s:tlod/%s",
			// chrom, pos, ref, altBase, qual, ecnt, hcnt, maxEd, minEd, nlod, tlod));
			// logger.info(String.format("O:--[2]Vcf Parser--:%s",
			// position.getInfo("HCNT").size()));

			List<VariantAnnotationBean> variantAnnotationList = new ArrayList<VariantAnnotationBean>();

			if (position.getInfo(VcfConstants.INFO_FIELD_CSQ) != null) {
				for (String s : position.getInfo(VcfConstants.INFO_FIELD_CSQ)) {
					List<String> dataList = StringUtil.convertStringToList(s, "\\|");
//					logger.info(String.format("----> CSQ : %s, ----> list size : %s", s, dataList.size()));

					if (dataList != null) {
						VariantAnnotationBean variantAnnotationBean = new VariantAnnotationBean();

						String allele = dataList.get(VcfConstants.INFO_FIELD_ALLELE);
						variantAnnotationBean.setAllele(allele);

						String consequence = dataList.get(VcfConstants.INFO_FIELD_CONSEQUENCE);
						variantAnnotationBean.setConsequence(consequence);

						String impact = dataList.get(VcfConstants.INFO_FIELD_IMPACT);
						variantAnnotationBean.setImpact(impact);

						String symbol = dataList.get(VcfConstants.INFO_FIELD_SYMBOL);
						variantAnnotationBean.setSymbol(symbol);

						String gene = dataList.get(VcfConstants.INFO_FIELD_GENE);
						variantAnnotationBean.setGene(gene);

						String featureType = dataList.get(VcfConstants.INFO_FIELD_FEATURE_TYPE);
						variantAnnotationBean.setFeatureType(featureType);

						String feature = dataList.get(VcfConstants.INFO_FIELD_FEATURE);
						variantAnnotationBean.setFeature(feature);

						String biotype = dataList.get(VcfConstants.INFO_FIELD_BIOTYPE);
						variantAnnotationBean.setBiotype(biotype);

						String exon = dataList.get(VcfConstants.INFO_FIELD_EXON);
						variantAnnotationBean.setExon(exon);

						String intron = dataList.get(VcfConstants.INFO_FIELD_INTRON);
						variantAnnotationBean.setIntron(intron);

						String hgvsc = dataList.get(VcfConstants.INFO_FIELD_HGVSC);
						variantAnnotationBean.setHgvsc(hgvsc);

						String hgvsp = dataList.get(VcfConstants.INFO_FIELD_HGVSP);
						variantAnnotationBean.setHgvsp(hgvsp);

						String cdnaPosition = dataList.get(VcfConstants.INFO_FIELD_CDNA_POSITION);
						variantAnnotationBean.setCdnaPosition(cdnaPosition);

						String cdsPosition = dataList.get(VcfConstants.INFO_FIELD_CDS_POSITION);
						variantAnnotationBean.setCdsPosition(cdsPosition);

						String protienPosition = dataList.get(VcfConstants.INFO_FIELD_PROTEIN_POSITION);
						variantAnnotationBean.setProteinPosition(protienPosition);

						String aminoAcids = dataList.get(VcfConstants.INFO_FIELD_AMINO_ACIDS);
						variantAnnotationBean.setAminoAcids(aminoAcids);

						String codons = dataList.get(VcfConstants.INFO_FIELD_CODONS);
						variantAnnotationBean.setCodons(codons);

						String existingVariantion = dataList.get(VcfConstants.INFO_FIELD_EXISTING_VARIANTION);
						variantAnnotationBean.setExistingVariantion(existingVariantion);

						String distance = dataList.get(VcfConstants.INFO_FIELD_DISTANCE);
						variantAnnotationBean.setDistance(distance);

						variantAnnotationList.add(variantAnnotationBean);
					}
				}
			}

			variantCallBean.setVariantAnnotationList(variantAnnotationList);

			// variantAnnotationList.add();

			// if (position.getInfo("CSQ") != null) {
			// for(String val : position.getInfo("CSQ")) {
			// logger.info("----> CSQ : " + val);
			// }
			// }
			//
			// if (position.getFormat() != null) {
			// for(String val : position.getFormat()) {
			// logger.info("----> FORMAT : " + val);
			// }
			// }
			//
			// if (position.getIds() != null) {
			// for(String val : position.getIds()) {
			// logger.info("----> ID : " + val);
			// }
			// }
			//
			// if (position.getFilters() != null) {
			// for(String val : position.getFilters()) {
			// logger.info("----> Filter : " + val);
			// }
			// }
			//
			// if (position.getAllele(0) != null) {
			// logger.info("----> Allele[0] : " + position.getAllele(0));
			// }
			// if (position.getAllele(1) != null) {
			// logger.info("----> Allele[1] : " + position.getAllele(1));
			// }
			//

			List<VariantResultBean> variantResultList = new ArrayList<VariantResultBean>();

			if (sampleData != null) {
				// for (VcfSample s : sampleData) {
				// s.getPropertyKeys().forEach((x) -> {
				// // System.out.println(x);
				// logger.info(String.format("%s %s", x, s.getProperty(x)));
				// });
				// }

				// Retrieve tumor sample
				VariantResultBean tumor = retrieveSample(sampleData.get(0), VcfConstants.SAMPLE_TYPE_TUMOR);
				variantResultList.add(tumor);

				// Retrieve normal sample
				VariantResultBean normal = retrieveSample(sampleData.get(1), VcfConstants.SAMPLE_TYPE_NORMAL);
				variantResultList.add(normal);
			}

			variantCallBean.setVariantResultList(variantResultList);
			variantCallList.add(variantCallBean);
		}).build();
		parser.parse();
		targetReader.close();

		return variantCallList;
	}

	private static VariantResultBean retrieveSample(VcfSample sample, String sampleType) {

		if (sample != null) {

			VariantResultBean variantResultBean = new VariantResultBean();
			variantResultBean.setGenotype(sample.getProperty(VcfConstants.INFO_FIELD_GENOTYPE));

			if (sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_DEPTH) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_DEPTH))) {

				List<String> result = StringUtil.convertStringToList(sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_DEPTH), ",");

				Double refAlleleDepth = Double.parseDouble(result.get(0));
				if (!refAlleleDepth.isNaN()) {
					variantResultBean.setRefAlleleDepth(new BigDecimal(refAlleleDepth));
				}

				Double altAlleleDepth = Double.parseDouble(result.get(1));
				if (!altAlleleDepth.isNaN()) {
					variantResultBean.setAltAlleleDepth(new BigDecimal(altAlleleDepth));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_FREQUENCY) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_FREQUENCY))) {
				
				Double alleleFrequency = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_FREQUENCY));
				if (!alleleFrequency.isNaN()) {
					variantResultBean.setAlleleFrequency(new BigDecimal(alleleFrequency));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F1R2) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F1R2))) {
				
				Double altAlleleF1r2 = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F1R2));
				if (!altAlleleF1r2.isNaN()) {
					variantResultBean.setAltAlleleF1r2(new BigDecimal(altAlleleF1r2));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F2R1) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F2R1))) {
				
				Double altAlleleF2r1 = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F2R1));
				if (!altAlleleF2r1.isNaN()) {
					variantResultBean.setAltAlleleF1r2(new BigDecimal(altAlleleF2r1));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F2R1) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F2R1))) {
				
				Double altAlleleF2r1 = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_ALT_ALLELE_F2R1));
				if (!altAlleleF2r1.isNaN()) {
					variantResultBean.setAltAlleleF1r2(new BigDecimal(altAlleleF2r1));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_OXOG_READ_COUNTS) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_OXOG_READ_COUNTS))) {
				
				Double oxogReadCounts = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_OXOG_READ_COUNTS));
				if (!oxogReadCounts.isNaN()) {
					variantResultBean.setOxogReadCounts(new BigDecimal(oxogReadCounts));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_QSS) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_QSS))) {

				List<String> result = StringUtil.convertStringToList(sample.getProperty(VcfConstants.INFO_FIELD_ALLELE_QSS), ",");

				Double refAlleleQss = Double.parseDouble(result.get(0));
				if (!refAlleleQss.isNaN()) {
					variantResultBean.setRefAlleleQss(new BigDecimal(refAlleleQss));
				}

				Double altAlleleQss = Double.parseDouble(result.get(1));
				if (!altAlleleQss.isNaN()) {
					variantResultBean.setAltAlleleQss(new BigDecimal(altAlleleQss));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_REF_ALLELE_F1R2) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_REF_ALLELE_F1R2))) {
				
				Double refAlleleF1r2 = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_REF_ALLELE_F1R2));
				if (!refAlleleF1r2.isNaN()) {
					variantResultBean.setRefAlleleF1r2(new BigDecimal(refAlleleF1r2));
				}
			}

			if (sample.getProperty(VcfConstants.INFO_FIELD_REF_ALLELE_F2R1) != null
					&& !".".equals(sample.getProperty(VcfConstants.INFO_FIELD_REF_ALLELE_F2R1))) {
				
				Double refAlleleF2r1 = Double.parseDouble(sample.getProperty(VcfConstants.INFO_FIELD_REF_ALLELE_F2R1));
				if (!refAlleleF2r1.isNaN()) {
					variantResultBean.setRefAlleleF2r1(new BigDecimal(refAlleleF2r1));
				}
			}

			variantResultBean.setSampleType(sampleType);
			return variantResultBean;
		}

		return null;
	}
}
