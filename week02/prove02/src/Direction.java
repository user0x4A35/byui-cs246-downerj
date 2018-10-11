/**
 * Directions that creatures can move in
 * @see Creature, Wolf, Farmer
 */
public enum Direction {
    UP (0), RIGHT (1), DOWN (2), LEFT (3);

    private final int value;

    private Direction(int value) {
        this.value = value;
    }

    public int getInt() {
        return value;
    }
}