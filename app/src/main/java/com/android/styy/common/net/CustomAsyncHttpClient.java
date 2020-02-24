package com.android.styy.common.net;

import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.client.params.ClientPNames;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultRedirectHandler;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class CustomAsyncHttpClient extends AsyncHttpClient {

    @Override
    public void setEnableRedirects(final boolean enableRedirects) {

        ((DefaultHttpClient) getHttpClient()).setRedirectHandler(new DefaultRedirectHandler() {
            @Override
            public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 301 || statusCode == 302) {
                    return enableRedirects;
                }
                return false;
            }
        });
        getHttpClient().getParams().setParameter(ClientPNames.MAX_REDIRECTS, 3);
        getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
    }
}
