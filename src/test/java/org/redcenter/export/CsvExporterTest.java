package org.redcenter.export;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvExporterTest extends ExporterTest
{
    private static final String TEST_JPA_FILE_NAME = "testJpa.csv";
    private static final String TEST_CUST_FILE_NAME = "testCust.csv";
    private static final String TEST_CUST_MASS_FILE_NAME = "testCustMass.csv";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        new File(TEST_JPA_FILE_NAME).deleteOnExit();
        new File(TEST_CUST_FILE_NAME).deleteOnExit();
        new File(TEST_CUST_MASS_FILE_NAME).deleteOnExit();
    }

    @Test
    public void testJpaExport() throws IllegalArgumentException, IllegalAccessException, IOException
    {
        testJpaExport(TEST_JPA_FILE_NAME);

        // check content
        Map<String, String[]> map = readCsv(TEST_JPA_FILE_NAME);
        Assert.assertArrayEquals(getJpaHeader(), map.get("HEADER"));
        Assert.assertArrayEquals(getJpaContent(), map.get("CONTENT"));
    }

    @Test
    public void testCustExport() throws IllegalArgumentException, IllegalAccessException, IOException
    {
        testCustExport(TEST_CUST_FILE_NAME);
        
        // check content
        Map<String, String[]> map = readCsv(TEST_CUST_FILE_NAME);
        Assert.assertArrayEquals(getCustHeader(), map.get("HEADER"));
        Assert.assertArrayEquals(getCustContent(), map.get("CONTENT"));
    }

    @Test
    public void testExoprtWithMassData() throws IllegalArgumentException, IllegalAccessException, IOException
    {
        exportMass(TEST_CUST_MASS_FILE_NAME, 1000, CsvExporter.MAX_ROW_IN_MEM);
         
        // TODO check content without traverse whole file
        Map<String, String[]> map = readCsv(TEST_CUST_MASS_FILE_NAME);
        Assert.assertArrayEquals(getCustHeader(), map.get("HEADER"));
        Assert.assertArrayEquals(getCustContent(), map.get("CONTENT"));
    }

    private Map<String, String[]> readCsv(String fileName) throws FileNotFoundException, IOException
    {
        Map<String, String[]> map = new HashMap<String, String[]>();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(fileName));
            String line;

            // header
            if ((line = br.readLine()) != null)
            {
                String[] fields = line.split(CsvExporter.SEPARATOR);
                map.put("HEADER", fields);
            }

            // first line of content
            if ((line = br.readLine()) != null)
            {
                String[] fields = line.split(CsvExporter.SEPARATOR);
                map.put("CONTENT", fields);
            }
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
        }
        return map;
    }
    
    @Override
    protected <T> CsvExporter<T> getExporter(File file) throws FileNotFoundException
    {
        return new CsvExporter<T>(file);
    }
}
