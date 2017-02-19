package com.tuner.tuner.fragmet.tablature.helper;

import android.support.v7.util.DiffUtil;

import com.tuner.tuner.models.FileModel;

import java.util.List;

public class FileDiffCallback extends DiffUtil.Callback {

    private List<FileModel> oldList;
    private List<FileModel> newList;

    public FileDiffCallback(List<FileModel> oldList, List<FileModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getLength() == newList.get(newItemPosition).getLength();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName()) &&
                oldList.get(oldItemPosition).getPath().equals(newList.get(newItemPosition).getPath());
    }
}
