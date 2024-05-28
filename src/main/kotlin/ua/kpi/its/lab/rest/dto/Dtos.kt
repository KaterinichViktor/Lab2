package ua.kpi.its.lab.rest.dto

data class JournalRequest(
    var name: String,
    var theme: String,
    var language: String,
    var foundingDate: String,
    var issn: String,
    var recommendedPrice: String,
    var isPeriodic: Boolean,
    var articles: List<ScientificArticleRequest>
)

data class JournalResponse(
    var id: Long,
    var name: String,
    var theme: String,
    var language: String,
    var foundingDate: String,
    var issn: String,
    var recommendedPrice: String,
    var isPeriodic: Boolean,
    var articles: List<ScientificArticleResponse>
)

data class ScientificArticleRequest(
    var title: String,
    var author: String,
    var writingDate: String,
    var wordCount: Int,
    var citationCount: Int,
    var isOriginalLanguage: Boolean
)

data class ScientificArticleResponse(
    var id: Long,
    var title: String,
    var author: String,
    var writingDate: String,
    var wordCount: Int,
    var citationCount: Int,
    var isOriginalLanguage: Boolean
)
