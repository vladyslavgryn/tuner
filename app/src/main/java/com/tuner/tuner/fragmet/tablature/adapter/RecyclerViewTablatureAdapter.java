package com.tuner.tuner.fragmet.tablature.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.tablature.TablaturePresenter;
import com.tuner.tuner.fragmet.tablature.helper.FileDiffCallback;
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

        return new ViewHolder(view, new ViewHolder.ClickViewHolder() {
            @Override
            public void deleteFile(int position) {
                tablaturePresenter.deleteFile(files.get(position).getFile(), position);
            }

            @Override
            public void openFile(int position) {
                tablaturePresenter.openFile(files.get(position).getFile());
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FileModel file = files.get(position);

        holder.textFile.setText(file.getName());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public void removeFile(int position) {
        this.files.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void updateFiles(List<FileModel> files) {
        final FileDiffCallback fileDiffCallback = new FileDiffCallback(this.files, files);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(fileDiffCallback);

        this.files.clear();
        this.files.addAll(files);

        diffResult.dispatchUpdatesTo(this);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textFile;
        Button buttonOpen, buttonDelete;
        ClickViewHolder viewHolder;

        ViewHolder(View view, ClickViewHolder clickViewHolder) {
            super(view);

            viewHolder = clickViewHolder;

            textFile = (TextView) view.findViewById(R.id.text_file);
            buttonOpen = (Button) view.findViewById(R.id.btn_open);
            buttonDelete = (Button) view.findViewById(R.id.btn_delete);

            buttonOpen.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_open:
                    viewHolder.openFile(getAdapterPosition());
                    break;
                case R.id.btn_delete:
                    viewHolder.deleteFile(getAdapterPosition());
                    break;
            }
        }

        interface ClickViewHolder {

            void deleteFile(int position);

            void openFile(int position);
        }
    }
}
