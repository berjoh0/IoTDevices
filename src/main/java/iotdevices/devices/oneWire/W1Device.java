package iotdevices.devices.oneWire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.pi4j.util.Console;

import iotserver.IoTServer;

public class W1Device {
    private String path;
    private static final Console console = new Console();

    public W1Device(String path) {
        this.path = path;
    }

    public List<String> listDevices() {
        List<String> devices = new ArrayList<String>();

        File[] files = new File(path).listFiles((dir, name) -> name.startsWith("28-"));

        int sensor = 30000;

        for (File file : files) {
            console.println(file.getName() + "/" + file.getAbsolutePath() + "="
                    + getW1DeviceTemperatureValue(file.getAbsolutePath() + "/w1_slave"));
            new IoTServer().sendToServer("http://mainraspberry.local/sensor/Sensor", "10000", "" + (sensor++),
                    getW1DeviceTemperatureValue(file.getAbsolutePath() + "/w1_slave"));
        }

        return devices;
    }

    private String getW1DeviceTemperatureValue(String device) {
        String retVal = "";

        try {

            File w1File = new File(device);
            BufferedReader br = new BufferedReader(new FileReader(w1File));

            String inLine;
            while ((inLine = br.readLine()) != null) {
                if (inLine.indexOf("t=") > -1) {
                    retVal = "" + (Double.parseDouble(inLine.substring(inLine.indexOf("t=") + 2)) / 1000);
                }
            }

            br.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return retVal;
    }
}
