package lovely.zzyfgasstation.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.HttpManager;
import org.xutils.http.RequestParams;
import org.xutils.x;

import lovely.zzyfgasstation.bean.UserInfo;
import lovely.zzyfgasstation.manager.LoginManager;
import lovely.zzyfgasstation.utils.ToastUtils;


/**
 * 网络请求接口实现类
 * Created by admin on 2016/6/17.
 */
public class NetHttpClientImpl implements NetHttpClient {
    private HttpManager httpManager;
    private static NetHttpClientImpl sInstance;
    private static final String TAG = "NetHttpClientImpl";
    private Context context;

    private NetHttpClientImpl(Context context) {
        httpManager = x.http();
        this.context = context;
    }

    /**
     * 单例
     */
    public static NetHttpClientImpl getInstance(Context context) {
        synchronized (NetHttpClientImpl.class) {
            if (sInstance == null) {
                sInstance = new NetHttpClientImpl(context);
            }
        }
        return sInstance;
    }


    /**
     * 拼头信息参数
     */
    private RequestParams splitParams(String url) {
        RequestParams params = new RequestParams(url);
        return params;
    }

    private JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", "id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void post(RequestParams requestParams, NetApiCallback callBack) {
//        requestParams.addHeader("Content-Type", "application/json;charset=utf-8");
        httpManager.post(requestParams, callBack);
    }

    //登录
    public void login(String userid, String password, ResultCallBack callBack) {
//        RequestParams params = new RequestParams(NetHttpClient.LOGIN);
        if (TextUtils.isEmpty(LoginManager.newInstance(context).getUrl())) {
            ToastUtils.showToast(context, "基础地址为空");
//            LoginManager.newInstance(context).setFirst(true);
            return;
        }
        RequestParams params = new RequestParams(LoginManager.newInstance(context).getUrl() + "/login");
        params.addBodyParameter("userid", userid);
        params.addBodyParameter("Pwd", password);
        Log.e(TAG, params + "");
        post(params, new NetApiCallback(callBack));
    }

    //注销
    public void unLogin(ResultCallBack callBack) {
//        RequestParams params = new RequestParams(NetHttpClient.UN_LOGIN);
        RequestParams params = new RequestParams(LoginManager.newInstance(context).getUrl()+"/unlogin");
        UserInfo mUserInfo = LoginManager.newInstance(context).getUserInfo();
        if (null != mUserInfo) {
            params.addBodyParameter("userid", mUserInfo.getUserid());
            params.addBodyParameter("sessionid", mUserInfo.getSessionId());
        }
        Log.e(TAG, params + "");
        post(params, new NetApiCallback(callBack));
    }

    //获取油枪列表
    public void getGunList(ResultCallBack callBack) {
//        RequestParams params = new RequestParams(NetHttpClient.GET_OILGUN);
        RequestParams params = new RequestParams(LoginManager.newInstance(context).getUrl()+"/getgunlist");
        UserInfo mUserInfo = LoginManager.newInstance(context).getUserInfo();
        if (null != mUserInfo) {
            params.addBodyParameter("userid", mUserInfo.getUserid());
            params.addBodyParameter("sessionid", mUserInfo.getSessionId());
        }
        Log.e(TAG, params + "");
        post(params, new NetApiCallback(callBack));
    }

    //获取某个油枪下的未结账列表
    public void getNoPayList(String num, ResultCallBack callBack) {
//        RequestParams params = new RequestParams(NetHttpClient.GET_NO_PAY_LIST);
        RequestParams params = new RequestParams(LoginManager.newInstance(context).getUrl()+"/tradelist");
        UserInfo mUserInfo = LoginManager.newInstance(context).getUserInfo();
        if (null != mUserInfo) {
            params.addBodyParameter("userid", mUserInfo.getUserid());
            params.addBodyParameter("sessionid", mUserInfo.getSessionId());
        }
        params.addBodyParameter("gunNo", num);
        Log.e(TAG, params + "");
        post(params, new NetApiCallback(callBack));
    }

    //获取某个油枪下的未结账列表
    public void getCashPayList(String num, ResultCallBack callBack) {
//        RequestParams params = new RequestParams(NetHttpClient.GET_CASH_LIST);
        RequestParams params = new RequestParams(LoginManager.newInstance(context).getUrl()+"/tradelistCase");
        UserInfo mUserInfo = LoginManager.newInstance(context).getUserInfo();
        if (null != mUserInfo) {
            params.addBodyParameter("userid", mUserInfo.getUserid());
            params.addBodyParameter("sessionid", mUserInfo.getSessionId());
        }
        params.addBodyParameter("gunNo", num);
        Log.e(TAG, params + "");
        post(params, new NetApiCallback(callBack));
    }

}

