package lab1;

/**
 * Точка на карте игры.
 */
public record Point(double x, double y) {

    /** Расстояние по прямой. */
    public double distanceTo(Point other) {
        return Math.hypot(other.x - x, other.y - y);
    }

    /** Расстояние по дорогам (сетка улиц): сумма смещений по осям. */
    public double roadDistanceTo(Point other) {
        return Math.abs(other.x - x) + Math.abs(other.y - y);
    }

    @Override
    public String toString() {
        return String.format("(%.1f; %.1f)", x, y);
    }
}
