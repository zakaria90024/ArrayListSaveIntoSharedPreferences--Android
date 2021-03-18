package com.zakaria.arraylistsaveintosharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadData();
        buildRecyclerView();
        setInsertButton();


        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }


        private void saveData() {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(mExampleList);
            editor.putString("task list", json);
            editor.apply();
        }
        private void loadData() {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list", null);

            Type type = new TypeToken<ArrayList<ExampleItem>>() {}.getType();
            mExampleList = gson.fromJson(json, type);
            if (mExampleList == null) {
                mExampleList = new ArrayList<>();
            }
        }


        private void buildRecyclerView() {
            mRecyclerView = findViewById(R.id.recyclerview);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new ExampleAdapter(mExampleList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }

        private void setInsertButton() {
            Button buttonInsert = findViewById(R.id.button_insert);
            buttonInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText line1 = findViewById(R.id.edittext_line_1);
                    EditText line2 = findViewById(R.id.edittext_line_2);
                    insertItem(line1.getText().toString(), line2.getText().toString());
                }
            });
        }


        private void insertItem(String line1, String line2) {
            mExampleList.add(new ExampleItem(line1, line2));
            mAdapter.notifyItemInserted(mExampleList.size());
        }

}