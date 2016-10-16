package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class ExcelExporterTest extends ExporterTest
{
    private static final String TEST_JPA_FILE_NAME = "testJpa.xlsx";
    private static final String TEST_CUST_FILE_NAME = "testCust.xlsx";
    private static final String TEST_CUST_MASS_FILE_NAME = "testCustMass.xlsx";
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        new File(TEST_JPA_FILE_NAME).deleteOnExit();
        new File(TEST_CUST_FILE_NAME).deleteOnExit();
    }

    @Test
    public void testJpaExport() throws IllegalArgumentException, IllegalAccessException, IOException
    {
        // TODO check content
        // Assert.fail("Not yet implemented");
        testJpaExport(TEST_JPA_FILE_NAME);
    }

    @Test
    public void testCustExport() throws IllegalArgumentException, IllegalAccessException, IOException
    {
        // TODO check content
        // Assert.fail("Not yet implemented");
        testCustExport(TEST_CUST_FILE_NAME);
    } 

    @Test
    public void testExoprtWithMassData() throws IllegalArgumentException, IllegalAccessException, IOException
    {
        exportMass(TEST_CUST_MASS_FILE_NAME, 1000, ExcelExporter.MAX_ROW_IN_MEM);
        
        // TODO check content without traverse whole file
        System.out.println("End");
    }

    @Override
    protected <T> ExcelExporter<T> getExporter(File file) throws FileNotFoundException
    {  
        return new ExcelExporter<T>(file);
    }
}
