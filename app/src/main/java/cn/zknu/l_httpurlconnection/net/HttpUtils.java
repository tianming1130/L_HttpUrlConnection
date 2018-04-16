package cn.zknu.l_httpurlconnection.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2018\4\16 0016.
 */

public class HttpUtils {
    public static void requestGet(final CallBack<String> callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConn = null;
                try {
                    String baseUrl = "http://10.0.2.2/get.php?key=get method";
                    URL url = new URL(baseUrl);
                    urlConn = (HttpURLConnection) url.openConnection();

                    urlConn.setRequestMethod("GET");
                    urlConn.setConnectTimeout(5 * 1000);
                    urlConn.setReadTimeout(5 * 1000);
                    urlConn.connect();
                    if (urlConn.getResponseCode() == 200) {
                        InputStream is = urlConn.getInputStream();
                        callBack.onResponse("Get获取数据成功--->" + streamToString(is));
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                }
            }
        }).start();
    }
    public static void downLoadFile(final CallBack<Bitmap> callBackBitmap){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
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
                        callBackBitmap.onResponse(bitmap);
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
                }
            }
        }).start();

    }
    private static String streamToString(InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);
        StringBuilder sBuilder = new StringBuilder();
        try {
            byte[] buffer = new byte[1024];
            int len = -1;

            while ((len = bis.read(buffer)) != -1) {
                sBuilder.append(new String(buffer, 0, len));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
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
