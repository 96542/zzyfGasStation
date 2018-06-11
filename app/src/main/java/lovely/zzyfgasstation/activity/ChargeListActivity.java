package lovely.zzyfgasstation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import lovely.zzyfgasstation.R;
import lovely.zzyfgasstation.bean.ChargeBean;
import lovely.zzyfgasstation.bean.oilGunList;
import lovely.zzyfgasstation.manager.LoginManager;
import lovely.zzyfgasstation.net.NetHttpClientImpl;
import lovely.zzyfgasstation.net.ResultCallBack;
import lovely.zzyfgasstation.utils.JsonUtils;
import lovely.zzyfgasstation.utils.ToastUtils;
import lovely.zzyfgasstation.viewhelper.RecyclerViewHelper;

public class ChargeListActivity extends BaseActivity {
    private static final String TAG = "ChargeListActivity";
    private List<ChargeBean> mListCharge = new ArrayList<>();
    private RecyclerView mRv;
    private oilGunList mOilGunList;
    private String num;

    @Override
    public int getContentViewId() {
        return R.layout.activity_charge_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mRv = findViewById(R.id.rv_base);
        mOilGunList = getIntent().getParcelableExtra("bean");
        if (null != mOilGunList) {
            num = mOilGunList.getOilNo();
            setTitle(TextUtils.isEmpty(num) ? "" : num + "油枪");
        }
        setTitleRightTv("错误订单");
    }

    @Override
    public void loadData(Bundle savedInstanceState) {

        new RecyclerViewHelper<ChargeBean>(mRv, R.layout.item_for_chargelist) {

            @Override
            public void convertView(BaseViewHolder helper, final ChargeBean item) {
                if (isEmpty()) {
                    mRv.setVisibility(View.GONE);
                    findViewById(R.id.tv_no_data).setVisibility(View.VISIBLE);
                }
                helper.itemView.findViewById(R.id.tv_gas_cancel).setVisibility(View.GONE);
                helper.itemView.findViewById(R.id.tv_gas_receivables).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ChargeListActivity.this, OrderReceiptsActivity.class).putExtra("chargeBean", item));
                    }
                });
                ((TextView) helper.getView(R.id.tv_date)).setText(item.getMachTime());
                ((TextView) helper.getView(R.id.tv_gas_type)).setText(item.getOilName());
                ((TextView) helper.getView(R.id.tv_gas_l)).setText(item.getQty() + "");
                ((TextView) helper.getView(R.id.tv_gas_time)).setText(item.getMachTime());
                ((TextView) helper.getView(R.id.tv_gas_person)).setText(LoginManager.newInstance(getBaseContext()).getUserInfo().getUserName());
                ((TextView) helper.getView(R.id.tv_gas_price)).setText(item.getMoney() + "");
//                ((TextView) helper.getView(R.id.tv_gas_discount)).setText(item.getMachTime());
            }

            @Override
            public void loadList() {
//                for (int i = 0; i < 2; i++) {
//                    mListCharge.add(new ChargeBean("9527", 15238426579l, "2018-05-04 10:41:05", 200, "76542", "#92汽油", 9.1f, 32.6f, "01"));
//                }
//                addDatas(mListCharge);
                NetHttpClientImpl.getInstance(getBaseContext()).getNoPayList(num, new ResultCallBack() {
                    @Override
                    public void onReceiveData(int errorCode, String result, String method) {
                        Log.e(TAG, result);
                        addDatas(JsonUtils.parseList(result, ChargeBean.class));
                    }

                    @Override
                    public void onReceiveError(int errorCode, String errorMessage, String method) {
                        ToastUtils.showToast(getBaseContext(), errorMessage);
                    }
                });


            }

            @Override
            public void onItemClick(BaseViewHolder helper, ChargeBean item) {
                super.onItemClick(helper, item);

            }
        };
    }

    @Override
    protected void onRightTvClick() {
        startActivity(new Intent(this, ErrorOrderActivity.class).putExtra("num", num));
    }
}
