package com.wjy.excelmoudle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





public class Readexcel {
    public List<Student> readXls() throws IOException {

        //
        if(!ConnectMysql.excel_path.endsWith(".xls")&&!ConnectMysql.excel_path.endsWith(".xlsx"))
        {
            System.out.println("文件不是excel类型");
        }
        FileInputStream is =null;
        Workbook workbook = null;

        try
        {
            //获取一个绝对地址的流
            is = new FileInputStream(ConnectMysql.excel_path);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            //2003版本的excel，用.xls结尾
            workbook = new HSSFWorkbook(is);//得到工作簿

        }
        catch (Exception ex)
        {
            //ex.printStackTrace();
            try
            {
                //这里需要重新获取流对象，因为前面的异常导致了流的关闭
                is = new FileInputStream(ConnectMysql.excel_path);

                //2007版本的excel，用.xlsx结尾
                workbook = new XSSFWorkbook(ConnectMysql.excel_path);//得到工作簿
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        Student student = null;

        //list接口，有多个实现类，包括了Arraylist（含有list没有的特殊属性）
        // 创建了ArrayList对象，上溯到LIST,成为list对象，对象都由实体类对象组成的集合，方便改动成其他的实现类
        List<Student> list = new ArrayList<Student>();

        //读取excel,遍历excel中所有的sheet
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            //得到工作表对象
             sheet = workbook.getSheetAt(i);
            //去掉空表
            if (sheet == null) {continue; }
            //遍历当前sheet中的所有行,循环初值去掉表头
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {//getLastRowNum返回最后一行的下标
                //读取一行
                row = sheet.getRow(j);
                //去掉空行
                if (row == null) {continue;}
                    student = new Student();
                    Cell no = row.getCell(0);//getcell得到指定行的单元格，数据第一行第一列开始来取
                    Cell name = row.getCell(1);
                    Cell age = row.getCell(2);
                    Cell score = row.getCell(3);
                    student.setNo(getValue(no));//设置实例数据
                    student.setName(getValue(name));
                    student.setAge(getValue(age));
                    student.setScore(Float.valueOf(getValue(score)));
                    list.add(student);//添加到集合

                }
            }
        return list;//返回整个集合
    }

    //定义取值函数，哪种类型就返回哪种类型
    private String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {//?getCellType不推荐的代码

            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {

            return String.valueOf(cell.getNumericCellValue());
        } else {

            return String.valueOf(cell.getStringCellValue());
        }

    }
}
