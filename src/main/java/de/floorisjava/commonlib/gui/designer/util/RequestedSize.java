package de.floorisjava.commonlib.gui.designer.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a request for the size of an {@link de.floorisjava.commonlib.gui.designer.element.Element}.
 */
@Getter
@RequiredArgsConstructor
public class RequestedSize {

    /**
     * The requested width.
     */
    private final Dimension width;

    /**
     * The requested height.
     */
    private final Dimension height;
}
