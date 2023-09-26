package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.advanced.States.Trajectorys;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class StateMachine{
        ArrayList<STATE> states = new ArrayList<>();
        ArrayList<STATE> emStates = new ArrayList<>();
        STATE currentState;
        STATE nextState;
        Trajectorys trajectorys;

        SampleMecanumDrive drive;
        ElapsedTime timer;
        boolean emergency = false;
        int loopIndex;

        public StateMachine(ElapsedTime time, SampleMecanumDrive drive, Trajectorys trajectorys) {
                this.timer = time;
                this.drive = drive;
                this.trajectorys = trajectorys;
                this.trajectorys.init();
        }


        public void addState(boolean em, STATE state) {
                if(em) {
                        emStates.add(state);
                }
                else {
                        states.add(state);
                }
                state.init(drive, trajectorys);

        }

        public void periodic() {
                loopIndex = currentState.periodic(loopIndex);
                if(timer.milliseconds() > currentState.endTime || timer.milliseconds() > currentState.startTime) {
                        try {
                                currentState = emStates.get(states.indexOf(currentState));
                        } catch (Exception e) {
                                currentState = emStates.get(emStates.indexOf(currentState));
                        }
                }
                else {
                        if(currentState.isFinnished()) {
                                currentState = states.get(states.indexOf(currentState) + 1);
                                loopIndex = 0;
                        }
                }

        }
}
