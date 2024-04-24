package api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AuthResponseDeserializer : JsonDeserializer<AuthResource<*>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): AuthResource<*> {
        val jsonObject = json?.asJsonObject

        return when (jsonObject?.get("type")?.asString ?: "") {
            "success" -> {
                val data = context?.deserialize<Any>(jsonObject?.get("data"), (typeOfT as ParameterizedType).actualTypeArguments[0])
                    ?: IllegalStateException("Error")
                AuthResource.Success(data)
            }
            "validation_error" -> {
                val message = context?.deserialize<String>(jsonObject?.get("message"), String::class.java)
                    ?: ""
                AuthResource.ValidationError<Nothing>(message)
            }
            "error" -> {
                val error = context?.deserialize<String>(jsonObject?.get("error"), String::class.java)
                    ?: ""
                AuthResource.Error<Nothing>(error)
            }
            else -> throw IllegalStateException("Error")
        }
    }
}