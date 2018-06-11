package lovely.zzyfgasstation.net;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.lang.ref.WeakReference;

import lovely.zzyfgasstation.R;
import lovely.zzyfgasstation.app.BaseApplication;
import lovely.zzyfgasstation.utils.Constant;

/**
 * Created by skn on 2018/4/13/16:13.
 */

public class NetApiCallback implements Callback.CommonCallback<String> {
    private boolean cancelled = false;

    private WeakReference<Object> pageRef;

    private String method;
    private int code1;

    private ResultCallBack callBack;

    boolean isRemainCallback = false;//次标记用来判定是否在页面结束时取消网络请求，默认(false)取消，为true则不取消

    /**
     * 取消网络接口的构造方法
     */
    public NetApiCallback(Object page, String method, ResultCallBack callBack) {
        pageRef = new WeakReference<Object>(page);
        this.method = method;
        this.callBack = callBack;
        isRemainCallback = false;
    }

    public NetApiCallback(ResultCallBack callBack){
        this.callBack = callBack;
    }

    /**
     * 不取消网络接口的构造方法
     */
    public NetApiCallback(Object page, String method, ResultCallBack callBack, boolean isRemainCallback) {
        pageRef = new WeakReference<Object>(page);
        this.method = method;
        this.callBack = callBack;
        this.isRemainCallback = isRemainCallback;
    }

    @Override
    public void onSuccess(String result) {
        try {
            JSONObject json = new JSONObject(result);
            String code = json.getString("Code");
            if (null!=code && !"".equals(code)){
                code1= Integer.parseInt(code);
                if(code1==0){
                    String data;
                    try {
                        data = json.getString("Data");
                    }catch (Exception e){
                        data = "{}";
                    }
                    callBack.onReceiveData(code1,data,method);
                }else{
                    callBack.onReceiveError(code1, json.getString("Message"),method);
                }
            }else {
                callBack.onReceiveError(code1, json.getString("Message"),method);
            }
        } catch (JSONException e) {
            callBack.onReceiveError(Constant.JSON_FAILED, BaseApplication.getContext().getString(R.string.request_failed),method);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtil.i(Constant.RESPONSE_FAILED+ex.getMessage());
        callBack.onReceiveError(Constant.REQUEST_FAILED, BaseApplication.getContext().getString(R.string.network_timeout),method);
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
