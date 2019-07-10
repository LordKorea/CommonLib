package nge.lk.mods.commonlib.gui.designer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiButton;
import nge.lk.mods.commonlib.gui.designer.element.Button;
import nge.lk.mods.commonlib.gui.designer.element.Label;
import nge.lk.mods.commonlib.gui.designer.element.TextField;

import java.util.function.Consumer;
import java.util.function.IntSupplier;

/**
 * Rendering context for designer GUIs.
 */
@Getter
@RequiredArgsConstructor
public class RenderContext {

    /**
     * A consumer which is used for registering minecraft buttons.
     */
    private final Consumer<GuiButton> minecraftButtonRegistration;

    /**
     * A consumer which is used for registering buttons.
     */
    private final Consumer<Button> buttonRegistration;

    /**
     * A consumer which is used for registering labels.
     */
    private final Consumer<Label> labelRegistration;

    /**
     * A consumer which is used for text field registration.
     */
    private final Consumer<TextField> textFieldRegistration;

    /**
     * A supplier which provides button IDs.
     */
    private final IntSupplier buttonIdSupplier;
}
