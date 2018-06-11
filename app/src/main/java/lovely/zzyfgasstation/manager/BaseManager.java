package lovely.zzyfgasstation.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by skn on 2018/3/22/16:50.
 */

public class BaseManager {
    protected Context context;
    protected SharedPreferences sp;
    protected BaseManager(Context context){
        this.context = context;
        sp = context.getSharedPreferences("sp_name",Context.MODE_PRIVATE);
    }
    protected BaseManager(){

    }


}
