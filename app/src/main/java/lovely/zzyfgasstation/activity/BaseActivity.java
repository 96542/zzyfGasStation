package lovely.zzyfgasstation.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import lovely.zzyfgasstation.R;

public abstract class BaseActivity extends AppCompatActivity {
    public TextView titleCenterTv;
    public TextView titleRightTv;
    public TextView leftTv;
    public Unbinder binder;

    public abstract int getContentViewId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void loadData(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        binder = ButterKnife.bind(this);
        leftTv = (TextView) findViewById(R.id.tv_back);
        titleCenterTv = (TextView) findViewById(R.id.tv_title);
        titleRightTv = (TextView) findViewById(R.id.tv_right);
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViews(savedInstanceState);
        loadData(savedInstanceState);
    }

    protected void setTitle(String title) {
        if (titleCenterTv != null) {
            titleCenterTv.setText(title);
        }
    }

    protected void setTitleRightTv(String string) {
        if (titleRightTv != null) {
            titleRightTv.setText(string);
            titleRightTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRightTvClick();
                }
            });
        }
    }

    protected void onRightTvClick() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
    }

    private ProgressDialog mProgressDialog;

    public void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
        }
        if (TextUtils.isEmpty(msg)) {
            msg = "正在加载数据...";
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
    }

    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setMessage("正在加载数据...");
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }

    }

    public void dismissProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showToast(String text) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
        }

    }


    public Activity getActivity() {
        return this;
    }


    public void closeInputMethod(Activity act) {
        View view = getCurrentFocus();
//        if (view != null) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
    }

    /***
     * 打开输入法
     *
     * @param act
     */
    public void openInputMethod(Activity act) {
        View view = act.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) act.getSystemService(INPUT_METHOD_SERVICE))
                    .showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /*权限申请*/
    private int REQUEST_CODE_PERMISSION = 0x00099;

    public void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void showTipsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 999);
    }

    public void permissionSuccess(int requestCode) {

    }

    public void permissionFail(int requestCode) {
    }

    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            requestPermission(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x0002);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

}
