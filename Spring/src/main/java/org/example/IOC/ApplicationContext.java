package org.example.IOC;

import org.example.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private final Map<Class<?>, Object> container = new HashMap<>();

    public void register(Class<?> componentClass) {
        try {
            Object instance = componentClass.getDeclaredConstructor().newInstance();
            container.put(componentClass, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> T getBean(Class<T> componentClass) {
        return (T) container.get(componentClass);
    }

    public void injectDependencies() throws IllegalAccessException {
        for (Object bean : container.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependency = container.get(field.getType());
                    field.setAccessible(true);
                    field.set(bean, dependency);
                }
            }
        }
    }
}
