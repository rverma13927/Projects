package org.example.IOC;

import org.example.annotation.Autowired;
import org.example.annotation.PostConstruct;
import org.example.annotation.PreDestroy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public void injectDependencies() {
        for (Object bean : container.values()) {
            injectFields(bean);
            invokePostConstruct(bean);
            }
    }

    public void destroy() {
        for (Object bean : container.values()) {
            invokePreDestroy(bean);
        }
    }

    private void injectFields(Object bean) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = container.get(field.getType());
                //Cases where the dependency is not found in the container, throwing an informative exception if the dependency is missing.
                if (dependency == null)
                    throw new RuntimeException("No bean found for type: " + field.getType().getName());

                field.setAccessible(true);
                try {
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject dependency into field: " + field.getName() + " of bean: " + bean.getClass().getName(), e);
                }
            }
        }
    }

    private void invokePostConstruct(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.setAccessible(true);
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to invoke @PostConstruct method on bean: " + bean.getClass().getName(), e);
                }
            }
        }
    }

    private void invokePreDestroy(Object bean) {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(PreDestroy.class)) {
                method.setAccessible(true);
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to invoke @PreDestroy method on bean: " + bean.getClass().getName(), e);
                }
            }
        }
    }
}
