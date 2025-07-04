package test;

import java.lang.reflect.Field;

public class TestUtils {
    private TestUtils() {
        throw new AssertionError("Создание экземпляров - запрещено!");
    }

    public static void resetAutoIncrementCounters(Class<?>... classes) throws Exception {
        for (Class<?> clazz : classes) {
            try {
                Field field = clazz.getDeclaredField("lastId");
                field.setAccessible(true);
                field.set(null, 0);
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException("Class " + clazz.getSimpleName() + " doesn't have lastId field", e);
            }
        }
    }
}
