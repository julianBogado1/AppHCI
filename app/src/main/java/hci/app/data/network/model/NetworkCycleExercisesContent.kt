package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

class NetworkCycleExercisesContent {

    @SerializedName("exercise"   ) var isPublic   : NetworkExercise? = null
    @SerializedName("order") var id   : Int?      = null
    @SerializedName("order"      ) var order   : Int?      = null
    @SerializedName("repetitions") var repetitios   : Int?      = null
    @SerializedName("duration"   ) var duration   : Int?      = null
    @SerializedName("metadata"   ) var metadata   : String?   = null

}