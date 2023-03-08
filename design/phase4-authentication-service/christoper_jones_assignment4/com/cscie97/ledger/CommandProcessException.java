package com.cscie97.ledger;

/*
The CommandProcessorException is returned from the CommandProcessor methods in response to an error condition. The
CommandProcessorException captures the command that was attempted and the reason for the failure. In the case where
commands are read from a file, the line number of the command should be included in the exception.
*/
public class CommandProcessException extends Throwable {

    // Command that was performed
    private String command;

    // Reason for the exception
    private String reason;

    // The line number of the command in the input file.
    private int lineNumber;

    // The CommandProcessorException is returned from the CommandProcessor methods in response to an error condition. The
    // CommandProcessorException captures the command that was attempted and the reason for the failure. In the case where
    // commands are read from a file, the line number of the command should be included in the exception.
    public CommandProcessException(String command, String reason, int lineNumber){
        this.command = command;
        this.reason = reason;
        this.lineNumber = lineNumber;
    }

    // Getter for the given command
    public String getCommand() {
        return this.command;
    }

    // Getter for the given reason
    public String getReason(){
        return this.reason;
    }

    // Getter for the line number
    public int getLineNumber(){
        return this.lineNumber;
    }
}
