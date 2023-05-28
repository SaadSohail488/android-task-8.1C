package com.example.task81c;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayActivity extends AppCompatActivity {

    String url;
    YouTubePlayerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        url = getIntent().getStringExtra("youtube_url");

        view = findViewById(R.id.youtube_player_view);

        String link;

        if(url.contains("watch")) {
            link = url.substring(url.lastIndexOf("=") + 1);
        }
        else {
            link = url.substring(url.lastIndexOf("/") + 1);
        }

        getLifecycle().addObserver(view);

        view.invalidate();

        view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(link, 0);
            }
        });
    }
}