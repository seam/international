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

/**
 * A basic implementation of {@link MutableMessage}.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class MessageImpl implements Message, MutableMessage
{
   private static final long serialVersionUID = -1812292372048679525L;

   private String summary;
   private String targets;
   private Level level;

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((level == null) ? 0 : level.hashCode());
      result = prime * result + ((summary == null) ? 0 : summary.hashCode());
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
      MessageImpl other = (MessageImpl) obj;
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

   /*
    * Getters & Setters
    */
   public String getText()
   {
      return summary;
   }

   public String getTargets()
   {
      return targets;
   }

   public Level getLevel()
   {
      return level;
   }

   public void setText(final String summary)
   {
      this.summary = summary;
   }

   public void setTargets(final String targets)
   {
      this.targets = targets;
   }

   public void setLevel(final Level level)
   {
      this.level = level;
   }

}
