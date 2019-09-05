package org.wit.gallery.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.gallery.helpers.exists
import org.wit.gallery.helpers.read
import org.wit.gallery.helpers.write
import org.wit.gallery.helpers.*
import java.util.*

val JSON_FILE = "galleries.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<GalleryModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class GalleryJSONStore : GalleryStore, AnkoLogger {

  val context: Context
  var galleries = mutableListOf<GalleryModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<GalleryModel> {
    return galleries
  }

  override fun create(gallery: GalleryModel) {
    gallery.id = generateRandomId()
    galleries.add(gallery)
    serialize()
  }

  override fun update(gallery: GalleryModel) {
    val galleriesList = findAll() as ArrayList<GalleryModel>
    var foundgallery: GalleryModel? = galleriesList.find { p -> p.id == gallery.id }
    if (foundgallery != null) {
      foundgallery.title = gallery.title
      foundgallery.description = gallery.description
      foundgallery.image = gallery.image
      foundgallery.lat = gallery.lat
      foundgallery.lng = gallery.lng
      foundgallery.zoom = gallery.zoom
    }
    serialize()
  }

  override fun delete(gallery: GalleryModel) {
    galleries.remove(gallery)
    serialize()
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(galleries, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    galleries = Gson().fromJson(jsonString, listType)
  }
}
