/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotdevices;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.util.Console;

import iotdevices.devices.oneWire.W1Device;

import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author luca
 */
public class Main {

    private static final int PIN_LED = 22; // PIN 15 = BCM 22
    private static final Console console = new Console();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        console.box("IoTDevices!");
        /*
         * Context pi4j = null;
         * try {
         * pi4j = Pi4J.newAutoContext();
         * new Main().run(pi4j);
         * } catch (InvocationTargetException e) {
         * console.println("Error: " + e.getTargetException().getMessage());
         * } catch (Exception e) {
         * console.println("Error: " + e.getMessage());
         * e.printStackTrace();
         * } finally {
         * if (pi4j != null) {
         * pi4j.shutdown();
         * }
         * }
         */
        new Main().run();

    }

    private void run() throws Exception {
        new W1Device("/sys/bus/w1/devices").listDevices();
    }

}
