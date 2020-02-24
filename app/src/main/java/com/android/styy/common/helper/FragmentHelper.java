package com.android.styy.common.helper;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Arrays;
import java.util.List;

public class FragmentHelper {

    FragmentManager fragmentManager;
    List<Fragment> fragments;
    int containerResId;

    int showPos;

    private FragmentHelper(FragmentManager fragmentManager, @IdRes int containerResId,boolean firstReplace, List<Fragment> fragments){
        this.fragmentManager = fragmentManager;
        this.containerResId = containerResId;
        this.fragments = fragments;
        if(!fragments.isEmpty() && firstReplace){
            replace(0);
        }
    }

    public static FragmentHelper createFragmentNavigator(FragmentManager fragmentManager,@IdRes int containerViewId,boolean firstReplace, Fragment... fragments){
        return new FragmentHelper(fragmentManager,containerViewId,firstReplace, Arrays.asList(fragments));
    }

    public static FragmentHelper createFragmentNavigator(FragmentManager fragmentManager,@IdRes int containerViewId, Fragment... fragments){
        return new FragmentHelper(fragmentManager,containerViewId,true, Arrays.asList(fragments));
    }


    public void replace(int index){
        Fragment fragment = fragments.get(index);
        fragmentManager
                .beginTransaction()
                .replace(containerResId,fragment)
                .commit();
        fragment.onHiddenChanged(false);
    }

    public void onShowFragment(int showPos){

        Fragment showFragment = fragments.get(showPos);

        FragmentTransaction mTransaction = fragmentManager.beginTransaction();

        if(showFragment.isAdded()){
            mTransaction.
                    show(showFragment);
        }else{
            mTransaction
                    .add(containerResId,showFragment);
        }
        mTransaction.commit();
        showFragment.onHiddenChanged(false);
        this.showPos = showPos;
    }

    public void onHideFragment(int hidePos){
        Fragment hideFragment = fragments.get(hidePos);

        FragmentTransaction mTransaction = fragmentManager.beginTransaction();

        if(hideFragment.isAdded()){
            mTransaction.
                    hide(hideFragment);
        }
        mTransaction.commit();
        hideFragment.onHiddenChanged(true);
    }

    public void switchFragment(int showPos,int hidePos){

        onHideFragment(hidePos);
        onShowFragment(showPos);
    }

    public int getShowPos(){
        return showPos;
    }

    public Fragment getShowFragment(){
        return fragments.get(getShowPos());
    }
}
