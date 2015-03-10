/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opentosca.csarrepo.util;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;

/**
 * Thankfully inspired by
 * http://svn.apache.org/repos/asf/commons/proper/lang/trunk
 * /src/main/java/org/apache/commons/lang3/StringUtils.java
 * 
 * @author Marcus Eisele (marcus.eisele@gmail.com)
 *
 */
public class StringUtils {
	private static final String EMPTY = "";

	public static String join(List list, String separator) {
		if (list == null) {
			return null;
		}

		if (separator == null) {
			separator = EMPTY;
		}

		if (list.size() <= 0) {
			return EMPTY;
		}

		final StringBuilder buf = new StringBuilder(list.size() * 16);

		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				buf.append(separator);
			}
			if (list.get(i) != null) {
				buf.append(list.get(i));
			}
		}
		return buf.toString();
	}

	public static String join(List list) {
		return StringUtils.join(list, ",");
	}

	/**
	 * Function taken from
	 * http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
	 * 
	 * Formats a number into MB
	 * 
	 * @param size
	 * @return A readable and smooth file size
	 */
	public static String readableFileSize(long size) {
		if (size <= 0)
			return "0";
		final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public static long getURLParameter(String pathInfo) throws ServletException {

		try {
			// {id}
			return Long.parseLong(pathInfo.split("/")[1]);
		} catch (Exception e) {
			throw new ServletException("URL is not valid");
		}
	}

	/**
	 * Extracts the Filename without file extension from the given fullPath
	 * 
	 * @param fullpath
	 *            the full file path containing either / or \
	 * @return the filename only (part after the last \ and /)
	 */
	public static String extractFilenameFromPath(String fullFilepath) {

		String filenameWithExtension = fullFilepath.substring(fullFilepath.lastIndexOf("/") + 1).substring(
				fullFilepath.lastIndexOf("\\") + 1);

		int dotPosition = filenameWithExtension.lastIndexOf('.');
		if (dotPosition >= 0) {
			return filenameWithExtension.substring(0, dotPosition);
		} else {
			return filenameWithExtension;
		}

	}

}
