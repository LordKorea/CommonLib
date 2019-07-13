package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiButton;
import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;
import nge.lk.mods.commonlib.gui.designer.util.Alignment;

import java.util.function.Consumer;

/**
 * A button in a GUI.
 */
public class Button extends BaseElement {

    /**
     * The internal button element.
     */
    private @Getter final GuiButton button;

    /**
     * The button listener. Clicks will be forwarded to this listener.
     */
    private @Getter final Consumer<Button> buttonListener;

    /**
     * The metadata of this button. Often useful for auto-generated buttons which need to know their source to
     * automate click handling.
     */
    private @Getter @Setter Object metadata;

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
     * @param width         The width.
     * @param groupBreaking Whether the button is render group breaking.
     * @param centered      Whether the button is centered.
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
        button = new GuiButton(-1, 0, 0, 0, 0, "");
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        button.id = ctx.getButtonIdSupplier().getAsInt();

        ctx.getMinecraftButtonRegistration().accept(button);
        ctx.getButtonRegistration().accept(this);

        button.xPosition = positionX;
        button.yPosition = positionY;
        button.width = width;
        button.height = height;
    }
}
