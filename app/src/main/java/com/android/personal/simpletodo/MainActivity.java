package com.android.personal.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 0;
    ListView listView;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String content = data.getExtras().getString(Global.CONTENT);
            int selected = data.getExtras().getInt(Global.SELECTED);
            items.set(selected, content);
            writeItems();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    private void setUpListViewListener(){
        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items.remove(i);
                        writeItems();
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), EditActivity.class);
                intent.putExtra(Global.SELECTED, i);
                intent.putExtra(Global.CONTENT, items.get(i));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    public void addItem(View v){
        EditText editText = (EditText)findViewById(R.id.add);
        String text = editText.getText().toString();
        itemsAdapter.add(text);
        writeItems();
        editText.setText("");
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            items = new ArrayList<>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(file, items);
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
