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
package org.jboss.seam.international.test.status.builder;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.status.Bundles;
import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.builder.BundleTemplateMessageImpl;
import org.jboss.seam.international.status.builder.TemplateMessage;
import org.jboss.seam.international.status.builder.TemplateMessageImpl;
import org.jboss.seam.international.test.MockLogger;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RunWith(Arquillian.class)
public class BundleTemplateMessageImplTest
{
   @Deployment
   public static Archive<?> createTestArchive()
   {
      return ShrinkWrap.create("test.jar", JavaArchive.class).addClasses(MessageFactory.class, BundleTemplateMessageImpl.class, TemplateMessageImpl.class, Bundles.class, MockLogger.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
   }

   @Inject
   MessageFactory factory;

   @Test
   public void testParameterizedTemplate() throws Exception
   {
      String expected = "There are 5 cars, and they are all green; green is the best color.";
      TemplateMessage builder = factory.info("There are {0} cars, and they are all {1}; {1} is the best color.", 5, "green");
      assertEquals(expected, builder.build().getText());
   }

   @Test
   public void testParameterizedTemplateInsertsParamNumbersIfNotEnoughParamValues() throws Exception
   {
      String expected = "There are 5 cars, and they are all {1}; {1} is the best color.";
      TemplateMessage builder = factory.warn("There are {0} cars, and they are all {1}; {1} is the best color.", 5);
      assertEquals(expected, builder.build().getText());
   }

   @Test
   public void testPlainTextTemplate() throws Exception
   {
      String expected = "There are 5 cars, and they are all green; green is the best color.";
      TemplateMessage builder = factory.error("There are 5 cars, and they are all green; green is the best color.");
      assertEquals(expected, builder.build().getText());
   }

   @Test
   public void testPlainTextTemplateWithParamsIsUnmodified() throws Exception
   {
      String expected = "There are 5 cars, and they are all green; green is the best color.";
      TemplateMessage builder = factory.fatal("There are 5 cars, and they are all green; green is the best color.", "blue", "red", 6);
      assertEquals(expected, builder.build().getText());
   }
}
