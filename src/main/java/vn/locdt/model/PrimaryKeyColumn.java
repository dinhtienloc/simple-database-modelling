package vn.locdt.model;

/**
 * Created by locdt on 2/2/2018.
 */
public class PrimaryKeyColumn extends Column {
    public PrimaryKeyColumn(String name) {
        super(name);
        this.primaryKey = true;
    }
}
