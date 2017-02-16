package com.tuner.tuner.fragmet.tablature;

import com.tuner.tuner.models.FileModel;

import java.io.File;
import java.util.List;

interface TablatureView {

    void showFile(File file);

    void showMessage(int resId, int colorId);

    void isVisibilityImage(int visibility);

    void isVisibilityRecycler(int visibility);

    void setRecyclerView(List<FileModel> files);
}
