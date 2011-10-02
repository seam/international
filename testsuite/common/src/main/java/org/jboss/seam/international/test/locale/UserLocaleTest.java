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
package org.jboss.seam.international.test.locale;

import java.util.Locale;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.Alter;
import org.jboss.seam.international.test.util.Deployments;
import org.jboss.seam.international.test.util.Libraries;
import org.jboss.solder.core.Client;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserLocaleTest {
    @Deployment(name = "UserLocale")
    public static WebArchive createTestArchive() {
        return Deployments.addEmptyBeansXML(Deployments.createWebArchive())
                .addAsLibraries(Libraries.seamIntlLibraryWithTransitives());
        // .addManifestResource("org/jboss/seam/international/test/locale/default-locale.xml",
        // ArchivePaths.create("beans.xml"));
    }

    @Inject
    @Client
    Locale locale;

    @Inject
    @Alter
    @Client
    Event<Locale> localeEvent;

    @Inject
    @Client
    Instance<Locale> localeSource;

    @Test
    public void testUserLocaleProducerDirect() {
        Assert.assertNotNull(locale);
    }

    @Test
    public void testUserLocaleEvent() {
        Locale canada = Locale.CANADA;
        Assert.assertNotNull(locale);
        Assert.assertFalse(locale.equals(canada));
        localeEvent.fire(canada);
        Locale lc = localeSource.get();
        Assert.assertNotNull(lc);
        Assert.assertTrue(lc.equals(canada));
    }
}
