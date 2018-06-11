package lovely.zzyfgasstation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lovely.zzyfgasstation.R;
import lovely.zzyfgasstation.bean.oilGunList;
import lovely.zzyfgasstation.net.NetHttpClientImpl;
import lovely.zzyfgasstation.net.ResultCallBack;
import lovely.zzyfgasstation.manager.LoginManager;
import lovely.zzyfgasstation.utils.JsonUtils;
import lovely.zzyfgasstation.utils.ToastUtils;
import lovely.zzyfgasstation.viewhelper.RecyclerViewHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public Unbinder binder;
    @BindView(R.id.rv_date)
    RecyclerView mRv;
    @BindView(R.id.tv_title)
    TextView mTv_GasStation;
    @BindView(R.id.tv_date)
    TextView mTv_Date;
    @BindView(R.id.tv_person)
    TextView mTv_Person;
    @BindView(R.id.tv_login_out)
    TextView mTv_Back;
    private static String mYear;
    private static String mMonth;
    private static String mDay;
    private static String mWay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binder = ButterKnife.bind(this);
        mTv_GasStation.setText(TextUtils.isEmpty(LoginManager.newInstance(this).getUserInfo().getStationName())
                ? "" : LoginManager.newInstance(this).getUserInfo().getStationName());
        mTv_Person.setText(TextUtils.isEmpty(LoginManager.newInstance(this).getUserInfo().getUserName())
                ? "" : LoginManager.newInstance(this).getUserInfo().getUserName());
        mTv_Date.setText(StringDate());
        initData();
    }

    private void initData() {
        new RecyclerViewHelper<oilGunList>(mRv, new GridLayoutManager(this, 2), R.layout.item_for_gas_station) {
            @Override
            public void convertView(BaseViewHolder helper, oilGunList item) {
                ((TextView) helper.getView(R.id.tv_num)).setText(item.getOilNo());
                try {
                    ((TextView) helper.getView(R.id.tv_name)).setText(new String(item.getOilType().getBytes(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void loadList() {
//                for (int i = 0; i < 8; i++) {
//                    mOilGunLists.add(new oilGunList(i + "", "测试汽油" + i));
//                }
//                addDatas(mOilGunLists);
                NetHttpClientImpl.getInstance(getBaseContext()).getGunList(new ResultCallBack() {
                    @Override
                    public void onReceiveData(int errorCode, String result, String method) {
                        Log.e(TAG, result);
                        addDatas(JsonUtils.parseList(result, oilGunList.class));
                    }

                    @Override
                    public void onReceiveError(int errorCode, String errorMessage, String method) {
                        ToastUtils.showToast(getBaseContext(), errorMessage);
//                                for (int i = 0; i < 8; i++) {
//                                    mOilGunLists.add(new oilGunList(i + "", "测试汽油" + i));
//                                }
//                                addDatas(mOilGunLists);
                    }
                });
            }

            @Override
            public void onItemClick(BaseViewHolder helper, oilGunList item) {
                super.onItemClick(helper, item);
                startActivity(new Intent(MainActivity.this, ChargeListActivity.class).putExtra("bean", item));
            }
        };
    }

    @OnClick(R.id.tv_login_out)
    public void getOut() {
        new AlertDialog.Builder(this).setTitle("是否注销并退出?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (null != dialog) {
                    dialog.dismiss();
                }
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                logout();
            }
        }).create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logout();
        binder.unbind();
    }

    private void logout() {
        if (null != LoginManager.newInstance(getBaseContext()).getUserInfo()) {
            NetHttpClientImpl.getInstance(getBaseContext()).unLogin(new ResultCallBack() {
                @Override
                public void onReceiveData(int errorCode, String result, String method) {
                    Log.e(TAG, result);
                    finish();
                }

                @Override
                public void onReceiveError(int errorCode, String errorMessage, String method) {
                }
            });
        }
    }


    public static String StringDate() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + "   " + "星期" + mWay;
    }

    public interface Backable {
        boolean goBack();
    }

    Backable mBackable;
    long lastExit;

    @Override
    public void onBackPressed() {
        if (mBackable != null) {
            boolean back = mBackable.goBack();
            if (back) {
                return;
            }
        }
        if (System.currentTimeMillis() - lastExit > 2000) {
            ToastUtils.showToast(getBaseContext(), "再按一次返回键退出程序");
            lastExit = System.currentTimeMillis();
        } else {
            logout();
            super.onBackPressed();
        }
    }
}
