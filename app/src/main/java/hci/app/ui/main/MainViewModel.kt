package hci.app.ui.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hci.app.data.DataSourceException
import hci.app.data.model.Sport
import hci.app.data.repository.SportRepository
import hci.app.data.repository.UserRepository
import hci.app.util.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import hci.app.data.model.Error
import hci.app.data.network.UserRemoteDataSource
import hci.app.data.network.model.NetworkRoutines

class MainViewModel(private val sessionManager: SessionManager,private val userRepository: UserRepository,
                    private val sportRepository: SportRepository, private val userRemoteDataSource: UserRemoteDataSource) : ViewModel() {

    var uiState by mutableStateOf(MainUiState(isAuthenticated = sessionManager.loadAuthToken() != null))
        private set

    fun printAuth() : Unit{
        Log.d("SARACATUNGA", sessionManager.loadAuthToken().toString())
    }

    private var fetchJob: Job? = null

    fun dismissMessage(){
       uiState = uiState.copy(message = "") //todo hubo error de nullable--> clase min 2:34 porlas
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, message="")
        runCatching{userRepository.login(username, password)}
            .onSuccess { uiState = uiState.copy(isFetching = false, isAuthenticated = true)
                        getCurrentUser()}
            .onFailure { e -> uiState = uiState.copy(message = e.message!!, isFetching = false) }
    }
    fun logout() = viewModelScope.launch{
        uiState = uiState.copy(isFetching = true, message="")
        runCatching{ userRepository.logout() }
            .onSuccess { uiState = uiState.copy(isFetching = false, isAuthenticated = false,currentUser = null,
                currentSport = null, sports = null) }
            .onFailure { e -> uiState = uiState.copy(message = e.message!!, isFetching = false, isAuthenticated = false,
                currentUser = null, currentSport = null, sports = null) }
    }

    fun getCurrentUser(){
        //fetchJob?.cancel()
        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRepository.getCurrentUser(uiState.currentUser == null)
            }.onSuccess { response ->
                uiState = uiState.copy(currentUser = response, isLoading = false)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }

    fun getRoutines(){
        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getRoutines()
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, routines = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }

    fun getOneRoutine(routineId : Int){

        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getOneRoutine(routineId)
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, oneRoutine = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }
    fun getCycles(routineId : Int){

        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getCycles(routineId)
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, cycles = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }
    fun getOneCycle(routineId : Int, cycleId : Int){

        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getOneCycle(routineId, cycleId)
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, oneCycle = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }

    fun getCycleExercises(cycleId : Int){

        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getCycleExercises(cycleId)
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, exercises = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }
    /*
    * DEVUELVE NetworkCycleExercisesContent
    * exercisesContent
    * */
    fun getOneCycleExercise(cycleId : Int, exerciseId : Int){
        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getOneCycleExercise(cycleId, exerciseId)
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, exercisesContent = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }

    /*
    * DEVUELVE NetworkCycleExercisesContent
    * asignar a exercisesContent
    * */
    fun getExercises(){
        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getExercises()
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, exercisesContent = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }
    fun getOneExercise(exerciseId : Int){

        viewModelScope.launch{
            uiState = uiState.copy(isLoading = true)
            runCatching {
                userRemoteDataSource.getOneExercise(exerciseId)
            }.onSuccess { response ->
                uiState = uiState.copy(isLoading = false, oneExercise = response)
            }.onFailure { e ->
                uiState = uiState.copy(isLoading = false, error = handleError(e), message = e.message?:"")
            }
        }
    }


    private fun <R> runOnViewModelScope(
        block: suspend () -> R,
        updateState: (MainUiState, R) -> MainUiState
    ): Job = viewModelScope.launch {
        uiState = uiState.copy(isFetching = true, error = null)
        runCatching {
            block()
        }.onSuccess { response ->
            uiState = updateState(uiState, response).copy(isFetching = false)
        }.onFailure { e ->
            uiState = uiState.copy(isFetching = false, error = handleError(e))    //handleError(e)) iria aca todo
        }
    }

    private fun handleError(e: Throwable): Error {
        return if (e is DataSourceException) {
            Error(e.code, e.message ?: "", e.details)
        } else {
            Error(null, e.message ?: "", null)
        }
    }
}