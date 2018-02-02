package vn.locdt.db.system;

import vn.locdt.wrapper.DatabaseMetadataWrapper;
import vn.locdt.exception.CatalogNotSupportException;
import vn.locdt.exception.SchemaNotSupportException;
import vn.locdt.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by locdt on 1/31/2018.
 */
public abstract class SystemModeling {
    protected DatabaseMetadataWrapper wrapper;
    protected String catalog;

    public SystemModeling() {}

    public SystemModeling(DatabaseMetadataWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public DatabaseMetadataWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(DatabaseMetadataWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public abstract List<Catalog> model();

    public abstract Catalog modelCatalog(ResultSet catalog) throws CatalogNotSupportException;

    public abstract Schema modelSchema(ResultSet schema) throws SchemaNotSupportException;

    public abstract Table modelTable(Catalog catalog, ResultSet table);

    public abstract Column modelColumn(Table table, ResultSet column);

    public abstract PrimaryKeyColumn modelPrimaryKey(Table table, ResultSet pk);

    public abstract ForeignKey modelForeignKey(Table table, ResultSet fk);
}
