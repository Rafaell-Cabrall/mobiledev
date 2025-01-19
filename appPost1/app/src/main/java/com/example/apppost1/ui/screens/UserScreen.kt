package com.example.apppost1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppost1.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: PostViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val primaryColor = Color(0xFF40E0D0)
    val onPrimaryColor = Color.White


    fun formatDate(input: String): String {
        val clean = input.filter { it.isDigit() }
        return when (clean.length) {
            in 1..2 -> clean
            in 3..4 -> "${clean.substring(0, 2)}/${clean.substring(2)}"
            in 5..8 -> "${clean.substring(0, 2)}/${clean.substring(2, 4)}/${clean.substring(4)}"
            else -> clean.take(10)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciador de Usuários", color = onPrimaryColor) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primaryColor
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = primaryColor,
                    cursorColor = primaryColor,
                    containerColor = Color(0xFFE0F7FA)
                )
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = primaryColor,
                    cursorColor = primaryColor,
                    containerColor = Color(0xFFE0F7FA)
                )
            )
            TextField(
                value = birthDate,
                onValueChange = { birthDate = formatDate(it) },
                label = { Text("Data de Nascimento (DD/MM/AAAA)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = primaryColor,
                    cursorColor = primaryColor,
                    containerColor = Color(0xFFE0F7FA)
                )
            )
            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || birthDate.isBlank()) {
                        Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                    } else {
                        isLoading = true
                        viewModel.createUser(name, email, birthDate,
                            onSuccess = {
                                Toast.makeText(context, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
                                isLoading = false
                                name = ""
                                email = ""
                                birthDate = ""
                            },
                            onError = {
                                Toast.makeText(context, "Erro ao criar usuário!", Toast.LENGTH_SHORT).show()
                                isLoading = false
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text("Criar Usuário", color = onPrimaryColor)
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = primaryColor
                )
            } else {
                LazyColumn {
                    items(viewModel.users) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Nome: ${user.name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Email: ${user.email}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = primaryColor
                                )
                                Text(
                                    text = "Data de Nascimento: ${user.birthDate}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = primaryColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

