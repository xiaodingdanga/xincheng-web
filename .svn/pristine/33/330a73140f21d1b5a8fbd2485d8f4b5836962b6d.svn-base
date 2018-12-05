package com.xincheng.common;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExportExcel {
	/**
	 * Excel导出
	 * @param title列名
	 * @param dataList数据
	 * @param sheetname表名
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcel(HSSFWorkbook workbook,String title[],List<Object[]> dataList,String sheetname) throws Exception{
		try {
			// 创建工作表实例
			HSSFSheet sheet = workbook.createSheet(sheetname);
			// 设置标题及列宽度
			this.setTitle(title,sheet,workbook);
			// 给excel填充数据
			this.setData(dataList,sheet,workbook);
		}catch(Exception e){
			e.printStackTrace();
		}
		return workbook;
	}
	/**
	 * Excel导出--隐藏最后一行，在最后一个title的第二列
	 * @param title列名
	 * @param dataList数据
	 * @param sheetname表名
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcelHidden(HSSFWorkbook workbook,String title[],List<Object[]> dataList,String sheetname) throws Exception{
		try {
			// 创建工作表实例
			HSSFSheet sheet = workbook.createSheet(sheetname);
			// 设置标题及列宽度
			this.setTitleHidden(title,sheet,workbook);
			// 给excel填充数据
			this.setData(dataList,sheet,workbook);
		}catch(Exception e){
			e.printStackTrace();
		}
		return workbook;
	}
	/**
	 * 设置表头 --隐藏最后一行，在最后一个title的第二列
	 * @param title
	 * @param sheet
	 * @param workbook
	 */
	public void setTitleHidden(String title[],HSSFSheet sheet,HSSFWorkbook workbook){
		// 获取样式 
        HSSFCellStyle style=this.createTitleStyle(workbook);
		// 创建第一行标题
		HSSFRow row=sheet.createRow(0);//建立标题行
		for(int i=0;i<title.length-1;i++){
			sheet.setColumnWidth(i,4000);//设置列宽
			this.createCell(row,i,style,HSSFCell.CELL_TYPE_STRING,title[i]);
    	}
		//隐藏列
		int lasttitle=title.length-1;
		sheet.setColumnWidth(lasttitle,0);//设置列宽
		this.createCell(row,lasttitle,style,HSSFCell.CELL_TYPE_STRING,title[lasttitle]);
	}
	/**
	 * 设置表头 
	 * @param title
	 * @param sheet
	 * @param workbook
	 */
	public void setTitle(String title[],HSSFSheet sheet,HSSFWorkbook workbook){
		// 获取样式 
        HSSFCellStyle style=this.createTitleStyle(workbook);
		// 创建第一行标题
		HSSFRow row=sheet.createRow(0);//建立标题行
		for(int i=0;i<title.length;i++){
			sheet.setColumnWidth(i,4000);//设置列宽
			this.createCell(row,i,style,HSSFCell.CELL_TYPE_STRING,title[i]);
    	}
	}
	/**
	 * 设置数据
	 * @param dataList
	 * @param sheet
	 * @param workbook
	 */
	public void setData(List<Object[]> dataList,HSSFSheet sheet,HSSFWorkbook workbook){
		 HSSFCellStyle style=this.createDataStyle(workbook); 
		for(int i=0;i<dataList.size();i++){
			// 获取样式 
//	        HSSFCellStyle style=this.createDataStyle(workbook); 
			// 将dataList里面的数据取出来
			String[] data=(String[])dataList.get(i);
			HSSFRow row=sheet.createRow(i+1);//建立新行
			for(int j=0;j<data.length;j++){
				this.createCell(row,j,style,HSSFCell.CELL_TYPE_STRING,data[j]);
			}
		}
	}
    /**
     * 设置excel的title样式   
     * @param wb
     * @return
     */
    private HSSFCellStyle createTitleStyle(HSSFWorkbook wb){
    	HSSFFont boldFont = wb.createFont();   
        boldFont.setFontHeight((short)250);//字体高
        boldFont.setBoldweight((short)250);//字体重
        HSSFCellStyle style = wb.createCellStyle();   
        style.setFont(boldFont);//放入字体样式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中   
        return style;   
    }
    /**
     * 设置excel的data样式
     * @param wb
     * @return
     */
    private HSSFCellStyle createDataStyle(HSSFWorkbook wb){
    	HSSFFont boldFont = wb.createFont();   
        boldFont.setFontHeight((short) 200);   
        HSSFCellStyle style = wb.createCellStyle();   
        style.setFont(boldFont);   
        return style;   
    }
       
    /**
     * 创建Excel单元格   
     * @param row
     * @param column
     * @param style
     * @param cellType
     * @param value
     */
    private void createCell(HSSFRow row,int column,HSSFCellStyle style,int cellType,Object value){   
    	HSSFCell cell=row.createCell(column);   
    	if(style!=null){   
    		cell.setCellStyle(style);   
    	}    
        switch(cellType){   
        	case HSSFCell.CELL_TYPE_BLANK:{};   
        		break;   
        	case HSSFCell.CELL_TYPE_STRING:{cell.setCellValue(value.toString());};   
        		break;   
        	default:   
        		break;   
        }   
    }
    /**
     * 获取单元格值并转换成string
     * @param cell
     * @return
     */
    public static String getCellValue(HSSFCell cell){
		String value = null;
		if(cell!=null){
			switch(cell.getCellType()){
	    		case HSSFCell.CELL_TYPE_NUMERIC:
	    			String str=null;
	    			double doubleValue = cell.getNumericCellValue();
    		     	if(doubleValue-(int)doubleValue<Double.MIN_VALUE){ 
    		     	//是否为int型
    		     		str=Integer.toString((int)doubleValue);
    		     	}else{ 
    		     		//是否为double型
    		     		str=Double.toString(cell.getNumericCellValue());
    		     		DecimalFormat df = new DecimalFormat("######0.00");
    		     		str= df.format(cell.getNumericCellValue());
    		     	}
    		     	value=""+str;
	    		break;
	    		case HSSFCell.CELL_TYPE_STRING:
	    			value=cell.getStringCellValue();
	    		break;
				case HSSFCell.CELL_TYPE_FORMULA:
					try{
						value = String.valueOf(cell.getNumericCellValue());   
					}catch(Exception e){
						value = cell.getStringCellValue();
					}
				case HSSFCell.CELL_TYPE_BLANK:
					value="";
	    		break;
	    	}
		}
		return value;
	}
    /**
     * 生成表格
     * @param title
     * @param startRow 
     * @param endLine
     * @param dataList 数据list
     * @param styleList 样式list
     */
    public static HSSFWorkbook exportExcel(String title,int endLine,LinkedList<Map<String,Object>> dataList,LinkedList<Map<String,Object>> styleList){
    	int startRow = 0;
    	int startLine = 0;
    	HSSFWorkbook book = new HSSFWorkbook();  
    	try{  
    		HSSFSheet sheet = book.createSheet("Sheet1");  
    		
	    	//数据加载
	    	if(null!=dataList){
	    		for(Map<String,Object> dataMap:dataList){
	    			HSSFRow row = sheet.createRow(startRow++);
	    			String cellValue = "";
	    			for(startLine=0;startLine<endLine;startLine++){
	    				if(dataMap.containsKey(String.valueOf(startLine))){
	    					HSSFCell cell = row.createCell(startLine);
	    					cellValue = dataMap.get(String.valueOf(startLine))==null?"":dataMap.get(String.valueOf(startLine)).toString();
		    				cell.setCellValue(cellValue);
	    				}
	    			}
	    		}
	    	}
	    	//样式加载
	    	if(null!=styleList){
	    		startRow = 0;
	    		String cellWidth = "";
	    		String rowHeight = "";
	    		String fontPoint = "";
	    		String fontBold = "";
	    		String fontName ="";
	    		String align = "";
	    		HSSFRow row = null;
	    		HSSFCell cell = null;
	    		for(Map<String,Object> styleMap:styleList){
	    			
	    			row = sheet.getRow(startRow);
	    			if(styleMap.containsKey("cellWidth")){
	    				cellWidth = styleMap.get("cellWidth").toString();
	    			}
	    			if(styleMap.containsKey("rowHeight")){
	    				rowHeight = styleMap.get("rowHeight").toString();
	    				row.setHeight(Short.parseShort(rowHeight));
	    			}
	    			
	    			if(styleMap.containsKey("FontPoint")){
	    				fontPoint = styleMap.get("FontPoint").toString();
	    			}
	    			if(styleMap.containsKey("FontBold")){
	    				fontBold = styleMap.get("FontBold").toString();
	    			}
	    			if(styleMap.containsKey("FontName")){
	    				fontName = styleMap.get("FontName").toString();
	    			}
	    			if(styleMap.containsKey("align")){
	    				align = styleMap.get("align").toString();
	    			}
	    			
	    			for(startLine=0;startLine<endLine;startLine++){
	    				//合并单元格
		    			if(styleMap.containsKey("CellRange"+startLine)){
		    				CellRangeAddress cellRangeAddress = (CellRangeAddress) styleMap.get("CellRange"+startLine);
		    				sheet.addMergedRegion(cellRangeAddress);  
		    			}
//		    			cellStyle.put("cellWidth", "20");
//		    			cellStyle.put("cellHeight", "20");
//		    			cellStyle.put("FontPoint", "20");//字体大小
//		    			cellStyle.put("FontBold", HSSFFont.BOLDWEIGHT_NORMAL);//加粗
		    			//设置单元格样式
		    			if(styleMap.containsKey("FontPoint"+startLine)){
		    				fontPoint = styleMap.get("FontPoint"+startLine).toString();
		    			}
		    			if(styleMap.containsKey("FontBold"+startLine)){
		    				fontBold = styleMap.get("FontBold"+startLine).toString();
		    			}
		    			if(styleMap.containsKey("FontName"+startLine)){
		    				fontName = styleMap.get("FontName"+startLine).toString();
		    			}
		    			if(!cellWidth.equals("")){
		    				sheet.setColumnWidth((short)startLine,Short.parseShort(cellWidth));      
		    			}
		    			HSSFCellStyle cellStyle = book.createCellStyle();  
		    			HSSFFont fontStyle  = book.createFont();
		    			if(!fontPoint.equals("")){
		    				fontStyle.setFontHeightInPoints(Short.parseShort(fontPoint));
//		    				cellStyle.setColor(HSSFColor.RED.index);
		    			}
		    			if(!fontBold.equals("")){
		    				fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    			}
		    			if(!fontName.equals("")){
		    				fontStyle.setFontName(fontName);
		    			}
		    			if(!align.equals("")){
		    				fontStyle.setFontName(fontName);
		    				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中 
		    				cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		    			}
		    			cellStyle.setFont(fontStyle);
		    			cell = row.getCell(startLine);
		    			if(null!=cell){
		    				cell.setCellStyle(cellStyle);
		    			}
//		    			sheet.autoSizeColumn(startLine, true);//自适应列宽度  
	    			}
	    			startRow++;
	    			cellWidth = "";
		    		rowHeight = "";
		    		fontPoint = "";
		    		fontBold = "";
		    		fontName ="";
		    		align = "";
	    		}
	    	} 
       }catch(Exception e){
    		e.printStackTrace();
    	}
    	return book;
    }
    
}
