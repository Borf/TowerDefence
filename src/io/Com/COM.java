package io.Com;

import game.Game;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import io.Buttons.Button;
import util.Reflection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by sander on 3-11-2017.
 */
public class COM implements SerialPortEventListener {
    SerialPort serialPort;
    Game game;
    Class<?>[] Buttons;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
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
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        setButtons();
    }

    private void setButtons(){
        Buttons = Reflection.getClasses("io.Buttons");
        for (int i = 0; i < Buttons.length; i++) {

            try{
                Button temp = (Button)Buttons[i].newInstance();
                switch (temp.PinNumber){
                    case 5:
                        button1 = temp;
                        button1.setCom(this);
                        break;
                    case 6:
                        button2 = temp;
                        button2.setCom(this);
                        break;
                    case 7:
                        button3 = temp;
                        button3.setCom(this);
                        break;
                    case 8:
                        button4 = temp;
                        button4.setCom(this);
                        break;
                    case 9:
                        button5 = temp;
                        button5.setCom(this);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }


    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine=input.readLine();
                switch (inputLine) {
                    case "5":
                        button1.effect();
                        break;
                    case "6":
                        button2.effect();
                        break;
                    case "7":
                        button3.effect();
                        break;
                    case "8":
                        button4.effect();
                        break;
                    case "9":
                        button5.effect();
                        break;
                }
            } catch (Exception e) {

            }
        }

    }

    public COM() throws Exception {
        this.initialize();
        Thread t=new Thread() {
            public void run() { }
        };
        t.start();
        System.out.println("Started");
    }
    public void setGame(Game game){
        this.game = game;
    }
    public Game getGame(){
        return this.game;
    }
}
