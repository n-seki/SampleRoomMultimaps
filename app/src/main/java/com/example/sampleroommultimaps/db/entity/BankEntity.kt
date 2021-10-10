package com.example.sampleroommultimaps.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Bank"
)
data class BankEntity(
    @PrimaryKey @ColumnInfo(name = "bank_id") val id: String,
    val name: String
)