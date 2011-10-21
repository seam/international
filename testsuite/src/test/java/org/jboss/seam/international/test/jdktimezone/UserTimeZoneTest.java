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
package org.jboss.seam.international.test.jdktimezone;

import java.util.TimeZone;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.Alter;
import org.jboss.seam.international.jdktimezone.DefaultTimeZoneProducer;
import org.jboss.seam.international.jdktimezone.ForwardingTimeZone;
import org.jboss.seam.international.jdktimezone.UserTimeZoneProducer;
import org.jboss.seam.international.test.util.Deployments;
import org.jboss.seam.international.test.util.Libraries;
import org.jboss.seam.international.timezone.DefaultTimeZone;
import org.jboss.solder.core.Client;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserTimeZoneTest {
    @Deployment(name = "UserTimeZone")
    public static WebArchive createTestArchive() {
        return Deployments.addEmptyBeansXML(Deployments.createWebArchive())
                .addAsLibraries(Libraries.solderLibraryWithTransitives())
                .addClass(UserTimeZoneProducer.class)
                .addClass(DefaultTimeZoneProducer.class)
                .addClass(DefaultTimeZone.class)
                .addClass(Alter.class)
                .addClass(ForwardingTimeZone.class);
        // .addManifestResource("org/jboss/seam/international/test/datetimezone/user-timezone.xml",
        // ArchivePaths.create("beans.xml"));
    }

    @Inject
    @Client
    TimeZone timeZone;

    @Inject
    @Alter
    @Client
    Event<TimeZone> timeZoneEvent;

    @Inject
    @Client
    Instance<TimeZone> timeZoneSource;

    @Test
    public void testUserTimeZoneProducerDirect() {
        Assert.assertNotNull(timeZone);
    }

    @Test
    public void testUserTimeZoneEvent() {
        TimeZone tijuana = TimeZone.getTimeZone("America/Tijuana");
        Assert.assertNotNull(timeZone);
        Assert.assertFalse(timeZone.equals(tijuana));
        timeZoneEvent.fire(tijuana);
        TimeZone tz = timeZoneSource.get();
        Assert.assertNotNull(tz);
        Assert.assertTrue(tz.equals(tijuana));
    }
}
