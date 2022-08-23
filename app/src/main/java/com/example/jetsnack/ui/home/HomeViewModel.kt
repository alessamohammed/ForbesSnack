package com.example.jetsnack.ui.home

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsnack.data.repository.BillionaireRepository
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

    init {

        viewModelScope.launch {
            val billionaires = billionaireRepository.getBillionaires()
            _billionaireState.value = billionaires

        }
    }

    fun getBillionaires() {
        viewModelScope.launch {
            val billionaires = billionaireRepository.getBillionaires()
            _billionaireState.value = billionaires
        }
    }
}