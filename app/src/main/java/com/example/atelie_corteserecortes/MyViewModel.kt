package com.example.atelie_corteserecortes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.atelie_corteserecortes.db.Budget
import com.example.atelie_corteserecortes.db.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MyViewModel(private val repository: Repository) : ViewModel() {

    val budget = repository.garmentList
    private var budgetUpdateDelete: Budget?

    val inputGarment = MutableLiveData<String?>()
    val inputNumber = MutableLiveData<String?>()

    val onClear = MutableLiveData<Boolean>()

    val saveUpdateBtnTxt = MutableLiveData<String>()
    val clearDeleteBtnTxt = MutableLiveData<String>()

    init {
        budgetUpdateDelete = null
        saveUpdateBtnTxt.value = "Enviar"
        clearDeleteBtnTxt.value = "Limpar"
        onClear.value = false
    }

    fun insert(budget: Budget): Job =
        viewModelScope.launch {
            repository.insert(budget)
        }

    fun save() {
        val garment = inputGarment.value!!
        val number = inputNumber.value!!

        insert(Budget(0, garment, number))
        inputGarment.value = null
        inputNumber.value = null
    }

    fun clearAll(): Job =
        viewModelScope.launch {
            repository.deleteAll()
        }

    fun clearOrDelete() {
        setOnClearState(true)
    }

    fun setOnClearState(state: Boolean){
        onClear.value = state
    }
}

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel Desconhecida")
    }
}