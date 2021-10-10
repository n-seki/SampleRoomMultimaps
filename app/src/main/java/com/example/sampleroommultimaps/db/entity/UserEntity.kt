package com.example.sampleroommultimaps.db.entity

import androidx.room.*

@Entity(
    tableName = "User",
    foreignKeys = [
        ForeignKey(
            entity = BankEntity::class,
            parentColumns = ["bank_id"],
            childColumns = ["bank_id"]
        )
    ],
    indices = [
        Index(
            value = ["bank_id"]
        )
    ]
)
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "user_id") val id: String,
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "bank_id") val bankId: String
)