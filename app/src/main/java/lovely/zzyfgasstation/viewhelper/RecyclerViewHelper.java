package lovely.zzyfgasstation.viewhelper;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import lovely.zzyfgasstation.R;
import lovely.zzyfgasstation.widget.RecyleViewDiver;
import lovely.zzyfgasstation.widget.WrapContentLinearLayoutManager;


/**
 * Created by skn on 2017/9/27/10:17.
 */

public abstract class RecyclerViewHelper<T> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private static final String TAG = "ViewHelper";

    public abstract void convertView(BaseViewHolder helper, T item);

    public abstract void loadList();

    protected View rootView;
    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout refreshLayout;
    protected BaseQuickAdapter adapter;
    protected int itemId;
    public int pageNo = 1;
    public int pageSize = 10;
    protected boolean shouldLoadMore = true;
    protected View emptyView;
    protected View headView;
    protected View loadingView;
    protected Context context;

    public RecyclerViewHelper(View rootView, int itemId) {
        this.rootView = rootView;
        this.itemId = itemId;
        initView();
    }

    public RecyclerViewHelper(View rootView, RecyclerView.LayoutManager layoutManager, int itemId) {
        this.rootView = rootView;
        this.layoutManager = layoutManager;
        this.itemId = itemId;
        initView();
    }

    public RecyclerViewHelper(View rootView, RecyclerView.LayoutManager layoutManager, int itemId, boolean shouldLoadMore) {
        this.rootView = rootView;
        this.layoutManager = layoutManager;
        this.itemId = itemId;
        this.shouldLoadMore = shouldLoadMore;
        initView();
    }

    public RecyclerViewHelper(View rootView, RecyclerView.LayoutManager layoutManager, int itemId, View headView) {
        this.rootView = rootView;
        this.layoutManager = layoutManager;
        this.itemId = itemId;
        this.headView = headView;
        initView();
    }

    private void initView() {
        context = rootView.getContext();
        if (rootView instanceof RecyclerView) {
            recyclerView = (RecyclerView) rootView;
        } else {
            recyclerView = rootView.findViewById(R.id.recycler_view);
        }
        if (layoutManager == null) {
            //修改后的  圈子刷新崩溃查询的方案
            layoutManager = new WrapContentLinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
            //原来的
//                        layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        }
        if (layoutManager instanceof LinearLayoutManager) {
            if (layoutManager instanceof GridLayoutManager) {
            } else {
                if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                    recyclerView.addItemDecoration(new RecyleViewDiver(context, LinearLayoutManager.VERTICAL));
                }
            }
        }
        refreshLayout = rootView.findViewById(R.id.refresh_layout);
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
        }
        beforeLoad();
        getAdapter();
        stepAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        loadList();
    }

    protected void beforeLoad() {
    }

    private void getAdapter() {
        adapter = new BaseQuickAdapter<T, BaseViewHolder>(itemId, new ArrayList<T>()) {
            @Override
            protected void convert(final BaseViewHolder helper, final T item) {
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClick(helper, item);
                    }
                });
                convertView(helper, item);
            }
        };
    }

    private void stepAdapter() {
        if (shouldLoadMore) {
            adapter.setEnableLoadMore(true);
            adapter.setOnLoadMoreListener(this);
        }
        if (emptyView != null) {
            adapter.setEmptyView(emptyView);
            adapter.setHeaderAndEmpty(true);
        }
        if (headView != null) {
            adapter.addHeaderView(headView);
        }
    }


    public boolean isEmpty(){
        return  adapter.getItemCount()==0;
    }
    public void addDatas(List<T> list) {
        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
//            refreshLayout.setRefreshing(false);
        }
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (list == null) {
            adapter.setEnableLoadMore(false);
            adapter.notifyDataSetChanged();
            adapter.loadMoreComplete();
            return;
        }
        if (pageNo == 1) {
            adapter.setNewData(list);
            if(shouldLoadMore){
                adapter.setEnableLoadMore(list.size()==pageSize);
            }
        } else {
            adapter.addData(list);
        }
        adapter.loadMoreComplete();
        if (list.size() < pageSize && shouldLoadMore) {
            adapter.loadMoreEnd();
        }
    }

    public void deleteItem(int position) {
        adapter.remove(position);
    }

    public void addItem(T t, int position) {
        adapter.add(position, t);
    }

    public void scrollToTop() {
        if (adapter.getHeaderLayoutCount() > 0) {
            layoutManager.scrollToPosition(adapter.getData().size());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layoutManager.scrollToPosition(adapter.getFooterLayoutCount() + adapter.getHeaderLayoutCount());
                }
            }, 200);
        } else {
            layoutManager.scrollToPosition(0);
        }
    }

    public void scrollTop(final int position) {
        layoutManager.scrollToPosition(adapter.getData().size() - 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutManager.scrollToPosition(position);
            }
        }, 200);
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        loadList();
    }


    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        loadList();
    }

    public void loadFail() {
        pageNo--;
        adapter.loadMoreFail();
    }

    public void loadComplete() {
        adapter.loadMoreComplete();
        adapter.setEnableLoadMore(false);
        Toast.makeText(rootView.getContext(), "没有更多了....", Toast.LENGTH_SHORT).show();
    }

    public void onItemClick(BaseViewHolder helper, T item) {

    }

    public void addHeadView(View headView) {
        adapter.addHeaderView(headView);
    }

//    // 测试数据 泛型为string 时候用
//    public void setTestData(final int i) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<String> list = new ArrayList<>();
//                for (int i1 = 0; i1 < i; i1++) {
//                    list.add("");
//                }
//                addDatas((List<T>) list);
//            }
//        }, 200);
//    }
}
