package vn.locdt.db.system.mysql;

import vn.locdt.ResultSetIterator;
import vn.locdt.db.system.ResultSetExtractor;
import vn.locdt.model.*;
import vn.locdt.util.SQL;
import vn.locdt.util.Utils;

import java.sql.ResultSet;

/**
 * Created by locdt on 2/3/2018.
 */
public class MySQLResultSetExtractor extends ResultSetExtractor {

    @Override
    public Catalog catalog(ResultSet rs) {
        return SQL.wrap(() -> {
            String catalog = rs.getString("TABLE_CAT");
            Catalog newCatalog = new Catalog(catalog);
            return newCatalog;
        });
    }

    @Override
    public Schema schema(ResultSet rs) {
        return null;
    }

    public Table table(ResultSet rs) {
        return SQL.wrap(() -> {
            String tableName = rs.getString("TABLE_NAME");
            Table newTable = new Table(tableName);
            return newTable;
        });
    }

    @Override
    public Column column(ResultSet rs) {
        return SQL.wrap(() -> {
            String columnName = rs.getString("COLUMN_NAME");
            int dataTypeCode = rs.getInt("DATA_TYPE");
            String dataType = rs.getString("TYPE_NAME");
            int columnSize = rs.getInt("COLUMN_SIZE");
            boolean nullable = rs.getBoolean("NULLABLE");
            boolean autoIncremented = "YES".equals(rs.getString("IS_AUTOINCREMENT")) ? true : false;
            boolean generated = "YES".equals(rs.getString("IS_GENERATEDCOLUMN")) ? true : false;
            ;

            Column col = new Column(columnName);
            col.setDataTypeCode(dataTypeCode);
            col.setDataType(dataType);
            col.setSize(columnSize);
            col.setNullable(nullable);
            col.setAutoIncrement(autoIncremented);
            col.setGenerated(generated);
            return col;
        });
    }

    @Override
    public String primaryKey(ResultSet rs) {
        return SQL.wrap(() -> {
            String pkName = rs.getString("COLUMN_NAME");
            return pkName;
        });
    }

    @Override
    public ForeignKey foreignKey(ResultSet rs) {
        return SQL.wrap(() -> {
            ForeignKey fk = new ForeignKey();

            String pkTableName = rs.getString("PKTABLE_NAME");
            fk.setReferencedTableName(pkTableName);
            String pkColumnName = rs.getString("PKCOLUMN_NAME");
            fk.setReferencedColumnName(pkColumnName);

            String fkTableName = rs.getString("FKTABLE_NAME");
            fk.setReferencingTableName(fkTableName);
            String fkColumnName = rs.getString("FKCOLUMN_NAME");
            fk.setReferencingColumnName(fkColumnName);

            short updateRule = rs.getShort("UPDATE_RULE");
            fk.setUpdateRule(updateRule);

            short deleteRule = rs.getShort("DELETE_RULE");
            fk.setDeleteRule(deleteRule);

            return fk;
        });
    }
}
