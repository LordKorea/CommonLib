package nge.lk.mods.commonlib.gui.designer.element;

import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;

/**
 * Represents an element on the screen.
 */
public interface Element {

    /**
     * Returns the parent element.
     *
     * @return The parent element.
     */
    Element getParent();

    /**
     * Updates the global x position of this element.
     *
     * @param x The x position.
     */
    void setPositionX(int x);

    /**
     * Updates the global y position of this element.
     *
     * @param y The y position.
     */
    void setPositionY(int y);

    /**
     * Updates the global with of this element.
     *
     * @param width The width.
     */
    void setWidth(int width);

    /**
     * Updates the global height of this element.
     *
     * @param height The height.
     */
    void setHeight(int height);

    /**
     * Obtains the render properties for this element.
     *
     * @return The render properties.
     */
    RenderProperties getRenderProperties();

    /**
     * Initializes this element for rendering.
     *
     * @param ctx The rendering context.
     */
    void prepareRender(RenderContext ctx);
}
