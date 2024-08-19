package com.example.projectakhir

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projectakhir.database.AlumniDAO
import java.util.Calendar

class EditAlumniActivity : AppCompatActivity() {

    // Views
    private lateinit var etNim: EditText
    private lateinit var etNamaAlumni: EditText
    private lateinit var etTempatLahir: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etAgama: EditText
    private lateinit var etTelepon: EditText
    private lateinit var etTahunMasuk: EditText
    private lateinit var etTahunLulus: EditText
    private lateinit var etPekerjaan: EditText
    private lateinit var etJabatan: EditText
    private lateinit var btnSubmit: Button

    private lateinit var alumniDAO: AlumniDAO
    private var alumniId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_alumni)

        alumniDAO = AlumniDAO(this)

        // Set up padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()

        etTanggalLahir.setOnClickListener {
            showDatePickerDialog(etTanggalLahir)
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnSubmit.setOnClickListener {
            handleFormSubmission()
        }

        // Get alumni ID from intent and load data
        alumniId = intent.getLongExtra("ALUMNI_ID", -1L)
        if (alumniId != -1L) {
            loadAlumniData(alumniId)
        }
    }

    private fun initializeViews() {
        etNim = findViewById(R.id.etNim)
        etNamaAlumni = findViewById(R.id.etNamaAlumni)
        etTempatLahir = findViewById(R.id.etTempatLahir)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        etAlamat = findViewById(R.id.etAlamat)
        etAgama = findViewById(R.id.etAgama)
        etTelepon = findViewById(R.id.etTelepon)
        etTahunMasuk = findViewById(R.id.etTahunMasuk)
        etTahunLulus = findViewById(R.id.etTahunLulus)
        etPekerjaan = findViewById(R.id.etPekerjaan)
        etJabatan = findViewById(R.id.etJabatan)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(date)
            }, year, month, day)

        datePickerDialog.show()
    }

    private fun handleFormSubmission() {
        // Collect form data
        val nim = etNim.text.toString()
        val namaAlumni = etNamaAlumni.text.toString()
        val tempatLahir = etTempatLahir.text.toString()
        val tanggalLahir = etTanggalLahir.text.toString()
        val alamat = etAlamat.text.toString()
        val agama = etAgama.text.toString()
        val telepon = etTelepon.text.toString()
        val tahunMasuk = etTahunMasuk.text.toString().toIntOrNull() ?: 0
        val tahunLulus = etTahunLulus.text.toString().toIntOrNull() ?: 0
        val pekerjaan = etPekerjaan.text.toString()
        val jabatan = etJabatan.text.toString()

        if (nim.isBlank() || namaAlumni.isBlank() || tempatLahir.isBlank() || tanggalLahir.isBlank() ||
            alamat.isBlank() || agama.isBlank() || telepon.isBlank() || tahunMasuk == 0 ||
            tahunLulus == 0 || pekerjaan.isBlank() || jabatan.isBlank()) {

            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Update alumni record
        val updated = alumniDAO.updateAlumni(
            alumniId, nim, namaAlumni, tempatLahir, tanggalLahir, alamat, agama, telepon, tahunMasuk, tahunLulus, pekerjaan, jabatan
        )

        if (updated > 0) {
            Toast.makeText(this, "Alumni updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update alumni", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadAlumniData(id: Long) {
        val cursor = alumniDAO.getAlumniById(id)

        cursor?.use {
            if (it.moveToFirst()) {
                val nim = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NIM))
                val namaAlumni = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAMA))
                val tempatLahir = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TEMPAH_LAHIR))
                val tanggalLahir = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TANGGAL_LAHIR))
                val alamat = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ALAMAT))
                val agama = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AGAMA))
                val telepon = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEPON))
                val tahunMasuk = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TAHUN_MASUK))
                val tahunLulus = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TAHUN_LULUS))
                val pekerjaan = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PEKERJAAN))
                val jabatan = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_JABATAN))

                etNim.setText(nim)
                etNamaAlumni.setText(namaAlumni)
                etTempatLahir.setText(tempatLahir)
                etTanggalLahir.setText(tanggalLahir)
                etAlamat.setText(alamat)
                etAgama.setText(agama)
                etTelepon.setText(telepon)
                etTahunMasuk.setText(tahunMasuk.toString())
                etTahunLulus.setText(tahunLulus.toString())
                etPekerjaan.setText(pekerjaan)
                etJabatan.setText(jabatan)
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}