package hci.app.data.network.api

import hci.app.data.network.model.NetworkCredentials
import hci.app.data.network.model.NetworkCycleExercises
import hci.app.data.network.model.NetworkCycleExercisesContent
import hci.app.data.network.model.NetworkExercise
import hci.app.data.network.model.NetworkRoutineContent
import hci.app.data.network.model.NetworkRoutineCycleContent
import hci.app.data.network.model.NetworkRoutineCycles
import hci.app.data.network.model.NetworkRoutines
import hci.app.data.network.model.NetworkToken
import hci.app.data.network.model.NetworkUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiUserService {
    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>

    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>

    @GET("users/current/routines")
    suspend fun getRoutines(@Query("size") size : Int? = 90) : Response<NetworkRoutines>

    @GET("routines/{routineId}")
    suspend fun getOneRoutine(@Path("routineId")routineId : Int) : Response<NetworkRoutineContent>

    @GET("routines/{routineId}/cycles")
    suspend fun getCycles(@Path("routineId")routineId : Int) : Response<NetworkRoutineCycles>
    @GET("routines/{routineId}/cycles/{cycleId}")
    suspend fun getOneCycle(@Path("routineId")routineId : Int, @Path("cycleId")cycleId : Int) : Response<NetworkRoutineCycleContent>

   @GET("cycles/{cycleId}/exercises")
    suspend fun getCycleExercises(@Path("cycleId")cycleId : Int) : Response<NetworkCycleExercises>

    @GET("cycles/{cycleId}/exercises/{exerciseId}")
    suspend fun getOneCycleExercise(@Path("cycleId")cycleId : Int, @Path("exerciseId")exerciseId : Int) : Response<NetworkCycleExercisesContent>

    @GET("exercises")
    suspend fun getExercises(@Query("size")size : Int? = 90) : Response<NetworkCycleExercisesContent>
    @GET("exercises/{exerciseId}")
    suspend fun getOneExercise(@Path("exerciseId")exerciseId : Int) : Response<NetworkExercise>
}
