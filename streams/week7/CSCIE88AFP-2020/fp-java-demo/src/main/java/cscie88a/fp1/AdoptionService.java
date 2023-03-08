package cscie88a.fp1;

import cscie88a.basics4.ActionResult;

public class AdoptionService {

    public ActionResult tryToAdopt(IAdoptable someoneToAdopt) {
        if (someoneToAdopt.readyForAdoption()) {
            return ActionResult.SUCCESS;
        } else
            return ActionResult.FAILURE;
    }

}
