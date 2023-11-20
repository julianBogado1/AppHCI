package hci.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import hci.app.data.model.Routine
import hci.app.ui.main.MainViewModel

@Composable
fun RoutinesScreen(viewModel: MainViewModel) {

    viewModel.getRoutines() // Trigger fetching routines

    val uiState = viewModel.uiState

    val routineList = uiState.routines?.content?.map { networkRoutine ->
        Routine(
            name = networkRoutine.name ?: "",
            description = networkRoutine.detail ?: "",
            rating = networkRoutine.score ?: 0,
            duration = networkRoutine.date ?: 0,
            dUnit = "min"
        )
    } ?: emptyList()

    LazyColumn {
        items(routineList.size) { index ->
            val item = routineList[index]
            ListItemComponent(item = item)
        }
    }
}

@Composable
fun ListItemComponent(item: Routine) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFD9D9D9))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box{
                Column(
                    modifier = Modifier
                        .padding(2.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${item.name}", style = MaterialTheme.typography.titleLarge)

                    Text(text = "${item.description}", style = MaterialTheme.typography.bodyLarge)

                }
            }

            Box{
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight()
                        .background(Color(0xFFBEBEBE)),
                    verticalArrangement = Arrangement.Center
                ){

                    Text(
                        text = "${item.rating}", style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "${item.duration} ${item.dUnit}", style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}