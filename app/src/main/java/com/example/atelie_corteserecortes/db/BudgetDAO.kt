package com.example.atelie_corteserecortes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.atelie_corteserecortes.db.Budget

@Dao
interface BudgetDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGarment(garment: Budget)

    @Query("SELECT * FROM budget_data_table")
    fun showGarment(): LiveData<List<Budget>>

    @Query("DELETE FROM budget_data_table")
    suspend fun deleteAll()
}