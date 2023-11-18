package hci.app.data.network.model

import com.google.gson.annotations.SerializedName


data class Category (

    @SerializedName("id"     ) var id     : Int?    = null,
    @SerializedName("name"   ) var name   : String? = null,
    @SerializedName("detail" ) var detail : String? = null

)