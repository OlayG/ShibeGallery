package com.example.appkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appkotlin.databinding.ItemImageBinding
import com.example.appkotlin.extension.loadImage

class AnimalAdapter : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    private var urls: MutableList<String> = mutableListOf()

    private var listener: OnImageClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
    ).let { AnimalViewHolder(it) }

    override fun getItemCount() = urls.count()

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.loadImage(urls[position])
    }

    fun loadImage(urls: List<String>) {
        this.urls.clear()
        this.urls.addAll(urls)
        notifyDataSetChanged()
    }

    inner class AnimalViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun loadImage(url: String) {
            binding.ivShibe.apply {
                loadImage(url)
                setOnClickListener { listener?.imageSelected(url) }
            }
        }
    }

    interface OnImageClickedListener {
        fun imageSelected(url: String)
    }
}