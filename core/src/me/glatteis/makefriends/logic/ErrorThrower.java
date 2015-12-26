package me.glatteis.makefriends.logic;

import me.glatteis.makefriends.objects.Robot;

/**
 * Created by Linus on 21.12.2015.
 */
public class ErrorThrower {

    private CodeInterpreter interpreter;
    private int line;
    private Robot robot;

    public ErrorThrower(CodeInterpreter interpreter, Robot robot) {
        this.interpreter = interpreter;
        this.robot = robot;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void throwError(String reason) {
        reason += " [Line " + line + 1 + "]";
        robot.saySomething(reason, true);
        interpreter.finish(true);
    }

}
