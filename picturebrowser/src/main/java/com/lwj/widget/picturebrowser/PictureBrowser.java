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
 * 图片浏览器
 * 基于DialogFragment
 * 支持横竖屏切换
 */

public class PictureBrowser extends DialogFragment implements PictureFragment.OnClickOutsideListener, View.OnClickListener, ViewPager.OnPageChangeListener {

    private static String PICTURE_URL_LIST = "10000";
    private static String START_INDEX = "10001";
    private static String SHOW_DELETE_ICON = "10002";
    private static String SHOW_INDEX_HINT = "10003";
    private static String CANCEL_OUTSIDE = "10004";
    private static String TAG = "PictureBrowser";
    private ViewPager mVpPicture;
    private TextView mTvNum;
    private ImageView mIvDelete;
    private boolean mShowDeleteIcon;
    private boolean mShowIndexHint;
    private boolean mCancelOutside;
    private ArrayList<String> mUrlList = new ArrayList<>();
    private int mStartIndex;
    private View mLayout;
    private PicturePagerAdapter mAdapter;
    private FragmentManager mFragmentManager;

    public PictureBrowser() {


    }



    public static PictureBrowser newInstance(ArrayList<String> pictureUrlList) {
        return newInstance(pictureUrlList, 0,true,true,true);
    }

    public static PictureBrowser newInstance(ArrayList<String> pictureUrlList, int startIndex,boolean showDeleteIcon
            ,boolean showIndexHint,boolean cancelOutside

    ) {

        Bundle args = new Bundle();
        args.putStringArrayList(PICTURE_URL_LIST, pictureUrlList);
        args.putInt(START_INDEX, startIndex);
        args.putBoolean(SHOW_DELETE_ICON, showDeleteIcon);
        args.putBoolean(SHOW_INDEX_HINT, showIndexHint);
        args.putBoolean(CANCEL_OUTSIDE, cancelOutside);
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
        private boolean mShowDeleteIcon=true;
        private boolean mShowIndexHint=true;
        private boolean mCancelOutside=true;

        public Builder() {

        }

        public Builder setStartIndex(int startIndex) {

            if (startIndex >= 0) {
                this.mStartIndex = startIndex;
            }

            return this;
        }

        public Builder setUrlList(ArrayList<String> urlList) {

            this.mUrlList = urlList == null ? new ArrayList<String>() : urlList;

            return this;
        }

        public Builder setFragmentManager(FragmentManager manager) {

            if (manager != null) {

                this.mManager = manager;

            }

            return this;
        }

        public Builder initPictureLoader(PictureLoader  loader)
        {

            PictureFragment.initPictureLoader(loader);

            return this;
        }

        public Builder setPictureLoader(PictureLoader  loader)
        {

            PictureFragment.setPictureLoader(loader);

            return this;
        }

        public Builder setShowDeleteIcon(boolean showDeleteIcon) {
            this.mShowDeleteIcon = showDeleteIcon;
            return this;
        }

        public Builder setShowIndexHint(boolean showIndexHint) {
            this.mShowIndexHint = showIndexHint;
            return this;
        }

        public Builder setCancelOutside(boolean cancelOutside) {
            this.mCancelOutside = cancelOutside;
            return this;
        }

        public PictureBrowser create() {

            //用于系统销毁(如:横竖屏切换)保存参数
            PictureBrowser fragment = PictureBrowser.newInstance(this.mUrlList, this.mStartIndex, this.mShowDeleteIcon,this.mShowIndexHint,this.mCancelOutside );
            //开启时使用的两个判断参数
            fragment.setFragmentManager(this.mManager);
            fragment.setUrlList(this.mUrlList);
            return fragment;
        }


    }



    public void setFragmentManager(FragmentManager manager) {

        if (manager != null) {
            this.mFragmentManager = manager;
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> urlList = bundle.getStringArrayList(PICTURE_URL_LIST);

            if (urlList != null) {
                this.mUrlList.clear();
                this.mUrlList.addAll(urlList);
            }
            this.mStartIndex = bundle.getInt(START_INDEX);
            this.mShowDeleteIcon = bundle.getBoolean(SHOW_DELETE_ICON,true);
            this.mShowIndexHint = bundle.getBoolean(SHOW_INDEX_HINT,true);
            this.mCancelOutside = bundle.getBoolean(CANCEL_OUTSIDE,true);
        }

        this.mLayout = inflater.inflate(R.layout.layout_picture_browser_dialog, container, false);
        this.mVpPicture = (ViewPager) this.mLayout.findViewById(R.id.vp_picture);
        this.mTvNum = (TextView) this.mLayout.findViewById(R.id.tv_num);
        this.mIvDelete = (ImageView) this.mLayout.findViewById(R.id.iv_delete);
        this.mIvDelete.setOnClickListener(this);
        this.mVpPicture.addOnPageChangeListener(this);
        //显示
        setShowIndexHint(this.mShowIndexHint);
        setShowDeleteIcon(this.mShowDeleteIcon);

        FragmentManager manager = getChildFragmentManager();
        this.mAdapter = new PicturePagerAdapter(manager, this.mUrlList);
        this.mVpPicture.setAdapter(this.mAdapter);

        //设置开始页
        setCurrentItem(this.mStartIndex);
        setCurrentItemHint(this.mStartIndex);

        return mLayout;

    }

    /**
     * 显示删除图标
     * @param showDeleteIcon
     */
    public void setShowDeleteIcon(boolean showDeleteIcon){

        this.mShowDeleteIcon=showDeleteIcon;
        if(this.mShowDeleteIcon){
            mIvDelete.setVisibility(View.VISIBLE);
        }else
        {
            mIvDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 显示Index文字
     * @param showIndexHint
     */
    public void setShowIndexHint(boolean showIndexHint){

        this.mShowIndexHint=showIndexHint;
        if(this.mShowIndexHint){
            mTvNum.setVisibility(View.VISIBLE);
        }else
        {
            mTvNum.setVisibility(View.GONE);
        }
    }

    /**
     * 是否点击图片退出
     * @param cancelOutside
     */
    public void setCancelOutside(boolean cancelOutside){

        this.mCancelOutside=cancelOutside;
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
            mVpPicture.setCurrentItem(item);
        }
    }

    private void setCurrentItemHint(int position) {

        if (position < mUrlList.size()) {
            mTvNum.setText((position + 1) + "/" + mUrlList.size());
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItemHint(position);
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
        if(this.mCancelOutside)
        {
            this.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
