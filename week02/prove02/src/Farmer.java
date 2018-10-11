import java.awt.Color;
import java.util.Random;

/**
 * The Farmer moves in a given (random) direction, moving one cell to its right
 * (from its perspective) if wrapping around the screen. If the Farmer has seeds, then
 * it will plant a new Plant in an empty cell for every few cells. If the Farmer
 * comes across an existing Plant, then it will harvest a seed (increasing its ability
 * to plant on the next turn).
 * <p>
 * @author  James Downer
 * @version 1.0
 * @since   2018-09-29
 */
public class Farmer extends Creature implements Movable, Planter, Harvester, WrapSensor {
    private int numSeeds;
    private Color color;
    private Shape shape;
    private Direction dir;
    private Random rand;
    private int numSteps;
    static final int PLANT_DISTANCE = 5;

    /**
     * Constructs the Farmer.
     */
    public Farmer() {
        numSeeds = 20;
        _health = 1;
        color = new Color(127, 63, 0);
        shape = Shape.Triangle;
        rand = new Random();
        setDirection(rand.nextInt(4));
        numSteps = 0;
    }

    /**
     * Sets/changes the direction of movement.
     * <p>
     * @param dir The {@link Direction} to move
     */
    private void setDirection(int dir) {
        dir = dir % 4;

        switch (dir) {
            case 0:
                this.dir = Direction.UP;
                break;
            case 1:
                this.dir = Direction.RIGHT;
                break;
            case 2:
                this.dir = Direction.DOWN;
                break;
            case 3:
                this.dir = Direction.LEFT;
                break;
        }
    }

    /**
     * Plants a new {@link Plant} in the given cell, if unoccupied by an existing Plant.
     * @param creature The existing {@link Creature} in the current cell
     * @return A new {@link Plant} or null
     */
    @Override
    public Plant plant(Creature creature) {
        Plant newPlant;

        if (++numSteps % PLANT_DISTANCE == 0) {
            boolean isDefined = (creature != null) && (creature instanceof Plant);
            boolean haveSeeds = numSeeds > 0;
            if (isDefined || !haveSeeds) {
                newPlant = null;
            } else {
                newPlant = new Plant();
                numSeeds--;
            }
        } else {
            newPlant = null;
        }

        return newPlant;
    }

    /**
     * Harvests a seed from the given cell if there is an existing {@link Plant}.
     * @param creature The {@link Creature} to check at the current cell
     */
    @Override
    public void harvest(Creature source) {
        if ((source != null) && (source instanceof Plant)) {
            numSeeds++;
        }
    }

    /**
     * Moves the Farmer one cell in the direction it's pointing.
     */
    @Override
    public void move() {
        switch (dir) {
            case UP:
                _location.y--;
                break;
            case DOWN:
                _location.y++;
                break;
            case LEFT:
                _location.x--;
                break;
            case RIGHT:
                _location.x++;
                break;
        }
    }

    /**
     * If wrapping around the screen, the Farmer will move one cell
     * to its respective right.
     */
    @Override
    public void wrapCallback() {
        switch (dir) {
            case UP:
                _location.x++;
                break;
            case DOWN:
                _location.x--;
                break;
            case LEFT:
                _location.y--;
                break;
            case RIGHT:
                _location.y++;
                break;
        }
    }

    /**
     * Gets the {@link Shape} to draw the Farmer as.
     * @return The Triangle {@link Shape}
     */
    @Override
    public Shape getShape() {
        return shape;
    }

    /**
     * Get the Color to paint the Farmer.
     * @return The Color brown
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Checks if the Farmer has a positive health count.
     * @return Whether or not the Farmer is alive
     */
    @Override
    public Boolean isAlive() {
        return _health > 0;
    }
}