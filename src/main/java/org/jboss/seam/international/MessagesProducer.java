package org.jboss.seam.international;

import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;

import org.jboss.seam.resources.ResourceLoader;

public class MessagesProducer
{
   private String bundleName = "messages";
   
   public
   @Produces
   //@RequestScoped
   @Messages
   ResourceBundle getResourceBundle(ResourceLoader resourceLoader)
   {
      return resourceLoader.loadBundle(bundleName);
   }
}
