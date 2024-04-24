package api

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor(
    private val preferences: User,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldNotAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Unauthorized::class }

        return if (shouldNotAttachAuthHeader) {
            chain.proceed(chain.request())
        } else {
            runBlocking {
                chain.proceed(chain.request().newBuilder().addHeader("Authorization", preferences.token).build())
            }
        }
    }
}