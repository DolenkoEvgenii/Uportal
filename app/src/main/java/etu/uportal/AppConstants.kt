package etu.uportal

object AppConstants {
    const val API_URL = "http://138.68.91.198:8080/"
    val HOST = API_URL.substring(0, API_URL.indexOf("/", API_URL.indexOf("://") + 3))
}
