package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.Setter;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;

/**
 * A text label in a GUI.
 */
public class Label extends BaseElement {

    /**
     * The the caption of this element.
     */
    private @Getter @Setter String caption;

    /**
     * The color of this element (RGB).
     */
    private @Getter @Setter int color;

    /**
     * Constructor.
     *
     * @param renderProperties The render properties of this label.
     */
    public Label(final RenderProperties renderProperties) {
        super(renderProperties);
    }

    /**
     * Combined setter for caption and color.
     *
     * @param caption The new caption.
     * @param color   The new color.
     */
    public void setText(final String caption, final int color) {
        setCaption(caption);
        setColor(color);
    }
}
