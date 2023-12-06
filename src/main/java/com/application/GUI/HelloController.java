package com.application.GUI;

import com.application.Calculator.exportFile;
import com.application.Calculator.functions;
import com.application.Calculator.inputData;
import com.application.Calculator.tableData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.Date;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class HelloController {
    functions start;
    exportFile export;
    inputData data = new inputData();
    @FXML
    private TextField loanSum;

    @FXML
    private TextField loanPeriod;

    @FXML
    private TextField loanPercent;

    @FXML
    private Button submitButton;

    @FXML
    private ToggleButton loanLinear;
    @FXML
    private ToggleButton loanAnuiteto;
    @FXML
    private DatePicker loanPostponement0;
    @FXML
    private DatePicker loanPostponement1;
    @FXML
    private TextField loanPostPercent;
    @FXML
    private DatePicker loanTimeFilter0;
    @FXML
    private DatePicker loanTimeFilter1;
    @FXML
    private TableView<tableData> outputTable;
    @FXML
    private LineChart<?,?> outputChart;
    @FXML
    private Text loanError;

    boolean logicInput = true;

    // Upon pressing Submit button
    public void submit(ActionEvent event) throws Exception {
        try {
            data.loanSum = parseFloat(loanSum.getText());
            System.out.println(data.loanSum);

            String temp = loanPeriod.getText();
            int a = parseInt(temp.substring(0, temp.indexOf("-")));
            int b = parseInt(temp.substring(temp.indexOf("-") + 1));
            data.loanPeriod = a * 12 + b;
            System.out.println(data.loanPeriod);

            data.loanPercent = parseInt(loanPercent.getText());
            System.out.println(data.loanPercent);

            logicInput = true;
        } catch (Exception e) {
            loanError.setText("Wrong input");
            logicInput = false;
        }

        data.loanLinear = loanLinear.isSelected();
        System.out.println(data.loanLinear);

        data.loanAnuiteto = loanAnuiteto.isSelected();
        System.out.println(data.loanAnuiteto);

        // equal to null if unselected
        data.loanPostponement0 = loanPostponement0.getValue();
        System.out.println(data.loanPostponement0);

        // equal to null if unselected
        data.loanPostponement1 = loanPostponement1.getValue();
        System.out.println(data.loanPostponement1);

        if (data.loanPostponement0 != null) {
            data.loanPostPercent = roundDown(parseInt(loanPostPercent.getText()));
            System.out.println(data.loanAnuiteto);
        }

        data.loanTimeFilter0 = loanTimeFilter0.getValue();
        System.out.println(data.loanTimeFilter0);

        data.loanTimeFilter1 = loanTimeFilter1.getValue();
        System.out.println(data.loanTimeFilter1);

        System.out.println("LOGIKA: " + logicInput);

        if(logicInput == true){
            calculate();
        }
    }

    public static float roundDown(float f) {
        return (float) (Math.floor(f * 100)/100);
    }

    public void calculate(){
        start = new functions(data, outputTable);
        start.createList();
        start.createTable();
        start.createChart(outputChart);
    }

    public void clearChart(){
        outputChart.getData().clear();
    }

    public void saveTable(){
        export = new exportFile(data, outputTable);
        export.exportTable();
    }
}