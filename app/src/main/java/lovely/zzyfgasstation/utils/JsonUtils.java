package lovely.zzyfgasstation.utils;

import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json解析封装类
 * @author admin
 *
 */
public class JsonUtils {
    public static final String TAG = "JsonUtils";

    public JsonUtils() {
    }


    public static <T> void saveObjectToSharePreferences(T object, String key) {
        if(SpUtil.sp == null) {
            LogUtil.i("please config environment first:ENVConfig.configurationEnvironment(context)");
        } else if(object != null && key != null) {
            Editor editor = SpUtil.sp.edit();
            String objStr = objectToJson(object);
            LogUtil.i("add to sp json:" + objStr);
            editor.putString(key, objStr);
            editor.commit();
        }

    }

    public static <T> void saveListToSharePreferences(List<?> object, String key) {
        if(SpUtil.sp == null) {
            LogUtil.i("please config environment first:ENVConfig.configurationEnvironment(context)");
        } else if(object != null && key != null) {
            Editor editor = SpUtil.sp.edit();
            Gson gson = new Gson();
            String objStr = gson.toJson(object);
            LogUtil.i("add to sp json:" + objStr);
            editor.putString(key, objStr);
            editor.commit();
        }

    }

    public static <T> T getObjectFromSharePreferences(String key, Class<T> cls) {
        if(SpUtil.sp == null) {
            LogUtil.i("please config environment first:ENVConfig.configurationEnvironment(context)");
            return null;
        } else {
            String jsonStr = SpUtil.sp.getString(key, "");
            LogUtil.i("json from sp:" + jsonStr);
            return !jsonStr.equals("") && jsonStr != null?jsonToObject(jsonStr, cls):null;
        }
    }

    public static <T> List<?> getListFromSharePreferences(String key, Class<T> cls) {
        if(SpUtil.sp == null) {
            LogUtil.i("please config environment first:ENVConfig.configurationEnvironment(context)");
            return null;
        } else {
            String jsonStr = SpUtil.sp.getString(key, "");
            LogUtil.i("json from sp:" + jsonStr);
            return !jsonStr.equals("") && jsonStr != null?jsonToList(jsonStr, cls):null;
        }
    }

    public static <T> List<?> getListFromSharePreferences(String key, Type type) {
        if(SpUtil.sp == null) {
            LogUtil.i("please config environment first:ENVConfig.configurationEnvironment(context)");
            return null;
        } else {
            String jsonStr = SpUtil.sp.getString(key, "");
            LogUtil.i("json from sp:" + jsonStr);
            return !jsonStr.equals("") && jsonStr != null?jsonToList(jsonStr, type):null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> obj) {
        return (new Gson()).fromJson(json, obj);
    }

    public static String objectToJson(Object obj) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        return jsonString;
    }

    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        ArrayList list = new ArrayList();
        ArrayList temp = (ArrayList)gson.fromJson(json, ArrayList.class);

        for(int i = 0; i < temp.size(); ++i) {
            Map map = (Map)temp.get(i);
            list.add(jsonToObject((new Gson()).toJson(map), cls));
        }

        return list;
    }

    public static <T> List<T> jsonToList(String json, Type type) {
        List list = (List)(new Gson()).fromJson(json, type);
        return list;
    }
    public static <T> T parse(String data, Class<T> class1) {
        return new Gson().fromJson(data, class1);
    }

    /**
     * json转换成列表
     *
     * @param data
     * @param class1
     * @return
     */
    public static <T> List<T> parseList(String data, Class<T> class1) {
        if (TextUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<T> mList = new ArrayList<T>();
        try {
            JSONArray mArray = new JSONArray(data);
            final int size = mArray.length();
            for (int i = 0; i < size; i++) {
                T t = parse(mArray.getJSONObject(i).toString(), class1);
                mList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }
}
