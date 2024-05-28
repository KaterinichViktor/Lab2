package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.dto.JournalRequest
import ua.kpi.its.lab.rest.dto.JournalResponse

interface JournalService {
    /**
     * Creates a new Journal record.
     *
     * @param journal: The JournalRequest instance to be inserted
     * @return: The recently created JournalResponse instance
     */
    fun create(journal: JournalRequest): JournalResponse

    /**
     * Reads all created Journal records.
     *
     * @return: List of created JournalResponse records
     */
    fun read(): List<JournalResponse>

    /**
     * Reads a Journal record by its id.
     * The order is determined by the order of creation.
     *
     * @param id: The id of JournalRequest record
     * @return: The JournalResponse instance at index
     */
    fun readById(id: Long): JournalResponse

    /**
     * Updates a JournalRequest record data.
     *
     * @param id: The id of the Journal instance to be updated
     * @param journal: The JournalRequest with new Journal values
     * @return: The updated JournalResponse record
     */
    fun updateById(id: Long, journal: JournalRequest): JournalResponse

    /**
     * Deletes a JournalRequest record by its index.
     * The order is determined by the order of creation.
     *
     * @param id: The id of Journal record to delete
     * @return: The deleted JournalResponse instance at index
     */
    fun deleteById(id: Long): JournalResponse
}
