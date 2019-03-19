package etu.uportal.model.models

import etu.uportal.model.data.User

object UsersModel {
    val userList: MutableList<User> = ArrayList()

    val offset: Int
        get() = userList.size

    val isEmpty: Boolean
        get() = userList.isEmpty()

    fun clear() {
        userList.clear()
    }

    fun addAll(users: List<User>) {
        userList.addAll(users)
    }
}