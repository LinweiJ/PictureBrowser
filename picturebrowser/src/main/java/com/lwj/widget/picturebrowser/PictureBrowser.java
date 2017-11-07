package com.lwj.widget.picturebrowser;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lwj on 2017/11/6.
 */

public class PictureBrowser extends DialogFragment implements PictureFragment.OnClickOutsideListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    private static String PICTUREURLLIST = "10000";
    private static String STARTINDEX = "10001";
    private static String TAG = "PictureBrowser";
    //    private RecyclerView mRvPicture;
    private ViewPager mVpPicture;
    private TextView mTvNum;
    private ImageView mIvDelete;
    private ArrayList<String> mUrlList = new ArrayList<>();
    private int mStartIndex;
    private View mLayout;
    private PicturePagerAdapter<String> mAdapter;
    private FragmentManager mFragmentManager;

    public PictureBrowser() {


    }

    public static PictureBrowser newInstance(ArrayList<String> pictureUrlList) {
        return newInstance(pictureUrlList, 0);
    }

    public static PictureBrowser newInstance(ArrayList<String> pictureUrlList, int startIndex) {

        Bundle args = new Bundle();
        args.putStringArrayList(PICTUREURLLIST, pictureUrlList);
        args.putInt(STARTINDEX, startIndex);
        PictureBrowser fragment = new PictureBrowser();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 建造者
     */
    public static class Builder {

        private int mStartIndex;
        private ArrayList<String> mUrlList;
        private FragmentManager mManager;

        public Builder() {

        }

        public Builder setStartIndex(int startIndex) {

            if (startIndex >= 0) {
                mStartIndex = startIndex;
            }

            return this;
        }

        public Builder setUrlList(ArrayList<String> urlList) {

            mUrlList = urlList == null ? new ArrayList<String>() : urlList;

            return this;
        }

        public Builder setFragmentManager(FragmentManager manager) {

            if (manager != null) {

                mManager = manager;

            }

            return this;
        }

        public  Builder setPictureLoader(PictureLoader pictureLoader)
        {

            PictureFragment.setPictureLoader(pictureLoader);
            return this;
        }

        public PictureBrowser create() {

            //用于切屏保存参数
            PictureBrowser fragment = PictureBrowser.newInstance(mUrlList, mStartIndex);
            //开启时使用的两个判断参数
            fragment.setFragmentManager(mManager);
            fragment.setUrlList(mUrlList);

            return fragment;
        }


    }


    public void setFragmentManager(FragmentManager manager) {

        if (manager != null) {

            mFragmentManager = manager;

        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.picture_browser_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        Log.e("mLayout", "" + (mLayout == null));


        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> urlList = bundle.getStringArrayList(PICTUREURLLIST);

            if (urlList != null) {
                mUrlList.clear();
                mUrlList.addAll(urlList);
            }
            mStartIndex = bundle.getInt(STARTINDEX);
        }

        mLayout = inflater.inflate(R.layout.layout_picture_browser_dialog, container, false);

        mVpPicture = (ViewPager) mLayout.findViewById(R.id.vp_picture);
        mTvNum = (TextView) mLayout.findViewById(R.id.tv_num);
        mIvDelete = (ImageView) mLayout.findViewById(R.id.iv_delete);
        mIvDelete.setOnClickListener(this);


        mVpPicture.addOnPageChangeListener(this);

        mVpPicture.removeAllViews();
        FragmentManager manager = getChildFragmentManager();
        mAdapter = new PicturePagerAdapter<>(manager, mUrlList);
        mVpPicture.setAdapter(mAdapter);


        setCurrentItem(mStartIndex);
        setCurrentItemNum(mStartIndex);

        return mLayout;

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    public DialogFragment show() {
        if (mFragmentManager != null && mUrlList.size() > 0) {
            this.show(mFragmentManager, TAG);
        }
        return this;
    }

    public DialogFragment show(FragmentManager manager) {
        if (mUrlList.size() > 0) {
            this.show(manager, TAG);
        }
        return this;
    }

    @Override
    public void show(FragmentManager manager, String tag) {


        super.show(manager, tag);


    }


    public void setStartIndex(int startIndex) {

        if (startIndex >= 0) {
            mStartIndex = startIndex;
        }
    }

    public void setUrlList(ArrayList<String> urlList) {
        mUrlList.clear();
        if (urlList != null) {
            mUrlList.addAll(urlList);
            if (mAdapter != null) {
                mAdapter.setPictureList(urlList);
            }
        }
    }

    private void setCurrentItem(int item) {

        if (mVpPicture != null) {
            Log.e("mStartIndex", "" + item);
            mVpPicture.setCurrentItem(item);
        }
    }

    private void setCurrentItemNum(int position) {

        if (position < mUrlList.size()) {
            mTvNum.setText((position + 1) + "/" + mUrlList.size());
        }


    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItemNum(position);
    }

    /**
     * 状态
     *
     * @param i
     */
    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.iv_delete) {
            this.dismiss();

        }


    }

    /**
     * PictureFragment 点击消失
     */
    @Override
    public void onClickOutside() {

        this.dismiss();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
}
