package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieDetailsViewModel: MovieDetailViewModel, navController: NavHostController) {

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Детали фильма") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            })
    }) {
        Column(modifier = Modifier.fillMaxSize().padding(it).padding(16.dp)) {
            if (movieDetailsViewModel.movie != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Column {
                        Row {
                            Text(text = movieDetailsViewModel.movie!!.title, style = MaterialTheme.typography.headlineLarge)
                            Text(text = "${movieDetailsViewModel.movie!!.year} год", style = MaterialTheme.typography.bodyMedium)
                        }

//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Row {
//                            Text(text = "Время ", style = MaterialTheme.typography.bodyLarge)
//                            Text(text = "${movieDetailsViewModel.movie!!.duration} год", style = MaterialTheme.typography.bodyMedium)
//                        }
//
//                        Row {
//                            Text(text = "Оценка ", style = MaterialTheme.typography.bodyLarge)
//                            Text(text = "${movieDetailsViewModel.movie!!.rating} год", style = MaterialTheme.typography.bodyMedium)
//                        }

                    }
                    AsyncImage(
                        model = movieDetailsViewModel.movie!!.posterUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(300.dp)
                            .padding(top = 20.dp)
                            .clip(RoundedCornerShape(16.dp))
                        ,
                        contentDescription = "Изображение"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = movieDetailsViewModel.movie!!.description ?: "", style = MaterialTheme.typography.bodySmall)
            } else {
                Text(text = "Фильм не найден", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}