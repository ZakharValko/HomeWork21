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
                if(field.getType() != String.class && field.getType() != Character.class) { // Проверяем тип поля и исключаем String и Character
                    Number currentValue = (Number) field.get(myMath); // Текущее значение поля
                    Number valueToSet = (double) currentValue * (double) currentValue; // Получаем новое значение
                    field.set(myMath, valueToSet); // Устанавливаем значение
                    System.out.println(field.get(myMath));
                }
            }
            field.setAccessible(false); // Закрываем редактирование полей
        }
    }
}
