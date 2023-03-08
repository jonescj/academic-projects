package cscie97.smartcity.controller;

import java.net.URISyntaxException;

// The implementation of the Command interface
public interface Command {
    public void execute() throws URISyntaxException;
}
