package nge.lk.mods.commonlib.gui.factory.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import nge.lk.mods.commonlib.gui.factory.Positioning;

import java.util.function.Consumer;

/**
 * Represents a button element.
 *
 * @see GuiButton
 * @deprecated Use {@link nge.lk.mods.commonlib.gui.designer}
 */
@Deprecated
@Getter
public class ButtonElement extends BaseElement {

    /**
     * The internal button element.
     */
    private final GuiButton button;

    /**
     * The button listener. Clicks will be forwarded to this listener.
     */
    private final Consumer<ButtonElement> buttonListener;

    /**
     * The metadata of this button. Often useful for auto-generated buttons which need to know their source to
     * automate click handling.
     */
    @Setter private Object metadata;

    /**
     * Constructor.
     *
     * @param id             The id of this button.
     * @param buttonListener The listener that will react to button clicks.
     * @param positioning    The positioning of this element.
     */
    public ButtonElement(final int id, final Consumer<ButtonElement> buttonListener, final Positioning positioning) {
        super(positioning, 3, 5);
        this.buttonListener = buttonListener;
        button = new GuiButton(id, 0, 0, 0, 0, "");
    }

    @Override
    public void fixPosition(final int x, final int y) {
        super.fixPosition(x, y);

        button.xPosition = x;
        button.yPosition = y;
    }

    @Override
    public void fixDimensions(final int globalWidth, final int globalHeight) {
        super.fixDimensions(globalWidth, globalHeight);

        // 8 + display string width: Minimum size for a button with a buffer of 4 units to either side.
        width = Math.max(width, 8 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(button.displayString));

        button.width = width;
        button.height = height;
    }
}
