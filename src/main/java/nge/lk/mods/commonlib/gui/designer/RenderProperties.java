package nge.lk.mods.commonlib.gui.designer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nge.lk.mods.commonlib.gui.designer.util.Alignment;
import nge.lk.mods.commonlib.gui.designer.util.Dimension;
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

    /**
     * Creates a builder for render properties.
     *
     * @return The builder.
     */
    public static RenderPropertiesBuilder builder() {
        return new RenderPropertiesBuilder();
    }

    /**
     * A builder for RenderProperties.
     */
    public static class RenderPropertiesBuilder {

        /**
         * Whether an element ends a render group.
         */
        private boolean isGroupBreaking = false;

        /**
         * Whether an element is centered.
         */
        private boolean centered = false;

        /**
         * The size an element requests for itself.
         */
        private RequestedSize requestSize = new RequestedSize(Dimension.absolute(0), Dimension.absolute(0));

        /**
         * The secondary alignment requested by the element.
         */
        private Alignment secondaryAlignment = Alignment.LEFT;

        /**
         * Makes the element group breaking.
         *
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder groupBreaking() {
            isGroupBreaking = true;
            return this;
        }

        /**
         * Makes the element centered.
         *
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder centered() {
            centered = true;
            return this;
        }

        /**
         * Sets the requested size.
         *
         * @param width  The requested width.
         * @param height The requested height.
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder requestSize(final Dimension width, final Dimension height) {
            requestSize = new RequestedSize(width, height);
            return this;
        }

        /**
         * Sets the secondary alignment.
         *
         * @param secondaryAlignment The secondary alignment.
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder secondaryAlignment(final Alignment secondaryAlignment) {
            this.secondaryAlignment = secondaryAlignment;
            return this;
        }

        /**
         * Builds the RenderProperties.
         *
         * @return The RenderProperties.
         */
        public RenderProperties build() {
            return new RenderProperties(isGroupBreaking, centered, requestSize, secondaryAlignment);
        }
    }
}
