package com.jobverse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobverse.data.FakeRepository
import com.jobverse.data.Repository
import com.jobverse.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class AuthViewModel(private val repo: Repository = FakeRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState

    fun register(name:String, phone:String, role:UserRole) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            val u = repo.register(name, phone, role)
            _uiState.value = AuthState.LoggedIn(u)
        }
    }

    fun login(phone:String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            val u = repo.login(phone)
            if (u != null) _uiState.value = AuthState.LoggedIn(u)
            else _uiState.value = AuthState.Idle
        }
    }
}

sealed class AuthState {
    object Idle: AuthState()
    object Loading: AuthState()
    data class LoggedIn(val user: User): AuthState()
}

class JobsViewModel(private val repo: Repository = FakeRepository()) : ViewModel() {
    val jobs = repo.listJobs()

    fun postSampleJob() {
        viewModelScope.launch {
            repo.postJob(Job(UUID.randomUUID().toString(),
                "میوه‌چینی - کار گروهی",
                "نیاز به کارگر برای میوه‌چینی به مدت ۵ روز",
                "گرگان",
                "emp-1",
                JobType.TeamWork,
                150000,
                250000))
        }
    }

    fun apply(userId:String, jobId:String, onResult:(Boolean)->Unit) {
        viewModelScope.launch {
            val ok = repo.applyToJob(userId, jobId)
            onResult(ok)
        }
    }
}

class WalletViewModel(private val repo: Repository = FakeRepository()): ViewModel() {
    val currentUser = repo.getCurrentUser()

    fun charge(userId:String, amount:Long) {
        viewModelScope.launch { repo.chargeWallet(userId, amount) }
    }

    fun purchasePackage(userId:String, packageId:String) {
        viewModelScope.launch { repo.purchasePackage(userId, packageId) }
    }
}
