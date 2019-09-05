package org.wit.gallery.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.gallery.models.GalleryJSONStore
import org.wit.gallery.models.GalleryStore

class MainApp : Application(), AnkoLogger {

  lateinit var galleries: GalleryStore

  override fun onCreate() {
    super.onCreate()
    this.galleries = GalleryJSONStore(applicationContext)
    info("Gallery started")
  }
}