package EightAM.asteroids;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

final class PaintStore {
    private static PaintStore instance;
    private static Paint defaultPaint = new Paint();
    private Map<String, Paint> sPaintMap;

    private PaintStore() {
        sPaintMap = new HashMap<>();
        defaultPaint.setColor(Color.WHITE);
        defaultPaint.setStyle(Paint.Style.FILL);
        defaultPaint.setAntiAlias(true);
        sPaintMap.put("default", defaultPaint);
    }

    static PaintStore getInstance() {
        if (instance == null) instance = new PaintStore();
        return instance;
    }

    Paint getPaint(String name) {
        if (sPaintMap.containsKey(name)) {
            return sPaintMap.get(name);
        }
        return defaultPaint;
    }

    void addPaint(String name, Paint p) {
        Paint paint = new Paint(p);
        sPaintMap.put(name, paint);
    }

    void clearStore() {
        sPaintMap.clear();
    }
}
