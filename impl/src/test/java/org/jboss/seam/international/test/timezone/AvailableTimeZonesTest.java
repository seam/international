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
package org.jboss.seam.international.test.timezone;

import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.timezone.AvailableTimeZones;
import org.jboss.seam.international.timezone.ForwardingTimeZone;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AvailableTimeZonesTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses(AvailableTimeZones.class, AvailableTimeZoneBean.class, ForwardingTimeZone.class).addManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
   }

   @Inject
   Instance<AvailableTimeZoneBean> availBean;
   @Inject
   List<DateTimeZone> timeZones;

   @Test
   public void testAvailableTimeZonesProducerViaBean()
   {
      Assert.assertNotNull(availBean);
      List<DateTimeZone> list = availBean.get().getAvailTimeZones();
      Assert.assertNotNull(list);
      Assert.assertTrue(!list.isEmpty());
      Assert.assertTrue(list.size() > 0);
   }

   @Test
   public void testAvailableTimeZonesProducerDirect()
   {
      Assert.assertNotNull(timeZones);
      Assert.assertTrue(!timeZones.isEmpty());
      Assert.assertTrue(timeZones.size() > 0);
   }
}
