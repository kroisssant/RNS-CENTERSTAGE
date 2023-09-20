package org.firstinspires.ftc.teamcode.Libs;

public interface STATE {
    default void init() {};
    default void periodic() {};
    default boolean isFinnished() {return false;}
}
