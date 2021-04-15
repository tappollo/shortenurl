package com.tappollo.urlshortener.utils.sqlite

import org.hibernate.dialect.Dialect
import java.sql.Types
import org.hibernate.dialect.function.StandardSQLFunction

import org.hibernate.dialect.function.SQLFunctionTemplate

import org.hibernate.dialect.function.VarArgsSQLFunction
import org.hibernate.type.StringType

//Copied from https://fullstackdeveloper.guru/2020/05/01/how-to-integrate-sqlite-database-with-spring-boot/
class SQLDialect : Dialect() {
    init {
        registerColumnType(Types.BIT, "integer")
        registerColumnType(Types.TINYINT, "tinyint")
        registerColumnType(Types.SMALLINT, "smallint")
        registerColumnType(Types.INTEGER, "integer")
        registerColumnType(Types.BIGINT, "bigint")
        registerColumnType(Types.FLOAT, "float")
        registerColumnType(Types.REAL, "real")
        registerColumnType(Types.DOUBLE, "double")
        registerColumnType(Types.NUMERIC, "numeric")
        registerColumnType(Types.DECIMAL, "decimal")
        registerColumnType(Types.CHAR, "char")
        registerColumnType(Types.VARCHAR, "varchar")
        registerColumnType(Types.LONGVARCHAR, "longvarchar")
        registerColumnType(Types.DATE, "date")
        registerColumnType(Types.TIME, "time")
        registerColumnType(Types.TIMESTAMP, "timestamp")
        registerColumnType(Types.BINARY, "blob")
        registerColumnType(Types.VARBINARY, "blob")
        registerColumnType(Types.LONGVARBINARY, "blob")
        registerColumnType(Types.BLOB, "blob")
        registerColumnType(Types.CLOB, "clob")
        registerColumnType(Types.BOOLEAN, "integer")

        registerFunction("concat", VarArgsSQLFunction(StringType.INSTANCE, "", "||", ""))
        registerFunction("mod", SQLFunctionTemplate(StringType.INSTANCE, "?1 % ?2"))
        registerFunction("substr", StandardSQLFunction("substr", StringType.INSTANCE))
        registerFunction("substring", StandardSQLFunction("substr", StringType.INSTANCE))
    }

    override fun supportsLimit(): Boolean {
        return true
    }

    override fun getLimitString(query: String, hasOffset: Boolean): String {
        return StringBuffer(query.length + 20).append(query).append(if (hasOffset) " limit ? offset ?" else " limit ?")
            .toString()
    }

    override fun supportsCurrentTimestampSelection(): Boolean {
        return true
    }

    override fun isCurrentTimestampSelectStringCallable(): Boolean {
        return false
    }

    override fun getCurrentTimestampSelectString(): String {
        return "select current_timestamp"
    }

    override fun supportsUnionAll(): Boolean {
        return true
    }

    override fun hasAlterTable(): Boolean {
        return false
    }

    override fun dropConstraints(): Boolean {
        return false
    }

    override fun getAddColumnString(): String {
        return "add column"
    }

    override fun getForUpdateString(): String {
        return ""
    }

    override fun supportsOuterJoinForUpdate(): Boolean {
        return false
    }

    override fun getDropForeignKeyString(): String? {
        throw UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect")
    }

    override fun getAddForeignKeyConstraintString(
        constraintName: String?, foreignKey: Array<String?>?, referencedTable: String?,
        primaryKey: Array<String?>?, referencesPrimaryKey: Boolean
    ): String? {
        throw UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect")
    }

    override fun getAddPrimaryKeyConstraintString(constraintName: String?): String? {
        throw UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect")
    }

    override fun supportsIfExistsBeforeTableName(): Boolean {
        return true
    }

    override fun supportsCascadeDelete(): Boolean {
        return false
    }
}