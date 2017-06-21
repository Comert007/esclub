package com.ww.android.esclub.vm.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;
import com.ww.android.esclub.R;
import com.ww.android.esclub.bezier.BizierEvaluator2;
import com.ww.android.esclub.bezier.Point;
import com.ww.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ww.com.core.widget.CustomRecyclerView;

/**
 * Created by feng on 2017/6/19.
 */

public class CartView implements IView {
    @BindView(R.id.iv_shopping)
    ImageView ivShopping;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop; //
    @BindView(R.id.crv_classify)
    CustomRecyclerView crvClassify; //一级分类
    @BindView(R.id.crv_items)
    CustomRecyclerView crvItems; //二级分类
    @BindView(R.id.crv_shop)
    CustomRecyclerView crvShop; //商品袋
    @BindView(R.id.tv_num_tip)
    TextView tvNumTip; //小红点显示
    @BindView(R.id.ll_shopping_content)
    LinearLayout llShoppingContent;  //商品袋
    @BindView(R.id.btn_account)
    Button btnAccount; //去结算

    private boolean visiable;
    private LinearLayoutManager classifyManager;
    private LinearLayoutManager itemManager;

//    private PopupWindow popupWindow;

    private Context context;

    @Override
    public void onAttach(@NonNull Activity activity, @NonNull View view) {
        ButterKnife.bind(this, view);
        context = view.getContext();

        deployCrv();

//        ivShopping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showShoppingContent();
//            }
//        });
    }


    public void showShopRes(boolean hasGoods) {
        if (hasGoods) {
            ivShopping.setImageResource(R.mipmap.ic_shopping_cart_icon);
        } else {
            ivShopping.setImageResource(R.mipmap.ic_shopping_cart_disable_icon);
        }
    }

    public void showShopping(int state) {

        TransitionManager.beginDelayedTransition(rlShop);
        rlShop.setVisibility(state == RecyclerView.SCROLL_STATE_IDLE ? View.VISIBLE : View.GONE);

        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            rlShop.setVisibility(View.VISIBLE);
        } else {
            rlShop.setVisibility(View.GONE);
        }
    }

    //显示商品袋
    public void showShoppingContent(){
        TransitionManager.beginDelayedTransition(llShoppingContent);
        visiable = !visiable;
        llShoppingContent.setVisibility(visiable? View.VISIBLE : View.GONE);
    }

    private void deployCrv() {
        classifyManager = new LinearLayoutManager(context);
        itemManager = new LinearLayoutManager(context);
        LinearLayoutManager shopManager = new LinearLayoutManager(context);

        crvClassify.setLayoutManager(classifyManager);
        crvItems.setLayoutManager(itemManager);
        crvShop.setLayoutManager(shopManager);

        crvClassify.setItemAnimator(new DefaultItemAnimator());
        crvItems.setItemAnimator(new DefaultItemAnimator());
        crvShop.setItemAnimator(new DefaultItemAnimator());
    }


    public ImageView getIvShopping() {
        return ivShopping;
    }

    public CustomRecyclerView getCrvShop() {
        return crvShop;
    }

    public CustomRecyclerView getCrvClassify() {
        return crvClassify;
    }

    public CustomRecyclerView getCrvItems() {
        return crvItems;
    }

    public LinearLayoutManager getClassifyManager() {
        return classifyManager;
    }

    public LinearLayoutManager getItemManager() {
        return itemManager;
    }

    public Button getBtnAccount() {
        return btnAccount;
    }

    //添加进购物车 动画显示
    public void playAnimation(int[] position, final ViewGroup rootView) {
        final ImageView mImg = new ImageView(context);
        mImg.setImageResource(R.mipmap.ic_circle);
        mImg.setScaleType(ImageView.ScaleType.MATRIX);
        rootView.addView(mImg);

        int[] des = new int[2];
        ivShopping.getLocationInWindow(des);

        /*动画开始位置，也就是物品的位置;动画结束的位置，也就是购物车的位置 */
        Point startPosition = new Point(position[0], position[1]);
        Point endPosition = new Point(des[0] + ivShopping.getWidth() / 2, des[1] + ivShopping
                .getHeight() / 2);

        int pointX = (startPosition.x + endPosition.x) / 2 - 100;
        int pointY = startPosition.y - 200;
        Point controllPoint = new Point(pointX, pointY);

        /*
        * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用
        * 这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
        * 一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
        * */
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BizierEvaluator2(controllPoint),
                startPosition, endPosition);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                mImg.setX(point.x);
                mImg.setY(point.y);
            }
        });

        /**
         * 动画结束，移除掉小圆圈
         */
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.removeView(mImg);
            }
        });
    }

    public void setTip(String text_num) {
        int num = Integer.valueOf(text_num);
        if (num > 0) {
            tvNumTip.setVisibility(View.VISIBLE);
        } else {
            tvNumTip.setVisibility(View.GONE);
        }
        tvNumTip.setText(text_num);
    }
//
//    private void createPopup(){
//        if (popupWindow==null){
//            View popView = LayoutInflater.from(context).inflate(R.layout.pop_cart_shopping,null);
//            ScreenUtil.scale(popView);
//            popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            popupWindow.setFocusable(true);
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            popupWindow.setAnimationStyle(R.style.anim_pop_bottom);
//        }
//
//    }
//
//    public void showGoods(){
//        if (popupWindow==null){
//            createPopup();
//        }
//        if (popupWindow.isShowing()){
//            dismissPopup();
//        }else {
//            showPopup();
//        }
//    }
//
//    private void showPopup(){
//        if (popupWindow!=null){
//            popupWindow.showAtLocation(flContent, Gravity.TOP,0,ScreenUtil.getScalePxValue(200));
//        }
//    }
//
//    private void dismissPopup(){
//        if (popupWindow!=null){
//            popupWindow.dismiss();
//        }
//    }


    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }


}
