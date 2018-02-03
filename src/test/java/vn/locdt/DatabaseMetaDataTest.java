package vn.locdt;

import org.junit.Test;
import vn.locdt.modeling.ModelingTestCase;
import vn.locdt.util.SQL;

import java.sql.SQLException;

/**
 * Created by locdt on 1/31/2018.
 */
public class DatabaseMetaDataTest extends ModelingTestCase {

    @Test
    public void testMetadata() throws Exception {
        wrapper.setCatalog("sakila");
//        ResultSet rs = wrapper.getMetaData().getSchemas();
//        while (rs.next()) {
//            System.out.println(rs.getString("TABLE_SCHEM") + " " + rs.getString("TABLE_CATALOG"));
//        }

//        rs = wrapper.getMetaData().getCatalogs();
//        while (rs.next()) {
//            System.out.println(rs.getString("TABLE_CAT"));
//        }

//        wrapper.getAllColumns("actor").forEach(row -> {
//            try {
//                System.out.println(row.getString("TYPE_NAME"));
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });

        wrapper.getForeignKeys("address").forEach(
            SQL.wrap(
                row -> {
                    System.out.println("PKTABLE_NAME: " + row.getString("PKTABLE_NAME"));
                    System.out.println("PKCOLUMN_NAME: " + row.getString("PKCOLUMN_NAME"));
                    System.out.println("FKTABLE_NAME: " + row.getString("FKTABLE_NAME"));
                    System.out.println("FKCOLUMN_NAME: " + row.getString("FKCOLUMN_NAME"));
                    System.out.println("UPDATE_RULE: " + row.getString("UPDATE_RULE"));
                    System.out.println("DELETE_RULE: " + row.getString("DELETE_RULE"));
                }
            )
        );
    }
}
