package hci.app

import android.app.Application
import hci.app.data.network.SportRemoteDataSource
import hci.app.data.network.UserRemoteDataSource
import hci.app.data.network.api.RetrofitClient
import hci.app.data.repository.SportRepository
import hci.app.data.repository.UserRepository
import hci.app.util.SessionManager


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object{
        lateinit var instance: MyApplication
            private set
    }
    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val sportRemoteDataSource: SportRemoteDataSource
        get() = SportRemoteDataSource(RetrofitClient.getApiSportService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val sportRepository: SportRepository
        get() = SportRepository(sportRemoteDataSource)
}