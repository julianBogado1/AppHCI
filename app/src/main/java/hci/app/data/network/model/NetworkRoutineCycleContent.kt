package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineCycleContent (

    @SerializedName("id"         ) var id         : Int?      = 0,
    @SerializedName("name"       ) var name       : String?   = "",
    @SerializedName("detail"     ) var detail     : String?   = "",
    @SerializedName("date"       ) var date       : Long?   = 0,
    @SerializedName("score"      ) var score      : Int?      = 0,
    @SerializedName("repetitions") var repetitions: Int?  = 0,
    @SerializedName("metadata"   ) var metadata   : String?   = null

)