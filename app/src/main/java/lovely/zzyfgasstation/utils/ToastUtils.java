package lovely.zzyfgasstation.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 单列Toast工具类
 */
public class ToastUtils {
    private static Toast sToast;
    public static void showToast(Context context, String msg){
        if(sToast == null){
            //用ApplicationContext防止内存泄露
            sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        }
        //如果这个Toast已经在显示了，那么这里会立即改变Toast的文本
        sToast.setText(msg);
        sToast.show();
    }
}
