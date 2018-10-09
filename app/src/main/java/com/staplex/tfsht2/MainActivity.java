package com.staplex.tfsht2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final HashMap<String, Integer> stations = new HashMap<>();

    static {
        stations.put("Водный стадион", android.R.color.holo_green_dark);
        stations.put("Смоленская", android.R.color.holo_blue_dark);
        stations.put("Краснопресненская", R.color.brown);
        stations.put("ВДНХ", android.R.color.holo_orange_dark);
        stations.put("Планерная", android.R.color.holo_purple);
        stations.put("Раменки", R.color.yellow);
        stations.put("Алтуфьево", android.R.color.darker_gray);
        stations.put("Нагорная", android.R.color.darker_gray);
        stations.put("Римская", android.R.color.holo_green_light);
        stations.put("Преображенская площадь", android.R.color.holo_red_dark);
        stations.put("Новокосино", R.color.yellow);
        stations.put("Водный стадион 2", android.R.color.holo_green_dark);
        stations.put("Смоленская 2", android.R.color.holo_blue_dark);
        stations.put("Краснопресненская 2", R.color.brown);
        stations.put("ВДНХ 2", android.R.color.holo_orange_dark);
        stations.put("Планерная 2", android.R.color.holo_purple);
        stations.put("Раменки 2", R.color.yellow);
        stations.put("Алтуфьево 2", android.R.color.darker_gray);
        stations.put("Нагорная 2", android.R.color.darker_gray);
        stations.put("Римская 2", android.R.color.holo_green_light);
        stations.put("Преображенская площадь 2", android.R.color.holo_red_dark);
        stations.put("Новокосино 2", R.color.yellow);
    }

    private View inflateMetroStationView(ViewGroup root, String name, int color) {
        View v = getLayoutInflater().inflate(R.layout.metro_station_layout, root, false);
        ((TextView) v.findViewById(R.id.metro_station_name)).setText(name);
        v.findViewById(R.id.location_icon).setBackgroundColor(color);
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MetroViewGroup top = (MetroViewGroup) findViewById(R.id.mvg_top);
        for (Map.Entry<String, Integer> station :
                stations.entrySet()) {
            top.addView(inflateMetroStationView(top, station.getKey(), station.getValue()));
        }
    }
}
