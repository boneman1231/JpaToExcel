package org.redcenter.export.temp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtil
{
    private static final String CONFIG_FOLDER_PATH = ".";
    private static final String MSG_CFG_BASE_NAME = "test";

    public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException
    {
        System.out.println(getMessage("aaa", "TW"));
        System.out.println(getMessage("aaa", "CN"));
        System.out.println(getMessage("aaa", "EN"));
    }

    public static String getMessage(String key, String language) throws MalformedURLException
    {
        File configFolder = new File(CONFIG_FOLDER_PATH);
        URL[] urls = { configFolder.toURI().toURL() };
        ClassLoader loader = new URLClassLoader(urls);

        // get locale
        Locale locale;
        switch (language)
        {
        case "TW":
            locale = Locale.TRADITIONAL_CHINESE;
            break;
        case "CN":
            locale = Locale.SIMPLIFIED_CHINESE;
            break;
        case "EN":
            locale = Locale.US;
            break;
        default:
            locale = Locale.getDefault();
        }

        ResourceBundle resourceBundle = ResourceBundle.getBundle(MSG_CFG_BASE_NAME, locale, loader, new UTF8Control());
        return resourceBundle.getString(key);
    }
}
