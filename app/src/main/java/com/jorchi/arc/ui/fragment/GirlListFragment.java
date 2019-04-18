package com.jorchi.arc.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jorchi.arc.R;
import com.jorchi.arc.data.DataRepository;
import com.jorchi.arc.data.local.db.AppDatabaseManager;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.databinding.FragmentGirlListBinding;
import com.jorchi.arc.global.BasicApp;
import com.jorchi.arc.ui.adapter.GirlListAdapter;
import com.jorchi.arc.ui.listener.OnItemClickListener;
import com.jorchi.arc.utils.Util;
import com.jorchi.arc.viewmodel.GirlListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GirlListFragment extends Fragment {

    private FragmentGirlListBinding mBinding;
    private GirlListViewModel mGirlListViewModel;
    private GirlListAdapter mAdapter;

    private final OnItemClickListener<Girl> mGirlClickListener = new OnItemClickListener<Girl>() {
        @Override
        public void onClick(Girl girl) {
            if (Util.isNetworkConnected(getContext().getApplicationContext())) {

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_girl_list, container, false);
        mAdapter = new GirlListAdapter(mGirlClickListener);
        mBinding.rvGirlList.setAdapter(mAdapter);
        initView();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        mBinding.rvGirlList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPostion = layoutManager.findLastVisibleItemPosition();
                if (lastPostion == mAdapter.getItemCount() - 1) {
                    //上拉加载更多
                    mGirlListViewModel.loadNextPageGirls();
                }
            }
        });

        mBinding.srl.setOnRefreshListener(() -> {
            mAdapter.clearGirlList();
            mBinding.setRefresh(true);
            mGirlListViewModel.refreshGirlsData();
        });
        mBinding.srl.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void subscribeUI() {
        if (!isAdded()) return;
        GirlListViewModel.Factory factory = new GirlListViewModel.Factory(BasicApp.getInstance(),
                DataRepository.getInstance());
        mGirlListViewModel = ViewModelProviders.of(this, factory).get(GirlListViewModel.class);

        mGirlListViewModel.getGirlsLiveData().observe(this, new Observer<List<Girl>>() {
            @Override
            public void onChanged(List<Girl> girls) {
                if (girls != null)
                    mAdapter.setGirlList(girls);
            }
        });

        mGirlListViewModel.getLoadMoreState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading == null) return;
                if (loading == false) {
                    if (mBinding.srl.isRefreshing())
                        mBinding.srl.setRefreshing(false);
                }
                mBinding.setIsShow(loading);
            }
        });
        mGirlListViewModel.refreshGirlsData();
        mBinding.setRefresh(true);
    }
}

























