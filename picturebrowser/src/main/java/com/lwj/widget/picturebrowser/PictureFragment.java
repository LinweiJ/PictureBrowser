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
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment implements View.OnClickListener {


    private static String PICTUREURL = "pictureUrl";
    private String mPictureUrl;
    private PhotoView mIvPicture;
    private FrameLayout mFlPicture;
    private View mSpaceOutside;
    private OnClickOutsideListener mOnClickOutsideListener;
    private static PictureLoader mPictureLoader;

    public PictureFragment() {
    }

    public static PictureFragment newInstance(String pictureUrl) {

        Bundle args = new Bundle();
        args.putString(PICTUREURL, pictureUrl);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mPictureUrl = bundle.getString(PICTUREURL);
        }

        View view = inflater.inflate(R.layout.fragment_picture, container, false);

        mIvPicture = (PhotoView) view.findViewById(R.id.iv_picture);
        // 启用图片缩放功能
        mIvPicture.enable();
        mIvPicture.setOnClickListener(this);
        if (getParentFragment() instanceof OnClickOutsideListener) {
            setOnClickOutsideListener((OnClickOutsideListener) getParentFragment());
        }

        //展示图片
        showPicture(this,mIvPicture,mPictureUrl);

        return view;

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 可以覆写该方法
     * @param pictureFragment
     * @param ivPicture
     * @param pictureUrl
     */
    public void showPicture(PictureFragment pictureFragment, PhotoView ivPicture, String pictureUrl)
    {
//        Glide.with(this)
//                .load(pictureUrl)
//                .placeholder(new ColorDrawable(Color.LTGRAY))
//                .into(mIvPicture);
        if(this.mPictureLoader!=null){
            this.mPictureLoader.showPicture(pictureFragment, ivPicture,pictureUrl);
        }
    }

    public static void setPictureLoader(PictureLoader pictureLoader)
    {
        if(mPictureLoader==null&&pictureLoader!=null)
        {
            mPictureLoader=pictureLoader;
        }

    }
    public static PictureLoader getPictureLoader()
    {
        return mPictureLoader;
    }
    public interface OnClickOutsideListener {

        void onClickOutside();
    }


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
