package com.example.msgapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.msgapp2.data.local.database.AppDataBase
import com.example.msgapp2.repository.MessageRepository
import com.example.msgapp2.ui.theme.MsgApp2Theme
import com.example.msgapp2.ui.view.MessageApp
import com.example.msgapp2.viewmodel.MessageViewModel
import com.example.msgapp2.viewmodel.MessageViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            name = "messages-db"
        ).fallbackToDestructiveMigration().build()

        val repository = MessageRepository(db.messageDao())

        setContent {
            MsgApp2Theme {
                val viewModel: MessageViewModel =
                    viewModel(factory = MessageViewModelFactory(repository))
                MessageApp(viewModel)
            }
        }
    }
}
