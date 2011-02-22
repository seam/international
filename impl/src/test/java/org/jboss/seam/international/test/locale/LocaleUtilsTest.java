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
package org.jboss.seam.international.test.locale;

import java.util.Locale;

import junit.framework.Assert;

import org.jboss.seam.international.locale.LocaleUtils;
import org.junit.Test;

public class LocaleUtilsTest {
    @Test
    public void testLocaleLanguage() {
        String lang = "en";
        Locale lc = LocaleUtils.toLocale(lang);
        Assert.assertEquals(lang, lc.getLanguage());
    }

    @Test
    public void testLocaleCountryHyphen() {
        String locale = "en-GB";
        Locale lc = LocaleUtils.toLocale(locale);
        Assert.assertEquals(locale.substring(0, 2), lc.getLanguage());
        Assert.assertEquals(locale.substring(3), lc.getCountry());
    }

    @Test
    public void testLocaleCountryUnderscore() {
        String locale = "en_GB";
        Locale lc = LocaleUtils.toLocale(locale);
        Assert.assertEquals(locale.substring(0, 2), lc.getLanguage());
        Assert.assertEquals(locale.substring(3), lc.getCountry());
    }

    @Test
    public void testLocaleVariantHyphen() {
        String locale = "en--WIN";
        Locale lc = LocaleUtils.toLocale(locale);
        Assert.assertEquals(locale.substring(0, 2), lc.getLanguage());
        Assert.assertEquals(locale.substring(4), lc.getVariant());
    }

    @Test
    public void testLocaleVariantUnderscore() {
        String locale = "en__WIN";
        Locale lc = LocaleUtils.toLocale(locale);
        Assert.assertEquals(locale.substring(0, 2), lc.getLanguage());
        Assert.assertEquals(locale.substring(4), lc.getVariant());
    }

    @Test
    public void testLocaleCountryVariantHyphen() {
        String locale = "en-GB-WIN";
        Locale lc = LocaleUtils.toLocale(locale);
        Assert.assertEquals(locale.substring(0, 2), lc.getLanguage());
        Assert.assertEquals(locale.substring(3, 5), lc.getCountry());
        Assert.assertEquals(locale.substring(6), lc.getVariant());
    }

    @Test
    public void testLocaleCountryVariantUnderscore() {
        String locale = "en_US_WIN";
        Locale lc = LocaleUtils.toLocale(locale);
        Assert.assertEquals(locale.substring(0, 2), lc.getLanguage());
        Assert.assertEquals(locale.substring(3, 5), lc.getCountry());
        Assert.assertEquals(locale.substring(6), lc.getVariant());
    }

    @Test
    public void testLocaleNull() {
        Locale lc = LocaleUtils.toLocale(null);
        Assert.assertTrue(lc == null);
    }

    @Test
    public void testLocaleLengthFailure() {
        // Test length of 1 char
        try {
            LocaleUtils.toLocale("s");
            Assert.fail("IllegalArgumentException not thrown for length of 1 char");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }

        // Test length of 3 chars
        try {
            LocaleUtils.toLocale("sss");
            Assert.fail("IllegalArgumentException not thrown for length of 3 chars");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }

        // Test length of 4 chars
        try {
            LocaleUtils.toLocale("ssss");
            Assert.fail("IllegalArgumentException not thrown for length of 4 chars");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }

        // Test length of 6 chars
        try {
            LocaleUtils.toLocale("ssssss");
            Assert.fail("IllegalArgumentException not thrown for length of 6 chars");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }

        // Test length of 8 chars
        try {
            LocaleUtils.toLocale("ssssssss");
            Assert.fail("IllegalArgumentException not thrown for length of 8 chars");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }
    }

    @Test
    public void testLocaleCountryFailure() {
        String locale = "en;GB";
        try {
            LocaleUtils.toLocale(locale);
            Assert.fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }
    }

    @SuppressWarnings("static-access")
    @Test
    public void testLocaleCountryVariantFailure() {
        String locale = "en_GB'WIN";
        try {
            LocaleUtils lu = new LocaleUtils();
            lu.toLocale(locale);
            Assert.fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }
    }

    @Test
    public void testLocaleNonAlphas() {
        try {
            LocaleUtils.toLocale(";;");
            Assert.fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }

        try {
            LocaleUtils.toLocale("en_;;");
            Assert.fail("IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected outcome
        }
    }
}
