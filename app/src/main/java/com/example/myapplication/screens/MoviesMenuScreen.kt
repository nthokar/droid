import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.data.Movie
import com.example.myapplication.screens.MoviesViewModel
import kotlinx.coroutines.delay

@Composable
fun MoviesMenuScreen(navigator: NavHostController,
                     viewModel: MoviesViewModel
) {
    val scrollPosition by rememberSaveable { mutableIntStateOf(viewModel.scrollPosition) }

    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollPosition
    )


    Column(modifier = Modifier.fillMaxSize()) {

        val loadMore = remember {
            derivedStateOf {
                val layoutInfo = scrollState.layoutInfo
                val totalItemsNumber = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                lastVisibleItemIndex > (totalItemsNumber - 2)
            }
        }

        Text(
            text = "Список фильмов",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        )


        LaunchedEffect(scrollState) {
            snapshotFlow {
                scrollState.firstVisibleItemIndex
            }
                .collect{ index ->
                    viewModel.scrollPosition = index
                }
        }

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {


            items(viewModel.movies) { movie ->
                MovieItem(movie = movie, onClick = {
                    // Переходим к экрану с деталями фильма
                    navigator.navigate("movie_detail/${movie.id}")
                })
            }

            // Когда пользователь достигает конца списка, добавляются новые фильмы
            item {
                if (loadMore.value) {
                    LaunchedEffect(Unit) {
                        delay(1000L) // Имитация задержки загрузки данных
                        for (i in viewModel.movies.size until viewModel.movies.size + 10) {
                            viewModel.movies.add(Movie(id = i, title = "Фильм $i", year = 2023 + i, posterUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/9784475/0c67265b-6631-4e25-b89c-3ddf4e5a1ee7/1920x"))
                        }
                    }
                }

                Box(Modifier.height(56.dp))
            }
        }
    }


}


@Composable
private fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Box(Modifier
        .fillMaxWidth()
        .background(color = Color.LightGray)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
//                .padding(horizontal = 6.dp, vertical = 2.dp),
            ) {
            AsyncImage(
                model = movie.posterUrl,
                modifier = Modifier
                    .size(120.dp)
                ,
                contentDescription = "Изображение"
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
                Text(text = "${movie.year} год", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.DarkGray)
        )
    }
}

