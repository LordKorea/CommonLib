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
     * Constructor.
     *
     * @param renderProperties The render properties of this label.
     */
    public TextField(final RenderProperties renderProperties) {
        super(renderProperties);
        textField = new GuiTextField(0, Minecraft.getMinecraft().fontRenderer, 0, 0, 0,
                0);
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getTextFieldRegistration().accept(this);

        textField.x = positionX;
        textField.y = positionY;
        textField.width = width;
        textField.height = height;
    }
}
