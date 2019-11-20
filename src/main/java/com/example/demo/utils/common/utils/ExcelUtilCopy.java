package com.example.demo.utils.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtilCopy {

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

	public static String ExportIdfa(String url, List<LinkedHashMap> data) {

		String fileName = url+"提现记录("+ DateUtil.dataToStr(DateUtil.addDayByDate(new Date(), -3), "yyyyMMdd")+"-"+ DateUtil.dataToStr(DateUtil.addDayByDate(new Date(), -1), "yyyyMMdd")+").xls";
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

			HSSFCell cell = row.createCell((short) 0);
			cell.setCellValue("用户编号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellValue("用户昵称");
			cell.setCellStyle(style);
			cell = row.createCell((short) 2);
			cell.setCellValue("支付宝名称");
			cell.setCellStyle(style);
			cell = row.createCell((short) 3);
			cell.setCellValue("支付宝帐号");
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellValue("提现金额");
			cell.setCellStyle(style);
			cell = row.createCell((short) 5);
			cell.setCellValue("提现状态");
			cell.setCellStyle(style);
			cell = row.createCell((short) 6);
			cell.setCellValue("提现时间");
			cell.setCellStyle(style);

			for (int i = 0; i < data.size(); i++) {
				row = sheet.createRow((int) i + 1);
				Map map = data.get(i);
				row.createCell((short) 0).setCellValue(map.get("userId").toString());
				cell = row.createCell((short) 1);
				cell.setCellValue(map.get("nickName").toString());
				cell = row.createCell((short) 2);
				cell.setCellValue(map.get("alipay").toString());
				cell = row.createCell((short) 3);
				cell.setCellValue(map.get("alipayNo").toString());
				cell = row.createCell((short) 4);
				cell.setCellValue(map.get("income").toString());
				cell = row.createCell((short) 5);
				cell.setCellValue(map.get("cashStatus").toString());
				cell = row.createCell((short) 6);
				cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
						.format(map.get("createDate")));
			}

			try {

				FileOutputStream fout = new FileOutputStream(fileName);
				wb.write(fout);
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fileName;
	}
}
