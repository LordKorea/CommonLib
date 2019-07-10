package nge.lk.mods.commonlib.gui.designer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nge.lk.mods.commonlib.gui.designer.util.Alignment;
import nge.lk.mods.commonlib.gui.designer.util.RequestedSize;

/**
 * Rendering properties.
 */
@Getter
@RequiredArgsConstructor
public class RenderProperties {

    /**
     * Whether an element ends a render group.
     */
    private final boolean isGroupBreaking;

    /**
     * Whether an element is centered.
     */
    private final boolean centered;

    /**
     * The size an element requests for itself.
     */
    private final RequestedSize requestedSize;

    /**
     * The secondary alignment requested by the element.
     */
    private final Alignment secondaryAlignment;
}
