package com.zhoubing.bishe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zhoubing.bishe.R;

/**
 * Created by dell on 2017/7/30.
 */

/**
 *  后续处理步骤：对
 */
public class JibenFragment extends Fragment {
    private EditText editText ; private  Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.jiben,container,false);
        editText = (EditText) view.findViewById(R.id.user);

        bundle = new Bundle();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(editText.getText().toString()!=null){
            Log.e("ceshi一下",editText.getText().toString());
            outState.putString("id",editText.getText().toString());
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        super.onViewStateRestored(bundle);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState!=null){
            String shuju = savedInstanceState.getString("id");
            editText.setText(shuju);
            Log.e("ceshi",savedInstanceState.getString("id"));
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
