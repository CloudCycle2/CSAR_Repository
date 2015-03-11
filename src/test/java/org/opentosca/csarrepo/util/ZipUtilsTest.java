package org.opentosca.csarrepo.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ZipUtils
 * 
 * @author Dennis Przytarski
 */
public class ZipUtilsTest {

	private final static String SOURCE_FILE = "test.zip";
	private final static String TARGET_FILE = "test2.zip";
	private static final String FIRST_FILE = "test1.txt";
	private static final String SECOND_FILE = "test2.txt";

	private File file;

	@Before
	public void beforeZipTest() {
		try {
			String path = getClass().getClassLoader().getResource(".").getPath();
			Path sourcePath = Paths.get(path + SOURCE_FILE);
			Path targetPath = Paths.get(path + TARGET_FILE);
			Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

			file = targetPath.toFile();

			ZipFile zipFile = new ZipFile(file);
			assertTrue(zipFile.isValidZipFile());
			assertEquals(FIRST_FILE, ((FileHeader) zipFile.getFileHeaders().get(0)).getFileName());
			assertEquals(SECOND_FILE, ((FileHeader) zipFile.getFileHeaders().get(1)).getFileName());
		} catch (IOException | ZipException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteFileFromZip() throws IOException {
		try {
			ZipUtils.delete(file, FIRST_FILE);

			ZipFile zipFile = new ZipFile(file);
			assertEquals(SECOND_FILE, ((FileHeader) zipFile.getFileHeaders().get(0)).getFileName());
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteNotExistingFileFromZip() throws IOException {
		try {
			assertFalse(ZipUtils.delete(file, "abc"));
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddFileToZip() throws IOException {
		try {
			File temporaryFile = new File(SECOND_FILE);
			temporaryFile.deleteOnExit();
			InputStream inputStream = new ByteArrayInputStream("test2".getBytes());
			Files.copy(inputStream, Paths.get(temporaryFile.getPath()), StandardCopyOption.REPLACE_EXISTING);

			ZipUtils.add(file, temporaryFile);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

}
