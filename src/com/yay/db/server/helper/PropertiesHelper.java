package com.yay.db.server.helper;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesHelper
{

    public static String getResource( String inKey )
    {
        StringBuilder strBuilder = new StringBuilder();
        try
        {
            ResourceBundle applicationBundle = ResourceBundle
                    .getBundle( "server" );
            strBuilder.append( applicationBundle.getString( inKey ) );
        }
        catch ( final MissingResourceException ex )
        {
        	ex.printStackTrace();
            strBuilder.append( "!" )
                    .append( inKey ).append( "!" );
        }
        return strBuilder.toString();
    }
}
