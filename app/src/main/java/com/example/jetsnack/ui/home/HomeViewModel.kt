package com.example.jetsnack.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsnack.data.repository.BillionaireRepo
import com.example.jetsnack.domain.model.request.Billionaire
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val billionaireRepo: BillionaireRepo
):ViewModel() {

    private val _state = MutableStateFlow(emptyList<Billionaire>())
    val state: StateFlow<List<Billionaire>>
    get() = _state

    init {

        viewModelScope.launch {
            val billionaires = billionaireRepo.getBillionaires()
            Log.d("billionaires", billionaires.toString())


        }
    }
}