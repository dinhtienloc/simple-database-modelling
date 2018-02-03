package vn.locdt.db.system.mysql;

import vn.locdt.db.system.ResultSetExtractor;
import vn.locdt.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * Created by locdt on 2/3/2018.
 */
public class MySQLResultSetExtractor extends ResultSetExtractor {

    @Override
    public Catalog catalog(ResultSet rs) {
        try {
            String catalog = rs.getString("TABLE_CAT");
            Catalog newCatalog = new Catalog(catalog);
            return newCatalog;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Schema schema(ResultSet rs) {
        return null;
    }

    public Table table(ResultSet rs) {
        try {
            String tableName = rs.getString("TABLE_NAME ");
            Table newTable = new Table(tableName);
            return newTable;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Column column(ResultSet rs) {
        try {
            String columnName = rs.getString("COLUMN_NAME");
            int dataTypeCode = rs.getInt("DATA_TYPE");
            String dataType = rs.getString("TYPE_NAME");
            int columnSize = rs.getInt("COLUMN_SIZE");
            boolean nullable = rs.getBoolean("NULLABLE");
            boolean autoIncremented = "YES".equals(rs.getString("IS_AUTOINCREMENT")) ? true : false;
            boolean generated = "YES".equals(rs.getString("IS_GENERATEDCOLUMN")) ? true : false;
            ;

            Column newColumn = new Column(columnName);
            newColumn.setDataTypeCode(dataTypeCode);
            newColumn.setDataType(dataType);
            newColumn.setSize(columnSize);
            newColumn.setNullable(nullable);
            newColumn.setAutoIncrement(autoIncremented);
            newColumn.setGenerated(generated);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PrimaryKeyColumn primaryKey(ResultSet rs) {
        return null;
    }

    @Override
    public ForeignKey foreignKey(ResultSet rs) {
        return null;
    }

    protected <T> Consumer<T> wrapSqlCommand(Consumer<T> c) {
        return i -> {
            try {
                c.accept(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
