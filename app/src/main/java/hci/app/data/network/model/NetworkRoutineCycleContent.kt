package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineCycleContent (

    @SerializedName("id"         ) var id         : Int?      = null,
    @SerializedName("name"       ) var name       : String?   = null,
    @SerializedName("detail"     ) var detail     : String?   = null,
    @SerializedName("date"       ) var date       : String?   = null,
    @SerializedName("score"      ) var score      : Int?      = null,
    @SerializedName("repetitions") var repetitions: Int?  = null,
    @SerializedName("metadata"   ) var metadata   : String?   = null

)