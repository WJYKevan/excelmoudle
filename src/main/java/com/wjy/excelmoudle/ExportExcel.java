package com.wjy.excelmoudle;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileOutputStream;
import java.sql.*;

import static com.wjy.excelmoudle.ConnectMysql.*;

public class ExportExcel {

    private static String outPutFile = "/revert/user.xls";
    public  void exportToExcel() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //连接数据库查询数据
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            ps = conn.prepareStatement(select_student_all_sql);
            //获取结果集
            rs = ps.executeQuery();
            //用于获取字段的描述信息,含有返回字段数，表名，字段名的方法
            ResultSetMetaData metaData = rs.getMetaData();
            //创建workBook对象
            HSSFWorkbook workBook = new HSSFWorkbook();
            //在workBook对象中创建一张表格
            HSSFSheet sheet = workBook.createSheet("专家表");
            //单元格样式对象
            HSSFCellStyle cellStyle = workBook.createCellStyle();
            //设置文本居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            //创建第0行,作为表格的表头
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = null;
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                cell = row.createCell(i);
                //动态获取字段名
                cell.setCellValue(metaData.getColumnName(i + 1));
                cell.setCellStyle(cellStyle);
            }
            int rowIndex = 1;
            while (rs.next()) {
                //循环将查询出来的数据封装到表格的一行中
                row = sheet.createRow(rowIndex);
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(rs.getString(i + 1));
                    cell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
            FileOutputStream os = new FileOutputStream(outPutFile);
            //输出流将文件写到硬盘
            workBook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            ps.close();
            conn.close();
        }

    }
}
