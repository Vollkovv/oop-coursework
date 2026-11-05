package lab1;

import java.util.function.Consumer;

/**
 * Пешком: только по дорогам, 5 км/ч.
 */
public class WalkStrategy implements MoveStrategy {

    private static final double SPEED = 5.0;

    @Override
    public String getName() {
        return "пешком";
    }

    @Override
    public double move(String heroName, Point from, Point to, Consumer<String> output) {
        double distance = from.roadDistanceTo(to);
        double time = distance / SPEED;
        output.accept(String.format("%s идёт пешком по дорогам из %s в %s: %.1f км, %.2f ч.",
                heroName, from, to, distance, time));
        return time;
    }
}
