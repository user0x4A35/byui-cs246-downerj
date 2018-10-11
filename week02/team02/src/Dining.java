public class Dining implements Expense {
    private Destination mDest;
    private int mNumNights;
    static final float DINING_RATE = 10.00f;

    public Dining(Destination dest, int numNights) {
        mDest = dest;
        mNumNights = numNights;
    }

    public float getCost() {
        return DINING_RATE * mDest.toInt();
    }
}