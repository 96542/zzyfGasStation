package lovely.zzyfgasstation.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ums.upos.sdk.exception.SdkException;
import com.ums.upos.sdk.system.BaseSystemManager;
import com.ums.upos.sdk.system.OnServiceStatusListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import lovely.zzyfgasstation.R;
import lovely.zzyfgasstation.bean.UserInfo;
import lovely.zzyfgasstation.manager.LoginManager;
import lovely.zzyfgasstation.net.NetHttpClientImpl;
import lovely.zzyfgasstation.net.ResultCallBack;
import lovely.zzyfgasstation.utils.JsonUtils;
import lovely.zzyfgasstation.utils.RexUtils;
import lovely.zzyfgasstation.utils.ToastUtils;

/**
 * 作者：zdd on 2018/5/24 11:44
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public Unbinder binder;
    @BindView(R.id.et_name)
    EditText mEt_Name;
    @BindView(R.id.et_password)
    EditText mEt_Pwd;

    @BindView(R.id.tv_login)
    TextView mTv_Login;
    private View mPopView;
    private Dialog mPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binder = ButterKnife.bind(this);
        if (null != LoginManager.newInstance(getBaseContext()).getUserInfo()) {
            mEt_Name.setText(TextUtils.isEmpty(LoginManager.newInstance(getBaseContext()).getUserInfo().getUserid()) ? "" : LoginManager.newInstance(getBaseContext()).getUserInfo().getUserid());
            Editable able = mEt_Name.getText();
            int length = able.length();
            Selection.setSelection(able, length);
            mEt_Pwd.setText(TextUtils.isEmpty(LoginManager.newInstance(getBaseContext()).getUserInfo().getPwd()) ? "" : LoginManager.newInstance(getBaseContext()).getUserInfo().getPwd());
        }

        //第一次安装APP 输入基础接口请求地址
        if (LoginManager.newInstance(getBaseContext()).getIsFirst()) {
            initFirst();
        }
    }

    private void initFirst() {
        InitPopWindow(R.layout.pop_item_is_first);
        final EditText mEt_Name = mPopView.findViewById(R.id.et_name);
        mEt_Name.setHint("请输入基础接口请求地址");
        TextView mTv_Cancel = mPopView.findViewById(R.id.tv_cancel);
        TextView mTv_Config = mPopView.findViewById(R.id.tv_config);
        mTv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                    finish();
                }
            }
        });
        mTv_Config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEt_Name.getText().toString().trim())) {
                    ToastUtils.showToast(getBaseContext(), "地址不能为空");
                } else {
//                    if (RexUtils.isIP(mEt_Name.getText().toString().trim())) {
                        Log.e(TAG, mEt_Name.getText().toString().trim());
                        LoginManager.newInstance(LoginActivity.this).setFirst(false);
                        LoginManager.newInstance(LoginActivity.this).setUrl(mEt_Name.getText().toString().trim());
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
//                    }
                }

            }
        });
    }


    @OnClick(R.id.tv_login)
    public void login() {
        if (TextUtils.isEmpty(mEt_Name.getText().toString()) || TextUtils.isEmpty(mEt_Pwd.getText().toString())) {
            Toast.makeText(this, "账号/密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }

        //设备绑定操作
        try {
            BaseSystemManager.getInstance().deviceServiceLogin(LoginActivity.this,
                    null, "99999998", new OnServiceStatusListener() {
                        @Override
                        public void onStatus(int arg0) {
                            if (0 == arg0 || 2 == arg0 || 100 == arg0) {

                            }
                        }
                    });
        } catch (SdkException e) {
            e.printStackTrace();
        }
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        NetHttpClientImpl.getInstance(this).login(mEt_Name.getText().toString(), mEt_Pwd.getText().toString(), new ResultCallBack() {
            @Override
            public void onReceiveData(int errorCode, String result, String method) {
                Log.e(TAG, result);
                UserInfo userInfo = JsonUtils.parse(result, UserInfo.class);
                userInfo.setUserid(mEt_Name.getText().toString());
                userInfo.setPwd(mEt_Pwd.getText().toString());
                LoginManager.newInstance(getBaseContext()).login(userInfo);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onReceiveError(int errorCode, String errorMessage, String method) {
                ToastUtils.showToast(getBaseContext(), errorMessage);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    private void InitPopWindow(int layoutId) {
        // 将布局文件转换成View对象，popupview 内容视图
        mPopView = getLayoutInflater().inflate(layoutId, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setView(mPopView);
        mPopupWindow = builder.create();
        mPopupWindow.show();
        mPopupWindow.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
