package hci.app.Composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color


data class Rutina(
    val name: String,
    val description: String,
    val rating: Int,
    val duration: Int,
    val dUnit: String,
    val cicles: List<Cicle>
)

data class Cicle(
    val name: String,
    val number: Int,
    val rep: Int,
    val ejs: List<Ejercicio>
)

data class Ejercicio(
    val name: String,
    val description: String,
    val series: Int,
    val duration: Int,
    val dUnit: String
)

@Composable
fun MyListScreen() {
    val excicle =listOf(
        Cicle(
            name = "Ejercitación",
            number = 1,
            rep = 3,
            ejs = listOf(
                Ejercicio("Squats", "Leg workout", 3, 30, "s"),
                Ejercicio("Push-ups", "Upper body workout", 3, 20, "s"),
            )
        ))

    val rutineList = remember {
        listOf(
            Rutina("Rutina 1", "Desc 1", 2, 12, "min",excicle),
            Rutina("Rutina 2", "Desc 2", 3, 9, "min",excicle),
            Rutina("Rutina 3", "Desc 3", 2, 15, "min",excicle),
            Rutina("Rutina 1", "Desc 1", 2, 12, "min",excicle),
            Rutina("Rutina 2", "Desc 2", 3, 9, "min",excicle),
            Rutina("Rutina 3", "Desc 3", 2, 15, "min",excicle),
            Rutina("Rutina 1", "Desc 1", 2, 12, "min",excicle),
            Rutina("Rutina 2", "Desc 2", 3, 9, "min",excicle),
            Rutina("Rutina 3", "Desc 3", 2, 15, "min",excicle),
            Rutina("Rutina 1", "Desc 1", 2, 12, "min",excicle),
            Rutina("Rutina 2", "Desc 2", 3, 9, "min",excicle),
            Rutina("Rutina 3", "Desc 3", 2, 15, "min",excicle)
        )
    }

    LazyColumn(
    ) {
        items(rutineList.size) { index ->
            val item = rutineList[index]
            ListItemComponent(item = item)
        }
    }
}

@Composable
fun ListItemComponent(item: Rutina) {
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