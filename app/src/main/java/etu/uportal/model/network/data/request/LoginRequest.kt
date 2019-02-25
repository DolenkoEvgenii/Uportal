package etu.uportal.model.network.data.request

data class LoginRequest(
        val username: String,
        val password: String,
        val grantType: String = "password",
        val clientId: String = "android",
        val clientSecret: String = "2secret2"
)