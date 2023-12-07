package com.example.biteshub.dataclass

data class RegisterDataAccount(
    var username: String,
    var email: String,
    var password: String,
    var confirmPass: String
)

data class LoginDataAccount(
    var email: String,
    var password: String
)

data class ResponseDetail(
    var error: Boolean,
    var message: String
)

data class ResponseLogin(
    var error: Boolean,
    var message: String,
    var loginResult: LoginResult
)

data class LoginResult(
    var userId: String,
    var username: String,
    var token: String
)