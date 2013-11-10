package com.yasya.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public abstract class CustomDialog extends DialogFragment {
    Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.DialogAppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        builder = new Builder(this, getActivity(), inflater, container);
        return build(builder).create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onDialogCreated(builder.getIncludeRoot());
    }

    public void changeLayout(int whatLayout, int whereLayout){
        builder.setNewView(whatLayout, whereLayout);
    }

    protected abstract Builder build(Builder builder);

    protected abstract void onDialogCreated(View includedView);


    @Override
    public void onDestroyView() {
        // bug in the compatibility library
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    public String getTitle() {
        //todo title need to be null
        return getArguments().getString("title");
    }

    public int getLayout() {
        return getArguments().getInt("layout");
    }

    /**
     * Custom dialog builder
     */
    protected static class Builder {

        private final DialogFragment mDialogFragment;
        private final Context mContext;
        private final ViewGroup mContainer;
        private final LayoutInflater mInflater;
        private View root;
        private View includeRoot;
        private CharSequence mTitle = null;
        private CharSequence mPositiveButtonText;
        private View.OnClickListener mPositiveButtonListener;
        private View.OnClickListener mNegativeButtonListener;
        private View mView;
        private int mLayout;
        private int mNewLayout;
        private View changedRoot;
        private Button mPositiveButton;
        private Button mNegativeButton;
        private ListAdapter mListAdapter;
        private int mListCheckedItemIdx;
        private AdapterView.OnItemClickListener mOnItemClickListener;

        public Builder(DialogFragment dialogFragment, Context context,
                       LayoutInflater inflater, ViewGroup container) {
            this.mDialogFragment = dialogFragment;
            this.mContext = context;
            this.mContainer = container;
            this.mInflater = inflater;
        }


        public LayoutInflater getLayoutInflater() {
            return mInflater;
        }

        public Builder setTitle(int titleId) {
            this.mTitle = mContext.getText(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.mTitle = title;
            return this;
        }

        public Builder setPositiveButton(int textId,
                                         final View.OnClickListener listener) {
            mPositiveButtonText = mContext.getText(textId);
            mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(int textId,
                                         final View.OnClickListener listener) {
            mContext.getText(textId);
            mNegativeButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(final View.OnClickListener listener) {
            mPositiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(final View.OnClickListener listener) {
            //  mContext.getText(textId);
            mNegativeButtonListener = listener;
            return this;
        }

        /**
         * Set list
         */
        public Builder setItems(ListAdapter listAdapter, int checkedItemIdx,
                                final AdapterView.OnItemClickListener listener) {
            mListAdapter = listAdapter;
            mOnItemClickListener = listener;
            mListCheckedItemIdx = checkedItemIdx;
            return this;
        }

        public Builder setView(View view) {
            mView = view;
            return this;
        }

        public Builder setView(int layout) {
            mLayout = layout;
           return this;
        }

        public Builder setNewView(int whatLayout, int whereLayout) {
            mNewLayout = whatLayout;
            FrameLayout content = (FrameLayout) getIncludeRoot().findViewById(whereLayout);
            if(mNewLayout != 0){
                changedRoot = mInflater.inflate(mNewLayout, content, false);
                setChangedRoot(changedRoot);
                content.addView(changedRoot);
            }
            return this;
        }

        public View create() {
            View v = getDialogLayoutAndInitTitle();

            FrameLayout content = (FrameLayout) v
                    .findViewById(R.id.lyt_include);

            if (mView != null) {
                content.addView(mView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
            }
            if (mLayout != 0) {
                includeRoot = mInflater.inflate(mLayout, content, false);
                Log.e("includeRoot", includeRoot.toString());
                setIncludeRoot(includeRoot);
                content.addView(includeRoot);
            }
            return v;
        }

        public void setIncludeRoot(View includeRoot) {
            this.includeRoot = includeRoot;
            Log.e("setInclude", includeRoot.toString());
        }

        public View getIncludeRoot() {
            return includeRoot;
        }

        private View getDialogLayoutAndInitTitle() {
            root = mInflater.inflate(R.layout.dialog, mContainer, false);
            mPositiveButton = (Button) root.findViewById(R.id.btn_ok);
            mPositiveButton.setOnClickListener(mPositiveButtonListener);

            mNegativeButton = (Button) root.findViewById(R.id.btn_cancel);
            mNegativeButton.setOnClickListener(mNegativeButtonListener);
            TextView tvTitle = (TextView) root.findViewById(R.id.txt_title);
            View viewTitleDivider = root.findViewById(R.id.divider);
            if (mTitle != null) {
                tvTitle.setText(mTitle);
            } else {
                tvTitle.setVisibility(View.GONE);
                viewTitleDivider.setVisibility(View.GONE);
            }
            return root;
        }

        public View getChangedRoot() {
            return changedRoot;
        }

        public void setChangedRoot(View changedRoot) {
            this.changedRoot = changedRoot;
        }
    }
}
