package com.example.hbjia.http;

import android.app.Application;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * Created by hbjia on 2014/12/15.
 */
public class MyApplication extends Application {

    private HttpClient httpClient;

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.shutDownHttpClient();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        httpClient = createHttpClient();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.shutDownHttpClient();
    }

    private HttpClient createHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);

        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schReg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager manager = new ThreadSafeClientConnManager(params, schReg);
        return new DefaultHttpClient(manager, params);
    }

    private void shutDownHttpClient() {
        if(httpClient != null && httpClient.getConnectionManager() != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }
}
