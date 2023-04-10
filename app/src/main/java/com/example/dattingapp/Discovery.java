package com.example.dattingapp;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class Discovery extends ProfileActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

//        SeekBar seekBar = findViewById(R.id.seekBar);
//        final TextView textView = findViewById(R.id.);
//
//// Tính toán vị trí của dấu tick
//        int thumbOffset = seekBar.getThumbOffset();
//        int seekBarWidth = seekBar.getWidth() - 2 * thumbOffset;
//        int val = seekBar.getProgress();
//        int xPos = thumbOffset + seekBarWidth * val / seekBar.getMax();
//
//// Đặt vị trí của TextView theo vị trí của dấu tick
//        textView.setX(xPos);
//        textView.setText(String.valueOf(val));
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                // Tính toán vị trí của dấu tick
//                int thumbOffset = seekBar.getThumbOffset();
//                int seekBarWidth = seekBar.getWidth() - 2 * thumbOffset;
//                int val = seekBar.getProgress();
//                int xPos = thumbOffset + seekBarWidth * val / seekBar.getMax();
//
//                // Đặt vị trí của TextView theo vị trí của dấu tick và cập nhật giá trị
//                textView.setX(xPos);
//                textView.setText(String.valueOf(val));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                // Không cần thực hiện gì khi bắt đầu chạm SeekBar
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                // Không cần thực hiện gì khi kết thúc chạm SeekBar
//            }
//        });

    }
}
