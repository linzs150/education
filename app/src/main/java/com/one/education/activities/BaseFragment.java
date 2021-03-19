package com.one.education.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.one.education.language.ConstantGlobal;
import com.one.education.language.MultiLanguageUtil;
import com.one.education.language.SpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Call;


/**
 *
 */
public abstract class BaseFragment extends Fragment {

    public static final String FRAGMENT_TAG_KEY = "FRAGMENT_TAG_KEY";

    public static final String FRAGMENT_IS_SECOND = "FRAGMENT_IS_SECOND";

    private static final String FRAGMENT_STACKNAME = "FRAGMENT_STACKNAME";

    public String mLastStackName;

    /**
     * 网络类型
     */
    //    private int netMobile;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            savedInstanceState = null;
        }
        super.onCreate(savedInstanceState);
        //        inspectNet();
        //        initSystemFont();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        changeLanguage();
    }

    private void changeLanguage() {
        String spLanguage = SpUtil.getString(getActivity(), ConstantGlobal.LOCALE_LANGUAGE);
        String spCountry = SpUtil.getString(getActivity(), ConstantGlobal.LOCALE_COUNTRY);

        if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            // 如果有一个不同
            if (!MultiLanguageUtil.isSameWithSetting(getActivity())) {
                Locale locale = new Locale(spLanguage, spCountry);
                MultiLanguageUtil.changeAppLanguage(getActivity(), locale, false);
            }
        }
    }

    private void initSystemFont() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }


    //    /**
    //     * 初始化时判断有没有网络
    //     */
    //
    //    public boolean inspectNet() {
    //        this.netMobile = NetUtils.getNetConnectState(this.getActivity());
    //        return isNetConnect();
    //
    //        // if (netMobile == 1) {
    //        // System.out.println("inspectNet：连接wifi");
    //        // } else if (netMobile == 0) {
    //        // System.out.println("inspectNet:连接移动数据");
    //        // } else if (netMobile == -1) {
    //        // System.out.println("inspectNet:当前没有网络");
    //        //
    //        // }
    //    }
    //
    //    /**
    //     * 网络变化之后的类型
    //     */
    //    @Override
    //    public void onNetChange(int netMobile) {
    //        // TODO Auto-generated method stub
    //        this.netMobile = netMobile;
    //        isNetConnect();
    //    }
    //
    //    /**
    //     * 判断有无网络 。
    //     *
    //     * @return true 有网, false 没有网络.
    //     */
    //    public boolean isNetConnect() {
    //        if (netMobile == 1) {
    //            return true;
    //        } else if (netMobile == 0) {
    //            return true;
    //        } else if (netMobile == -1) {
    //            return false;
    //
    //        }
    //        return false;
    //    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public boolean onBackPressed() {
        return false;
    }

    protected ArrayList<Call> mCallArray = new ArrayList<>();

    public void showFragment(BaseFragment fragment) {
        showFragment(fragment, 0, 0);
    }

    public void showFragment(BaseFragment fragment, int animaIn, int animaOut) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(animaIn, 0, 0, animaOut);
//        transaction.add(R.id.container, fragment, fragment.getClass().getName());
        fragment.mLastStackName = "" + System.currentTimeMillis() + hashCode();
        transaction.addToBackStack(fragment.mLastStackName);
        transaction.commitAllowingStateLoss();
    }

    private View.OnClickListener createErrorPageClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onErrorClick(v);
            }
        };
    }

    protected void onErrorClick(View view) {
    }

    public void startActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        startActivity(intent);
        //        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void startFragment(Context context) {

        FragmentActivity fa = scanForFragmentActivity(context);
        if (fa != null) {
            startFragment(fa);
        }
    }

    private static AppCompatActivity scanForFragmentActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof AppCompatActivity)
            return (AppCompatActivity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForFragmentActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    public void startFragment(Context context, int animaIn, int animaOut) {
        if (context instanceof AppCompatActivity) {
            startFragment((AppCompatActivity) context, animaIn, animaOut);
        }
    }

    public void startFragment(Context context, int layoutId) {
        if (context instanceof AppCompatActivity) {
            startFragment((AppCompatActivity) context, layoutId, null, null);
        }
    }

    public void startFragment(FragmentActivity activity) {
        startFragment(activity, null);
    }

    public void startFragment(FragmentActivity activity, int animaIn, int animaOut) {
//        startFragment(activity, R.id.container, null, null, animaIn, animaOut);
    }

    public void startFragment(FragmentActivity activity, String tag) {
        startFragment(activity, null, tag);
    }

    public void startFragment(FragmentActivity activity, String stackName, String tag) {
//        startFragment(activity, R.id.container, stackName, tag);
        //        startFragment(activity, R.id.fl_show, stackName, tag);
    }

    public void startFragment(FragmentActivity activity, int layoutId, String stackName,
                              String tag) {
        startFragment(activity, layoutId, stackName, tag, 0, 0);
    }

    public void startFragment(FragmentActivity activity, int layoutId, String stackName,
                              String tag, int animaIn, int animaOut) {
        if (!TextUtils.isEmpty(mLastStackName)) {
            this.finishFragment(mLastStackName);
            mLastStackName = null;
        }
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (stackName == null) {
            stackName = "" + System.currentTimeMillis() + hashCode();
        }
        bundle.putString(FRAGMENT_STACKNAME, stackName);
        mLastStackName = stackName;
        if (tag == null) {
            tag = stackName;
        }
        bundle.putString(FRAGMENT_TAG_KEY, tag);
        bundle.putBoolean(FRAGMENT_IS_SECOND, true);
        if (activity != null) {
            setArguments(bundle);
            FragmentTransaction transaction = activity.getSupportFragmentManager()
                    .beginTransaction();
            transaction.addToBackStack(mLastStackName);
            transaction.add(layoutId, this, tag);
            transaction.setCustomAnimations(animaIn, 0, 0, animaOut);
            transaction.addToBackStack(stackName);
            transaction.commitAllowingStateLoss();
            activity.getSupportFragmentManager().executePendingTransactions();
        }
    }


    public void startFragment(FragmentManager fragmentManager, int layoutId) {
        if (!TextUtils.isEmpty(mLastStackName)) {
            this.finishFragment(mLastStackName);
            mLastStackName = null;
        }
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        String stackName = "" + System.currentTimeMillis() + hashCode();
        bundle.putString(FRAGMENT_STACKNAME, stackName);
        mLastStackName = stackName;
        String tag = stackName;
        bundle.putString(FRAGMENT_TAG_KEY, tag);
        bundle.putBoolean(FRAGMENT_IS_SECOND, true);
        if (fragmentManager != null) {
            setArguments(bundle);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(mLastStackName);
            transaction.add(layoutId, this, tag);
            transaction.addToBackStack(stackName);
            transaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    public void finishFragment() {
        finishFragment(mLastStackName);
    }

    public void finishFragment(String name) {

        getFragmentManager().popBackStackImmediate(name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void finishAboveFragment() {
        getFragmentManager().popBackStackImmediate(mLastStackName, 0);
    }

    public void finishAllFragment() {
        getFragmentManager().popBackStackImmediate(null, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void addJob(Call call) {
        mCallArray.add(call);
    }

    protected void clearAllJob() {
        for (Call call : mCallArray) {
            if (call != null) {
                call.cancel();
            }
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        clearAllJob();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
