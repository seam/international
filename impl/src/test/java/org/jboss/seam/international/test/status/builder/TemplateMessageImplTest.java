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
package org.jboss.seam.international.test.status.builder;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.builder.TemplateMessage;
import org.jboss.seam.international.status.builder.TemplateMessageImpl;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class TemplateMessageImplTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses(MessageFactory.class, TemplateMessageImpl.class)
                .addManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Inject
    MessageFactory factory;

    @Test
    public void testParameterizedTemplate() throws Exception {
        String expected = "There are 5 cars, and they are all green; green is the best color.";
        TemplateMessage builder = factory.info("There are {0} cars, and they are all {1}; {1} is the best color.", 5, "green");
        assertEquals(expected, builder.build().getText());
    }

    @Test
    public void testParameterizedTemplateInsertsParamNumbersIfNotEnoughParamValues() throws Exception {
        String expected = "There are 5 cars, and they are all {1}; {1} is the best color.";
        TemplateMessage builder = factory.warn("There are {0} cars, and they are all {1}; {1} is the best color.", 5);
        assertEquals(expected, builder.build().getText());
    }

    @Test
    public void testPlainTextTemplate() throws Exception {
        String expected = "There are 5 cars, and they are all green; green is the best color.";
        TemplateMessage builder = factory.error("There are 5 cars, and they are all green; green is the best color.");
        assertEquals(expected, builder.build().getText());
    }

    @Test
    public void testPlainTextTemplateWithParamsIsUnmodified() throws Exception {
        String expected = "There are 5 cars, and they are all green; green is the best color.";
        TemplateMessage builder = factory.fatal("There are 5 cars, and they are all green; green is the best color.", "blue",
                "red", 6);
        assertEquals(expected, builder.build().getText());
    }
}
