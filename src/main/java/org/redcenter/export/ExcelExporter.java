package org.redcenter.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.redcenter.export.api.IExporter;
import org.redcenter.export.filter.ExcelVo;

/**
 * Export excel by POI  
 * Use SXSSFWorkbook to write file by streaming for mass data  
 * @author boneman
 *
 * @param <T>
 */
public class ExcelExporter<T> extends AbstractExporter<T> implements IExporter<T>
{    
    protected static final int MAX_ROW_IN_MEM = 100;
    protected Workbook wb;
 
    public ExcelExporter(File file) throws FileNotFoundException 
    {
        super(file);
        wb = new SXSSFWorkbook(MAX_ROW_IN_MEM);
    }
    
    @Override
    protected void export(List<T> records, boolean includeHeader, ExcelVo vo)
            throws IOException, IllegalArgumentException, IllegalAccessException
    {
        // create sheet 
        String sheetName = vo.getSheetName();
        if (sheetName == null | sheetName.isEmpty())
        {
            Class<?> clazz = records.get(0).getClass();
            sheetName = clazz.getSimpleName();
        }
        Sheet sheet = wb.createSheet(sheetName);

        // create header with bold font
        if (includeHeader)
        {
            createHeader(sheet);
        }

        // create values
        createValue(records, sheet);

        // write to file
        wb.write(os);
    }

    private void createHeader(Sheet sheet)
    {
        // set format 
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        Row row = sheet.createRow(line++);
        int colIndex = 0;
        for (Entry<String, Field> entry : map.entrySet())
        {
            Cell cell = row.createCell(colIndex++);
            cell.setCellValue(entry.getKey());
            cell.setCellStyle(style);
        }
    }

    private void createValue(List<?> records, Sheet sheet) throws IllegalAccessException
    {
        for (Object record : records)
        {
            Row row = sheet.createRow(line++);
            int colIndex = 0;
            for (Entry<String, Field> entry : map.entrySet())
            {
                Cell cell = row.createCell(colIndex++);
                Field field = entry.getValue();
                field.setAccessible(true);
                Object value = field.get(record);
                if (value == null)
                {                    
                    // avoid NullPointerException for value.toString()
                    value = "";
                }
                cell.setCellValue(value.toString());
            }
        }
    }

    public void close() throws IOException
    {
        wb.close();
        super.close();
    }
}
