package de.floorisjava.commonlib.gui.designer;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;
import de.floorisjava.commonlib.gui.designer.element.Box;
import de.floorisjava.commonlib.gui.designer.element.Label;
import de.floorisjava.commonlib.gui.designer.element.RootBox;
import de.floorisjava.commonlib.gui.designer.element.TextField;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a custom designed GUI.
 *
 * @see net.minecraft.client.gui.screen.Screen
 */
public abstract class GuiDesigner extends Screen {

    /**
     * The root element.
     */
    protected final Box root = new RootBox();

    /**
     * The labels in this GUI.
     */
    private final List<Label> labels = new ArrayList<>();

    /**
     * The text fields in this GUI.
     */
    private final List<TextField> textFields = new ArrayList<>();

    protected GuiDesigner() {
        super(new StringTextComponent(""));
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        renderBackground();

        // Draw the labels in this GUI.
        labels.forEach(l -> drawString(font, l.getCaption(), l.getPositionX(), l.getPositionY(), l.getColor()));

        // Draw the input elements.
        textFields.forEach(t -> t.getTextField().render(mouseX, mouseY, partialTicks));

        // Takes care of buttons etc.
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void init() {
        // Clear GuiScreen's button list.
        super.buttons.clear();
        labels.clear();
        textFields.clear();

        // Prepare the root box and all its children for rendering.
        final RenderContext ctx = new RenderContext(super.buttons::add, labels::add, textFields::add);
        root.setWidth(width);
        root.setHeight(height);
        root.prepareRender(ctx);

        // Enable repeat events for input elements.
        Objects.requireNonNull(minecraft).keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public void tick() {
        // Update all input elements.
        textFields.forEach(t -> t.getTextField().tick());
    }

    @Override
    public void onClose() {
        // Disable repeat events (for ingame keyboard mechanics).
        Objects.requireNonNull(minecraft).keyboardListener.enableRepeatEvents(false);
        super.onClose();
    }

    @Override
    public boolean charTyped(final char typedChar, final int keyCode) {
        final boolean result = super.charTyped(typedChar, keyCode);

        // All GUIs use ESC for closing.
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            closeGui();
            return true;
        }

        // Forward key events to all input elements.
        textFields.forEach(t -> t.getTextField().charTyped(typedChar, keyCode));

        return result;
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int mouseButton) {
        final boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);

        // Forward mouse clicks to all input elements.
        textFields.forEach(t -> t.getTextField().mouseClicked(mouseX, mouseY, mouseButton));

        return result;
    }

    /**
     * This method should setup all elements for this GUI.
     * <p>
     * The derived class has to call it by itself before the GUI is opened.
     */
    protected abstract void createGui();

    /**
     * Closes this GUI.
     */
    protected void closeGui() {
        Objects.requireNonNull(minecraft).displayGuiScreen(null);
    }
}
