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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGet,btnPost,btnDownload;
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

        mHander=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mShowMsg.setText(msg.getData().getString("data"));
            }
        };
    }

    private void initView() {
        btnGet=(Button)findViewById(R.id.btn_get);
        btnPost=(Button)findViewById(R.id.btn_post);
        btnDownload=(Button)findViewById(R.id.btn_download);
        mShowMsg =(TextView)findViewById(R.id.tv_show_msg);
        mImageShow=(ImageView)findViewById(R.id.iv_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap=HttpConnectionUtil.downLoadFile();
                if (bitmap!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageShow.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        }).start();
    }

    private void postAndShowData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<>();
                map.put("key", "post");
                String str = HttpConnectionUtil.requestPost(map);
                Log.i("TAG", str);
                Message msg = mHander.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("data", str);
                msg.setData(bundle);
                msg.what = 0;
                mHander.sendMessage(msg);
            }
        }).start();
    }

    private void getAndShowData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> map=new HashMap<>();
                map.put("key","get");
                String str=HttpConnectionUtil.requestGet(map);
                Log.i("TAG",str);
                Message msg=mHander.obtainMessage();
                Bundle bundle=new Bundle();
                bundle.putString("data",str);
                msg.setData(bundle);
                msg.what=0;
                mHander.sendMessage(msg);
            }
        }).start();
    }
}
