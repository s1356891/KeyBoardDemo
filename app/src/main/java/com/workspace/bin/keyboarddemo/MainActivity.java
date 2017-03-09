package com.workspace.bin.keyboarddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.workspace.bin.keyboarddemo.view.KeyboardUtil;
import com.workspace.bin.keyboarddemo.view.XEditText;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout rl_keyboard;
    private KeyboardUtil keyboardUtil;
    private int change_type;
    private XEditText et_amount;
    private Button btn_showKey,btn_hideKey,btn_price,btn_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTestBtn();
        initKeyboard();
    }
    private void initTestBtn() {
        btn_showKey = (Button) findViewById(R.id.btn_showKey);
        btn_hideKey = (Button) findViewById(R.id.btn_hideKey);
        btn_price = (Button) findViewById(R.id.btn_price);
        btn_number = (Button) findViewById(R.id.btn_number);
        btn_showKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showKeyBoard();
            }
        });
        btn_hideKey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyBoard();
            }
        });
        btn_price.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change_type = KeyboardUtil.TYPE_PRICE;
                showKeyBoard();
            }
        });
        btn_number.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                change_type = KeyboardUtil.TYPE_NUMBER;
                showKeyBoard();
            }
        });
    }
    private void initKeyboard() {
        et_amount = (XEditText) findViewById(R.id.et_amount);
        keyboardUtil = new KeyboardUtil(this, et_amount);
        rl_keyboard = (RelativeLayout) findViewById(R.id.rl__keyboard);
        et_amount.setInputType(InputType.TYPE_NULL);
        keyboardUtil.setKeyboardListener(new KeyboardUtil.KeyboardListener() {

            @Override
            public void onOK() {
                String result = et_amount.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case KeyboardUtil.TYPE_NUMBER:
                            msg += "num:"+result;
                            break;
                        case KeyboardUtil.TYPE_PRICE:
                            msg += "price:"+result;
                            break;
                        default:
                            msg += "input:"+result;
                            break;
                    }
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                hideKeyBoard();
                change_type = -1;
            }
        });
        et_amount.setDrawableRightListener(new XEditText.DrawableRightListener() {

            @Override
            public void onDrawableRightClick(View view) {
                Editable editable = ((EditText) view).getText();
                int start = ((EditText) view).getSelectionStart();
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            }
        }, new XEditText.DrawableRightLongListener() {
            @Override
            public void onDrawableRightLongClick(View view) {
                Editable editable = ((EditText) view).getText();
                int start = ((EditText) view).getSelectionStart();
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(0, start);
                    }
                }
            }
        });
    }
    /**
     * 显示键盘
     */


    protected void showKeyBoard() {
        rl_keyboard.setVisibility(View.VISIBLE);
        et_amount.setText("");
        switch (change_type) {
            case KeyboardUtil.TYPE_NUMBER:
                et_amount.setHint("请输入数量");
                break;

            case KeyboardUtil.TYPE_PRICE:
                et_amount.setHint("请输入价格");
                break;

            default:
                break;
        }
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
    }

    /**
     * 隐藏键盘
     */
    protected void hideKeyBoard() {
        rl_keyboard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (rl_keyboard.getVisibility() != View.GONE) {
                hideKeyBoard();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

