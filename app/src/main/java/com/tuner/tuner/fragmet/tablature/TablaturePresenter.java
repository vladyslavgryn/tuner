package com.tuner.tuner.fragmet.tablature;

import android.graphics.Color;
import android.view.View;

import com.tuner.tuner.R;
import com.tuner.tuner.utils.FileUtil;
import com.tuner.tuner.models.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TablaturePresenter {

    private TablatureView tablatureView;

    TablaturePresenter(TablatureView tablatureView) {
        this.tablatureView = tablatureView;
    }

    public void openFile(File file) {
        if (file.exists()) {
            tablatureView.showFile(file);
        } else {
            tablatureView.showMessage(R.string.msg_err_file, Color.RED);
        }
    }

    public void deleteFile(File file) {
        try {
            if (file.delete()) {
                tablatureView.showMessage(R.string.msg_suc_delete, Color.WHITE);
                readFilesFromPath();
            } else {
                tablatureView.showMessage(R.string.msg_err_delete, Color.RED);
            }
        } catch (SecurityException e) {
            tablatureView.showMessage(R.string.msg_err_delete, Color.RED);
        }
    }

    void readFilesFromPath() {

        tablatureView.isVisibilityRecycler(View.GONE);
        tablatureView.isVisibilityImage(View.VISIBLE);

        if (FileUtil.isExternalStorageAvailable()) {
            File path = FileUtil.getApplicationPath();
            validatePath(path);
        } else {
            tablatureView.showMessage(R.string.msg_err_external, Color.RED);
        }
    }

    private void validatePath(File path) {

        if (path.exists()) {
            List<FileModel> fileModels = getFiles(path);

            if (!fileModels.isEmpty()) {
                tablatureView.isVisibilityImage(View.GONE);
                tablatureView.isVisibilityRecycler(View.VISIBLE);
                tablatureView.setRecyclerView(fileModels);
            }
        } else {
            if (!path.mkdir()) {
                tablatureView.showMessage(R.string.msg_err_dir, Color.RED);
            }
        }
    }

    private List<FileModel> getFiles(File path) {

        List<FileModel> fileModels = new ArrayList<>();
        File[] files = path.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(FileUtil.FILE_EXT)) {
                FileModel fileModel = new FileModel();

                fileModel.setName(file.getName());
                fileModel.setPath(file.getAbsolutePath());
                fileModel.setFile(file.getAbsoluteFile());

                fileModels.add(fileModel);
            }
        }

        return fileModels;
    }
}
