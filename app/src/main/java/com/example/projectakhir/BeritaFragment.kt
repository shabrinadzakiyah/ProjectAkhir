package com.example.projectakhir

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectakhir.database.NewsDAO

/**
 * A fragment representing a list of Items.
 */
class BeritaFragment : Fragment() {

    private var columnCount = 1
    private lateinit var newsDAO: NewsDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        newsDAO = NewsDAO(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_berita_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyBeritaRecyclerViewAdapter(getNewsFromDatabase())
            }
        }
        return view
    }

    private fun getNewsFromDatabase(): List<NewsItem> {
        val newsList = mutableListOf<NewsItem>()
        val cursor: Cursor = newsDAO.getAllNews()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE))
                val content = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_CONTENT))
                val pathImage = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_PATH_IMAGE))
                newsList.add(NewsItem(id, title, content, pathImage))
            }
            close()
        }
        Log.d("BeritaFragment", "getNewsFromDatabase: $newsList")
        return newsList
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            BeritaFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

data class NewsItem(val id: Long, val title: String, val content: String, val pathImage: String)