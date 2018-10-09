package com.staplex.tfsht2;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final HashMap<String, Integer> stations = new HashMap<>();
    private MetroViewGroup mvgTop, mvgBottom;

    private View inflateMetroStationView(ViewGroup root, String name, @ColorInt int color) {
        View v = getLayoutInflater().inflate(R.layout.metro_station_layout, root, false);
        ((TextView) v.findViewById(R.id.metro_station_name)).setText(name);
        ((ImageView) v.findViewById(R.id.location_icon)).setColorFilter(color);
        v.setOnClickListener((View view) -> {
            if (view.getParent() == mvgTop) {
                mvgTop.removeView(view);
                mvgBottom.addView(view);
                mvgBottom.requestLayout();
            } else {
                mvgBottom.removeView(view);
                mvgTop.addView(view);
                mvgTop.requestLayout();
            }
        });
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        stations.put("Водный стадион", getResources().getColor(android.R.color.holo_green_dark));
        stations.put("Смоленская", getResources().getColor(android.R.color.holo_blue_dark));
        stations.put("Краснопресненская", getResources().getColor(R.color.brown));
        stations.put("ВДНХ", getResources().getColor(android.R.color.holo_orange_dark));
        stations.put("Планерная", getResources().getColor(android.R.color.holo_purple));
        stations.put("Раменки", getResources().getColor(R.color.yellow));
        stations.put("Алтуфьево", getResources().getColor(android.R.color.darker_gray));
        stations.put("Нагорная", getResources().getColor(android.R.color.darker_gray));
        stations.put("Римская", getResources().getColor(android.R.color.holo_green_light));
        stations.put("Преображенская площадь", getResources().getColor(android.R.color.holo_red_dark));
        stations.put("Новокосино", getResources().getColor(R.color.yellow));
        stations.put("Водный стадион 2", getResources().getColor(android.R.color.holo_green_dark));
        stations.put("Смоленская 2", getResources().getColor(android.R.color.holo_blue_dark));
        stations.put("Краснопресненская 2", getResources().getColor(R.color.brown));
        stations.put("ВДНХ 2", getResources().getColor(android.R.color.holo_orange_dark));
        stations.put("Планерная 2", getResources().getColor(android.R.color.holo_purple));
        stations.put("Раменки 2", getResources().getColor(R.color.yellow));
        stations.put("Алтуфьево 2", getResources().getColor(android.R.color.darker_gray));
        stations.put("Нагорная 2", getResources().getColor(android.R.color.darker_gray));
        stations.put("Римская 2", getResources().getColor(android.R.color.holo_green_light));
        stations.put("Преображенская площадь 2", getResources().getColor(android.R.color.holo_red_dark));
        stations.put("Новокосино 2", getResources().getColor(R.color.yellow));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mvgTop = findViewById(R.id.mvg_top);
        mvgBottom = findViewById(R.id.mvg_bottom);
        for (Map.Entry<String, Integer> station :
                stations.entrySet()) {
            mvgBottom.addView(inflateMetroStationView(mvgBottom, station.getKey(), station.getValue()));
        }
    }
}
