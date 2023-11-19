package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkExercise {

    @SerializedName("id"         ) var id         : Int?      = null,
    @SerializedName("name"       ) var name       : String?   = null,
    @SerializedName("detail"     ) var detail     : String?   = null,
    @SerializedName("date"       ) var date       : Int?      = null,
    @SerializedName("type"       ) var score      : String?   = null,
    @SerializedName("order"      ) var isPublic   : Int?      = null,
    @SerializedName("metadata"   ) var metadata   : String?   = null

}