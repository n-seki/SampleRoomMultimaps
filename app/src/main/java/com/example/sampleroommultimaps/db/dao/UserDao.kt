package com.example.sampleroommultimaps.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sampleroommultimaps.db.entity.BankEntity
import com.example.sampleroommultimaps.db.entity.PaymentEntity
import com.example.sampleroommultimaps.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM User JOIN Bank ON User.bank_id = Bank.bank_id")
    suspend fun getUserAndBank(): Map<UserEntity, BankEntity>

    @Query("SELECT * FROM User JOIN Payment ON User.user_id = Payment.user_id")
    suspend fun getUserAndPayments(): Map<UserEntity, List<PaymentEntity>>
}