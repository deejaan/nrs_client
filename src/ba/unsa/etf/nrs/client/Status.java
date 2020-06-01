package ba.unsa.etf.nrs.client;

public enum Status {
    ACTIVE, COMPLETED;

    @Override
    public String toString() {
        return super.toString().substring(0, 1).toUpperCase().concat(super.toString().substring(1).toLowerCase());
    }
}
