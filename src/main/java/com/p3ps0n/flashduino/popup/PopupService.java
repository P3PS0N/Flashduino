package com.p3ps0n.flashduino.popup;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.DialogPane;

public class PopupService {
    public enum Type {
        FILE_NOT_SELECTED,
        PORT_NOT_SELECTED,
        DEVICES_NOT_FOUND,
        DEVICES_LIST_NOT_FOUND,
        AVRDUDE_NOT_FOUND,
        AVRDUDE_ERROR,
        FLASH_WRITE_CONFIRMATION,
        EEPROM_WRITE_CONFIRMATION,
        READING_CONFIRMATION,
        WRITING_COMPLETE,
        READING_COMPLETE,
        WRITING_ERROR,
        READING_ERROR

    }

    public Alert getPopup(PopupService.Type type) {
        Alert alert;

        String confirmationMsg = "Continue?";
        String errorMsg = "An error occurred";

        switch (type) {
            case FILE_NOT_SELECTED:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("Select file first");
                break;

            case PORT_NOT_SELECTED:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("Select COM Port first");
                break;

            case DEVICES_NOT_FOUND:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("Devices not found. Check your USB connection");
                break;

            case DEVICES_LIST_NOT_FOUND:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("devices.json not found");
                break;

            case AVRDUDE_NOT_FOUND:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("avrdude.exe not found");
                break;

            case AVRDUDE_ERROR:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("avrdude internal error");
                break;

            case FLASH_WRITE_CONFIRMATION:
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Write to flash");
                alert.setHeaderText(confirmationMsg);
                break;

            case EEPROM_WRITE_CONFIRMATION:
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Write to EEPROM");
                alert.setHeaderText(confirmationMsg);
                break;

            case READING_CONFIRMATION:
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Read memory");
                alert.setHeaderText(confirmationMsg);
                break;

            case WRITING_COMPLETE:
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Writing completed");
                alert.setHeaderText("Writing completed successfully");
                break;

            case READING_COMPLETE:
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Reading completed");
                alert.setHeaderText("Reading completed successfully");
                break;

            case WRITING_ERROR:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("Unknown error occurred while writing");
                break;

            case READING_ERROR:
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(errorMsg);
                alert.setHeaderText("Unknown error occurred while reading");
                break;

            default:
                alert = new Alert(AlertType.NONE);
        }

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        return alert;
    }

}
