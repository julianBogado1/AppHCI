package hci.app.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.Date

class NetworkShortUser(
    @SerializedName("id")           var id: Int?,
    @SerializedName("username")     var username: String,
    @SerializedName("gender")       var gender: String? = null,
    @SerializedName("birthdate")    var birthdate: Date? = null,
    @SerializedName("avatarUrl")    var avatarUrl: String? = null,
    @SerializedName("date")         var date: Date,
    @SerializedName("lastActivity")    var lastActivity: Date? = null,
)