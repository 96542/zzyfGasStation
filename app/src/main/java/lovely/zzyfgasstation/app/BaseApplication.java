package lovely.zzyfgasstation.app;

import android.app.Application;
import android.content.Context;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class BaseApplication extends Application {

    public static final String TAG = "Application";
    public static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();

    }

    private void initConfig() {
        mContext = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
    }


    public static Context getContext() {
        return mContext;
    }


}
