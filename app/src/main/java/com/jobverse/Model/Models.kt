package com.jobverse.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

sealed class UserRole { object JobSeeker: UserRole(); object Employer: UserRole() }

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val role: UserRole = UserRole.JobSeeker,
    val credits: Int = 7,
    val walletBalance: Long = 0L
)

enum class JobType { FullTime, PartTime, Project, MicroTask, TeamWork }

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val city: String,
    val employerId: String,
    val jobType: JobType,
    val salaryMin: Long?,
    val salaryMax: Long?,
    val createdAt: Instant = Clock.System.now(),
    var applicantsCount: Int = 0
)

data class Project(
    val id:String,
    val title:String,
    val description:String,
    val budget:Long,
    val employerId:String,
    val createdAt: Instant = Clock.System.now()
)

data class Package(
    val id:String,
    val title:String,
    val price:Long,
    val credits:Int,
    val durationDays:Int
)

data class WalletTransaction(
    val id:String,
    val userId:String,
    val amount:Long,
    val createdAt: Instant = Clock.System.now(),
    val note:String = ""
)
