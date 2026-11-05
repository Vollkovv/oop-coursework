package coursework;

import javax.swing.*;
import java.awt.Dimension;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Курсовая работа — Объектно-ориентированное программирование");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new CourseworkPanel());
            frame.setMinimumSize(new Dimension(820, 620));
            frame.setSize(1000, 760);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
