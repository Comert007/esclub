package com.ww.android.esclub.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.cart.CommitOrderActivity;
import com.ww.android.esclub.adapter.cart.CartItemAdapter;
import com.ww.android.esclub.adapter.cart.CartShopAdapter;
import com.ww.android.esclub.adapter.cart.ClassifyAdapter;
import com.ww.android.esclub.bean.CartClassifyBean;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.views.CartView;
import com.ww.mvp.model.VoidModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindArray;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/7.
 */

public class CartFragment extends BaseFragment<CartView, VoidModel> implements OnItemClickListener,
        CartItemAdapter.OnCartAction {

    private ClassifyAdapter classifyAdapter; //一级adapter
    private CartItemAdapter cartItemAdapter; //二级adapter
    private CartShopAdapter cartShopAdapter; //商品袋详情adapter

    private List<String> shoppingResult; //已经添加的商品集合

    int[] icons = {R.mipmap.ic_hot, R.mipmap.ic_discount, R.mipmap.ic_wine, R.mipmap.ic_fruit};
    @BindArray(R.array.cart_classify)
    String[] names;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init() {
        shoppingResult = new ArrayList<>();
        initLeft();
        initRight();
        initShopContent();

        v.getBtnAccount().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommitOrderActivity.start(getContext());
            }
        });
    }

    //初始化左边列表
    private void initLeft() {
        List<CartClassifyBean> classifyBeen = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            CartClassifyBean classifyBean = new CartClassifyBean();
            if (i == 0) {
                classifyBean.setSelected(true);
            } else {
                classifyBean.setSelected(false);
            }
            classifyBean.setIcon(icons[i]);
            classifyBean.setName(names[i]);
            classifyBeen.add(classifyBean);
        }

        classifyAdapter = new ClassifyAdapter(getContext());
        classifyAdapter.setOnItemClickListener(this);
        classifyAdapter.addList(classifyBeen);
        v.getCrvItems().addOnScrollListener(new RecyclerViewListener());
        v.getCrvClassify().setAdapter(classifyAdapter);
    }

    private void initRight() {
        cartItemAdapter = new CartItemAdapter(getContext());
        cartItemAdapter.setOnCartAction(this);
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_cart_empty,null);
        ScreenUtil.scale(footView);
        v.getCrvItems().setAdapter(cartItemAdapter);
        v.getCrvItems().addFooterView(footView);
        cartItemAdapter.addList(Arrays.asList("1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1",
                "1", "1", "1", "1"));

    }

    private void initShopContent(){
        cartShopAdapter = new CartShopAdapter(getContext());
        v.getCrvShop().setAdapter(cartShopAdapter);
       v.getIvShopping().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View vi) {
               v.showShoppingContent();
               if (shoppingResult!=null&&shoppingResult.size()>0){
                   cartShopAdapter.addList(shoppingResult);
               }

           }
       });
    }


    @Override
    public void onItemClick(int position, View view) {
        if (cartItemAdapter.getItemCount() >= position * 10) {
            v.getCrvItems().smoothScrollToPosition(position * 10);
//            if (position*10!=0){
//                int firstItem =  v.getItemManager().findFirstVisibleItemPosition();
//                int top = v.getCrvItems().getChildAt(position*10 - firstItem).getTop();
//                v.getCrvItems().smoothScrollBy(0,top);
//            }
        }

    }

    @Override
    public void onAdd(int position, View view) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        v.playAnimation(loc, (ViewGroup) getActivity().getWindow().getDecorView());

        shoppingResult.add(cartItemAdapter.getItem(position));
        v.showShopRes(true);
        v.setTip(shoppingResult.size()+"");

    }

    @Override
    public void onMinus(int position, View view) {
        shoppingResult.remove(cartItemAdapter.getItem(position));
        if (shoppingResult.size()==0){
            v.showShopRes(false);
        }

        v.setTip(shoppingResult.size()+"");
    }


    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            v.showShopping(newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                int firstItem = v.getItemManager().findFirstVisibleItemPosition();
                int lastItem = v.getItemManager().findLastVisibleItemPosition();
                int leftPosition = lastItem / 10;
                Debug.d("leftPosition:"+leftPosition);
                classifyAdapter.recoverySelected(leftPosition);
            }

        }
    }

}
