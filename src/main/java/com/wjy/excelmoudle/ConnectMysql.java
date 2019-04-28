package com.wjy.excelmoudle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectMysql {


    //设置Mysql参数
    public static final String user = "root";
    public static final String password = "root@123456/";
    public static final String url = "jdbc:mysql://129.204.64.249:3306/excel";

    //？捕获连接异常信息

    //设置excel文件路径
    public static final String excel_path = "/revert/student1_info.xlsx";

    //设置数据库操作语句
    public static final String insert_student_sql = "insert into student_info(no,name,age,score)"
            + "value(?,?,?,?)";
    public static final String update_student_sql = "update student_info"
            + "set no = ?, name = ?, age= ?, score = ? where id = ? ";
    public static final String select_student_all_sql = "select id,no,name,age,score from student_info";
    public static final String select_student_sql = "select * from student_info where name like ";


    //设置插入数据方法
    public static void insert(String sql, Student student) throws SQLException {
        //Connection接口里面有PreparedStatement的对象用来发送sql语句
        Connection conn = null;
        //动态处理SQL语句，Statement的子类，有执行，查询，更新方法
        PreparedStatement ps = null;
        try {
            //连接数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection(ConnectMysql.url, ConnectMysql.user, ConnectMysql.password);
            //发送SQL语句
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getNo());
            ps.setString(2, student.getName());
            ps.setString(3, student.getAge());
            ps.setString(4, String.valueOf(student.getScore()));
            boolean flag = ps.execute();
            if(!flag){
                System.out.println("Save data : No. = " + student.getNo() + "," +
                        "Name = " + student.getName() + "," +
                        " Age = " + student.getAge() + " succeed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            //1.conn在close之前判断conn!=null更多的是出错保护的需要，
            // 防止初始化连接的时候已经出错导制conn未被赋值的情况。
            //2.conn不close直接置null只能释放内存，不会释放连接，会造成连接资源泄漏。
            //3.conn在close之后再设置 null是一个好习惯，可以避免已经关闭的连接再次被误用，
            // 还有就是一个连接被关闭两次的情况。

            if (conn != null) {
                conn.close();
            }
        }
    }

    //设查询数据方法
    public static List selectOne(String sql, Student student) throws SQLException {
        //接口对象，接口能创建对象但是不能实例化
        Connection conn = null;
        //类对象
        PreparedStatement ps = null;
        //ResultSet接口对象用来接受所有查询内容
        ResultSet rs = null;
        //list接口对象通过ArrayList实现类来实例化
        List list = new ArrayList();
        try {
            //连接数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection(url, user, password);
            //发送SQL语句
            ps = conn.prepareStatement(sql);
            //接受查询的记录
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("no").equals(student.getNo()) || rs.getString("name").equals(student.getName())|| rs.getString("age").equals(student.getAge())){
                    list.add(1);
                }else{
                    list.add(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }


    public static ResultSet selectAll(String sql) throws SQLException {
        //新建连接对象
        Connection conn = null;
        //新建PreparedStatement对象
        PreparedStatement ps = null;
        //新建接受数据的对象
        ResultSet rs = null;
        try {
            //连接数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接
            conn = DriverManager.getConnection(url, user, password);
            //发送SQL语句
            ps = conn.prepareStatement(sql);
            //接受数据
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return rs;
    }
}
