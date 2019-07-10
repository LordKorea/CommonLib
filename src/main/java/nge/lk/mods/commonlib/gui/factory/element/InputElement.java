package nge.lk.mods.commonlib.gui.factory.element;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import nge.lk.mods.commonlib.gui.factory.Positioning;

/**
 * Represents a text input field.
 *
 * @see GuiTextField
 * @deprecated Use {@link nge.lk.mods.commonlib.gui.designer}
 */
@Deprecated
@Getter
public class InputElement extends BaseElement {

    /**
     * The internal text field.
     */
    private final GuiTextField textField;

    /**
     * Constructor.
     *
     * @param positioning This element's positioning.
     */
    public InputElement(final Positioning positioning) {
        super(positioning, 3, 5);

        textField = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, 0, 0, 0,
                0);
    }

    @Override
    public void fixPosition(final int x, final int y) {
        super.fixPosition(x, y);

        textField.xPosition = x;
        textField.yPosition = y;
    }

    @Override
    public void fixDimensions(final int globalWidth, final int globalHeight) {
        super.fixDimensions(globalWidth, globalHeight);

        textField.width = width;
        textField.height = height;
    }
}
