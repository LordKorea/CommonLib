package de.floorisjava.commonlib.gui.designer.element;

import net.minecraft.client.gui.widget.AbstractSlider;
import de.floorisjava.commonlib.gui.designer.RenderContext;
import de.floorisjava.commonlib.gui.designer.RenderProperties;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * A slider in a GUI.
 */
public class Slider extends BaseElement {

    /**
     * The internal slider element.
     */
    private final AbstractSlider slider;

    /**
     * Constructor.
     *
     * @param min              The minimum possible value of the slider.
     * @param max              The maximum possible value of the slider.
     * @param defaultValue     The default value of the slider.
     * @param formatter        The formatter for the slider caption: {@code (Element, Value) -> String}.
     * @param responder        The responder to slider value changes.
     * @param renderProperties The render properties of this button.
     */
    public Slider(final double min, final double max, final double defaultValue,
                  final BiFunction<Slider, Double, String> formatter, final BiConsumer<Slider, Double> responder,
                  final RenderProperties renderProperties) {
        super(renderProperties);
        slider = new AbstractSlider(0, 0, 0, 0, unlerp(min, max, defaultValue)) {
            @Override
            protected void updateMessage() {
                setMessage(formatter.apply(Slider.this, lerp(min, max, value)));
            }

            @Override
            protected void applyValue() {
                responder.accept(Slider.this, lerp(min, max, value));
            }
        };
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getMinecraftButtonRegistration().accept(slider);

        slider.x = positionX;
        slider.y = positionY;
        slider.setWidth(width);
        slider.setHeight(height);
    }

    /**
     * Reverses a lerp(m, x, l): unlerp(m, x, lerp(m, x, l)) is approximately equal to l.
     *
     * @param min The lower bound.
     * @param max The upper bound.
     * @param val The lerped value.
     * @return The lerp factor.
     */
    private double unlerp(final double min, final double max, final double val) {
        return (val - min) / (max - min);
    }

    /**
     * Linear interpolation.
     *
     * @param min    The lower bound.
     * @param max    The upper bound.
     * @param factor The lerp factor.
     * @return The lerped value.
     */
    private double lerp(final double min, final double max, final double factor) {
        return (max - min) * factor + min;
    }
}
