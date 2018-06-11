package lovely.zzyfgasstation.net;

/**
 * Created by 725 on 2016/6/28.
 */
public interface ResultCallBack {
    void onReceiveData(int errorCode, String result, String method);

    void onReceiveError(int errorCode, String errorMessage, String method);
}
