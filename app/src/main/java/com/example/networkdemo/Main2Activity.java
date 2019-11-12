package com.example.networkdemo;

import android.net.Network;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.example.networkdemo.NetworkUtils.get;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvResult;
    private Button btn;
    private Button btn1;
    private ScrollView scrollView;
    private ImageView imageView;
    private static final String IP_BASE_URL="http://ip.taobao.com/service/getIpInfo.php";
    private static final String IP_URL = IP_BASE_URL+"?ip=112.2.253.137";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        tvResult = findViewById(R.id.tvResult);
        btn=findViewById(R.id.btn);
        btn1=findViewById(R.id.btn1);
        scrollView=findViewById(R.id.scro);
        imageView=findViewById(R.id.imageView);


        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        scrollView.setOnClickListener(this);
        imageView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result = NetworkUtils.get(IP_URL);
                        if (result != null) {
                            Log.d("MainActivity", result);
                            tvResult.post(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText(result);
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText("数据为null");
                                }
                            });
                        }
                    }
                }).start();
                break;

            case R.id.btn1:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("ip","112.2.253.208"));
                        final String result = NetworkUtils.post(IP_BASE_URL,params);
                        if (result != null) {
                            Log.d("MainActivity", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText(result);
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText("请求失败 ，未获得数据");
                                }
                            });
                        }
                    }
                }).start();
                break;


        }
    }

}
