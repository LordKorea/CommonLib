package nge.lk.mods.commonlib.gui.designer.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Represents a dimension value (e.g. a length).
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Dimension {

    /**
     * Returns an absolute dimension.
     *
     * @param val The dimension value.
     * @return The dimension.
     */
    public static Dimension absolute(final int val) {
        return new Dimension(Unit.ABSOLUTE, val);
    }

    /**
     * Returns a relative dimension.
     *
     * @param val The dimension value.
     * @return The dimension.
     */
    public static Dimension relative(final int val) {
        return new Dimension(Unit.RELATIVE, val);
    }

    /**
     * The dimension unit.
     */
    private final Unit unit;

    /**
     * The dimension value.
     */
    private final int val;

    /**
     * Evaluates the dimension with the given total value.
     *
     * @param total The total value, i.e. what relative dimensions refer to.
     * @return The evaluated dimension, in absolute units.
     */
    public int evaluate(final int total) {
        if (unit == Unit.ABSOLUTE) {
            return val;
        }
        return (int) ((val / 100.0f) * total);
    }

    /**
     * The unit in which the dimensions is given.
     */
    private enum Unit {
        /**
         * Absolute units, i.e. possibly scaled pixels.
         */
        ABSOLUTE,

        /**
         * Relative units, i.e. a fraction of the parent element.
         */
        RELATIVE
    }
}
