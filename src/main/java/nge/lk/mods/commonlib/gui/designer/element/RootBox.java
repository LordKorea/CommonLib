package nge.lk.mods.commonlib.gui.designer.element;

import nge.lk.mods.commonlib.gui.designer.util.Dimension;
import nge.lk.mods.commonlib.gui.designer.util.RequestedSize;

/**
 * The root element box.
 */
public class RootBox extends Box {

    /**
     * Constructor.
     */
    public RootBox() {
        super(null, new RequestedSize(Dimension.relative(100), Dimension.relative(100)));
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
