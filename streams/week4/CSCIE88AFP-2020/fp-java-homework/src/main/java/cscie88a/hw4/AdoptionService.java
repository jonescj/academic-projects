package cscie88a.hw4;

import cscie88a.hw2.ActionResult;

public class AdoptionService {

    public ActionResult tryToAdopt(IAdoptable someoneToAdopt) {
        if (someoneToAdopt.readyForAdoption()) {
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAILURE;
        }
    }

    public ActionResult tryToAdopt2(IAdoptable2 someoneToAdopt​, boolean healthCheckDone){
        if (someoneToAdopt.readyForAdoption(healthCheckDone)) {
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAILURE;
        }
    }
}
