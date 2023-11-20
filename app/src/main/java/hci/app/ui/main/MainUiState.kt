package hci.app.ui.main

import hci.app.data.model.Sport
import hci.app.data.model.User
import hci.app.data.model.Error
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.data.network.model.NetworkCycleExercisesContent
import hci.app.data.network.model.NetworkExercise
import hci.app.data.network.model.NetworkRoutineContent
import hci.app.data.network.model.NetworkRoutineCycleContent
import hci.app.data.network.model.NetworkRoutineCycles
import hci.app.data.network.model.NetworkRoutines


data class MainUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val sports: List<Sport>? = null,
    val currentSport: Sport? = null,
    val error: Error? = null,
    val isLoading: Boolean = false,
    val message: String = "",
    val routines: NetworkRoutines? = null,
    val cycles : NetworkRoutineCycles? = null,
    val oneRoutine : NetworkRoutineContent? = null,
    val oneCycle : NetworkRoutineCycleContent? = null,
    val exercises : NetworkCycleExercises? = null,
    val exercisesContent: NetworkCycleExercisesContent? = null,
    val oneExercise : NetworkExercise? = null,
)

val MainUiState.hasError: Boolean get() = error != null
val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllSports: Boolean get() = isAuthenticated
val MainUiState.canGetCurrentSport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canAddSport: Boolean get() = isAuthenticated && currentSport == null
val MainUiState.canModifySport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canDeleteSport: Boolean get() = canModifySport

val routines : NetworkRoutines? = null

val oneRoutine : NetworkRoutineContent? = null

val cycles : NetworkRoutineCycles? = null
val oneCycle : NetworkRoutineCycleContent? = null
val exercises : NetworkCycleExercises? = null
val exercisesContent: NetworkCycleExercisesContent? = null
val oneExercise : NetworkExercise? = null