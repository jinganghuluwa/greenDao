package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.green.bean");
        schema.setDefaultJavaPackageDao("com.green.dao");
        initStudent(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }

    private static void initStudent(Schema schema) {
        Entity student = schema.addEntity("Student");
        student.addLongProperty("id").primaryKey().index();
        student.addStringProperty("name");
        student.addIntProperty("age");
        student.addStringProperty("hobby");
    }
}
