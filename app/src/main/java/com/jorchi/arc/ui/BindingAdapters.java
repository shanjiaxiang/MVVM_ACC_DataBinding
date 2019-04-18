package com.jorchi.arc.ui;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jorchi.arc.R;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BindingAdapters {
    @BindingAdapter("isShow")
    public static void isShow(View view, boolean show){
        view.setVisibility(show? View.VISIBLE:View.GONE);
    }
    @BindingAdapter("refresh")
    public static void refresh(SwipeRefreshLayout view, boolean refresh){
       view.setRefreshing(refresh);
    }
    @BindingAdapter("imgUrl")
    public static void setImgUrl(ImageView view, String imgUrl){
        Glide.with(view.getContext())
                .load(imgUrl)
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(view);
    }

}
