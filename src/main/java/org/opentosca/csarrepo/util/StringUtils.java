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

import java.util.List;

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
}