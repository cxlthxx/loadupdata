package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

public class HttpClienFile {

	public static String postUploadFile(MultipartFile file, String url) throws IOException {
		String response = "";

		InputStream ins = null;

		try {
			CloseableHttpClient client = HttpClients.createDefault();

			final HttpPost postMethod = new HttpPost(url);

			ins = file.getInputStream();

			HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8"))
					.addBinaryBody("file", ins, ContentType.DEFAULT_TEXT, file.getOriginalFilename()).build();

			postMethod.setEntity(reqEntity);

			CloseableHttpResponse res = client.execute(postMethod);

			response = EntityUtils.toString(res.getEntity(), "UTF-8");

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (ins != null) {

				ins.close();

			}

		}

		return response;
	}
	
	
	public static InputStream postStream(String url) {
		 CloseableHttpClient client = HttpClients.createDefault();
	        HttpPost post = new HttpPost(url);
	        post.setHeader("Content-type",ContentType.APPLICATION_OCTET_STREAM.toString());
	        InputStream inputStream = null;
	        try{
	            CloseableHttpResponse response = client.execute(post);
	            int statusCode = response.getStatusLine().getStatusCode();
	            if(statusCode==200){
	               System.out.println("远程调用成功");
	                HttpEntity responseEntity = response.getEntity();
	                inputStream =  responseEntity.getContent();
	                return  inputStream;
	            }
	        }catch (Exception e){
	            System.out.println("远程调用失败");
	            return  inputStream;
	        }
	        return  inputStream;
	}

}
