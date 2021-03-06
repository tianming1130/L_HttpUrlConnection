package cn.zknu.l_httpurlconnection;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.zknu.l_httpurlconnection.net.CallBackBitmap;
import cn.zknu.l_httpurlconnection.net.CallBackString;
import cn.zknu.l_httpurlconnection.net.HttpUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STRING=0;
    private static final int BITMAP =1 ;
    private Button btnGet, btnPost, btnDownload;
    private TextView mShowMsg;
    private Handler mHander;
    private ImageView mImageShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void init() {
        btnGet.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        mHander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case STRING:
                        mShowMsg.setText((String)msg.obj);
                        break;
                    case BITMAP:
                        mImageShow.setImageBitmap((Bitmap)msg.obj);
                        break;
                }
            }
        };
    }

    private void initView() {
        btnGet = (Button) findViewById(R.id.btn_get);
        btnPost = (Button) findViewById(R.id.btn_post);
        btnDownload = (Button) findViewById(R.id.btn_download);
        mShowMsg = (TextView) findViewById(R.id.tv_show_msg);
        mImageShow = (ImageView) findViewById(R.id.iv_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                getAndShowData();
                break;
            case R.id.btn_post:
                postAndShowData();
                break;
            case R.id.btn_download:
                downloadImage();
                break;
        }
    }

    private void downloadImage() {

                HttpUtil.downLoadFile(new CallBackBitmap() {
                    @Override
                    public void onResponse(final Bitmap response) {
                        if (response != null) {
                            Message msg=mHander.obtainMessage();
                            msg.obj=response;
                            msg.what=BITMAP;
                            mHander.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });
    }

    private void postAndShowData() {
        HttpUtil.requestPost(new CallBackString() {
            @Override
            public void onResponse(String response) {
                Log.i("TAG", response);
                Message msg = mHander.obtainMessage();
                msg.what = STRING;
                msg.obj=response;
                mHander.sendMessage(msg);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void getAndShowData() {
        HttpUtil.requestGet(new CallBackString() {
            @Override
            public void onResponse(String response) {
                Message msg = mHander.obtainMessage();
                msg.obj=response;
                msg.what = 0;
                mHander.sendMessage(msg);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
