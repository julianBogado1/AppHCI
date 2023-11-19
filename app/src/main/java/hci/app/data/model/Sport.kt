package hci.app.data.model

import hci.app.data.network.model.NetworkSport


class Sport(
    var id: Int? = null,
    var name: String,
    var detail: String? = null
) {
    fun asNetworkModel(): NetworkSport {
        return NetworkSport(
            id = id,
            name = name,
            detail = detail
        )
    }
}

data class Routine(
    val name: String,
    val description: String,
    val rating: Int,
    val duration: Int,
    val dUnit: String
)

data class Cycle(
    val name: String,
    val repetitions: Int
)

data class Exercise(
    val name: String,
    val description: String,
    val repetitions: Int,
    val duration: Int
)
