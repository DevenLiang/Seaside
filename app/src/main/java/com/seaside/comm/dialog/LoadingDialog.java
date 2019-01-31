package com.seaside.comm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.*;
import com.airbnb.lottie.LottieAnimationView;
import com.seaside.R;
import com.seaside.utils.DensityUtil;


/**
 * Createed by Deven
 * on 2018/12/14.
 * Descibe: TODO:
 */
public class LoadingDialog extends Dialog {
    private View dialogView;
    private Context mContext;
    private LottieAnimationView mLottieAnimationView;

    //跳水
    public static final int JUMP = 1;
    //普通加载方式
    public static final int NORMAL = 2;

    @IntDef({JUMP, NORMAL})
    public @interface MODE {
    }


    @LoadingDialog.MODE
    private int currMode = NORMAL;

    public void setCurrMode(@LoadingDialog.MODE int currMode) {
        this.currMode = currMode;
    }


    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.commonDialogNoBackground);
        mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.comm_loading, null);

        setContentView(dialogView);
        //setCancelable(false);

        Window win = getWindow();
        if (win != null) {
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.height = DensityUtil.dip2px(150);
            lp.width = DensityUtil.dip2px(150);
            win.setAttributes(lp);
        }

        mLottieAnimationView = dialogView.findViewById(R.id.animation_view);

        playMode();

    }

    private void playMode() {
        String file = "data.json";
        if (currMode == JUMP) {
            file = "jump_loader.json";
        }
        mLottieAnimationView.setAnimation(file);
        mLottieAnimationView.loop(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLottieAnimationView.playAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLottieAnimationView.cancelAnimation();
    }

    /**
     * 向外提供获取view的方法
     *
     * @param viewId viewId
     * @return view
     */
    public View getView(int viewId) {
        return dialogView.findViewById(viewId);
    }
}
