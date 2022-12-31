package com.penelope.ketodiet.ui.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.penelope.ketodiet.data.statistic.Composition;
import com.penelope.ketodiet.databinding.ActivityHistoryBinding;
import com.penelope.ketodiet.res.StringRes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private HistoryViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 파이 차트 업데이트
        viewModel.getComposition().observe(this, composition -> {
            if (composition != null) {
                List<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry((float) composition.getRateOfCarbohydrate(), "탄수화물"));
                entries.add(new PieEntry((float) composition.getRateOfProtein(), "단백질"));
                entries.add(new PieEntry((float) composition.getRateOfFats(), "지방"));
                PieDataSet pds = new PieDataSet(entries, "");
                pds.setColors(0xFF555555, 0xFF777777, 0xFF999999);
                pds.setValueTextColor(0xFFEEEEEE);
                pds.setValueTextSize(12f);
                pds.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return Math.round(value * 100) + "%";
                    }
                });
                PieData pieData = new PieData(pds);
                binding.pieChart.setData(pieData);
                binding.pieChart.getDescription().setEnabled(false);
                binding.pieChart.invalidate();
            }
        });

        // 라인 차트의 스타일을 지정한다
        binding.lineChart.getDescription().setEnabled(false);
        binding.lineChart.getXAxis().setDrawGridLines(false);
        binding.lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.lineChart.getXAxis().setTextColor(0xFFCCCCCC);
        binding.lineChart.getAxisRight().setEnabled(false);
        binding.lineChart.getAxisLeft().setAxisMinimum(0f);
        binding.lineChart.getAxisLeft().setAxisMaximum(1f);
        binding.lineChart.getAxisLeft().setDrawGridLines(false);
        binding.lineChart.getAxisLeft().setTextColor(0xFFCCCCCC);
        binding.lineChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                int v = Math.round(value * 100);
                return (v == 20 || v == 40 || v == 60 || v == 80  || v == 100) ? String.valueOf(v) : "";
            }
        });

        // 통계를 라인 차트에 업데이트한다
        binding.lineChart.setTouchEnabled(false);
        viewModel.getCompositionDetail().observe(this, detail -> {
            if (detail != null) {
                // 통계 정보를 엔트리로 정리한다
                List<Entry> entriesC = new ArrayList<>();
                List<Entry> entriesP = new ArrayList<>();
                List<Entry> entriesF = new ArrayList<>();
                for (int i = 0; i < detail.size(); i++) {
                    Composition composition = detail.get(i).second;
                    if (composition.getCalories() > 0) {
                        entriesC.add(new Entry(i, (float) composition.getRateOfCarbohydrate()));
                        entriesP.add(new Entry(i, (float) composition.getRateOfProtein()));
                        entriesF.add(new Entry(i, (float) composition.getRateOfFats()));
                    }
                }
                // 데이터셋의 스타일을 지정하고 다시 그린다
                LineDataSet dataSetC = new LineDataSet(entriesC, "탄수화물");
                LineDataSet dataSetP = new LineDataSet(entriesP, "단백질");
                LineDataSet dataSetF = new LineDataSet(entriesF, "지방");
                dataSetC.setLineWidth(5f);
                dataSetP.setLineWidth(5f);
                dataSetF.setLineWidth(5f);
                dataSetC.setColor(0xFF555555);
                dataSetP.setColor(0xFFAAAAAA);
                dataSetF.setColor(0xFFDDDDDD);
                binding.lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        int index = (int) value;
                        if (index > detail.size() - 1) {
                            return "";
                        }
                        LocalDate date = detail.get(index).first;
                        return StringRes.monthDay(date.getMonthValue(), date.getDayOfMonth());
                    }
                });
                LineData data = new LineData(dataSetC, dataSetP, dataSetF);
                data.setDrawValues(false);
                binding.lineChart.setData(data);
                binding.lineChart.invalidate();
            }
        });
    }
}