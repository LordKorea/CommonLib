package nge.lk.mods.commonlib.gui.factory.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import nge.lk.mods.commonlib.gui.factory.Positioning;

/**
 * Represents a text element.
 *
 * @see FontRenderer
 * @deprecated Use {@link nge.lk.mods.commonlib.gui.designer}
 */
@Deprecated
@Getter
@Setter
public class TextElement extends BaseElement {

    /**
     * The the caption of this element.
     */
    private String caption;

    /**
     * The color of this element (RGB).
     */
    private int color;

    /**
     * Constructor.
     *
     * @param positioning The positioning of this element.
     */
    public TextElement(final Positioning positioning) {
        super(positioning, 10, 0);
        if (positioning.hasRelativeDimensions()) {
            throw new IllegalStateException("Text elements can not have relative dimensions");
        }
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

    @Override
    public int getHorizontalBuffer() {
        // As the element has no width in its positioning, measurements of its size are taken into account for the
        // horizontal buffer.
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        return fontRenderer.getStringWidth(caption) + 5;
    }
}
