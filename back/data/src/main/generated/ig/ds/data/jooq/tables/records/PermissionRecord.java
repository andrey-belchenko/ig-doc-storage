/*
 * This file is generated by jOOQ.
 */
package ig.ds.data.jooq.tables.records;


import ig.ds.data.jooq.enums.AccessLevel;
import ig.ds.data.jooq.tables.Permission;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PermissionRecord extends UpdatableRecordImpl<PermissionRecord> implements Record4<Integer, String, String, AccessLevel> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>attachments.permission.permission_id</code>.
     */
    public void setPermissionId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>attachments.permission.permission_id</code>.
     */
    public Integer getPermissionId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>attachments.permission.user_id</code>.
     */
    public void setUserId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>attachments.permission.user_id</code>.
     */
    public String getUserId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>attachments.permission.region_id</code>.
     */
    public void setRegionId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>attachments.permission.region_id</code>.
     */
    public String getRegionId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>attachments.permission.access_level</code>.
     */
    public void setAccessLevel(AccessLevel value) {
        set(3, value);
    }

    /**
     * Getter for <code>attachments.permission.access_level</code>.
     */
    public AccessLevel getAccessLevel() {
        return (AccessLevel) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, String, AccessLevel> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, String, String, AccessLevel> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Permission.PERMISSION.PERMISSION_ID;
    }

    @Override
    public Field<String> field2() {
        return Permission.PERMISSION.USER_ID;
    }

    @Override
    public Field<String> field3() {
        return Permission.PERMISSION.REGION_ID;
    }

    @Override
    public Field<AccessLevel> field4() {
        return Permission.PERMISSION.ACCESS_LEVEL;
    }

    @Override
    public Integer component1() {
        return getPermissionId();
    }

    @Override
    public String component2() {
        return getUserId();
    }

    @Override
    public String component3() {
        return getRegionId();
    }

    @Override
    public AccessLevel component4() {
        return getAccessLevel();
    }

    @Override
    public Integer value1() {
        return getPermissionId();
    }

    @Override
    public String value2() {
        return getUserId();
    }

    @Override
    public String value3() {
        return getRegionId();
    }

    @Override
    public AccessLevel value4() {
        return getAccessLevel();
    }

    @Override
    public PermissionRecord value1(Integer value) {
        setPermissionId(value);
        return this;
    }

    @Override
    public PermissionRecord value2(String value) {
        setUserId(value);
        return this;
    }

    @Override
    public PermissionRecord value3(String value) {
        setRegionId(value);
        return this;
    }

    @Override
    public PermissionRecord value4(AccessLevel value) {
        setAccessLevel(value);
        return this;
    }

    @Override
    public PermissionRecord values(Integer value1, String value2, String value3, AccessLevel value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PermissionRecord
     */
    public PermissionRecord() {
        super(Permission.PERMISSION);
    }

    /**
     * Create a detached, initialised PermissionRecord
     */
    public PermissionRecord(Integer permissionId, String userId, String regionId, AccessLevel accessLevel) {
        super(Permission.PERMISSION);

        setPermissionId(permissionId);
        setUserId(userId);
        setRegionId(regionId);
        setAccessLevel(accessLevel);
        resetChangedOnNotNull();
    }
}
