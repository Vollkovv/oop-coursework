package lab1;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Герой игры. Способ перемещения задаётся стратегией и может меняться во время работы программы.
 */
public class Hero {

    private final String name;
    private Point position;
    private MoveStrategy moveStrategy;
    private double totalHours;
    private final Consumer<String> output;

    public Hero(String name, Point position, MoveStrategy moveStrategy, Consumer<String> output) {
        this.name = Objects.requireNonNull(name);
        this.position = Objects.requireNonNull(position);
        this.moveStrategy = Objects.requireNonNull(moveStrategy);
        this.output = Objects.requireNonNull(output);
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = Objects.requireNonNull(moveStrategy);
        output.accept(String.format("%s теперь перемещается %s.", name, moveStrategy.getName()));
    }

    /** Переместиться в точку target текущим способом. */
    public void move(Point target) {
        Objects.requireNonNull(target);
        totalHours += moveStrategy.move(name, position, target, output);
        position = target;
    }

    public String getName() {
        return name;
    }

    public Point getPosition() {
        return position;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public double getTotalHours() {
        return totalHours;
    }
}
