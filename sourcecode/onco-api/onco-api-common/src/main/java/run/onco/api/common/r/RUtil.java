package run.onco.api.common.r;

import org.apache.log4j.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import run.onco.api.common.utils.FileUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class RUtil {

	private final static Logger logger = Logger.getLogger(RUtil.class);

	public static RConnection openConnection(RConnection conn) throws RserveException {

		boolean check = false;
		conn = new RConnection();

		if (conn.isConnected()) {
			check = true;
			logger.debug(String.format("I:--START--:--Rserve connection Established--:check/%s", check));
		}

		return conn;
	}

	public static boolean closeConnection(RConnection conn) {

		boolean check = false;

		if (conn.isConnected()) {

			if (conn.close()) {
				check = true;
				logger.debug(String.format("O:--SUCCESS--:--Rserve connection Closed--:check/%s", check));
			}
		} else {
			logger.debug("O:--FAIL--:--Rserve could not connected--");
		}

		return check;
	}

	public static void execute(RConnection conn, String fileName, double x, int y, int z, String outputFile) throws RserveException, REXPMismatchException {

		REXP re = new REXP();

		String s = FileUtil.readStringFromResourceFile(String.format("rscripts/%s", fileName));
		logger.debug(String.format("O:--Execute R--:--Get RScript--:s/%s", s));

		conn.eval(s);
		
		evaluation(re, conn, x, y, z, outputFile);
	}

	private static void evaluation(REXP re, RConnection conn, double x, int y, int z, String outputFile) throws RserveException, REXPMismatchException {

//		conn.eval("ejecution<-fun2(" + x + "," + y + "," + z + ")");
		// outputFile
		conn.eval("ejecution<-fun1('" + outputFile + "')");
		re = conn.eval("table(ejecution)");
		
		if(re != null) {
			logger.debug(String.format("O:--Excecute R--:output/%s", re));
		} else {
			logger.debug("O:--Excecute R--:message/Output is empty.");
		}
		
//		System.out.println("Class 1:" + re.asIntegers()[0]);
//		System.out.println("Class 2:" + re.asIntegers()[1]);
//		System.out.println("Class 3:" + re.asIntegers()[2]);
	}
}
