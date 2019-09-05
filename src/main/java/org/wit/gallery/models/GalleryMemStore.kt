package org.wit.gallery.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class GalleryMemStore : GalleryStore, AnkoLogger {

  val galleries = ArrayList<GalleryModel>()

  override fun findAll(): List<GalleryModel> {
    return galleries
  }

  override fun create(gallery: GalleryModel) {
    gallery.id = getId()
    galleries.add(gallery)
    logAll()
  }

  override fun update(gallery: GalleryModel) {
    var foundGallery: GalleryModel? = galleries.find { p -> p.id == gallery.id }
    if (foundGallery != null) {
      foundGallery.title = gallery.title
      foundGallery.description = gallery.description
      foundGallery.image = gallery.image
      foundGallery.lat = gallery.lat
      foundGallery.lng = gallery.lng
      foundGallery.zoom = gallery.zoom
      logAll();
    }
  }

  override fun delete(gallery: GalleryModel) {
    galleries.remove(gallery)
  }
  
  fun logAll() {
    galleries.forEach { info("${it}") }
  }
}