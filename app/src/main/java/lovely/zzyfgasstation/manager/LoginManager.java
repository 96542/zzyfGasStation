package lovely.zzyfgasstation.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import lovely.zzyfgasstation.bean.UserInfo;
import lovely.zzyfgasstation.utils.JsonUtils;

/**
 * Created by skn on 2018/3/22/16:41.
 * 登录以后 在本地缓存的登录信息;
 */

public class LoginManager extends BaseManager {
    private static final String TAG = "LoginManager";
    private static LoginManager newInstance;
    private UserInfo userInfo;
    private String BaseUrl;
    private boolean isFirst;

    public static LoginManager newInstance(Context context) {
        synchronized (LoginManager.class) {
            if (newInstance == null) {
                newInstance = new LoginManager(context.getApplicationContext());
            }
        }

        return newInstance;
    }

    private LoginManager(Context c) {
        super(c);
        userInfo = JsonUtils.parse(sp.getString("user_info", ""), UserInfo.class);
        BaseUrl = sp.getString("url", "");
        isFirst = sp.getBoolean("isFirst", true);
    }

    public void login(UserInfo baseUser) {
        userInfo = baseUser;
        Log.e(TAG, new Gson().toJson(userInfo));
        sp.edit().putString("user_info", new Gson().toJson(userInfo)).apply();
    }

    public void setFirst(boolean isTrue) {
        sp.edit().putBoolean("isFirst", isTrue).commit();
    }

    public boolean getIsFirst() {
        boolean isFirst = sp.getBoolean("isFirst", true);
        Log.e(TAG,isFirst+"56565");
        return sp.getBoolean("isFirst", true);
    }

    public void setUrl(String url) {
        sp.edit().putString("url", url).commit();
    }

    public String getUrl() {
        return "".equals(BaseUrl) ? sp.getString("url", "") : BaseUrl;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void logout() {
        UserInfo userInfo = new UserInfo();
        sp.edit().putString("user_info", new Gson().toJson(userInfo)).apply();
    }

}
