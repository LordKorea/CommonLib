package de.floorisjava.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import de.floorisjava.commonlib.gui.designer.RenderContext;
import de.floorisjava.commonlib.gui.designer.RenderProperties;

/**
 * A basic implementation of {@link Element}.
 */
@RequiredArgsConstructor
public abstract class BaseElement implements Element {

    /**
     * The render properties of this element.
     */
    @Getter
    protected final RenderProperties renderProperties;

    /**
     * The x position, in global space.
     */
    @Getter
    @Setter
    protected int positionX;

    /**
     * The y position, in global space.
     */
    @Getter
    @Setter
    protected int positionY;

    /**
     * The width, in global space.
     */
    @Setter
    protected int width;

    /**
     * The height, in global space.
     */
    @Setter
    protected int height;

    @Override
    public void prepareRender(final RenderContext ctx) {
    }
}
