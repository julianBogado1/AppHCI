package hci.app.ui.main

import hci.app.data.model.Sport
import hci.app.data.model.User
import hci.app.data.model.Error


data class MainUiState(
    val isAuthenticated: Boolean = false,
    val isFetching: Boolean = false,
    val currentUser: User? = null,
    val sports: List<Sport>? = null,
    val currentSport: Sport? = null,
    val error: Error? = null,
    val isLoading: Boolean = false,
    val message: String = "",
)

val MainUiState.hasError: Boolean get() = error != null
val MainUiState.canGetCurrentUser: Boolean get() = isAuthenticated
val MainUiState.canGetAllSports: Boolean get() = isAuthenticated
val MainUiState.canGetCurrentSport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canAddSport: Boolean get() = isAuthenticated && currentSport == null
val MainUiState.canModifySport: Boolean get() = isAuthenticated && currentSport != null
val MainUiState.canDeleteSport: Boolean get() = canModifySport
