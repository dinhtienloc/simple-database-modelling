package vn.locdt.modeling;

import junit.framework.TestCase;
import vn.locdt.wrapper.DatabaseMetadataWrapper;
import vn.locdt.TestConstants;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by locdt on 2/1/2018.
 */
public class ModelingTestCase extends TestCase {
    protected DatabaseMetadataWrapper wrapper;

    @Override
    protected void setUp() throws Exception {
        try {
            Connection conn = DriverManager.getConnection(TestConstants.DB_URL,
                    TestConstants.DB_USER,
                    TestConstants.DB_PASS);
            wrapper = new DatabaseMetadataWrapper(conn.getMetaData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
