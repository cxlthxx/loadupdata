package com.example.demo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.Stundent;
import com.example.demo.reactor.NioAcceptor;
import com.example.demo.service.StundectServiceImpl;

@RestController
public class StundentController {
	
	@Autowired
	private StundectServiceImpl serviceImpl;
	private ServletResponse response;
	
	
	@RequestMapping("/findAll")
	public String findAll() throws IOException {
		JSONObject json = new JSONObject();
		List<Stundent> stundent2 = serviceImpl.getStundent();
		json.put("stundent", stundent2);
		return json.toJSONString();
		
	}
	

	/**
	* 导入学员清单
	* @param file
	* @param clientid 
	* @return
	* @throws IOException 
	*/
	@RequestMapping("/importUsers")
	public String importUsers(@RequestParam MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException{
		//解决跨域报错问题
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:1122");
		List<Stundent> list = new ArrayList<Stundent>();
		String size = "";
		XSSFWorkbook workbook =null;
		//创建Excel，读取文件内容 2007版('.xlsx')
		 workbook = new XSSFWorkbook(file.getInputStream());
		 //读取文件2003版 ('.xls')
	//	 workbook = new HSSFWorkbook(file.getInputStream());
	
		//获取所有的工作表的的数量
	    int numOfSheet = workbook.getNumberOfSheets();
	    //遍历这个这些表
	    for (int i = 0; i < numOfSheet; i++) {
	        //获取一个sheet也就是一个工作簿
	        Sheet sheet = workbook.getSheetAt(i);
	        int lastRowNum = sheet.getLastRowNum();
	        //从第一行开始第一行一般是标题
	        for (int j = 1; j <= lastRowNum; j++) {
	            Row row = sheet.getRow(j);
	            Stundent stundent = new Stundent();
	            //获取电话单元格
	            if (row.getCell(0) != null) {
	                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
	                Integer id = Integer.valueOf(row.getCell(0).getStringCellValue());
	                stundent.setId(id);
	            }
	            //密码
	            if (row.getCell(1) != null) {
	                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
	                String name = row.getCell(1).getStringCellValue();
	                stundent.setName(name);;
	            }
	            //姓名
	            if (row.getCell(2) != null) {
	                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
	                Integer age = Integer.valueOf(row.getCell(2).getStringCellValue());
	                stundent.setAge(age);
	            }
	            list.add(stundent);
	        }
	        
	    }
	    List<Stundent> list1 = serviceImpl.findAll(list);
	    size = Integer.toString(list1.size());
	    		return size;
	
		}
	/**
	 * 导出excel数据
	 * @throws IOException 
	 */
	@RequestMapping("/upload")
	public void upload(HttpServletResponse response) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook();
		//创建sheet表单
		XSSFSheet sheet = wb.createSheet("成绩单");
		//创建第一行
		XSSFRow row1 = sheet.createRow(0);
		row1.createCell(0).setCellValue("id");
		row1.createCell(1).setCellValue("name");
		row1.createCell(2).setCellValue("age");
		XSSFRow row2 = null;
		List<Stundent> stundent = serviceImpl.getStundent();
			int i = 1;
			for (Stundent stundent2 : stundent) {
				XSSFRow createRow = sheet.createRow(i);
				createRow.createCell(0).setCellValue(stundent2.getId());
				createRow.createCell(1).setCellValue(stundent2.getName());
				createRow.createCell(2).setCellValue(stundent2.getAge());
				++i;
		}
			
		
		//输出excel文件
		OutputStream output=response.getOutputStream();
		response.setHeader("Content-disposition", "attachment; filename=stundent.xlsx");
		response.setContentType("application/msexcel");        
	    wb.write(output);
	    output.close();
		
	}
	
}
