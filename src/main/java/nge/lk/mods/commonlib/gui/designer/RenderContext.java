package nge.lk.mods.commonlib.gui.designer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.gui.GuiButton;
import nge.lk.mods.commonlib.gui.designer.element.Button;

import java.util.function.Consumer;

/**
 * Rendering context for designer GUIs.
 */
@Getter
@RequiredArgsConstructor
public class RenderContext {

    /**
     * A consumer which is used for registering buttons.
     */
    private final Consumer<Button> buttonRegistration;
}
