package com.staplex.tfsht2;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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
        v.setTransitionName(name);
        v.setOnClickListener((View view) -> {
            MetroViewGroup oldParent, newParent;
            if (view.getParent() == mvgTop) {
                oldParent = mvgTop;
                newParent = mvgBottom;
            } else {
                oldParent = mvgBottom;
                newParent = mvgTop;
            }

            View newView = inflateMetroStationView(newParent, name, color);
            newView.setVisibility(View.INVISIBLE);
            newView.addOnLayoutChangeListener((nView, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                if (newView.getParent() != null) {
                    Rect to = new Rect(left, top, right, bottom);
                    to.offset(newParent.getLeft(), newParent.getTop());
                    to.offset(-oldParent.getLeft(), -oldParent.getTop());
                    Path path = new Path();
                    path.setLastPoint(view.getX(), view.getY());
                    path.lineTo(to.left, to.top);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(view, "x", "y", path)
                            .setDuration(500);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            TransitionManager.beginDelayedTransition(root, new ChangeBounds());
                            oldParent.removeView(view);
                            newView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator.start();
                }
            });
            newParent.addView(newView);
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
