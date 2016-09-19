package org.redcenter.export.test;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.redcenter.export.ExcelExporter;
import org.redcenter.export.IExporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Hello world!
 *
 */
public class App
{
    private static final int MAX_ROW_IN_MEM = 1000;

    public static void main(String[] args)
    {
        try
        {
            exportExcel();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("End");
    }

    private static void exportExcel() throws IOException
    {
//        Workbook wb = new SXSSFWorkbook(MAX_ROW_IN_MEM);
//        Sheet sheet = wb.createSheet();
//
//        Row row = sheet.createRow(0);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("test");
//
//        OutputStream os = new FileOutputStream("test.xlsx");
//        wb.write(os);
//        wb.close();
                
        JpaEntity entity = new JpaEntity();
        entity.setKey("k");
        entity.setName("n");
        entity.setValue("v");
        List<JpaEntity> list = new ArrayList<JpaEntity>();
        list.add(entity);

        File file = new File("test.xlsx");
        IExporter exporter = new ExcelExporter(file );
        try
        {
            exporter.exoprt(list);
            exporter.close();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
