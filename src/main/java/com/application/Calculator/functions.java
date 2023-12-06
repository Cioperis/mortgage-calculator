package com.application.Calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.application.GUI.HelloController.roundDown;

public class functions {
    public TableView<tableData> table = new TableView<>();
    private inputData data;
    private ObservableList<tableData> list = FXCollections.observableArrayList();

    public functions(inputData data, TableView<tableData> table){
        this.table = table;
        this.data = data;
    }

    public void createList() {
        list.clear();
        data.startDate = LocalDate.now();

        if (data.loanPostponement0 != null && data.loanPostponement1 != null) {
            int n = (int) ChronoUnit.MONTHS.between(data.loanPostponement0, data.loanPostponement1);

            for (int i = 0; i < n; i++) {
                data.startDate = data.loanPostponement0.plusMonths(i);
                float interest = data.loanPostPercent / 100 / 12 * data.loanSum;
                interest = roundDown(interest);
                String date = data.loanPostponement0.plusMonths(i).toString();

                if (data.loanTimeFilter0 == null || data.loanTimeFilter0.isBefore(data.loanPostponement0.plusMonths(i)) &&
                        data.loanTimeFilter1.isAfter(data.loanPostponement0.plusMonths(i))) {
                    list.add(new tableData(date, interest, interest, data.loanSum));
                }
            }

            data.startDate = data.startDate.plusMonths(1);
        }

        if (data.loanAnuiteto == true) {
            float payment = (float) (data.loanSum * data.loanPercent / 100 / 12 / (1 - (1 / Math.pow(1 + data.loanPercent / 100 / 12, data.loanPeriod))));
            payment = roundDown(payment);

            for (int i = 0; i < data.loanPeriod; i++) {
                float interest = data.loanSum * data.loanPercent / 100 / 12;
                interest = roundDown(interest);
                String date = data.startDate.plusMonths(i).toString();

                if (data.loanTimeFilter0 == null || (data.loanTimeFilter0.isBefore(data.startDate.plusMonths(i)) &&
                        data.loanTimeFilter1.isAfter(data.startDate.plusMonths(i)))) {
                    list.add(new tableData(date, payment, interest, data.loanSum));
                }

                data.loanSum -= payment - interest;
                data.loanSum = roundDown(data.loanSum);
            }
        } else {
            float payment = data.loanSum / data.loanPeriod;
            payment = roundDown(payment);

            for (int i = 0; i < data.loanPeriod; i++) {
                float interest = data.loanSum * data.loanPercent / 100 / 12;
                interest = roundDown(interest);
                String date = data.startDate.plusMonths(i).toString();

                if (data.loanTimeFilter0 == null || (data.loanTimeFilter0.isBefore(data.startDate.plusMonths(i)) &&
                        data.loanTimeFilter1.isAfter(data.startDate.plusMonths(i)))) {
                    list.add(new tableData(date, payment + interest, interest, data.loanSum));
                }

                data.loanSum -= payment;
                data.loanSum = roundDown(data.loanSum);
            }
        }
    }

    public void createTable(){
        table.getColumns().clear();

        TableColumn<tableData, String> dateColumn = new TableColumn<>("Data");
        dateColumn.setCellValueFactory(new PropertyValueFactory<tableData, String>("data"));

        TableColumn<tableData, Float> paymentColumn = new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<tableData, Float>("payment"));

        TableColumn<tableData, Float> interestColumn = new TableColumn<>("Interest");
        interestColumn.setCellValueFactory(new PropertyValueFactory<tableData, Float>("interest"));

        TableColumn<tableData, Float> balanceColumn = new TableColumn<>("Balance");
        balanceColumn.setCellValueFactory(new PropertyValueFactory<tableData, Float>("balance"));

        table.getColumns().addAll(dateColumn, paymentColumn, interestColumn, balanceColumn);
        table.setItems(list);
    }
    public void createChart(LineChart<?,?> chart){
        XYChart.Series series = new XYChart.Series<>();

        for(int i=0; i<list.size(); i++){
            series.getData().add(new XYChart.Data(list.get(i).getData(), list.get(i).getPayment()));
        }

        if(data.loanAnuiteto == true){
            series.setName("Anuiteto");
        }
        else{
            series.setName("Linear");
        }

        chart.getData().add(series);
    }
}

