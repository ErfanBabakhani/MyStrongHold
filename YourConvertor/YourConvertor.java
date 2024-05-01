import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.reflect.Modifier.isStatic;

public class YourConvertor {
    public static ArrayList finalValue = new ArrayList();

    public Object deserialize(String input, String className) {

        //TODO Implement this method
        Class klass = null;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
        }
        try {

            Field[] fields1 = klass.getDeclaredFields();
            setAllFieldAccess(fields1);
            Constructor constructor = klass.getConstructor();
            Object obj = constructor.newInstance();

            String stdIn = input.substring(1, input.length() - 1);

            stdIn = stdIn.replaceAll("\\{", " {");
            stdIn = stdIn.replaceAll("}", "} ");

            ArrayList<String> fields = new ArrayList<>();
            ArrayList<String> value = new ArrayList<>();

            ArrayList<String> innerValue = findInnerValue(stdIn);
            ArrayList<String> innerFields = new ArrayList<>();

            stdIn = deleteInnerValue(stdIn, innerValue);


            stdIn = stdIn.replaceAll(" ", "");

            String[] splitByComma = stdIn.split(",\"");


            for (int i = 0; i < splitByComma.length; i++) {
                String[] keyAndValue = splitByComma[i].split("\":");
                if (keyAndValue.length == 1) {
                    innerFields.add(keyAndValue[0]);
                } else {
                    if (keyAndValue[1] == null || keyAndValue[1].equals("")) {
                        innerFields.add(keyAndValue[0]);
                    } else {
                        fields.add(keyAndValue[0].replaceAll("\"", ""));
                        value.add(keyAndValue[1].replaceAll("\"", ""));
                    }
                }
            }
            setTheSimpleFields(fields, value, obj, klass);
            setTheInnerField(innerFields, innerValue, obj, klass, className);
            return obj;
        } catch (Exception e) {

        }

        return null;
    }


    private void setTheInnerField(ArrayList<String> key, ArrayList<String> value, Object ob, Class klass, String classFullName) throws NoSuchFieldException, IllegalAccessException {

        for (int i = 0; i < key.size(); i++) {

            Object tar = null;
            try {
                String className = klass.getDeclaredField(key.get(i).replaceAll("\"", "")).getType().getSimpleName();

                tar = this.deserialize(value.get(i), classFullName + "$" + className);


            } catch (NoSuchFieldException e) {

                throw new RuntimeException(e);
            }

            Field field = klass.getDeclaredField(key.get(i).replaceAll("\"", ""));
            field.setAccessible(true);
            field.set(ob, tar);

        }
    }

    private void setTheSimpleFields(ArrayList<String> key, ArrayList<String> value, Object ob, Class klass) {

        for (int i = 0; i < key.size(); i++) {
            try {


                if (isArray(value.get(i))) {

                    Field field = null;
                    try {
                        field = klass.getDeclaredField(key.get(i));
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    field.setAccessible(true);
                    try {
                        setTheArr(value.get(i), key.get(i), ob, klass);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                } else if (isNumber(value.get(i)) == 0) {


                    Field field = null;
                    try {
                        field = klass.getDeclaredField(key.get(i));
                    } catch (NoSuchFieldException e) {
                        try {
                            field = klass.getSuperclass().getDeclaredField(key.get(i));
                        } catch (NoSuchFieldException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    field.setAccessible(true);
                    try {
                        if (value.get(i).length() == 1) {
                            field.set(ob, value.get(i).charAt(0));
                        } else if (hasSetter(field.getName(), allSetterMethode(klass.getMethods())) != null) {

                            Method method = hasSetter(field.getName(), allSetterMethode(klass.getDeclaredMethods()));

                            method.invoke(ob, value.get(i));

                        } else {
                            field.set(ob, value.get(i));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    if (isNumber(value.get(i)) == 1) {

                        Field field = null;
                        try {
                            field = klass.getDeclaredField(key.get(i));
                        } catch (NoSuchFieldException e) {
                            try {
                                field = klass.getSuperclass().getDeclaredField(key.get(i));
                            } catch (NoSuchFieldException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        field.setAccessible(true);
                        try {
                            field.set(ob, Integer.parseInt(value.get(i)));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }


                    } else {

                        Field field = null;
                        try {
                            field = klass.getDeclaredField(key.get(i));
                        } catch (NoSuchFieldException e) {
                            throw new RuntimeException(e);
                        }
                        field.setAccessible(true);
                        try {
                            field.set(ob, Float.parseFloat(value.get(i)));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                        finalValue.add(value.get(i));
                    }
                }
            } catch (Exception e) {

            }


        }

    }

    public void setAllFieldAccess(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
        }
    }

    private int isNumber(String value) {
        int num = 0;
        try {
            Integer.parseInt(value);
            return 1;
        } catch (Exception e) {

        }
        try {
            Float.parseFloat(value);
            return 2;
        } catch (Exception e) {

        }
        return 0;
    }

    private boolean isArray(String value) {
        if (value.contains("[") && value.contains("]"))
            return true;
        return false;
    }

    private void setTheArr(String value, String key, Object ob, Class klass) throws NoSuchFieldException, IllegalAccessException {

        value = value.substring(1, value.length() - 1);

        String[] split = value.split(",");
        if (isNumber(split[0]) == 0) {
            ArrayList<String> out = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                out.add(split[i]);
            }
            Field field = klass.getDeclaredField(key);
            field.setAccessible(true);
            field.set(ob, out);

        } else if (isNumber(split[0]) == 1) {
            ArrayList<Integer> out = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                out.add(Integer.parseInt(split[i]));
            }

            Field field = klass.getDeclaredField(key);
            field.setAccessible(true);
            field.set(ob, out);
        } else {
            ArrayList<Float> out = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                out.add(Float.parseFloat(split[i]));
            }

            Field field = klass.getDeclaredField(key);
            field.setAccessible(true);
            field.set(ob, out);
        }
    }


    private ArrayList<String> findInnerValue(String input) {
        Pattern pattern = Pattern.compile("(?<innerVal>\\{\"\\S+})");
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> out = new ArrayList<>();
        while (matcher.find()) {
            out.add(matcher.group("innerVal"));
        }
        return out;
    }

    private String deleteInnerValue(String in, ArrayList<String> inner) {
        for (int i = 0; i < inner.size(); i++) {
            in = in.replace(inner.get(i), "");
        }
        return in;
    }

    public String serialize(Object input) {

        String builder;
        builder = ("{");
        Class klass = input.getClass();
        ArrayList<Field[]> allFields = new ArrayList<>();
        Field[] fields1 = klass.getDeclaredFields();
        allFields.add(fields1);

        if (klass.getSuperclass() != null && !klass.getSuperclass().getSimpleName().equals("Object")) {
            allFields.add(klass.getSuperclass().getDeclaredFields());
        }
        Field[] fields = sort(mergeFields(allFields));
        int i = 0;
        int lastIndex = fields.length - 1;
        Method[] allGetters = allGetterMethode(klass.getDeclaredMethods());

        try {
            for (Field field : fields) {


                if (isStatic(field.getModifiers())) {
                    continue;
                }

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                builder += "\"" + field.getName() + "\":";
                Type type = field.getType();
                if (type == String.class) {
                    if (hasGetter(field.getName(), allGetters) != null) {
                        builder += ("\"") + hasGetter(field.getName(), allGetters).invoke(input) + ("\"");
                    } else {
                        builder += ("\"") + (field.get(input)) + ("\"");
                    }
                } else if (type == ArrayList.class) {
                    builder += field.get(input);
                } else if (type == Integer.TYPE || type == Float.TYPE
                        || type == Long.TYPE || type == Double.TYPE
                ) {
                    if (hasGetter(field.getName(), allGetters) != null) {
                        builder += hasGetter(field.getName(), allGetters).invoke(input);
                    } else {
                        builder += (field.get(input));
                    }
                } else if (type == Character.TYPE) {
                    if (hasGetter(field.getName(), allGetters) != null) {
                        builder += "\"" + hasGetter(field.getName(), allGetters).invoke(input) + "\"";
                    } else {
                        builder += "\"" + (field.get(input)) + "\"";
                    }

                } else if (type == Boolean.TYPE) {
                    if (hasGetter(field.getName(), allGetters) != null) {
                        builder += hasGetter(field.getName(), allGetters).invoke(input);
                    } else {
                        builder += (field.get(input));
                    }

                } else {
                    builder += this.serialize(field.get(input));
                }
                if (i != lastIndex)
                    builder += ",";
                field.setAccessible(isAccessible);
                i++;
            }
        } catch (Exception e) {

        }
        builder += "}";
        builder = builder.replaceAll(" ", "");
        return builder;
    }

    private Field[] sort(Field[] tar) {

        for (int i = 0; i < tar.length - 1; i++) {
            for (int j = i + 1; j < tar.length; j++) {
                if (tar[j].getName().compareTo(tar[i].getName()) < 0) {
                    Field temp = tar[j];
                    tar[j] = tar[i];
                    tar[i] = temp;
                }
            }
        }
        return tar;
    }

    public Field[] mergeFields(ArrayList<Field[]> tar) {
        int size = 0;
        for (int i = 0; i < tar.size(); i++) {
            size += tar.get(i).length;
        }
        Field[] result = new Field[size];
        System.arraycopy(tar.get(0), 0, result, 0, tar.get(0).length);
        for (int i = 1; i < tar.size(); i++) {
            System.arraycopy(tar.get(i), 0, result, tar.get(i - 1).length, tar.get(i).length);
        }
        return result;
    }

    private Method[] allGetterMethode(Method[] methods) {
        int size = 0;
        for (int i = 0; i < methods.length; i++) {
            if (isGetter(methods[i]))
                size++;
        }
        Method[] methods1 = new Method[size];
        int index = 0;
        for (int i = 0; i < methods.length; i++) {
            if (isGetter(methods[i])) {
                methods1[index] = methods[i];
                index++;
            }
        }
        return methods1;
    }

    private Method[] allSetterMethode(Method[] methods) {
        int size = 0;
        for (int i = 0; i < methods.length; i++) {
            if (isSetter(methods[i]))
                size++;
        }
        Method[] methods1 = new Method[size];
        int index = 0;
        for (int i = 0; i < methods.length; i++) {
            if (isSetter(methods[i])) {
                methods1[index] = methods[i];
                index++;
            }
        }
        return methods1;
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) return false;
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) return false;
        if (method.getParameterTypes().length != 1) return false;
        return true;
    }

    public Method hasGetter(String fieldName, Method[] allMethode) {
        fieldName = fieldName.toUpperCase();
        for (int i = 0; i < allMethode.length; i++) {
            if (allMethode[i].getName().toUpperCase().contains(fieldName))
                return allMethode[i];
        }
        return null;
    }

    public Method hasSetter(String fieldName, Method[] allMethode) {
        fieldName = fieldName.toUpperCase();
        for (int i = 0; i < allMethode.length; i++) {
            if (allMethode[i].getName().toUpperCase().contains(fieldName)) {

                return allMethode[i];
            }
        }
        return null;
    }

}

