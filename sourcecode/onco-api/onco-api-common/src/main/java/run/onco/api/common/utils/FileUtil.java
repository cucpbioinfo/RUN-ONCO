package run.onco.api.common.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class FileUtil {

	private final static Logger logger = Logger.getLogger(FileUtil.class);

	public static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;

		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return bytesArray;
	}

	public static String readStringFromResourceFile(String fileName) {
		BufferedReader reader = null;

		try {
			Resource resource = new ClassPathResource(fileName);
			InputStream in = resource.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			// return reader.readLine();

			StringBuilder out = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				out.append(line);
				out.append(System.getProperty("line.separator"));
			}

			System.out.println(out.toString()); // Prints the string content read from input stream

			return out.toString();
		} catch (IOException ex) {
			logger.info(String.format("O:--FAIL--:--Get Resource File--:errMsg/%s", ex.getMessage()));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					logger.error(String.format("O:--FAIL--:--Get Resource File--:fileName/%s:errMsg:/%s", fileName,
							ex.getMessage()));
				}
			}
		}

		return AppConstants.EMPTY_STRING;
	}

	public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

		File file = new File(fileName);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(content);
		writer.flush();
		writer.close();
	}

	public static File createTempFile(String prefix, String suffix, byte[] byteArray) throws IOException {
		File tempFile = File.createTempFile(prefix, suffix, null);
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(byteArray);
		fos.flush();
		fos.close();
		return tempFile;
	}

	public static void createDirectory(final String path) {
		File theDir = new File(path);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + path);
			theDir.mkdir();
		}
	}
	
	public static void moveFile(String srcFile, String destFile) {
		
		File file = new File(srcFile); 
        
        // renaming the file and moving it to a new location 
        if(file.renameTo(new File(destFile))) 
        { 
            // if file copied successfully then delete the original file 
            file.delete(); 
            System.out.println("File moved successfully"); 
        } 
        else
        { 
            System.out.println("Failed to move the file"); 
        } 
	}
	
	public static void removeFile(String filePath) throws IOException {
		Files.deleteIfExists(Paths.get(filePath)); 
	}
}
