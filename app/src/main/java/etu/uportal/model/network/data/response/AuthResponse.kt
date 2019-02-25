package etu.uportal.model.network.data.response

data class AuthResponse(
        val accessToken: String,
        val expiresIn: Int,
        val refreshToken: String,
        val scope: String,
        val tokenType: String
)