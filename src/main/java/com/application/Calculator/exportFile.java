package com.application.Calculator;


import javafx.scene.control.TableView;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class exportFile extends functions{
    public void exportTable() {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sheet1");

        Row row = spreadsheet.createRow(0);

        for (int i = 0; i < table.getColumns().size(); i++) {
            row.createCell(i).setCellValue(table.getColumns().get(i).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);

            for (int j = 0; j < table.getColumns().size(); j++) {
                if (table.getColumns().get(j).getCellData(i) != null) {
                    if (table.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
        }

        FileOutputStream fileOut = null;
        try {
            File old = new File("out.xls");
            old.delete();
            fileOut = new FileOutputStream("out.xls");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public exportFile(inputData data, TableView<tableData> table) {
        super(data, table);
    }
}