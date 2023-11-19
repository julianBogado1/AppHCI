package hci.app.data.network.api

import hci.app.data.network.model.NetworkCredentials
import hci.app.data.network.model.NetworkRoutines
import hci.app.data.network.model.NetworkToken
import hci.app.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiUserService {
    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>

    @GET("routines")
    suspend fun getRoutines(categoryId : Int? = null, userId : Int? = null, difficulty : String? = null, score : Int? = null,
                            search : String? = null, page : Int? = null, size : Int? = null, orderBy : String? = null,
                            direction : String? = null) : Response<ArrayList<NetworkRoutines>>
}
