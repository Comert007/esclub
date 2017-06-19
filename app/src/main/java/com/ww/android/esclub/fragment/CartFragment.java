package com.ww.android.esclub.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ww.android.esclub.R;
import com.ww.android.esclub.adapter.cart.ClassifyAdapter;
import com.ww.android.esclub.bean.CartClassifyBean;
import com.ww.android.esclub.fragment.base.BaseFragment;
import com.ww.android.esclub.fragment.cart.CartClassifyFragment;
import com.ww.android.esclub.listener.OnItemClickListener;
import com.ww.android.esclub.vm.views.CartView;
import com.ww.mvp.model.VoidModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;

/**
 * Created by feng on 2017/6/7.
 */

public class CartFragment extends BaseFragment<CartView,VoidModel> implements OnItemClickListener{

    private ClassifyAdapter classifyAdapter;

    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    int[] icons ={R.mipmap.ic_hot,R.mipmap.ic_discount,R.mipmap.ic_wine,R.mipmap.ic_fruit};
    @BindArray(R.array.cart_classify)
    String [] names;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void init() {

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        initLeft();

        initRight();
        fragmentTransaction.show(fragments.get(0));
        fragmentTransaction.commit();
    }

    //初始化左边列表
    private void initLeft(){
        List<CartClassifyBean> classifyBeen = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            CartClassifyBean classifyBean = new CartClassifyBean();
            if (i==0){
                classifyBean.setSelected(true);
            }else {
                classifyBean.setSelected(false);
            }
            classifyBean.setIcon(icons[i]);
            classifyBean.setName(names[i]);
            classifyBeen.add(classifyBean);
        }

        classifyAdapter = new ClassifyAdapter(getContext());
        classifyAdapter.setOnItemClickListener(this);
        classifyAdapter.addList(classifyBeen);
        v.getCrvClassify().setAdapter(classifyAdapter);
    }

    private void initRight(){
        fragments = new ArrayList<>();
        fragments.add(new CartClassifyFragment());
        fragments.add(new CartClassifyFragment());
        fragments.add(new CartClassifyFragment());
        fragments.add(new CartClassifyFragment());

        for (Fragment fragment : fragments) {
            fragmentTransaction.add(R.id.fl_content,fragment);
        }
    }

    private void hideFragments(){
        if (fragments!=null&& fragments.size()>0){
            for (int i = 0; i < fragments.size(); i++) {
                fragmentTransaction.hide(fragments.get(i));
            }
        }
    }

    @Override
    public void onItemClick(int position, View v) {
        hideFragments();
        fragmentTransaction.show(fragments.get(position));
    }
}
