package Test_App;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by sander on 3-11-2017.
 */
public class io implements SerialPortEventListener {
    SerialPort serialPort;
    Test_App.ui ui;
    private static final String PORT_NAMES[] = {
            "COM6", // Windows
    };

    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    public void initialize() {


        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }

    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine=input.readLine();
                System.out.println(inputLine);
                switch (inputLine) {
                    case "5":
                        ui.pin = " 5";
                        ui.X = 474;
                        break;
                    case "6":
                        ui.pin = " 6";
                        ui.X =460;
                        break;
                    case "7":
                        ui.pin = " 7";
                        ui.X =444;
                        break;
                    case "8":
                        ui.pin = " 8";
                        ui.X =420;
                        break;
                    case "9":
                        ui.pin = " 9";
                        ui.X =406;
                        break;
                }
                ui.repaint();
            } catch (Exception e) {

            }
        }

    }

    public io() throws Exception {
        this.initialize();
        Thread t=new Thread() {
            public void run() { }
        };
        t.start();
        System.out.println("IO Started");
    }
    public void setUI(Test_App.ui ui){
        this.ui = ui;
    }

}
