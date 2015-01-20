package org.opentosca.csarrepo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.opentosca.csarrepo.exception.PersistenceException;

/**
 * This class implements several hash algorithms
 *
 * @author Thomas Kosch (mail@thomaskosch.com)
 *
 */
public class Hash {

	/**
	 * Generates a sha256 hash for a given file object.
	 *
	 * @param file
	 *            FileObject
	 * @return sha256 hash
	 * @throws PersistenceException
	 *             if error occurred
	 */
	public static String sha256(final File file) throws PersistenceException {
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			InputStream inputStream = new FileInputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				messageDigest.update(bytes, 0, read);
			}
			inputStream.close();

			byte[] sha256 = messageDigest.digest();
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < sha256.length; i++) {
				stringBuffer.append(Integer.toString((sha256[i] & 0xFF) + 0x100, 16).substring(1));
			}

			return stringBuffer.toString();
		} catch (IOException | NoSuchAlgorithmException e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Generates a sha256 hash for a given string
	 * 
	 * @param password
	 * @return
	 */
	public static String sha256(final String password) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes("UTF-8"));

			byte[] sha256 = messageDigest.digest();
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < sha256.length; i++) {
				stringBuffer.append(Integer.toString((sha256[i] & 0xFF) + 0x100, 16).substring(1));
			}
			return stringBuffer.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return password;

	}
}
