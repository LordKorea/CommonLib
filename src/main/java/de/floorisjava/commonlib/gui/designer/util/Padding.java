package de.floorisjava.commonlib.gui.designer.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a box padding.
 */
@Getter
@RequiredArgsConstructor
public class Padding {

    /**
     * The top padding.
     */
    private final Dimension top;

    /**
     * The right padding.
     */
    private final Dimension right;

    /**
     * The bottom padding.
     */
    private final Dimension bottom;

    /**
     * The left padding.
     */
    private final Dimension left;

    /**
     * Creates a padding with relative dimensions.
     *
     * @param top    The top padding.
     * @param right  The right padding.
     * @param bottom The bottom padding.
     * @param left   The left padding.
     * @return The padding.
     */
    public static Padding relative(final int top, final int right, final int bottom, final int left) {
        return new Padding(Dimension.relative(top), Dimension.relative(right), Dimension.relative(bottom),
                Dimension.relative(left));
    }

    /**
     * Creates a padding with absolute dimensions.
     *
     * @param top    The top padding.
     * @param right  The right padding.
     * @param bottom The bottom padding.
     * @param left   The left padding.
     * @return The padding.
     */
    public static Padding absolute(final int top, final int right, final int bottom, final int left) {
        return new Padding(Dimension.absolute(top), Dimension.absolute(right), Dimension.absolute(bottom),
                Dimension.absolute(left));
    }
}
