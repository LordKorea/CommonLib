package nge.lk.mods.commonlib.gui.designer.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nge.lk.mods.commonlib.gui.designer.RenderContext;
import nge.lk.mods.commonlib.gui.designer.RenderProperties;
import nge.lk.mods.commonlib.gui.designer.util.Alignment;
import nge.lk.mods.commonlib.gui.designer.util.Dimension;
import nge.lk.mods.commonlib.gui.designer.util.Padding;
import nge.lk.mods.commonlib.gui.designer.util.RequestedSize;

import java.util.ArrayList;
import java.util.List;

/**
 * A box, which can contain further elements.
 */
public class Box extends BaseElement {

    /**
     * The render buckets of this box.
     */
    private final List<RenderBucket> renderBuckets = new ArrayList<>();

    /**
     * The padding of this box.
     */
    private final Padding padding;

    /**
     * Constructor.
     *
     * @param parent           The parent element.
     * @param renderProperties The requested render properties for the box.
     */
    public Box(final Element parent, final RenderProperties renderProperties) {
        this(parent, renderProperties, new Padding(Dimension.absolute(0), Dimension.absolute(0), Dimension.absolute(0),
                Dimension.absolute(0)));
    }

    /**
     * Constructor.
     *
     * @param parent           The parent element.
     * @param renderProperties The requested render properties for the box.
     * @param padding          The padding for the box.
     */
    public Box(final Element parent, final RenderProperties renderProperties, final Padding padding) {
        super(parent, renderProperties);
        this.padding = padding;
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        final int[] paddings = new int[]{
                padding.getTop().evaluate(height),
                padding.getRight().evaluate(width),
                padding.getBottom().evaluate(height),
                padding.getLeft().evaluate(width)
        };
        final int paddingClearedWidth = width - paddings[1] - paddings[3];
        final int paddingClearedHeight = height - paddings[0] - paddings[2];

        // Prepare all buckets.
        for (final RenderBucket bucket : renderBuckets) {
            final Alignment primaryAlignment = bucket.getAlignment();

            // Initialize the primary cursor and secondary cursors.
            int primaryCursor = getPrimaryCursor(primaryAlignment, paddings);
            final int[] defaultSecondaryCursors = new int[2];
            initializeSecondaryCursors(primaryAlignment, paddings, defaultSecondaryCursors);

            // Determine element render groups in this bucket.
            final List<List<Element>> renderGroups = new ArrayList<>();
            List<Element> activeGroup = new ArrayList<>();
            for (final Element element : bucket.getElements()) {
                activeGroup.add(element);
                if (element.getRenderProperties().isGroupBreaking()) {
                    renderGroups.add(activeGroup);
                    activeGroup = new ArrayList<>();
                }
            }
            if (!activeGroup.isEmpty()) {
                renderGroups.add(activeGroup);
            }

            // Validate render groups in this bucket.
            renderGroups.forEach(x -> validateRenderGroup(primaryAlignment, x));

            // Prepare all render groups.
            for (final List<Element> renderGroup : renderGroups) {
                final int[] secondaryCursors = defaultSecondaryCursors.clone();
                int maxPrimaryDimension = 0;

                // Prepare width and height first. Also accumulate total width/height and center status.
                int totalWidth = 0;
                int totalHeight = 0;
                boolean isCentered = false;
                for (final Element element : renderGroup) {
                    final RenderProperties properties = element.getRenderProperties();

                    final RequestedSize requestedSize = properties.getRequestedSize();
                    final int effectiveWidth = requestedSize.getWidth().evaluate(paddingClearedWidth);
                    final int effectiveHeight = requestedSize.getHeight().evaluate(paddingClearedHeight);
                    element.setWidth(effectiveWidth);
                    element.setHeight(effectiveHeight);
                    totalWidth += effectiveWidth;
                    totalHeight += effectiveHeight;

                    isCentered = isCentered || properties.isCentered();

                    maxPrimaryDimension = Math.max(maxPrimaryDimension,
                            pickAlignmentDimension(primaryAlignment, effectiveWidth, effectiveHeight));
                }

                // Compute the vertical or horizontal buffer zones and apply them to the secondary cursors, if centering
                // is requested.
                if (isCentered) {
                    final int verticalBuffer = Math.max(0, paddingClearedHeight - totalHeight) / 2;
                    final int horizontalBuffer = Math.max(0, paddingClearedWidth - totalWidth) / 2;
                    for (final Alignment secondaryAlignment : Alignment.values()) {
                        if (!primaryAlignment.isValidSecondaryAlignment(secondaryAlignment)) {
                            continue;
                        }
                        final int idx = secondaryAlignment.getSubAlignmentOffset();
                        secondaryCursors[idx] += secondaryAlignment.getCursorDirection()
                                * pickAlignmentDimension(secondaryAlignment, horizontalBuffer, verticalBuffer);
                    }
                }

                // Position the elements in the render group.
                for (final Element element : renderGroup) {
                    final RenderProperties properties = element.getRenderProperties();
                    final Alignment secondaryAlignment = properties.getSecondaryAlignment();
                    final int cursorIdx = secondaryAlignment.getSubAlignmentOffset();

                    final RequestedSize requestedSize = properties.getRequestedSize();
                    final int effectiveWidth = requestedSize.getWidth().evaluate(paddingClearedWidth);
                    final int effectiveHeight = requestedSize.getHeight().evaluate(paddingClearedHeight);

                    element.setPositionX(pickAlignmentDimension(primaryAlignment, primaryCursor,
                            secondaryCursors[cursorIdx]));
                    element.setPositionY(pickAlignmentDimension(primaryAlignment, secondaryCursors[cursorIdx],
                            primaryCursor));

                    // Advance the relevant secondary cursor.
                    secondaryCursors[cursorIdx] += secondaryAlignment.getCursorDirection()
                            * pickAlignmentDimension(secondaryAlignment, effectiveWidth, effectiveHeight);
                }

                // Advance the primary cursor.
                primaryCursor += primaryAlignment.getCursorDirection() * maxPrimaryDimension;
            }
        }
    }

    /**
     * Picks the correlated dimension for the given alignment.
     *
     * @param alignment  The alignment.
     * @param horizontal The horizontal dimension.
     * @param vertical   The vertical dimension.
     * @return The correlated dimension for the given alignment.
     */
    private int pickAlignmentDimension(final Alignment alignment, final int horizontal, final int vertical) {
        switch (alignment) {
            case TOP:
            case BOTTOM:
                return vertical;
            case RIGHT:
            case LEFT:
                return horizontal;
        }
        throw new IllegalArgumentException("Invalid alignment: " + alignment);
    }

    /**
     * Determines the primary cursor for the given alignment and paddings.
     *
     * @param primaryAlignment The primary alignment to consider.
     * @param paddings         The evaluated paddings to consider.
     * @return The determined primary cursor.
     */
    private int getPrimaryCursor(final Alignment primaryAlignment, final int[] paddings) {
        switch (primaryAlignment) {
            case TOP:
                return positionY + paddings[0];
            case RIGHT:
                return positionX + width - paddings[1];
            case BOTTOM:
                return positionY + height - paddings[2];
            case LEFT:
                return positionX + paddings[3];
        }
        throw new IllegalArgumentException("Invalid alignment: " + primaryAlignment);
    }

    /**
     * Initializes secondary cursors in the given array.
     *
     * @param primaryAlignment The primary alignment to consider.
     * @param paddings         The evaluated paddings to consider.
     * @param secondaryCursors The array in which secondary cursors are initialized.
     */
    private void initializeSecondaryCursors(final Alignment primaryAlignment, final int[] paddings,
                                            final int[] secondaryCursors) {
        switch (primaryAlignment) {
            case TOP:
            case BOTTOM:
                secondaryCursors[Alignment.RIGHT.getSubAlignmentOffset()] = positionX + width - paddings[1];
                secondaryCursors[Alignment.LEFT.getSubAlignmentOffset()] = positionX + paddings[3];
                break;
            case RIGHT:
            case LEFT:
                secondaryCursors[Alignment.TOP.getSubAlignmentOffset()] = positionY + paddings[0];
                secondaryCursors[Alignment.BOTTOM.getSubAlignmentOffset()] = positionY + height - paddings[2];
                break;
        }
    }

    /**
     * Validates a render group.
     *
     * @param primaryAlignment The primary alignment of the group.
     * @param renderGroup      The render group.
     */
    private void validateRenderGroup(final Alignment primaryAlignment, final List<Element> renderGroup) {
        boolean hasCentered = false;
        boolean hasUncentered = false;
        for (final Element element : renderGroup) {
            final RenderProperties properties = element.getRenderProperties();
            hasCentered = hasCentered || properties.isCentered();
            hasUncentered = hasUncentered || !properties.isCentered();

            if (!primaryAlignment.isValidSecondaryAlignment(properties.getSecondaryAlignment())) {
                throw new IllegalStateException("Invalid secondary alignment " + properties.getSecondaryAlignment()
                        + " for primary alignment " + primaryAlignment);
            }
        }

        if (hasCentered && hasUncentered) {
            throw new IllegalStateException("Render group can not contain centered and uncentered" +
                    " elements at the same time!");
        }
    }

    /**
     * A render bucket is an aligned list of elements to be rendered.
     */
    @RequiredArgsConstructor
    private class RenderBucket {

        /**
         * The alignment of the render bucket.
         */
        private @Getter final Alignment alignment;

        /**
         * The elements in the render bucket.
         */
        private @Getter final List<Element> elements;
    }
}
