package hu.nati.appok.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import hu.nati.appok.todolist.db.TODODBHelper;
import hu.nati.appok.todolist.db.TodoDAO;
import hu.nati.appok.todolist.model.DateComparator;
import hu.nati.appok.todolist.model.Item;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TodoDAO dao;
    private List<Item> items;
    private ItemAdapter adapter;
    private ListView lvItems;
    private static final int RQC_NEW = 1;
    private static final int RQC_EDIT = 2;
    private DateComparator dc;
    private CheckBox cbStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                startActivityForResult(intent, RQC_NEW);
            }
        });

        lvItems = findViewById(R.id.lvItems);

        dao = new TodoDAO(this);


        items = dao.getAllItems();

        dc = new DateComparator();
        Collections.sort(items, dc);

        adapter = new ItemAdapter(this, R.layout.item, items);

        lvItems.setAdapter(adapter);

        registerForContextMenu(lvItems);

        lvItems.setOnItemClickListener(this);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;

        Item selectedItem = items.get(index);

        if (id == R.id.miEdit) {
            Intent intent = new Intent(this, ItemActivity.class);
            intent.putExtra("item", selectedItem);
            intent.putExtra("index", index);
            startActivityForResult(intent, RQC_EDIT);
            return true;

        } else if (id == R.id.miRemove) {
            dao.deleteItem(selectedItem);
            items.remove(selectedItem);
            adapter.notifyDataSetChanged();
            return true;

        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Item item = (Item) data.getSerializableExtra("result");
            if (requestCode == RQC_NEW) {
                int id = dao.saveItem(item);

                item.setId(id);
                items.add(item);
                Collections.sort(items, dc);

                adapter.notifyDataSetChanged();

            } else if (requestCode == RQC_EDIT) {
                int index = data.getIntExtra("index", -1);
                if (index > -1) {
                    dao.saveItem(item);
                    items.set(index, item);
                    Collections.sort(items, dc);
                    adapter.notifyDataSetChanged();

                }
            } else {
                Log.e("TODOList", "Hiba a mentés közben");
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//ha rákattint egy elemre, akkor átállítja annak a státuszát
        Item itemSelected = items.get(position);

       if(itemSelected.getStatus()==0){ //beállítja a státuszt a jelenlegi ellenkezőjére
           itemSelected.setStatus(1);
       } else{
           itemSelected.setStatus(0);
       }


        dao.saveItem(itemSelected);

        items.set(position, itemSelected);
        adapter.notifyDataSetChanged();

    }
}








