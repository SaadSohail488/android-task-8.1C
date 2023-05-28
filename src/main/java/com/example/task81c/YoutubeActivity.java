package com.example.task81c;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class YoutubeActivity extends AppCompatActivity {
    private EditText editTextYouTubeUrl;
    private Button buttonPlay, buttonAddToPlaylist, buttonMyPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        // Initialize views
        editTextYouTubeUrl = findViewById(R.id.editTextYouTubeUrl);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonAddToPlaylist = findViewById(R.id.buttonAddToPlaylist);
        buttonMyPlaylist = findViewById(R.id.buttonMyPlaylist);

        buttonPlay.setOnClickListener(v -> {
            // Retrieve entered YouTube URL
            String youtubeUrl = editTextYouTubeUrl.getText().toString().trim();

            // Create an intent to launch the PlayActivity
            Intent playIntent = new Intent(YoutubeActivity.this, PlayActivity.class);
            playIntent.putExtra("youtube_url", youtubeUrl);
            startActivity(playIntent);
        });

        buttonAddToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeUrl = editTextYouTubeUrl.getText().toString().trim();

                // Create an instance of SQLClass
                SQLClass sqlClass = new SQLClass(YoutubeActivity.this);

                // Add the YouTube URL to the playlist
                boolean isAdded = sqlClass.addToPlaylist(youtubeUrl);

                if (isAdded) {
                    // URL added successfully, show a success message
                    Toast.makeText(YoutubeActivity.this, "URL added to playlist", Toast.LENGTH_SHORT).show();
                } else {
                    // Failed to add URL, show an error message
                    Toast.makeText(YoutubeActivity.this, "Failed to add URL to playlist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonMyPlaylist.setOnClickListener(v -> {
            // Launch the MyPlaylistActivity
            Intent playlistIntent = new Intent(YoutubeActivity.this, MyPlaylistActivity.class);
            startActivity(playlistIntent);
        });
    }
}
