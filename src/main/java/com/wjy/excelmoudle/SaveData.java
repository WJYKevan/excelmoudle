package com.wjy.excelmoudle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SaveData {
    public static void save() throws IOException, SQLException {
        //新建读取excel对象并且实例化
        Readexcel xlsMain = new Readexcel();
        //新建实体类对象
        Student student = null;
        //实体类对象组成的集合，实际类型为student的参数化类型保存数据
        List<Student> list = xlsMain.readXls();


        //保存到数据库，有多少个实体，多少个记录循环多少次
        for (int i = 0; i < list.size(); i++) {
            //得到当前实体
            student = list.get(i);
            //新建list对象
            List l = ConnectMysql.selectOne(ConnectMysql.select_student_sql + "'%" + student.getName() + "%'", student);
            if (!l.contains(1)) {
                ConnectMysql.insert(ConnectMysql.insert_student_sql, student);
            } else {
                System.out.println("The Record was Exist : No = " + student.getNo() + " , Name = " + student.getName() + ", Age = " + student.getAge() + ", and has been throw away!");
            }
        }
    }
}
