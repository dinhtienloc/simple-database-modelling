package vn.locdt.db.system.mysql;

import vn.locdt.db.system.ResultSetExtractor;
import vn.locdt.db.system.SystemModeling;
import vn.locdt.exception.SystemCatalogException;
import vn.locdt.DatabaseMetadataWrapper;
import vn.locdt.exception.CatalogNotSupportException;
import vn.locdt.exception.SchemaNotSupportException;
import vn.locdt.model.*;
import vn.locdt.ResultSetIterator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by locdt on 1/31/2018.
 */
public class MySQLSystemModeling extends SystemModeling {
    public static final String SYSTEM = "MySQL";
    public static final List<String> sysCatalogs = Arrays.asList("information_schema", "performance_schema", "sys");

    public MySQLSystemModeling() {
        super();
    }

    @Override
    public SystemModeling addExtractor(ResultSetExtractor extractor) {
        this.setExtractor(new MySQLResultSetExtractor());
        return this;
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
    public Catalog modelCatalog(ResultSet rs) throws CatalogNotSupportException {
        wrapper.setCatalog(catalog);
        Catalog newCatalog = extractor.catalog(rs);
        wrapper.getAllTables().forEach(row -> modelTable(newCatalog, row));
        return newCatalog;
    }

    @Override
    public Schema modelSchema(ResultSet schemaRs) throws SchemaNotSupportException {
        throw new SchemaNotSupportException("Schema is not support in '" + SYSTEM + "' database");
    }

    @Override
    public Table modelTable(Catalog catalog, ResultSet rs) {
        Table table = extractor.table(rs);

        wrapper.getAllColumns(table.getName()).forEach(row -> modelColumn(table, row));
        wrapper.getPrimaryKeys(table.getName()).forEach(row -> modelPrimaryKey(table, row));
        wrapper.getForeignKeys(table.getName()).forEach(row -> modelForeignKey(table, row));

        catalog.addTable(table);
        table.setCatalog(catalog);
        return table;
    }

    @Override
    public Column modelColumn(Table table, ResultSet rs) {
        Column newColumn = extractor.column(rs);
        newColumn.setTable(table);
        table.addColumn(newColumn);
        return newColumn;
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
