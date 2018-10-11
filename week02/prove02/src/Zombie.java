import java.awt.Color;

/**
 * Zombies move to the right every turn and will attack any non-{@link Plant} {@link Creature}
 * that it lands on.
 * <p>
 * @author  James Downer
 * @version 1.0
 * @since   2018-09-29
 */
public class Zombie extends Creature implements Movable, Aggressor {

    private Color color;
    private Shape shape;

    /**
     * Constructs the Zombie.
     */
    public Zombie() {
        _health = 1;
        color = new Color(0, 0, 255);
        shape = Shape.Square;
    }

    /**
     * Moves the Zombie one cell to the right.
     */
    @Override
    public void move() {
        _location.x++;
    }

    /**
     * Deals 10 damage to an existing {@link Creature} if it's not
     * a {@link Plant}.
     * @param target The {@link Creature} to attack
     */
    @Override
    public void attack(Creature target) {
        if (target == null) {
            return;
        }
        
        if (!(target instanceof Plant)) {
            target.takeDamage(10);
            _health++;
        }
    }

    /**
     * Checks if the Zombie has a positive health count.
     * @return Whether or not the Zombie is alive
     */
    @Override
    public Boolean isAlive() {
		return _health > 0;
	}
    
    /**
     * Gets the {@link Shape} to draw the Zombie as.
     * @return The Square {@link Shape}
     */
    @Override
	public Shape getShape() {
		return shape;
	}
    
    /**
     * Get the Color to paint the Zombie.
     * @return The Color blue
     */
    @Override
	public Color getColor() {
		return color;
	}
}