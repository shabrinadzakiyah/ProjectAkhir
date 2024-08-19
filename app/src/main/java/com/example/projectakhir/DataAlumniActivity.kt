package com.example.projectakhir
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectakhir.adapter.AlumniAdapter
import com.example.projectakhir.database.AlumniDAO
import com.example.projectakhir.models.Alumni

class DataAlumniActivity : AppCompatActivity() {

    private lateinit var alumniDAO: AlumniDAO
    private lateinit var listViewAlumni: ListView
    private lateinit var toolbar: Toolbar

    override fun onResume() {
        loadAlumniData()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_alumni)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listViewAlumni = findViewById(R.id.listViewAlumni)
        alumniDAO = AlumniDAO(this)



        listViewAlumni.setOnItemClickListener { _, _, position, _ ->
            val selectedAlumni = listViewAlumni.getItemAtPosition(position) as Alumni
            showOptionsDialog(selectedAlumni)
        }

        listViewAlumni.setOnItemLongClickListener { _, _, position, _ ->
            val selectedAlumni = listViewAlumni.getItemAtPosition(position) as Alumni
            showOptionsDialog(selectedAlumni)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun loadAlumniData() {
        val alumniList = mutableListOf<Alumni>()
        val cursor = alumniDAO.getAllAlumni()

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val nim = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NIM))
                val nama = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAMA))

                val alumni = Alumni(id, nim, nama)
                alumniList.add(alumni)
            }
        }

        val adapter = AlumniAdapter(this, alumniList)
        listViewAlumni.adapter = adapter
    }

    private fun showOptionsDialog(alumni: Alumni) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(this)
            .setTitle("Select Action")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editAlumni(alumni)
                    1 -> deleteAlumni(alumni)
                }
            }
            .create()
            .show()
    }

    private fun editAlumni(alumni: Alumni) {
        // Launch EditAlumniActivity with the selected alumni ID
        // For example:
        val intent = Intent(this, EditAlumniActivity::class.java)
        intent.putExtra("ALUMNI_ID", alumni.id)
        startActivity(intent)
    }

    private fun deleteAlumni(alumni: Alumni) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete ${alumni.name}?")
            .setPositiveButton("Yes") { _, _ ->
                val rowsDeleted = alumniDAO.deleteAlumni(alumni.id)
                if (rowsDeleted > 0) {
                    Toast.makeText(this, "Alumni deleted", Toast.LENGTH_SHORT).show()
                    loadAlumniData()  // Refresh the list after deletion
                } else {
                    Toast.makeText(this, "Failed to delete alumni", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .create()
            .show()
    }
}