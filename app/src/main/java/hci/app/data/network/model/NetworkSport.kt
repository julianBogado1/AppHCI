package hci.app.data.network.model

import com.google.gson.annotations.SerializedName
import hci.app.data.model.Sport

class NetworkSport(

    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String,
    @SerializedName("detail")
    var detail: String? = null
) {

    fun asModel(): Sport {
        return Sport(
            id = id,
            name = name,
            detail = detail
        )
    }
}