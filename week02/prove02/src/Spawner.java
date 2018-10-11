/**
 * Spawners can spawn new Creatures.
 * <p>
 * @author  James Downer
 * @version 1.0
 * @since   2018-09-29
 */
public interface Spawner {
    /**
    * This method is called every frame.
    * If no {@link Creature} is created, then the return value will be null.
    * @return The {@link Creature} spawned
    */
    public Creature spawnNewCreature();
}