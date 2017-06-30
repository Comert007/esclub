package com.ww.android.esclub.fragment;

import android.content.Intent;
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
import com.ww.android.esclub.api.ApiException;
import com.ww.android.esclub.bean.CartClassifyBean;
import com.ww.android.esclub.bean.cart.GoodsBean;
import com.ww.android.esclub.bean.cart.GoodsItem;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.models.CartModel;
import com.ww.android.esclub.vm.views.CartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import ww.com.core.Debug;
import ww.com.core.ScreenUtil;

/**
 * Created by feng on 2017/6/7.
 */

public class CartFragment extends BaseFragment<CartView, CartModel> implements OnItemClickListener,
        CartItemAdapter.OnCartAction, CartShopAdapter.OnShopAction {

    private ClassifyAdapter classifyAdapter; //一级adapter
    private CartItemAdapter cartItemAdapter; //二级adapter
    private CartShopAdapter cartShopAdapter; //商品袋详情adapter

    private ArrayList<GoodsItem> shoppingResult; //已经添加的商品集合

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
                if (shoppingResult.size() == 0) {
                    showToast(getString(R.string.cart_is_empty));
                } else {
                    CommitOrderActivity.start(getActivity(), shoppingResult);
                }

            }
        });

        onGoods();
    }

    private void onGoods() {
        m.onGoods(bindUntilEvent(FragmentEvent.DESTROY), new HttpSubscriber<List<GoodsBean>>
                (getContext(), true) {
            @Override
            public void onNext(List<GoodsBean> goodsBeen) {
                if (goodsBeen == null || goodsBeen.size() == 0) {
                    return;
                }
                List<String> names = new ArrayList<>();
                List<GoodsItem> goodsItems = new ArrayList<>();
                int position = 0;
                for (int i = 0; i < goodsBeen.size(); i++) {
                    GoodsBean goodsBean = goodsBeen.get(i);
                    names.add(goodsBean.getName());
                    //每组开头加上title
                    GoodsItem item = new GoodsItem();
                    item.setCartType(CartItemAdapter.CART_LINE);
                    item.setClassifyName(goodsBean.getName());
                    goodsItems.add(item);
                    //将内容按分类加入List<GoodsItem>
                    List<GoodsItem> perGoods = goodsBean.getGoods();
                    for (GoodsItem perGood : perGoods) {
                        perGood.setPosition(position);
                        perGood.setClassifyId(goodsBean.getId());
                        perGood.setClassifyName(goodsBean.getName());
                        position++;
                    }
                    goodsItems.addAll(perGoods);
                }
                initLeft(names);
                classifyNames.addAll(names);
                cartItemAdapter.addList(goodsItems);
            }
        });
    }


    private void initAdapter() {
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
        cartShopAdapter.setOnShopAction(this);
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
        List<GoodsItem> goods = cartItemAdapter.getList();
        List<Integer> jumpPoints = new ArrayList<>();

        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).getCartType() == CartItemAdapter.CART_LINE) {
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


    @OnClick(R.id.tv_clean_shopping)
    public void onCleanShopping(){
        shoppingResult.clear();
        v.showShopRes(false);
        v.setTip(countSize() + "");
        v.showShoppingContent();
        cartShopAdapter.notifyDataSetChanged();

        List<GoodsItem> goodsItems = cartItemAdapter.getList();
        for (GoodsItem goodsItem : goodsItems) {
            goodsItem.setNum(0);
        }
        cartItemAdapter.notifyDataSetChanged();
    }
    //大于0 说明有相同的存在
    private int checkIsSame(List<GoodsItem> goodsItems, GoodsItem item) {
        int index = -1;
        for (int i = 0; i < goodsItems.size(); i++) {
            GoodsItem goodsItem = goodsItems.get(i);
            if (goodsItem.getPosition() == item.getPosition()) {
                index = i;
            }
        }
        Debug.d("index:"+index);
        return index;
    }

    @Override
    public void onAdd(int position, View view) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        v.playAnimation(loc, (ViewGroup) getActivity().getWindow().getDecorView());

        GoodsItem item = cartItemAdapter.getItem(position);
        int index = checkIsSame(shoppingResult, item);
        if (index>= 0) {
            Debug.d("set---->>>");
            shoppingResult.set(index,item);
        } else {
            Debug.d("add---->>>");
            shoppingResult.add(item);
        }


        v.showShopRes(true);
        v.setTip(countSize() + "");

        cartShopAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinus(int position, View view) {
        GoodsItem item = cartItemAdapter.getItem(position);
        int index = checkIsSame(shoppingResult, item);
        if (index>=0){
            if (0==item.getNum()){
                shoppingResult.remove(index);
            }else
                shoppingResult.set(index,item);
        }else {
            throw new ApiException(getString(R.string.error_of_application));
        }

        if (isListNull()) {
            v.showShopRes(false);
        }

        v.setTip(countSize() + "");

        cartShopAdapter.notifyDataSetChanged();
    }

    //商品袋
    @Override
    public void onShopAdd(int position, View view) {

        GoodsItem item = shoppingResult.get(position);
        int index = checkIsSame(cartItemAdapter.getList(),item);
        if (index>=0){
            cartItemAdapter.notifyItemChanged(index,item);
        }else {
            throw new ApiException(getString(R.string.error_of_application));
        }
        v.setTip(countSize() + "");
        cartItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShopMinus(int position, View view) {
        GoodsItem item = cartShopAdapter.getItem(position);
        if (0==item.getNum()){
            cartShopAdapter.removeItem(item);
            shoppingResult.remove(position);
            cartShopAdapter.notifyDataSetChanged();
        }
        int index = checkIsSame(cartItemAdapter.getList(),item);
        if (index >= 0){
            cartItemAdapter.notifyItemChanged(index,item);
        }else {
            throw new ApiException(getString(R.string.error_of_application));
        }


        if (isListNull()){
            shoppingResult.clear();
            v.showShopRes(false);
            v.setTip(countSize() + "");
            v.showShoppingContent();
            cartShopAdapter.notifyDataSetChanged();
        }else {
            v.setTip(countSize() + "");
        }

        cartItemAdapter.notifyDataSetChanged();
    }

    private boolean isListNull(){
        boolean isNull = true;
        for (GoodsItem goodsItem : shoppingResult) {
            Debug.d(goodsItem.getNum()+"");
            if (0!=goodsItem.getNum()){
                isNull = false;
            }
        }
        Debug.d("---->>>>isnull:"+isNull);
        return isNull;
    }

    //计算购物车数量
    private int countSize(){
        int count=0;
        if (shoppingResult!=null && shoppingResult.size()>0) {
            for (int i = 0; i < shoppingResult.size(); i++) {
                GoodsItem item = shoppingResult.get(i);
                count += item.getNum();
            }
        }
        return count;
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            v.showShopping(newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                int lastItem = v.getItemManager().findLastVisibleItemPosition();
                int count = cartItemAdapter.getItemCount();
                if (lastItem < count) {
                    String classifyName = cartItemAdapter.getItem(lastItem).getClassifyName();
                    for (int i = 0; i < classifyNames.size(); i++) {
                        if (TextUtils.equals(classifyNames.get(i), classifyName)) {
                            Debug.d("leftPosition:" + i);
                            classifyAdapter.recoverySelected(i);
                        }
                    }

                }

            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            shoppingResult.clear();
            cartShopAdapter.notifyDataSetChanged();
        }
    }
}
