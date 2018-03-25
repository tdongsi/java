package my.learning.advanced.one;

/**
 * Created by tdongsi on 3/25/18.
 */
public class CompanyContractor implements Company, Contractor {

    private int id;

    @Override
    public String getFirst() {
        return null;
    }

    @Override
    public String getLast() {
        return null;
    }

    @Override
    public void doWork() {

    }

    @Override
    public String getName() {
        return Contractor.super.getName() + " at " + Company.super.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyContractor)) return false;

        CompanyContractor that = (CompanyContractor) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
