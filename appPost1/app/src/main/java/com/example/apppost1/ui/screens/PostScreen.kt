package com.example.apppost1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apppost1.data.models.Post
import com.example.apppost1.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var editingPost by remember { mutableStateOf<Post?>(null) }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        isLoading = true
        try {
            viewModel.featchPost()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gerenciador de Posts") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )


            TextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Conteúdo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )


            Button(
                onClick = {

                    if (title.trim().isEmpty() || content.trim().isEmpty()) {
                        Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                    } else {
                        isLoading = true
                        viewModel.createPost(
                            title.trim(), content.trim(),
                            onSuccess = {
                                Toast.makeText(context, "Post criado com sucesso", Toast.LENGTH_SHORT).show()
                                isLoading = false
                            },
                            onError = {
                                Toast.makeText(context, "Erro ao criar o post!", Toast.LENGTH_SHORT).show()
                                isLoading = false
                            }
                        )

                        title = ""
                        content = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Criar Post")
            }

            Spacer(modifier = Modifier.height(16.dp))


            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(viewModel.posts) { post ->
                        PostItem(
                            post = post,
                            onDelete = { viewModel.deletePost(it) },
                            onEdit = { editingPost = it }
                        )
                    }
                }
            }
        }
    }


    if (editingPost != null) {
        AlertDialog(
            onDismissRequest = { editingPost = null },
            title = { Text("Editar Post") },
            text = {
                Column {
                    TextField(
                        value = editingPost!!.title,
                        onValueChange = { newTitle -> editingPost = editingPost!!.copy(title = newTitle) },
                        label = { Text("Título") },
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = editingPost!!.content,
                        onValueChange = { newContent -> editingPost = editingPost!!.copy(content = newContent) },
                        label = { Text("Conteúdo") },
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.updatePost(
                            editingPost!!.id,
                            editingPost!!.title,
                            editingPost!!.content
                        )
                        editingPost = null
                    }
                ) {
                    Text("Salvar")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingPost = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
