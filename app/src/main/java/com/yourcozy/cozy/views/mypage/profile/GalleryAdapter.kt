package com.yourcozy.cozy.views.mypage.profile

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yourcozy.cozy.R

class GalleryAdapter : PagedListAdapter<ItemPhoto, GalleryAdapter.ViewHolder>(diffItemCallback) {

    companion object {
        private val diffItemCallback = object : DiffUtil.ItemCallback<ItemPhoto>() {
            override fun areItemsTheSame(oldItem: ItemPhoto, newItem: ItemPhoto): Boolean =
                oldItem.imgDataPath == newItem.imgDataPath

            override fun areContentsTheSame(oldItem: ItemPhoto, newItem: ItemPhoto): Boolean =
                oldItem == newItem
        }
    }

    interface ACallback {
        fun onClickEvent(position: Int, uri: Uri)
    }

    private var callback: ACallback? = null

    fun setCallback(callback: ACallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            with(holder) {
                val uri = item.imgDataPath.toUri()
                var image: ImageView = itemView.findViewById(R.id.iv_gallery_img)

                Glide.with(holder.itemView.context).load(uri).into(image)
//                holder.bind(uri)
                itemView.setOnClickListener {
                    callback?.onClickEvent(position, uri)
                }
//                Glide.with(holder.itemView.context).load(item.imgDataPath).into(iv_gallery_img)
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    inner class ViewHolder(parent: ViewGroup) : GalleryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
    )
}