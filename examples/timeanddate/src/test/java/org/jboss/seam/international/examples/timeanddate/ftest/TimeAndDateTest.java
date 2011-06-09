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
package org.jboss.seam.international.examples.timeanddate.ftest;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import org.jboss.test.selenium.AbstractTestCase;
import org.jboss.test.selenium.locator.XpathLocator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.jboss.test.selenium.guard.request.RequestTypeGuardFactory.*;
import static org.jboss.test.selenium.locator.LocatorFactory.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * A functional test for a TimeAndDate example
 *
 * @author <a href="http://community.jboss.org/people/mgencur">Martin Gencur</a>
 */
public class TimeAndDateTest extends AbstractTestCase {
    protected XpathLocator WORLDSCLOCK_LINK = xp("//a[contains(text(),'worldsclock')]");
    protected XpathLocator HOME_LINK = xp("//a[contains(text(),'home')]");
    protected XpathLocator AFRICA_LINK = xp("//a[contains(text(),'Africa')]");
    protected XpathLocator AMERICA_LINK = xp("//a[contains(text(),'America')]");
    protected XpathLocator ASIA_LINK = xp("//a[contains(text(),'Asia')]");
    protected XpathLocator ATLANTIC_LINK = xp("//a[contains(text(),'Atlantic')]");
    protected XpathLocator AUSTRALIA_LINK = xp("//a[contains(text(),'Australia')]");
    protected XpathLocator EUROPE_LINK = xp("//a[contains(text(),'Europe')]");
    protected XpathLocator PACIFIC_LINK = xp("//a[contains(text(),'Pacific')]");
    protected XpathLocator DATETIME_INFO = xp("//table/tbody/tr[1]/td[2]");
    protected XpathLocator TIMEZONE_INFO = xp("/html/body/div[3]/div/form/span");

    private static final String FIRST_ENTRY_ERR = "First entry in the table not found";
    private static final String LAST_ENTRY_ERR = "Last entry in the table not found";
    private static final String STANDARD_DATE_TIME_FORMAT = "HH:mm:ss MM/dd/yyyy";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(STANDARD_DATE_TIME_FORMAT);

    @BeforeMethod
    public void openStartUrl() throws MalformedURLException {
        selenium.open(new URL(contextPath.toString()));
    }

    @Test
    public void testHomeLink() {
        waitHttp(selenium).click(HOME_LINK);
        assertTrue(selenium.isTextPresent("This example application demonstrates several features of the Seam International module."),
                "The page should contain a description of the application");
    }

    @Test
    public void testAfricaTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(AFRICA_LINK);
        String tzInfo = selenium.getText(TIMEZONE_INFO);
        assertTrue(tzInfo.contains("Default Time Zone"), "Text not found on the page");
        int from = tzInfo.indexOf("-") + 2;
        assertNotNull(formatter.parse(tzInfo, new ParsePosition(from)), "The date should be parseable");
        assertTrue(selenium.isTextPresent("Africa/Abidjan"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("Africa/Windhoek"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }

    @Test
    public void testAmericaTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(AMERICA_LINK);
        assertTrue(selenium.isTextPresent("America/Adak"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("America/Yellowknife"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }

    @Test
    public void testAsiaTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(ASIA_LINK);
        assertTrue(selenium.isTextPresent("Asia/Aden"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("Asia/Yerevan"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }

    @Test
    public void testAtlanticTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(ATLANTIC_LINK);
        assertTrue(selenium.isTextPresent("Atlantic/Azores"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("Atlantic/Stanley"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }

    @Test
    public void testAustraliaTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(AUSTRALIA_LINK);
        assertTrue(selenium.isTextPresent("Australia/ACT"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("Australia/Yancowinna"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }

    @Test
    public void testEuropeTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(EUROPE_LINK);
        assertTrue(selenium.isTextPresent("Europe/Amsterdam"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("Europe/Zurich"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }

    @Test
    public void testPacificTimes() throws ParseException {
        waitHttp(selenium).click(WORLDSCLOCK_LINK);
        waitHttp(selenium).click(PACIFIC_LINK);
        assertTrue(selenium.isTextPresent("Pacific/Apia"), FIRST_ENTRY_ERR);
        assertTrue(selenium.isTextPresent("Pacific/Yap"), LAST_ENTRY_ERR);
        formatter.parse(selenium.getText(DATETIME_INFO));
    }
}
