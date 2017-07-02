package org.redcenter.export;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class TextToExcelExporter
{
    private static final String FILE_EXTENSION_XLSX = ".xlsx";
    private static final int MAX_ROW_IN_MEM = 100;
    private static final String SHEET_NAME = "RAW";
    private static final String SEPARATER = "\t";

    public static void main(String args[])
    {
        String filename = "result.txt";
        new TextToExcelExporter().export(filename);
    }

    public void export(String filename)
    {
        BufferedReader br = null;
        FileOutputStream os = null;
        SXSSFWorkbook wb = null;
        try
        {
            // prepare output
            String newFilename = FilenameUtils.getBaseName(filename) + FILE_EXTENSION_XLSX;
            os = new FileOutputStream(newFilename);
            wb = new SXSSFWorkbook(MAX_ROW_IN_MEM);
            Sheet sheet = wb.createSheet(SHEET_NAME);

            // read input
            br = new BufferedReader(new FileReader(filename));
            String input;
            int line = 0;
            while ((input = br.readLine()) != null)
            {
                Row row = sheet.createRow(line++);
                String[] tokens = input.split(SEPARATER);
                for (int i = 0; i < tokens.length; i++)
                {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(tokens[i]);
                }
            }
            
            wb.write(os);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (wb != null)
            {
                wb.dispose(); // include wb.close() and delete temp file
            }
        }
    }
}
