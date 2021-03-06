package vn.locdt.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by locdt on 2/1/2018.
 */
public class Catalog extends Model {
    private List<Table> tables;

    public Catalog() {
        super();
        this.tables = new ArrayList<>();
    }

    public Catalog(String name) {
        super(name);
        this.tables = new ArrayList<>();
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public void addTable(Table t) {
        if (t != null) this.tables.add(t);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tablesSize=" + tables.size() +
                '}';
    }
}
