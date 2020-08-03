package com.example.shibegallery.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shibegallery.databinding.ItemImageBinding;

import java.util.ArrayList;
import java.util.List;

public class ShibeAdapter extends RecyclerView.Adapter<ShibeAdapter.ShibeViewHolder> {

    private List<String> urls;
    private OnImageClickedListener listener;

    public ShibeAdapter() {
        this(null);
    }

    public ShibeAdapter(@Nullable  OnImageClickedListener listener) {
        this.urls = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShibeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageBinding binding = ItemImageBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ShibeViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShibeViewHolder holder, int position) {
        String url = urls.get(position);
        holder.loadImage(url);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void loadImage(List<String> urls) {
        this.urls.clear();
        this.urls = urls;
        notifyDataSetChanged();
    }

    public void setListener(OnImageClickedListener listener) {
        this.listener = listener;
    }

    static class ShibeViewHolder extends RecyclerView.ViewHolder {
        private ItemImageBinding binding;
        private OnImageClickedListener listener;

        public ShibeViewHolder(@NonNull ItemImageBinding binding, @NonNull OnImageClickedListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void loadImage(String url) {
            Glide.with(binding.ivShibe)
                    .load(url)
                    .into(binding.ivShibe);

            binding.getRoot().setOnClickListener(
                    (view) -> listener.imageSelected(url)
            );
        }
    }

     public interface OnImageClickedListener {
        public void imageSelected(String url);
    }
}
