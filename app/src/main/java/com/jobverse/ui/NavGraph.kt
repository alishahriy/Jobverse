package com.jobverse.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jobverse.ui.screens.*
import com.jobverse.viewmodel.JobsViewModel
import com.jobverse.viewmodel.WalletViewModel

@Composable
fun AppNavGraph(
    jobsViewModel: JobsViewModel,
    walletViewModel: WalletViewModel
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                jobsViewModel = jobsViewModel,
                onNavigateToWallet = { navController.navigate("wallet") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }

        composable("wallet") {
            WalletScreen(walletViewModel = walletViewModel) {
                navController.popBackStack()
            }
        }

        composable("profile") {
            ProfileScreen {
                navController.popBackStack()
            }
        }

        composable("jobDetail/{jobId}") { backStack ->
            val id = backStack.arguments?.getString("jobId") ?: ""
            JobDetailScreen(jobId = id, jobsViewModel = jobsViewModel) {
                navController.popBackStack()
            }
        }
    }
}
