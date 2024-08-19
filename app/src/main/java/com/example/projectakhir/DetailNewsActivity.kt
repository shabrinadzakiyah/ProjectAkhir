package com.example.projectakhir

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class DetailNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val imageView = findViewById<ImageView>(R.id.detail_image)

        val intent = intent
        val title = intent.getStringExtra("EXTRA_TITLE")
        val content = intent.getStringExtra("EXTRA_CONTENT")
        val imagePath = intent.getStringExtra("EXTRA_IMAGE_PATH")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val titleTextView = findViewById<TextView>(R.id.detail_title)
        val contentTextView = findViewById<TextView>(R.id.detail_content)

        titleTextView.text = title
        contentTextView.text = content

        // Load the image from drawable resource
        val imageResource = when (imagePath) {
            "drawable/tech_update" -> R.drawable.tech_update
            "drawable/sports_highlights" -> R.drawable.sports_highlights
            "drawable/world_news" -> R.drawable.world_news
            else -> R.drawable.ic_news
        }
        imageView.setImageResource(imageResource)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}