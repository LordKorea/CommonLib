package de.floorisjava.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import de.floorisjava.commonlib.gui.designer.RenderContext;
import de.floorisjava.commonlib.gui.designer.RenderProperties;
import de.floorisjava.commonlib.gui.designer.util.Alignment;
import de.floorisjava.commonlib.gui.designer.util.Dimension;
import de.floorisjava.commonlib.gui.designer.util.RequestedSize;

/**
 * A text label in a GUI.
 */
public class Label extends BaseElement {

    /**
     * The the caption of this element.
     */
    @Getter
    @Setter
    private String caption;

    /**
     * The color of this element (RGB).
     */
    @Getter
    @Setter
    private int color;

    /**
     * Creates a centered label that breaks the current group. The secondary alignment defaults to
     * {@link de.floorisjava.commonlib.gui.designer.util.Alignment#LEFT}.
     *
     * @param caption The label caption.
     * @param color   The label color.
     * @return The label.
     */
    public static Label centered(final String caption, final int color) {
        return centered(caption, color, Alignment.LEFT);
    }

    /**
     * Creates a centered label that breaks the current group.
     *
     * @param caption            The label caption.
     * @param color              The label color.
     * @param secondaryAlignment The secondary alignment.
     * @return The label.
     */
    public static Label centered(final String caption, final int color, final Alignment secondaryAlignment) {
        final Label label = new Label(RenderProperties.builder()
                .centered()
                .groupBreaking()
                .secondaryAlignment(secondaryAlignment)
                .build());
        label.setText(caption, color);
        label.pack();
        return label;
    }

    /**
     * Creates a label which is not group breaking. The secondary alignment defaults to
     * {@link de.floorisjava.commonlib.gui.designer.util.Alignment#LEFT}.
     *
     * @param caption The label caption.
     * @param color   The label color.
     * @return The label.
     */
    public static Label regular(final String caption, final int color) {
        return regular(caption, color, false, Alignment.LEFT);
    }

    /**
     * Creates a label. The secondary alignment defaults to
     * {@link de.floorisjava.commonlib.gui.designer.util.Alignment#LEFT}.
     *
     * @param caption       The label caption.
     * @param color         The label color.
     * @param groupBreaking Whether the label breaks the current group.
     * @return The label.
     */
    public static Label regular(final String caption, final int color, final boolean groupBreaking) {
        return regular(caption, color, groupBreaking, Alignment.LEFT);
    }

    /**
     * Creates a label.
     *
     * @param caption            The label caption.
     * @param color              The label color.
     * @param groupBreaking      Whether the label breaks the current group.
     * @param secondaryAlignment The secondary alignment.
     * @return The label.
     */
    public static Label regular(final String caption, final int color, final boolean groupBreaking,
                                final Alignment secondaryAlignment) {
        final RenderProperties.RenderPropertiesBuilder builder = RenderProperties.builder()
                .secondaryAlignment(secondaryAlignment);
        if (groupBreaking) {
            builder.groupBreaking();
        }

        final Label label = new Label(builder.build());
        label.setText(caption, color);
        label.pack();
        return label;
    }

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
        final int width = Minecraft.getInstance().fontRenderer.getStringWidth(caption);
        renderProperties.setRequestedSize(new RequestedSize(Dimension.absolute(width), Dimension.absolute(10)));
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getLabelRegistration().accept(this);
    }
}
