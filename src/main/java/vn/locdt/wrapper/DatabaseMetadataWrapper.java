package vn.locdt.wrapper;

import org.apache.commons.lang.SystemUtils;
import vn.locdt.util.Utils;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by locdt on 1/29/2018.
 */
public class DatabaseMetadataWrapper {
    private DatabaseMetaData metaData;
    private String catalog;

    public DatabaseMetadataWrapper(DatabaseMetaData metaData) {
        this.metaData = metaData;
    }

    public DatabaseMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(DatabaseMetaData metaData) {
        this.metaData = metaData;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public List<String> getTableNames(String namePattern) throws SQLException{
        ResultSet rs = getTables(namePattern);
        return new ResultSetWrapper<>(rs, DatabaseMetadataWrapper::getTableNameFromResultSet)
                    .stream()
                    .collect(Collectors.toList());
    }

    public ResultSet getTables(String namePattern) throws SQLException {
        return this.getMetaData().getTables(this.getCatalog(), null, namePattern, new String[]{"TABLE"});
    }

    public ResultSet getAllTables() throws SQLException {
        return getTables(null);
    }

    public List<String> getAllTableNames() throws SQLException {
        return getTableNames(null);
    }

    public ResultSet getViews(String namePattern) throws SQLException {
        return this.getMetaData().getTables(this.getCatalog(), null, namePattern, new String[]{"VIEW"});
    }

    public ResultSet getColumns(String tableNamePattern, String columnNamePattern) throws SQLException {
        return this.getMetaData().getColumns(this.getCatalog(), null, tableNamePattern, columnNamePattern);
    }

    public ResultSet getAllColumns(String tableNamePattern) throws SQLException {
        return getColumns(tableNamePattern, null);
    }

    public ResultSet getPrimaryKeys(String tableNamePattern) throws SQLException {
        return this.getMetaData().getPrimaryKeys(this.getCatalog(), null, tableNamePattern);
    }

    public ResultSet getForeignKeys(String tableNamePattern) throws SQLException {
        return this.getMetaData().getImportedKeys(this.getCatalog(), null, tableNamePattern);
    }

    public ResultSet getIndices(String tableNamePattern, boolean unique, boolean approximate) throws SQLException {
        return this.getMetaData().getIndexInfo(this.getCatalog(), null, tableNamePattern, unique, approximate);
    }

    private static String getResultSetStringKey(ResultSet rs, String key) {
        try {
            String value = rs.getString(key);
            return value;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTableNameFromResultSet(ResultSet rs) {
        return getResultSetStringKey(rs,"TABLE_NAME");
    }
    private static String getColumnNameFromResultSet(ResultSet rs) {
        return getResultSetStringKey(rs, "COLUMN_NAME");
    }
}
