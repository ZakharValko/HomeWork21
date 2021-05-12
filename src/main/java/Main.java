import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        MyMath myMath = new MyMath();


        try {
            changeValues(myMath);
        } catch (IllegalAccessException e) {
            System.out.println("Access to private fields is illegal!!!");
        }

    }

    public static void changeValues(MyMath myMath) throws IllegalAccessException {
        // Создаем массив с полями объекта класса MyMath
        Field[] fields = myMath.getClass().getDeclaredFields();

        for(Field field: fields){
            field.setAccessible(true); // Открываем редактирование полей
            if(field.isAnnotationPresent(Pow.class)){ // Проверяем есть ли у поля аннотация
                Object value = field.get(myMath);
                if(value instanceof Number) { // Проверяем тип поля и исключаем String и Character
                    Number currentValue = (Number) field.get(myMath); // Текущее значение поля

                    // Определяем к какому типу кастить и получаем значение которое необходимо установить
                    Number valueToSet;
                    String type = field.getType().getName();
                    switch (type){
                        case "java.lang.Byte": valueToSet = (byte) currentValue * (byte) currentValue;
                            break;
                        case "java.lang.Short": valueToSet = (short) currentValue * (short) currentValue;
                            break;
                        case "java.lang.Integer": valueToSet = (int) currentValue * (int) currentValue;
                            break;
                        case "java.lang.Double":  valueToSet = (double) currentValue * (double) currentValue;
                            break;
                        case "java.lang.Float": valueToSet = (float) currentValue * (float) currentValue;
                            break;
                        case "java.lang.Long": valueToSet = (long) currentValue * (long) currentValue;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + type);
                    }

                    field.set(myMath, valueToSet); // Устанавливаем значение
                    System.out.println(field.get(myMath));
                }
            }
            field.setAccessible(false); // Закрываем редактирование полей
        }
    }
}
