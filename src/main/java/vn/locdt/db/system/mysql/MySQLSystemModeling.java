package vn.locdt.db.system.mysql;

import vn.locdt.db.system.SystemModeling;
import vn.locdt.exception.SystemCatalogException;
import vn.locdt.util.Utils;
import vn.locdt.wrapper.DatabaseMetadataWrapper;
import vn.locdt.exception.CatalogNotSupportException;
import vn.locdt.exception.SchemaNotSupportException;
import vn.locdt.model.*;
import vn.locdt.wrapper.ResultSetWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by locdt on 1/31/2018.
 */
public class MySQLSystemModeling extends SystemModeling {
    public static final String SYSTEM = "MySQL";
    public static final List<String> sysCatalogs = Arrays.asList("information_schema", "performance_schema", "sys");

    public MySQLSystemModeling() {
        super();
    }

    public MySQLSystemModeling(DatabaseMetadataWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public List<Catalog> model() throws SystemCatalogException {
        List<Catalog> catalogs = new ArrayList<>();
        try {
            ResultSet rs = wrapper.getMetaData().getCatalogs();
            while (rs.next()) {
                String catalog = rs.getString("TABLE_CAT");

                if (this.catalog != null) {
                    if (catalog.equals(this.catalog)) {
                        catalogs.add(modelCatalog(rs));
                        break;
                    }
                }
                else if (!sysCatalogs.contains(catalog)) {
                    catalogs.add(modelCatalog(rs));
                }
                else {
                    throw new SystemCatalogException("Could not model a system catalog.");
                }
            }
            return catalogs;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CatalogNotSupportException e) {
            e.printStackTrace();
        }
        return new ArrayList(){};
    }

    @Override
    public Catalog modelCatalog(ResultSet catalogRs) throws CatalogNotSupportException {
        try {
            String catalog = catalogRs.getString("TABLE_CAT");
            Catalog newCatalog = new Catalog(catalog);
            wrapper.setCatalog(catalog);

            ResultSetWrapper.wrap(wrapper.getAllTables()).forEach(row -> modelTable(newCatalog, row));
            return newCatalog;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Schema modelSchema(ResultSet schemaRs) throws SchemaNotSupportException {
        throw new SchemaNotSupportException("Schema is not support in '" + SYSTEM + "' database");
    }

    @Override
    public Table modelTable(Catalog catalog, ResultSet tableRs) {
        try {
            String tableName = tableRs.getString("TABLE_NAME ");
            Table newTable = new Table(tableName);
            ResultSetWrapper.wrap(wrapper.getAllColumns(tableName)).forEach(row -> modelColumn(newTable, row));
            ResultSetWrapper.wrap(wrapper.getPrimaryKeys(tableName)).forEach(row -> modelPrimaryKey(newTable, row));
            ResultSetWrapper.wrap(wrapper.getForeignKeys(tableName)).forEach(row -> modelForeignKey(newTable, row));
            catalog.addTable(newTable);
            newTable.setCatalog(catalog);
            return newTable;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Column modelColumn(Table table, ResultSet columnRs) {
        try {
            String columnName = columnRs.getString("COLUMN_NAME");
            int dataTypeCode = columnRs.getInt("DATA_TYPE");
            String dataType = columnRs.getString("TYPE_NAME");
            int columnSize = columnRs.getInt("COLUMN_SIZE");
            boolean nullable = columnRs.getBoolean("NULLABLE");
            boolean autoIncremented = "YES".equals(columnRs.getString("IS_AUTOINCREMENT")) ? true : false;
            boolean generated = "YES".equals(columnRs.getString("IS_GENERATEDCOLUMN")) ? true : false; ;

            Column newColumn = new Column(columnName);
            newColumn.setDataTypeCode(dataTypeCode);
            newColumn.setDataType(dataType);
            newColumn.setSize(columnSize);
            newColumn.setNullable(nullable);
            newColumn.setAutoIncrement(autoIncremented);
            newColumn.setGenerated(generated);

            newColumn.setTable(table);
            table.addColumn(newColumn);

            return newColumn;
        }
        catch (SQLException e) {

        }
        return null;
    }

    @Override
    public PrimaryKeyColumn modelPrimaryKey(Table table, ResultSet pk) {
        return null;
    }

    @Override
    public ForeignKey modelForeignKey(Table table, ResultSet fkRs) {
        return null;
    }

}
