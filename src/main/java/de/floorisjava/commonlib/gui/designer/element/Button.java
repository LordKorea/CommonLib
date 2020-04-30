package de.floorisjava.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import de.floorisjava.commonlib.gui.designer.RenderContext;
import de.floorisjava.commonlib.gui.designer.RenderProperties;
import de.floorisjava.commonlib.gui.designer.util.Alignment;

import java.util.function.Consumer;

/**
 * A button in a GUI.
 */
public class Button extends BaseElement {

    /**
     * The internal button element.
     */
    @Getter
    private final net.minecraft.client.gui.widget.button.Button button;

    /**
     * The button listener. Clicks will be forwarded to this listener.
     */
    @Getter
    private final Consumer<Button> buttonListener;

    /**
     * The metadata of this button. Often useful for auto-generated buttons which need to know their source to
     * automate click handling.
     */
    @Getter
    @Setter
    private Object metadata;

    /**
     * Creates render properties for a button with relative width.
     *
     * @param width The width.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width) {
        return relativeProperties(width, false, false);
    }

    /**
     * Creates render properties for a button with relative width.
     *
     * @param width         The width.
     * @param groupBreaking Whether the button is render group breaking.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width, final boolean groupBreaking) {
        return relativeProperties(width, groupBreaking, false);
    }

    /**
     * Creates render properties for a button with relative width.
     *
     * @param width         The width.
     * @param groupBreaking Whether the button is render group breaking.
     * @param centered      Whether the button is centered.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width, final boolean groupBreaking,
                                                      final boolean centered) {
        return relativeProperties(width, groupBreaking, centered, Alignment.LEFT);
    }

    /**
     * Creates render properties for a button with relative width.
     *
     * @param width              The width.
     * @param groupBreaking      Whether the button is render group breaking.
     * @param centered           Whether the button is centered.
     * @param secondaryAlignment The secondary alignment of the button.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width, final boolean groupBreaking,
                                                      final boolean centered, final Alignment secondaryAlignment) {
        final RenderProperties.RenderPropertiesBuilder builder = RenderProperties.builder().relativeWidth(width)
                .absoluteHeight(20).secondaryAlignment(secondaryAlignment);
        if (groupBreaking) {
            builder.groupBreaking();
        }
        if (centered) {
            builder.centered();
        }
        return builder.build();
    }

    /**
     * Constructor.
     *
     * @param buttonListener   The listener that will react to button clicks.
     * @param renderProperties The render properties of this button.
     */
    public Button(final Consumer<Button> buttonListener, final RenderProperties renderProperties) {
        super(renderProperties);
        this.buttonListener = buttonListener;
        button = new ExtendedButton(0, 0, 0, 0, "", b -> buttonListener.accept(this));
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getMinecraftButtonRegistration().accept(button);

        button.x = positionX;
        button.y = positionY;
        button.setWidth(width);
        button.setHeight(height);
    }
}
