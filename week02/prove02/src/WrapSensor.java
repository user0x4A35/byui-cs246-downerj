/**
 * WrapSensors can sense if the wrapped around the screen.
 * <p>
 * @author  James Downer
 * @version 1.0
 * @since   2018-09-29
 */
public interface WrapSensor {
    /**
     * This method is called only if the {@link Creature} wrapped.
     */
    public void wrapCallback();
}