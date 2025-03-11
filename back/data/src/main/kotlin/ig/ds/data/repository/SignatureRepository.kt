//package ig.ds.data.repository
//
//import ig.ds.data.jooq.tables.Signature.SIGNATURE
//import ig.ds.data.model.Signature
//import jakarta.enterprise.context.ApplicationScoped
//import jakarta.inject.Inject
//import org.jooq.DSLContext
//
//
//@ApplicationScoped
//class SignatureRepository @Inject constructor(private val dsl: DSLContext) {
//
//    fun create(signerName: String, attachmentId: Int): Signature {
//        val record = dsl.insertInto(SIGNATURE)
//            .set(SIGNATURE.SIGNER_NAME, signerName)
//            .set(SIGNATURE.ATTACHMENT_ID, attachmentId)
//            .returning()
//            .fetchOne()!!
//
//        return Signature(
//            id = record.id,
//            signerName = record.signerName,
//            signedAt = record.signedAt,
//            attachmentId = record.attachmentId
//        )
//    }
//}