package com.p3ps0n.flashduino.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fazecast.jSerialComm.SerialPort;
import com.p3ps0n.flashduino.App;
import com.p3ps0n.flashduino.device.Device;
import com.p3ps0n.flashduino.device.DeviceService;
import com.p3ps0n.flashduino.popup.PopupService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MainController {
    @FXML AnchorPane mainPane;
    @FXML ComboBox<String> portBox, devBox;
    @FXML Label labelId, labelBaudrate, labelProgrammer,
            labelFlashFilename, labelFlashSize, labelFlashDate,
            labelReadFilename, labelAbout;

    private File selectedFile, savedFile;
    private FileChooser fChooser = new FileChooser();

    private PopupService popup = new PopupService();
    private DeviceService deviceService = new DeviceService();

    private List<Device> devices;
    private Device device;

    private List<SerialPort> ports;
    private SerialPort port;

    @FXML
    private void initialize() throws IOException {
        Properties prop = new Properties();
        prop.load(this.getClass().getResourceAsStream("/project.properties"));
        labelAbout.setText("Flashduino " + prop.getProperty("version"));

        refreshPorts();

        /*  Read devices.json and assign devices to combobox    */
        try {
            File jsonFile = new File("devices.json");
            devices = new ObjectMapper().readValue(jsonFile, new TypeReference<List<Device>>(){});
            for(Device d : devices) {
                devBox.getItems().add(d.getName());
            }

        } catch (IOException ex) {
            popup.getPopup(PopupService.Type.DEVICES_LIST_NOT_FOUND).showAndWait();
            Platform.exit();
        }

        devBox.getSelectionModel().select(0);

        refreshDevice();

        fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hex files (*.hex)", "*.hex"));
        fChooser.setInitialDirectory(new File("./"));
    }

    @FXML
    private void refreshPorts() {
        ports = Arrays.asList(SerialPort.getCommPorts());
        portBox.getItems().clear();

        if(ports.isEmpty()) {
            portBox.getItems().add("No devices found");
            port = null;

        } else {
            for (int i = 0; i < ports.size(); i++) {
                portBox.getItems().add(i, ports.get(i).getDescriptivePortName());
            }

            port = ports.get(0);
        }

        portBox.getSelectionModel().select(0);
    }

    @FXML
    private void refreshDevice() {
        device = devices.get(devBox.getSelectionModel().getSelectedIndex());

        labelId.setText(device.getId());
        labelBaudrate.setText(device.getBaudrate());
        labelProgrammer.setText(device.getProgrammer());
    }

    @FXML
    private void selectFile() {
        selectedFile = fChooser.showOpenDialog(mainPane.getScene().getWindow());

        if(selectedFile != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            labelFlashFilename.setText(selectedFile.getName());
            labelFlashSize.setText(String.format("%.2f kB", selectedFile.length() / 1024.0));
            labelFlashDate.setText(sdf.format(selectedFile.lastModified()));
        } else {
            labelFlashFilename.setText("");
            labelFlashSize.setText("");
            labelFlashDate.setText("");
        }
    }

    @FXML
    private void saveFile() {
        savedFile = fChooser.showSaveDialog(mainPane.getScene().getWindow());

        if(savedFile != null) {
            labelReadFilename.setText(savedFile.getName());
        } else {
            labelReadFilename.setText("");
        }
    }


    @FXML
    private void writeFlash() {
        deviceService.run(device, port, selectedFile, DeviceService.MemType.FLASH, DeviceService.Operation.WRITE);
    }

    @FXML
    private void writeEEPROM() {
        deviceService.run(device, port, selectedFile, DeviceService.MemType.EEPROM, DeviceService.Operation.WRITE);
    }

    @FXML
    private void readFlash() {
        deviceService.run(device, port, savedFile, DeviceService.MemType.FLASH, DeviceService.Operation.READ);
    }

    @FXML
    private void readEEPROM() {
        deviceService.run(device, port, savedFile, DeviceService.MemType.EEPROM, DeviceService.Operation.READ);
    }

    @FXML
    private void openAbout() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/about.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(parent));
        stage.getIcons().add(App.getIcon());
        stage.setTitle("About");
        stage.setResizable(false);
        stage.showAndWait();
    }

}
