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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import hci.app.Composables.ArmFlexIcon
import hci.app.Composables.ArmFlexOutlineIcon
import hci.app.data.model.Cycle
import hci.app.data.model.Exercise
import hci.app.data.model.Routine
import hci.app.ui.main.MainViewModel


@Composable
fun RoutineDetailScreen(viewModel: MainViewModel, routineId: Int) {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletRutineDetailLayout(viewModel, routineId)
    } else {
        PhoneRutineDetailLayout(viewModel, routineId)
    }
}

@Composable
fun PhoneRutineDetailLayout(viewModel: MainViewModel, routineId: Int) {

    viewModel.getOneRoutine(routineId)
    val routine = viewModel.uiState.oneRoutine

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
                Text(
                    text = routine?.name!!,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

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
            Text(text = "${routine?.detail}", style = MaterialTheme.typography.bodyLarge)
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
            Text(text = "${routine?.category}", style = MaterialTheme.typography.bodyLarge)
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
            Text(text = "${routine?.date!!}", style = MaterialTheme.typography.bodyLarge)
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
            Text(text = "${routine?.metadata?.duration} min", style = MaterialTheme.typography.bodyLarge)
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


        when (routine?.difficulty) {
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
            Text(text = "${routine?.score}", style = MaterialTheme.typography.bodyLarge)
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray))
        }

        item{
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Ciclo de Calentamiento: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

                Text(text = "Repeticiones: ", style = MaterialTheme.typography.bodyLarge)
            }
        }

        viewModel.getCycles(routineId)
        val cycles = viewModel.uiState.cycles?.content
        cycles?.first()?.id?.let { viewModel.getOneCycle(routineId, it) }
        val cycle = viewModel.uiState.oneCycle
        cycle?.id?.let { viewModel.get }
        val exercises = cycle.

        cycles?.first().ejs.forEach{ ejercicio ->
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


        routine.cicles.drop(1).dropLast(1).forEach { cicle ->
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

                Text(text = "Repeticiones: ${routine.cicles.last().rep}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        routine.cicles.last().ejs.forEach{ ejercicio ->
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

@Preview(showBackground = true)
@Composable
fun RutinaDetailScreenPreview() {
    val cicle = Cicle(
        name = "Ejercitación",
        number = 1,
        rep = 3,
        ejs = listOf(
            Ejercicio("Squats", "Leg workout", 3, 30, "s"),
            Ejercicio("Push-ups", "Upper body workout", 3, 20, "s"),
        )
    )

    val excicle= listOf(cicle,cicle,cicle)

    val rutina = Rutina("Rutina 1", "Description for Rutina 1", 2, 30, "min",excicle,7.5, "13/11/2023", "Cardio")
    RoutineDetailScreen(routine = rutina)
}

