import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NEWS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, " +
                    "$COLUMN_CONTENT TEXT, " +
                    "$COLUMN_PATH_IMAGE TEXT)"
        )

        db.execSQL(
            "CREATE TABLE $TABLE_ALUMNI (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NIM TEXT, " +
                    "$COLUMN_NAMA TEXT, " +
                    "$COLUMN_TEMPAH_LAHIR TEXT, " +
                    "$COLUMN_TANGGAL_LAHIR TEXT, " +
                    "$COLUMN_ALAMAT TEXT, " +
                    "$COLUMN_AGAMA TEXT, " +
                    "$COLUMN_TELEPON TEXT, " +
                    "$COLUMN_TAHUN_MASUK INTEGER, " +
                    "$COLUMN_TAHUN_LULUS INTEGER, " +
                    "$COLUMN_PEKERJAAN TEXT, " +
                    "$COLUMN_JABATAN TEXT)"
        )

        // Insert dummy data
        insertDummyData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NEWS")
        onCreate(db)
    }

    private fun insertDummyData(db: SQLiteDatabase) {
        val titles = listOf(
            "Tech Update: Latest Innovations",
            "Sports Highlights: This Week’s Best Moments",
            "World News: Major Events Around the Globe"
        )

        val contents = listOf(
            "Discover the latest technological innovations and breakthroughs happening in the tech world. From cutting-edge gadgets to revolutionary software, get all the updates on what’s new in technology.",
            "Catch up on the most exciting sports moments from this week. From thrilling match highlights to standout performances, find out what made the headlines in the world of sports.",
            "Stay informed about major global events that are shaping our world. From political developments to significant cultural shifts, get the latest news on what’s happening around the globe."
        )

        val imageResourceIds = listOf(
            "drawable/tech_update",
            "drawable/sports_highlights",
            "drawable/world_news"
        )

        for (i in titles.indices) {
            val values = ContentValues().apply {
                put(COLUMN_TITLE, titles[i])
                put(COLUMN_CONTENT, contents[i])
                put(COLUMN_PATH_IMAGE, imageResourceIds[i])
            }

            // Log values for debugging
            Log.d("DatabaseHelper", "Inserting data: $values")

            val rowId = db.insert(TABLE_NEWS, null, values)
            if (rowId == -1L) {
                Log.e("DatabaseHelper", "Failed to insert row for title: ${titles[i]}")
            } else {
                Log.d("DatabaseHelper", "Inserted row with ID: $rowId")
            }
        }
    }


    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "alumni.db"

        // News table
        const val TABLE_NEWS = "news"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PATH_IMAGE = "path_image"

        // Alumni table
        const val TABLE_ALUMNI = "alumni"
        const val COLUMN_NIM = "nim"
        const val COLUMN_NAMA = "nama"
        const val COLUMN_TEMPAH_LAHIR = "tempat_lahir"
        const val COLUMN_TANGGAL_LAHIR = "tanggal_lahir"
        const val COLUMN_ALAMAT = "alamat"
        const val COLUMN_AGAMA = "agama"
        const val COLUMN_TELEPON = "telepon"
        const val COLUMN_TAHUN_MASUK = "tahun_masuk"
        const val COLUMN_TAHUN_LULUS = "tahun_lulus"
        const val COLUMN_PEKERJAAN = "pekerjaan"
        const val COLUMN_JABATAN = "jabatan"
    }
}