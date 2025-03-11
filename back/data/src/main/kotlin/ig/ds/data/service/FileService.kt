package ig.ds.data.service

import ig.ds.data.jooq.Tables.FILE
import ig.ds.data.model.File
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.jooq.DSLContext
import java.io.InputStream


@ApplicationScoped
class FileService @Inject constructor(
    private val dsl: DSLContext,
    private val s3Service: S3Service
) {


    fun addFile(fileInfo: File, fileContentStream: InputStream) {
        s3Service.addFile(fileInfo.fileId!!, fileContentStream, fileInfo.fileSize)
        dsl.insertInto(FILE)
            .set(FILE.FILE_ID, fileInfo.fileId)
            .set(FILE.FILE_NAME, fileInfo.fileName)
            .set(FILE.FILE_SIZE, fileInfo.fileSize)
            .execute()
    }

    fun getFileInfo(fileId: String): File? {
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

    fun getFileContent(fileId: String):InputStream{
        return  s3Service.getFile(fileId)
    }


}