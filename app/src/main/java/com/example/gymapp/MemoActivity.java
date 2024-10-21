package com.example.gymapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {

    EditText mMemoEdit = null;
    TextFileManager mTextFileManager; // 이 부분은 onCreate 내에서 초기화할 것입니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memoactivity_main);

        mMemoEdit = findViewById(R.id.memo_edit);
        mTextFileManager = new TextFileManager(this); // 초기화를 onCreate 내에서 수행합니다.
    }

    public void onClick(View v) {
        int viewId = v.getId(); // 클릭된 뷰의 ID를 얻습니다.

        if (viewId == R.id.load_btn) {
            String memoData = mTextFileManager.load();
            mMemoEdit.setText(memoData);
            Toast.makeText(this, "불러오기 완료", Toast.LENGTH_LONG).show();

        } else if (viewId == R.id.save_btn) {
            String memoData = mMemoEdit.getText().toString();
            mTextFileManager.save(memoData);
            mMemoEdit.setText("");
            Toast.makeText(this, "저장 완료", Toast.LENGTH_LONG).show();

        } else if (viewId == R.id.delete_btn) {
            mTextFileManager.delete();
            mMemoEdit.setText("");
            Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show();
        }
    }
}
