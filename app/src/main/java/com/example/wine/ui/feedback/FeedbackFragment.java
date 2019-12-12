package com.example.wine.ui.feedback;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wine.R;

public class FeedbackFragment extends Fragment {

    private FeedbackViewModel FeedbackViewModel;
    private EditText mFeedBackEditText;
    private Button mSendFeedBackButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FeedbackViewModel =
                ViewModelProviders.of(this).get(FeedbackViewModel.class);
        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
        mFeedBackEditText = (EditText) root.findViewById(R.id.feedback_content);
        mSendFeedBackButton = (Button) root.findViewById(R.id.feedback_submit);
        mSendFeedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mFeedBackEditText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Toast.makeText(getContext().getApplicationContext(), "感谢您的反馈！",
                            Toast.LENGTH_SHORT).show();
                    //此处写处理逻辑
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "请您输入内容！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}