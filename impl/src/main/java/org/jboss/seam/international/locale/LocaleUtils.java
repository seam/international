/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.international.locale;

import java.util.Locale;

public final class LocaleUtils {
    /**
     * <p>
     * Converts a String to a Locale.
     * </p>
     * 
     * <p>
     * This method takes the string format of a locale and creates the locale object from it.
     * </p>
     * 
     * <pre>
     *   LocaleUtils.toLocale("en")         = new Locale("en", "")
     *   LocaleUtils.toLocale("en_GB")      = new Locale("en", "GB")
     *   LocaleUtils.toLocale("en-GB")      = new Locale("en", "GB")
     *   LocaleUtils.toLocale("en_GB_xxx")  = new Locale("en", "GB", "xxx")   (#)
     *   LocaleUtils.toLocale("en-GB-xxx")  = new Locale("en", "GB", "xxx")   (#)
     *   LocaleUtils.toLocale("en__xxx")    = new Locale("en", "", "xxx")     (#)
     *   LocaleUtils.toLocale("en--xxx")    = new Locale("en", "", "xxx")     (#)
     * </pre>
     * 
     * <p>
     * This method validates the input strictly. The language code must be lowercase. The country code must be uppercase. The
     * separator must be an underscore or hyphen. The length must be correct.
     * </p>
     * 
     * @param str the locale String to convert, null returns null
     * @return a Locale, null if null input
     * @throws IllegalArgumentException if the string is an invalid format
     */
    public static Locale toLocale(String str) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len != 2 && len != 5 && len < 7) {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        char ch0 = str.charAt(0);
        char ch1 = str.charAt(1);
        if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z') {
            throw new IllegalArgumentException("Invalid locale format: " + str);
        }
        if (len == 2) {
            return new Locale(str);
        } else {
            char ch2 = str.charAt(2);
            if (ch2 != '_' && ch2 != '-') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            char ch3 = str.charAt(3);
            if (ch3 == '_' || ch3 == '-') {
                return new Locale(str.substring(0, 2), "", str.substring(4));
            }
            char ch4 = str.charAt(4);
            if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z') {
                throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            if (len == 5) {
                return new Locale(str.substring(0, 2), str.substring(3, 5));
            } else {
                char ch5 = str.charAt(5);
                if (ch5 != '_' && ch5 != '-') {
                    throw new IllegalArgumentException("Invalid locale format: " + str);
                }
                return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
            }
        }
    }
}
