package nge.lk.mods.commonlib.gui.designer.util;

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
}
