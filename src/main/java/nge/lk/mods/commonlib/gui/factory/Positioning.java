package nge.lk.mods.commonlib.gui.factory;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the position of an element in a GUI.
 */
@NoArgsConstructor
public class Positioning {

    /**
     * Coordinate unit type hasn't be assigned.
     */
    @Deprecated
    public static final int UNIT_UNASSIGNED = 0;

    /**
     * Relative coordinates, i.e. a percentage of the window dimension.
     */
    @Deprecated
    public static final int UNIT_RELATIVE = 1;

    /**
     * Absolute coordinates, i.e. in screen units.
     */
    @Deprecated
    public static final int UNIT_ABSOLUTE = 2;

    /**
     * Whether this positioning is centered (horizontally).
     */
    @Getter private boolean centered;

    /**
     * Whether this positioning is aligned to the bottom of the GUI.
     */
    @Getter private boolean bottomAligned;

    /**
     * Whether this positioning is aligned to the right of the GUI.
     */
    @Getter private boolean rightAligned;

    /**
     * Whether this positioning breaks the current row.
     */
    @Getter private boolean breakingRow;

    /**
     * Whether this positioning uses relative or absolute width.
     */
    private PositioningUnit widthUnitType = PositioningUnit.UNASSIGNED;

    /**
     * Whether this positioning uses relative or absolute height.
     */
    private PositioningUnit heightUnitType = PositioningUnit.UNASSIGNED;

    /**
     * The width (relative or absolute) of this positioning.
     *
     * @see Positioning#widthUnitType
     */
    private int width;

    /**
     * The height (relative or absolute) of this positioning.
     *
     * @see Positioning#heightUnitType
     */
    private int height;

    /**
     * Centers this positioning.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning center() {
        centered = true;

        // Centered elements break the current row.
        breakingRow = true;
        return this;
    }

    /**
     * Aligns this positioning to the bottom of the GUI.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning alignBottom() {
        bottomAligned = true;
        return this;
    }

    /**
     * Aligns this positioning to the right of the GUI.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning alignRight() {
        rightAligned = true;
        return this;
    }

    /**
     * Makes this positioning break the current row.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning breakRow() {
        breakingRow = true;
        return this;
    }

    /**
     * Sets the relative height of this positioning.
     *
     * @param height The relative height, 0..100.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning relativeHeight(final int height) {
        assert heightUnitType == PositioningUnit.UNASSIGNED : "can't reassign unit type";
        heightUnitType = PositioningUnit.RELATIVE;
        this.height = height;
        return this;
    }

    /**
     * Sets the absolute height of this positioning.
     *
     * @param height The absolute height.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning absoluteHeight(final int height) {
        assert heightUnitType == PositioningUnit.UNASSIGNED : "can't reassign unit type";
        heightUnitType = PositioningUnit.ABSOLUTE;
        this.height = height;
        return this;
    }

    /**
     * Sets the relative width of this positioning.
     *
     * @param width The relative width, 0..100.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning relativeWidth(final int width) {
        assert widthUnitType == PositioningUnit.UNASSIGNED : "can't reassign unit type";
        widthUnitType = PositioningUnit.RELATIVE;
        this.width = width;
        return this;
    }

    /**
     * Sets the absolute width of this positioning.
     *
     * @param width The absolute width.
     *
     * @return {@code this}, for chaining.
     */
    public Positioning absoluteWidth(final int width) {
        assert widthUnitType == PositioningUnit.UNASSIGNED : "can't reassign unit type";
        widthUnitType = PositioningUnit.ABSOLUTE;
        this.width = width;
        return this;
    }

    /**
     * Calculates the effective height for the given global height.
     *
     * @param globalHeight The global height of the GUI, needed for relative computation.
     *
     * @return The effective height.
     */
    public int getHeight(final int globalHeight) {
        if (heightUnitType == PositioningUnit.ABSOLUTE) {
            return height;
        }
        return (height * globalHeight) / 100;
    }

    /**
     * Calculates the effective width for the given global width.
     *
     * @param globalWidth The global width of the GUI, needed for relative computation.
     *
     * @return The effective width.
     */
    public int getWidth(final int globalWidth) {
        if (widthUnitType == PositioningUnit.ABSOLUTE) {
            return width;
        }
        return (width * globalWidth) / 100;
    }

    /**
     * Returns true if width or height unit types are relative.
     *
     * @return {@code true} iff there are relative dimensions in this positioning.
     */
    public boolean hasRelativeDimensions() {
        return widthUnitType == PositioningUnit.RELATIVE || heightUnitType == PositioningUnit.RELATIVE;
    }

    /**
     * Represents possible positioning units.
     */
    private enum PositioningUnit {
        /**
         * An unassigned unit.
         */
        UNASSIGNED,

        /**
         * Relative units (percentage).
         */
        RELATIVE,

        /**
         * Absolute units (scaled pixels).
         */
        ABSOLUTE
    }
}
