package com.example.androidanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mButtonStart;
    private Button mButtonStop;
    private ImageView mImageViewShow;
    private AnimationDrawable mAnimationDrawable;
    private Button btn_alpha;
    private Button btn_scale;
    private Button btn_trans;
    private Button btn_rotate;
    private Button btn_set;
    private ImageView imageView;
    private Animation animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         xml方式实现帧动画
         */
//        mImageViewShow = findViewById(R.id.image);
//        // 获取动画对象
//        mAnimationDrawable = (AnimationDrawable) mImageViewShow.getBackground();
//        mButtonStart = findViewById(R.id.button);
//        mButtonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //开始动画
//                mAnimationDrawable.start();
//            }
//        });
//
//        mButtonStop = findViewById(R.id.button2);
//        mButtonStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //结束动画
//                mAnimationDrawable.stop();
//            }
//        });

        /*
            Java脚本实现帧动画
         */
//        mImageViewShow = findViewById(R.id.image);
//        // 获取动画对象
//        mAnimationDrawable =new AnimationDrawable();
//        mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.img),200);
//        mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.img_1),200);
//        mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.img_2),200);
//        mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.img_3),200);
//        mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.img_4),200);
//        mAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.img_5),200);
//        mAnimationDrawable.setOneShot(false);//设置循环播放
//        mImageViewShow.setBackground(mAnimationDrawable);
//
//        mButtonStart = findViewById(R.id.button);
//        mButtonStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //开始动画
//                mAnimationDrawable.start();
//            }
//        });
//
//        mButtonStop = findViewById(R.id.button2);
//        mButtonStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //结束动画
//                mAnimationDrawable.stop();
//            }
//        });

        /*
            使用AnimationSet来写View动画  补间动画
         */
//        bindViews();

        /*
            属性动画ValueAnimator
         */
//        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
//        anim.setDuration(300);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float currentValue = (float) animation.getAnimatedValue();
//                Log.d("TAG", "cuurent value is " + currentValue);
//            }
//        });
//        anim.start();

        /*
            属性动画ObjectAnimator
         */
        TextView textview = findViewById(R.id.ObjectAnimator);
        //第一个参数要求传入一个object对象
        //旋转
//        ObjectAnimator animator = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
//        animator.setDuration(5000);
//        animator.start();
        //移动
//        float translationX = textview.getTranslationX();
//        float translationY = textview.getTranslationY();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(textview, "translationX", translationX, 360f,translationX);
//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(textview, "translationY", translationY, 360f,translationY);
//        animator.setDuration(5000);
//        animator1.setDuration(5000);
//        animator.start();
//        animator1.start();
        /*
            AnimatorSet动画组合
         */
//        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", 0f, 100f);
//        ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
//        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(rotate).with(fadeInOut).after(moveIn);
//        animSet.setDuration(5000);
//        animSet.start();

        /*
        XML 属性动画
         */
        //load 动画
        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.set_animator);
        //作用对象
        animator.setTarget(textview);
        animator.start();

    }

    private void bindViews() {
        btn_alpha = findViewById(R.id.btn_alpha);
        btn_scale = findViewById(R.id.btn_scale);
        btn_rotate = findViewById(R.id.btn_rotate);
        btn_trans = findViewById(R.id.btn_tran);
        btn_set = findViewById(R.id.btn_set);
        imageView = findViewById(R.id.img_show);

        btn_alpha.setOnClickListener((View.OnClickListener) this);
        btn_scale.setOnClickListener((View.OnClickListener) this);
        btn_rotate.setOnClickListener((View.OnClickListener) this);
        btn_trans.setOnClickListener((View.OnClickListener) this);
        btn_set.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_alpha:
                animation = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
                imageView.startAnimation(animation);
                break;
            case R.id.btn_scale:
                animation = AnimationUtils.loadAnimation(this,R.anim.anim_scale);
                imageView.startAnimation(animation);
                break;
            case R.id.btn_rotate:
                animation = AnimationUtils.loadAnimation(this,R.anim.anim_rotate);
                imageView.startAnimation(animation);
                break;
            case R.id.btn_tran:
                animation = AnimationUtils.loadAnimation(this,R.anim.anim_translate);
                imageView.startAnimation(animation);
                break;
            case R.id.btn_set:
                animation = AnimationUtils.loadAnimation(this,R.anim.anim_set);
                imageView.startAnimation(animation);
                break;
            default:
                break;
        }
    }
}
