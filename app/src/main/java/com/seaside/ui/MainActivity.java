package com.seaside.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.seaside.R;
import com.seaside.SeasideApplication;
import com.seaside.di.components.DaggerMainComponent;
import com.seaside.presenter.MainPresenter;
import com.seaside.ui.fragment.ChatFragment;
import com.seaside.ui.fragment.ContactsFragment;
import retrofit2.Retrofit;

import javax.inject.Inject;

/**
 * Createed by Deven
 * on 2018/11/28.
 * Descibe: TODO:
 */
public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.fth_main)
    FragmentTabHost fthMain;
//    @BindView(android.R.id.tabcontent)
//    FrameLayout tabcontent;
//    @BindView(R.id.realtabcontent)
//    FrameLayout realtabcontent;
//    @BindView(android.R.id.tabs)
//    TabWidget tabs;
    private String[] tabTag = {"chat", "contacts"};
    private String[] tabName = {"聊天", "联系人"};
    private Class[] fragments = {ChatFragment.class, ContactsFragment.class};
    @Inject
    Retrofit mRetrofit;
    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        DaggerMainComponent.builder().appComponent(((SeasideApplication)getApplication()).getAppComponent())
                .build().inject(this);
        Log.w("AMainActivity","mRetrofit =" + mRetrofit.toString());
        Log.w("AMainActivity", "mainPresenter = " +mainPresenter.toString());


    }

    private void initView() {
        int[] rID = new int[]{R.drawable.selector_tab_chat, R.drawable.selector_tab_contacts};
        fthMain.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
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
