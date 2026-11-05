package coursework;

import javax.swing.*;
import java.awt.*;

/** Общие элементы четырёх экранов: форма и нередактируемый журнал. */
abstract class LabPanel extends JPanel {
    protected final JTextArea output = new JTextArea(12, 50);
    protected final JPanel form = new JPanel();

    LabPanel(String title, String prefix) {
        super(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder(title));
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        add(form, BorderLayout.NORTH);
        output.setName(prefix + "Output");
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(BorderFactory.createTitledBorder("Результат"));
        add(scroll, BorderLayout.CENTER);
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(button("Очистить результат", prefix + "Clear", () -> output.setText("")));
        add(footer, BorderLayout.SOUTH);
    }

    protected void println(String message) {
        output.append(message + System.lineSeparator());
        output.setCaretPosition(output.getDocument().getLength());
    }

    protected void error(Exception exception) {
        println("Ошибка: " + exception.getMessage());
    }

    protected static JButton button(String title, String name, Runnable action) {
        JButton button = new JButton(title);
        button.setName(name);
        button.addActionListener(event -> action.run());
        return button;
    }

    protected static JTextField field(String name, String initial, int columns) {
        JTextField field = new JTextField(initial, columns);
        field.setName(name);
        return field;
    }

    protected static JPanel row(Component... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        for (Component component : components) panel.add(component);
        panel.setAlignmentX(LEFT_ALIGNMENT);
        return panel;
    }
}
