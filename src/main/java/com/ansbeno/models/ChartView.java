package com.ansbeno.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import org.primefaces.component.piechart.PieChart;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ChartView implements Serializable {

      private static final long serialVersionUID = 1L;

      private String json;

      private String pieModel;

      @PostConstruct
      public void init() {

            createPieModel();

      }

      private void createPieModel() {

            // PieChartModel pieModel = new PieChartModel();
            // ChartData data = new ChartData();
            // PieChartDataSet dataSet = new PieChartDataSet();

            // dataSet.setData(Arrays.asList(300, 50, 100));
            // dataSet.setLabel("My First Dataset");
            // dataSet.setBackgroundColor(Arrays.asList("rgb(255, 99, 132)", "rgb(54, 162,
            // 235)", "rgb(255, 205, 86)"));

            // data.addChartDataSet(dataSet);
            // data.setLabels(Arrays.asList("Red", "Blue", "Yellow"));

            // pieModel.setData(data);
            // this.pieModel = pieModel.toJson();
      }

}