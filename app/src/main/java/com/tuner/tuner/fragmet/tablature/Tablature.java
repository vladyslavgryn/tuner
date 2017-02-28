package com.tuner.tuner.fragmet.tablature;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuner.tuner.BuildConfig;
import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.tablature.adapter.RecyclerViewTablatureAdapter;
import com.tuner.tuner.helper.Permission;
import com.tuner.tuner.helper.PermissionInterface;
import com.tuner.tuner.models.FileModel;
import com.tuner.tuner.utils.FileUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tablature extends Fragment implements TablatureView, PermissionInterface {

    public static final String[] WRITE_PERMISSION = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public static final int REQUEST_EXTERNAL = 0;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.image_empty_files)
    ImageView imageView;

    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;

    private Unbinder unbinder;
    private Permission permission;
    private TablaturePresenter tablaturePresenter;
    private RecyclerViewTablatureAdapter recyclerViewTablatureAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        tablaturePresenter = new TablaturePresenter(this);
        permission = new Permission(getContext());
        permission.setPermissionInterface(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablature, container, false);

        unbinder = ButterKnife.bind(this, view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            tablaturePresenter.readFilesFromPath(true);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        permission.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage(int resId, int colorId) {
        Snackbar snackbar = Snackbar.make(linearLayout, resId, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(colorId);
        snackbar.show();
    }

    @Override
    public void setRecyclerView(List<FileModel> files) {
        recyclerViewTablatureAdapter = new RecyclerViewTablatureAdapter(files, tablaturePresenter);
        recyclerView.setAdapter(recyclerViewTablatureAdapter);
    }

    @Override
    public void updateFiles(List<FileModel> fileModels) {
        if (null == recyclerViewTablatureAdapter) {
            setRecyclerView(fileModels);
        } else {
            recyclerViewTablatureAdapter.updateFiles(fileModels);
            recyclerViewTablatureAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int removeFile(int position) {
        recyclerViewTablatureAdapter.removeFile(position);

        return recyclerViewTablatureAdapter.getItemCount();
    }

    @Override
    public void setVisibilityImage(int visibility) {
        imageView.setVisibility(visibility);
    }

    @Override
    public void setVisibilityRecycler(int visibility) {
        recyclerView.setVisibility(visibility);
    }

    @Override
    public void showFile(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri fileUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);

        intent.setDataAndType(fileUri, FileUtil.FILE_MIME_TYPE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showMessage(R.string.msg_err_app, Color.RED);
        }
    }

    @Override
    public void requestPermission() {
        permission.requestPermission(WRITE_PERMISSION, getActivity(), REQUEST_EXTERNAL);
    }

    @Override
    public void requestPermissionRationale() {
        Snackbar.make(linearLayout, R.string.app_chord, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.msg_err_file, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        permission.requestPermission(WRITE_PERMISSION, getActivity(), REQUEST_EXTERNAL);
                    }
                }).show();
    }

    @Override
    public void permissionGranted() {
        tablaturePresenter.readFilesFromPath(false);
    }
}
