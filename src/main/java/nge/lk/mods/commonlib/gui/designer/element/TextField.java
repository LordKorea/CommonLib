package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;

/**
 * A text field in a GUI.
 */
public class TextField extends BaseElement {

    /**
     * The internal text field.
     */
    private @Getter final GuiTextField textField;

    /**
     * Creates render properties for a text field with relative width.
     *
     * @param width The width.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width) {
        return relativeProperties(width, false, false);
    }

    /**
     * Creates render properties for a text field with relative width.
     *
     * @param width         The width.
     * @param groupBreaking Whether the text field is render group breaking.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width, final boolean groupBreaking) {
        return relativeProperties(width, groupBreaking, false);
    }

    /**
     * Creates render properties for a text field with relative width.
     *
     * @param width         The width.
     * @param groupBreaking Whether the text field is render group breaking.
     * @param centered      Whether the text field is centered.
     * @return The render properties.
     */
    public static RenderProperties relativeProperties(final int width, final boolean groupBreaking,
                                                      final boolean centered) {
        final RenderProperties.RenderPropertiesBuilder builder = RenderProperties.builder().relativeWidth(width)
                .absoluteHeight(20);
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
     * @param renderProperties The render properties of this label.
     */
    public TextField(final RenderProperties renderProperties) {
        super(renderProperties);
        textField = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, 0, 0, 0,
                0);
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getTextFieldRegistration().accept(this);

        textField.xPosition = positionX;
        textField.yPosition = positionY;
        textField.width = width;
        textField.height = height;
    }
}
