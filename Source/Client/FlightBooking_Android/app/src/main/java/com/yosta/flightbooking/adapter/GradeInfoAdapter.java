package com.yosta.flightbooking.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yosta.flightbooking.R;
import com.yosta.flightbooking.binding.GradeInfoViewModel;
import com.yosta.flightbooking.databinding.ViewItemGradeBinding;
import com.yosta.flightbooking.model.GradeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phuc-Hau Nguyen on 10/14/2016.
 */

public class GradeInfoAdapter extends RecyclerView.Adapter<GradeInfoAdapter.BindingHolder> {

    private Context mContext;
    private List<GradeInfo> mGradeInfos;

    public GradeInfoAdapter(Context context) {
        this.mContext = context;
        this.mGradeInfos = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewItemGradeBinding commentBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.view_item_grade,
                parent, false);
        return new BindingHolder(commentBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ViewItemGradeBinding gradeBinding = (ViewItemGradeBinding) holder.binding;
        gradeBinding.setGrade(new GradeInfoViewModel(mContext, mGradeInfos.get(position)));
    }

    @Override
    public int getItemCount() {
        return mGradeInfos.size();
    }

    public void addGrades(List<GradeInfo> gradeInfos) {
        mGradeInfos.addAll(gradeInfos);
        notifyDataSetChanged();
    }

    public int addGrade(GradeInfo gradeInfo) {
        mGradeInfos.add(gradeInfo);
        int index = mGradeInfos.size() - 1;
        notifyItemChanged(index);
        return index;
    }

    public void clear() {
        mGradeInfos.clear();
    }

    static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public BindingHolder(ViewItemGradeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
