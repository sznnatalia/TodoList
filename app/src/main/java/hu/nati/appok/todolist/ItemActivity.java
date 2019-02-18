package hu.nati.appok.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hu.nati.appok.todolist.model.Item;

public class ItemActivity extends AppCompatActivity {

    private Spinner spYear;
    private Spinner spMonth;
    private Spinner spDay;
   private Intent intent;
    private  Item item;
    private EditText etTodo;
    private EditText etComment;
    private RadioButton rbLow;
    private RadioButton rbMed;
    private RadioButton rbHigh;
    private ArrayAdapter<String> yearAdapter;
    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<String> dayAdapter;
    private List<String> year;
    private List<String> month;
    private List<String> day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        etTodo = findViewById(R.id.etTodo);
        etComment = findViewById(R.id.etComment);
        rbLow = findViewById(R.id.rbLow);
        rbMed = findViewById(R.id.rbMed);
        rbHigh = findViewById(R.id.rbHigh);

        spYear = findViewById(R.id.spYear);
        spMonth = findViewById(R.id.spMonth);
        spDay = findViewById(R.id.spDay);

        yearAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, yearListFill());

        spYear.setAdapter(yearAdapter);

        monthAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, monthListFill());
        spMonth.setAdapter(monthAdapter);

        dayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dayListFill());
        spDay.setAdapter(dayAdapter);


        intent = getIntent();

        item = (Item) intent.getSerializableExtra("item");

        if (item != null) {


            etTodo.setText(item.getTodo());
            etComment.setText(item.getComment());

            switch (item.getPrior()) {
                case 0:
                    rbLow.setChecked(true);
                    break;
                case 1:
                    rbMed.setChecked(true);
                    break;
                case 2:
                    rbHigh.setChecked(true);
                    break;
            }

            int yearPos = yearAdapter.getPosition(year());
            spYear.setSelection(yearPos);

            int monthPos = monthAdapter.getPosition(month());
            spMonth.setSelection(monthPos);

            int dayPos = dayAdapter.getPosition(day());
            spDay.setSelection(dayPos);


        }


    }

    private List<String> yearListFill() {
        year = new ArrayList<>();
        year.add("2019");
        year.add("2020");
        year.add("2021");
        year.add("2022");
        year.add("2023");

        return year;

    }

    private List<String> monthListFill() {
        month = new ArrayList<>();
        month.add("01");
        month.add("02");
        month.add("03");
        month.add("04");
        month.add("05");
        month.add("06");
        month.add("07");
        month.add("08");
        month.add("09");
        month.add("10");
        month.add("11");
        month.add("12");

        return month;

    }

    private List<String> dayListFill() {
        day = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            day.add(i + "");
        }

        return day;

    }

    private String year() {
        String year = item.getDate().substring(0, 5);

        return year;


    }


    private String month() {
        String month = item.getDate().substring(6, 8);

        return month;

    }

    private String day(){
        String day = item.getDate().substring(8,10);
        return day;
    }

    public void back(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {

        if(item==null){
            item = new Item();
            item.setStatus(0);
        }

        item.setTodo(etTodo.getText().toString());
        item.setComment(etComment.getText().toString());

        if(rbLow.isChecked()){
            item.setPrior(0);
        } else if (rbMed.isChecked()){
            item.setPrior(1);
        } else if (rbHigh.isChecked()){
            item.setPrior(2);
        }


        String year = (String) spYear.getSelectedItem();
        String month = (String) spMonth.getSelectedItem();
        String day = (String) spDay.getSelectedItem();

        String date = year+"."+month+"."+day+".";
        item.setDate(date);

      //  Toast.makeText(this, (item.getTodo()+" "+item.getComment()+" "+item.getPrior()+" "+item.getDate()+" "+item.getStatus()), Toast.LENGTH_SHORT).show();


           intent.putExtra("result",item);

           setResult(RESULT_OK, intent);
           finish();

    }
}
