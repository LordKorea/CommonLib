package de.floorisjava.commonlib.gui.designer.element;

import de.floorisjava.commonlib.gui.designer.RenderProperties;
import de.floorisjava.commonlib.gui.designer.util.Alignment;
import de.floorisjava.commonlib.gui.designer.util.Dimension;
import de.floorisjava.commonlib.gui.designer.util.RequestedSize;

/**
 * The root element box.
 */
public class RootBox extends Box {

    /**
     * Constructor.
     */
    public RootBox() {
        super(new RenderProperties(false, false,
                new RequestedSize(Dimension.relative(100), Dimension.relative(100)), Alignment.TOP));
    }

    @Override
    public void setPositionX(final int positionX) {
        throw new UnsupportedOperationException("can't change root box position");
    }

    @Override
    public void setPositionY(final int positionY) {
        throw new UnsupportedOperationException("can't change root box position");
    }
}
