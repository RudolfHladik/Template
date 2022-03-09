package com.rudolfhladik.kmm.template.data.api

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.*
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.rudolfhladik.kmm.template.data.database.DatabaseManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

internal object GraphQLManager {
    private val client: ApolloClient = ApolloClient.Builder()
        .httpServerUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .build()

    private val dbManager = DatabaseManager

    suspend fun <D : Query.Data> executeQuery(query: Query<D>): D {
        val call = client.query(query)
        return executeOperation(call)
    }

    suspend fun <D : Mutation.Data> executeMutation(mutation: Mutation<D>): D {
        val call = client.mutation(mutation)
            .apply {
                val token = getAuthorizationHeader()
                addHttpHeader("Authorization", token)
            }
        return executeOperation(call)
    }

    private suspend fun getAuthorizationHeader(): String = dbManager.getToken()?.token ?: ""

    fun <D : Subscription.Data> executeSubscription(subscription: Subscription<D>): Flow<D> =
        client.subscription(subscription).toFlow()
            .map { unfoldResultGraphQl(it) }

    private suspend fun <D : Operation.Data> executeOperation(apolloCall: ApolloCall<D>): D {
        val result: Result<D> = runCatching {
            val response: ApolloResponse<D> = apolloCall.toFlow().single()

            unfoldResultGraphQl(response)
        }
        result.fold(
            onSuccess = { data ->
                return data
            },
            onFailure = { throwable ->
                throw when (throwable) {
                    is ApolloNetworkException -> CloudApiException(
                        message = throwable.message ?: throwable.cause?.message,
                        errorCode = CloudErrorCode.UNKNOWN,
                        cause = throwable.cause
                    )
                    else -> throwable
                }
            }
        )
    }

    private fun <T : Operation.Data> unfoldResultGraphQl(response: ApolloResponse<T>): T =
        if (response.hasErrors().not()) {
            response.data ?: error("Data is null")
        } else {
            val error = response.errors?.firstOrNull()
            throw CloudApiException(
                message = error?.message ?: "",
                errorCode = getResponseErrorCode(error),
                cause = null,
            )
        }

    private fun getResponseErrorCode(error: Error?): CloudErrorCode =
        CloudErrorCode.from(error?.extensions?.get("code") as? String)
}

class CloudApiException(
    message: String?,
    val errorCode: CloudErrorCode,
    cause: Throwable?
) : RuntimeException(message, cause) {
    override val message: String?
        get() = super.cause?.message ?: super.message
}

data class CloudErrorCode(val code: String) {

    companion object {
        val UNAUTHORIZED = CloudErrorCode("UNAUTHORIZED")
        val CONFLICT = CloudErrorCode("CONFLICT")
        val UNKNOWN = CloudErrorCode("UNKNOWN")
        val UNPROCESSABLE_ENTITY = CloudErrorCode("UNPROCESSABLE_ENTITY")
        val SEMANTIC_ERROR = CloudErrorCode("SEMANTIC_ERROR")

        fun from(value: String?): CloudErrorCode = value?.let { CloudErrorCode(value) } ?: UNKNOWN
    }

    fun isUnauthorized(): Boolean = code.startsWith(UNAUTHORIZED.code)

    fun isConflict(): Boolean = code.startsWith(CONFLICT.code)

    fun isUnprocessableEntity(): Boolean = code.startsWith(UNPROCESSABLE_ENTITY.code)

    fun isSemanticError(): Boolean = code.startsWith(SEMANTIC_ERROR.code)
}
