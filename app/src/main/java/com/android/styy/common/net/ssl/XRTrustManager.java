package com.android.styy.common.net.ssl;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class XRTrustManager  implements X509TrustManager {
    private X509TrustManager defaultTrustManager;
    private X509TrustManager localTrustManager;

    public XRTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {

        TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        var4.init((KeyStore) null);
        defaultTrustManager = SslContextFactory.chooseTrustManager(var4.getTrustManagers());
        this.localTrustManager = localTrustManager;
    }
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try{
            defaultTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException ce){
            localTrustManager.checkServerTrusted(chain, authType);
        }
    }
    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return new X509Certificate[0];
    }
}