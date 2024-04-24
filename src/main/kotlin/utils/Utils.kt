package utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import api.Resource
import org.jetbrains.skia.Image
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object Utils {

    fun <T> resourceWrapper(response: Resource<T>): T {
        return when (response) {
            is Resource.Success -> response.data!!
            is Resource.Error -> throw IllegalStateException(response.error)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun convertToImage(encodedImage: String): ImageBitmap {
        val byteArray = Base64.Mime.decode(encodedImage)
        return Image.makeFromEncoded(byteArray).toComposeImageBitmap()
    }

    fun convertToDate(dateStr: String): LocalDateTime {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))
    }

    fun formatDate(date: LocalDateTime): String {
        return "${date.month} ${date.dayOfMonth}, ${date.year} ${toMilitaryTime(date.hour, date.minute)}"
    }

    private fun toMilitaryTime(h: Int, m: Int): String {
        return SimpleDateFormat("hh:mm a").format(SimpleDateFormat("HH:mm").parse("${h}:${m}") ?: "01:00 AM")
    }
}