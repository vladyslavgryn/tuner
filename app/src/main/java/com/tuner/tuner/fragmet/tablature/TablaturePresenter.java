package com.tuner.tuner.fragmet.tablature;

import android.graphics.Color;
import android.view.View;

import com.tuner.tuner.R;
import com.tuner.tuner.models.FileModel;
import com.tuner.tuner.utils.FileUtil;

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

    public void deleteFile(File file, int position) {
        try {
            if (file.delete()) {
                int count = tablatureView.removeFile(position);
                if (count == 0) {
                    tablatureView.setVisibilityRecycler(View.GONE);
                    tablatureView.setVisibilityImage(View.VISIBLE);
                }
                tablatureView.showMessage(R.string.msg_suc_delete, Color.WHITE);
            } else {
                tablatureView.showMessage(R.string.msg_err_delete, Color.RED);
            }
        } catch (SecurityException e) {
            tablatureView.showMessage(R.string.msg_err_delete, Color.RED);
        }
    }

    void readFilesFromPath(boolean updateFiles) {

        tablatureView.setVisibilityRecycler(View.GONE);
        tablatureView.setVisibilityImage(View.VISIBLE);

        if (FileUtil.isExternalStorageAvailable()) {
            File path = FileUtil.getApplicationPath();
            validatePath(path, updateFiles);
        } else {
            tablatureView.showMessage(R.string.msg_err_external, Color.RED);
        }
    }

    private void validatePath(File path, boolean updateFiles) {

        if (path.exists()) {
            List<FileModel> fileModels = getFiles(path);

            if (!fileModels.isEmpty()) {
                tablatureView.setVisibilityImage(View.GONE);
                tablatureView.setVisibilityRecycler(View.VISIBLE);

                if (updateFiles) {
                    tablatureView.updateFiles(fileModels);
                } else {
                    tablatureView.setRecyclerView(fileModels);
                }
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
                fileModel.setLength(file.length());

                fileModels.add(fileModel);
            }
        }

        return fileModels;
    }
}
