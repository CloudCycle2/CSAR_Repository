package org.opentosca.csarrepo.filesystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
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

	private static final String BASE_PATH = System.getProperty("java.io.tmpdir") + File.separator + "csarrepo"
			+ File.separator;

	private static final String FILE_EXTENSION = ".csar";

	public FileSystem() {
		// ensure the base_path is available
		File file = new File(BASE_PATH);
		if (!file.exists()) {
			file.mkdir();
			LOGGER.info("Created " + BASE_PATH + " to store CSARs");
		}
	}

	/**
	 * moves the given file to a persistent place
	 *
	 * @param fileName
	 *            the fileName the file will get inside the csar-folder on the
	 *            HDD
	 * @param file
	 * @return the filename
	 * @throws IOException
	 */
	public String saveToFileSystem(UUID fileName, File file) throws IOException {

		File newFile = new File(this.generateFilePath(fileName));

		Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.ATOMIC_MOVE);

		LOGGER.info("Created new file: " + newFile.getAbsolutePath() + ", size: " + newFile.length());
		return newFile.getName();
	}

	/**
	 * returns the file object represented by pathname
	 *
	 * @param fileId
	 *            the id of the file
	 * @return the file or <code>null</code> if the file doesn't exist
	 */
	public File getFile(UUID fileId) {
		File file = new File(generateFilePath(fileId));
		if (file.exists() && file.isFile()) {
			return file;
		}
		return null;
	}

	/**
	 * delete the file specified by pathname
	 *
	 * @param fileId
	 *            the id of the file
	 * @return <code>true</code> if the deletions was successful
	 */
	public boolean deleteFile(UUID fileId) {
		File file = new File(generateFilePath(fileId));
		if (file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}

	/**
	 * Gets the size of the file
	 *
	 * @param fileId
	 *            the id of the file
	 * @return the size of the file
	 */
	public long getFileSize(UUID fileId) {
		File file = new File(generateFilePath(fileId));
		if (file.exists() && file.isFile()) {
			return file.length();
		}
		return 0;
	}

	private String generateFilePath(UUID fileId) {
		return BASE_PATH + fileId.toString() + FILE_EXTENSION;
	}

	/**
	 * Creates a temporary file on the HDD
	 *
	 * @param is
	 *            InputStream
	 * @return FileObject representing the created file
	 * @throws IOException
	 *             if error occurred
	 */
	public File saveTempFile(InputStream is) throws IOException {
		File tmpFile = File.createTempFile("tmpCSAR", ".tmp");
		tmpFile.deleteOnExit();

		OutputStream outputStream = new FileOutputStream(tmpFile);
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = is.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.flush();
		outputStream.close();

		LOGGER.info("tmp file created: " + tmpFile.getAbsolutePath() + ", size: " + tmpFile.length());
		return tmpFile;
	}

	public String generateHash(File tmpFile) {
		// TODO:implement me
		return tmpFile.getName();
	}
}
