package com.yasya.dialogs;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MyTestDialog extends CustomDialog {
    EditText editText;

    public static void show(FragmentManager manager, String title,
                            int layout) {
        // todo title need to be int
        MyTestDialog dialog = new MyTestDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("layout", layout);
        dialog.setArguments(args);
        dialog.show(manager, "frag");
    }

    @Override
    protected Builder build(Builder builder) {
        builder.setTitle(getTitle());
        builder.setView(getLayout());
        builder.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null){
                Toast.makeText(getActivity(), editText.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder;
    }

    @Override
    protected void onDialogCreated(View includedView) {
        Log.e("onDialogCreated", includedView.toString());

        RadioGroup group = (RadioGroup) includedView.findViewById(R.id.radio);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        Toast.makeText(getActivity().getApplicationContext(), "radio1 checked", Toast.LENGTH_SHORT).show();
                        changeLayout(R.layout.changed1, R.id.lyt_changed);
                        editText = (EditText)getChangedLayout().findViewById(R.id.edt_text);
                        break;
                    case R.id.radio2:
                        changeLayout(R.layout.changed2, R.id.lyt_changed);
                        break;
                }
            }
        });


    }
}
