package cn.zknu.l_httpurlconnection.net;

/**
 * Created by Administrator on 2018/4/13.
 */

public interface CallBack {
     void onResponse(String response);
     void onFailed(String response);
}
