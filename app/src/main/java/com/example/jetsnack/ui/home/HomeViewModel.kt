package com.example.jetsnack.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsnack.data.repository.BillionaireRepository
import com.example.jetsnack.domain.model.BillionaireRepo
import com.example.jetsnack.domain.model.Filter
import com.example.jetsnack.domain.model.SearchSuggestionGroup
import com.example.jetsnack.domain.model.request.Billionaire
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val billionaireRepository: BillionaireRepository
) : ViewModel() {

    private val _billionaireState = MutableStateFlow(emptyList<Billionaire>())
    val billionaireState: StateFlow<List<Billionaire>>
        get() = _billionaireState

    // billionaire results
    private val _billionaireResults = MutableStateFlow(emptyList<Billionaire>())
    val billionaireResults: StateFlow<List<Billionaire>>
        get() = _billionaireResults


    val filters = BillionaireRepo.getIndustryFilters()
    private val _filterState = MutableStateFlow(filters)
    val filterState: StateFlow<List<Filter>>
        get() = _filterState

    // sortState
    private val _sortState = MutableStateFlow(BillionaireRepo.getSortDefault())
    val sortState: MutableStateFlow<String>
        get() = _sortState

    // changeSortState
    fun changeSortState(sort: String) {
        _sortState.value = sort
        changeFilterSort(sort)
    }

    // SearchSuggestionGroup
    private val _searchSuggestion = MutableStateFlow(emptyList<String>())
    val searchSuggestion: StateFlow<List<String>>
        get() = _searchSuggestion

    init {

        viewModelScope.launch {
            try {
                val billionaires = billionaireRepository.getBillionaires()
                _billionaireState.value = billionaires
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.message!!)
            }
        }
    }

    fun changeFilter(filter: Filter) {
        _filterState.value = _filterState.value.map {
            if (it.id == filter.id) {
                if (!filter.enabled.value) {
                    it.enabled.value = true
                    getBillionairesByIndustry(it.name)
                    it
                } else {
                    it.enabled.value = false
                    getBillionaires()
                    it
                }
            } else {
                it.enabled.value = false
                it
            }
        }

    }
    // search
    fun search(search: String) {
        _billionaireResults.value = billionaireState.value.filter {
            it.personName.contains(search, true)

        }
        val topMatchBillionaire = if (_billionaireResults.value.isNotEmpty())
            _billionaireResults.value[0].personName else ""
        if (!_searchSuggestion.value.contains(topMatchBillionaire)) {
                _searchSuggestion.value = (_searchSuggestion.value + topMatchBillionaire!!)
            }
    }

    private fun changeFilterSort(filter: String) {
        deselectFilters()
        getBillionairesByFilter(filter)
    }

    private fun deselectFilters() {
        _filterState.value = _filterState.value.map {
            it.enabled.value = false
            it
        }
    }

    private fun getBillionaires() {
        viewModelScope.launch {
            try {
                val billionaires = billionaireRepository.getBillionaires()
                _billionaireState.value = billionaires
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.message!!)
            }
        }
    }

    private fun getBillionairesByFilter(filter: String) {
        viewModelScope.launch {
            try {
                val billionaires = if (filter == "Default") {
                    billionaireRepository.getBillionaires()
                } else
                    billionaireRepository.getBillionairesByFilter(filter)
                _billionaireState.value = billionaires
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.message!!)
            }
        }
    }

    // industry
    private fun getBillionairesByIndustry(industry: String) {
        viewModelScope.launch {
            try {
                val billionaires = billionaireRepository.getBillionairesByIndustry(industry)
                _billionaireState.value = billionaires
            } catch (e: Exception) {
                Log.e("HomeViewModel", e.message!!)
            }
        }
    }
}