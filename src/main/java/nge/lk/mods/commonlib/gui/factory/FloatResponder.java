package nge.lk.mods.commonlib.gui.factory;

import net.minecraft.client.gui.GuiPageButtonList;

/**
 * A specialization of {@link GuiPageButtonList.GuiResponder} which only responds to float value
 * changes.
 *
 * @see GuiPageButtonList.GuiResponder
 */
@FunctionalInterface
public interface FloatResponder extends GuiPageButtonList.GuiResponder {

    @Override
    default void setEntryValue(final int id, final boolean value) {
        throw new UnsupportedOperationException("float responder can not accept boolean values");
    }

    @Override
    default void setEntryValue(final int id, final String value) {
        throw new UnsupportedOperationException("float responder can not accept string values");
    }
}
