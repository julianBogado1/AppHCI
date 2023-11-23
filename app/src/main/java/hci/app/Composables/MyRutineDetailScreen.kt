package hci.app.Composables


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.ui.main.MainViewModel
import hci.app.ui.main.copyLinkToClipboard
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MyRoutineDetailScreen(viewModel: MainViewModel, routineId: Int) {
    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value
    val exercisesMap = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    val context = LocalContext.current

    LaunchedEffect(key1 = routineId, key2 = exercisesMap) {
        launch {
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId).invokeOnCompletion {
                val cycles = viewModel.uiState.cycles
                cycles?.content?.forEach { cycle ->
                    cycle.id?.let { cycleId ->
                        viewModel.getCycleExercises(cycleId).invokeOnCompletion {
                            val cycleExercises = viewModel.uiState.cycleExercises
                            cycleExercises?.let {
                                exercisesMap[cycleId] = it
                            }
                        }
                    }
                }
            }
        }
    }

    if (isTablet) {
        TabletRutineDetailLayout(viewModel = viewModel, routineId = routineId, exercisesMap = exercisesMap, context = context)
    } else {
        PhoneRutineDetailLayout(viewModel = viewModel, routineId = routineId, exercisesMap = exercisesMap, context = context)
    }
}

@Composable
fun PhoneRutineDetailLayout(viewModel: MainViewModel, routineId: Int, exercisesMap: MutableMap<Int, NetworkCycleExercises>, context: Context) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(bottom = 80.dp)
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

                Image(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier
                        .clickable { copyLinkToClipboard("https://www.creatina.share.com/rutinas?id=${viewModel.uiState.oneRoutine?.id}", context) }
                )
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
            Text(text = "${viewModel.uiState.oneRoutine?.detail}", style = MaterialTheme.typography.bodyLarge)
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

        var formatter = SimpleDateFormat("yyyy-MM-dd")
        item{
            Text(text = "${formatter.format(Date(viewModel.uiState.oneRoutine?.date?:0))}", style = MaterialTheme.typography.bodyLarge)
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
            Text(text = "${viewModel.uiState.oneRoutine?.metadata?.duration?:""}", style = MaterialTheme.typography.bodyLarge)
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


        item {
            when (viewModel.uiState.oneRoutine?.difficulty) {
                "rookie" ->
                    Row() {
                        ArmFlexIcon()
                        ArmFlexOutlineIcon()
                        ArmFlexOutlineIcon()
                    }

                "beginner" -> Row() {
                    ArmFlexIcon()
                    ArmFlexOutlineIcon()
                    ArmFlexOutlineIcon()
                }

                "intermediate" -> Row() {
                    ArmFlexIcon()
                    ArmFlexIcon()
                    ArmFlexOutlineIcon()
                }

                "advanced" -> Row() {
                    ArmFlexIcon()
                    ArmFlexIcon()
                    ArmFlexIcon()   //todo SARACA
                }

                "expert" -> Row() {
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
            Text(text = "${viewModel.uiState.oneRoutine?.score}", style = MaterialTheme.typography.bodyLarge)
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))

            Divider(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray))
        }

        viewModel.uiState.cycles?.content?.forEach { oneCycle ->
            item{
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
                        text = "Repeticiones: ${oneCycle.repetitions}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            exercisesMap.get(oneCycle.id)?.content?.forEach{ exercise ->
                item{
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(Color(0xFFD9D9D9))
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Box(modifier = Modifier.weight(0.6f)){
                                Column(
                                    modifier = Modifier
                                        .padding(2.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "${exercise.exercise?.name}", style = MaterialTheme.typography.titleLarge)

                                    Text(text = "${exercise.exercise?.detail}", style = MaterialTheme.typography.bodyLarge)

                                }
                            }

                            Box(modifier = Modifier
                                .weight(0.4f)
                                .background(Color(0xFFBEBEBE))){
                                Row(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxSize()

                                ){
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = "Series ", style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${exercise.repetitions}", style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ){
                                        Text(
                                            text = "Duración", style = MaterialTheme.typography.bodyLarge
                                        )

                                        Text(
                                            text = "${exercise.duration} m", style = MaterialTheme.typography.bodyLarge,
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
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun TabletRutineDetailLayout(viewModel: MainViewModel, routineId: Int, exercisesMap: MutableMap<Int, NetworkCycleExercises>, context: Context) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 80.dp)
    ) {
        LazyColumn( modifier = Modifier
            .width(300.dp)
            .fillMaxHeight()
            .padding(top = 40.dp, bottom = 40.dp)
        ){
            item{
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){

                    viewModel.uiState.oneRoutine?.name?.let { Text(text = it, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
                    Image(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier
                            .clickable { copyLinkToClipboard("https://www.creatina.share.com/rutinas?id=${viewModel.uiState.oneRoutine?.id}", context) }
                    )
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
                Text(text = "${viewModel.uiState.oneRoutine?.detail}", style = MaterialTheme.typography.bodyLarge)
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

            var formatter = SimpleDateFormat("yyyy-MM-dd")
            item{
                Text(text = "${formatter.format(Date(viewModel.uiState.oneRoutine?.date?:0))}", style = MaterialTheme.typography.bodyLarge)
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
                Text(text = "${viewModel.uiState.oneRoutine?.metadata?.duration?:""}", style = MaterialTheme.typography.bodyLarge)
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


            item {
                when (viewModel.uiState.oneRoutine?.difficulty) {
                    "rookie" ->
                        Row() {
                            ArmFlexIcon()
                            ArmFlexOutlineIcon()
                            ArmFlexOutlineIcon()
                        }

                    "beginner" -> Row() {
                        ArmFlexIcon()
                        ArmFlexOutlineIcon()
                        ArmFlexOutlineIcon()
                    }

                    "intermediate" -> Row() {
                        ArmFlexIcon()
                        ArmFlexIcon()
                        ArmFlexOutlineIcon()
                    }

                    "advanced" -> Row() {
                        ArmFlexIcon()
                        ArmFlexIcon()
                        ArmFlexIcon()   //todo SARACA
                    }

                    "expert" -> Row() {
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
                Text(text = "${viewModel.uiState.oneRoutine?.score}", style = MaterialTheme.typography.bodyLarge)
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
                Spacer(modifier = Modifier.height(8.dp))

                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray))
            }

            viewModel.uiState.cycles?.content?.forEach { cycle ->
                item{
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        cycle.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF4C4C4C))
                            )
                        }

                        Text(
                            text = "Repeticiones: ${cycle.repetitions}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                exercisesMap.get(cycle.id)?.content?.forEach{ exercise ->
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
                                Box(modifier = Modifier.weight(0.6f)){
                                    Column(
                                        modifier = Modifier
                                            .padding(2.dp),
                                        verticalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "${exercise.exercise?.name}", style = MaterialTheme.typography.titleLarge)

                                        Text(text = "${exercise.exercise?.detail}", style = MaterialTheme.typography.bodyLarge)

                                    }
                                }

                                Box(modifier = Modifier
                                    .weight(0.4f)
                                    .background(Color(0xFFBEBEBE))){
                                    Row(
                                        modifier = Modifier.fillMaxSize()
                                    ){
                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ) {
                                            Text(
                                                text = "Series ", style = MaterialTheme.typography.bodyLarge
                                            )

                                            Text(
                                                text = "${exercise.repetitions}", style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                            )
                                        }

                                        Column(
                                            modifier = Modifier
                                                .padding(2.dp)
                                        ){
                                            Text(
                                                text = "Duración", style = MaterialTheme.typography.bodyLarge
                                            )

                                            Text(
                                                text = "${exercise.duration} m", style = MaterialTheme.typography.bodyLarge,
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
}
