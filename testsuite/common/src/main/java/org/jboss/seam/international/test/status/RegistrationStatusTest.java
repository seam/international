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
package org.jboss.seam.international.test.status;

import javax.inject.Inject;

import org.jboss.seam.international.status.MessagesImpl;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

//@RunWith(Arquillian.class)
public class RegistrationStatusTest {
    // @Deployment(name = "RegistrationStatus")
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar").addPackage(MessagesImpl.class.getPackage())
                .addClasses(RegisterAction.class, RegistrationStatusMessages.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
    }

    @Inject
    MessagesImpl messages;

    @Inject
    RegisterAction register;

    // @Before
    public void before() {
        messages.clear();
    }

    // @Test
    public void testMessages() {
        register.register("houdini");
        // assertEquals(1, messages.getAll().size());
    }

    @Test
    public void noTest() {

    }
}
