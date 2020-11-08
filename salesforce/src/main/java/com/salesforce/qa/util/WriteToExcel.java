package com.salesforce.qa.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.salesforce.qa.base.TestBase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WriteToExcel extends TestBase{
	
	public HSSFCell cell;
	public HSSFSheet sheet;
	public HSSFWorkbook workbook;
	public String timeStamp = new SimpleDateFormat("YYYY_MM_dd_HH_mm_ss").format(new Date());
	
	String filePath = System.getProperty("user.dir")+"\\";
	//String filePath = prop.getProperty("outputpath")+"\\";
			//"C://Users//saif2//Desktop//excel//";
	
	int rowNum = 1;
	
	public String writeToExcel(List<String> header,List<String> tableData,String fileNameToCreate, String sheetName) throws Exception
	{
		String fullFilePath = filePath+fileNameToCreate+"_"+timeStamp+".xls";
		File file = new File(filePath+fileNameToCreate+"_"+timeStamp+".xls");
		boolean fileExist = file.exists();
		System.out.println("File Exist::"+fileExist);
		
		if(fileExist == false)
		{
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
			
			Font headerFont = workbook.createFont();
			((HSSFFont) headerFont).setBold(true);
			headerFont.setFontHeightInPoints((short)12);
			headerFont.setColor(IndexedColors.BLACK.getIndex());
			
			
			//Create the cell style for font
			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			
			//Create Row
			HSSFRow headerRow = sheet.createRow(0);
			
			//Create Cell
			for(int i=0;i<header.size();i++)
			{
				cell = headerRow.createCell(i);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(header.get(i));
				sheet.autoSizeColumn(i);
			}			
		}
		else
		{
			FileInputStream inputStream = new FileInputStream(file);
			workbook = new HSSFWorkbook(inputStream);
			sheet = workbook.getSheet(sheetName);
		}

		List<String> tempTableData = tableData;
		
		String allListDataSeparate[] = new String[tempTableData.size()];
		for(int i=0;i<tempTableData.size();i++)
		{
			allListDataSeparate[i]= tempTableData.get(i);
		}
		
		int totalDataSet =0;
		
		for(int j=0;j<allListDataSeparate.length;j++)
		{
			if(allListDataSeparate[j].equals("$"))
			{
				totalDataSet++;
			}else{
				continue;
			}
		}
		
		System.out.println("Total Rows Table::"+totalDataSet);
		
		//iterating r number of rows
		for (int r = 1; r <= totalDataSet; r++) {
			HSSFRow row = sheet.createRow(r);
			
			// iterating c number of columns
			for (int c = 0; c < header.size()+1; c++) {
				if(!allListDataSeparate[c].equals("$"))
				{
					HSSFCell cell = row.createCell(c);
					cell.setCellValue(allListDataSeparate[c]);
					tempTableData.remove(0);
				}else{
					tempTableData.remove(0);
				}
			}
			allListDataSeparate = tempTableData.toArray(new String[0]);
		}
		
		
		
		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
		
		return fullFilePath;
	}
	
}
