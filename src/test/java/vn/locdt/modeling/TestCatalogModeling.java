package vn.locdt.modeling;

import org.junit.Test;
import vn.locdt.DatabaseReaderFactory;
import vn.locdt.db.system.SystemModeling;
import vn.locdt.model.Catalog;
import vn.locdt.model.Model;

import java.util.List;

/**
 * Created by locdt on 2/1/2018.
 */
public class TestCatalogModeling extends ModelingTestCase {
    @Test
    public void testCatalogModeling() {
        SystemModeling reader = DatabaseReaderFactory.createSystemReader("MySQL", wrapper);
        reader.setCatalog("sakila");
        List<Catalog> catalogs = reader.model();
    }
}
