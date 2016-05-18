package com.anenn.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void defaultInputMethod(View view) {
        startActivity(new Intent(this, SimplePassCodeActivity.class));
    }

    public void customInputMethod(View view) {
        startActivity(new Intent(this, EnhancePassCodeActivity.class));
    }
}
