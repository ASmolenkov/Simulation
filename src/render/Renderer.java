package render;

import world.Coordinate;
import world.Entity;
import world.MapWorld;

import java.util.Map;

public interface Renderer {
    void render(MapWorld mapWorld);
}
