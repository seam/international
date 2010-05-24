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
package org.jboss.seam.international.status.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Populates an interpolated string using the given template and parameters:
 * <p>
 * <b>For example:</b><br>
 * Template:
 * <code>"This is a {0} template with {1} parameters. Just {1}."</code><br>
 * Parameters: <code>"simple", 2</code><br>
 * Result: <code>"This is a simple template with 2 parameters. Just 2"</code>
 * 
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
class Interpolator
{
   private static final String templateRegex = "\\{(\\d+)\\}";
   private static final Pattern templatePattern = Pattern.compile(templateRegex);

   /**
    * Populate a template with the corresponding parameters.
    */
   public String populate(final String template, final Object... params)
   {
      StringBuffer result = new StringBuffer();
      if (template != null)
      {
         Matcher matcher = templatePattern.matcher(template);
         while (matcher.find())
         {
            int index = Integer.valueOf(matcher.group(1));
            Object value = matcher.group();

            if (params.length > index)
            {
               if (params[index] != null)
               {
                  value = params[index];
               }
            }
            matcher.appendReplacement(result, value.toString());
         }
         matcher.appendTail(result);
      }
      return result.toString();
   }
}
