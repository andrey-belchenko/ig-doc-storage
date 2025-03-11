package ig.ds.data.model

data class Permission(
    val permissionId: Int,
    val userId: String,
    val regionId: String?,
    val accessLevel: AccessLevel
)