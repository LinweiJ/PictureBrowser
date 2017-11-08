package com.lwj.widget.picturebrowser;

import android.support.v4.app.Fragment;

import com.bm.library.PhotoView;

/**
 * Created by lwj on 2017/11/8 00:42.
 */

/**
 * Created by lwj on 2017/11/6.
 * 作用于{@link PictureFragment#showPicture(PictureFragment,PhotoView,String)}的图片加载器
 *
 */

public  interface PictureLoader  {

    void showPicture(PictureFragment fragment, PhotoView pictureView, String pictureUrl);

}

