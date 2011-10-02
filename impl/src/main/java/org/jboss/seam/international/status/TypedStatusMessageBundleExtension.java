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
package org.jboss.seam.international.status;

import java.lang.reflect.Field;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

import org.jboss.solder.logging.Logger;
import org.jboss.solder.literal.MessageBundleLiteral;
import org.jboss.solder.messages.MessageBundle;
import org.jboss.solder.reflection.annotated.AnnotatedTypeBuilder;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
public class TypedStatusMessageBundleExtension implements Extension {

    private static final Logger log = Logger.getLogger(TypedStatusMessageBundleExtension.class);

    <X> void detectBundleInjectionTargets(@Observes ProcessInjectionTarget<X> event, BeanManager beanManager) {
        AnnotatedType<X> type = event.getAnnotatedType();
        for (AnnotatedField<?> f : type.getFields()) {
            Field field = f.getJavaMember();
            Class<?> clz = field.getType();
            if (clz.isAnnotationPresent(MessageBundle.class)) {
                log.info("Add @MessageBundle to " + type.getJavaClass().getName() + "." + field.getName()
                        + " injection point for the type: " + clz.getName());
                AnnotatedTypeBuilder<X> typeBuilder = new AnnotatedTypeBuilder<X>().readFromType(type);
                typeBuilder.addToField(field, MessageBundleLiteral.INSTANCE);
                event.setInjectionTarget(beanManager.createInjectionTarget(typeBuilder.create()));
            }
        }
    }
}
