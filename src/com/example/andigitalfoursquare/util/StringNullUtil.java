package com.example.andigitalfoursquare.util;


/**
 * |
 *
 * @author Ramnath Suthakar
 *         Date: 23/06/2015
 */

public class StringNullUtil
{
    public static Boolean isNullOrEmpty(String myString)
    {
         return myString == null || "".equals(myString);
    }
}