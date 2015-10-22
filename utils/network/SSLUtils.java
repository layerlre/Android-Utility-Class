package com.layernet.utils.network;

import android.content.Context;

//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.picasso.OkHttpDownloader;
//import com.squareup.picasso.Picasso;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Layer on 10/22/2015.
 */

public class SSLUtils {

//    private static final OkHttpClient client = new OkHttpClient();
//    private static Picasso mInstance;
//    private static OkHttpDownloader downloader;
    private static SSLContext trustAllSSLContext;
    private static HostnameVerifier hostnameVerifier;

    private SSLUtils(Context context) {
//        client.setHostnameVerifier(getHostnameVerifier());
//        client.setSslSocketFactory(getTrustAllSSLContext().getSocketFactory());
//        mInstance = new Picasso.Builder(context).downloader(getDownloader()).build();
    }

    public static HostnameVerifier getHostnameVerifier() {
        if (hostnameVerifier != null) {
            return hostnameVerifier;
        }
        hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

    public static SSLContext getTrustAllSSLContext() {
        if (trustAllSSLContext != null) {
            return trustAllSSLContext;
        }
        try {
            trustAllSSLContext = SSLContext.getInstance("TLS");
            trustAllSSLContext.init(null, getTrustAllCerts(), new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trustAllSSLContext;
    }

    public static TrustManager[] getTrustAllCerts() {
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        }};
    }

/*    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            new SSLUtils(context);
        }
        return mInstance;
    }

    public static OkHttpDownloader getDownloader() {
        if (downloader == null) {
            downloader = new OkHttpDownloader(client);
        }
        return downloader;
    }*/


}
