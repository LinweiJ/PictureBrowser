package com.lwj.widget.picturebrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by mguy on 2017/7/21.
 */

public class PicturePagerAdapter<T> extends FragmentPagerAdapter {


    private ArrayList<String> mPictureList;

    public PicturePagerAdapter(FragmentManager fm, ArrayList<String> pictureList) {
        super(fm);
        mPictureList =pictureList==null? new ArrayList<String>():pictureList;

    }

    public void  setPictureList(ArrayList<String> pictureList)
    {
        mPictureList=pictureList==null? new ArrayList<String>():pictureList;
    }


    @Override
    public Fragment getItem(int i) {

        String pictureUrl= mPictureList.get(i);

        return PictureFragment.newInstance(pictureUrl);
    }

    @Override
    public int getCount() {

        return mPictureList.size();

    }
}
