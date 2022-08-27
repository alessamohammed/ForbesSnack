package com.example.jetsnack.ui.home

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsnack.data.repository.BillionaireRepository
import com.example.jetsnack.data.repository.BillionaireRepository_impl
import com.example.jetsnack.domain.model.BillionaireRepo
import com.example.jetsnack.domain.model.Filter
import com.example.jetsnack.domain.model.request.Billionaire
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val billionaireRepository: BillionaireRepository
):ViewModel() {

    private val _billionaireState = MutableStateFlow(emptyList<Billionaire>())
    val billionaireState: StateFlow<List<Billionaire>>
    get() = _billionaireState

    val filters = BillionaireRepo.getFilters()
    private val _filterState = MutableStateFlow(filters)
    val filterState: StateFlow<List<Filter>>
        get() = _filterState

     fun changeFilter(filter: Filter) {
        _filterState.value = _filterState.value.map {
            if (it.id == filter.id) {
                if (!filter.enabled.value) {

                    it.enabled.value = true
                    getBillionairesByFilter(it)
                    it
                } else {
                    it.enabled.value = false
                    it
                }
            } else {
                it.enabled.value = false
                it
            }
        }

    }
    init {

        viewModelScope.launch {
        try {
            val billionaires = billionaireRepository.getBillionaires()
            _billionaireState.value = billionaires
        }
        catch (e: Exception) {
            Log.e("HomeViewModel", e.message!!)
        }
        }
    }

    suspend fun getBillionaires() {
        viewModelScope.launch {
            try {
                val billionaires = billionaireRepository.getBillionaires()
                _billionaireState.value = billionaires
            }
            catch (e: Exception) {
                Log.e("HomeViewModel", e.message!!)
            }
        }
    }

    fun getBillionairesByFilter(filter: Filter) {
        viewModelScope.launch {
            try {
                val billionaires = billionaireRepository.getBillionairesByFilter(filter.name)
                _billionaireState.value = billionaires
            }
            catch (e: Exception) {
                Log.e("HomeViewModel", e.message!!)
            }
        }
    }
}