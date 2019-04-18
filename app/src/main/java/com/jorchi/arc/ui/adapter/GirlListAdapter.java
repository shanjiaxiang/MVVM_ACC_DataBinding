package com.jorchi.arc.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.jorchi.arc.R;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.databinding.FragmentGirlListItemBinding;
import com.jorchi.arc.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class GirlListAdapter extends RecyclerView.Adapter<GirlListAdapter.GirlViewHolder> {

    private OnItemClickListener<Girl> mGirlClickListener = null;

    private List<Girl> mGirlList = null;

    public GirlListAdapter(OnItemClickListener<Girl> listener) {
        mGirlClickListener = listener;
        mGirlList = new ArrayList<>();
    }

    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentGirlListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.fragment_girl_list_item, parent,
                        false);
        return new GirlViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GirlViewHolder holder, int position) {
        holder.mBinding.setGirl(mGirlList.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mGirlList == null ? 0 : mGirlList.size();
    }

    public void setGirlList(List<Girl> girlList) {
        mGirlList.addAll(girlList);
        notifyDataSetChanged();
    }

    public void clearGirlList() {
        mGirlList.clear();
        notifyDataSetChanged();
    }

    class GirlViewHolder extends RecyclerView.ViewHolder {
        FragmentGirlListItemBinding mBinding;

        GirlViewHolder(FragmentGirlListItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
