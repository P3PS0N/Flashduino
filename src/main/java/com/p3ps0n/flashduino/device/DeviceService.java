package com.p3ps0n.flashduino.device;

import com.fazecast.jSerialComm.SerialPort;
import com.p3ps0n.flashduino.popup.PopupService;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class DeviceService {
    public enum MemType {
        FLASH,
        EEPROM
    }

    public enum Operation {
        READ,
        WRITE
    }

    private PopupService popup = new PopupService();
    private final String COMMAND_TEMPLATE = "./bin/avrdude.exe -C ./bin/avrdude.conf -V -p %s -c %s -b %s -P %s -D -U ";

    public DeviceService() {

    }

    public void run(Device device, SerialPort port, File file, MemType type, Operation operation) {
        if(port != null)  {
            if(file != null) {

                Alert confirmationPopup;
                String action;

                if(operation.equals(Operation.WRITE)) {
                    switch(type) {
                        case FLASH:
                            confirmationPopup = popup.getPopup(PopupService.Type.FLASH_WRITE_CONFIRMATION);
                            action = "flash:w:%s:a"; // flash memory, write operation, auto detect
                            break;

                        case EEPROM:
                            confirmationPopup = popup.getPopup(PopupService.Type.EEPROM_WRITE_CONFIRMATION);
                            action = "eeprom:w:%s:i"; // eeprom memory, write operation, intel hex
                            break;

                        default:
                            confirmationPopup = null;
                            action = "";
                    }

                } else {
                    switch(type) {
                        case FLASH:
                            action = "flash:r:%s:i";
                            break;

                        case EEPROM:
                            action = "eeprom:r:%s:i";
                            break;

                        default:
                            action = "";
                    }

                    confirmationPopup = popup.getPopup(PopupService.Type.READING_CONFIRMATION);

                }


                    Optional<ButtonType> result = confirmationPopup.showAndWait();

                    if (result.get() == ButtonType.OK) {


                        String command = String.format(COMMAND_TEMPLATE + action,
                                device.getId(), device.getProgrammer(), device.getBaudrate(), port.getSystemPortName(), file.getAbsoluteFile()
                        );

                        System.out.println(command);

                        try {
                            Process process = Runtime.getRuntime().exec(command);
                            process.waitFor();

                            if(process.exitValue() == 0) {
                                switch (operation) {
                                    case WRITE:
                                        popup.getPopup(PopupService.Type.WRITING_COMPLETE).showAndWait();
                                        break;

                                    case READ:
                                        popup.getPopup(PopupService.Type.READING_COMPLETE).showAndWait();
                                        break;
                                }

                            } else {
                                switch (operation) {
                                    case WRITE:
                                        popup.getPopup(PopupService.Type.WRITING_ERROR).showAndWait();
                                        break;

                                    case READ:
                                        popup.getPopup(PopupService.Type.READING_ERROR).showAndWait();
                                        break;
                                }

                            }

                        } catch (IOException ex) {
                            popup.getPopup(PopupService.Type.AVRDUDE_NOT_FOUND).showAndWait();
                        } catch (InterruptedException ex) {
                            popup.getPopup(PopupService.Type.AVRDUDE_ERROR).showAndWait();
                        }

                    }
            } else {
                popup.getPopup(PopupService.Type.FILE_NOT_SELECTED).showAndWait();
            }
        } else {
            popup.getPopup(PopupService.Type.DEVICES_NOT_FOUND).showAndWait();
        }
    }
}
