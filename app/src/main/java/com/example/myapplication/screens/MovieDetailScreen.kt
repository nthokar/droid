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
import androidx.compose.material3.Button
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
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieDetailsViewModel: MovieDetailViewModel, navController: NavHostController) {
    ConstraintLayout {

        // Create references for the composables to constrain
        val (topBar, movieTitle, movieYear, poster, description) = createRefs()

        TopAppBar(modifier = Modifier.constrainAs(topBar) { top.linkTo(parent.top) }, title = { Text(text = "Детали фильма") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            })

        Text(modifier = Modifier.constrainAs(movieTitle) {
            top.linkTo(topBar.bottom, margin = 16.dp)
            absoluteLeft.linkTo(parent.absoluteLeft)
                                                         },
            text = movieDetailsViewModel.movie!!.title, style = MaterialTheme.typography.headlineLarge)
        Text(modifier = Modifier.constrainAs(movieYear) {
            absoluteLeft.linkTo(movieTitle.absoluteRight)
            top.linkTo(movieTitle.top)},
                text = "${movieDetailsViewModel.movie!!.year} год", style = MaterialTheme.typography.bodyMedium)

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

        AsyncImage(
            model = movieDetailsViewModel.movie!!.posterUrl,
            modifier = Modifier
                .size(300.dp)
                .constrainAs(poster) {
                    absoluteRight.linkTo(parent.absoluteRight, margin = -50.dp)
                    top.linkTo(topBar.bottom, margin = 16.dp)
                    }
            ,
            contentDescription = "Изображение"
        )


        Text(modifier = Modifier.constrainAs(description) {
            top.linkTo(movieTitle.bottom, margin = 16.dp)
            absoluteLeft.linkTo(parent.absoluteLeft)
        },
            text = movieDetailsViewModel.movie!!.description ?: "", style = MaterialTheme.typography.bodySmall)
    }

}