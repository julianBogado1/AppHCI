package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

class NetworkExercise(

    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("name"     ) var name     : String? = null,
    @SerializedName("detail"   ) var detail   : String? = null,
    @SerializedName("type"     ) var type     : String? = null,
    @SerializedName("date"     ) var date     : Long?    = null,
    @SerializedName("metadata" ) var metadata : String? = null

)