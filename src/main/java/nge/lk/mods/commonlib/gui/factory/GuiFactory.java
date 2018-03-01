package nge.lk.mods.commonlib.gui.factory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import nge.lk.mods.commonlib.gui.factory.element.BaseElement;
import nge.lk.mods.commonlib.gui.factory.element.ButtonElement;
import nge.lk.mods.commonlib.gui.factory.element.InputElement;
import nge.lk.mods.commonlib.gui.factory.element.SliderElement;
import nge.lk.mods.commonlib.gui.factory.element.TextElement;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Represents a GUI screen which can be created in easy steps without too much arithmetic hassles.
 *
 * @see GuiScreen
 */
public abstract class GuiFactory extends GuiScreen {

    /**
     * The elements of this GUI, regardless of actual type.
     */
    private final List<BaseElement> elements;

    /**
     * Text elements of this GUI. Needed for special treatment.
     */
    private final List<TextElement> textElements;

    /**
     * Input elements of this GUI. Needed for special treatment.
     */
    private final List<InputElement> inputElements;

    /**
     * Button elements of this GUI. Needed for special treatment.
     */
    private final List<ButtonElement> buttonElements;

    /**
     * The padding to the top of the GUI, as a percentage.
     */
    private double paddingTop;

    /**
     * The padding to the left of the GUI, as a percentage.
     */
    private double paddingLeft;

    /**
     * The padding to the bottom of the GUI, as a percentage.
     */
    private double paddingBottom;

    /**
     * The padding to the right of the GUI, as a percentage.
     */
    private double paddingRight;

    /**
     * Constructor.
     */
    protected GuiFactory() {
        elements = new LinkedList<>();
        textElements = new LinkedList<>();
        inputElements = new LinkedList<>();
        buttonElements = new ArrayList<>();
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawDefaultBackground();

        // Draw the text elements.
        for (final TextElement element : textElements) {
            if (element.getPositioning().isCentered()) {
                drawCenteredString(fontRendererObj, element.getCaption(), width / 2, element.getFixedY(),
                        element.getColor());
            } else {
                drawString(fontRendererObj, element.getCaption(), element.getFixedX(), element.getFixedY(),
                        element.getColor());
            }
        }

        // Draw the input elements.
        for (final InputElement element : inputElements) {
            element.getTextField().drawTextBox();
        }

        // Takes care of buttons etc.
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        // Clear GuiScreen's button list.
        buttonList.clear();

        // Calculate the actual padding given global width/height.
        final int[] padding = new int[]{
                (int) (height * paddingTop),
                (int) (width * paddingLeft),
                (int) (height * paddingBottom),
                (int) (width * paddingRight)
        };

        // Setup the x-coordinate cursors for all four alignment modes.
        final int[] xCursorsDefault = new int[]{
                padding[1],         // top aligned left aligned
                width - padding[3], // top aligned right aligned
                padding[1],         // bottom aligned left aligned
                width - padding[3], // bottom aligned right aligned
        };
        final int[] xCursors = xCursorsDefault.clone();

        // Setup the y-coordinate cursors for both vertical alignment modes.
        final int[] yCursors = new int[]{
                padding[0],          // top aligned
                height - padding[2], // bottom aligned
        };

        for (final BaseElement element : elements) {
            final Positioning positioning = element.getPositioning();

            // Find out the cursors and direction vector for the alignment of this element.
            final int cursorX = (positioning.isBottomAligned() ? 2 : 0) + (positioning.isRightAligned() ? 1 : 0);
            final int cursorY = positioning.isBottomAligned() ? 1 : 0;
            final int directionY = positioning.isBottomAligned() ? -1 : 1;

            // Fix the dimensions of this element now that global width/height are known.
            element.fixDimensions(width, height);

            if (positioning.isRightAligned()) {
                // Move cursor before fixing the elements position to have the cursor at the left of the element.
                xCursors[cursorX] -= element.getWidth();
            }

            if (positioning.isCentered() && !(element instanceof TextElement)) {
                // Generic centering. TextElements use the FontRenderer.
                element.fixPosition(width / 2 - element.getWidth() / 2, yCursors[cursorY]);
            } else {
                element.fixPosition(xCursors[cursorX], yCursors[cursorY]);
            }

            if (!positioning.isRightAligned()) {
                xCursors[cursorX] += element.getHorizontalBuffer() + element.getWidth();
            } else {
                // Width was already subtracted before.
                xCursors[cursorX] -= element.getHorizontalBuffer();
            }

            if (positioning.isBreakingRow()) {
                // Switch to the next line.
                xCursors[cursorX] = xCursorsDefault[cursorX];
                yCursors[cursorY] += (element.getVerticalBuffer() + element.getHeight()) * directionY;
            }

            // Add buttons/sliders to GuiScreen's internal rendering list.
            if (element instanceof ButtonElement) {
                buttonList.add(((ButtonElement) element).getButton());
            }
            if (element instanceof SliderElement) {
                buttonList.add(((SliderElement) element).getSlider());
            }
        }

        // Enable repeat events for input elements.
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void updateScreen() {
        // Update all input elements.
        for (final InputElement input : inputElements) {
            input.getTextField().updateCursorCounter();
        }
    }

    @Override
    public void onGuiClosed() {
        // Disable repeat events (for ingame keyboard mechanics).
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        // All GUIs use ESC for closing.
        if (keyCode == Keyboard.KEY_ESCAPE) {
            closeGui();
        }

        // Forward key events to all input elements.
        for (final InputElement element : inputElements) {
            element.getTextField().textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        // Forward mouse clicks to all input elements.
        for (final InputElement element : inputElements) {
            element.getTextField().mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        // Only perform actions on enabled buttons.
        if (button.enabled) {
            if (button.id < buttonElements.size() && button.id >= 0) {
                final ButtonElement element = buttonElements.get(button.id);
                element.getButtonListener().accept(element);
            }
        }
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
        mc.displayGuiScreen(null);
    }

    /**
     * Sets the padding for this GUI.
     *
     * @param percentTop The top padding, as a percentage.
     * @param percentLeft The left padding, as a percentage.
     * @param percentBottom The bottom padding, as a percentage.
     * @param percentRight The right padding, as a percentage.
     */
    protected void setPadding(final double percentTop, final double percentLeft, final double percentBottom,
                              final double percentRight) {
        paddingTop = percentTop;
        paddingLeft = percentLeft;
        paddingBottom = percentBottom;
        paddingRight = percentRight;
    }

    /**
     * Adds a blank element for spacing purposes.
     *
     * @param positioning The positioning of the blank element.
     */
    protected void addBlank(final Positioning positioning) {
        elements.add(new BaseElement(positioning, 0, 0));
    }

    /**
     * Adds a text element.
     *
     * @param positioning The positioning of the text element.
     *
     * @return The created text element.
     */
    protected TextElement addText(final Positioning positioning) {
        final TextElement element = new TextElement(positioning);
        elements.add(element);
        textElements.add(element);
        return element;
    }

    /**
     * Adds an input element.
     *
     * @param positioning The positioning of the input element.
     *
     * @return The created input element.
     */
    protected InputElement addInput(final Positioning positioning) {
        final InputElement element = new InputElement(positioning);
        elements.add(element);
        inputElements.add(element);
        return element;
    }

    /**
     * Adds a button element.
     *
     * @param buttonListener The listener for button clicks.
     * @param positioning The positioning of this element.
     *
     * @return The created button element.
     */
    protected ButtonElement addButton(final Consumer<ButtonElement> buttonListener, final Positioning positioning) {
        final ButtonElement element = new ButtonElement(buttonElements.size(), buttonListener, positioning);
        elements.add(element);
        buttonElements.add(element);
        return element;
    }

    /**
     * Adds a slider element.
     *
     * @param min The minimum slider value.
     * @param max The maximum slider value.
     * @param def The default slider value.
     * @param formatter The formatter for the slider caption: {@code (Element, Value) -> Caption}.
     * @param responder The responder for value changes.
     * @param positioning The positioning of this element.
     *
     * @return The created slider element.
     */
    protected SliderElement addSlider(final float min, final float max, final float def,
                                      final BiFunction<SliderElement, Float, String> formatter,
                                      final BiConsumer<SliderElement, Float> responder, final Positioning positioning) {
        final SliderElement element = new SliderElement(min, max, def, formatter, responder, positioning);
        elements.add(element);
        return element;
    }
}
