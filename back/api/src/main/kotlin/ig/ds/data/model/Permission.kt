package ig.ds.data.model

data class Permission(
    var permissionId: Int,
    var userId: String,
    var regionId: String?,
    var accessLevel: AccessLevel
)