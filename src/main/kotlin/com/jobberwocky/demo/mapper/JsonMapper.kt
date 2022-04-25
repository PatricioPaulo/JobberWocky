package com.jobberwocky.demo.mapper

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

enum class JsonMapper {

    INSTANCE;
    val mapper: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
            .create()

}

