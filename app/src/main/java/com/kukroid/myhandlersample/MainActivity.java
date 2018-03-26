package com.kukroid.myhandlersample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static TextView command,swtsCommand;
    Handler uiHandler;
    Message msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        command = findViewById(R.id.command);
        swtsCommand = findViewById(R.id.swtsCommand);
        uiHandler = new UIHandler();

    }

    public void giveOrder(View view) {
        Runnable kitchenThread = new Runnable() {
            @Override
            public void run() {

                try {
                    msg = uiHandler.obtainMessage(UIHandler.OK);
                    uiHandler.sendMessage(msg);
                    Thread.sleep(1000);
                    msg = uiHandler.obtainMessage(UIHandler.STARTED);
                    uiHandler.sendMessage(msg);
                    Thread.sleep(5000);
                    msg = uiHandler.obtainMessage(UIHandler.READY);
                    uiHandler.sendMessage(msg);
                    Thread.sleep(1000);
                    msg = uiHandler.obtainMessage(UIHandler.ENJOY);
                    uiHandler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(kitchenThread).start();
    }

    public void getSweets(View view) {

        Runnable runSwtsCommand = new Runnable() {
            @Override
            public void run() {

                msg = uiHandler.obtainMessage(UIHandler.SWEETS_READY);
                uiHandler.sendMessage(msg);
            }
        };

        msg = uiHandler.obtainMessage(UIHandler.SWEETS_COMMAND);
        uiHandler.sendMessage(msg);
        uiHandler.postDelayed(runSwtsCommand,5000);

    }

    private static class UIHandler extends Handler{

        private static final int OK = 1;
        private static final int STARTED = 2;
        private static final int READY = 3;
        private static final int ENJOY = 4;
        private static final int SWEETS_COMMAND = 5;
        private static final int SWEETS_READY = 6;


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case OK:
                    command.setText("Ok Sir, Food preprartion will be started in 1 sec");
                    break;
                case STARTED:
                    command.setText("Food prepration has been started");
                    break;
                case READY:
                    command.setText("Your food is ready");
                    break;
                case ENJOY:
                    command.setText("Please enjoy the food");
                    break;
                case SWEETS_COMMAND:
                    swtsCommand.setText("Your Sweets will be ready in 5 sec");
                    break;
                case SWEETS_READY:
                    swtsCommand.setText("Please enjoy the sweets");
                    break;

            }
        }
    }
}
