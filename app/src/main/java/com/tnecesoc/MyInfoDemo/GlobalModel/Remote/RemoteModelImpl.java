package com.tnecesoc.MyInfoDemo.GlobalModel.Remote;

import com.google.gson.reflect.TypeToken;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public abstract class RemoteModelImpl {

    private String url;
    private Map<String, String> parameters;

    public RemoteModelImpl(String url, Map<String, String> parameters) {
        this.url = url;
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected void doRequest(HttpUtil.HttpResponseListener listener) {
        HttpUtil.sendPostRequest(getUrl(), parameters, listener);
    }

    protected <T> T doQueryForObject(TypeToken<T> tClass, HttpUtil.HttpErrorListener listener) {
        return HttpUtil.sendPostRequestForResult(tClass, getUrl(), null, parameters, listener);
    }

}
