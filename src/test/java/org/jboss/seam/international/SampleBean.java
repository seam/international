package org.jboss.seam.international;

import javax.inject.Named;

public
@Named
class SampleBean
{
   public String getFeature()
   {
      return "rich";
   }
   
   public String getPlatform()
   {
      return "Java EE";
   }
}
