# Flashduino
Easily program your arduino board with flash &amp; EEPROM management support
## Getting started
To run application you'll need *avrdude* and in most cases *libusb0.dll*.
You can download it from here: [Avrdude](http://download.savannah.gnu.org/releases/avrdude/).
Place required files in */bin* catalog next to compiled jar package.
```
.
├── Flashduino.jar
├── bin
│   ├── avrdude.conf
│   ├── avrdude.exe
│   └── libusb0.dll
└── devices.json
```

## Devices list
You can simply add support for other microcontrollers by adding it to *devices.json*
```
...
{
  "name": "Displayname",
  "id": "AVR Part",
  "baudrate": "Overriding baudrate",
  "programmer": "Used programmer"
}
...
```

## Writing to EEPROM
If you're using *Optiboot* you can't read or write to eeprom directly. To use this feature you'll need to burn *ATmegaBOOT*.
To do this connect ISP Programmer to arduino and choose *LilyPad Arduino (ATmega328P)* board. Then press *Burn bootloader*. now you should have the ATmegaBOOT loaded.
