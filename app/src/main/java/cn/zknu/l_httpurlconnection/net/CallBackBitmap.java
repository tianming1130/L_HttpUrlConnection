package cn.zknu.l_httpurlconnection.net;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018\4\16 0016.
 */

public interface CallBackBitmap {
    void onResponse(Bitmap response);
    void onFailed(Exception e);
}
