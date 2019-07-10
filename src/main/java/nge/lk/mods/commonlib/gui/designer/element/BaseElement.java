package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;

/**
 * A basic implementation of {@link Element}.
 */
@RequiredArgsConstructor
public abstract class BaseElement implements Element {

    /**
     * The parent element.
     */
    protected @Getter final Element parent;

    /**
     * The render properties of this element.
     */
    protected @Getter final RenderProperties renderProperties;

    /**
     * The x position, in global space.
     */
    protected @Setter int positionX;

    /**
     * The y position, in global space.
     */
    protected @Setter int positionY;

    /**
     * The width, in global space.
     */
    protected @Setter int width;

    /**
     * The height, in global space.
     */
    protected @Setter int height;
}
