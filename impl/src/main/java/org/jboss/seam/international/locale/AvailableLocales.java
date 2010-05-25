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
package org.jboss.seam.international.locale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;

@ApplicationScoped
public class AvailableLocales
{
   private String[] supportedLocaleKeys;

   @Inject
   Logger log;

   @Produces
   private List<Locale> locales = null;

   @PostConstruct
   public void init()
   {
      locales = new ArrayList<Locale>();

      for (String localeKey : supportedLocaleKeys)
      {
         try
         {
            Locale lc = LocaleUtils.toLocale(localeKey);
            locales.add(lc);
         }
         catch (IllegalArgumentException e)
         {
            log.error("AvailableLocales: Supported Locale key of " + localeKey + " was not formatted correctly", e);
         }
      }

      Collections.sort(locales, new Comparator<Locale>()
      {
         public int compare(final Locale a, final Locale b)
         {
            return a.toString().compareTo(b.toString());
         }
      });

      locales = Collections.unmodifiableList(locales);
   }
}
