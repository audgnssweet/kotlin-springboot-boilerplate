package yourssu.team.common.error

enum class ErrorCode(val status: Int, val code: String, val message: String) {
    //auth
    AUTH_ROLE_LOW(403, "A001", "role low"),

    //member
    MEMBER_NOT_FOUND(404,"M001", "member not found"),
    MEMBER_PASSWD_MISMATCH(403, "M002", "password not match"),
    USERNAME_DUPLICATED(403, "M003", "username duplicated"),
    NICKNAME_DUPLICATED(403,"M004", "nickname duplicated"),

    //jwt
    JWT_EXPIRE(403, "JWT001", "token expired"),
    JWT_ERROR(400, "JWT002", "token error"),
    REFRESH_TOKEN_ERROR(400, "JWT003", "refreshToken is not valid")
}