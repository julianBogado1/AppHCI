package hci.app.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import hci.app.ui.main.MainViewModel

@Composable
fun MyRutineExecScreen1(rutina: Rutina, viewModel: MainViewModel) {
    val ejIndexState by viewModel.ejIndexState
    val cicleIndexState by viewModel.cicleIndexState

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletRutineExec1Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)})
    } else {
        PhoneRutineExec1Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)})
    }
}

@Composable
fun MyRutineExecScreen2(rutina: Rutina, viewModel: MainViewModel) {
    val ejIndexState by viewModel.ejIndexState
    val cicleIndexState by viewModel.cicleIndexState

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if (isTablet) {
        TabletRutineExec2Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)})
    } else {
        PhoneRutineExec2Layout(rutina,ejIndexState,{ newValue -> viewModel.setEjIndex(newValue)},cicleIndexState,{newValue -> viewModel.setCicleIndex(newValue)})
    }
}

@Composable
fun PhoneRutineExec1Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 40.dp)
        ){

            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
                color= Color(0xFF000000))

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text="${rutina.cicles[cicleIndex].name}",
                    style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold)

            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                val displayText = if (duration == 0 || series == 0) {
                    if(duration==0){ "${series} series"}
                    else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                } else {
                    "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                }

                Text(
                    text = displayText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].description}",
                    style = MaterialTheme.typography.headlineSmall)
            }

            Box(modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
            ){
                Text(text="timer")
            }

            Column(modifier = Modifier
                .fillMaxWidth()
            ){
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        onClick = {
                            onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                            if(ejIndex==0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                    ) {
                        Text(
                            text = "Siguiente",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                    ) {
                        Text(
                            text = "Descanso",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabletRutineExec1Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
            ){
                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                        color= Color(0xFF000000))

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].name}",
                            style = MaterialTheme.typography.titleLarge)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(30.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold)

                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                        val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                        val displayText = if (duration == 0 || series == 0) {
                            if(duration==0){ "${series} series"}
                            else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                        } else {
                            "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                        }

                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].description}",
                            style = MaterialTheme.typography.headlineSmall)
                    }
                }

            }

            Box(modifier = Modifier
                .padding(top=40.dp,bottom=40.dp)
            ){
                VerticalDivider(modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                    color=(Color(0xFF000000)))
            }

            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(top = 20.dp, bottom = 20.dp)
            ){
                item{
                    Box(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                    ){
                        Text(text="timer")
                    }
                }

                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if(ejIndex==0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                            ) {
                                Text(
                                    text = "Siguiente",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                            ) {
                                Text(
                                    text = "Descanso",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

            }
        }


    }
}

@Composable
fun PhoneRutineExec2Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        var ejIndex by remember { mutableStateOf(0) }
        var cicleIndex by remember { mutableStateOf(0) }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 20.dp)
        ){
            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)
                }
            }

            item{
                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                    color= Color(0xFF000000))

                Spacer(modifier = Modifier.height(10.dp))
            }

            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.cicles[cicleIndex].name}",
                        style = MaterialTheme.typography.titleLarge)
                }
            }

            item{
                Spacer(modifier = Modifier.height(30.dp))
            }

            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold)

                }
            }

            item{
                Spacer(modifier = Modifier.height(10.dp))
            }

            item{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                    val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                    val displayText = if (duration == 0 || series == 0) {
                        if(duration==0){ "${series} series"}
                        else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                    } else {
                        "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                    }

                    Text(
                        text = displayText,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item{
                Box(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                ){
                    Text(text="timer")
                }
            }

            item{
                Column(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            onClick = {
                                onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                if(ejIndex==0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                        ) {
                            Text(
                                text = "Siguiente",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                        ) {
                            Text(
                                text = "Descanso",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="A continuación en este ciclo",
                            style = MaterialTheme.typography.titleMedium)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                        color= Color(0xFF000000))

                    Spacer(modifier = Modifier.height(10.dp))

                    rutina.cicles[cicleIndex].ejs.drop(ejIndex+1).forEach{ ejercicio ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .background(Color(0xFFBEBEBE))
                        ){
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Box {
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "${ejercicio.name}",
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        Text(
                                            text = "${ejercicio.description}",
                                            style = MaterialTheme.typography.bodyLarge
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

@Composable
fun TabletRutineExec2Layout(rutina: Rutina, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF73C7A4))
    ) {

        var ejIndex by remember { mutableStateOf(0) }
        var cicleIndex by remember { mutableStateOf(0) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
            ) {
                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.name} - ${rutina.duration} ${rutina.dUnit}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalDivider(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                        color= Color(0xFF000000))

                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].name}",
                            style = MaterialTheme.typography.titleLarge)
                    }
                }

                item{
                    Spacer(modifier = Modifier.height(30.dp))
                }

                item{
                    Row(modifier = Modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text="${rutina.cicles[cicleIndex].ejs[ejIndex].name}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold)

                    }
                }

                item{
                    Spacer(modifier = Modifier.height(10.dp))
                }

                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val duration = rutina.cicles[cicleIndex].ejs[ejIndex].duration
                        val series = rutina.cicles[cicleIndex].ejs[ejIndex].series

                        val displayText = if (duration == 0 || series == 0) {
                            if(duration==0){ "${series} series"}
                            else { "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit}"}
                        } else {
                            "${duration} ${rutina.cicles[cicleIndex].ejs[ejIndex].dUnit} / ${series} series"
                        }

                        Text(
                            text = displayText,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                item{
                    Box(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                    ){
                        Text(text="timer")
                    }
                }

            }

            Box(modifier = Modifier
                .padding(top=40.dp,bottom=40.dp)
            ){
                VerticalDivider(modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp),
                    color=(Color(0xFF000000)))
            }

            LazyColumn( modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(16.dp)
                .padding(top = 20.dp, bottom = 20.dp)
            ) {
                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if(ejIndex==0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                            ) {
                                Text(
                                    text = "Siguiente",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                            ) {
                                Text(
                                    text = "Descanso",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(text="A continuación en este ciclo",
                                style = MaterialTheme.typography.titleMedium)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalDivider(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                            color= Color(0xFF000000))

                        Spacer(modifier = Modifier.height(10.dp))

                        rutina.cicles[cicleIndex].ejs.drop(ejIndex+1).forEach{ ejercicio ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .background(Color(0xFFBEBEBE))
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Box {
                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "${ejercicio.name}",
                                                style = MaterialTheme.typography.titleLarge
                                            )

                                            Text(
                                                text = "${ejercicio.description}",
                                                style = MaterialTheme.typography.bodyLarge
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
}

/*
@Preview(showBackground = true)
@Composable
fun MyRutineExecScreenPreview() {
    val cicle = Cicle(
        name = "Ejercitación",
        number = 1,
        rep = 3,
        ejs = listOf(
            Ejercicio("Squats", "Leg workout", 2, 30, "s"),
            Ejercicio("Push-ups", "Upper body workout", 3, 2, "s"),
        )
    )

    val excicle= listOf(cicle,cicle,cicle)

    val rutina = Rutina("Rutina 1", "Description for Rutina 1", 2, 30, "min",excicle,7.5, "13/11/2023", "Cardio")
    MyRutineExecScreen1(rutina)
}*/