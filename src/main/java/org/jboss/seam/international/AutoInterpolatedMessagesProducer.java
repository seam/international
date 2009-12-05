package org.jboss.seam.international;

import java.util.ResourceBundle;

import javax.inject.Named;
import javax.enterprise.inject.Produces;

public class AutoInterpolatedMessagesProducer
{
   public
   @Produces
   @Named("org.jboss.seam.international.messages")
   //@RequestScoped
   @AutoInterpolatedMessages
   ResourceBundle getMessages(@Messages ResourceBundle messages, Interpolator interpolator)
   {
      return new InterpolatingResourceBundle(messages, interpolator);
   }
}
