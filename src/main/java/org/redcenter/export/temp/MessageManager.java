package org.redcenter.export.temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The Class MessageManager.
 *
 * @author boneman
 */
public class MessageManager
{

    /** The Constant LOCALE_ENGLISH. */
    public static final String LOCALE_ENGLISH = "EN";

    /** The Constant LOCALE_TRADITIONAL_CHINESE. */
    public static final String LOCALE_TRADITIONAL_CHINESE = "CN";

    /** The Constant LOCALE_SIMPLIFIED_CHINESE. */
    public static final String LOCALE_SIMPLIFIED_CHINESE = "TW";

    /** The props. */
    private Properties props = null;

    /** The resource bundle. */
    private ResourceBundle resourceBundle = null;

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException
    {
        MessageManager manager = new MessageManager(".", "test", "TW");
        System.out.println(manager.getMessage("aaa"));
        manager = new MessageManager(".", "test", "CN");
        System.out.println(manager.getMessage("aaa"));
        manager = new MessageManager(".", "test", "EN");
        System.out.println(manager.getMessage("aaa"));
    }

    /**
     * Instantiates a new message manager.
     *
     * @param filePath
     *            the file path
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public MessageManager(String filePath) throws IOException
    {
        props = new Properties();
        InputStream inputStream = new FileInputStream(filePath);
        try
        {
            Reader reader = new InputStreamReader(inputStream, UTF8Control.CHARSET_UTF_8);
            try
            {
                props.load(reader);
            }
            finally
            {
                reader.close();
            }
        }
        finally
        {
            inputStream.close();
        }
    }

    /**
     * Instantiates a new message manager.
     *
     * @param path
     *            the path
     * @param fileBaseName
     *            the file base name
     * @param language
     *            the language
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public MessageManager(String path, String fileBaseName, String language) throws IOException
    {
        Locale locale = getLocale(language);
        init(path, fileBaseName, locale);
    }

    /**
     * Instantiates a new message manager.
     *
     * @param path
     *            the path
     * @param fileBaseName
     *            the file base name
     * @param locale
     *            the locale
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public MessageManager(String path, String fileBaseName, Locale locale) throws IOException
    {
        init(path, fileBaseName, locale);
    }

    /**
     * Inits the.
     *
     * @param folderPath
     *            the folder path
     * @param fileBaseName
     *            the file base name
     * @param locale
     *            the locale
     * @throws MalformedURLException
     *             the malformed URL exception
     */
    private void init(String folderPath, String fileBaseName, Locale locale) throws MalformedURLException
    {
        File configFolder = new File(folderPath);
        URL[] urls = { configFolder.toURI().toURL() };
        ClassLoader loader = new URLClassLoader(urls);
        resourceBundle = ResourceBundle.getBundle(fileBaseName, locale, loader, new UTF8Control());
    }

    /**
     * Gets the locale.
     *
     * @param language
     *            the language
     * @return the locale
     */
    private Locale getLocale(String language)
    {
        switch (language)
        {
        case "TW":
            return Locale.TRADITIONAL_CHINESE;
        case "CN":
            return Locale.SIMPLIFIED_CHINESE;
        case "EN":
            return Locale.US;
        default:
            return Locale.getDefault();
        }
    }

    /**
     * Gets the message.
     *
     * @param key
     *            the key
     * @return the message
     */
    public String getMessage(String key)
    {
        if (resourceBundle != null)
        {
            return resourceBundle.getString(key);
        }
        if ((props) != null)
        {
            return props.getProperty(key);
        }
        return null;
    }
}
