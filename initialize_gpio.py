#Python script to export(initialize) the pins(input and output pins)
import os

os.chdir("/sys/class/gpio")

#exporting pins
os.system("echo 398 > export")
os.system("echo 481 > export")
os.system("echo 298 > export")
os.system("echo 388 > export")

#o/p pins
os.chdir("gpio398")
os.system("echo out > direction")
os.chdir("..")

os.chdir("gpio481")
os.system("echo out > direction")
os.chdir("..")

#i/p pins
os.chdir("gpio388")
os.system("echo in > direction")
os.chdir("..")

os.chdir("gpio298")
os.system("echo in > direction")
os.chdir("..")
