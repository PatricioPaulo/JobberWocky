package com.jobberwocky.demo.utils

import com.google.inject.Inject
import com.jobberwocky.demo.exceptions.BadRequestException
import javax.validation.ConstraintViolation
import javax.validation.Validator
import kotlin.jvm.Throws

class EntityValidator<T> {

    @Inject
    var validator : Validator? = null

    @Throws(BadRequestException::class)
    fun validate(entity: T) {
        val violations = validator!!.validate(entity)
        val sb = StringBuilder()
        var count = 1
        for (violation in violations) {
            if (count > 1) sb.append(" | ")
            sb.append(count)
                    .append("- ")
                    .append(violation.message)
            count++
        }
        if (violations.isNotEmpty()) throw BadRequestException(sb.toString())
    }
}