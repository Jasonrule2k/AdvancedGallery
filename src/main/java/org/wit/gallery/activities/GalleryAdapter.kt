package org.wit.gallery.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Gallery
import kotlinx.android.synthetic.main.card_gallery.view.*
import org.wit.gallery.R
import org.wit.gallery.helpers.readImageFromPath
import org.wit.gallery.models.GalleryModel

interface GalleryListener {
  fun onGalleryClick(Gallery: GalleryModel)
}

class GalleryAdapter constructor(private var galleries: List<GalleryModel>,
                                   private val listener: GalleryListener) : RecyclerView.Adapter<GalleryAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_gallery, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val gallery = galleries[holder.adapterPosition]
    holder.bind(gallery, listener)
  }

  override fun getItemCount(): Int = galleries.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(gallery: GalleryModel, listener: GalleryListener) {
      itemView.galleryTitle.text = gallery.title
      itemView.description.text = gallery.description
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, gallery.image))
      itemView.setOnClickListener { listener.onGalleryClick(gallery) }
    }
  }
}