package run.onco.component.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class CommandLineUtil {

	public static String executeCommand(String command, String folder) {
		StringBuffer output = new StringBuffer();

		Process p;
		
		try {
			
			if (!StringUtil.isNullOrWhitespace(folder)) {
				p = Runtime.getRuntime().exec(command, null, new File(folder));
			} else {
				p = Runtime.getRuntime().exec(command);
			}
			
			p.waitFor();
			BufferedReader reader =  new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();
	}
}
