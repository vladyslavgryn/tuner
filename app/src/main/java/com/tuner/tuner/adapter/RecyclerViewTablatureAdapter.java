package com.tuner.tuner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.tablature.TablaturePresenter;
import com.tuner.tuner.models.FileModel;

import java.util.List;

public class RecyclerViewTablatureAdapter extends RecyclerView.Adapter<RecyclerViewTablatureAdapter.ViewHolder> {

    private List<FileModel> files;
    private TablaturePresenter tablaturePresenter;

    public RecyclerViewTablatureAdapter(List<FileModel> files, TablaturePresenter tablaturePresenter) {
        this.files = files;
        this.tablaturePresenter = tablaturePresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_tablature, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileModel file = files.get(position);

        holder.textFile.setText(file.getName());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textFile;
        Button buttonOpen, buttonDelete;

        ViewHolder(View view) {
            super(view);

            textFile = (TextView) view.findViewById(R.id.text_file);
            buttonOpen = (Button) view.findViewById(R.id.btn_open);
            buttonDelete = (Button) view.findViewById(R.id.btn_delete);

            buttonOpen.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            FileModel fileModel = files.get(getAdapterPosition());

            switch (v.getId()) {
                case R.id.btn_open:
                    tablaturePresenter.openFile(fileModel.getFile());
                    break;
                case R.id.btn_delete:
                    tablaturePresenter.deleteFile(fileModel.getFile());
                    break;
            }
        }
    }
}
