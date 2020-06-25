package run.onco.parser.vcf;

import run.onco.parser.vcf.model.VcfMetadata;
import run.onco.parser.vcf.model.VcfPosition;
import run.onco.parser.vcf.model.VcfSample;

import java.util.List;


/**
 * This interface controls what is actually done for each data line in a VCF file.
 *
 * @author Mark Woon
 */
public interface VcfLineParser {

  void parseLine(VcfMetadata metadata, VcfPosition position, List<VcfSample> sampleData);
}
