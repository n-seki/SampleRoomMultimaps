package com.example.sampleroommultimaps.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.sampleroommultimaps.db.entity.PaymentEntity

@Dao
interface PaymentDao {

    @Insert
    suspend fun insertPayments(payments: List<PaymentEntity>)
}