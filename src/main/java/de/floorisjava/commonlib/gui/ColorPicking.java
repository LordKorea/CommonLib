package de.floorisjava.commonlib.gui;

/**
 * Represents something that can receive a color from a {@link ColorPickerGui}.
 */
@FunctionalInterface
public interface ColorPicking {

    /**
     * Called when a color is picked.
     *
     * @param colorPicked The picked color.
     */
    void onPickColor(int colorPicked);
}
