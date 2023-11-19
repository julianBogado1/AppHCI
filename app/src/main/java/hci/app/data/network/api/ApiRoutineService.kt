package hci.app.data.network.api

import hci.app.data.network.model.NetworkRoutines

interface ApiRoutineService {

    @GET("routines")
    suspend fun getRoutines(): Response<NetworkRoutines>

    @GET("routine/{id}")
    suspend fun getRoutine(@Path("id") id: String): Response<NetworkRoutines>

}