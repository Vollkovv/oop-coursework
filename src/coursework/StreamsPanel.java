package coursework;

import lab4.StreamMethods;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

final class StreamsPanel extends LabPanel {
    private final JComboBox<String> operation = new JComboBox<>(new String[]{
            "1. Среднее целых чисел", "2. Верхний регистр и префикс _new_",
            "3. Квадраты чисел, встречающихся один раз", "4. Последний элемент коллекции",
            "5. Сумма чётных чисел", "6. Map: первый символ → остаток строки"});
    private final JTextArea input = new JTextArea("4 8 15 16 23 42 8 4", 5, 50);
    private final JLabel hint = new JLabel();

    StreamsPanel() {
        super("Шесть операций Stream API", "lab4");
        operation.setName("lab4Operation");
        input.setName("lab4Input");
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        form.add(row(new JLabel("Метод:"), operation));
        form.add(row(hint));
        JScrollPane scroll = new JScrollPane(input);
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        form.add(scroll);
        form.add(row(button("Выполнить", "lab4Run", this::run)));
        operation.addActionListener(event -> updateHint());
        updateHint();
    }

    private void updateHint() {
        int index = operation.getSelectedIndex();
        hint.setText(index == 0 || index == 2 || index == 4
                ? "Целые числа через пробел, запятую, точку с запятой или новую строку."
                : "Каждая строка — отдельный элемент. Для Map при повторе ключа остаётся первая строка.");
    }

    private void run() {
        output.setText("");
        try {
            Object result = switch (operation.getSelectedIndex()) {
                case 0 -> StreamMethods.average(numbers());
                case 1 -> StreamMethods.toUpperCaseWithPrefix(strings());
                case 2 -> StreamMethods.squaresOfUnique(numbers());
                case 3 -> StreamMethods.last(strings());
                case 4 -> StreamMethods.sumOfEven(numbers().stream().mapToInt(Integer::intValue).toArray());
                case 5 -> StreamMethods.toFirstCharMap(strings());
                default -> throw new IllegalStateException("Не выбран метод.");
            };
            println(String.valueOf(result));
        } catch (NumberFormatException exception) {
            println("Ошибка: нужны целые числа от -2147483648 до 2147483647.");
        } catch (IllegalArgumentException | java.util.NoSuchElementException exception) {
            error(exception);
        }
    }

    private List<Integer> numbers() {
        if (input.getText().isBlank()) return List.of();
        return Arrays.stream(input.getText().trim().split("[,;\\s]+"))
                .map(Integer::parseInt).toList();
    }

    private List<String> strings() {
        return input.getText().isEmpty() ? List.of() : input.getText().lines().toList();
    }
}
