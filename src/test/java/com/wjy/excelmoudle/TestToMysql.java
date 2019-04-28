package com.wjy.excelmoudle;

import java.io.IOException;
import java.sql.SQLException;

public class TestToMysql {
    public static void main(String[] args) throws IOException, SQLException {
        SaveData saveData = new SaveData();
        saveData.save();
        System.out.println("end");
    }
}

