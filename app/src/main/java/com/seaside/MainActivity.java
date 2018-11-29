package com.seaside;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabWidget;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = MainActivity.class.getSimpleName();
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabs)
    TabWidget tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        TextView SampleText = findViewById(R.id.sample_text);
//        SampleText.setText(stringFromJNI());
//        SampleText.setOnClickListener(this);
    }


    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sample_text:
//                Toast.makeText(this, "被点击了！！", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, MainActivity4.class);
//                startActivity(intent);
//                break;
//        }
    }
}
