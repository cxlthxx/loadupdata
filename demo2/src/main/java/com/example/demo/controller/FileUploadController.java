package com.example.demo.controller;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.nio.charset.Charset;


 

import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.ContentType;

import org.apache.http.entity.mime.MultipartEntityBuilder;


import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;



@RestController

@RequestMapping(value="/files")

public class FileUploadController 

{

 

 

    /**

     * 单个文件上传

     * @param file

     * @param redirectAttributes

     * @return

     * @throws IOException 

     */

    @SuppressWarnings("unchecked")
    
    @RequestMapping(value="/uploadFile",method=RequestMethod.POST,produces="application/json")
    public String singleFileUpload(@RequestParam(value="file") MultipartFile file) throws IOException{

        String response = ""; 

        InputStream ins = null;

        try

        {

            CloseableHttpClient client = HttpClients.createDefault(); 

            final HttpPost postMethod = new HttpPost("http://localhost:8080/importUsers");

            //注释掉的部分会在项目生成一个临时文件 在最后需要删除

            /* File f = null;          

            if(file.equals("")||file.getSize()<=0)

            {           

                file = null;       

            }else{ 

            //把 MultipartFile  类型转成file类型

                InputStream ins = file.getInputStream();         

                f=new File(file.getOriginalFilename());         

                inputStreamToFile(ins, f);       

            }

            FileBody bin = new FileBody(f);

            HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).addPart("file", bin).build();

            */

             ins = file.getInputStream();  

          

            HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8")).addBinaryBody("file", ins,ContentType.DEFAULT_TEXT,file.getOriginalFilename()).build();

            

            postMethod.setEntity(reqEntity);

            CloseableHttpResponse res = client.execute(postMethod);

//返回的结果有乱码 还未解决！！！！！

            response = EntityUtils.toString(res.getEntity(),"UTF-8"); 

             //删除文件

           // File del = new File(f.toURI());

            //del.delete();

        }

        catch (Exception e)

        {

            e.printStackTrace();

        }finally {

            if(ins!=null){

                ins.close(); 

            }

        }

        return response;

    }

 

 

    public static void inputStreamToFile(InputStream ins,File file) {

        try {

            OutputStream os = new FileOutputStream(file);

            int bytesRead = 0;

            byte[] buffer = new byte[8192];

            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {

                os.write(buffer, 0, bytesRead);

            }

          

            os.close();

            ins.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
