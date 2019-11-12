package com.example.networkdemo;

import android.provider.Settings;
import android.text.TextUtils;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    public static  String get(String urlPath){
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            connection.setDoInput(true);

            if("https".equalsIgnoreCase(url.getProtocol())){
                ((HttpsURLConnection)connection).setSSLSocketFactory(HttpsUtil.getSSLSocketFactory());

            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                is.close();
                connection.disconnect();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getParamString(List<NameValuePair> pairs)
            throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (NameValuePair pair : pairs) {
            if (!TextUtils.isEmpty(builder)) {
                builder.append("δ");
            }
            builder.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        return builder.toString();
    }

    public static  String post(String urlPath,List<NameValuePair> params){
        HttpURLConnection connection = null;
        InputStream is = null;
        if (params == null || params.size() == 0){
            return get(urlPath);
        }
        try {
            String body = getParamString(params);
            byte[] data = body.getBytes();
            URL url = new URL(urlPath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if("https".equalsIgnoreCase(url.getProtocol())){
                ((HttpsURLConnection)connection).setSSLSocketFactory(HttpsUtil.getSSLSocketFactory());

            }
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");;
            connection.setRequestProperty("Context-Length",String.valueOf(data.length));
            OutputStream os = connection.getOutputStream();
            os.write(data);
            os.flush();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    response.append(line);
                }
                is.close();
                connection.disconnect();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


