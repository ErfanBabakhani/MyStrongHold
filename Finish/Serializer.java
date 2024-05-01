import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Serializer {
    Object tar;

    public String getSerializedString() throws IllegalAccessException {

        String builder = "";

        Class klass = this.tar.getClass();
        ArrayList<Field[]> allFields = new ArrayList<>();
        Field[] fields1 = klass.getDeclaredFields();
        allFields.add(fields1);

        if (klass.getSuperclass() != null && !klass.getSuperclass().getSimpleName().equals("Object")) {
            allFields.add(klass.getSuperclass().getDeclaredFields());
        }
        Field[] fields = sort(mergeFields(allFields));
        int i = 0;
        int lastIndex = fields.length - 1;

        try {
            for (Field field : fields) {

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.getAnnotation(Rename.class) != null) {
                    System.out.println(field.getAnnotation(Rename.class).name());
                    if (field.getAnnotation(Rename.class).name().equals("")) {
                        builder += field.getName() + ":";
                    } else
                        builder += field.getAnnotation(Rename.class).name() + ":";
                } else {
                    builder += field.getName() + ":";
                }
                Type type = field.getType();
                if (type == String.class) {
                    builder += (field.get(tar));

                } else if (type == ArrayList.class) {
                    builder += field.get(tar);
                } else if (type == Integer.TYPE || type == Float.TYPE
                        || type == Long.TYPE || type == Double.TYPE
                ) {

                    builder += (field.get(tar));

                } else if (type == Character.TYPE) {

                    builder += (field.get(tar));


                } else if (type == Boolean.TYPE) {

                    builder += (field.get(tar));


                }
                if (i != lastIndex)
                    builder += ",";
                field.setAccessible(isAccessible);
                i++;
            }
        } catch (Exception e) {

        }
        System.out.println(builder);
        builder = builder.replaceAll(" ", "");
        return builder;
    }

    public Object getObject() {
        return tar;
    }

    public void setObject(Object object) {
        this.tar = object;
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
}