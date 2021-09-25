package com.example.atelie_corteserecortes.db

import com.example.atelie_corteserecortes.db.Budget
import com.example.atelie_corteserecortes.db.BudgetDAO

class Repository (private val dao: BudgetDAO) {
    val garmentList = dao.showGarment()

    suspend fun insert(garment: Budget) {
        dao.insertGarment(garment)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}