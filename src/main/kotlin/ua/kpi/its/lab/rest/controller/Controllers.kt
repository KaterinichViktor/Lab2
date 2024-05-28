package ua.kpi.its.lab.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.JournalRequest
import ua.kpi.its.lab.rest.dto.JournalResponse
import ua.kpi.its.lab.rest.svc.JournalService

@RestController
@RequestMapping("/journals")
class JournalController @Autowired constructor(
    private val journalService: JournalService
) {
    /**
     * Gets the list of all journals
     *
     * @return: List of JournalResponse
     */
    @GetMapping(path = ["", "/"])
    fun journals(): List<JournalResponse> = journalService.read()

    /**
     * Reads the journal by its id
     *
     * @param id: id of the journal
     * @return: JournalResponse for the given id
     */
    @GetMapping("{id}")
    fun readJournal(@PathVariable("id") id: Long): ResponseEntity<JournalResponse> {
        return wrapNotFound { journalService.readById(id) }
    }

    /**
     * Creates a new journal instance
     *
     * @param journal: JournalRequest with set properties
     * @return: JournalResponse for the created journal
     */
    @PostMapping(path = ["", "/"])
    fun createJournal(@RequestBody journal: JournalRequest): JournalResponse {
        return journalService.create(journal)
    }

    /**
     * Updates existing journal instance
     *
     * @param journal: JournalRequest with properties set
     * @return: JournalResponse of the updated journal
     */
    @PutMapping("{id}")
    fun updateJournal(
        @PathVariable("id") id: Long,
        @RequestBody journal: JournalRequest
    ): ResponseEntity<JournalResponse> {
        return wrapNotFound { journalService.updateById(id, journal) }
    }

    /**
     * Deletes existing journal instance
     *
     * @param id: id of the journal
     * @return: JournalResponse of the deleted journal
     */
    @DeleteMapping("{id}")
    fun deleteJournal(
        @PathVariable("id") id: Long
    ): ResponseEntity<JournalResponse> {
        return wrapNotFound { journalService.deleteById(id) }
    }

    fun <T> wrapNotFound(call: () -> T): ResponseEntity<T> {
        return try {
            // call function for result
            val result = call()
            // return "ok" response with result body
            ResponseEntity.ok(result)
        } catch (e: IllegalArgumentException) {
            // catch not-found exception
            // return "404 not-found" response
            ResponseEntity.notFound().build()
        }
    }
}
