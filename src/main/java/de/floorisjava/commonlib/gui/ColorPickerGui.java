package de.floorisjava.commonlib.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.gui.GuiUtils;
import de.floorisjava.commonlib.gui.designer.GuiDesigner;
import de.floorisjava.commonlib.gui.designer.RenderProperties;
import de.floorisjava.commonlib.gui.designer.element.Box;
import de.floorisjava.commonlib.gui.designer.element.Button;
import de.floorisjava.commonlib.gui.designer.element.Label;
import de.floorisjava.commonlib.gui.designer.util.Alignment;
import de.floorisjava.commonlib.gui.designer.util.Padding;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

/**
 * A color picker (HSV picking, converted on the fly to RGB).
 */
public class ColorPickerGui extends GuiDesigner {

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
     * The parent which receives the picked color. If this is a {@link net.minecraft.client.gui.screen.Screen} it will be opened after picking a
     * color.
     */
    private final ColorPicking parent;

    /**
     * Converts the given RGB color to a normalized HSV (HSB) array.
     *
     * @param rgb The input color.
     * @return The normalized HSV array.
     */
    private static float[] toHSV(final int rgb) {
        return Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null);
    }

    /**
     * Converts the given HSV array to a RGB color.
     *
     * @param hsv The HSV array.
     * @return The equivalent RGB color.
     */
    private static int fromHSV(final float[] hsv) {
        return Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
    }

    /**
     * Constructor.
     *
     * @param preset The preset color.
     * @param parent The {@link de.floorisjava.commonlib.gui.ColorPicking} which will receive the picked color.
     */
    public ColorPickerGui(final int preset, final ColorPicking parent) {
        this.parent = parent;
        selectedColor = toHSV(preset);
        createGui();
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);

        // Draw the HSV labels, each label 25 pixels apart from each other, this is also the distance between the
        // HSV selectors.
        font.drawString("Hue",
                (width - 256.0f) / 2 - font.getStringWidth("Hue") - 10,
                height / 4.0f + 6,
                0xA0A0A0);
        font.drawString("Saturation",
                (width - 256.0f) / 2 - font.getStringWidth("Saturation") - 10,
                height / 4.0f + 31,
                0xA0A0A0);
        font.drawString("Value",
                (width - 256.0f) / 2 - font.getStringWidth("Value") - 10,
                height / 4.0f + 56,
                0xA0A0A0);

        // Hue selector.
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        Objects.requireNonNull(minecraft).getTextureManager().bindTexture(HUE_SPECTRE);
        GuiUtils.drawTexturedModalRect((width - 256) / 2, height / 4, 0, 0, 256, 20, 0.0f);

        // Draw gradient for saturation: White (lowest saturation) to (HSV) YYFFFF (full saturation, full value) for the
        // current hue YY.
        drawHorizontalGradientRect((width - 256) / 2, height / 4 + 25, (width - 256) / 2 + 256,
                height / 4 + 45, 0xFFFFFFFF, fromHSV(new float[]{ selectedColor[0], 1.0f, 1.0f }));

        // Draw gradient for value: Black (lowest value) to (HSV) YYZZFF (full value) for the current hue YY and the
        // current saturation ZZ.
        drawHorizontalGradientRect((width - 256) / 2, height / 4 + 50, (width - 256) / 2 + 256,
                height / 4 + 70, 0xFF000000,
                fromHSV(new float[]{ selectedColor[0], selectedColor[1], 1.0f }));

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
    public boolean mouseClicked(final double mouseX, final double mouseY, final int mouseButton) {
        updateColor(mouseX, mouseY);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void createGui() {
        final Box contentPane = new Box(RenderProperties.fullSize(),
                Padding.relative(10, 5, 20, 5));
        contentPane.addRenderBucket(Alignment.TOP, Label.centered("Color Picker", 0xAAAAAA));

        final Button button = new Button(b -> closeGui(),
                Button.relativeProperties(30, true, true));
        button.getButton().setMessage("Choose Color");
        contentPane.addRenderBucket(Alignment.BOTTOM, button);

        root.addRenderBucket(Alignment.TOP, contentPane);
    }

    @Override
    protected void closeGui() {
        parent.onPickColor(fromHSV(selectedColor));

        // If the parent object is a GuiScreen, (re-)open it.
        if (parent instanceof Screen) {
            Objects.requireNonNull(minecraft).displayGuiScreen((Screen) parent);
        }
    }

    @Override
    public boolean mouseDragged(final double mouseX, final double mouseY, final int clickedMouseButton,
                                final double deltaX, final double deltaY) {
        updateColor(mouseX, mouseY);
        return super.mouseDragged(mouseX, mouseY, clickedMouseButton, deltaX, deltaY);
    }

    /**
     * Updates the color when a mouse interaction at the given position occurs.
     *
     * @param mouseX the x position where the mouse interaction happened.
     * @param mouseY the y position where the mouse interaction happened.
     */
    private void updateColor(final double mouseX, final double mouseY) {
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
                    selectedColor[i] = ((float) mouseX - xBegin) / (xEnd - xBegin);
                    return;
                }
            }
        }
    }

    /**
     * Draws a rectangle.
     *
     * @param left   The distance to the left border of the GUI.
     * @param top    The distance to the top border of the GUI.
     * @param right  The distance to the right border of the GUI.
     * @param bottom The distance to the bottom border of the GUI.
     * @param color  The color of the rect.
     */
    private void drawRect(final int left, final int top, final int right, final int bottom,
                          final int color) {
        drawHorizontalGradientRect(left, top, right, bottom, color, color);
    }

    /**
     * Draws a rectangle with a horizontal color gradient.
     *
     * @param left       The distance to the left border of the GUI.
     * @param top        The distance to the top border of the GUI.
     * @param right      The distance to the right border of the GUI.
     * @param bottom     The distance to the bottom border of the GUI.
     * @param startColor The starting color of the gradient (left hand color).
     * @param endColor   The end color of the gradient (right hand color).
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
        RenderSystem.disableTexture();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        // Draw the gradient rect.
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, 0.0)
                .color(endRed, endGreen, endBlue, endAlpha).endVertex();
        bufferbuilder.pos(left, top, 0.0)
                .color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferbuilder.pos(left, bottom, 0.0)
                .color(startRed, startGreen, startBlue, startAlpha).endVertex();
        bufferbuilder.pos(right, bottom, 0.0)
                .color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();

        // Undo OpenGL state setup.
        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }
}
