package org.opentosca.csarrepo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Extractor for zip files (CSAR)
 * 
 * @author Dennis Przytarski
 */
public class Extractor {

	private static final Logger LOGGER = LogManager.getLogger(Extractor.class);

	/**
	 * 
	 * @param file
	 *            the csar/zip file as file object
	 * @param pathToFile
	 *            the path of the file to extract
	 * @param patternToEvaluate
	 *            the regex pattern, the first result will be returned
	 * @return the first result of the regex matches
	 * @throws IOException
	 */
	public static String extract(File file, String pathToFile, String patternToEvaluate) throws IllegalStateException,
			IOException {
		ByteArrayOutputStream outputStream = null;
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;

		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			LOGGER.debug("file {}, zipEntry {}", pathToFile, zipEntry.getName());
			if (zipEntry.getName().equals(pathToFile)) {
				outputStream = new ByteArrayOutputStream();
				for (int b = zipInputStream.read(); b != -1; b = zipInputStream.read()) {
					outputStream.write(b);
				}
				outputStream.close();
				break;
			}
			zipInputStream.closeEntry();
		}
		zipInputStream.close();

		String data = outputStream.toString();
		Pattern pattern = Pattern.compile(patternToEvaluate);
		Matcher matcher = pattern.matcher(data);
		if (!matcher.find()) {
			throw new IllegalStateException();
		}

		return matcher.group(1);
	}

}
