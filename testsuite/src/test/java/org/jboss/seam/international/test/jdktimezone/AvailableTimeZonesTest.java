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

import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.jdktimezone.AvailableTimeZones;
import org.jboss.seam.international.jdktimezone.ForwardingTimeZone;
import org.jboss.seam.international.test.util.Deployments;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AvailableTimeZonesTest {
    @Deployment(name = "AvailableTimeZones")
    public static WebArchive createTestArchive() {
        return Deployments.addEmptyBeansXML(Deployments.createWebArchive())
                .addClasses(AvailableTimeZones.class, AvailableTimeZoneBean.class, ForwardingTimeZone.class);
    }

    @Inject
    Instance<AvailableTimeZoneBean> availBean;
    @Inject
    List<ForwardingTimeZone> timeZones;

    @Test
    public void testAvailableTimeZonesProducerViaBean() {
        Assert.assertNotNull(availBean);
        List<ForwardingTimeZone> list = availBean.get().getAvailTimeZones();
        Assert.assertNotNull(list);
        Assert.assertTrue(!list.isEmpty());
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testAvailableTimeZonesProducerDirect() {
        Assert.assertNotNull(timeZones);
        Assert.assertTrue(!timeZones.isEmpty());
        Assert.assertTrue(timeZones.size() > 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRawOffsetUnsupportedOperationException() {
        Assert.assertNotNull(timeZones);
        Assert.assertTrue(!timeZones.isEmpty());
        Assert.assertTrue(timeZones.size() > 0);
        ForwardingTimeZone ftz = timeZones.get(0);
        ftz.setRawOffset(123);
    }
}
