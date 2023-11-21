package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkRoutineContent (

    //todo fijarse si el user puede ser null

    @SerializedName("id")           var id         : Int?      = null,
    @SerializedName("name")         var name       : String?   = null,
    @SerializedName("detail")       var detail     : String?   = null,
    @SerializedName("date")         var date       : Long?      = null,
    @SerializedName("score")        var score      : Int?      = null,
    @SerializedName("isPublic")     var isPublic   : Boolean?  = null,
    @SerializedName("difficulty")   var difficulty : String?   = null,
    @SerializedName("user")         var user       : NetworkShortUser?,
    @SerializedName("category")     var category   : Category? = Category(),
    @SerializedName("metadata")     var metadata   : String?   = null

)