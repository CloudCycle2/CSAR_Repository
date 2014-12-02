package org.opentosca.csarrepo.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Provides the file system functionality.
 * 
 * @author Fabian Toth, Dennis Przytarski
 */
public class FileSystem {

	private static final String BASE_PATH = System.getProperty("java.io.tmpdir") + File.pathSeparator + "csarrepo"
			+ File.pathSeparator;

	public boolean saveToFileSystem(long id, File file) {
		try {
			Files.move(file.toPath(), Paths.get(BASE_PATH + id + ".csar"), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			// TODO Log4j
			return false;
		}
		return true;
	}

	public File getFile(long id) {
		File file = new File(BASE_PATH + id + ".csar");
		if (file.exists() && file.isFile()) {
			return file;
		}
		return null;
	}

	public boolean deleteFile(long id) {
		File file = new File(BASE_PATH + id + ".csar");
		if (file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}

}
