package org.firstinspires.ftc.teamcode.hardware;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

@Config
public class Transfer extends Subsystem {
    public Servo liftRight;
    public Servo liftLeft;

    public Servo protector;

    public  static double liftRightPosition1 = 0.1;
    public  static double liftLeftPosition1 = 0.1;

    public  static double protectorPosition1 = 0.7;
    public  static double liftRightPosition2 = 0.8;
    public  static double liftLeftPosition2 = 0.8;
    public  static double protectorPosition2 = 0.4;

    public  static  double protectorDelaySeconds = 0.4;

    public  static  double liftDelaySeconds = 0.4;


    private  Transfer() {}

    public static Transfer INSTANCE = new Transfer();




    public void initialize() {
        liftRight = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "liftRight");
        liftLeft = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "liftLeft");
        protector = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "Protector");

        liftRight.setPosition(liftRightPosition1);
        liftLeft.setPosition(liftLeftPosition1);
        protector.setPosition(protectorPosition1);
    }

    public InstantCommand goToDefaultPosition() {
        return new InstantCommand(()-> {
            liftRight.setPosition(liftRightPosition1);
            liftLeft.setPosition(liftLeftPosition1);
            protector.setPosition(protectorPosition1);
        });
    }

    public Command transferBall() {
        // protector moves, wait, then kicker moves
        return new SequentialGroup(
                new InstantCommand(() -> protector.setPosition(protectorPosition2)),
                new Delay(protectorDelaySeconds),
                new InstantCommand(() -> liftRight.setPosition(liftRightPosition1)),
                new InstantCommand(() -> liftLeft.setPosition(liftLeftPosition2)),

                new Delay(liftDelaySeconds),
                goToDefaultPosition()
        );
    }




}
