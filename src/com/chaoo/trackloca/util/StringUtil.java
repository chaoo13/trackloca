package com.chaoo.trackloca.util;

public class StringUtil {
	
   public static String nvl(String string, String replace) {
           return (string != null) ? string : replace;
   }

   public static String nvl(String string) {
           return nvl(string, "");
   }

   public static boolean emptyString(String string) {
           return "".equals(nvl(string).trim());
   }
}
