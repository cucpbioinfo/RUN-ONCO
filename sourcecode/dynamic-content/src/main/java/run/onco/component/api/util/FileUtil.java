package run.onco.component.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class FileUtil {

	public static String readFileAsString(String filePath) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(filePath)));
		return data;
	}
	
	public static void writeFile(String str, String filePath) throws IOException {
		Path path = Paths.get(filePath);
	    Charset charset = StandardCharsets.UTF_8;
	    Files.write(path, str.getBytes(charset));
	}
	
	public static void listFilesForFolder(String directoryName, List<File> files) {
	    File directory = new File(directoryName);

	    // Get all files from a directory.
	    File[] fList = directory.listFiles();
	    if(fList != null)
	        for (File file : fList) {      
	            if (file.isFile() && !file.isHidden()) {
	                files.add(file);
	            } else if (file.isDirectory() && !file.isHidden()) {
	            		listFilesForFolder(file.getAbsolutePath(), files);
	            }
	        }
	}
	
	public static void copy(File sourceLocation, File targetLocation) throws IOException {
	    if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation);
	    } else {
	        copyFile(sourceLocation, targetLocation);
	    }
	}

	private static void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f));
	    }
	}

	private static void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    		) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}
	
	public static void cleanDirectory(File directory) throws IOException {
		FileUtils.cleanDirectory(directory);
	}
	
	public static boolean isDirectoryExist(String dirPath) {
		Path path = Paths.get(dirPath);
		
		if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			return true;
		}
		
		return false;
	}
}
