package ua.kpi.its.lab.rest.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "journals")
class Journal(
    @Column
    var name: String,

    @Column
    var theme: String,

    @Column
    var language: String,

    @Column
    var foundingDate: Date,

    @Column
    var issn: String,

    @Column
    var recommendedPrice: BigDecimal,

    @Column
    var isPeriodic: Boolean,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "journal")
    var articles: MutableList<ScientificArticle>
) : Comparable<Journal> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: Journal): Int {
        val equal = this.name == other.name && this.foundingDate.time == other.foundingDate.time
        return if (equal) 0 else 1
    }

    override fun toString(): String {
        return "Journal(name=$name, foundingDate=$foundingDate, articles=$articles)"
    }
}

@Entity
@Table(name = "scientific_articles")
class ScientificArticle(
    @Column
    var title: String,

    @Column
    var author: String,

    @Column
    var writingDate: Date,

    @Column
    var wordCount: Int,

    @Column
    var citationCount: Int,

    @Column
    var isOriginalLanguage: Boolean,

    @ManyToOne
    @JoinColumn(name = "journal_id", referencedColumnName = "id")
    var journal: Journal? = null
): Comparable<ScientificArticle> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: ScientificArticle): Int {
        val equal = this.title == other.title && this.writingDate.time == other.writingDate.time
        return if (equal) 0 else 1
    }

    override fun toString(): String {
        return "ScientificArticle(title=$title, writingDate=$writingDate, wordCount=$wordCount)"
    }
}