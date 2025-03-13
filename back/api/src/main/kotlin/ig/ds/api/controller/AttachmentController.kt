package ig.ds.api.controller

import ig.ds.data.model.*
import ig.ds.data.service.AttachmentService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.reactive.PartType
import org.jboss.resteasy.reactive.RestForm


import org.jboss.resteasy.reactive.multipart.FileUpload



@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AttachmentController @Inject constructor(
    private val attachmentService: AttachmentService
) {

    @POST
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    fun uploadFile(
        @RestForm("file") @PartType(MediaType.APPLICATION_OCTET_STREAM) file: FileUpload
    ): File {

        val fileName = file.fileName()
        val fileSize = file.size()
        val fileInfo = attachmentService.addFile(file.fileName(), file.size(), file.uploadedFile().toFile().inputStream())
        return fileInfo

    }

    @GET
    @Path("/files/{fileId}")
    fun getFile(
        @HeaderParam("userId") userId: String?,
        @PathParam("fileId") fileId: String
    ): Response {
        val user = User("tester")
        val fileInfo = attachmentService.getFileInfo(user, fileId)
        val fileContent = attachmentService.getFileContent(user, fileId)

        return Response.ok(fileContent)
            .header("Content-Disposition", "attachment; filename=\"${fileInfo.fileName}\"")
//            .header("Content-Length", fileInfo.fileSize)
            .build()
    }

    @POST
    @Path("/attachments")
    fun addAttachment(
        @HeaderParam("userId") userId: String,
        attachment: Attachment
    ): Response {
        val user = User(userId)
        val attachmentId = attachmentService.addAttachment(user, attachment)
        return Response.ok(attachmentId).build()
    }

    @POST
    @Path("/signatures")
    fun addSignature(
        @HeaderParam("userId") userId: String,
        signature: Signature,
    ): Response {
        val user = User(userId)
        val signatureId = attachmentService.addSignature(user, signature)
        return Response.ok(signatureId).build()
    }

    @DELETE
    @Path("/attachments/{attachmentId}")
    fun deleteAttachment(
        @HeaderParam("userId") userId: String,
        @PathParam("attachmentId") attachmentId: String
    ): Response {
        val user = User(userId)
        attachmentService.deleteAttachment(user, attachmentId)
        return Response.ok().build()
    }

    @DELETE
    @Path("/signatures/{signatureId}")
    fun deleteSignature(
        @HeaderParam("userId") userId: String,
        @PathParam("signatureId") signatureId: String
    ): Response {
        val user = User(userId)
        attachmentService.deleteSignature(user, signatureId)
        return Response.ok().build()
    }

    @POST
    @Path("/attachments/query")
    fun getAttachments(
        @HeaderParam("userId") userId: String,
        queryOptions: QueryOptions
    ): List<Attachment> {
        val user = User(userId)
        val attachments = attachmentService.getAttachments(user, queryOptions)
        return attachments
    }
}