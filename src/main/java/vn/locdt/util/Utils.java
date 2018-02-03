package vn.locdt.util;

import vn.locdt.exception.ThrowingConsumer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * Created by locdt on 2/2/2018.
 */
public class Utils {
    public static boolean isValidString(String str) {
        return str != null && str.length() > 0;
    }

    public static <T, E extends Exception> Consumer<T> consumerWrapper(Consumer<T> c, Class<E> clazz) {
        return i -> {
            try {
                c.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }

    public static Consumer<ResultSet> wrapSqlCommand(ThrowingConsumer<ResultSet, SQLException> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
