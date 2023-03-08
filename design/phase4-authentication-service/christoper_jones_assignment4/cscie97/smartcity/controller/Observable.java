package cscie97.smartcity.controller;

import com.cscie97.ledger.LedgerException;
import cscie97.smartcity.authentication.AuthenticationException;

import java.net.URISyntaxException;

/* The observable class is an interface that allows objects to be monitoring and send updates to a controller*/
public interface Observable {
    public void attach(Controller controller) throws AuthenticationException;
    public void detach(Controller controller);
    public void notify(Controller controller) throws LedgerException, URISyntaxException, ControllerException;
}
