package com.one.education.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.education.activities.BaseFragment;
import com.one.education.education.R;

/**
 * @创建者 Administrator
 * @创建时间 2020/4/23 23:10
 * @描述 ${TODO}
 * @更新者 $Author$
 * @更新时间 $Date$
 * @更新描述 ${TODO}
 **/

public class HomeFragment extends BaseFragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.home_fragment, container, false);
        return mView;
    }
}
