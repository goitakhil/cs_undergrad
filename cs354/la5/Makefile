
LightSwitch.exe:
	xbuild LightSwitch.sln
	mv LightSwitch/bin/Debug/LightSwitch.exe LightSwitch.exe

clean:
	rm -rf LightSwitch/bin LightSwitch/obj LightSwitch.exe

run: LightSwitch.exe
	mono LightSwitch.exe

default: LightSwitch.exe

