package com.jobverse.data

import com.jobverse.model.*
import kotlinx.coroutines.flow.Flow

interface Repository {
    // Auth
    suspend fun register(name:String, phone:String, role:UserRole): User
    suspend fun login(phone:String): User?

    // Users
    fun getCurrentUser(): Flow<User?>
    suspend fun updateUser(user:User)

    // Jobs
    suspend fun postJob(job:Job)
    fun listJobs(): Flow<List<Job>>
    suspend fun applyToJob(userId:String, jobId:String): Boolean

    // Projects
    suspend fun postProject(project: Project)
    fun listProjects(): Flow<List<Project>>

    // Wallet
    suspend fun chargeWallet(userId:String, amount:Long): WalletTransaction
    suspend fun withdraw(userId:String, amount:Long): WalletTransaction

    // Packages
    fun listPackages(): Flow<List<Package>>
    suspend fun purchasePackage(userId:String, packageId:String)
}
