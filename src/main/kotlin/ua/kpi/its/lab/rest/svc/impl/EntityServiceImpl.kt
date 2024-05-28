package ua.kpi.its.lab.rest.svc.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.dto.ScientificArticleResponse
import ua.kpi.its.lab.rest.dto.JournalRequest
import ua.kpi.its.lab.rest.dto.JournalResponse
import ua.kpi.its.lab.rest.entity.ScientificArticle
import ua.kpi.its.lab.rest.entity.Journal
import ua.kpi.its.lab.rest.repo.JournalRepository
import ua.kpi.its.lab.rest.svc.JournalService
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
class JournalServiceImpl @Autowired constructor(
    private val repository: JournalRepository
) : JournalService {
    override fun create(journal: JournalRequest): JournalResponse {
        val articles = journal.articles.map {
            ScientificArticle(
                title = it.title,
                author = it.author,
                writingDate = this.stringToDate(it.writingDate),
                wordCount = it.wordCount,
                citationCount = it.citationCount,
                isOriginalLanguage = it.isOriginalLanguage
            )
        }.toMutableList()
        val newJournal = Journal(
            name = journal.name,
            theme = journal.theme,
            language = journal.language,
            foundingDate = this.stringToDate(journal.foundingDate),
            issn = journal.issn,
            recommendedPrice = this.stringToPrice(journal.recommendedPrice),
            isPeriodic = journal.isPeriodic,
            articles = articles
        )
        newJournal.articles.forEach { it.journal = newJournal }
        val savedJournal = this.repository.save(newJournal)
        return this.journalEntityToDto(savedJournal)
    }

    override fun read(): List<JournalResponse> {
        return this.repository.findAll().map(this::journalEntityToDto)
    }

    override fun readById(id: Long): JournalResponse {
        val journal = this.getJournalById(id)
        return this.journalEntityToDto(journal)
    }

    override fun updateById(id: Long, journal: JournalRequest): JournalResponse {
        val oldJournal = this.getJournalById(id)

        oldJournal.apply {
            name = journal.name
            theme = journal.theme
            language = journal.language
            foundingDate = this@JournalServiceImpl.stringToDate(journal.foundingDate)
            issn = journal.issn
            recommendedPrice = this@JournalServiceImpl.stringToPrice(journal.recommendedPrice)
            isPeriodic = journal.isPeriodic
        }

        oldJournal.articles.clear()
        oldJournal.articles.addAll(journal.articles.map {
            ScientificArticle(
                title = it.title,
                author = it.author,
                writingDate = this@JournalServiceImpl.stringToDate(it.writingDate),
                wordCount = it.wordCount,
                citationCount = it.citationCount,
                isOriginalLanguage = it.isOriginalLanguage,
                journal = oldJournal
            )
        })

        val newJournal = this.repository.save(oldJournal)
        return this.journalEntityToDto(newJournal)
    }

    override fun deleteById(id: Long): JournalResponse {
        val journal = this.getJournalById(id)
        this.repository.delete(journal)
        return this.journalEntityToDto(journal)
    }

    private fun getJournalById(id: Long): Journal {
        return this.repository.findById(id).getOrElse {
            throw IllegalArgumentException("Journal not found by id = $id")
        }
    }

    private fun journalEntityToDto(journal: Journal): JournalResponse {
        return JournalResponse(
            id = journal.id,
            name = journal.name,
            theme = journal.theme,
            language = journal.language,
            foundingDate = this.dateToString(journal.foundingDate),
            issn = journal.issn,
            recommendedPrice = this.priceToString(journal.recommendedPrice),
            isPeriodic = journal.isPeriodic,
            articles = journal.articles.map {
                ScientificArticleResponse(
                    id = it.id,
                    title = it.title,
                    author = it.author,
                    writingDate = this.dateToString(it.writingDate),
                    wordCount = it.wordCount,
                    citationCount = it.citationCount,
                    isOriginalLanguage = it.isOriginalLanguage
                )
            }
        )
    }

    private fun dateToString(date: Date): String {
        val instant = date.toInstant()
        val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    private fun stringToDate(date: String): Date {
        val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val instant = dateTime.toInstant(ZoneOffset.UTC)
        return Date.from(instant)
    }

    private fun priceToString(price: BigDecimal): String = price.toString()

    private fun stringToPrice(price: String): BigDecimal = BigDecimal(price)
}
