package hci.app.data.network

import hci.app.data.network.api.ApiUserService
import hci.app.data.network.model.NetworkCredentials
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.data.network.model.NetworkCycleExercisesContent
import hci.app.data.network.model.NetworkExercise
import hci.app.data.network.model.NetworkRoutineContent
import hci.app.data.network.model.NetworkRoutineCycleContent
import hci.app.data.network.model.NetworkRoutineCycles
import hci.app.data.network.model.NetworkRoutines
import hci.app.data.network.model.NetworkUser
import hci.app.util.SessionManager


class UserRemoteDataSource(
    private val sessionManager: SessionManager,
    private val apiUserService: ApiUserService
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            apiUserService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        sessionManager.removeAuthToken()
        handleApiResponse { apiUserService.logout() }
    }

    suspend fun getCurrentUser(): NetworkUser {
        return handleApiResponse { apiUserService.getCurrentUser() }
    }

    //todo chequear estos parametros
    suspend fun getRoutines() : NetworkRoutines{
        return handleApiResponse { apiUserService.getRoutines() }
    }
    suspend fun getOneRoutine(routineId : Int) : NetworkRoutineContent{
        return handleApiResponse { apiUserService.getOneRoutine(routineId) }
    }
    suspend fun getCycles(routineId : Int) : NetworkRoutineCycles{
        return handleApiResponse { apiUserService.getCycles(routineId) }
    }
    suspend fun getOneCycle(routineId : Int, cycleId : Int) : NetworkRoutineCycleContent{
        return handleApiResponse { apiUserService.getOneCycle(routineId, cycleId) }
    }
    suspend fun getCycleExercises(cycleId : Int) : NetworkCycleExercises {
        return handleApiResponse { apiUserService.getCycleExercises(cycleId) }
    }
    suspend fun getOneCycleExercise(routineId : Int, cycleId : Int) : NetworkCycleExercisesContent {
        return handleApiResponse { apiUserService.getOneCycleExercise(routineId, cycleId) }
    }
    suspend fun getExercises() : NetworkCycleExercisesContent {
        return handleApiResponse { apiUserService.getExercises() }
    }
    suspend fun getOneExercise(exerciseId : Int) : NetworkExercise {
        return handleApiResponse { apiUserService.getOneExercise(exerciseId) }
    }

}