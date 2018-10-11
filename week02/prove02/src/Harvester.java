/**
 * Harvesters can react to Plants.
 * <p>
 * @author  James Downer
 * @version 1.0
 * @since   2018-09-29
 */
public interface Harvester {
    /**
	* This method is called every frame.
	* If there is no existing Creature at that particular location, then that value will be null.
    * @param source The {@link Creature} on the current cell
    */
    public void harvest(Creature source);
}