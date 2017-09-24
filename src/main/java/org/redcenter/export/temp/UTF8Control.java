package org.redcenter.export.temp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * The Class UTF8Control.
 */
public class UTF8Control extends Control
{

    /** The Constant CHARSET_UTF_8. */
    public static final String CHARSET_UTF_8 = "UTF-8";

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ResourceBundle.Control#newBundle(java.lang.String,
     * java.util.Locale, java.lang.String, java.lang.ClassLoader, boolean)
     */
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException
    {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload)
        {
            URL url = loader.getResource(resourceName);
            if (url != null)
            {
                URLConnection connection = url.openConnection();
                if (connection != null)
                {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        }
        else
        {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null)
        {
            try
            {
                // Only this line is changed to make it to read properties files
                // as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, CHARSET_UTF_8));
            }
            finally
            {
                stream.close();
            }
        }
        return bundle;
    }
}
