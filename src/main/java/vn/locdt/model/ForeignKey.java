package vn.locdt.model;

/**
 * Created by locdt on 2/2/2018.
 */
public class ForeignKey extends Model {
    private Column referencedColumn;
    private Column referencingColumn;

    public Column getReferencedColumn() {
        return referencedColumn;
    }

    public void setReferencedColumn(Column referencedColumn) {
        this.referencedColumn = referencedColumn;
    }

    public Column getReferencingColumn() {
        return referencingColumn;
    }

    public void setReferencingColumn(Column referencingColumn) {
        this.referencingColumn = referencingColumn;
    }
}
