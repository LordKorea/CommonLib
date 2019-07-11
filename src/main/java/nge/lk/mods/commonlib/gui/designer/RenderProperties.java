package nge.lk.mods.commonlib.gui.designer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nge.lk.mods.commonlib.gui.designer.util.Alignment;
import nge.lk.mods.commonlib.gui.designer.util.Dimension;
import nge.lk.mods.commonlib.gui.designer.util.RequestedSize;

/**
 * Rendering properties.
 */
@Getter
@AllArgsConstructor
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
    private @Setter RequestedSize requestedSize;

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
     * Returns render properties that request the full available size.
     *
     * @return The render properties.
     */
    public static RenderProperties fullSize() {
        return builder().fullSize().build();
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
         * Sets the requested size to 100% relative size.
         *
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder fullSize() {
            requestSize = new RequestedSize(Dimension.relative(100), Dimension.relative(100));
            return this;
        }

        /**
         * Sets the relative width of the requested size.
         *
         * @param val The value.
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder relativeWidth(final int val) {
            requestSize = new RequestedSize(Dimension.relative(val), requestSize.getHeight());
            return this;
        }

        /**
         * Sets the absolute width of the requested size.
         *
         * @param val The value.
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder absoluteWidth(final int val) {
            requestSize = new RequestedSize(Dimension.absolute(val), requestSize.getHeight());
            return this;
        }

        /**
         * Sets the relative height of the requested size.
         *
         * @param val The value.
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder relativeHeight(final int val) {
            requestSize = new RequestedSize(requestSize.getWidth(), Dimension.relative(val));
            return this;
        }

        /**
         * Sets the absolute height of the requested size.
         *
         * @param val The value.
         * @return {@code this}, for chaining.
         */
        public RenderPropertiesBuilder absoluteHeight(final int val) {
            requestSize = new RequestedSize(requestSize.getWidth(), Dimension.absolute(val));
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
