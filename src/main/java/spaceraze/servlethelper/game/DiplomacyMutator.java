package spaceraze.servlethelper.game;

import spaceraze.world.diplomacy.DiplomacyState;
import spaceraze.world.enums.DiplomacyGameType;

import java.util.List;

public class DiplomacyMutator {

    private DiplomacyMutator(){}

    public static void resetDiplomacyStates(List<DiplomacyState> diplomacyStates){
        for (DiplomacyState aState : diplomacyStates) {
            aState.setChangedThisTurn(false);
        }
    }

    public static void setPlayerDiplomacy(DiplomacyGameType diplomacyGameType, List<DiplomacyState> diplomacyStates){
        for (DiplomacyState dipState : diplomacyStates) {
            dipState.modifyDueToGameType(diplomacyGameType);
        }
    }


}
