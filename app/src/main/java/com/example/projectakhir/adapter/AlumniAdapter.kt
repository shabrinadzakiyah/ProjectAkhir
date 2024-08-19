package com.example.projectakhir.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.projectakhir.R
import com.example.projectakhir.models.Alumni

class AlumniAdapter(context: Context, private val alumniList: List<Alumni>) :
    ArrayAdapter<Alumni>(context, 0, alumniList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val alumni = getItem(position) ?: return convertView ?: View(parent.context)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_alumni, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvNim = view.findViewById<TextView>(R.id.tvNim)

        tvName.text = alumni.name
        tvNim.text = alumni.nim

        return view
    }
}