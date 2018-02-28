package nge.lk.mods.commonlib.gui.factory.element;

import lombok.Getter;
import net.minecraft.client.gui.GuiSlider;
import nge.lk.mods.commonlib.gui.factory.FloatResponder;
import nge.lk.mods.commonlib.gui.factory.Positioning;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Represents a slider input.
 *
 * @see GuiSlider
 */
@Getter
public class SliderElement extends BaseElement {

    /**
     * The internal slider element.
     */
    private final GuiSlider slider;

    /**
     * Constructor.
     *
     * @param min The minimum possible value of the slider.
     * @param max The maximum possible value of the slider.
     * @param defaultValue The default value of the slider.
     * @param formatter The formatter for the slider caption: {@code (Element, Value) -> String}.
     * @param responder The responder to slider value changes.
     * @param positioning The positioning of this element.
     */
    public SliderElement(final float min, final float max, final float defaultValue,
                         final BiFunction<SliderElement, Float, String> formatter,
                         final BiConsumer<SliderElement, Float> responder, final Positioning positioning) {
        super(positioning, 3, 5);

        slider = new GuiSlider(
                (FloatResponder) (id1, value) -> responder.accept(this, value),
                -1,
                0,
                0,
                "",
                min,
                max,
                defaultValue,
                (id12, name, value) -> formatter.apply(this, value)
        );
    }

    @Override
    public void fixPosition(final int x, final int y) {
        super.fixPosition(x, y);

        slider.xPosition = x;
        slider.yPosition = y;
    }

    @Override
    public void fixDimensions(final int globalWidth, final int globalHeight) {
        super.fixDimensions(globalWidth, globalHeight);

        // Width is hard-limited to 399 units by the slider texture itself.
        width = Math.min(width, 399);

        slider.width = width;
        slider.height = height;
    }
}
