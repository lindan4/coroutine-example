package com.example.coroutine_example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.coroutine_example.viewmodel.MyViewModel
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.coroutine_example.data.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineDemoScreen(myViewModel = MyViewModel())
        }
    }
}

@Composable
fun CoroutineDemoScreen(myViewModel: MyViewModel) {

    var data by remember { mutableStateOf("Click a button to fetch data") }
    val composableScope = rememberCoroutineScope()

    val context = LocalContext.current
    val activity = context as? Activity  // Get Activity context if possible



    val viewModelData by myViewModel.data.collectAsState()

    LaunchedEffect(viewModelData) {
        if (viewModelData.isNotEmpty() && data != viewModelData) {
            data = viewModelData
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = data, style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            myViewModel.fetchData()
        }) {
            Text("Fetch Data with ViewModel")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            GlobalScope.launch {
                data = "Loading..."
                val result = Repository.fetchData()
                data = "$result via GlobalScope"
            }
        }) {
            Text("Fetch Data from global scope")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            (activity as? LifecycleOwner)?.lifecycleScope?.launch {
                data = "Loading..."
                val result = Repository.fetchData()
                data = "$result from lifecycle scope"
            }
        }) {
            Text("Fetch Data from lifecycle scope")
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            composableScope.launch {
                data = "Loading..."
                val result = Repository.fetchData()
                data = "$result from Composable scope"
            }
        }) {
            Text("Fetch Data from Composable scope")
        }

    }
}

