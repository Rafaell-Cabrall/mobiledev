package com.example.investidor2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.investidor2.ui.view.InvestidorScreen
import com.example.investidor2.viewmodel.InvestimentosViewModel
import android.Manifest
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        } else {
            Log.d("MainActivity", "Permissão para notificações não é necessária nesta versão.")
        }

        setContent {
            val viewModel: InvestimentosViewModel = viewModel()
            InvestidorScreen(viewModel)
        }
    }
}
