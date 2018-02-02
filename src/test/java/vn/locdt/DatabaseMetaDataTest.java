package vn.locdt;

import org.junit.Test;
import vn.locdt.modeling.ModelingTestCase;
import vn.locdt.wrapper.ResultSetWrapper;

import java.sql.ResultSet;
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

        ResultSetWrapper.wrap(wrapper.getAllColumns("actor")).forEach(row -> {
            try {
                System.out.println(row.getString("TYPE_NAME"));
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
