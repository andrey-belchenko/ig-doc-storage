/*
 * This file is generated by jOOQ.
 */
package ig.ds.data.jooq.tables;


import ig.ds.data.jooq.Attachments;
import ig.ds.data.jooq.Keys;
import ig.ds.data.jooq.enums.AccessLevel;
import ig.ds.data.jooq.tables.records.PermissionRecord;

import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Permission extends TableImpl<PermissionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>attachments.permission</code>
     */
    public static final Permission PERMISSION = new Permission();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PermissionRecord> getRecordType() {
        return PermissionRecord.class;
    }

    /**
     * The column <code>attachments.permission.permission_id</code>.
     */
    public final TableField<PermissionRecord, Integer> PERMISSION_ID = createField(DSL.name("permission_id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>attachments.permission.user_id</code>.
     */
    public final TableField<PermissionRecord, String> USER_ID = createField(DSL.name("user_id"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>attachments.permission.region_id</code>.
     */
    public final TableField<PermissionRecord, String> REGION_ID = createField(DSL.name("region_id"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>attachments.permission.access_level</code>.
     */
    public final TableField<PermissionRecord, AccessLevel> ACCESS_LEVEL = createField(DSL.name("access_level"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(ig.ds.data.jooq.enums.AccessLevel.class), this, "");

    private Permission(Name alias, Table<PermissionRecord> aliased) {
        this(alias, aliased, null);
    }

    private Permission(Name alias, Table<PermissionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>attachments.permission</code> table reference
     */
    public Permission(String alias) {
        this(DSL.name(alias), PERMISSION);
    }

    /**
     * Create an aliased <code>attachments.permission</code> table reference
     */
    public Permission(Name alias) {
        this(alias, PERMISSION);
    }

    /**
     * Create a <code>attachments.permission</code> table reference
     */
    public Permission() {
        this(DSL.name("permission"), null);
    }

    public <O extends Record> Permission(Table<O> child, ForeignKey<O, PermissionRecord> key) {
        super(child, key, PERMISSION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Attachments.ATTACHMENTS;
    }

    @Override
    public Identity<PermissionRecord, Integer> getIdentity() {
        return (Identity<PermissionRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PermissionRecord> getPrimaryKey() {
        return Keys.PERMISSION_PKEY;
    }

    @Override
    public Permission as(String alias) {
        return new Permission(DSL.name(alias), this);
    }

    @Override
    public Permission as(Name alias) {
        return new Permission(alias, this);
    }

    @Override
    public Permission as(Table<?> alias) {
        return new Permission(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Permission rename(String name) {
        return new Permission(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Permission rename(Name name) {
        return new Permission(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Permission rename(Table<?> name) {
        return new Permission(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, String, AccessLevel> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function4<? super Integer, ? super String, ? super String, ? super AccessLevel, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function4<? super Integer, ? super String, ? super String, ? super AccessLevel, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
