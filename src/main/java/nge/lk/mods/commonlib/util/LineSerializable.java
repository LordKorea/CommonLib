package nge.lk.mods.commonlib.util;

import java.util.Iterator;

/**
 * Represents something that can be serialized and deserialized in a single line.
 */
public interface LineSerializable {

    /**
     * Converts a LineSerializable producer to a string producer.
     *
     * @param producer The LineSerializable producer.
     *
     * @return The converted string producer.
     */
    static Iterator<String> toStringProducer(final Iterator<? extends LineSerializable> producer) {
        return new Iterator<String>() {

            @Override
            public boolean hasNext() {
                return producer.hasNext();
            }

            @Override
            public String next() {
                return producer.next().serialize();
            }
        };
    }

    /**
     * Use the information from the line to populate the instance state.
     *
     * @param line The line containing the information.
     */
    void deserialize(String line);

    /**
     * Encode the instance state into a line.
     *
     * @return The line containing the instance data.
     */
    String serialize();
}
