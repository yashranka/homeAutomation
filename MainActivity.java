package iotproject.com.mqttpublishing;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webview);

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.clearCache(true);
        myWebView.clearHistory();
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        myWebView.loadUrl("https://controlbox.eu-gb.mybluemix.net/ui/#");

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.myFAB);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak");

                try {
                    startActivityForResult(i, 100);
                }catch (Exception e){
                    Log.d("test","exception");
                }
            }
        });



        /*
        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqttdashboard.com:1883",
                        clientId);

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("test", "onSuccess");
                    try{

                        String topic = "home/test";
                        String payload = "the payload";
                        byte[] encodedPayload = new byte[0];
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        Log.d("test", message.toString());
                        client.publish(topic, message);
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("test", "onFailure");

                }
            });
        } catch (MqttException e) {

            Log.d("test","catch");
            e.printStackTrace();
        }

        String topic = "home/test";
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        */

    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.d("url", url);
            return false;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result","there");
        switch(requestCode){

            case 100 : Log.d("result","here");
                if (data != null&&resultCode == RESULT_OK) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Log.d("result",result.get(0));


                    String clientId = MqttClient.generateClientId();
                    final MqttAndroidClient client =
                            new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqttdashboard.com:1883",
                                    clientId);



                    if(result.get(0).equalsIgnoreCase("drawing room LED 1 on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led1_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        //message.setRetained(true);
                                        Log.d("drawing room led 1 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 1 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 1 off") || result.get(0).equalsIgnoreCase("drawing room LED 1 of") || result.get(0).equalsIgnoreCase("drawing room LED one off") || result.get(0).equalsIgnoreCase("drawing room LED one of") ){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led1_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 1 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 1 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 2 on") || result.get(0).equalsIgnoreCase("drawing room LED to on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led2_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 2 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 2 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 2 off") || result.get(0).equalsIgnoreCase("drawing room LED 2 of") || result.get(0).equalsIgnoreCase("drawing room LED to off") || result.get(0).equalsIgnoreCase("drawing room LED to of")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led2_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 2 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 2 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 3 on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led3_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 3 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 3 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 3 off") || result.get(0).equalsIgnoreCase("drawing room LED 3 of")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led3_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 3 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 3 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 4 on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led4_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 4 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 4 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("drawing room LED 4 off") || result.get(0).equalsIgnoreCase("drawing room LED 4 of")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "drawingRoom/led4_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("drawing room led 4 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Drawing Room Led 4 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("kitchen LED on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "kitchen/led_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("kitchen led test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Kitchen Led turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("kitchen LED off") || result.get(0).equalsIgnoreCase("kitchen LED of")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "kitchen/led_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("kitchen led test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Kitchen Led turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("bathroom LED on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bathroom/led_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bathroom led test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bathroom Led turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("bathroom LED off") || result.get(0).equalsIgnoreCase("bathroom LED  of")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bathroom/led_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bathroom led test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bathroom Led turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("bedroom 1 LED 1 on") || result.get(0).equalsIgnoreCase("bedroom one LED one off") || result.get(0).equalsIgnoreCase("bedroom one LED one of")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom1/led1_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 1 led 1 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 1 Led 1 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("bedroom 1 LED 1 off") || result.get(0).equalsIgnoreCase("bedroom 1 LED 1 of") || result.get(0).equalsIgnoreCase("bedroom one LED one of") || result.get(0).equalsIgnoreCase("bedroom 1 LED one off") ){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom1/led1_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 1 led 1 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 1 Led 1 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("bedroom 1 LED 2 on") || result.get(0).equalsIgnoreCase("bedroom 1 LED to on") || result.get(0).equalsIgnoreCase("bedroom one LED to on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom1/led2_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 1 led 2 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 1 Led 2 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("bedroom 1 LED 2 off") || result.get(0).equalsIgnoreCase("bedroom 1 LED to off") || result.get(0).equalsIgnoreCase("bedroom 1 LED to of") || result.get(0).equalsIgnoreCase("bedroom one LED to off")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom1/led2_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 1 led 2 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 1 Led 2 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("bedroom 2 LED 1 on") || result.get(0).equalsIgnoreCase("bedroom to LED 1 on") || result.get(0).equalsIgnoreCase("bedroom to LED one on")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom2/led1_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 2 led 1 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 2 Led 1 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }





                    }else if(result.get(0).equalsIgnoreCase("bedroom 2 LED 1 off") || result.get(0).equalsIgnoreCase("bedroom 2 LED 1 of") || result.get(0).equalsIgnoreCase("bedroom to LED one of") || result.get(0).equalsIgnoreCase("bedroom to LED one off")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom2/led1_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 2 led 1 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 2 Led 1 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }else if(result.get(0).equalsIgnoreCase("bedroom 2 LED 2 on") || result.get(0).equalsIgnoreCase("bedroom to LED to on") || result.get(0).equalsIgnoreCase("bedroom 2 LED to on")) {

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try {

                                        String topic = "bedroom2/led2_stt";
                                        String payload = "1";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 2 led 2 test", message.toString() + "test");
                                        Toast.makeText(MainActivity.this, "Bedroom 2 Led 2 turned ON", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test", "catch");
                            e.printStackTrace();
                        }




                    }else if(result.get(0).equalsIgnoreCase("bedroom 2 LED 2 off") || result.get(0).equalsIgnoreCase("bedroom 2 LED to of") || result.get(0).equalsIgnoreCase("bedroom 2 LED to off")){

                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "bedroom2/led2_stt";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("bedroom 2 led 2 test", message.toString()+"test");
                                        Toast.makeText(MainActivity.this, "Bedroom 2 Led 2 turned OFF", Toast.LENGTH_SHORT).show();
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }



                    /*else if(result.get(0).equals("drawing room lights off")){
                        Log.d("test","light off");
                        try {
                            MqttConnectOptions options = new MqttConnectOptions();
                            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
                            IMqttToken token = client.connect(options);
                            token.setActionCallback(new IMqttActionListener() {
                                @Override
                                public void onSuccess(IMqttToken asyncActionToken) {
                                    // We are connected
                                    Log.d("test", "onSuccess");
                                    try{

                                        String topic = "draw_room/light";
                                        String payload = "0";
                                        byte[] encodedPayload = new byte[0];
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        Log.d("lights off test", message.toString());
                                        Toast.makeText(MainActivity.this, "Drawing Room Light turned OFF", Toast.LENGTH_SHORT).show();

                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                    // Something went wrong e.g. connection timeout or firewall problems
                                    Log.d("test", "onFailure");

                                }
                            });
                        } catch (MqttException e) {

                            Log.d("test","catch");
                            e.printStackTrace();
                        }


                    }*/
                    else{
                        Toast.makeText(this, "Sorry we did'nt get you", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }
}
