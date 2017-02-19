package com.tuner.tuner.fragmet.tablature;

import com.tuner.tuner.models.FileModel;

import java.io.File;
import java.util.List;

interface TablatureView {

    void showFile(File file);

    void showMessage(int resId, int colorId);

    void setVisibilityImage(int visibility);

    void setVisibilityRecycler(int visibility);

    void updateFiles(List<FileModel> fileModels);

    int removeFile(int position);

    void setRecyclerView(List<FileModel> files);
}
