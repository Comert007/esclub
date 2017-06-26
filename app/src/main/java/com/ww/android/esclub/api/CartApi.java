package com.ww.android.esclub.api;

import com.ww.android.esclub.bean.ResponseBean;
import com.ww.android.esclub.bean.cart.GoodsItem;

import java.util.List;

import rx.Observable;
import ww.com.http.core.AjaxParams;

/**
 * Created by feng on 2017/6/25.
 */

public class CartApi extends BaseApi {

    //商品列表
    public static final Observable<ResponseBean> onGoods(){

        AjaxParams params = new AjaxParams();

        return onPost(getActionUrl("/goods/list"),params);
    }

    public static final Observable<ResponseBean> onPay(String id,List<GoodsItem> goodsItems){

        AjaxParams params = new AjaxParams();
        for (int i = 0; i < goodsItems.size(); i++) {
            GoodsItem goodsItem = goodsItems.get(i);
            int position = goodsItem.getPosition();
            params.addParameters("id",id);
            params.addParameters("goods["+position+"][id]",goodsItem.getId());
            params.addParameters("goods["+position+"][num]",goodsItem.getNum()+"");
            params.addParameters("goods["+position+"][price]",goodsItem.getPrice());
        }

        return onPost(getActionUrl("/order/payForGoods"),params);
    }



}
