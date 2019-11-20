package com.example.demo.utils.common.utils;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExcelUtil {
	/**
	 * 导出Excel
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param values 内容
	 * @param wb HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		if(wb == null){
			wb = new HSSFWorkbook();
		}

		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);

		//设置默认宽度、高度值
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(20);

		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(0);

		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		// 背景色
		style.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.SEA_GREEN.index);

		// 生成一个字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.BLACK.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");

		// 把字体 应用到当前样式
		style.setFont(font);

		//声明列对象
		HSSFCell cell = null;

		//创建标题
		for(int i=0;i<title.length;i++){
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		HSSFCellStyle styleC = wb.createCellStyle();
		styleC.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		//创建内容
		for(int i=0;i<values.length;i++){
			row = sheet.createRow(i + 1);
			for(int j=0;j<values[i].length;j++){
				//将内容按顺序赋给对应的列对象
				HSSFCell c = row.createCell(j);
				c.setCellValue(values[i][j]);
				c.setCellStyle(styleC);
			}
		}
		return wb;
	}
	@Autowired
	static HttpServletRequest request;
	// 判断excel版本
	static Workbook openWorkbook(InputStream in, String fileFileName) throws IOException {

		Workbook wb = null;
		if (fileFileName.endsWith(".xlsx")) {
			wb = new XSSFWorkbook(in);// Excel 2007
		} else {
			wb = (Workbook) new HSSFWorkbook(in);// Excel 2003
		}
		return wb;
	}

	@SuppressWarnings("deprecation")
	public static List<String[]> getExcelData(String fileFileName) throws Exception {

		InputStream in = new FileInputStream(fileFileName); // 创建输入流
		Workbook wb = openWorkbook(in, fileFileName);// 获取Excel文件对象
		Sheet sheet = wb.getSheetAt(1);// 获取文件的指定工作表m 默认的第一个
		List<String[]> list = new ArrayList<String[]>();
		Row row = null;
		Cell cell = null;
		int totalRows = sheet.getPhysicalNumberOfRows(); // 总行数
		int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();// 总列数
		for (int i = 1; i < totalRows; i++) {

			// 创建一个数组 用来存储每一列的值
			String[] str = new String[totalCells];
			row = sheet.getRow(i);
			for (int j = 0; j < totalCells; j++) {
				cell = (Cell) sheet.getCellComment(j, i);
				cell = row.getCell(j);
				System.out.println(j + "DDDDDDDDDD");
				if (j == 0) {
					Date theDate = cell.getDateCellValue();
					str[j] = DateUtil.dataToStr(theDate, "yyyy-MM-dd");

				} else {

					str[j] = row.getCell(j).toString();// cell.getRow().getCell(j).toString();
				}

			}
			// 把刚获取的列存入list
			list.add(str);
		}
		for (int r = 0; r < totalRows; r++) {
			row = sheet.getRow(r);
			System.out.print("第" + r + "行");
			for (int c = 0; c < totalCells; c++) {
				cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						// 时间格式
						// if(HSSFDateUtil.isCellDateFormatted(cell)){
						// Date dd = cell.getDateCellValue();
						// DateFormat df = new
						// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						// cellValue = df.format(dd);
						// }
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;
					default:
						cellValue = "未知类型";
						break;
					}
					System.out.print("   " + cellValue + "\t");

				}
			}
			System.out.println();
		}
		// 返回值集合
		return list;
	}

	public static String ExportIdfa(String url, List<LinkedHashMap> data, String[] cellHeads) {
		String fileName = url+"提现记录("+DateUtil.dataToStr(DateUtil.addDayByDate(new Date(), -3), "yyyyMMdd")+"-"+DateUtil.dataToStr(DateUtil.addDayByDate(new Date(), -1), "yyyyMMdd")+").xls";
		if (data != null && data.size() > 0) {
			// 第一步，创建一个webbook，对应一个Excel文件
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("最近三天的提现记录");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCell cell = null;
			for (int i = 0; i < cellHeads.length; i++){
				cell = row.createCell((short) i);
				cell.setCellValue(cellHeads[i]);
				cell.setCellStyle(style);
			}

			for (int i = 0; i < data.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Map map = data.get(i);
				Set set = map.keySet();
				int index = 0;
				for (Iterator iterator = set.iterator(); iterator.hasNext();){
					String key = (String) iterator.next();
					cell = row.createCell((short) index);
					cell.setCellValue(map.get(key).toString());
					index++;
				}
			}
			try {
				File file = new File(fileName);
				if (file.exists()){
					file.deleteOnExit();
				}
				FileOutputStream fout = new FileOutputStream(fileName);
				wb.write(fout);
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}

	public static String ExportExcel(List<LinkedHashMap> data, String[] cellHeads, HttpServletResponse response) throws ParseException {
		String fileName = "e://提现记录("+DateUtil.dataToStr(DateUtil.addDayByDate(new Date(), -3), "yyyyMMdd")+"-"+DateUtil.dataToStr(DateUtil.addDayByDate(new Date(), -1), "yyyyMMdd")+").xls";
		if (data != null && data.size() > 0) {
			// 第一步，创建一个webbook，对应一个Excel文件
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("最近三天的提现记录");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			HSSFCell cell = null;
			for (int i = 0; i < cellHeads.length; i++){
				cell = row.createCell((short) i);
				cell.setCellValue(cellHeads[i]);
				cell.setCellStyle(style);
			}

			for (int i = 0; i < data.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Map map = data.get(i);
				if (!map.containsKey("cashAmountBalance")){
					map.put("cashAmountBalance", "0.00");
				}
				if (!map.containsKey("returnAmountBalance")){
					map.put("returnAmountBalance", "0.00");
				}
				Set set = map.keySet();
				int index = 0;
				for (Iterator iterator = set.iterator(); iterator.hasNext();){
					String key = (String) iterator.next();
					cell = row.createCell((short) index);
					cell.setCellValue(map.get(key).toString());
					if ("payment_time".equals(key)){
						cell.setCellValue(DateUtil.dataToStr(DateUtil.stampToDate(map.get(key).toString()), "yyyy-MM-dd"));
					}
					if ("balanceTime".equals(key)){
						if ("2".equals(map.get("trade_type").toString())){
							cell.setCellValue(DateUtil.dataToStr(DateUtil.addDayByDate(DateUtil.stampToDate(map.get(key).toString()), 1), "yyyy-MM-dd"));
						}else {
							cell.setCellValue(DateUtil.dataToStr(DateUtil.stampToDate(map.get(key).toString()), "yyyy-MM-dd"));
						}

					}
					if ("advertiser_type".equals(key)){
						if ("0".equals(map.get(key).toString())){
							cell.setCellValue("直客");
						}else if ("1".equals(map.get(key).toString())){
							cell.setCellValue("代理商");
						}else {
							cell.setCellValue("广告主");
						}
					}
					if ("totalIncome".equals(key)){
						if ("0".equals(map.get("trade_type").toString()) || "1".equals(map.get("trade_type").toString())){
							cell.setCellValue(map.get(key).toString());
						}else {
							cell.setCellValue("-");
						}
					}
					if ("cashIncome".equals(key)){
						if ("0".equals(map.get("trade_type").toString()) || "1".equals(map.get("trade_type").toString())){
							cell.setCellValue(map.get(key).toString());
						}else {
							cell.setCellValue("-");
						}
					}
					if ("returnIncome".equals(key)){
						if ("0".equals(map.get("trade_type").toString()) || "1".equals(map.get("trade_type").toString())){
							cell.setCellValue(map.get(key).toString());
						}else {
							cell.setCellValue("-");
						}
					}
					if ("totalPay".equals(key)){
						if ("0".equals(map.get("trade_type").toString()) || "1".equals(map.get("trade_type").toString())){
							cell.setCellValue("-");
						}else {
							cell.setCellValue(map.get(key).toString());
						}
					}
					if ("cashPay".equals(key)){
						if ("0".equals(map.get("trade_type").toString()) || "1".equals(map.get("trade_type").toString())){
							cell.setCellValue("-");
						}else {
							cell.setCellValue(map.get(key).toString());
						}
					}
					if ("returnPay".equals(key)){
						if ("0".equals(map.get("trade_type").toString()) || "1".equals(map.get("trade_type").toString())){
							cell.setCellValue("-");
						}else {
							cell.setCellValue(map.get(key).toString());
						}
					}
					if ("trade_type".equals(key)){
						if ("0".equals(map.get(key).toString())){
							cell.setCellValue("充值");
						}else if ("1".equals(map.get(key).toString())){
							cell.setCellValue("资金划拨");
						}else if ("2".equals(map.get(key).toString())){
							cell.setCellValue("广告消费");
						}else if ("3".equals(map.get(key).toString())){
							cell.setCellValue("资金回收");
						}else {
							cell.setCellValue("退款");
						}
					}

					index++;
				}
			}
			try {
				File file = new File(fileName);
				if (file.exists()){
					file.deleteOnExit();
				}
				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", "attachment;filename=totalFinance_" + DateUtil.dataToStr(new Date(), "yyyyMMdd") + ".xls");//默认Excel名称
				response.flushBuffer();
				wb.write(response.getOutputStream());
				wb.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
}
