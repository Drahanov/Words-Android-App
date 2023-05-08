package com.words.domain.words.model

data class TranslationEntity(
    val exception_code: Any,
    val matches: List<Matche>,
    val mtLangSupported: Any,
    val quotaFinished: Boolean,
    val responderId: Any,
    val responseData: ResponseData,
    val responseDetails: String,
    val responseStatus: Int
)

data class Matche(
    val createDate: String,
    val createdBy: String,
    val id: Int,
    val lastUpdateDate: String,
    val lastUpdatedBy: String,
    val match: Double,
    val model: String,
    val quality: Int,
    val reference: String,
    val segment: String,
    val source: String,
    val subject: Boolean,
    val target: String,
    val translation: String,
    val usageCount: Int
)

data class ResponseData(
    val match: Double,
    val translatedText: String
)