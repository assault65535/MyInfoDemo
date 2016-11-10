package com.tnecesoc.MyInfoDemo.GlobalModel;

import com.tnecesoc.MyInfoDemo.Bean.Container;
import com.tnecesoc.MyInfoDemo.Utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tnecesoc on 2016/11/5.
 */
public abstract class RemoteModel<Argument> {

    private String url;
    private Map<String, String> parameters;

    public RemoteModel(String url, Map<String, String> parameters) {
        this.url = url;
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void doQuery(HttpUtil.HttpResponseListener listener) {
        HttpUtil.sendPostRequest(getUrl(), parameters, listener);
    }

}
