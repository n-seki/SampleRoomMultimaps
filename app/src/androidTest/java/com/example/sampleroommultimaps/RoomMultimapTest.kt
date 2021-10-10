package com.example.sampleroommultimaps

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sampleroommultimaps.db.*
import com.example.sampleroommultimaps.db.dao.BankDao
import com.example.sampleroommultimaps.db.dao.PaymentDao
import com.example.sampleroommultimaps.db.dao.UserDao
import com.example.sampleroommultimaps.db.entity.BankEntity
import com.example.sampleroommultimaps.db.entity.PaymentEntity
import com.example.sampleroommultimaps.db.entity.UserEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomMultimapTest {

    private lateinit var db: AppDatabase

    private lateinit var bankDao: BankDao
    private lateinit var paymentDao: PaymentDao
    private lateinit var userDao: UserDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        bankDao = db.bankDao()
        paymentDao = db.paymentDao()
        userDao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun multimap_one_to_one_relation() {
        runBlocking {
            // before
            val bank1 = BankEntity("bank_id_1", "AAA")
            val bank2 = BankEntity("bank_id_2", "BBB")
            val banks = listOf(bank1, bank2)

            val john = UserEntity("user_id_1", "John", "bank_id_1")
            val bob = UserEntity("user_id_2", "Bob", "bank_id_2")
            val andy = UserEntity("user_id_3", "Andy", "bank_id_2")
            val users = listOf(john, bob, andy)

            // during
            bankDao.insertBanks(banks)
            userDao.insertUsers(users)

            // after
            val userToBank = userDao.getUserAndBank()

            val expected = mapOf(
                john to bank1,
                bob to bank2,
                andy to bank2
            )

            assertThat(userToBank).containsExactlyEntriesIn(expected)
        }
    }

    @Test
    fun multimap_one_to_many_relation() {
        runBlocking {
            // after
            val bank1 = BankEntity("bank_id_1", "AAA")
            val bank2 = BankEntity("bank_id_2", "BBB")
            val banks = listOf(bank1, bank2)

            val john = UserEntity("user_id_1", "John", "bank_id_1")
            val bob = UserEntity("user_id_2", "Bob", "bank_id_2")
            val andy = UserEntity("user_id_3", "Andy", "bank_id_2")
            val users = listOf(john, bob, andy)

            val payment1 = PaymentEntity("payment_id_1", 100, "user_id_1")
            val payment2 = PaymentEntity("payment_id_2", 100, "user_id_2")
            val payment3 = PaymentEntity("payment_id_3", 200, "user_id_2")
            val payment4 = PaymentEntity("payment_id_4", 100, "user_id_3")
            val payment5 = PaymentEntity("payment_id_5", 200, "user_id_3")
            val payments = listOf(payment1, payment2, payment3, payment4, payment5)

            // during
            bankDao.insertBanks(banks)
            userDao.insertUsers(users)
            paymentDao.insertPayments(payments)

            // after
            val userToPayment = userDao.getUserAndPayments()

            val expected = mapOf(
                john to listOf(payment1),
                bob to listOf(payment2, payment3),
                andy to listOf(payment4, payment5)
            )

            assertThat(userToPayment).containsExactlyEntriesIn(expected)
        }
    }
}