package com.example.maxim.librarieshw2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText1;
    EditText editText2;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.et1_activity_main);
        editText2 = (EditText) findViewById(R.id.et2_activity_main);
        textView = (TextView) findViewById(R.id.tv_activity_main);

        PublishSubject<CharSequence> subject = PublishSubject.create();
        RxTextView.textChanges(editText1)
                .subscribe(subject);
        RxTextView.textChanges(editText2).subscribe(subject);

        disposable = subject.subscribe(new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                String string1 = String.valueOf(textView.getText());
                String string2 = String.valueOf(charSequence);
                textView.setText(string1 + string2);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
