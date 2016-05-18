package com.anenn.demo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.anenn.passcode.OnPassCodeFillListener;
import com.anenn.passcode.PassCodeView;

/**
 * Copyright Youdar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * <p>
 * Created by anenn <anennzxq@gmail.com> on 5/18/16.
 */
public class SimplePassCodeActivity extends AppCompatActivity {

    private PassCodeView mPassCodeView;
    private String mPassCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_simple_pass_code);

        initView();
    }

    private void initView() {
        mPassCodeView = (PassCodeView) findViewById(R.id.passCodeView);
        mPassCodeView.setOnPassCodeFillListener(new OnPassCodeFillListener() {
            @Override
            public void passCodeFill(String passCode) {
                if (mPassCode == null) {
                    mPassCode = passCode;
                } else if (mPassCode.equals(passCode)) {
                    mPassCode = null;
                    Toast.makeText(SimplePassCodeActivity.this, "Passcode is matched, welcome!", Toast.LENGTH_LONG).show();
                } else {
                    mPassCode = null;
                    Toast.makeText(SimplePassCodeActivity.this, "Passcode is not matched, please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mPassCodeView != null) {
            mPassCodeView.release();
        }
        super.onDestroy();
    }
}
