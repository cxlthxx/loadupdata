package com.example.demo.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String importUsers(@RequestParam(value="file") MultipartFile file) throws IOException {
		
		String url = "http://localhost:8080/importUsers";
		String result = HttpClienFile.postUploadFile(file, url);
		return result;
		
	}
	
	@RequestMapping("/upload")
	public void upload(HttpServletResponse response) throws IOException {
		String url = "http://localhost:8080/upload";
		 BufferedInputStream buf = null;
	        OutputStream out = null;
	        try{
	            InputStream in = HttpClienFile.postStream(url);//body为获取的pdf
	            response.setContentType("application/msexcel"); 
	            response.setHeader("Content-disposition", "attachment; filename=stundent.xlsx");
	            out = response.getOutputStream();
	            buf = new BufferedInputStream(in);
	            int readBytes = 0;
	            while ((readBytes = buf.read()) != -1) {
	                out.write(readBytes);
	            }
	        } catch (Exception e) {
	            System.out.println("文件获取异常");
	        }finally {
	            if (out != null) {
	                out.close();
	            }
	            if (buf != null) {
	                buf.close();
	            }
	        }
		 
	}
	
	

}
