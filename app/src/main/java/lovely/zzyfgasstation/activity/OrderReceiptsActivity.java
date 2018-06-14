package lovely.zzyfgasstation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ums.AppHelper;
import com.ums.anypay.service.IOnTransEndListener;
import com.ums.upos.sdk.exception.CallServiceException;
import com.ums.upos.sdk.exception.SdkException;
import com.ums.upos.sdk.printer.BoldEnum;
import com.ums.upos.sdk.printer.FontConfig;
import com.ums.upos.sdk.printer.FontSizeEnum;
import com.ums.upos.sdk.printer.OnPrintResultListener;
import com.ums.upos.sdk.printer.PrinterManager;
import com.ums.upos.sdk.scanner.OnScanListener;
import com.ums.upos.sdk.scanner.ScannerConfig;
import com.ums.upos.sdk.scanner.ScannerManager;
import com.ums.upos.sdk.system.BaseSystemManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import lovely.zzyfgasstation.R;
import lovely.zzyfgasstation.bean.ChargeBean;
import lovely.zzyfgasstation.manager.LoginManager;


public class OrderReceiptsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "OrderReceiptsActivity";
    final static public int WIDTH = 380;

    final static public int LINE_WIDTH = 400;
    /**
     * 设置前置/后置摄像头
     */
    private int scanner_type = 1;
    private ScannerManager scannerManager;
    private Bundle bundle;
    @BindView(R.id.tv_date)
    TextView mTv_Date;
    @BindView(R.id.tv_gas_price)
    TextView mTv_Price;
    @BindView(R.id.tv_gas_discount)
    TextView mTv_Discount;
    @BindView(R.id.tv_gas_position)
    TextView mTv_Position;
    @BindView(R.id.tv_gas_time)
    TextView mTv_Time;
    @BindView(R.id.tv_gas_type)
    TextView mTv_Type;
    @BindView(R.id.tv_gas_count)
    TextView mTv_Count;
    @BindView(R.id.tv_gas_person)
    TextView mTv_Person;
    @BindView(R.id.tv_gas_host)
    TextView mTv_Host;
    @BindView(R.id.tv_gas_order_num)
    TextView mTv_Num;
    private ChargeBean mBean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_order_receipts;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setTitle("订单收款");
        mBean = getIntent().getParcelableExtra("chargeBean");
        if (null != mBean) {
            String[] split = mBean.getMachTime().split(" ");
            mTv_Date.setText(TextUtils.isEmpty(split[0]) ? "" : split[0]);
            mTv_Price.setText(mBean.getMoney() + "");
            mTv_Discount.setText("");
            mTv_Position.setText(mBean.getTerminal());
            mTv_Time.setText(mBean.getMachTime());
            mTv_Type.setText(mBean.getOilName());
            mTv_Count.setText(mBean.getQty() + "升");
            mTv_Person.setText(TextUtils.isEmpty(LoginManager.newInstance(getBaseContext()).getUserInfo().getUserName()) ? "" : LoginManager.newInstance(getBaseContext()).getUserInfo().getUserName());
            mTv_Host.setText(TextUtils.isEmpty(LoginManager.newInstance(getBaseContext()).getUserInfo().getStationName()) ? "" : LoginManager.newInstance(getBaseContext()).getUserInfo().getStationName());
            mTv_Num.setText(mBean.getFlowNo() + "");
        }
        scannerManager = new ScannerManager();
        bundle = new Bundle();
        findViewById(R.id.ll_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openScan("微信付款");
//                payType("微信");
                payType("POS通");
            }
        });
        findViewById(R.id.ll_alipay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openScan("支付宝付款");
//                payType("支付宝");
                payType("POS通");
            }
        });
        findViewById(R.id.ll_card).setOnClickListener(this);
    }

    private void payType(final String msg) {
        //参数  amt 金额  isNeedPrintReceipt 是否打印  tradeType 支付方式(例如:扫码) all/usescan
        String str = "{" +
                "    \"amt\": \"" + mTv_Price.getText().toString() + "\"," +
                "    \"isNeedPrintReceipt\": false," +
                "    \"tradeType\": \"all\"" +
                "}";
        try {
            JSONObject json = new JSONObject(str);
            AppHelper.callTrans(OrderReceiptsActivity.this, "POS 通", msg, json, new IOnTransEndListener() {
                @Override
                public void onEnd(String s) {
                    Log.e(TAG, msg + s);
                    //打印
//                    AppHelper.callPrint(OrderReceiptsActivity.this,"");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.ll_cash)
    public void printExample() {
        printerExam();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_card:
                try {
                    JSONObject json = new JSONObject("{\"amt\":\"1\"}");
                    AppHelper.callTrans(this, "银行卡收款", "消费", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void print() {
        String printHtmlstr = "          2018-05-04\n" +
                "金额：                     ￥200元\n" +
                "----------------------------------\n" +
                "优惠：                         0元\n" +
                "机位：                    一号机位\n" +
                "加油时间：     2018-05-04 10:41:02\n" +
                "油品：                   #92号汽油\n" +
                "升数：                      32.6升\n" +
                "操作员：                      小刘\n" +
                "油站：              大乔石化北环店\n" +
                "订单号：       2342454758756756756";
        try {
            PrinterManager printer = new PrinterManager();
            printer.initPrinter();
            FontConfig fontConfig = new FontConfig();
            fontConfig.setBold(BoldEnum.NOT_BOLD);//不加粗
            fontConfig.setSize(FontSizeEnum.SMALL);//小号字体
            printer.setPrnText(printHtmlstr, fontConfig);
            printer.startPrint(new OnPrintResultListener() {

                @Override
                public void onPrintResult(int arg0) {//arg0可见ServiceResult.java
                    //登出，以免占用U架构服务
                    try {
                        BaseSystemManager.getInstance().deviceServiceLogout();
                    } catch (SdkException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openScan(String title) {
        scannerManager = new ScannerManager();
        bundle = new Bundle();
        bundle.putInt(ScannerConfig.COMM_SCANNER_TYPE, scanner_type);
        bundle.putBoolean(ScannerConfig.COMM_ISCONTINUOUS_SCAN, false);
        try {
            scannerManager.stopScan();
            scannerManager.initScanner(bundle);
            scannerManager.startScan(10000, new OnScanListener() {
                @Override
                public void onScanResult(int i, byte[] bytes) {
                    //防止用户未扫描直接返回，导致bytes为空
                    if (bytes != null && !bytes.equals("")) {
                        showToast(new String(bytes));
//                                tv_context.setText(new String(bytes));
                    }
                }
            });

        } catch (SdkException e) {
            e.printStackTrace();
        } catch (CallServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (-1 == resultCode) {
            Log.d("LOG", data.getStringExtra("resultCode") + "");//具体的code见ServiceResult
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    //打印
    void printerExam() {
        try {

            int height = 600;
            // 打印高度计算：每多一行加30
            Bitmap bitmap = Bitmap.createBitmap(WIDTH, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setTypeface(Typeface.SANS_SERIF);
            String content = "";
            // 打印交易时间
            content = mTv_Date.getText().toString();
            paint.setTextSize(35);
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_CENTER, 0, 0, content);
            //内容开始
            paint.setTextSize(24);
            float nextTransY = 50;
            content = "金额:  " + "￥" + mTv_Price.getText().toString() + "元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, nextTransY, content);

            // 分割线
            content = "------------------------------------------------\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 优惠
            content = "优惠:  " + mTv_Discount.getText().toString() + "元" + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 机位
            content = "机位:  " + mTv_Position.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 时间
            content = "加油时间:  " + mTv_Time.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 油品
            content = "油品:  " + mTv_Type.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 升数
            content = "升数:  " + mTv_Count.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 操作员
            content = "操作员:  " + mTv_Person.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 油站
            content = "油站:  " + mTv_Host.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 空白
            content = "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            // 订单号
            content = "订单号:  " + mTv_Num.getText().toString() + "\r\n";
            canvas = printSingleLine(canvas, paint, Layout.Alignment.ALIGN_NORMAL, 0, 30, content);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            String path = Environment.getExternalStorageDirectory() + "/image.png";
            FileOutputStream os = new FileOutputStream(new File(path));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            AppHelper.callPrint(OrderReceiptsActivity.this, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印一行
     *
     * @param canvas
     * @param paint
     * @param alignment
     * @param transX
     * @param transY
     * @param content
     * @return
     */
    private static Canvas printSingleLine(Canvas canvas, TextPaint paint, Layout.Alignment alignment, float transX, float transY, String content) {
        StaticLayout layout = new StaticLayout(content, paint, LINE_WIDTH, alignment, 1F, 0, false);
        canvas.translate(transX, transY);
        layout.draw(canvas);
        return canvas;
    }
}
