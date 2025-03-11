package ig.ds.data.service

import ig.ds.data.jooq.Tables.FILE
import ig.ds.data.model.File
import jakarta.enterprise.context.ApplicationScoped
import org.jooq.DSLContext

@ApplicationScoped
class FileService(private val dsl: DSLContext) {

    fun addFile(file: File): Int {
        return dsl.insertInto(FILE)
            .set(FILE.FILE_ID, file.fileId)
            .set(FILE.FILE_NAME, file.fileName)
            .set(FILE.FILE_SIZE, file.fileSize)
            .execute()
    }

    /**
     * Retrieves a file by its ID.
     *
     * @param fileId The ID of the file to retrieve.
     * @return The File data class if found, or null if not found.
     */
    fun getFileById(fileId: String): File? {
        return dsl.selectFrom(FILE)
            .where(FILE.FILE_ID.eq(fileId))
            .fetchOne { record ->
                File(
                    fileId = record[FILE.FILE_ID],
                    fileName = record[FILE.FILE_NAME],
                    fileSize = record[FILE.FILE_SIZE]
                )
            }
    }

    /**
     * Retrieves all files from the attachments.file table.
     *
     * @return A list of File data classes.
     */
    fun getAllFiles(): List<File> {
        return dsl.selectFrom(FILE)
            .fetch { record ->
                File(
                    fileId = record[FILE.FILE_ID],
                    fileName = record[FILE.FILE_NAME],
                    fileSize = record[FILE.FILE_SIZE]
                )
            }
    }

    /**
     * Updates an existing file record.
     *
     * @param fileId The ID of the file to update.
     * @param newFileName The new name for the file.
     * @param newFileSize The new size for the file.
     * @return The number of rows affected (1 if successful).
     */
    fun updateFile(fileId: String, newFileName: String, newFileSize: Long): Int {
        return dsl.update(FILE)
            .set(FILE.FILE_NAME, newFileName)
            .set(FILE.FILE_SIZE, newFileSize)
            .where(FILE.FILE_ID.eq(fileId))
            .execute()
    }

    /**
     * Deletes a file record by its ID.
     *
     * @param fileId The ID of the file to delete.
     * @return The number of rows affected (1 if successful).
     */
    fun deleteFile(fileId: String): Int {
        return dsl.deleteFrom(FILE)
            .where(FILE.FILE_ID.eq(fileId))
            .execute()
    }
}