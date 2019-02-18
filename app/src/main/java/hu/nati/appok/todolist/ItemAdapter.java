package hu.nati.appok.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hu.nati.appok.todolist.model.Item;

public class ItemAdapter extends ArrayAdapter<Item> {
private int resource;


    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.resource=resource;
        }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(resource,null);
        }

        TextView tvTODO = convertView.findViewById(R.id.tvTODO);
        TextView tvComm = convertView.findViewById(R.id.tvComment);
        ImageView prior = convertView.findViewById(R.id.imPrior);
        CheckBox cbStat = convertView.findViewById(R.id.cbStatus);
        TextView tvDate = convertView.findViewById(R.id.tvDate);

        tvTODO.setText(item.getTodo());
        tvComm.setText(item.getComment());
        tvDate.setText(item.getDate());

        cbStat.setChecked(item.getStatus()==0?false:true);



        if(item.getPrior()==0){
            prior.setImageResource(R.drawable.low);
        } else if (item.getPrior()==1){
            prior.setImageResource(R.drawable.medium);
        } else {
            prior.setImageResource(R.drawable.high);
        }

        if(item.getStatus()==0){
            cbStat.setChecked(false);
        } else {
            cbStat.setChecked(true);
        }

        return convertView;




    }
}
