/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.international.locale;

import java.util.Locale;

public final class LocaleUtils
{
   /**
    * <p>
    * Converts a String to a Locale.
    * </p>
    * 
    * <p>
    * This method takes the string format of a locale and creates the locale
    * object from it.
    * </p>
    * 
    * <pre>
    *   LocaleUtils.toLocale("en")         = new Locale("en", "")
    *   LocaleUtils.toLocale("en_GB")      = new Locale("en", "GB")
    *   LocaleUtils.toLocale("en_GB_xxx")  = new Locale("en", "GB", "xxx")   (#)
    * </pre>
    * 
    * <p>
    * This method validates the input strictly. The language code must be
    * lowercase. The country code must be uppercase. The separator must be an
    * underscore. The length must be correct.
    * </p>
    * 
    * @param str the locale String to convert, null returns null
    * @return a Locale, null if null input
    * @throws IllegalArgumentException if the string is an invalid format
    */
   public static Locale toLocale(String str)
   {
      if (str == null)
      {
         return null;
      }
      int len = str.length();
      if (len != 2 && len != 5 && len < 7)
      {
         throw new IllegalArgumentException("Invalid locale format: " + str);
      }
      char ch0 = str.charAt(0);
      char ch1 = str.charAt(1);
      if (ch0 < 'a' || ch0 > 'z' || ch1 < 'a' || ch1 > 'z')
      {
         throw new IllegalArgumentException("Invalid locale format: " + str);
      }
      if (len == 2)
      {
         return new Locale(str, "");
      }
      else
      {
         if (str.charAt(2) != '_')
         {
            throw new IllegalArgumentException("Invalid locale format: " + str);
         }
         char ch3 = str.charAt(3);
         if (ch3 == '_')
         {
            return new Locale(str.substring(0, 2), "", str.substring(4));
         }
         char ch4 = str.charAt(4);
         if (ch3 < 'A' || ch3 > 'Z' || ch4 < 'A' || ch4 > 'Z')
         {
            throw new IllegalArgumentException("Invalid locale format: " + str);
         }
         if (len == 5)
         {
            return new Locale(str.substring(0, 2), str.substring(3, 5));
         }
         else
         {
            if (str.charAt(5) != '_')
            {
               throw new IllegalArgumentException("Invalid locale format: " + str);
            }
            return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
         }
      }
   }
}
