/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arduino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author lokivava<oxigenium16@yandex.ru>
 */
public final class Boards
{
    public static class Board
    {
        public final List<String> strings;
        private Board(String ... strings)
        {
            this.strings = new ArrayList<String>();
            this.strings.addAll(Arrays.asList(strings));
        }
    }
    public static final Board MEGA = new Board(
            "BAUD_RATE = 115200", 
            "ARDUINO_VERSION = 167",
            "ARDUINO_MODEL = atmega2560",
            "ARDUINO_PROGRAMMER = wiring",
            "ARDUINO_PINS_DIR = ${ARDUINO_BASE_DIR}/hardware/arduino/avr/variants/mega",
            "",
            "ARDUINO_CORE_LIB = ${ARDUINO_BASE_DIR}/hardware/arduino/avr/libraries",
            "CORE_LIBS = Wire;Spi;SoftwareSerial;HID;EEPROM");
    public static final Board UNO = new Board(
            "BAUD_RATE = 115200", 
            "ARDUINO_VERSION = 167",
            "ARDUINO_MODEL = atmega328p",
            "ARDUINO_PROGRAMMER = arduino",
            "ARDUINO_PINS_DIR = ${ARDUINO_BASE_DIR}/hardware/arduino/avr/variants/standard",
            "",
            "ARDUINO_CORE_LIB = ${ARDUINO_BASE_DIR}/hardware/arduino/avr/libraries",
            "CORE_LIBS = Wire;Spi;SoftwareSerial;HID;EEPROM");
    public static final Board NANO = new Board(
            "BAUD_RATE = 57600", 
            "ARDUINO_VERSION = 167",
            "ARDUINO_MODEL = atmega328p",
            "ARDUINO_PROGRAMMER = arduino",
            "ARDUINO_PINS_DIR = ${ARDUINO_BASE_DIR}/hardware/arduino/avr/variants/standard",
            "",
            "ARDUINO_CORE_LIB = ${ARDUINO_BASE_DIR}/hardware/arduino/avr/libraries",
            "CORE_LIBS = Wire;Spi;SoftwareSerial;HID;EEPROM");
    public static Board getByName(String str)
    {
        switch(str)
        {
            case "Arduino Mega 2560":
                return MEGA;
            case "Arduino Nano":
                return NANO;
            default:
            case "Arduino Uno":
                return UNO;
        }
    }
}
