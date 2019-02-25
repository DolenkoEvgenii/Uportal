package etu.uportal.model.network.data.request

data class RefreshRequest(
        val refreshToken: String,
        val grantType: String = "refresh_token",
        val clientId: String = "android",
        val clientSecret: String = "2secret2"
)