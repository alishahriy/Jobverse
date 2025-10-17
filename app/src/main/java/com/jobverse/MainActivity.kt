package com.jobverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jobverse.data.FakeRepository
import com.jobverse.ui.AppNavGraph
import com.jobverse.ui.theme.JobverseTheme
import com.jobverse.viewmodel.JobsViewModel
import com.jobverse.viewmodel.WalletViewModel

class MainActivity : ComponentActivity() {
    private val repo = FakeRepository()
    private val jobsViewModel = JobsViewModel(repo)
    private val walletViewModel = WalletViewModel(repo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobverseTheme {
                AppNavGraph(jobsViewModel = jobsViewModel, walletViewModel = walletViewModel)
            }
        }
    }
}
