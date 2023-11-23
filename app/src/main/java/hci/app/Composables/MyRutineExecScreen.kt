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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import hci.app.R
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.ui.main.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun getRoutine(routineId : Int, viewModel: MainViewModel) : Map<Int, NetworkCycleExercises>{
    val routine = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    LaunchedEffect(key1 = routine){
        launch{
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId).invokeOnCompletion {
                val cycles = viewModel.uiState.cycles
                cycles?.content?.forEach { cycle ->
                    cycle.id?.let { cycleId ->
                        viewModel.getCycleExercises(cycleId).invokeOnCompletion {
                            val cycleExercises = viewModel.uiState.cycleExercises
                            cycleExercises?.let {
                                routine[cycleId] = it
                            }
                        }
                    }
                }
            }
        }
    }
    return routine
}

@Composable
fun getRoutineTotalCount(routineId : Int, viewModel: MainViewModel, cicleId : Int) : Int{
    val routine = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    var toReturn =0
    LaunchedEffect(key1 = routine){
        launch{
            viewModel.getOneRoutine(routineId)
            viewModel.getCycles(routineId).invokeOnCompletion {
                val cycles = viewModel.uiState.cycles
                toReturn = cycles?.totalCount?:0
            }
        }
    }
    return toReturn
}


@Composable
fun MyRutineExecScreen1(navController: NavHostController, viewModel: MainViewModel, routineId : Int, onNavigateToRoutine: (String) -> Unit) {
    val exercisesMap = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    var started = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = routineId) {
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
                viewModel.setTimerRemainingSec(0)
                viewModel.setTimeCountdown(0)
                viewModel.setInBreak(false)
                viewModel.setInStop(false)
                viewModel.setEjIndex(0)
                viewModel.setCicleIndex(0)
            }
        }
    }
    var routineData = viewModel.uiState.oneRoutine!!

    val exerciseIndex by viewModel.ejIndexState
    val cycleIndex by viewModel.cicleIndexState

    val inBreak by viewModel.inBreak
    val inStop by viewModel.inStop
    val timerRemainingSec by viewModel.timerRemainingSec
    val timeCountdown by viewModel.timeCountdown

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if(started.value || (cycleIndex == 0 && exerciseIndex == 0)) {
        started.value = true
        if (isTablet) {
            TabletRutineExec1Layout(
                navController = navController,
                viewModel,
                exercisesMap = exercisesMap,
                inBreak,
                { newValue: Boolean -> viewModel.setInBreak(newValue) },
                timerRemainingSec,
                { newValue: Int -> viewModel.setTimerRemainingSec(newValue) },
                inStop,
                { newValue: Boolean -> viewModel.setInStop(newValue) },
                timeCountdown,
                { newValue: Int -> viewModel.setTimeCountdown(newValue) },
                onNavigateToRoutine = onNavigateToRoutine,
                exerciseIndex = exerciseIndex,
                cycleIndex = cycleIndex,
                onExChange = { newValue: Int -> viewModel.setEjIndex(newValue) },
                onCycleChange = { newValue: Int -> viewModel.setCicleIndex(newValue) })
        } else {
            PhoneRutineExec1Layout(
                navController = navController,
                viewModel,
                exercisesMap = exercisesMap,
                inBreak,
                { newValue: Boolean -> viewModel.setInBreak(newValue) },
                timerRemainingSec,
                { newValue: Int -> viewModel.setTimerRemainingSec(newValue) },
                inStop,
                { newValue: Boolean -> viewModel.setInStop(newValue) },
                timeCountdown,
                { newValue: Int -> viewModel.setTimeCountdown(newValue) },
                onNavigateToRoutine = onNavigateToRoutine,
                exerciseIndex = exerciseIndex,
                cycleIndex = cycleIndex,
                onExChange = { newValue: Int -> viewModel.setEjIndex(newValue) },
                onCycleChange = { newValue: Int -> viewModel.setCicleIndex(newValue) })
        }
    }
}

@Composable
fun MyRutineExecScreen2(navController: NavHostController, viewModel: MainViewModel, routineId : Int, onNavigateToRoutine: (String) -> Unit) {
    val exercisesMap = remember { mutableStateMapOf<Int, NetworkCycleExercises>() }
    var started = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = routineId) {
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
                viewModel.setTimerRemainingSec(0)
                viewModel.setTimeCountdown(0)
                viewModel.setInBreak(false)
                viewModel.setInStop(false)
                viewModel.setEjIndex(0)
                viewModel.setCicleIndex(0)
            }
        }
    }
    var routineData = viewModel.uiState.oneRoutine!!

    val exerciseIndex by viewModel.ejIndexState
    val cycleIndex by viewModel.cicleIndexState

    val inBreak by viewModel.inBreak
    val inStop by viewModel.inStop
    val timerRemainingSec by viewModel.timerRemainingSec
    val timeCountdown by viewModel.timeCountdown

    val isTabletState = rememberUpdatedState(LocalConfiguration.current.screenWidthDp >= 600)
    val isTablet = isTabletState.value

    if(started.value || (cycleIndex == 0 && exerciseIndex == 0)) {
        started.value = true
        if (isTablet) {
            /*TabletRutineExec2Layout(
                navController = navController,
                viewModel,
                exercisesMap = exercisesMap,
                inBreak,
                { newValue: Boolean -> viewModel.setInBreak(newValue) },
                timerRemainingSec,
                { newValue: Int -> viewModel.setTimerRemainingSec(newValue) },
                inStop,
                { newValue: Boolean -> viewModel.setInStop(newValue) },
                timeCountdown,
                { newValue: Int -> viewModel.setTimeCountdown(newValue) },
                onNavigateToRoutine = onNavigateToRoutine,
                exerciseIndex = exerciseIndex,
                cycleIndex = cycleIndex,
                onExChange = { newValue: Int -> viewModel.setEjIndex(newValue) },
                onCycleChange = { newValue: Int -> viewModel.setCicleIndex(newValue) })

             */
        } else {
            PhoneRutineExec2Layout(
                navController = navController,
                viewModel,
                exercisesMap = exercisesMap,
                inBreak,
                { newValue: Boolean -> viewModel.setInBreak(newValue) },
                timerRemainingSec,
                { newValue: Int -> viewModel.setTimerRemainingSec(newValue) },
                inStop,
                { newValue: Boolean -> viewModel.setInStop(newValue) },
                timeCountdown,
                { newValue: Int -> viewModel.setTimeCountdown(newValue) },
                onNavigateToRoutine = onNavigateToRoutine,
                exerciseIndex = exerciseIndex,
                cycleIndex = cycleIndex,
                onExChange = { newValue: Int -> viewModel.setEjIndex(newValue) },
                onCycleChange = { newValue: Int -> viewModel.setCicleIndex(newValue) })
        }
    }
}

//rutina: NetworkRoutineCycles?, ejIndex: Int,onEjIndexChange: (Int) -> Unit, cicleIndex: Int,onCicleIndexChange: (Int) -> Unit, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit

@Composable
fun PhoneRutineExec1Layout(navController: NavHostController, viewModel : MainViewModel, exercisesMap: MutableMap<Int, NetworkCycleExercises>, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit, onNavigateToRoutine: (String) -> Unit, cycleIndex: Int, exerciseIndex: Int, onExChange: (Int) -> Unit, onCycleChange: (Int) -> Unit) {
    var totalCycleCount = (viewModel.uiState.cycles?.totalCount?:1) - 1

    if(totalCycleCount >= 0) {
        if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
            onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
            return
        }
        var totalExerciseCount = (exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.totalCount ?: 1) - 1
        if (totalExerciseCount == -1) {
            onCycleChange(viewModel.cicleIndexState.value + 1)
            if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                return
            }
        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF73C7A4))
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(top = 40.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${viewModel.uiState.oneRoutine?.name}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = Color(0xFF000000)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.name}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${
                                    exercisesMap[viewModel.uiState.cycles?.content?.get(
                                        viewModel.cicleIndexState.value
                                    )?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.exercise?.name
                                }",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )

                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val duration =
                                exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.duration ?: 0
                            val series =
                                exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.repetitions ?: 0


                            val displayText = if (duration == 0 || series == 0) {
                                if (duration == 0) {
                                    "${series} series"
                                } else {
                                    "${duration} m"
                                }
                            } else {
                                "${duration} m / ${series} series"      //todo chequear unidades
                            }

                            Text(
                                text = displayText,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${
                                    exercisesMap[viewModel.uiState.cycles?.content?.get(
                                        viewModel.cicleIndexState.value
                                    )?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.exercise?.detail
                                }",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                    item {
                        if (!(timeRemainingSec > 0)) timeRemainingSecChange(
                            (exercisesMap[viewModel.uiState.cycles?.content?.get(
                                viewModel.cicleIndexState.value
                            )?.id]?.content?.get(viewModel.ejIndexState.value)?.duration ?: 0) * 60
                        )

                        changeTimeCountdown(
                            (exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                viewModel.ejIndexState.value
                            )?.duration ?: 0) * 60 * 1000
                        )
                        //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                        //rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.duration ?: 0) != 0
                            ) {
                                MyTimer(
                                    seconds = timeRemainingSec,
                                    onTimerFinish = {
                                        if (viewModel.cicleIndexState.value >= (totalCycleCount)
                                            && viewModel.ejIndexState.value >= (totalExerciseCount)
                                        ) {
                                            //todo navegacao
                                            return@MyTimer
                                        }
                                        onExChange((viewModel.ejIndexState.value + 1) % (totalExerciseCount + 1))
                                        //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                        if (viewModel.ejIndexState.value == 0) {
                                            onCycleChange(viewModel.cicleIndexState.value + 1)
                                            if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                                                onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                                                return@MyTimer
                                            }
                                        }
                                        //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                        stopChange(false)
                                        breakChange(false)
                                        //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                                    },
                                    onTimerTick = timeRemainingSecChange,
                                    inBreak = inBreak,
                                    inStop = inStop,
                                    stopChange = stopChange,
                                    nextRemainingTime = timeCountdown
                                )
                            }
                        }

                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                    onClick = {
                                        if ((exercisesMap[viewModel.uiState.cycles?.content?.get(
                                                viewModel.cicleIndexState.value
                                            )?.id]?.content?.get(viewModel.ejIndexState.value)?.duration
                                                ?: 0) != 0
                                        ) {
                                            //rutina.cicles[cicleIndex].ejs[ejIndex].duration
                                            stopChange(true)
                                            breakChange(true)
                                        } else {
                                           onExChange((viewModel.ejIndexState.value + 1) % (totalExerciseCount + 1))
                                            //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                            if (viewModel.ejIndexState.value == 0) {
                                                onCycleChange(viewModel.cicleIndexState.value + 1)
                                                if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                                                    onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                                                    return@Button
                                                }
                                            }
                                        }
                                        /*saracatunga*/
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.next),
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.duration ?: 0) != 0
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                        onClick = {
                                            breakChange(!inBreak)
                                        },
                                        colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.rest),
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
    }
}

@Composable
fun TabletRutineExec1Layout(navController: NavHostController, viewModel : MainViewModel, exercisesMap: MutableMap<Int, NetworkCycleExercises>, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit, onNavigateToRoutine: (String) -> Unit, cycleIndex: Int, exerciseIndex: Int, onExChange: (Int) -> Unit, onCycleChange: (Int) -> Unit) {
    var totalCycleCount = (viewModel.uiState.cycles?.totalCount?:1) - 1

    if(totalCycleCount >= 0) {
        if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
            onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
            return
        }
        var totalExerciseCount = (exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.totalCount ?: 1) - 1
        if (totalExerciseCount == -1) {
            onCycleChange(viewModel.cicleIndexState.value + 1)
            if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                return
            }
        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF73C7A4))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp)
                            .padding(top = 20.dp, bottom = 20.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${viewModel.uiState.oneRoutine?.name}",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(10.dp))

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp),
                                color = Color(0xFF000000)
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.name}",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${
                                        exercisesMap[viewModel.uiState.cycles?.content?.get(
                                            viewModel.cicleIndexState.value
                                        )?.id]?.content?.get(viewModel.ejIndexState.value)?.exercise?.name
                                    }",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                val duration =
                                    exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.duration ?: 0
                                val series =
                                    exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.repetitions ?: 0


                                val displayText = if (duration == 0 || series == 0) {
                                    if (duration == 0) {
                                        "${series} series"
                                    } else {
                                        "${duration} m"
                                    }
                                } else {
                                    "${duration} m / ${series} series"      //todo chequear unidades
                                }

                                Text(
                                    text = displayText,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${
                                        exercisesMap[viewModel.uiState.cycles?.content?.get(
                                            viewModel.cicleIndexState.value
                                        )?.id]?.content?.get(
                                            viewModel.ejIndexState.value
                                        )?.exercise?.detail
                                    }",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 40.dp, bottom = 40.dp)
                    ) {
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp),
                            color = (Color(0xFF000000))
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp)
                            .padding(top = 20.dp, bottom = 20.dp)
                    ) {
                        item {
                            if (!(timeRemainingSec > 0)) timeRemainingSecChange(
                                (exercisesMap[viewModel.uiState.cycles?.content?.get(
                                    viewModel.cicleIndexState.value
                                )?.id]?.content?.get(viewModel.ejIndexState.value)?.duration ?: 0) * 60
                            )

                            changeTimeCountdown(
                                (exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.duration ?: 0) * 60 * 1000
                            )
                            //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                            //rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration
                            Box(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                            ) {
                                if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.duration ?: 0) != 0
                                ) {
                                    MyTimer(
                                        seconds = timeRemainingSec,
                                        onTimerFinish = {
                                            if (viewModel.cicleIndexState.value >= (totalCycleCount)
                                                && viewModel.ejIndexState.value >= (totalExerciseCount)
                                            ) {
                                                //todo navegacao
                                                return@MyTimer
                                            }
                                            onExChange((viewModel.ejIndexState.value + 1) % (totalExerciseCount + 1))
                                            //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                            if (viewModel.ejIndexState.value == 0) {
                                                onCycleChange(viewModel.cicleIndexState.value + 1)
                                                if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                                                    onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                                                    return@MyTimer
                                                }
                                            }
                                            //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                            stopChange(false)
                                            breakChange(false)
                                            //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                                        },
                                        onTimerTick = timeRemainingSecChange,
                                        inBreak = inBreak,
                                        inStop = inStop,
                                        stopChange = stopChange,
                                        nextRemainingTime = timeCountdown
                                    )
                                }
                            }
                        }
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                        onClick = {
                                            if ((exercisesMap[viewModel.uiState.cycles?.content?.get(
                                                    viewModel.cicleIndexState.value
                                                )?.id]?.content?.get(viewModel.ejIndexState.value)?.duration
                                                    ?: 0) != 0
                                            ) {
                                                //rutina.cicles[cicleIndex].ejs[ejIndex].duration
                                                stopChange(true)
                                                breakChange(true)
                                            } else {
                                                onExChange((viewModel.ejIndexState.value + 1) % (totalExerciseCount + 1))
                                                //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                                if (viewModel.ejIndexState.value == 0) {
                                                    onCycleChange(viewModel.cicleIndexState.value + 1)
                                                    if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                                                        onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                                                        return@Button
                                                    }
                                                }
                                            }
                                            /*saracatunga*/
                                        },
                                        colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.next),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.duration ?: 0) != 0
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Button(
                                            modifier = Modifier
                                                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                            onClick = {
                                                breakChange(!inBreak)
                                            },
                                            colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.rest),
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
        }
    }
}

@Composable
fun PhoneRutineExec2Layout(navController: NavHostController, viewModel : MainViewModel, exercisesMap: MutableMap<Int, NetworkCycleExercises>, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit, onNavigateToRoutine: (String) -> Unit, cycleIndex: Int, exerciseIndex: Int, onExChange: (Int) -> Unit, onCycleChange: (Int) -> Unit) {
    var totalCycleCount = (viewModel.uiState.cycles?.totalCount?:1) - 1

    if(totalCycleCount >= 0) {
        if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
            onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
            return
        }
        var totalExerciseCount = (exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.totalCount ?: 1) - 1
        if (totalExerciseCount == -1) {
            onCycleChange(viewModel.cicleIndexState.value + 1)
            if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                return
            }
        } else {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF73C7A4))
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .padding(top = 20.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${viewModel.uiState.oneRoutine?.name}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = Color(0xFF000000)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.name}",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${
                                    exercisesMap[viewModel.uiState.cycles?.content?.get(
                                        viewModel.cicleIndexState.value
                                    )?.id]?.content?.get(
                                        viewModel.ejIndexState.value
                                    )?.exercise?.name
                                }",
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold
                            )

                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val duration =
                                exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.duration ?: 0
                            val series =
                                exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.repetitions ?: 0


                            val displayText = if (duration == 0 || series == 0) {
                                if (duration == 0) {
                                    "${series} series"
                                } else {
                                    "${duration} m"
                                }
                            } else {
                                "${duration} m / ${series} series"      //todo chequear unidades
                            }

                            Text(
                                text = displayText,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    item {
                        if (!(timeRemainingSec > 0)) timeRemainingSecChange(
                            (exercisesMap[viewModel.uiState.cycles?.content?.get(
                                viewModel.cicleIndexState.value
                            )?.id]?.content?.get(viewModel.ejIndexState.value)?.duration ?: 0) * 60
                        )

                        changeTimeCountdown(
                            (exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                viewModel.ejIndexState.value
                            )?.duration ?: 0) * 60 * 1000
                        )
                        //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                        //rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration
                        Box(
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                        ) {
                            if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(
                                    viewModel.ejIndexState.value
                                )?.duration ?: 0) != 0
                            ) {
                                MyTimer(
                                    seconds = timeRemainingSec,
                                    onTimerFinish = {
                                        if (viewModel.cicleIndexState.value >= (totalCycleCount)
                                            && viewModel.ejIndexState.value >= (totalExerciseCount + 1)
                                        ) {
                                            //todo navegacao
                                            return@MyTimer
                                        }
                                        onExChange((viewModel.ejIndexState.value + 1) % (totalExerciseCount + 1))
                                        //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                        if (viewModel.ejIndexState.value == 0) {
                                            onCycleChange(viewModel.cicleIndexState.value + 1)
                                            if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                                                onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                                                return@MyTimer
                                            }
                                        }
                                        //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                        stopChange(false)
                                        breakChange(false)
                                        //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                                    },
                                    onTimerTick = timeRemainingSecChange,
                                    inBreak = inBreak,
                                    inStop = inStop,
                                    stopChange = stopChange,
                                    nextRemainingTime = timeCountdown
                                )
                            }
                        }

                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    modifier = Modifier
                                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                    onClick = {
                                        if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(viewModel.ejIndexState.value)?.duration ?: 0) != 0
                                        ) {
                                            //rutina.cicles[cicleIndex].ejs[ejIndex].duration
                                            stopChange(true)
                                            breakChange(true)
                                        } else {
                                            onExChange((viewModel.ejIndexState.value + 1) % (totalExerciseCount + 1))
                                            //(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size
                                            if (viewModel.ejIndexState.value == 0) {
                                                onCycleChange(viewModel.cicleIndexState.value + 1)
                                                if (viewModel.cicleIndexState.value == totalCycleCount + 1) {
                                                    onNavigateToRoutine("routine-details/${viewModel.uiState.oneRoutine?.id}")
                                                    return@Button
                                                }
                                            }
                                        }
                                        /*saracatunga*/
                                    },
                                    colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.next),
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            if ((exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.get(viewModel.ejIndexState.value)?.duration ?: 0) != 0
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                                        onClick = {
                                            breakChange(!inBreak)
                                        },
                                        colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.rest),
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Spacer(
                                modifier = Modifier
                                    .height(30.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text= stringResource(id =R.string.nextInCycle),
                                    style = MaterialTheme.typography.titleMedium)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            HorizontalDivider(modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                                color= Color(0xFF000000))

                            Spacer(modifier = Modifier.height(10.dp))

                           exercisesMap[viewModel.uiState.cycles?.content?.get(viewModel.cicleIndexState.value)?.id]?.content?.drop(viewModel.ejIndexState.value + 1)?.forEach { ejercicio ->
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
                                                    text = "${ejercicio.exercise?.name}",
                                                    style = MaterialTheme.typography.titleLarge
                                                )

                                                Text(
                                                    text = "${ejercicio.exercise?.detail}",
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
}

/*
@Composable
fun TabletRutineExec2Layout(navController: NavHostController, viewModel : MainViewModel, exercisesMap: MutableMap<Int, NetworkCycleExercises>, inBreak: Boolean, breakChange: (Boolean)->Unit, timeRemainingSec: Int, timeRemainingSecChange: (Int)->Unit, inStop: Boolean, stopChange: (Boolean)->Unit, timeCountdown: Int, changeTimeCountdown: (Int)->Unit, onNavigateToRoutine: (String) -> Unit, cycleIndex: Int, exerciseIndex: Int, onExChange: (Int) -> Unit, onCycleChange: (Int) -> Unit) {
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
                    if(!(timeRemainingSec>0))timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)
                    //TODO: esto se rompe porque no esta circularizado!!!
                    changeTimeCountdown(rutina.cicles[cicleIndex].ejs[(ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size].duration*60*1000)
                    Box(modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                    ){
                        if(rutina.cicles[cicleIndex].ejs[ejIndex].duration!=0){
                            MyTimer(
                                seconds = timeRemainingSec,
                                onTimerFinish = {
                                    onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                    if (ejIndex == 0){
                                        onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                    }
                                    //if(cicleIndex==0 && ejIndex==0) //return since its finished
                                    stopChange(false)
                                    breakChange(false)
                                    //timeRemainingSecChange(rutina.cicles[cicleIndex].ejs[ejIndex].duration*60)

                                },
                                onTimerTick = timeRemainingSecChange,
                                inBreak = inBreak,
                                inStop = inStop,
                                stopChange = stopChange,
                                nextRemainingTime = timeCountdown
                            )
                        }
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

                                    if (rutina.cicles[cicleIndex].ejs[ejIndex].duration != 0) {
                                        stopChange(true)
                                        breakChange(true)
                                    } else {
                                        onEjIndexChange((ejIndex + 1) % rutina.cicles[cicleIndex].ejs.size)
                                        if (ejIndex == 0) onCicleIndexChange((cicleIndex + 1) % rutina.cicles.size)
                                    }
                                    /*saracatunga*/

                                },
                                colors = ButtonDefaults.buttonColors(Color(0xFF000000))
                            ) {
                                Text(
                                    text = stringResource(R.string.next),
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
                                onClick = {breakChange(!inBreak)},
                                colors = ButtonDefaults.buttonColors(Color(0xFF49454F))
                            ) {
                                Text(
                                    text = stringResource(R.string.rest),
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
                            Text(text= stringResource(id = R.string.nextInCycle),
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
*/