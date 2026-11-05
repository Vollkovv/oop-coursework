package coursework;

import lab1.*;
import javax.swing.*;

final class HeroPanel extends LabPanel {
    private final JTextField name = field("lab1Name", "Герой", 12);
    private final JTextField startX = field("lab1StartX", "0", 5);
    private final JTextField startY = field("lab1StartY", "0", 5);
    private final JTextField targetX = field("lab1TargetX", "10", 5);
    private final JTextField targetY = field("lab1TargetY", "5", 5);
    private final JComboBox<String> strategy = new JComboBox<>(new String[]{"Пешком", "На лошади", "По воздуху"});
    private final MoveStrategy[] strategies = {new WalkStrategy(), new HorseStrategy(), new FlyStrategy()};
    private Hero hero;

    HeroPanel() {
        super("Перемещение героя — смена стратегии во время работы", "lab1");
        strategy.setName("lab1Strategy");
        form.add(row(new JLabel("Имя:"), name, new JLabel("Начальная точка X:"), startX,
                new JLabel("Y:"), startY, button("Создать героя", "lab1Create", this::create)));
        form.add(row(new JLabel("Способ:"), strategy, new JLabel("Цель X:"), targetX,
                new JLabel("Y:"), targetY, button("Переместиться", "lab1Move", this::move)));
        form.add(row(new JLabel("Координаты в км. Пешком и на лошади — по дорогам, полёт — по прямой.")));
        create();
    }

    private void create() {
        try {
            if (name.getText().isBlank()) throw new IllegalArgumentException("Введите имя героя.");
            Point start = new Point(coordinate(startX), coordinate(startY));
            hero = new Hero(name.getText().trim(), start, strategies[strategy.getSelectedIndex()], this::println);
            output.setText("");
            state();
        } catch (IllegalArgumentException exception) {
            error(exception);
        }
    }

    private void move() {
        try {
            Point target = new Point(coordinate(targetX), coordinate(targetY));
            if (hero == null) throw new IllegalArgumentException("Сначала создайте героя.");
            MoveStrategy chosen = strategies[strategy.getSelectedIndex()];
            if (chosen != hero.getMoveStrategy()) hero.setMoveStrategy(chosen);
            hero.move(target);
            state();
        } catch (IllegalArgumentException exception) {
            error(exception);
        }
    }

    private static double coordinate(JTextField field) {
        try {
            double value = Double.parseDouble(field.getText().trim().replace(',', '.'));
            if (!Double.isFinite(value)) throw new NumberFormatException();
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Координаты должны быть конечными числами.");
        }
    }

    private void state() {
        println(String.format("%s в точке %s, перемещается %s, всего в пути: %.2f ч.",
                hero.getName(), hero.getPosition(), hero.getMoveStrategy().getName(), hero.getTotalHours()));
    }
}
