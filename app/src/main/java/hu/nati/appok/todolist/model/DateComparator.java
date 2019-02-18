package hu.nati.appok.todolist.model;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        String dateString1 = o1.getDate();
        String dateString2 = o2.getDate();

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd.");
        Date date1=null;
        Date date2=null;
        try {
            date1  =  df.parse(dateString1);
            date2  =  df.parse(dateString2);

        } catch (ParseException e) {
            Log.e("TODOList", "Hiba a dátum formátummal: "+e);
        }

        if(date1.before(date2)){
            return -1;
        } else{
            return 1;
        }

    }
}
