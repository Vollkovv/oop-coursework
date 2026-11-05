package coursework;

import javax.swing.*;
import java.awt.*;

public class CourseworkPanel extends JPanel {
    public CourseworkPanel() {
        super(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        String[] titles = {"1. Перемещение героя", "2. Аннотированные методы",
                "3. Переводчик", "4. Stream API"};
        JComboBox<String> selector = new JComboBox<>(titles);
        selector.setName("labSelector");
        JPanel navigation = new JPanel(new BorderLayout(12, 0));
        navigation.add(new JLabel("Лабораторная работа:"), BorderLayout.WEST);
        navigation.add(selector, BorderLayout.CENTER);
        add(navigation, BorderLayout.NORTH);

        CardLayout layout = new CardLayout();
        JPanel cards = new JPanel(layout);
        cards.add(new HeroPanel(), titles[0]);
        cards.add(new ReflectionPanel(), titles[1]);
        cards.add(new TranslatorPanel(), titles[2]);
        cards.add(new StreamsPanel(), titles[3]);
        selector.addActionListener(event -> layout.show(cards, (String) selector.getSelectedItem()));
        add(cards, BorderLayout.CENTER);
    }
}
