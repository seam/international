package org.jboss.seam.international;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.jboss.seam.el.Expressions;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logger;

/**
 * Interpolates EL expressions in Strings
 * 
 * @author Gavin King
 */
public class Interpolator implements Serializable
{
   @Logger Log log;
   
   private Expressions expressions;
   private Locale locale;

   public Interpolator() {}
   
   public @Inject Interpolator(Expressions expressions, Locale locale)
   {
      this.expressions = expressions;
      this.locale = locale;
   }

   /**
    * Replace all EL expressions in the form #{...} with their evaluated
    * values.
    *
    * @param string a template
    * @return the interpolated string
    */
   public String interpolate(String string, Object... params)
   {
      if (params == null)
      {
         params = new Object[0];
      }

      if (params.length > 10)
      {
         throw new IllegalArgumentException("The number of parameters supplied (" + params.length + ") to the interpolator exceeds the limit of 10 parameters.");
      }

      if (string.indexOf('#') >= 0 || string.indexOf('{') >= 0)
      {
         string = interpolateExpressions(string, params);
      }

      return string;
   }

   private String interpolateExpressions(String string, Object... params)
   {
      StringTokenizer tokens = new StringTokenizer(string, "#{}", true);
      StringBuilder builder = new StringBuilder(string.length());
      try
      {
         while (tokens.hasMoreTokens())
         {
            String tok = tokens.nextToken();

            if ("#".equals(tok) && tokens.hasMoreTokens())
            {
               String nextTok = tokens.nextToken();

               while (nextTok.equals("#") && tokens.hasMoreTokens())
               {
                  builder.append(tok);
                  nextTok = tokens.nextToken();
               }

               if ("{".equals(nextTok))
               {
                  String expression = "#{" + tokens.nextToken() + "}";
                  try
                  {
                     Object value = expressions.createValueExpression(expression).getValue();
                     if (value != null)
                     {
                        builder.append(value);
                     }
                  }
                  catch (Exception e)
                  {
                     log.warn("exception interpolating string: " + string, e);
                  }
                  tokens.nextToken(); // the trailing "}"

               }
               else if (nextTok.equals("#"))
               {
                  // could be trailing #
                  builder.append("#");

               }
               else
               {
                  int index;
                  try
                  {
                     index = Integer.parseInt(nextTok.substring(0, 1));
                     if (index >= params.length)
                     {
                        //log.warn("parameter index out of bounds: " + index + " in: " + string);
                        builder.append("#").append(nextTok);
                     }
                     else
                     {
                        builder.append(params[index]).append(nextTok.substring(1));
                     }
                  }
                  catch (NumberFormatException nfe)
                  {
                     builder.append("#").append(nextTok);
                  }
               }
            }
            else if ("{".equals(tok))
            {
               StringBuilder expr = new StringBuilder();

               expr.append(tok);
               int level = 1;

               while (tokens.hasMoreTokens())
               {
                  String nextTok = tokens.nextToken();
                  expr.append(nextTok);

                  if (nextTok.equals("{"))
                  {
                     ++level;
                  }
                  else if (nextTok.equals("}"))
                  {
                     if (--level == 0)
                     {
                        try
                        {
                           if (params.length == 0)
                           {
                              builder.append(expr.toString());
                           }
                           else
                           {
                              String value = new MessageFormat(expr.toString(), locale).format(params);
                              builder.append(value);
                           }
                        }
                        catch (Exception e)
                        {
                           // if it is a bad message, use the expression itself
                           builder.append(expr);
                        }
                        expr = null;
                        break;
                     }
                  }
               }

               if (expr != null)
               {
                  builder.append(expr);
               }
            }
            else
            {
               builder.append(tok);
            }
         }
      }
      catch (Exception e)
      {
         log.warn("exception interpolating string: " + string, e);
      }

      return builder.toString();
   }
}
