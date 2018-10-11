public class Cruise implements Expense {
    private Destination mDest;
    static final float CRUISE_RATE = 1000.00f;

    public Cruise(Destination dest) {
        mDest = dest;
    }

    public Destination getDestination() {
        return mDest;
    }

    public float getCost() {
        return CRUISE_RATE * mDest.toInt();
    }

    public void setDestination(Destination dest) {
        mDest = dest;
    }
}