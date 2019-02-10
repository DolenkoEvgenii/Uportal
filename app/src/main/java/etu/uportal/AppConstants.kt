package etu.uportal

object AppConstants {
    const val API_API = ""
    val HOST = API_API.substring(0, API_API.indexOf("/", API_API.indexOf("://") + 3))
}
