package nge.lk.mods.commonlib.gui.factory.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nge.lk.mods.commonlib.gui.factory.Positioning;

/**
 * An element for a GUI managed by the {@link nge.lk.mods.commonlib.gui.factory.GuiFactory}
 *
 * @deprecated Use {@link nge.lk.mods.commonlib.gui.designer}
 */
@Deprecated
@Getter
@RequiredArgsConstructor
public class BaseElement {

    /**
     * The positioning of this element.
     */
    private final Positioning positioning;

    /**
     * The vertical buffer of this element.
     */
    private final int verticalBuffer;

    /**
     * The horizontal buffer of this element.
     */
    private final int horizontalBuffer;

    /**
     * The width of this element (calculated on the fly).
     */
    protected int width;

    /**
     * The height of this element (calculated on the fly).
     */
    protected int height;

    /**
     * The fixed X position of this element, calculated when the GUI factory aligns its elements.
     */
    private int fixedX;

    /**
     * The fixed Y position of this element, calculated when the GUI factory aligns its elements.
     */
    private int fixedY;

    /**
     * Fixes this elements position to the given coordinates.
     *
     * @param x The fixed x coordinate.
     * @param y The fixed y coordinate.
     */
    public void fixPosition(final int x, final int y) {
        fixedX = x;
        fixedY = y;
    }

    /**
     * Fixes this elements dimensions with respect to the given global width and height.
     *
     * @param globalWidth  The global width (of the window, scaled).
     * @param globalHeight The global height (of the window, scaled).
     * @see net.minecraft.client.gui.ScaledResolution
     */
    public void fixDimensions(final int globalWidth, final int globalHeight) {
        width = positioning.getWidth(globalWidth);
        height = positioning.getHeight(globalHeight);
    }
}
