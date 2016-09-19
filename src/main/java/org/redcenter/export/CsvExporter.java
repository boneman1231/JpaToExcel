package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvExporter implements IExporter
{
    protected static final int MAX_ROW_IN_MEM = 1000;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected OutputStream os;
    protected int line = 0;
    protected LinkedHashMap<String, Field> map = null;

    public CsvExporter(File file) throws FileNotFoundException
    {
        os = new FileOutputStream(file);
    }

    /**
     * Write records with header.
     */
    public void exoprt(List<?> records) throws IOException, IllegalArgumentException, IllegalAccessException
    {
        if (records == null || records.size() == 0)
        {
            return;
        }

        // prepare columns
        Class<?> clazz = records.get(0).getClass();
        IFieldFilter filter = new JpaFieldFilter();
        map = filter.getFieldMap(clazz);

        // create header with bold font

        // create values

        // write to file
    }

    /**
     * Write records without header.
     * Need to execute export() first.
     */
    public void append(List<?> records) throws Exception
    {
        if (records == null || records.size() == 0)
        {
            return;
        }

        // prepare columns
        if (map == null)
        {
            throw new Exception("Please execute export() first.");
        }

        // create values

        // write to file
    }

    public void close() throws IOException
    {
        os.close();
    }
}
