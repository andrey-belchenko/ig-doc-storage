/*
 * This file is generated by jOOQ.
 */
package ig.ds.data.jooq.enums;


import ig.ds.data.jooq.Attachments;

import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum AccessLevel implements EnumType {

    READ("READ"),

    WRITE("WRITE");

    private final String literal;

    private AccessLevel(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Attachments.ATTACHMENTS;
    }

    @Override
    public String getName() {
        return "access_level";
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    /**
     * Lookup a value of this EnumType by its literal
     */
    public static AccessLevel lookupLiteral(String literal) {
        return EnumType.lookupLiteral(AccessLevel.class, literal);
    }
}
