package com.example.projectakhir.database
import DatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class NewsDAO (context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getAllNews(): Cursor {
        val db = dbHelper.readableDatabase
        return db.query(
            DatabaseHelper.TABLE_NEWS,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}

class AlumniDAO(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    // Add a new alumni record
    fun addAlumni(
        nim: String, nama: String, tempatLahir: String, tanggalLahir: String,
        alamat: String, agama: String, telepon: String, tahunMasuk: Int,
        tahunLulus: Int, pekerjaan: String, jabatan: String
    ): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NIM, nim)
            put(DatabaseHelper.COLUMN_NAMA, nama)
            put(DatabaseHelper.COLUMN_TEMPAH_LAHIR, tempatLahir)
            put(DatabaseHelper.COLUMN_TANGGAL_LAHIR, tanggalLahir)
            put(DatabaseHelper.COLUMN_ALAMAT, alamat)
            put(DatabaseHelper.COLUMN_AGAMA, agama)
            put(DatabaseHelper.COLUMN_TELEPON, telepon)
            put(DatabaseHelper.COLUMN_TAHUN_MASUK, tahunMasuk)
            put(DatabaseHelper.COLUMN_TAHUN_LULUS, tahunLulus)
            put(DatabaseHelper.COLUMN_PEKERJAAN, pekerjaan)
            put(DatabaseHelper.COLUMN_JABATAN, jabatan)
        }
        val rowId = db.insert(DatabaseHelper.TABLE_ALUMNI, null, values)
        if (rowId == -1L) {
            Log.e("AlumniDAO", "Failed to insert alumni with NIM: $nim")
        } else {
            Log.d("AlumniDAO", "Inserted alumni with ID: $rowId")
        }
        return rowId
    }

    // Update an existing alumni record by ID
    fun updateAlumni(
        id: Long, nim: String, nama: String, tempatLahir: String, tanggalLahir: String,
        alamat: String, agama: String, telepon: String, tahunMasuk: Int,
        tahunLulus: Int, pekerjaan: String, jabatan: String
    ): Int {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NIM, nim)
            put(DatabaseHelper.COLUMN_NAMA, nama)
            put(DatabaseHelper.COLUMN_TEMPAH_LAHIR, tempatLahir)
            put(DatabaseHelper.COLUMN_TANGGAL_LAHIR, tanggalLahir)
            put(DatabaseHelper.COLUMN_ALAMAT, alamat)
            put(DatabaseHelper.COLUMN_AGAMA, agama)
            put(DatabaseHelper.COLUMN_TELEPON, telepon)
            put(DatabaseHelper.COLUMN_TAHUN_MASUK, tahunMasuk)
            put(DatabaseHelper.COLUMN_TAHUN_LULUS, tahunLulus)
            put(DatabaseHelper.COLUMN_PEKERJAAN, pekerjaan)
            put(DatabaseHelper.COLUMN_JABATAN, jabatan)
        }
        val rowsAffected = db.update(
            DatabaseHelper.TABLE_ALUMNI,
            values,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        if (rowsAffected == 0) {
            Log.e("AlumniDAO", "Failed to update alumni with ID: $id")
        } else {
            Log.d("AlumniDAO", "Updated alumni with ID: $id")
        }
        return rowsAffected
    }

    // Delete an alumni record by ID
    fun deleteAlumni(id: Long): Int {
        val rowsAffected = db.delete(
            DatabaseHelper.TABLE_ALUMNI,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        if (rowsAffected == 0) {
            Log.e("AlumniDAO", "Failed to delete alumni with ID: $id")
        } else {
            Log.d("AlumniDAO", "Deleted alumni with ID: $id")
        }
        return rowsAffected
    }

    // Get an alumni record by ID
    fun getAlumniById(id: Long): Cursor? {
        return db.query(
            DatabaseHelper.TABLE_ALUMNI,
            null,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
    }

    // Get all alumni records
    fun getAllAlumni(): Cursor? {
        return db.query(
            DatabaseHelper.TABLE_ALUMNI,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}
