package com.apk.aboutrasty

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_USERNAME = "username"
        private const val COLUMN_USER_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsersTableQuery = "CREATE TABLE $TABLE_USERS ($COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USER_USERNAME TEXT, $COLUMN_USER_PASSWORD TEXT)"
        db?.execSQL(createUsersTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertUser(username: String, password: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_USER_USERNAME, username)
            put(COLUMN_USER_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(TABLE_USERS, null, values)
    }

    fun readUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USER_USERNAME = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    fun isUserAvailable(): Boolean {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM users"
        val cursor = db.rawQuery(query, null)
        var count = 0

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()

        return count > 0
    }
}