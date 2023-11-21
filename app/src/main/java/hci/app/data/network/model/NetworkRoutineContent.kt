package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineContent (

    //todo fijarse si el user puede ser null

    @SerializedName("id")           var id         : Int?      = 0,
    @SerializedName("name")         var name       : String?   = "",
    @SerializedName("detail")       var detail     : String?   = "",
    @SerializedName("date")         var date       : Long?      = 0,
    @SerializedName("score")        var score      : Int?      = 0,
    @SerializedName("isPublic")     var isPublic   : Boolean?  = false,
    @SerializedName("difficulty")   var difficulty : String?   = "",
    @SerializedName("user")         var user       : NetworkShortUser?,
    @SerializedName("category")     var category   : Category? = Category(),
    @SerializedName("metadata")     var metadata   : NetworkRoutineContentMetadata? = NetworkRoutineContentMetadata()

)