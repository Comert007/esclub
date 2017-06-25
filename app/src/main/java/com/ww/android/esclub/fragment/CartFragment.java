package com.ww.android.esclub.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.FragmentEvent;
import com.ww.android.esclub.R;
import com.ww.android.esclub.activity.base.rx.HttpSubscriber;
import com.ww.android.esclub.activity.cart.CommitOrderActivity;
import com.ww.android.esclub.adapter.cart.CartItemAdapter;
import com.ww.android.esclub.adapter.cart.CartShopAdapter;
import com.ww.android.esclub.adapter.cart.ClassifyAdapter;
import com.ww.android.esclub.bean.CartClassifyBean;
import com.ww.android.esclub.bean.cart.GoodsBean;
import com.ww.android.esclub.bean.cart.GoodsItem;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.models.CartModel;
import com.ww.android.esclub.vm.views.CartView;

import java.util.ArrayList;
import java.util.List;

import ww.com.core.Debug;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/7.
 */

public class CartFragment extends BaseFragment<CartView, CartModel> implements OnItemClickListener,
        CartItemAdapter.OnCartAction {

    private ClassifyAdapter classifyAdapter; //一级adapter
    private CartItemAdapter cartItemAdapter; //二级adapter
    private CartShopAdapter cartShopAdapter; //商品袋详情adapter

    private List<GoodsItem> shoppingResult; //已经添加的商品集合

    int[] icons = {R.mipmap.ic_hot, R.mipmap.ic_discount, R.mipmap.ic_wine, R.mipmap.ic_fruit};
//    @BindArray(R.array.cart_classify)
//    String[] names;

    private List<String> classifyNames;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init() {
        classifyNames = new ArrayList<>();
        shoppingResult = new ArrayList<>();
        initAdapter();


        initShopContent();

        v.getBtnAccount().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommitOrderActivity.start(getContext());
            }
        });

        onGoods();
    }

    private void onGoods(){
        m.onGoods(bindUntilEvent(FragmentEvent.DESTROY), new HttpSubscriber<List<GoodsBean>>(getContext(),true) {
            @Override
            public void onNext(List<GoodsBean> goodsBeen) {
                if (goodsBeen==null||goodsBeen.size()==0){
                    return;
                }
                List<String> names = new ArrayList<>();
                List<GoodsItem> goodsItems = new ArrayList<>();
                for (int i = 0; i < goodsBeen.size(); i++) {
                    GoodsBean goodsBean = goodsBeen.get(i);
                    names.add(goodsBean.getName());
                    //每组开头加上title
                    GoodsItem item = new GoodsItem();
                    item.setCartType(CartItemAdapter.CART_LINE);
                    item.setClassifyName(goodsBean.getName());
                    //将内容按分类加入List<GoodsItem>
                    List<GoodsItem> perGoods =goodsBean.getGoods();
                    for (GoodsItem perGood : perGoods) {
                        perGood.setClassifyId(goodsBean.getId());
                        perGood.setClassifyName(goodsBean.getName());
                    }
                    goodsItems.addAll(perGoods);
                }
                initLeft(names);
                classifyNames.addAll(names);
                cartItemAdapter.addList(goodsItems);
            }
        });
    }


    private void initAdapter(){
        classifyAdapter = new ClassifyAdapter(getContext());
        classifyAdapter.setOnItemClickListener(this);

        v.getCrvItems().addOnScrollListener(new RecyclerViewListener());
        v.getCrvClassify().setAdapter(classifyAdapter);

        cartItemAdapter = new CartItemAdapter(getContext());
        cartItemAdapter.setOnCartAction(this);
        View footView = LayoutInflater.from(getContext()).inflate(R.layout.layout_cart_empty, null);
        ScreenUtil.scale(footView);
        v.getCrvItems().setAdapter(cartItemAdapter);
        v.getCrvItems().addFooterView(footView);

    }
    //初始化左边列表
    private void initLeft(List<String> names) {
        List<CartClassifyBean> classifyBeen = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            CartClassifyBean classifyBean = new CartClassifyBean();
            if (i == 0) {
                classifyBean.setIcon(icons[i]);
                classifyBean.setSelected(true);
            } else {
                classifyBean.setSelected(false);
            }

            classifyBean.setName(names.get(i));
            classifyBeen.add(classifyBean);
        }

        classifyAdapter.addList(classifyBeen);
    }



    private void initShopContent() {
        cartShopAdapter = new CartShopAdapter(getContext());
        v.getCrvShop().setAdapter(cartShopAdapter);
        v.getIvShopping().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {

                if (shoppingResult != null && shoppingResult.size() > 0) {
                    v.showShoppingContent();
                    cartShopAdapter.addList(shoppingResult);
                }

            }
        });
    }


    @Override
    public void onItemClick(int position, View view) {
         List<GoodsItem> goods =  cartItemAdapter.getList();
        List<Integer> jumpPoints = new ArrayList<>();

        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).getCartType()== CartItemAdapter.CART_LINE){
                jumpPoints.add(i);
            }
        }

        v.getCrvItems().smoothScrollToPosition(jumpPoints.get(position));


//        if (cartItemAdapter.getItemCount() >= position * 10) {
//            v.getCrvItems().smoothScrollToPosition(position * 10);
//            if (position*10!=0){
//                int firstItem =  v.getItemManager().findFirstVisibleItemPosition();
//                int top = v.getCrvItems().getChildAt(position*10 - firstItem).getTop();
//                v.getCrvItems().smoothScrollBy(0,top);
//            }


    }

    @Override
    public void onAdd(int position, View view) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        v.playAnimation(loc, (ViewGroup) getActivity().getWindow().getDecorView());

        shoppingResult.add(cartItemAdapter.getItem(position));
        v.showShopRes(true);
        v.setTip(shoppingResult.size() + "");

    }

    @Override
    public void onMinus(int position, View view) {
        shoppingResult.remove(cartItemAdapter.getItem(position));
        cartShopAdapter.notifyItemChanged(position);
        if (shoppingResult.size() == 0) {
            v.showShopRes(false);
        }

        v.setTip(shoppingResult.size() + "");
    }


    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            v.showShopping(newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                int lastItem = v.getItemManager().findLastVisibleItemPosition();
                int count = cartItemAdapter.getItemCount();
                if (lastItem<count){
                    String classifyName = cartItemAdapter.getItem(lastItem).getClassifyName();
                    for (int i = 0; i < classifyNames.size(); i++) {
                        if (TextUtils.equals(classifyNames.get(i),classifyName)){
                            Debug.d("leftPosition:" + i);
                            classifyAdapter.recoverySelected(i);
                        }
                    }

                }

            }

        }
    }

}
