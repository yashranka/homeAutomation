#Python script to change the value on the websit/appe according to the manual switch and also control the relay with manual switch
import os
import paho.mqtt.client as mqttc

os.chdir("/sys/class/gpio")
cli = mqttc.Client("gpio/in")
cli.connect("broker.mqttdashboard.com",port=1883)
pv1 = -1
pv2 = -1

#while loop to publish the change from manual switch onto website/app and to switch the relays accordingly
while True:
	file1 = open("/sys/class/gpio/gpio298/value","r")
	cv1 = int(file1.read())
	file1.close()

        file2 = open("/sys/class/gpio/gpio388/value","r")
        cv2 = int(file2.read())
        file2.close()

	if(cv1!=pv1):
		pv1 = cv1
 		cli.publish("drawingRoom/led1_manual",cv1)
		os.chdir("gpio398")
		if(cv1==1):
			os.system("echo 1 > value")
		else:
			os.system("echo 0 > value")
		os.chdir("..")

	if(cv2!=pv2):
                pv2 = cv2
                cli.publish("drawingRoom/led2_manual",cv2)
                os.chdir("gpio481")
                if(cv2==1):
                        os.system("echo 1 > value")
                else:
                        os.system("echo 0 > value")
                os.chdir("..")

