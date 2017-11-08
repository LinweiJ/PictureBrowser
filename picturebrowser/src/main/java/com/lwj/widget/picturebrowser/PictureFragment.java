package com.lwj.widget.picturebrowser;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bm.library.PhotoView;


/**
 * Created by lwj on 2017/11/6.
 * A simple {@link Fragment} subclass.
 * PictureBrowser下ViewPager单页Fragment
 */
public class PictureFragment extends Fragment implements View.OnClickListener {


    private static String PICTURE_URL = "10200";
    private String mPictureUrl;
    private PhotoView mIvPicture;
    private OnClickOutsideListener mOnClickOutsideListener;
    private static PictureLoader mPictureLoader;
    private View mLayout;

    public PictureFragment() {
    }

    public static PictureFragment newInstance(String pictureUrl) {

        Bundle args = new Bundle();
        args.putString(PICTURE_URL, pictureUrl);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (mLayout == null) {//第一次加载
            Bundle bundle = getArguments();
            if (bundle != null) {
                mPictureUrl = bundle.getString(PICTURE_URL);
            }

            mLayout = inflater.inflate(R.layout.fragment_picture, container, false);

            mIvPicture = (PhotoView) mLayout.findViewById(R.id.iv_picture);
            // 启用图片缩放功能
            mIvPicture.enable();
            mIvPicture.setOnClickListener(this);
            if (getParentFragment() instanceof OnClickOutsideListener) {
                setOnClickOutsideListener((OnClickOutsideListener) getParentFragment());
            }
            //展示图片
            showPicture(this, mIvPicture, mPictureUrl);
        } else {//已经加载过
            ViewGroup parent = (ViewGroup) mLayout.getParent();
            if (parent != null) {
                parent.removeView(mLayout);
            }
        }

        return mLayout;

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 显示图片
     *
     * @param fragment
     * @param pictureView
     * @param pictureUrl
     */
    public void showPicture(PictureFragment fragment, PhotoView pictureView, String pictureUrl) {
        if (mPictureLoader != null) {
            mPictureLoader.showPicture(fragment, pictureView, pictureUrl);
        }
    }

    /**
     * 初始化图片加载器(只设置一次)
     *
     * @param loader
     */
    public static void initPictureLoader(PictureLoader loader) {
        if (mPictureLoader == null && loader != null) {
            mPictureLoader = loader;
        }
    }

    /**
     * 设置图片加载器
     *
     * @param loader
     */
    public static void setPictureLoader(PictureLoader loader) {
        mPictureLoader = loader;
    }


    public interface OnClickOutsideListener {
        void onClickOutside();
    }

    /**
     * 点击图片外围事件由PictureBrowser处理(默认点击退出)
     *
     * @param listener
     */
    public void setOnClickOutsideListener(OnClickOutsideListener listener) {

        mOnClickOutsideListener = listener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_picture) {
            if (mOnClickOutsideListener != null) {
                mOnClickOutsideListener.onClickOutside();
            }

        }
    }


}

