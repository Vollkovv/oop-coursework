package coursework;

import lab2.AnnotatedMethodInvoker;
import lab2.Worker;
import javax.swing.JLabel;

final class ReflectionPanel extends LabPanel {
    ReflectionPanel() {
        super("Аннотированные защищённые и приватные методы", "lab2");
        form.add(row(new JLabel("Количество вызовов задаётся @Repeat; аргументы создаются по типам параметров.")));
        form.add(row(button("Вызвать методы", "lab2Run", () -> {
            output.setText("");
            new AnnotatedMethodInvoker(this::println).invokeAnnotated(new Worker(this::println));
        })));
    }
}
