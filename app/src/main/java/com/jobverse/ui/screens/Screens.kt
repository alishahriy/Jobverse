package com.jobverse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jobverse.model.Job
import com.jobverse.ui.components.JobCard
import com.jobverse.viewmodel.JobsViewModel
import com.jobverse.viewmodel.WalletViewModel
import com.jobverse.viewmodel.AuthState

@Composable
fun HomeScreen(jobsViewModel: JobsViewModel, onNavigateToWallet: ()->Unit, onNavigateToProfile: ()->Unit) {
    val jobs = jobsViewModel.jobs.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Jobverse") },
                actions = {
                    TextButton(onClick = onNavigateToWallet) { Text("Wallet") }
                    TextButton(onClick = onNavigateToProfile) { Text("Profile") }
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { jobsViewModel.postSampleJob() }) {
                Text("+")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(jobs.size) { i ->
                JobCard(job = jobs[i])
            }
        }
    }
}

@Composable
fun JobDetailScreen(jobId:String, jobsViewModel: JobsViewModel, onBack:()->Unit) {
    val job = jobsViewModel.jobs.collectAsState().value.find { it.id == jobId }
    Scaffold(topBar = { TopAppBar(title = { Text(job?.title ?: "جزئیات شغل") }) }) {
        Column(Modifier.padding(16.dp)) {
            Text("توضیحات: ${job?.description ?: "ندارد"}")
            Text("شهر: ${job?.city ?: ""}")
            Spacer(Modifier.height(10.dp))
            Button(onClick = onBack) { Text("بازگشت") }
        }
    }
}

@Composable
fun WalletScreen(walletViewModel: WalletViewModel, onBack:()->Unit) {
    val user = walletViewModel.currentUser.collectAsState().value
    Scaffold(topBar = {
        TopAppBar(title = { Text("Wallet") })
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("موجودی: ${user?.walletBalance ?: 0}")
            Spacer(Modifier.height(10.dp))
            Button(onClick = { walletViewModel.charge(user?.id ?: "", 50000) }) {
                Text("افزایش موجودی ۵۰ هزار تومان")
            }
            Spacer(Modifier.height(10.dp))
            Button(onClick = onBack) { Text("بازگشت") }
        }
    }
}

@Composable
fun ProfileScreen(onBack:()->Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("پروفایل کاربر") }) }) {
        Column(Modifier.padding(20.dp)) {
            Text("اینجا اطلاعات کاربر نمایش داده می‌شود.")
            Spacer(Modifier.height(10.dp))
            Button(onClick = onBack) { Text("بازگشت") }
        }
    }
}
