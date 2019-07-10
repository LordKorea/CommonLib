package nge.lk.mods.commonlib.gui.designer.element;

import net.minecraft.client.gui.GuiSlider;
import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;
import nge.lk.mods.commonlib.gui.factory.FloatResponder;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * A slider in a GUI.
 */
public class Slider extends BaseElement {

    /**
     * The internal slider element.
     */
    private final GuiSlider slider;

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
    public Slider(final float min, final float max, final float defaultValue,
                  final BiFunction<Slider, Float, String> formatter, final BiConsumer<Slider, Float> responder,
                  final RenderProperties renderProperties) {
        super(renderProperties);
        slider = new GuiSlider((FloatResponder) (id1, value) -> responder.accept(this, value), -1, 0, 0,
                "", min, max, defaultValue, (id12, name, value) -> formatter.apply(this, value));
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        super.prepareRender(ctx);
        ctx.getMinecraftButtonRegistration().accept(slider);

        slider.x = positionX;
        slider.y = positionY;
        slider.width = width;
        slider.height = height;
    }
}
