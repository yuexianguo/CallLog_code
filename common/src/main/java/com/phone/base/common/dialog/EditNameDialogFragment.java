package com.phone.base.common.dialog;

import static android.view.MotionEvent.ACTION_UP;
import static android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.phone.base.common.R;
import com.phone.base.common.listener.OnSingleClickListener;
import com.phone.base.common.view.ClearEditView;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;

import static com.phone.base.common.ConstantsKt.NAMED_MAX_LENGTH;

/**
 * description ：
 * author : Andy.Guo
 * email : Andy.Guo@waclighting.com.cn
 * date : 2020/12/25
 */
public class EditNameDialogFragment extends BaseDialogFragment {
    private static final String KEY_PARAMS_TITLE = "params_title";
    private static final String KEY_PARAMS_MSG = "params_msg";
    private static final String KEY_PARAMS_PRE_NAME = "params_pre_name";

    private String mTitle;
    private String mMsg;
    private String mPreName;
    private MyOkClickListener okClickListener = null;
    private DialogInterface.OnClickListener cancelClickListener = null;
    private ClearEditView mEdit_name;
    private ImageView mIv_dismiss;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public EditNameDialogFragment() {

    }

    public static EditNameDialogFragment newInstance(@Nullable String title, @Nullable String msg, @Nullable String preName) {
        EditNameDialogFragment fragment = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PARAMS_TITLE, title);
        args.putString(KEY_PARAMS_MSG, msg);
        args.putString(KEY_PARAMS_PRE_NAME, preName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(KEY_PARAMS_TITLE);
            mMsg = getArguments().getString(KEY_PARAMS_MSG);
            mPreName = getArguments().getString(KEY_PARAMS_PRE_NAME);
        }
    }

    public void setOkClickListener(MyOkClickListener okClickListener) {
        this.okClickListener = okClickListener;
    }

    public void setCancelClickListener(DialogInterface.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(mTitle)
                .setMessage(mMsg);
        builder.setCancelable(true);
        builder.setView(R.layout.common_layout_dialog_edit_name);

        if (okClickListener != null) {
            SoftReference<MyOkClickListener> softReference = new SoftReference<>(okClickListener);
            builder.setPositiveButton("Ok", (dialog, which) -> {
                MyOkClickListener myOkClickListener = softReference.get();
                if (myOkClickListener != null) {
                    if (TextUtils.isEmpty(mEdit_name.getText().toString().trim())) {
                        toastMsg("Could not enter a empty name.");
                        return;
                    }
                    if (mEdit_name.getText().toString().trim().length() > NAMED_MAX_LENGTH) {
                        toastMsg(getString(R.string.common_toast_name_long_limit));
                        return;
                    }
                    myOkClickListener.clickOk(dialog, mEdit_name.getText().toString().trim());
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
        mEdit_name = (ClearEditView) dialog.findViewById(R.id.dialog_edit_name);
        if (!TextUtils.isEmpty(mPreName)) {
            mEdit_name.setText(mPreName);
            mEdit_name.setSelection(mPreName.length());
            mEdit_name.requestFocus();
        }
        mEdit_name.setOnDrawableRightListener(new ClearEditView.OnDrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                mEdit_name.setText("");
            }
        });

        mHandler.postDelayed(runnable,300);

        return dialog;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showSoftInputFromWindow();
        }
    };

    private void showSoftInputFromWindow() {
        if (mEdit_name != null) {
            mEdit_name.setFocusable(true);
            mEdit_name.setFocusableInTouchMode(true);
            mEdit_name.requestFocus();
            InputMethodManager inputManager =
                    (InputMethodManager) mEdit_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(mEdit_name, HIDE_IMPLICIT_ONLY);
        }
    }

    public interface MyOkClickListener {
        void clickOk(DialogInterface dialog, String name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEdit_name != null) {
            InputMethodManager inputManager =
                    (InputMethodManager) mEdit_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        mHandler.removeCallbacks(runnable);
        okClickListener = null;
        cancelClickListener = null;
    }
}
