package com.example.sampleroommultimaps.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sampleroommultimaps.db.dao.BankDao
import com.example.sampleroommultimaps.db.dao.PaymentDao
import com.example.sampleroommultimaps.db.dao.UserDao
import com.example.sampleroommultimaps.db.entity.BankEntity
import com.example.sampleroommultimaps.db.entity.PaymentEntity
import com.example.sampleroommultimaps.db.entity.UserEntity

@Database(
    version = 1,
    entities = [
        BankEntity::class,
        PaymentEntity::class,
        UserEntity::class
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bankDao(): BankDao
    abstract fun paymentDao(): PaymentDao
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: RoomDatabase? = null

        fun getInstance(context: Context): RoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): RoomDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-db"
            ).build()
        }
    }
}