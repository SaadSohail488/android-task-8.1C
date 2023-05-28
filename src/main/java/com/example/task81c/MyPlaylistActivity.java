package com.example.task81c;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MyPlaylistActivity extends AppCompatActivity {
    private ListView listViewPlaylist;
    private List<String> playlistItems;
    private ArrayAdapter<String> playlistAdapter;
    private SQLClass sqlClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_playlist);

        // Initialize SQLClass
        sqlClass = new SQLClass(this);

        // Initialize the ListView and playlist data
        listViewPlaylist = findViewById(R.id.listViewPlaylist);
        playlistItems = new ArrayList<>();
        playlistAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistItems);
        listViewPlaylist.setAdapter(playlistAdapter);

        listViewPlaylist.setOnItemClickListener((parent, view, position, id) -> {
            Intent playIntent = new Intent(MyPlaylistActivity.this, PlayActivity.class);
            playIntent.putExtra("youtube_url", playlistItems.get(position));
            startActivity(playIntent);
        });

        // Retrieve URLs from the database and populate the playlist
        retrieveUrlsFromDatabase();
    }

    private void retrieveUrlsFromDatabase() {
        List<String> playlist = sqlClass.getPlaylist();
        playlistItems.addAll(playlist);
        playlistAdapter.notifyDataSetChanged();
    }

    private void addUrlToPlaylist(String url) {
        playlistItems.add(url);
        playlistAdapter.notifyDataSetChanged();
    }
}
