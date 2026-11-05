package lab1;

import java.util.function.Consumer;

/**
 * Стратегия перемещения героя между двумя точками.
 */
public interface MoveStrategy {

    /** Название способа перемещения для вывода пользователю. */
    String getName();

    /**
     * Переместить героя из точки from в точку to.
     *
     * @return затраченное время в часах
     */
    double move(String heroName, Point from, Point to, Consumer<String> output);
}
