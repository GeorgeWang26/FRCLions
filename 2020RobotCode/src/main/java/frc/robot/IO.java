package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;

public class IO {

    private Joystick joystick = new Joystick(0);
    private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private CameraServer camServer = CameraServer.getInstance();

    private NetworkTableEntry tx = table.getEntry("tx");
    private NetworkTableEntry ty = table.getEntry("ty");
    // NetworkTableEntry ta = table.getEntry("ta");

    private double x, y;
    private boolean[] buttons = new boolean[13];
    private double xAng, yAng;

    public void initCamera() {


        camServer.startAutomaticCapture(0);
    }

    public void updateInput() {
        for(int i = 1; i < 13; i++){
            buttons[i] = joystick.getRawButton(i);
        }
        x = joystick.getX();
        y = -joystick.getY();

        if(x < 0.1 || x > -0.1){
            x = 0;
        }
        if(y < 0.1 || y > -0.1){
            y = 0;
        }
        
        x *= 0.3;
        y *= 0.8;

        updateLimelight();
    }

    public void updateLimelight() {
        xAng = tx.getDouble(0.0);
        yAng = ty.getDouble(0.0);
    }

    // public void initY() {
    //     yAng = ty.getDouble(0.0);
    // }

    public boolean getButton(int buttonNum){
        return buttons[buttonNum];
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getLimeX(){
        return xAng;
    }

    public double getLimeY(){
        return yAng;
    }

}