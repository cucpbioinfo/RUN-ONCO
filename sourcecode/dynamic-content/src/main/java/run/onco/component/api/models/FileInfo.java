package run.onco.component.api.models;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class FileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9105259925398304361L;

	private String directory;
	private String fileName;

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
