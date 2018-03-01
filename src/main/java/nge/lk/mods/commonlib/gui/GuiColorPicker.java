package nge.lk.mods.commonlib.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import nge.lk.mods.commonlib.gui.factory.GuiFactory;
import nge.lk.mods.commonlib.gui.factory.Positioning;
import nge.lk.mods.commonlib.gui.factory.element.ButtonElement;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * A color picker (HSV picking, converted on the fly to RGB).
 */
public class GuiColorPicker extends GuiFactory implements Consumer<ButtonElement> {

    /**
     * The spectre used for hue selection. This is not easily generated using OpenGL so it is hardcoded.
     */
    private static final ResourceLocation HUE_SPECTRE = new ResourceLocation(CommonLibConstants.RESOURCE_DOMAIN,
            "textures/hue.png");

    /**
     * The currently selected color (HSV)
     */
    private final float[] selectedColor;

    /**
     * The parent which receives the picked color. If this is a {@link GuiScreen} it will be opened after picking a
     * color.
     */
    private final ColorPicking parent;

    /**
     * Converts the given RGB color to a normalized HSV (HSB) array.
     *
     * @param rgb The input color.
     *
     * @return The normalized HSV array.
     */
    private static float[] toHSV(final int rgb) {
        return Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null);
    }

    /**
     * Converts the given HSV array to a RGB color.
     *
     * @param hsv The HSV array.
     *
     * @return The equivalent RGB color.
     */
    private static int fromHSV(final float[] hsv) {
        return Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
    }

    /**
     * Constructor.
     *
     * @param preset The preset color.
     * @param parent The {@link ColorPicking} which will receive the picked color.
     */
    public GuiColorPicker(final int preset, final ColorPicking parent) {
        this.parent = parent;
        selectedColor = toHSV(preset);
        createGui();
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        // Draw the HSV labels, each label 25 pixels apart from each other, this is also the distance between the
        // HSV selectors.
        fontRendererObj.drawString("Hue",
                (width - 256) / 2 - fontRendererObj.getStringWidth("Hue") - 10,
                height / 4 + 6,
                0xA0A0A0);
        fontRendererObj.drawString("Saturation",
                (width - 256) / 2 - fontRendererObj.getStringWidth("Saturation") - 10,
                height / 4 + 31,
                0xA0A0A0);
        fontRendererObj.drawString("Value",
                (width - 256) / 2 - fontRendererObj.getStringWidth("Value") - 10,
                height / 4 + 56,
                0xA0A0A0);

        // Hue selector.
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(HUE_SPECTRE);
        drawTexturedModalRect((width - 256) / 2, height / 4, 0, 0, 256, 20);

        // Draw gradient for saturation: White (lowest saturation) to (HSV) YYFFFF (full saturation, full value) for the
        // current hue YY.
        drawHorizontalGradientRect((width - 256) / 2, height / 4 + 25, (width - 256) / 2 + 256,
                height / 4 + 45, 0xFFFFFFFF, fromHSV(new float[]{selectedColor[0], 1.0f, 1.0f}));

        // Draw gradient for value: Black (lowest value) to (HSV) YYZZFF (full value) for the current hue YY and the
        // current saturation ZZ.
        drawHorizontalGradientRect((width - 256) / 2, height / 4 + 50, (width - 256) / 2 + 256,
                height / 4 + 70, 0xFF000000,
                fromHSV(new float[]{selectedColor[0], selectedColor[1], 1.0f}));

        // Slider markers drawn onto the selectors.
        drawRect((int) ((width - 256) / 2 - 1 + 256 * selectedColor[0]), height / 4 - 2,
                (int) ((width - 256) / 2.0f + 1 + 256 * selectedColor[0]), height / 4 + 22, 0xAADDDDDD);
        drawRect((int) ((width - 256) / 2 - 1 + 256 * selectedColor[1]), height / 4 + 23,
                (int) ((width - 256) / 2.0f + 1 + 256 * selectedColor[1]), height / 4 + 47, 0xAADDDDDD);
        drawRect((int) ((width - 256) / 2 - 1 + 256 * selectedColor[2]), height / 4 + 48,
                (int) ((width - 256) / 2.0f + 1 + 256 * selectedColor[2]), height / 4 + 72, 0xAADDDDDD);

        // A rect containing the result color is also drawn to show it to the user.
        drawRect((width - 128) / 2, height / 4 + 75, (width - 128) / 2 + 128,
                height / 4 + 95, fromHSV(selectedColor));
    }

    @Override
    public void accept(final ButtonElement buttonElement) {
        closeGui();
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        updateColor(mouseX, mouseY);
    }

    @Override
    protected void createGui() {
        setPadding(0.1, 0.05, 0.2, 0.05);

        addText(new Positioning().center().breakRow()).setText("Color Picker", 0xA0A0A0);

        final Positioning chooseButtonPosition =
                new Positioning().alignBottom().center().relativeWidth(30).absoluteHeight(20);
        addButton(this, chooseButtonPosition).getButton().displayString = "Choose Color";
    }

    @Override
    protected void closeGui() {
        parent.onPickColor(fromHSV(selectedColor));

        // If the parent object is a GuiScreen, (re-)open it.
        if (parent instanceof GuiScreen) {
            mc.displayGuiScreen((GuiScreen) parent);
        }
    }

    @Override
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton,
                                  final long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        updateColor(mouseX, mouseY);
    }

    /**
     * Updates the color when a mouse interaction at the given position occurs.
     *
     * @param mouseX the x position where the mouse interaction happened.
     * @param mouseY the y position where the mouse interaction happened.
     */
    private void updateColor(final int mouseX, final int mouseY) {
        final int xBegin = (width - 256) / 2;
        final int xEnd = (width - 256) / 2 + 256;
        if (mouseX >= xBegin && mouseX <= xEnd) {
            // Check Y bounds for every selector. Selectors are 25 units apart from each other.
            for (int i = 0; i < 3; i++) {
                final int yBegin = height / 4 + 25 * i;
                final int yEnd = height / 4 + 25 * i + 20;
                if (mouseY >= yBegin && mouseY <= yEnd) {
                    // The click hit the selector.
                    // The color component should be set to a value in between 0.0 and 1.0.
                    // For this, the distance of the click to the left border is divided by the length of the slider.
                    selectedColor[i] = (mouseX - xBegin) / ((float) (xEnd - xBegin));
                    return;
                }
            }
        }
    }

    /**
     * Draws a rectangle with a horizontal color gradient.
     *
     * @param left The distance to the left border of the GUI.
     * @param top The distance to the top border of the GUI.
     * @param right The distance to the right border of the GUI.
     * @param bottom The distance to the bottom border of the GUI.
     * @param startColor The starting color of the gradient (left hand color).
     * @param endColor The end color of the gradient (right hand color).
     */
    private void drawHorizontalGradientRect(final int left, final int top, final int right, final int bottom,
                                            final int startColor, final int endColor) {
        // Convert unsigned-byte RGBA into normalized float RGBA.
        final float startAlpha = (float) (startColor >> 24 & 255) / 255.0F;
        final float startRed = (float) (startColor >> 16 & 255) / 255.0F;
        final float startGreen = (float) (startColor >> 8 & 255) / 255.0F;
        final float startBlue = (float) (startColor & 255) / 255.0F;
        final float endAlpha = (float) (endColor >> 24 & 255) / 255.0F;
        final float endRed = (float) (endColor >> 16 & 255) / 255.0F;
        final float endGreen = (float) (endColor >> 8 & 255) / 255.0F;
        final float endBlue = (float) (endColor & 255) / 255.0F;

        // Setup OpenGL state.
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        // Draw the gradient rect.
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer bufferbuilder = tessellator.getWorldRenderer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) right, (double) top, (double) zLevel)
                .color(endRed, endGreen, endBlue, endAlpha).endVertex();
        bufferbuilder.pos((double) left, (double) top, (double) zLevel)
                .color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferbuilder.pos((double) left, (double) bottom, (double) zLevel)
                .color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, (double) zLevel)
                .color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();

        // Undo OpenGL state setup.
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
