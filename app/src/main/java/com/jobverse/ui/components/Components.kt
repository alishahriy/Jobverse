package com.jobverse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jobverse.model.Job

@Composable
fun AppTopBar(title:String) {
    TopAppBar(title = { Text(title) })
}

@Composable
fun JobCard(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { },
        elevation = 4.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(job.title, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(4.dp))
            Text(job.description, style = MaterialTheme.typography.body2)
            Spacer(Modifier.height(4.dp))
            Text("شهر: ${job.city}")
            Text("درخواست‌ها: ${job.applicantsCount}")
        }
    }
}
