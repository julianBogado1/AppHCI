package hci.app.data.network

import hci.app.data.network.api.ApiUserService
import hci.app.data.network.model.NetworkCredentials
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
}