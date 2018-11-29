package com.seaside.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.seaside.R;
import com.seaside.ui.fragment.ChatFragment;
import com.seaside.ui.fragment.ContactsFragment;

/**
 * Createed by Deven
 * on 2018/11/28.
 * Descibe: TODO:
 */
public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.fth_main)
    FragmentTabHost fthMain;
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabs)
    TabWidget tabs;
    private String[] tabTag = {"chat", "contacts"};
    private String[] tabName = {"聊天", "联系人"};
    private Class[] fragments = {ChatFragment.class, ContactsFragment.class};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int[] rID = new int[]{R.drawable.selector_tab_chat, R.drawable.selector_tab_contacts};
        fthMain.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        fthMain.getTabWidget().setDividerDrawable(null);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < tabTag.length; i++) {
            View view = inflater.inflate(R.layout.layout_tabview, null);
            ImageView tabicon = view.findViewById(R.id.iv_tab_icon);
            TextView tabName = view.findViewById(R.id.tv_tab_name);
            tabicon.setImageResource(rID[i]);
            tabName.setText(this.tabName[i]);
            fthMain.addTab(
                    fthMain.newTabSpec(tabTag[i]).setIndicator(view),
                    fragments[i], null);
        }
    }


    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();
}
