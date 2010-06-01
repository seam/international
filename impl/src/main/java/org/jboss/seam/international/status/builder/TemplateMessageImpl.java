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

import java.util.Arrays;

import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.MessageBuilder;
import org.jboss.seam.international.status.MessageImpl;
import org.jboss.seam.international.status.MutableMessage;

/**
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class TemplateMessageImpl implements MessageBuilder, TemplateMessage
{
   private final Interpolator interpolator = new Interpolator();

   private String summary;
   private Object[] summaryParams;

   private String targets;
   private Level level;

   public Message build()
   {
      MutableMessage message = new MessageImpl();

      message.setLevel(level);
      message.setText(interpolator.populate(summary, summaryParams));
      message.setTargets(targets);

      return message;
   }

   public TemplateMessageImpl text(final String summary)
   {
      this.summary = summary;
      return this;
   }

   public TemplateMessageImpl textParams(final Object... summaryParams)
   {
      this.summaryParams = summaryParams;
      return this;
   }

   public TemplateMessage targets(final String targets)
   {
      this.targets = targets;
      return this;
   }

   public TemplateMessage level(final Level level)
   {
      this.level = level;
      return this;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((level == null) ? 0 : level.hashCode());
      result = prime * result + ((summary == null) ? 0 : summary.hashCode());
      result = prime * result + Arrays.hashCode(summaryParams);
      result = prime * result + ((targets == null) ? 0 : targets.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (obj == null)
      {
         return false;
      }
      if (getClass() != obj.getClass())
      {
         return false;
      }
      TemplateMessageImpl other = (TemplateMessageImpl) obj;
      if (level == null)
      {
         if (other.level != null)
         {
            return false;
         }
      }
      else if (!level.equals(other.level))
      {
         return false;
      }
      if (summary == null)
      {
         if (other.summary != null)
         {
            return false;
         }
      }
      else if (!summary.equals(other.summary))
      {
         return false;
      }
      if (!Arrays.equals(summaryParams, other.summaryParams))
      {
         return false;
      }
      if (targets == null)
      {
         if (other.targets != null)
         {
            return false;
         }
      }
      else if (!targets.equals(other.targets))
      {
         return false;
      }
      return true;
   }
}
