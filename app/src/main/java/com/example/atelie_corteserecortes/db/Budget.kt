package com.example.atelie_corteserecortes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_data_table")
data class Budget(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Int,
    @ColumnInfo(name = "garment")
    var garment: String,
    @ColumnInfo(name = "description")
    var description: String
) {
}