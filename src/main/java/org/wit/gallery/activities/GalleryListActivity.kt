package org.wit.gallery.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_gallery_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.gallery.R
import org.wit.gallery.main.MainApp
import org.wit.gallery.models.GalleryModel

class GalleryListActivity : AppCompatActivity(), GalleryListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gallery_list)
    app = application as MainApp
    toolbarMain.title = title
    setSupportActionBar(toolbarMain)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = GalleryAdapter(app.galleries.findAll(), this)
    loadGalleries()
  }

  private fun loadGalleries() {
    showGalleries( app.galleries.findAll())
  }

  fun showGalleries (galleries: List<GalleryModel>) {
    recyclerView.adapter = GalleryAdapter(galleries, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<GalleryActivity>(0)
      R.id.item_map -> startActivity<GalleryMapsActivity>()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onGalleryClick(Gallery: GalleryModel) {
    startActivityForResult(intentFor<GalleryActivity>().putExtra("gallery_edit", Gallery), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadGalleries()
    super.onActivityResult(requestCode, resultCode, data)

  }
}