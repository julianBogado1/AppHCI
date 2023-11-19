package hci.app.Composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyRutineDetailScreen(rutina: Rutina) {
    val cicle = Cicle(
        name = "Ejercitación",
        number = 1,
        rep = 3,
        ejs = listOf(
            Ejercicio("Squats", "Leg workout", 3, 30, "s"),
            Ejercicio("Push-ups", "Upper body workout", 3, 20, "s"),
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = rutina.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
        }


        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Text(text = "Descripción: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

        Text(text = "${rutina.description}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Text(text = "Categoría: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

        Text(text = "${rutina.category}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Text(text = "Fecha de Creación: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

        Text(text = "${rutina.date}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Text(text = "Duración: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

        Text(text = "${rutina.duration} ${rutina.dUnit}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Text(text = "Dificultad: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

        when (rutina.rating) {
            1 -> Row(){
                ArmFlexIcon()
                ArmFlexOutlineIcon()
                ArmFlexOutlineIcon()
            }
            2 -> Row(){
                ArmFlexIcon()
                ArmFlexIcon()
                ArmFlexOutlineIcon()
            }
            3 -> Row(){
                ArmFlexIcon()
                ArmFlexIcon()
                ArmFlexIcon()
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Text(text = "Puntuación: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

        Text(text = "${rutina.points}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Ciclo de Calentamiento: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

            Text(text = "Repeticiones: ${rutina.cicles.first().rep}", style = MaterialTheme.typography.bodyLarge)
        }

        rutina.cicles.first().ejs.forEach{ ejercicio ->
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

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

        rutina.cicles.drop(1).dropLast(1).forEach { cicle ->
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

            cicle.ejs.forEach{ ejercicio ->
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

            Spacer(modifier = Modifier.height(8.dp))

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray))
        }


        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "Ciclo de Enfriamiento: ", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C)))

            Text(text = "Repeticiones: ${rutina.cicles.last().rep}", style = MaterialTheme.typography.bodyLarge)
        }

        rutina.cicles.last().ejs.forEach{ ejercicio ->
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

        Spacer(modifier = Modifier.height(8.dp))

        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray))

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
    MyRutineDetailScreen(rutina = rutina)
}

