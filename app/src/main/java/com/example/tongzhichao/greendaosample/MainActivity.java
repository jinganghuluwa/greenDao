package com.example.tongzhichao.greendaosample;

import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.green.bean.Student;
import com.green.dao.DaoMaster;
import com.green.dao.DaoSession;
import com.green.dao.StudentDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText age;
    private EditText hoby;
    private Button add;
    private Button delete;
    private Button search;
    private Button change;
    private Button list;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        hoby = (EditText) findViewById(R.id.hoby);
        add = (Button) findViewById(R.id.add);
        delete = (Button) findViewById(R.id.delete);
        search = (Button) findViewById(R.id.search);
        change = (Button) findViewById(R.id.changed);
        list = (Button) findViewById(R.id.list);
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        search.setOnClickListener(this);
        change.setOnClickListener(this);
        list.setOnClickListener(this);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                Student student = new Student();
                try {
                    student.setId(Long.parseLong(age.getText().toString()));
                } catch (NumberFormatException e) {

                }
                student.setName(name.getText().toString());
                student.setHobby(hoby.getText().toString());
                daoSession.getStudentDao().insert(student);
                break;
            case R.id.delete:
                Student s = new Student();
                s.setName(name.getText().toString());
                daoSession.getStudentDao().queryBuilder().where(
                        StudentDao.Properties.Name.eq(name.getText().toString())).buildDelete().
                        executeDeleteWithoutDetachingEntities();
                break;
            case R.id.search:
                List<Student> list = daoSession.getStudentDao().queryBuilder().where(
                        StudentDao.Properties.Name.eq(name.getText().toString())
                ).list();
                for (int i = 0; i < list.size(); i++) {
                    Log.e("student", "id:" + list.get(i).getId() + ",name:" + list.get(i).getName()
                            + ",age:" + list.get(i).getAge());
                }
                break;
            case R.id.changed:
                Student student1 = new Student();
                student1.setName(name.getText().toString());
                student1.setId(2L);
                try {
                    student1.setAge(Integer.parseInt(age.getText().toString()));
                } catch (NumberFormatException e) {

                }
                break;
            case R.id.list:
                List<Student> lists = daoSession.getStudentDao().loadAll();
                for (int i = 0; i < lists.size(); i++) {
                    Log.e("student", "id:" + lists.get(i).getId() + ",name:" + lists.get(i).getName()
                            + ",age:" + lists.get(i).getAge());
                }
                break;
        }
    }
}
