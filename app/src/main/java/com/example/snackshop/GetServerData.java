package com.example.snackshop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class GetServerData {
    public GetServerData(){
    }
    public byte[] readParse(String sort){
        String url="http://192.168.137.1:8080/SnackShop/GetsortServlet";
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        int len=0;
        byte[] data=new byte[1024*1024*10];
        try{
            URL byteurl=new URL(url);
            HttpURLConnection conn=(HttpURLConnection) byteurl.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(100000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //输出请求
            String outdata="sort="+sort;
            OutputStream outputStream=conn.getOutputStream();
            outputStream.write(outdata.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream in=conn.getInputStream();
            while ((len=in.read(data))!=-1){
                outStream.write(data,0,len);
            }
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }
}
