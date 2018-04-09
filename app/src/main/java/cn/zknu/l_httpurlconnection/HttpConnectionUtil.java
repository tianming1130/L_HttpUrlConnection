package cn.zknu.l_httpurlconnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Administrator on 2018\4\9 0009.
 */

public class HttpConnectionUtil {
    private static final String TAG = "HttpConnectionUntil";
    public static String requestGet(@NonNull HashMap<String,String > paramsMap){
        String strRet="";
        HttpURLConnection urlConn=null;
        try {
            String baseUrl="http://10.0.2.2/get.php?";
            StringBuilder sBuilder=new StringBuilder();
            for (String key:paramsMap.keySet()){
                sBuilder.append("&");
                sBuilder.append(String.format("%s=%s",key, URLEncoder.encode(paramsMap.get(key),"utf-8")));
            }
            String tempParams=sBuilder.toString();
            tempParams=tempParams.substring(1);
            String requestUrl=baseUrl+tempParams;

            Log.i(TAG,"GET-URL---->"+requestUrl);
            URL url=new URL(requestUrl);

            urlConn= (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("GET");
            urlConn.setConnectTimeout(5*1000);
            urlConn.setReadTimeout(5*1000);
            urlConn.connect();
            if (urlConn.getResponseCode()==200){
                InputStream is=urlConn.getInputStream();
                strRet="Get获取数据成功--->"+streamToString(is);
            }else {
                strRet="Get获取数据失败";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConn!=null){
                urlConn.disconnect();
            }
            return strRet;
        }
    }
    public static String requestPost(HashMap<String,String> paramsMap){
        String strRet="";
        HttpURLConnection urlConn=null;
        try {
            String baseUrl="http://10.0.2.2/post.php";
            StringBuilder sBuilder=new StringBuilder();
            for (String key:paramsMap.keySet()){
                sBuilder.append("&");
                sBuilder.append(String.format("%s=%s",key, URLEncoder.encode(paramsMap.get(key),"utf-8")));
            }
            String tempParams=sBuilder.toString();
            String postBody=tempParams.substring(1);


            Log.i(TAG,"POST-Body---->"+postBody);
            URL url=new URL(baseUrl);

            urlConn= (HttpURLConnection) url.openConnection();

            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5*1000);
            urlConn.setReadTimeout(5*1000);
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);

            urlConn.connect();

            OutputStream os=urlConn.getOutputStream();
            os.write(postBody.getBytes());
            os.flush();
            os.close();
            if (urlConn.getResponseCode()==200){
                InputStream is=urlConn.getInputStream();
                strRet="Post获取数据成功--->"+streamToString(is);
            }else {
                strRet="Post获取数据失败";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (urlConn!=null){
                urlConn.disconnect();
            }
            return strRet;
        }
    }
    public static Bitmap downLoadFile(){
        Bitmap bitmap=null;
        HttpURLConnection urlConn=null;
        try {
            String baseUrl="http://10.0.2.2/1.jpg";
            URL url=new URL(baseUrl);
            urlConn= (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setConnectTimeout(5*1000);
            urlConn.setReadTimeout(5*1000);
            urlConn.connect();
            if (urlConn.getResponseCode()==200){
                InputStream is=urlConn.getInputStream();
                bitmap= BitmapFactory.decodeStream(is);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConn!=null){
                urlConn.disconnect();
            }
            return bitmap;
        }
    }
    private static String streamToString(InputStream is) {
        BufferedInputStream bis=new BufferedInputStream(is);
        StringBuilder sBuilder=new StringBuilder();
        try {
            byte[] buffer=new byte[1024];
            int len=-1;

            while ((len=bis.read(buffer))!=-1){
                sBuilder.append(new String(buffer,0,len));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sBuilder.toString();
        }
    }
}
