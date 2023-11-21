package hci.app


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import hci.app.Composables.ArmFlexIcon
import hci.app.Composables.ArmFlexOutlineIcon
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.data.network.model.NetworkCycleExercisesContent
import hci.app.data.network.model.NetworkRoutineContent
import hci.app.data.network.model.NetworkRoutineCycleContent
import hci.app.ui.main.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun RoutineDetailScreen(viewModel: MainViewModel, routineId: Int) {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    /*
    if (isTablet) {
        TabletRutineDetailLayout(viewModel, routineId)
    } else {
     */
        PhoneRoutineDetailLayout(viewModel, routineId)
    /*
    }
     */
}

@Composable
fun PhoneRoutineDetailLayout(viewModel: MainViewModel, routineId: Int) {

    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId)
            viewModel.uiState.cycles?.content?.forEach { cycle ->
                cycle.id?.let { viewModel.getCycleExercises(it) }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 40.dp)
    ) {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                viewModel.uiState.oneRoutine?.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Text(
                text = "Descripción: ",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
            )
        }

        item {
            Text(text = "${viewModel.uiState.oneRoutine?.detail}", style = MaterialTheme.typography.bodyLarge)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Text(
                text = "Fecha de Creación: ",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
            )
        }

        item {
            Text(text = "${viewModel.uiState.oneRoutine?.date}", style = MaterialTheme.typography.bodyLarge)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Text(
                text = "Duración: ",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
            )
        }

        item {
            Text(
                text = "${viewModel.uiState.oneRoutine?.metadata?.duration} min",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Text(
                text = "Dificultad: ",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
            )
        }


        when (viewModel.uiState.oneRoutine?.difficulty) {
            "beginner" -> item {
                Row() {
                    ArmFlexIcon()
                    ArmFlexOutlineIcon()
                    ArmFlexOutlineIcon()
                }
            }

            "intermediate" -> item {
                Row() {
                    ArmFlexIcon()
                    ArmFlexIcon()
                    ArmFlexOutlineIcon()
                }
            }

            "advanced" -> item {
                Row() {
                    ArmFlexIcon()
                    ArmFlexIcon()
                    ArmFlexIcon()
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        item {
            Text(
                text = "Puntuación: ",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
            )
        }

        item {
            Text(text = "${viewModel.uiState.oneRoutine?.score}", style = MaterialTheme.typography.bodyLarge)
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }


        item {
            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        viewModel.uiState.cycles?.content?.forEach { oneCycle ->

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    oneCycle.name?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
                        )
                    }

                    Text(
                        text = "${oneCycle.repetitions}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            val exercises = viewModel.uiState.cycleExercises?.get(oneCycle.id)

            exercises?.content?.forEach { exercise ->
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(Color(0xFFD9D9D9))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box {
                                Column(
                                    modifier = Modifier
                                        .padding(2.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${exercise.exercise?.name}",
                                        style = MaterialTheme.typography.titleLarge
                                    )

                                    Text(
                                        text = "${exercise.exercise?.detail}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )

                                }
                            }

                            Box {
                                Row(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .fillMaxHeight()
                                        .background(Color(0xFFBEBEBE))
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = "Series ",
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${exercise.repetitions}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = "Duración (min)",
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${exercise.duration}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
        }
    }
}

/*
@Composable
fun TabletRutineDetailLayout(viewModel: MainViewModel, routineId: Int) {
    val cicle = Cicle(
        name = "Ejercitación",
        number = 1,
        rep = 3,
        ejs = listOf(
            Ejercicio("Squats", "Leg workout", 3, 30, "s"),
            Ejercicio("Push-ups", "Upper body workout", 3, 20, "s"),
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start=80.dp)
    ) {
        LazyColumn( modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .padding(top=40.dp,bottom=40.dp)
        ){
            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = rutina.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                }
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            item{
                Text(text = "Descripción: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))
            }

            item{
                Text(text = "${rutina.description}", style = MaterialTheme.typography.bodyLarge)
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            item{
                Text(text = "Categoría: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))
            }

            item{
                Text(text = "${rutina.category}", style = MaterialTheme.typography.bodyLarge)
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            item{
                Text(text = "Fecha de Creación: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))
            }

            item{
                Text(text = "${rutina.date}", style = MaterialTheme.typography.bodyLarge)
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            item{
                Text(text = "Duración: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))
            }

            item{
                Text(text = "${rutina.duration} ${rutina.dUnit}", style = MaterialTheme.typography.bodyLarge)
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            item{
                Text(text = "Dificultad: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))
            }

            when (rutina.rating) {
                1 -> item{
                    Row(){
                        ArmFlexIcon()
                        ArmFlexOutlineIcon()
                        ArmFlexOutlineIcon()
                    }
                }
                2 -> item{
                    Row(){
                        ArmFlexIcon()
                        ArmFlexIcon()
                        ArmFlexOutlineIcon()
                    }
                }
                3 -> item{
                    Row(){
                        ArmFlexIcon()
                        ArmFlexIcon()
                        ArmFlexIcon()
                    }
                }
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            item{
                Text(text = "Puntuación: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))
            }

            item{
                Text(text = "${rutina.points}", style = MaterialTheme.typography.bodyLarge)
            }

        }

        Box(modifier = Modifier
            .padding(top=40.dp,bottom=40.dp)
        ){
            VerticalDivider(modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Color.Gray))
        }


        LazyColumn( modifier = Modifier
            .fillMaxHeight()
            .padding(40.dp)
        ){
            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Ciclo de Calentamiento: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

                    Text(text = "Repeticiones: ${rutina.cicles.first().rep}", style = MaterialTheme.typography.bodyLarge)
                }
            }

            rutina.cicles.first().ejs.forEach{ ejercicio ->
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
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
                                    Text(text = "${ejercicio.name}", style = MaterialTheme.typography.titleLarge)

                                    Text(text = "${ejercicio.description}", style = MaterialTheme.typography.bodyLarge)

                                }
                            }

                            Box{
                                Row(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .fillMaxHeight()
                                        .background(Color(0xFFBEBEBE))
                                ){
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = "Series ", style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${ejercicio.series}", style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ){
                                        Text(
                                            text = "Duración (${ejercicio.dUnit})", style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${ejercicio.duration}", style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                }
                            }



                        }

                    }
                }
            }

            item{
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            rutina.cicles.drop(1).dropLast(1).forEach { cicle ->
                item{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Ciclo de Ejercitación ${cicle.number}: ",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
                        )

                        Text(
                            text = "Repeticiones: ${cicle.rep}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                cicle.ejs.forEach{ ejercicio ->
                    item{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
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
                                        Text(text = "${ejercicio.name}", style = MaterialTheme.typography.titleLarge)

                                        Text(text = "${ejercicio.description}", style = MaterialTheme.typography.bodyLarge)

                                    }
                                }

                                Box{
                                    Row(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .fillMaxHeight()
                                            .background(Color(0xFFBEBEBE))
                                    ){
                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ) {
                                            Text(
                                                text = "Series ", style = MaterialTheme.typography.bodyLarge
                                            )

                                            Text(
                                                text = "${ejercicio.series}", style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ){
                                            Text(
                                                text = "Duración (${ejercicio.dUnit})", style = MaterialTheme.typography.bodyLarge
                                            )

                                            Text(
                                                text = "${ejercicio.duration}", style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray))
                }
            }

            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Ciclo de Enfriamiento: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

                    Text(text = "Repeticiones: ${rutina.cicles.last().rep}", style = MaterialTheme.typography.bodyLarge)
                }
            }

            rutina.cicles.last().ejs.forEach{ ejercicio ->
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
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
                                    Text(text = "${ejercicio.name}", style = MaterialTheme.typography.titleLarge)

                                    Text(text = "${ejercicio.description}", style = MaterialTheme.typography.bodyLarge)

                                }
                            }

                            Box{
                                Row(
                                    modifier = Modifier
                                        .width(160.dp)
                                        .fillMaxHeight()
                                        .background(Color(0xFFBEBEBE))
                                ){
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = "Series ", style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${ejercicio.series}", style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ){
                                        Text(
                                            text = "Duración (${ejercicio.dUnit})", style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${ejercicio.duration}", style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
 */

