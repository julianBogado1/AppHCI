package hci.app

import android.app.Application
import hci.app.data.network.UserRemoteDataSource
import hci.app.data.network.api.RetrofitClient
import hci.app.util.SessionManager


class MyApplication : Application() {
/*
    private val database : MyDatabase
        get() = MyDatabase.getInstance(this)

    val sessionManager: SessionManager
        get() = SessionManager(this)

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val sportLocalDataSource: SportLocalDataSource
        get() = SportLocalDataSource(database.sportDao())

    private val sportRemoteDataSource: SportRemoteDataSource
        get() = SportRemoteDataSource(RetrofitClient.getApiSportService(this))

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val sportRepository: SportRepository
        get() = SportRepository(sportLocalDataSource, sportRemoteDataSource)*/
}