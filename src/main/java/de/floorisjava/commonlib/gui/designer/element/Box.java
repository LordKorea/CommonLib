package de.floorisjava.commonlib.gui.designer.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import de.floorisjava.commonlib.gui.designer.RenderContext;
import de.floorisjava.commonlib.gui.designer.RenderProperties;
import de.floorisjava.commonlib.gui.designer.util.Alignment;
import de.floorisjava.commonlib.gui.designer.util.Dimension;
import de.floorisjava.commonlib.gui.designer.util.Padding;
import de.floorisjava.commonlib.gui.designer.util.RequestedSize;

/**
 * A box, which can contain further elements.
 */
public class Box extends BaseElement {

    /**
     * The render buckets of this box.
     */
    private final List<RenderBucket> renderBuckets = new ArrayList<>();

    /**
     * The actively edited render bucket.
     */
    private final List<Element> activeBucket = new ArrayList<>();

    /**
     * The padding of this box.
     */
    private final Padding padding;

    /**
     * Creates a relatively spaced vertical spacer which is group breaking.
     *
     * @param height The height of the spacer.
     * @return The created spacer.
     */
    public static Box relativeVerticalSpacer(final int height) {
        return relativeVerticalSpacer(height, Alignment.LEFT);
    }

    /**
     * Creates a relatively spaced vertical spacer which is group breaking.
     *
     * @param height             The height of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box relativeVerticalSpacer(final int height, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .groupBreaking()
                .relativeHeight(height)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a relatively spaced vertical spacer.
     *
     * @param height The height of the spacer.
     * @return The created spacer.
     */
    public static Box relativeVerticalPlaceholder(final int height) {
        return relativeVerticalPlaceholder(height, Alignment.LEFT);
    }

    /**
     * Creates a relatively spaced vertical spacer.
     *
     * @param height             The height of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box relativeVerticalPlaceholder(final int height, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .relativeHeight(height)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a absolutely spaced vertical spacer which is group breaking.
     *
     * @param height The height of the spacer.
     * @return The created spacer.
     */
    public static Box absoluteVerticalSpacer(final int height) {
        return absoluteVerticalSpacer(height, Alignment.LEFT);
    }

    /**
     * Creates a absolutely spaced vertical spacer which is group breaking.
     *
     * @param height             The height of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box absoluteVerticalSpacer(final int height, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .groupBreaking()
                .absoluteHeight(height)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a absolutely spaced vertical spacer.
     *
     * @param height The height of the spacer.
     * @return The created spacer.
     */
    public static Box absoluteVerticalPlaceholder(final int height) {
        return absoluteVerticalPlaceholder(height, Alignment.LEFT);
    }

    /**
     * Creates a absolutely spaced vertical spacer.
     *
     * @param height             The height of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box absoluteVerticalPlaceholder(final int height, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .absoluteHeight(height)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a relatively spaced horizontal spacer which is group breaking.
     *
     * @param width The width of the spacer.
     * @return The created spacer.
     */
    public static Box relativeHorizontalSpacer(final int width) {
        return relativeHorizontalSpacer(width, Alignment.LEFT);
    }

    /**
     * Creates a relatively spaced horizontal spacer which is group breaking.
     *
     * @param width              The width of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box relativeHorizontalSpacer(final int width, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .groupBreaking()
                .relativeWidth(width)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a relatively spaced horizontal spacer.
     *
     * @param width The width of the spacer.
     * @return The created spacer.
     */
    public static Box relativeHorizontalPlaceholder(final int width) {
        return relativeHorizontalPlaceholder(width, Alignment.LEFT);
    }

    /**
     * Creates a relatively spaced horizontal spacer.
     *
     * @param width              The width of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box relativeHorizontalPlaceholder(final int width, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .relativeWidth(width)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a absolutely spaced horizontal spacer which is group breaking.
     *
     * @param width The width of the spacer.
     * @return The created spacer.
     */
    public static Box absoluteHorizontalSpacer(final int width) {
        return absoluteHorizontalSpacer(width, Alignment.LEFT);
    }

    /**
     * Creates a absolutely spaced horizontal spacer which is group breaking.
     *
     * @param width              The width of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box absoluteHorizontalSpacer(final int width, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .groupBreaking()
                .absoluteWidth(width)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Creates a absolutely spaced horizontal spacer.
     *
     * @param width The width of the spacer.
     * @return The created spacer.
     */
    public static Box absoluteHorizontalPlaceholder(final int width) {
        return absoluteHorizontalPlaceholder(width, Alignment.LEFT);
    }

    /**
     * Creates a absolutely spaced horizontal spacer.
     *
     * @param width              The width of the spacer.
     * @param secondaryAlignment The secondary alignment.
     * @return The created spacer.
     */
    public static Box absoluteHorizontalPlaceholder(final int width, final Alignment secondaryAlignment) {
        return new Box(RenderProperties.builder()
                .absoluteWidth(width)
                .secondaryAlignment(secondaryAlignment)
                .build());
    }

    /**
     * Constructor.
     *
     * @param renderProperties The requested render properties for the box.
     */
    public Box(final RenderProperties renderProperties) {
        this(renderProperties, new Padding(
                Dimension.absolute(0),
                Dimension.absolute(0),
                Dimension.absolute(0),
                Dimension.absolute(0)));
    }

    /**
     * Constructor.
     *
     * @param renderProperties The requested render properties for the box.
     * @param padding          The padding for the box.
     */
    public Box(final RenderProperties renderProperties, final Padding padding) {
        super(renderProperties);
        this.padding = padding;
    }

    @Override
    public void prepareRender(final RenderContext ctx) {
        final int[] paddings = new int[]{
                padding.getTop().evaluate(height),
                padding.getRight().evaluate(width),
                padding.getBottom().evaluate(height),
                padding.getLeft().evaluate(width) };
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

                // Prepare width and height first. Also accumulate total width/height
                // and center status.
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

                    // Totals are incorrect if not in the dimension of the secondary
                    // alignment. However, this is not a problem, as totals are only used
                    // in the dimension of the secondary alignment.
                    totalWidth += effectiveWidth;
                    totalHeight += effectiveHeight;

                    isCentered = isCentered || properties.isCentered();

                    final int alignmentDimension = pickAlignmentDimension(primaryAlignment, effectiveWidth,
                            effectiveHeight);
                    maxPrimaryDimension = Math.max(maxPrimaryDimension, alignmentDimension);
                }

                // Compute the vertical or horizontal buffer zones and apply them to the
                // secondary cursors, if centering is requested.
                if (isCentered) {
                    final int verticalBuffer = Math.max(0, paddingClearedHeight - totalHeight) / 2;
                    final int horizontalBuffer = Math.max(0, paddingClearedWidth - totalWidth) / 2;
                    for (final Alignment secondaryAlignment : Alignment.values()) {
                        if (!primaryAlignment.isValidSecondaryAlignment(secondaryAlignment)) {
                            continue;
                        }
                        final int idx = secondaryAlignment.getSubAlignmentOffset();
                        final int alignmentDimension = pickAlignmentDimension(secondaryAlignment, horizontalBuffer,
                                verticalBuffer);
                        secondaryCursors[idx] += secondaryAlignment.getCursorDirection() * alignmentDimension;
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

                    // Process alignment corrections.
                    if (primaryAlignment == Alignment.RIGHT || secondaryAlignment == Alignment.RIGHT) {
                        element.setPositionX(element.getPositionX() - effectiveWidth);
                    }
                    if (primaryAlignment == Alignment.BOTTOM || secondaryAlignment == Alignment.BOTTOM) {
                        element.setPositionY(element.getPositionY() - effectiveHeight);
                    }

                    // The element is positioned, and can now be prepared for rendering
                    // itself.
                    element.prepareRender(ctx);

                    // Advance the relevant secondary cursor.
                    final int alignmentDimension = pickAlignmentDimension(secondaryAlignment, effectiveWidth,
                            effectiveHeight);
                    secondaryCursors[cursorIdx] += secondaryAlignment.getCursorDirection() * alignmentDimension;
                }

                // Advance the primary cursor.
                primaryCursor += primaryAlignment.getCursorDirection() * maxPrimaryDimension;
            }
        }
    }

    /**
     * Adds a render bucket with the specified elements.
     *
     * @param alignment The alignment of the render bucket.
     * @param elements  The elements in the render bucket.
     */
    public void addRenderBucket(final Alignment alignment, final Collection<Element> elements) {
        renderBuckets.add(new RenderBucket(alignment, new ArrayList<>(elements)));
    }

    /**
     * Adds a render bucket with the specified elements.
     *
     * @param alignment The alignment of the render bucket.
     * @param elements  The elements in the render bucket.
     */
    public void addRenderBucket(final Alignment alignment, final Element... elements) {
        addRenderBucket(alignment, Arrays.asList(elements));
    }

    /**
     * Adds an element to the actively edited render bucket.
     *
     * @param element The element.
     */
    public void addToActive(final Element element) {
        activeBucket.add(element);
    }

    /**
     * Commits the actively edited render bucket.
     *
     * @param alignment The alignment to commit the bucket with.
     */
    public void commitBucket(final Alignment alignment) {
        addRenderBucket(alignment, activeBucket);
        activeBucket.clear();
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
     * @param secondaryCursors The array in which secondary cursors are
     *                         initialized.
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
            throw new IllegalStateException("Render group can not contain centered and uncentered"
                                            + " elements at the same time!");
        }
    }

    /**
     * A render bucket is an aligned list of elements to be rendered.
     */
    @RequiredArgsConstructor
    private static class RenderBucket {
        /**
         * The alignment of the render bucket.
         */
        @Getter
        private final Alignment alignment;

        /**
         * The elements in the render bucket.
         */
        @Getter
        private final List<Element> elements;
    }
}
