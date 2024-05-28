package ua.kpi.its.lab.rest.repo

import org.springframework.data.jpa.repository.JpaRepository
import ua.kpi.its.lab.rest.entity.Journal
import ua.kpi.its.lab.rest.entity.ScientificArticle

interface JournalRepository : JpaRepository<Journal, Long> {

}

interface ScientificArticleRepository : JpaRepository<ScientificArticle, Long> {

}
