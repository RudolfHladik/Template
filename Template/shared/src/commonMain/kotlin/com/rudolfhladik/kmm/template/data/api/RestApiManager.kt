package com.rudolfhladik.kmm.template.data.api

import com.rudolfhladik.kmm.template.createHttpClient
import com.rudolfhladik.kmm.template.data.Endpoint
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.http.HttpMethod
import io.ktor.http.Url
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

internal object RestApiManager {
    private val httpClient = createHttpClient()

    private val json = Json(Json) {
        isLenient = true
        ignoreUnknownKeys = true
    }

    suspend fun <R : Any> executeRequest(ep: Endpoint<R>) =
        execute(
            url = Url(ep.url),
            method = ep.method,
            responseSerializer = ep.responseSerializer
        )

    private suspend fun <R : Any> execute(
        url: Url,
        headers: Map<String, String> = mapOf(),
        method: HttpMethod,
//        body: B?,
        responseSerializer: KSerializer<R>? = null,
//        bodySerializer: KSerializer<B>? = null
    ): R {
        val response: HttpResponse = runCatching {
            httpClient.request<HttpResponse> {
                url(url)
                header("Accept", "application/json")
                headers.forEach {
                    header(it.key, it.value)
                }
                this.method = method
//                if (body != null) {
//                    this.body =
//                        json.encodeToString(
//                            serializer = bodySerializer ?: error("Missing body serializer"),
//                            value = body
//                        )
//                }
            }
        }.getOrThrow()

        return if (response.isSuccessful()) {
            val bodyContent = response.readText()
            if (responseSerializer == null) {
                @Suppress("UNCHECKED_CAST")
                Unit as R
            } else {
                json.decodeFromString(responseSerializer, bodyContent)
            }
        } else {
            error("$url ${response.status.value} ")
        }
    }

    private fun HttpResponse.isSuccessful(): Boolean = status.value in 200..299
}
