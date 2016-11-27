package com.android.personal.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
public class EditActivity extends AppCompatActivity {

    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        selected = intent.getExtras().getInt(Global.SELECTED, 0);
        String content = intent.getExtras().getString(Global.CONTENT, "");
        updateText(content);
    }



    private void updateText(String text){
        EditText editText = (EditText)findViewById(R.id.edit);
        editText.setText(text);
    }


    public void save(View v){
        EditText editText = (EditText)findViewById(R.id.edit);
        Intent intent = new Intent();
        intent.putExtra(Global.SELECTED, selected);
        intent.putExtra(Global.CONTENT, editText.getText().toString());
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
