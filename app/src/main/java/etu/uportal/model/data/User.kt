package etu.uportal.model.data

data class User(
        val createdAt: Int,
        val email: String,
        val id: Int,
        val passwordHash: String,
        val roleId: Int,
        val updatedAt: Int
)