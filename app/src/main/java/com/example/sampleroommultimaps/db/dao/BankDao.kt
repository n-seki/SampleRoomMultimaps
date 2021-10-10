package com.example.sampleroommultimaps.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.sampleroommultimaps.db.entity.BankEntity

@Dao
interface BankDao {

    @Insert
    suspend fun insertBanks(banks: List<BankEntity>)
}