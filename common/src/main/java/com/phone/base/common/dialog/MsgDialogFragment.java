package com.phone.base.common.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/8/20
 */
public class MsgDialogFragment extends BaseDialogFragment {
    private static final String KEY_PARAMS_TITLE = "params_title";
    private static final String KEY_PARAMS_MSG = "params_msg";
    private static final String KEY_PARAMS_CANCEL = "params_cancel";
    private static final String KEY_PARAMS_OUTSIDE_CANCEL = "params_outside_cancel";

    private String mTitle;
    private String mMsg;
    private boolean mCancelEnable = false;
    private DialogInterface.OnClickListener okClickListener = null;
    private DialogInterface.OnClickListener cancelClickListener = null;
    private boolean isOutSideCancel = true;

    public MsgDialogFragment() {

    }

    public static MsgDialogFragment newInstance(@Nullable String title, @Nullable String msg, boolean cancelEnable, boolean outside) {
        MsgDialogFragment fragment = new MsgDialogFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PARAMS_TITLE, title);
        args.putString(KEY_PARAMS_MSG, msg);
        args.putBoolean(KEY_PARAMS_CANCEL, cancelEnable);
        args.putBoolean(KEY_PARAMS_OUTSIDE_CANCEL, outside);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(KEY_PARAMS_TITLE);
            mMsg = getArguments().getString(KEY_PARAMS_MSG);
            mCancelEnable = getArguments().getBoolean(KEY_PARAMS_CANCEL);
            isOutSideCancel = getArguments().getBoolean(KEY_PARAMS_OUTSIDE_CANCEL);
        }
    }

    public void setOkClickListener(DialogInterface.OnClickListener okClickListener) {
        this.okClickListener = okClickListener;
    }

    public void setCancelClickListener(DialogInterface.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setTitle(mTitle)
                .setMessage(mMsg);
        builder.setCancelable(mCancelEnable);
        if (okClickListener != null) {
            builder.setPositiveButton("Ok", (dialog, which) -> {
                if (okClickListener != null) {
                    okClickListener.onClick(dialog, which);
                }
            });
        }

        if (cancelClickListener != null) {
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                if (cancelClickListener != null) {
                    cancelClickListener.onClick(dialog, which);
                }
            });
        }
        Dialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(isOutSideCancel);
        return dialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelClickListener = null;
        okClickListener = null;
    }

}
