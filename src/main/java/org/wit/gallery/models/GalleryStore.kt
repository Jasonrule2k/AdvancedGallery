package org.wit.gallery.models

interface GalleryStore {
  fun findAll(): List<GalleryModel>
  fun create(gallery: GalleryModel)
  fun update(gallery: GalleryModel)
  fun delete(gallery: GalleryModel)
}