# Flashduino
Easily program your arduino board with flash &amp; EEPROM management support
## Getting started
To run application you'll need *avrdude* and in most cases *libusb0.dll*.
You can download it from here: [Avrdude](http://download.savannah.gnu.org/releases/avrdude/)
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
  "name": "MCU Displayname",
  "id": "AVR Part",
  "baudrate": "Overriding baudrate",
  "programmer": "Used programmer"
}
...
```
