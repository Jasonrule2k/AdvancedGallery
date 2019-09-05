package org.wit.gallery.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import org.wit.gallery.R

class GallerySpinner : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var display : TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gallery)

    option = findViewById(R.id.spinner) as Spinner
    display = findViewById(R.id.picture) as TextView

    var options = arrayOf("Choice 1", "Choice 2", "Choice 3")

    option.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,options)

    option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {
        display.text = "Please Choose an Option"
      }

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        display.text = options.get(position)
      }
    }
      
    }

  }

