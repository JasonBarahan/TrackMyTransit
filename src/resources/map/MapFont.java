package resources.map;

import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import static java.awt.font.TextAttribute.*;

/**
 * The font used for the visualization use case.
 */
public class MapFont {
    // Denotes attributes for the font used in the visualization use case.
    Map<AttributedCharacterIterator.Attribute, Object> map = Map.of(
            FAMILY, "Sans_Serif",
            SWAP_COLORS, true,
            WEIGHT, 5,
            SIZE, 13
    );

    /**
     * This class isn't supposed to be instantiated directly.
     */
    public MapFont() {}

    public Font getFont() {
        return new Font(map);
    }
}
