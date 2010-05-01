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

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.test.MockLogger;
import org.jboss.seam.international.timezone.DefaultTimeZoneProducer;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DefaultTimeZoneOverrideTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      JavaArchive arc = Archives.create("test.jar", JavaArchive.class).addClasses(MockLogger.class, DefaultTimeZoneProducer.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml")).addManifestResource("org/jboss/seam/international/test/timezone/seam-beans.xml", ArchivePaths.create("seam-beans.xml"));
      System.out.println(arc.toString(true));
      return arc;
   }

   @Inject
   DateTimeZone timeZone;

   @Test
   public void testDefaultTimeZoneProducerDirect()
   {
      Assert.assertNotNull(timeZone);
//      Assert.assertEquals("America/Tijuana", timeZone.getID());
   }
}
