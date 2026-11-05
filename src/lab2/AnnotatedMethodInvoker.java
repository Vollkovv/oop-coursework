package lab2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Вызывает у объекта все protected и private методы, помеченные {@link Repeat},
 * столько раз, сколько указано в аннотации.
 */
public class AnnotatedMethodInvoker {

    private final ArgumentFactory argumentFactory = new ArgumentFactory();
    private final Consumer<String> output;

    public AnnotatedMethodInvoker(Consumer<String> output) {
        this.output = output;
    }

    public void invokeAnnotated(Object target) {
        Method[] methods = target.getClass().getDeclaredMethods();
        // getDeclaredMethods не гарантирует порядок — сортируем, чтобы вывод был стабильным
        Arrays.sort(methods, Comparator.comparing(Method::getName));

        for (Method method : methods) {
            Repeat repeat = method.getAnnotation(Repeat.class);
            if (repeat == null || !isProtectedOrPrivate(method)) {
                continue;
            }
            if (repeat.value() < 0) {
                output.accept(method.getName() + ": отрицательное число повторов, пропускаю");
                continue;
            }

            Object[] args;
            try {
                args = argumentFactory.argumentsFor(method);
            } catch (IllegalArgumentException e) {
                output.accept(method.getName() + ": не удалось подготовить аргументы — " + e.getMessage());
                continue;
            }
            method.setAccessible(true); // доступ к private и protected из другого класса

            output.accept(String.format("%s %s%s — вызов %d раз(а)",
                    Modifier.toString(method.getModifiers()),
                    method.getName(),
                    Arrays.toString(args),
                    repeat.value()));

            for (int i = 0; i < repeat.value(); i++) {
                invoke(target, method, args);
            }
        }
    }

    private static boolean isProtectedOrPrivate(Method method) {
        int modifiers = method.getModifiers();
        return Modifier.isProtected(modifiers) || Modifier.isPrivate(modifiers);
    }

    private void invoke(Object target, Method method, Object[] args) {
        try {
            method.invoke(target, args);
        } catch (InvocationTargetException e) {
            // Исключение, выброшенное самим вызванным методом
            output.accept("  метод " + method.getName() + " выбросил исключение: " + e.getCause());
        } catch (IllegalAccessException e) {
            output.accept("  нет доступа к методу " + method.getName() + ": " + e.getMessage());
        }
    }
}
