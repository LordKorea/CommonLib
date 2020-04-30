package de.floorisjava.commonlib.gui.designer.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents an alignment.
 */
@RequiredArgsConstructor
public enum Alignment {

    /**
     * Alignment to the top.
     */
    TOP(AlignmentCategory.VERTICAL, 0, 1),

    /**
     * Alignment to the right.
     */
    RIGHT(AlignmentCategory.HORIZONTAL, 0, -1),

    /**
     * Alignment to the bottom.
     */
    BOTTOM(AlignmentCategory.VERTICAL, 1, -1),

    /**
     * Alignment to the left.
     */
    LEFT(AlignmentCategory.HORIZONTAL, 1, 1);

    /**
     * The alignment category of the alignment.
     */
    private final AlignmentCategory category;

    /**
     * The offset of this alignment in its category.
     */
    private @Getter
    final int subAlignmentOffset;

    /**
     * The direction in which a cursor with this alignment moves.
     */
    @Getter
    private final int cursorDirection;

    /**
     * Checks if the given alignment is a valid secondary alignment for this alignment.
     *
     * @param secondary The secondary alignment.
     * @return {@code true} iff the given alignment is a valid secondary alignment for this alignment.
     */
    public boolean isValidSecondaryAlignment(final Alignment secondary) {
        return category != secondary.category;
    }

    /**
     * Represents the different alignment categories.
     */
    private enum AlignmentCategory {

        /**
         * Represents a vertical alignment.
         */
        VERTICAL,

        /**
         * Represents a horizontal alignment.
         */
        HORIZONTAL
    }
}
