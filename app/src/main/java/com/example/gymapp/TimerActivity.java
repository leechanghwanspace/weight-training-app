package com.example.gymapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    LinearLayout timeCountSettingLV, timeCountLV;
    EditText hourET, minuteET, secondET;
    TextView hourTV, minuteTV, secondTV, finishTV;
    Button startBtn;
    int hour, minute, second;
    Timer timer;
    boolean isTimerRunning = false; // 추가: 타이머가 실행 중인지 여부를 저장하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeractivity_main);

        timeCountSettingLV = findViewById(R.id.timeCountSettingLV);
        timeCountLV = findViewById(R.id.timeCountLV);

        hourET = findViewById(R.id.hourET);
        minuteET = findViewById(R.id.minuteET);
        secondET = findViewById(R.id.secondET);

        hourTV = findViewById(R.id.hourTV);
        minuteTV = findViewById(R.id.minuteTV);
        secondTV = findViewById(R.id.secondTV);
        finishTV = findViewById(R.id.finishTV);

        startBtn = findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 추가: 타이머가 실행 중이면 시작 버튼 동작하지 않도록 처리
                if (isTimerRunning) {
                    Toast.makeText(TimerActivity.this, "타이머가 이미 실행 중입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (hourET.getText().toString().isEmpty() || minuteET.getText().toString().isEmpty() || secondET.getText().toString().isEmpty()) {
                    Toast.makeText(TimerActivity.this, "시간, 분, 초를 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                timeCountSettingLV.setVisibility(View.GONE);
                timeCountLV.setVisibility(View.VISIBLE);

                hourTV.setText(hourET.getText().toString());
                minuteTV.setText(minuteET.getText().toString());
                secondTV.setText(secondET.getText().toString());

                hour = Integer.parseInt(hourET.getText().toString());
                minute = Integer.parseInt(minuteET.getText().toString());
                second = Integer.parseInt(secondET.getText().toString());

                timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (second != 0) {
                            second--;
                        } else if (minute != 0) {
                            second = 59;
                            minute--;
                        } else if (hour != 0) {
                            second = 59;
                            minute = 59;
                            hour--;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTimerUI();

                                if (hour <= 0 && minute <= 0 && second <= 0) {
                                    timer.cancel();
                                    showTimerPopup();
                                    isTimerRunning = false; // 추가: 타이머 종료 후 상태 업데이트
                                }
                            }
                        });
                    }
                };

                timer.schedule(timerTask, 0, 1000);
                isTimerRunning = true; // 추가: 타이머가 시작되면 상태 업데이트
            }
        });
    }

    private void updateTimerUI() {
        if (second <= 9) {
            secondTV.setText("0" + second);
        } else {
            secondTV.setText(Integer.toString(second));
        }

        if (minute <= 9) {
            minuteTV.setText("0" + minute);
        } else {
            minuteTV.setText(Integer.toString(minute));
        }

        if (hour <= 9) {
            hourTV.setText("0" + hour);
        } else {
            hourTV.setText(Integer.toString(hour));
        }
    }

    private void showTimerPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("시간이 종료되었습니다.")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        resetTimer();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void resetTimer() {
        timeCountSettingLV.setVisibility(View.VISIBLE);
        timeCountLV.setVisibility(View.GONE);

        hourET.setText("");
        minuteET.setText("");
        secondET.setText("");
    }
}
