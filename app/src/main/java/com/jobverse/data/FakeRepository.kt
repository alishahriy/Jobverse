package com.jobverse.data

import com.jobverse.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

class FakeRepository : Repository {

    private val mutex = Mutex()
    private val _user = MutableStateFlow<User?>(null)
    private val _jobs = MutableStateFlow<List<Job>>(emptyList())
    private val _projects = MutableStateFlow<List<Project>>(emptyList())
    private val _packages = MutableStateFlow<List<Package>>(
        listOf(
            Package("p_basic","Basic 10 requests",20000,10,30),
            Package("p_pro","Pro 50 requests",45000,50,365),
            Package("p_year","Year 120 requests",90000,120,365)
        )
    )

    override suspend fun register(name: String, phone: String, role: UserRole): User {
        mutex.withLock {
            val u = User(UUID.randomUUID().toString(), name, phone, role, credits = 7, walletBalance = 0)
            _user.value = u
            return u
        }
    }

    override suspend fun login(phone: String): User? {
        delay(300)
        return _user.value
    }

    override fun getCurrentUser() = _user.asStateFlow()

    override suspend fun updateUser(user: User) {
        mutex.withLock { _user.value = user }
    }

    override suspend fun postJob(job: Job) {
        mutex.withLock { _jobs.value = listOf(job) + _jobs.value }
    }

    override fun listJobs() = _jobs.asStateFlow()

    override suspend fun applyToJob(userId: String, jobId: String): Boolean {
        mutex.withLock {
            val user = _user.value ?: return false
            if (user.credits <= 0) return false
            val updated = user.copy(credits = user.credits - 1)
            _user.value = updated
            _jobs.value = _jobs.value.map {
                if (it.id == jobId) it.copy(applicantsCount = it.applicantsCount + 1)
                else it
            }
            return true
        }
    }

    override suspend fun postProject(project: Project) {
        mutex.withLock { _projects.value = listOf(project) + _projects.value }
    }

    override fun listProjects() = _projects.asStateFlow()

    override suspend fun chargeWallet(userId: String, amount: Long): WalletTransaction {
        mutex.withLock {
            val user = _user.value ?: throw IllegalStateException("no user")
            val updated = user.copy(walletBalance = user.walletBalance + amount)
            _user.value = updated
            return WalletTransaction(UUID.randomUUID().toString(), userId, amount)
        }
    }

    override suspend fun withdraw(userId: String, amount: Long): WalletTransaction {
        mutex.withLock {
            val user = _user.value ?: throw IllegalStateException("no user")
            if (user.walletBalance < amount) throw IllegalStateException("insufficient")
            val updated = user.copy(walletBalance = user.walletBalance - amount)
            _user.value = updated
            return WalletTransaction(UUID.randomUUID().toString(), userId, -amount)
        }
    }

    override fun listPackages() = _packages.asStateFlow()

    override suspend fun purchasePackage(userId: String, packageId: String) {
        mutex.withLock {
            val user = _user.value ?: throw IllegalStateException("no user")
            val pack = _packages.value.first { it.id == packageId }
            val updated = user.copy(credits = user.credits + pack.credits)
            _user.value = updated
        }
    }
}
