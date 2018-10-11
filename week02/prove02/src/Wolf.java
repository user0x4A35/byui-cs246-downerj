import java.awt.Color;
import java.util.Random;

/**
* The Wolf hunts in a given direction until it senses an
* Animal or Farmer nearby and changes directions to attack.
* <p>
* @author  James Downer
* @version 1.0
* @since   2018-09-29
*/
public class Wolf extends Creature implements Movable, Aware, Aggressor, Spawner {
    
    private Direction dir;
    private boolean canSpawn;
    private Color color;
    private Shape shape;

    /**
     * Constructs the Wolf.
     */
    public Wolf() {
        setDirection(new Random().nextInt(4));

        _health = 1;
        canSpawn = false;
        color = new Color(140, 140, 140);
        shape = Shape.Square;
    }

    /**
     * Sets/changes the direction of movement.
     * <p>
     * @param dir The {@link Direction} to move
     */
    private void setDirection(int dir) {
        // enforce 0 through 3
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
     * Checks for the first Animal or Farmer in a clockwise rotation, starting in its
     * current direction; if it is found, then the Wolf changes to that direction.
     * @param above The {@link Creature} in the cell above
     * @param below The {@link Creature} in the cell below
     * @param left  The {@link Creature} in the cell to the left
     * @param right The {@link Creature} in the cell to the right
     */
    @Override
    public void senseNeighbors(Creature above, Creature below, Creature left, Creature right) {
        Creature[] creatures = {above, right, below, left};
        int offset = dir.getInt();

        for (int d = 0; d < 4; d++) {
            int index = (offset + d) % 4;
            Creature neighbor = creatures[index];
            if (neighbor == null) {
                continue;
            } else if (neighbor instanceof Animal || neighbor instanceof Farmer) {
                setDirection(d);
                break;
            }
        }
    }
    
    /**
     * Moves the Wolf one cell in the direction it's pointing.
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
     * Deals 5 damage to the existing target if it's an Animal or a Farmer.
     * If successful, the Wolf can then spawn one new Wolf pup.
     * @param target The {@link Creature} to attack
     */
    @Override
    public void attack(Creature target) {
        if (target == null) {
            return;
        }
        
        if (target instanceof Animal || target instanceof Farmer) {
            target.takeDamage(5);
            _health++;
            canSpawn = true;
        }
    }

    /**
     * Checks if the Wolf has a positive health count.
     * @return Whether or not the Wolf is alive
     */
    @Override
    public Boolean isAlive() {
		return _health > 0;
	}
    
    /**
     * Gets the {@link Shape} to draw the Wolf as.
     * @return The Square {@link Shape}
     */
    @Override
	public Shape getShape() {
		return shape;
	}
    
    /**
     * Get the Color to paint the Wolf.
     * @return The Color grey
     */
    @Override
	public Color getColor() {
		return color;
    }
    
    /**
     * Spawns a new Wolf if able.
     * @return The {@link Creature} spawned
     */
    @Override
    public Creature spawnNewCreature() {
        Wolf pup;
        
        if (!canSpawn) {
            pup = null;
        } else {
            pup = new Wolf();
            canSpawn = false;
        }

        return pup;
    }
}