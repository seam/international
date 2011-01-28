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

import org.jboss.seam.solder.literal.MessageBundleLiteral;
import org.jboss.seam.solder.logging.MessageBundle;
import org.jboss.seam.solder.reflection.annotated.AnnotatedTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 *
 */
public class MessagesExtension implements Extension
{

   private static final Logger log = LoggerFactory.getLogger(MessagesExtension.class);

   <X> void detectBundleInjectionTargets(@Observes ProcessInjectionTarget<X> event, BeanManager beanManager)
   {
      AnnotatedType<X> type = event.getAnnotatedType();
      for (AnnotatedField<?> f : type.getFields())
      {
         Field field = f.getJavaMember();
         Class<?> clz = field.getType();
         if (clz.isAnnotationPresent(MessageBundle.class))
         {
            log.info("Add @MessageBundle to " + type.getJavaClass().getName() + "." + field.getName() + " injection point for the type: " + clz.getName());
            AnnotatedTypeBuilder<X> typeBuilder = new AnnotatedTypeBuilder<X>().readFromType(type);
            typeBuilder.addToField(field, MessageBundleLiteral.INSTANCE);
            event.setInjectionTarget(beanManager.createInjectionTarget(typeBuilder.create()));
         }
      }
   }
}
