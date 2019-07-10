package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiButton;
import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;

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
        ctx.getMinecraftButtonRegistration().accept(button);
        ctx.getButtonRegistration().accept(this);

        button.id = ctx.getButtonIdSupplier().getAsInt();
        button.x = positionX;
        button.y = positionY;
        button.width = width;
        button.height = height;
    }
}
