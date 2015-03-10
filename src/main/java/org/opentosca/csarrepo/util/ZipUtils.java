package org.opentosca.csarrepo.util;

import java.io.File;
import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Zip utilities
 * 
 * @author Dennis Przytarski, Thomas Kosch
 *
 */
public class ZipUtils {

	private static final Logger LOGGER = LogManager.getLogger(ZipUtils.class);

	/**
	 * Adds a file to the given zip archive.
	 * 
	 * @param zipArchive
	 * @param fileToAdd
	 * @throws ZipException
	 */
	public static void add(File zipArchive, final File fileToAdd) throws ZipException {
		try {
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			ZipFile zipFile = new ZipFile(zipArchive);
			zipFile.addFile(fileToAdd, zipParameters);
			LOGGER.debug("file {} added to {}", fileToAdd, zipArchive.getPath());
		} catch (ZipException e) {
			throw new ZipException(e);
		}
	}

	/**
	 * Checks, if a file exists in the given zip archive.
	 * 
	 * @param zipArchive
	 * @param fileToFind
	 * @return true, if found; false, if not found
	 * @throws ZipException
	 */
	public static boolean exists(File zipArchive, final String fileToFind) throws ZipException {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipArchive);
			for (FileHeader fileHeader : (List<FileHeader>) zipFile.getFileHeaders()) {
				if (fileToFind.equals(fileHeader.getFileName())) {
					LOGGER.debug("file {} found in {}", fileToFind, zipArchive.getPath());
					return true;
				}
			}

			return false;
		} catch (ZipException e) {
			throw new ZipException(e);
		}
	}

	/**
	 * Removes a file from the given zip archive.
	 * 
	 * @param zipArchive
	 * @param fileToDelete
	 * @return false, if not found or not deleted
	 * @throws ZipException
	 */
	public static boolean delete(File zipArchive, final String fileToDelete) throws ZipException {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipArchive);
			if (ZipUtils.exists(zipArchive, fileToDelete)) {
				zipFile.removeFile(fileToDelete);
				LOGGER.debug("file {} removed from {}", fileToDelete, zipArchive.getPath());
				return true;
			}
			return false;
		} catch (ZipException e) {
			throw new ZipException(e);
		}
	}

}
