package hci.app.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkToken(

    @SerializedName("token")
    var token: String
)