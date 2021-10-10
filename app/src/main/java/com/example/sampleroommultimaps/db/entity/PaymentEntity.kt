package com.example.sampleroommultimaps.db.entity

import androidx.room.*

@Entity(
    tableName = "Payment",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"]
        )
    ],
    indices = [
        Index(
            value = ["user_id"]
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey @ColumnInfo(name = "payment_id") val id: String,
    val amount: Int,
    @ColumnInfo(name = "user_id") val userId: String
)