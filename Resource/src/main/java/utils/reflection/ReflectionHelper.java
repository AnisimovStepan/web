package utils.reflection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class ReflectionHelper {
    private static final Logger logger = LogManager.getLogger(ReflectionHelper.class.getName());
    
    // Create object by reflection instance
    public static Object createInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // Set class field of object
    public static void setFieldValue(Object object, String fieldName, String value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            
            Types types = Types.getType(field.getType());
            switch (types) {
                case BYTE:
                    field.set(object, Byte.valueOf(value));
                    break;
                case BOOLEAN:
                    field.set(object, Boolean.valueOf(value));
                    break;
                case SHORT:
                    field.set(object, Short.valueOf(value));
                    break;
                case CHAR:
                    field.set(object, value.charAt(0));
                    break;
                case INT:
                    field.set(object, Integer.decode(value));
                    break;
                case FLOAT:
                    field.set(object, Float.valueOf(value));
                    break;
                case LONG:
                    field.set(object, Long.valueOf(value));
                    break;
                case DOUBLE:
                    field.set(object, Double.valueOf(value));
                    break;
                case STRING:
                    field.set(object, value);
            }
            
            field.setAccessible(false);
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
