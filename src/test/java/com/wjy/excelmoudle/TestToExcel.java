package com.wjy.excelmoudle;

import java.sql.SQLException;

public class TestToExcel {
    public static void main(String[] args) throws  SQLException {
        ExportExcel exportexcel = new ExportExcel();
        exportexcel.exportToExcel();
        System.out.println("END");
    }

}
