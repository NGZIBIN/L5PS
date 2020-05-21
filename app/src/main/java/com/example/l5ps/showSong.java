package com.example.l5ps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class showSong extends AppCompatActivity {
    ListView lv;
    listAdapter adapter;
    ArrayList<Song> al;
    Button btnFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song);

        lv = (ListView) this.findViewById(R.id.lv);
        btnFilter = findViewById(R.id.btnFilter);
        DBHelper dbh = new DBHelper(this);
        final ArrayList<Song> songs = dbh.getAllSong();

        adapter = new listAdapter(this, R.layout.row, songs);
        lv.setAdapter(adapter);
        al = new ArrayList<Song>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Song data = songs.get(position);
                Intent i = new Intent(showSong.this,
                        thirdActivity.class);
                i.putExtra("data", data);
                startActivityForResult(i, 9);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            DBHelper dbh = new DBHelper(showSong.this);
            al.clear();
            al.addAll(dbh.getAllSong("5"));
            dbh.close();
            adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper dbh = new DBHelper(showSong.this);
            al.clear();
            al.addAll(dbh.getAllSong());
            dbh.close();
            adapter = new listAdapter(this, R.layout.row, al);
            lv.setAdapter(adapter);
        }

    }
}
