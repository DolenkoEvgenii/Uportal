package etu.uportal.utils

class PhoneUtil {
    companion object {
        fun formatPhone(phone: String): String {
            return phone.replace(" ", "").replace("-", "")
                    .replace("(", "").replace(")", "").replace("+", "")
        }

        fun formatPhoneForSend(phone: String): String {
            return "8" + formatPhoneWithoutStart(phone)
        }

        fun formatPhoneWithoutStart(phone: String): String {
            val cleanedString = formatPhone(phone)

            return if (cleanedString.isEmpty()) "" else cleanedString.substring(1)
        }
    }
}