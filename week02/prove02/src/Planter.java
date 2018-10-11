/**
 * Planters can place new Plants.
 * <p>
 * @author  James Downer
 * @version 1.0
 * @since   2018-09-29
 */
public interface Planter {
    /**
	* This method is called every frame.
    * If there is no existing {@link Creature} at that particular location, then
    * that value will be null.
    * If no {@link Plant} is created, then the return value will be null.
    * @param creature The {@link Creature} on the current cell
    * @return The new {@link Plant}
    */
    public Plant plant(Creature creature);
}