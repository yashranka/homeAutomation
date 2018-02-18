#Python script used to control relays using the website/app and also via voice commands(only available on app)

import os
import paho.mqtt.client as mqttc

#change directory to the gpio directory
os.chdir("/sys/class/gpio")

#on receiving the message on the subscribed topic call this function
def on_message_one(client,userdata,message):
	if((message.payload=="1" and message.topic=="drawingRoom/led1_website") or (message.payload=="1" and message.topic=="drawingRoom/led1_stt")):
		os.chdir("gpio398")
		os.system("echo 0 > value")
		os.chdir("..")

	elif((message.payload=="0" and message.topic=="drawingRoom/led1_website") or (message.payload=="0" and message.topic=="drawingRoom/led1_stt")):
		os.chdir("gpio398")
                os.system("echo 1 > value")
                os.chdir("..")

	elif((message.payload=="1" and message.topic=="drawingRoom/led2_website") or (message.payload=="1" and message.topic=="drawingRoom/led2_stt")):
                os.chdir("gpio481")
                os.system("echo 0 > value")
                os.chdir("..")

        elif((message.payload=="0" and message.topic=="drawingRoom/led2_website") or (message.payload=="0" and message.topic=="drawingRoom/led2_stt")):
                os.chdir("gpio481")
                os.system("echo 1 > value")
                os.chdir("..")

#mqqt client
client1 =  mqttc.Client("gpio")
client1.connect("broker.mqttdashboard.com",port=1883)
client1.subscribe([("drawingRoom/led1_website",2),("drawingRoom/led2_website",2),("drawingRoom/led1_stt",2),("drawingRoom/led2_stt",2)])
client1.on_message = on_message_one
client1.loop_forever()

#/led_website is used to subscribe to the changes on the website
#and /led_stt is used to subscribe to the changes due to voice commands
