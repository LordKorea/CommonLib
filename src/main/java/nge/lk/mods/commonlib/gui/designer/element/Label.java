package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;
import nge.lk.mods.commonlib.gui.designer.util.Dimension;
import nge.lk.mods.commonlib.gui.designer.util.RequestedSize;

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

    /**
     * Recalculates the width and height to fit the caption size.
     */
    public void pack() {
        final int width = Minecraft.getMinecraft().fontRenderer.getStringWidth(caption);
        renderProperties.setRequestedSize(new RequestedSize(Dimension.absolute(width), Dimension.absolute(10)));
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getLabelRegistration().accept(this);
    }
}
