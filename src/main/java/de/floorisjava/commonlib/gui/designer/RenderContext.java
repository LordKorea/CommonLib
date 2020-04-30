package de.floorisjava.commonlib.gui.designer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import de.floorisjava.commonlib.gui.designer.element.Label;
import de.floorisjava.commonlib.gui.designer.element.TextField;

import java.util.function.Consumer;

/**
 * Rendering context for designer GUIs.
 */
@Getter
@RequiredArgsConstructor
public class RenderContext {

    /**
     * A consumer which is used for registering minecraft buttons.
     */
    private final Consumer<net.minecraft.client.gui.widget.Widget> minecraftButtonRegistration;

    /**
     * A consumer which is used for registering labels.
     */
    private final Consumer<Label> labelRegistration;

    /**
     * A consumer which is used for text field registration.
     */
    private final Consumer<TextField> textFieldRegistration;
}
