package hci.app.ui.main

import android.content.Context
import android.util.Log
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import hci.app.BuildConfig
import hci.app.data.DataSourceException
import hci.app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Date
import hci.app.data.network.*
import hci.app.data.network.api.ApiDateTypeAdapter
import hci.app.data.network.api.AuthInterceptor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


interface ApiUserService {

    @POST("users/login")
    suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>
}
/*
    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/current")
    suspend fun getCurrentUser(): Response<NetworkUser>
}

private fun buildRetrofit(context: Context): Retrofit {}

*/
suspend fun apiCall(context: Context){

    val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(context))
        .addInterceptor(httpLoggingInterceptor)
        .build()

    //val myData = MyDataClass(/* initialize with your data */)
    val gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, ApiDateTypeAdapter())
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
    val myApi = retrofit.create(ApiUserService::class.java)

    try {
        val result = handleApiResponse {
            myApi.login(NetworkCredentials("johndoe", "1234567890"))
        }
        println("Result: $result")
        Log.d("User", "{$result}")
    } catch (e: DataSourceException) {
        println("Error: ${e.message}")
    }
}

fun saracatunga(context: Context) = runBlocking {
    launch{apiCall(context)}
}

/*
fun getApiUserService(context: Context): ApiUserService {
    return buildRetrofit(context).create(ApiUserService::class.java)
}


@POST("users/login")
suspend fun login(@Body credentials: NetworkCredentials): Response<NetworkToken>{
    return handleApiResponse {
        apiUserService.login(credentials)
    }
}
@POST("users/logout")
suspend fun logout(): Response<Unit>{}
@GET("users/current")
suspend fun getCurrentUser(): Response<NetworkUser>{}
@GET("sports")
suspend fun getSports(@Query("size") size: Int = 50): Response<NetworkPagedContent<NetworkSport>>{}

@POST("sports")
suspend fun addSport(@Body sport: NetworkSport): Response<NetworkSport>{}

@GET("sports/{sportId}")
suspend fun getSport(@Path("sportId") sportId: Int): Response<NetworkSport>{}

@PUT("sports/{sportId}")
suspend fun modifySport(
@Path("sportId") sportId: Int,
@Body sport: NetworkSport
): Response<NetworkSport>{}

@DELETE("sports/{sportId}")
suspend fun deleteSport(@Path("sportId") sportId: Int): Response<Unit>{}

class SportRemoteDataSource(
private val apiSportService: ApiSportService
) : RemoteDataSource() {

suspend fun getSports(): NetworkPagedContent<NetworkSport> {
    return handleApiResponse {
        apiSportService.getSports()
    }
}

suspend fun getSport(sportId: Int): NetworkSport {
    return handleApiResponse {
        apiSportService.getSport(sportId)
    }
}

suspend fun addSport(sport: NetworkSport): NetworkSport {
    return handleApiResponse {
        apiSportService.addSport(sport)
    }
}

suspend fun modifySport(sport: NetworkSport): NetworkSport {
    return handleApiResponse {
        apiSportService.modifySport(sport.id!!, sport)
    }
}

suspend fun deleteSport(sportId: Int) {
    handleApiResponse {
        apiSportService.deleteSport(sportId)
    }
}
}*/

suspend fun <T : Any> handleApiResponse(
    execute: suspend () -> Response<T>
): T {
    val CONNECTION_ERROR_CODE = 98
    val UNEXPECTED_ERROR_CODE = 99
    try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            return body
        }
        response.errorBody()?.let {
            val gson = Gson()
            val error = gson.fromJson<NetworkError>(
                it.string(),
                object : TypeToken<NetworkError?>() {}.type
            )
            throw DataSourceException(error.code, error.description, error.details)
        }
        throw DataSourceException(UNEXPECTED_ERROR_CODE, "Missing error", null)
    } catch (e: DataSourceException) {
        throw e
    } catch (e: IOException) {
        throw DataSourceException(
            CONNECTION_ERROR_CODE,
            "Connection error",
            getDetailsFromException(e)
        )
    } catch (e: Exception) {
        throw DataSourceException(
            UNEXPECTED_ERROR_CODE,
            "Unexpected error",
            getDetailsFromException(e)
        )
    }
}

private fun getDetailsFromException(e: Exception): List<String> {
    return if (e.message != null) listOf(e.message!!) else emptyList()
}



data class NetworkCredentials(

    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
)

class NetworkUser(

    @SerializedName("id")
    var id: Int?,
    @SerializedName("username")
    var username: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("gender")
    var gender: String? = null,
    @SerializedName("birthdate")
    var birthdate: Date? = null,
    @SerializedName("email")
    var email: String,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("avatarUrl")
    var avatarUrl: String? = null,
    @SerializedName("metadata")
    var metadata: NetworkUserMetadata? = null,
    @SerializedName("date")
    var date: Date,
    @SerializedName("lastActivity")
    var lastActivity: Date? = null,
    @SerializedName("verified")
    var verified: Boolean
) {

    fun asModel(): User {
        return User(
            id = id,
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            lastActivity = lastActivity
        )
    }
}
data class NetworkToken(

    @SerializedName("token")
    var token: String
)
data class NetworkPagedContent<T>(

    @SerializedName("totalCount")
    var totalCount: Int,
    @SerializedName("orderBy")
    var orderBy: String? = null,
    @SerializedName("direction")
    var direction: String? = null,
    @SerializedName("content")
    var content: List<T> = arrayListOf(),
    @SerializedName("size")
    var size: Int,
    @SerializedName("page")
    var page: Int,
    @SerializedName("isLastPage")
    var isLastPage: Boolean
)
/*
class NetworkSport(

    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String,
    @SerializedName("detail")
    var detail: String? = null
) {

    fun asModel(): Sport {
        return Sport(
            id = id,
            name = name,
            detail = detail
        )
    }
}
*/
data class NetworkError(

    @SerializedName("code")
    val code: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    var details: List<String>? = null
)
class NetworkUserMetadata