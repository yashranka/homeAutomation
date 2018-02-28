# homeAutomation

The GPIO pins of Jetson Tx2 are used to control the devices also the switches are connected to them. Firstly the python file is made to control the gpio pins of board using os library of python. There are 3 python files:

1. Python file to export the gpio pins which we are using

                    Two pins for each electronic device, one as input for taking the input of the manual switch connected to it and other for the relay (through which actual device is connected) as output. This file should be executed first.

2. Python file for controlling devices for the data got from website or app

                    In this file, MQTT is used as a server. When the user switches the button ON for a particular device, 1 or 0 is published and then the python file subscribes to same topic to switch that device connected to gpio pin. Also using voice recognizer, 1 or 0 is published and then subscribed in the file to that topic.

3. Python file for controlling devices using manual switch

                    MQTT is also used in this file. The switch is connected to gpio pin(used as input), if the person switches On or Off, the value of the pin changes and according the relay turns On or Off respectively. Also for the same switching we need to change the state of the button on website (and app), so for that we publish 1 or 0 depending the pin is low or high respectively by publishing on a topic which is then subscribed by the website to toggle the button.

The last 2 python files should be kept running on the machine 24/7.

The website or an Android app is used to control our home. Also the app has the a feature to give commands using our voice & control the devices. Google speech to text is used to do this.

The YouTube link for the video is
https://youtu.be/VeYHimM9GVQ
