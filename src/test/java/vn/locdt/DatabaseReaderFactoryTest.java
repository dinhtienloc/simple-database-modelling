package vn.locdt;

import org.junit.Test;
import vn.locdt.DatabaseReaderFactory;
import vn.locdt.db.system.SystemModeling;
import vn.locdt.modeling.ModelingTestCase;

/**
 * Created by locdt on 2/1/2018.
 */
public class DatabaseReaderFactoryTest extends ModelingTestCase {

    @Test
    public void testCreateSystemReader() {
        SystemModeling reader = DatabaseReaderFactory.createSystemReader("MySQL", wrapper);
        System.out.println(reader.getClass().getSimpleName());
    }
}
