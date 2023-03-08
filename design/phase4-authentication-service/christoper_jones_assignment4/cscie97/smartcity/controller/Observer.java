package cscie97.smartcity.controller;

import com.cscie97.ledger.LedgerException;

import java.net.URISyntaxException;

public interface Observer{
    public void update() throws URISyntaxException, ControllerException, LedgerException;
}
