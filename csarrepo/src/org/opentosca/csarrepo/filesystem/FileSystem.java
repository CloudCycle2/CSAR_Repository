package org.opentosca.csarrepo.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides the file system functionality.
 * 
 * @author Fabian Toth, Dennis Przytarski
 */
public class FileSystem {

	private static final Logger LOGGER = LogManager.getLogger(FileSystem.class);

	private static final String BASE_PATH = System
			.getProperty("java.io.tmpdir") + "csarrepo" + File.separator;

	private static final String FILE_EXTENSION = ".csar";

	static {
		// ensure the base_path is available
		File file = new File(BASE_PATH);
		if (!file.exists()) {
			file.mkdir();
			LOGGER.info("Created " + BASE_PATH + " to store CSARs");
		}
	}

	public String saveToFileSystem(File file) throws IOException {
		String generatedFileName = UUID.randomUUID().toString();
		String absPath = BASE_PATH + generatedFileName + FILE_EXTENSION;

		try {
			Files.move(file.toPath(), Paths.get(absPath),
					StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}

		return absPath;
	}

	/**
	 * returns the file object represented by pathname
	 * 
	 * @param pathname
	 *            may not be null
	 * @return the file or <code>null</code> if the file doesn't exist
	 */
	public File getFile(String pathname) {
		File file = new File(pathname);
		if (file.exists() && file.isFile()) {
			return file;
		}
		return null;
	}

	/**
	 * delete the file specified by pathname
	 * 
	 * @param pathname
	 *            of the file to be deleted
	 * @return <code>true</code> if the deletions was successful
	 */
	public boolean deleteFile(String pathname) {
		File file = new File(pathname);
		if (file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * Creates a temporary file on the HDD
	 * 
	 * @param fis
	 *            FileInputStream
	 * @return FileObject representing the created file
	 * @throws IOException
	 *             if error occurred
	 */
	public File saveTempFile(InputStream fis) throws IOException {
		File tmpFile = File.createTempFile("tmpCSAR", ".tmp");
		tmpFile.deleteOnExit();

		OutputStream outputStream = new FileOutputStream(tmpFile);
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = fis.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.flush();
		outputStream.close();

		LOGGER.info("tmp file created: " + tmpFile.getAbsolutePath());
		return tmpFile;
	}

}
