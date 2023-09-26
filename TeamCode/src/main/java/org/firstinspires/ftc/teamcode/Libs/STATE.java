package org.firstinspires.ftc.teamcode.Libs;

import com.qualcomm.robotcore.util.ElapsedTime;

public interface STATE {
    int loopIndexer = 0;
    long startTime = 0;
    long endTime = 0;
    long allocatedTime = 0;
    String name = null;
    default void init() {};
    default int periodic(int loopIndexer) {return loopIndexer;};
    default boolean isFinnished() {return false;}

}
