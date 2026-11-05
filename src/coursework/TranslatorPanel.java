package coursework;

import lab3.Dictionary;
import lab3.Translator;
import lab3.FileReadException;
import lab3.InvalidFileFormatException;
import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

final class TranslatorPanel extends LabPanel {
    private final JTextField dictionaryPath = field("lab3DictionaryPath", "examples/dictionary.txt", 37);
    private final JTextField textPath = field("lab3TextPath", "examples/text.txt", 37);
    private final JTextArea input = new JTextArea("dog look to the window, dog look forward", 5, 50);

    TranslatorPanel() {
        super("Переводчик — самый длинный вариант из словаря", "lab3");
        input.setName("lab3Input");
        input.setLineWrap(true);
        input.setWrapStyleWord(true);
        form.add(row(new JLabel("Словарь UTF-8:"), dictionaryPath,
                button("Выбрать…", "lab3DictionaryBrowse", () -> choose(dictionaryPath))));
        form.add(row(new JLabel("Файл текста:"), textPath,
                button("Выбрать…", "lab3TextBrowse", () -> {
                    if (choose(textPath)) loadText();
                }), button("Загрузить", "lab3LoadText", this::loadText)));
        JScrollPane inputScroll = new JScrollPane(input);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Введите текст вручную или загрузите из файла"));
        inputScroll.setAlignmentX(LEFT_ALIGNMENT);
        form.add(inputScroll);
        form.add(row(button("Перевести", "lab3Translate", this::translate),
                new JLabel("Формат словаря: слово или выражение | перевод")));
    }

    private boolean choose(JTextField field) {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            field.setText(chooser.getSelectedFile().getAbsolutePath());
            return true;
        }
        return false;
    }

    private void loadText() {
        try {
            String text = Files.readString(path(textPath), StandardCharsets.UTF_8);
            input.setText(text.startsWith("\uFEFF") ? text.substring(1) : text);
            output.setText("");
            println("Текст загружен. Можно исправить его перед переводом.");
        } catch (IOException | IllegalArgumentException exception) {
            error(exception);
        }
    }

    private void translate() {
        output.setText("");
        try {
            Dictionary dictionary = Dictionary.load(path(dictionaryPath));
            println("Словарь: " + dictionary.size() + " записей.");
            println(new Translator(dictionary).translate(input.getText()));
        } catch (FileReadException | InvalidFileFormatException | IllegalArgumentException exception) {
            error(exception);
        }
    }

    private static Path path(JTextField field) {
        if (field.getText().isBlank()) throw new IllegalArgumentException("Укажите путь к файлу.");
        return Path.of(field.getText().trim());
    }
}
