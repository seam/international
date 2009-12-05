/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
 *
 * $Id$
 */
package org.jboss.seam.international;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.jboss.seam.international.util.Strings;

/**
 * A status message which can be created in the business layer and displayed
 * in the view layer
 *
 * @author Pete Muir
 */
public class StatusMessage implements Serializable
{
    private static final long serialVersionUID = -177606761939586298L;

   /**
    * The severity of the status message
    */
   public enum Severity
   {
      INFO,
      WARN,
      ERROR,
      FATAL;
   }
   
   private String summaryTemplate;
   private String summary;
   private String detailTemplate;
   private String detail;
   private Severity severity = Severity.INFO;

   /**
    * Create a status message using the provided summary and detail templates.
    * The severity is only assigned if a summary is provided. Note that te
    * StatusMessages component is responsible for resolving templates from the
    * message bundle.
    */
   public StatusMessage(Severity severity, String summaryTemplate, String detailTemplate)
   {
      this.summaryTemplate = summaryTemplate;
      this.detailTemplate = detailTemplate;
      if (!Strings.isEmpty(summaryTemplate))
      {
         this.severity = severity;
      }
   }
   
   /**
    * Create a status message using the summary template and a null detail
    * template. The severity is only assigned if a summary is provided. Note
    * that te StatusMessages component is responsible for resolving templates
    * from the message bundle.
    */
   public StatusMessage(Severity severity, String template)
   {
      this(severity, template, null);
   }

   public boolean isEmpty()
   {
      return Strings.isEmpty(summary) && Strings.isEmpty(summaryTemplate);
   }

   public void interpolate(Interpolator interpolator, Object... params)
   {
      if (!Strings.isEmpty(summaryTemplate))
      {
         this.summary = interpolator.interpolate(summaryTemplate, params);
      }
      if (!Strings.isEmpty(detailTemplate))
      {
         this.detail = interpolator.interpolate(detailTemplate, params);
      }
   }

   /**
    * Get the message
    * 
    */
   public String getSummary()
   {
      return summary;
   }

   /**
    * Get the message severity
    */
   public Severity getSeverity()
   {
      return severity;
   }

   public String getDetail()
   {
      return detail;
   }

   public static String getBundleMessage(String key, String defaultMessageTemplate)
   {
      String messageTemplate = defaultMessageTemplate;
      if (key != null)
      {
         //FIXME disable temporarily
         //ResourceBundle resourceBundle = ResourceBundleProducer.getBundle();
         ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
         if (resourceBundle != null)
         {
            try
            {
               String bundleMessage = resourceBundle.getString(key);
               if (bundleMessage != null)
               {
                  messageTemplate = bundleMessage;
               }
            }
            catch (MissingResourceException mre)
            {
               //swallow
            }
         }
      }
      return messageTemplate;
   }

   @Override
   public String toString()
   {
      return "[" + severity + "] " + summary + " (" + detail + ")";
   }
}
