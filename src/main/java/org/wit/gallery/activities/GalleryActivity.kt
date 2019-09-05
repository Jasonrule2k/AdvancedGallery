package org.wit.gallery.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_gallery.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.gallery.R
import org.wit.gallery.helpers.readImage
import org.wit.gallery.helpers.readImageFromPath
import org.wit.gallery.helpers.showImagePicker
import org.wit.gallery.main.MainApp
import org.wit.gallery.models.GalleryModel
import org.wit.gallery.models.Location

class GalleryActivity : AppCompatActivity(), AnkoLogger {

  var gallery = GalleryModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var edit = false;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gallery)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("gallery Activity started..")

    app = application as MainApp

    if (intent.hasExtra("gallery_edit")) {
      edit = true
      gallery = intent.extras.getParcelable<GalleryModel>("gallery_edit")
      galleryTitle.setText(gallery.title)
      description.setText(gallery.description)
      galleryImage.setImageBitmap(readImageFromPath(this, gallery.image))
      if (gallery.image != null) {
        chooseImage.setText(R.string.change_gallery_image)
      }
      btnAdd.setText(R.string.save_gallery)
    }

    btnAdd.setOnClickListener() {
      gallery.title = galleryTitle.text.toString()
      gallery.description = description.text.toString()
      if (gallery.title.isEmpty()) {
        toast(R.string.enter_gallery_title)
      } else {
        if (edit) {
          app.galleries.update(gallery.copy())
        } else {

          app.galleries.create(gallery.copy())
        }
      }
      info("add Button Pressed: $galleryTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_gallery, menu)
    if (edit && menu != null) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        app.galleries.delete(gallery)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          gallery.image = data.getData().toString()
          galleryImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_gallery_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          gallery.lat = location.lat
          gallery.lng = location.lng
          gallery.zoom = location.zoom
        }
      }
    }
  }
}

