package hci.app.Composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex

data class Rutina(
    val name: String,
    val description: String,
    val rating: Int,
    val duration: Int,
    val dUnit: String,
    val cicles: List<Cicle>,
    val points: Double,
    val date: String,
    val category: String
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
    var sortCriteria by remember { mutableStateOf("rate") }
    var sortCriteriaName by remember { mutableStateOf("Dificultad") }

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
            Rutina("Rutina 1", "Desc 1", 2, 12, "min",excicle,5.0, "13/11/2023", "Piernas"),
            Rutina("Rutina 2", "Desc 2", 3, 9, "min",excicle,3.0, "13/11/2023", "Piernas"),
            Rutina("Rutina 3", "Desc 3", 2, 15, "min",excicle,3.0, "13/11/2023", "Brazos"),
            Rutina("Rutina 4", "Desc 1", 1, 12, "min",excicle,3.0, "09/11/2023", "Brazos"),
            Rutina("Rutina 5", "Desc 2", 1, 9, "min",excicle,5.0, "09/11/2023", "Brazos"),
            Rutina("Rutina 6", "Desc 3", 2, 15, "min",excicle,7.0, "09/11/2023", "Cardio"),
            Rutina("Rutina 7", "Desc 1", 2, 12, "min",excicle,2.5, "12/11/2023", "Cardio"),
            Rutina("Rutina 8", "Desc 2", 3, 9, "min",excicle,7.6, "12/11/2023", "Cardio"),
            Rutina("Rutina 9", "Desc 3", 2, 15, "min",excicle,9.0, "12/11/2023", "Yoga"),
            Rutina("Rutina 10", "Desc 1", 2, 12, "min",excicle,10.0, "11/11/2023", "Yoga"),
            Rutina("Rutina 11", "Desc 2", 3, 9, "min",excicle,5.0, "11/11/2023", "Yoga"),
            Rutina("Rutina 12", "Desc 3", 2, 15, "min",excicle,7.0, "12/11/2023", "Piernas")
        )
    }

    val sortedRutineList = remember(rutineList, sortCriteria) {
        when (sortCriteria.lowercase()) {
            "rate" -> rutineList.sortedBy { it.rating }
            "category" -> rutineList.sortedBy {it.category}
            "date" -> rutineList.sortedByDescending {it.date}
            "points" -> rutineList.sortedByDescending {it.points}
            else -> rutineList
        }
    }

    var isDropdownVisible by remember { mutableStateOf(false) }

    val icon = if (isDropdownVisible)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var TextFieldSize by remember { mutableStateOf(Size.Zero)}



    Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = sortCriteriaName,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        TextFieldSize = coordinates.size.toSize()
                    },
                label = {Text("Ordenar por")},
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { isDropdownVisible = !isDropdownVisible })
                }
            )


            DropdownMenu(
                expanded = isDropdownVisible,
                onDismissRequest = { isDropdownVisible = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){TextFieldSize.width.dp})
            ) {
                DropdownMenuItem("Dificultad") {
                    sortCriteria = "rate"
                    sortCriteriaName = "Dificultad"
                    isDropdownVisible = false
                }
                DropdownMenuItem("Categoría") {
                    sortCriteria = "category"
                    sortCriteriaName = "Categoría"
                    isDropdownVisible = false
                }
                DropdownMenuItem("Fecha") {
                    sortCriteria = "date"
                    sortCriteriaName = "Fecha"
                    isDropdownVisible = false
                }
                DropdownMenuItem("Puntaje") {
                    sortCriteria = "points"
                    sortCriteriaName = "Puntaje"
                    isDropdownVisible = false
                }
            }
        }


        LazyColumn {
            items(sortedRutineList.size) { index ->
                val item = sortedRutineList[index]
                ListItemComponent(item = item)
            }
        }

    }
}

@Composable
private fun DropdownMenuItem(text: String, onItemClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(8.dp)
    ) {
        Text(text = text,
            modifier = Modifier.align(Alignment.Center))
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
                        .padding(2.dp)
                        .padding(start = 6.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${item.name}", style = MaterialTheme.typography.titleLarge)

                    Text(text = "${item.description}", style = MaterialTheme.typography.bodyLarge)

                    Text(text = "${item.category}", style = MaterialTheme.typography.bodyMedium)

                    Text(text = "${item.date}", style = MaterialTheme.typography.bodyMedium)

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

                    when (item.rating) {
                        1 -> Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                            ArmFlexIcon()
                            ArmFlexOutlineIcon()
                            ArmFlexOutlineIcon()
                        }
                        2 -> Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                            ArmFlexIcon()
                            ArmFlexIcon()
                            ArmFlexOutlineIcon()
                        }
                        3 -> Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                            ArmFlexIcon()
                            ArmFlexIcon()
                            ArmFlexIcon()
                        }
                    }

                    Text(
                        text = "${item.duration} ${item.dUnit}", style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            modifier = Modifier.size(24.dp)
                        )

                        Text(text = "${item.points}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }



        }

    }

}

@Preview(showBackground = true)
@Composable
fun RutinaListScreenPreview() {

    MyListScreen()
}
