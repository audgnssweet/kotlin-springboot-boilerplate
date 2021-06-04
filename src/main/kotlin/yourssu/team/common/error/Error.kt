package yourssu.team.common.error

data class Error(
    val status: Int,
    val code: String,
    val message: String
)