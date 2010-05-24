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

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Represents the name/key pair for a {@link ResourceBundle} lookup.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class BundleKey implements Serializable
{
   private static final long serialVersionUID = -4817189437146173796L;

   private String bundle;
   private String key;

   public BundleKey(final String bundle, final String key)
   {
      super();
      this.bundle = bundle;
      this.key = key;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
      result = prime * result + ((key == null) ? 0 : key.hashCode());
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
      BundleKey other = (BundleKey) obj;
      if (bundle == null)
      {
         if (other.bundle != null)
         {
            return false;
         }
      }
      else if (!bundle.equals(other.bundle))
      {
         return false;
      }
      if (key == null)
      {
         if (other.key != null)
         {
            return false;
         }
      }
      else if (!key.equals(other.key))
      {
         return false;
      }
      return true;
   }

   /*
    * Getters & Setters
    */
   public String getBundle()
   {
      return bundle;
   }

   public void setBundle(final String bundle)
   {
      this.bundle = bundle;
   }

   public String getKey()
   {
      return key;
   }

   public void setKey(final String key)
   {
      this.key = key;
   }
}
