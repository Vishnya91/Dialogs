package com.yasya.dialogs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Eva on 10.11.13.
 */
public class FragMain extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.main_frag, container, false);


        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Button btn = (Button) view.findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyTestDialog.show(fragmentManager, "test title", R.layout.test_dialog);
            }
        });

        return view;
    }
}
