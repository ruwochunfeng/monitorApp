package com.zhoubing.bishe.fragment.jiemian;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhoubing.bishe.R;

/**
 * Created by dell on 2018/2/26.
 */

public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.set,container,false);
        return view;
    }
}
