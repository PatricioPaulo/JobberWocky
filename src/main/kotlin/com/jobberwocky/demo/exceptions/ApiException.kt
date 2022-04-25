package com.jobberwocky.demo.exceptions


import com.jobberwocky.demo.mapper.JsonMapper
import java.lang.Exception

/**
 * The type Api exception.
 */
open class ApiException : Exception {
    /**
     * Gets code.
     *
     * @return the code
     */
    val code: String

    /**
     * Gets description.
     *
     * @return the description
     */
    val description: String

    /**
     * Gets status code.
     *
     * @return the status code
     */
    var statusCode = 500
        private set

    /**
     * Instantiates a new Generic Api exception.
     *
     * @param code        the code
     * @param description the description
     */
    constructor(code: String, description: String) : super(code + description) {
        this.code = code
        this.description = description
    }

    /**
     * Instantiates a new Generic Api exception.
     *
     * @param code        the code
     * @param description the description
     * @param statusCode  the status code
     */
    constructor(code: String, description: String, statusCode: Int) : super("$code $description") {
        this.code = code
        this.description = description
        this.statusCode = statusCode
    }

    /**
     * To json string.
     *
     * @return the string
     */
    fun toJson(): String {
        val exceptionMap: MutableMap<String, Any> = HashMap()
        exceptionMap["error"] = code
        exceptionMap["message"] = description
        exceptionMap["status"] = statusCode
        return JsonMapper.INSTANCE.mapper.toJson(exceptionMap)
    }
}
