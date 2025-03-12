package ig.ds.controller

import ig.ds.data.model.*
import ig.ds.data.service.AttachmentService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.io.InputStream

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AttachmentController @Inject constructor(
    private val attachmentService: AttachmentService
) {

    @GET
    @Path("/files/{fileId}")
    fun getFile(
        @HeaderParam("userId") userId: String,
        @PathParam("fileId") fileId: String
    ): Response {
        val user = User(userId)
        val fileInfo = attachmentService.getFileInfo(user, fileId)
        val fileContent = attachmentService.getFileContent(user, fileId)

        return Response.ok(fileContent)
            .header("Content-Disposition", "attachment; filename=\"${fileInfo.fileName}\"")
            .header("Content-Length", fileInfo.fileSize)
            .build()
    }

    @POST
    @Path("/attachments")
    fun addAttachment(
        @HeaderParam("userId") userId: String,
        attachment: Attachment,
        fileContentStream: InputStream
    ): Response {
        val user = User(userId)
        val attachmentId = attachmentService.addAttachment(user, attachment, fileContentStream)
        return Response.ok(attachmentId).build()
    }

    @POST
    @Path("/signatures")
    fun addSignature(
        @HeaderParam("userId") userId: String,
        signature: Signature,
        fileContentStream: InputStream
    ): Response {
        val user = User(userId)
        val signatureId = attachmentService.addSignature(user, signature, fileContentStream)
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

    @GET
    @Path("/attachments")
    fun getAttachments(
        @HeaderParam("userId") userId: String,
        queryOptions: QueryOptions
    ): Response {
        val user = User(userId)
        val attachments = attachmentService.getAttachments(user, queryOptions)
        return Response.ok(attachments).build()
    }
}