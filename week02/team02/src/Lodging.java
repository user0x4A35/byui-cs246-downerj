public class Lodging implements Expense {
    private Destination mDest;
    private int mNumNights;
    final static float LODGING_DAY_RATE = 100.00f;
    final static float[] LODGING_SURCHARGES = {0.0f, 1.1f, 1.3f};

    public Lodging(Destination dest, int numNights) {
        mDest = dest;
        mNumNights = numNights;
    }

    public float getCost() {
        int dest = mDest.toInt();
        return (LODGING_DAY_RATE * dest) * LODGING_SURCHARGES[dest - 1];
    }
}