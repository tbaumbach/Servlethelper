package spaceraze.servlethelper.game;

import spaceraze.util.general.Logger;
import spaceraze.world.Galaxy;
import spaceraze.world.Planet;
import spaceraze.world.Player;
import spaceraze.world.diplomacy.*;
import spaceraze.world.enums.DiplomacyIconState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DiplomacyPureFunctions {

    private DiplomacyPureFunctions(){}

    public static List<DiplomacyState> getDiplomacyStates(Player aPlayer, List<DiplomacyState> diplomacyStates){
        List<DiplomacyState> foundStates = new ArrayList<>();
        for (DiplomacyState aState : diplomacyStates) {
            if (aState.isPlayer(aPlayer)){
                foundStates.add(aState);
            }
        }
        return foundStates;
    }

    public static DiplomacyState getDiplomacyState(Player player1, Player player2, List<DiplomacyState> diplomacyStates){
        DiplomacyState foundState = null;
        Logger.finer("getDiplomacyState: " + player1 + " & " + player2);
        int counter = 0;
        while ((foundState == null) & (counter < diplomacyStates.size())){
            DiplomacyState tmpState = diplomacyStates.get(counter);
            Logger.finer("tmpState: " + tmpState);
            if (tmpState.isPlayers(player1,player2)){
                foundState = tmpState;
            }else{
                counter++;
            }
        }
        Logger.finer("player1, player2: " + player1 + ", " + player2 + " foundState: " + foundState);
        return foundState;
    }

    public static boolean hostileDuelists(Player player1, Player player2, Planet planet, List<DiplomacyState> diplomacyStates){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, diplomacyStates);
            if (planet.isPlanetOwner(player1) | planet.isPlanetOwner(player2)){
                if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.CEASE_FIRE)){ // ewar, war, cease fire
                    hostile = true;
                }
            }else{ // annan spelares eller neutral planet
                if (state.getCurrentLevel().isLower(DiplomacyLevel.CEASE_FIRE)){ // ewar, war
                    hostile = true;
                }
            }
        }
        return hostile;
    }

    public static boolean hostileCivilians(Player player1, Player player2, List<DiplomacyState> diplomacyStates){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, diplomacyStates);
            if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.WAR)){ // ewar, war
                hostile = true;
            }
        }
        return hostile;
    }

    public static boolean friendlyCivilians(Player player1, Player player2, List<DiplomacyState> diplomacyStates){
        boolean friendly = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, diplomacyStates);
            if (state.getCurrentLevel().isHigherOrEqual(DiplomacyLevel.ALLIANCE)){ // conf, lord, vassal, alliance
                friendly = true;
            }
        }
        return friendly;
    }

    public static boolean friendlyTraders(Player player1, Player player2, List<DiplomacyState> diplomacyStates){
        boolean friendly = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, diplomacyStates);
            if (state.getCurrentLevel().isHigherOrEqual(DiplomacyLevel.PEACE)){ // conf, lord, vassal, alliance, peace
                friendly = true;
            }
        }
        return friendly;
    }

    public static boolean friendlySpaceports(Player player1, Player player2, List<DiplomacyState> diplomacyStates){
        boolean friendly = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, diplomacyStates);
            if (state.getCurrentLevel().isHigherOrEqual(DiplomacyLevel.ALLIANCE)){ // conf, lord, vassal, alliance
                friendly = true;
            }
        }else{ // always friendly to same player
            friendly = true;
        }
        return friendly;
    }

    public boolean hostileCapitals(Player player1, Player player2, List<DiplomacyState> diplomacyStates){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, diplomacyStates);
            if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.WAR)){ // ewar, war
                hostile = true;
            }
        }
        return hostile;
    }

    public static boolean hostileBesiege(Player playerPlanet, Player playerTaskForce, List<DiplomacyState> diplomacyStates){
        Logger.finer("hostileBesiege playerPlanet: " + playerPlanet.getName() + " playerTaskForce: " + playerTaskForce.getName());
        boolean hostile = false;
        DiplomacyState state = getDiplomacyState(playerPlanet, playerTaskForce, diplomacyStates);
        Logger.finer("hostile: " + state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.WAR));
        if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.WAR)){ // ewar, war
            hostile = true;
        }
        return hostile;
    }

    public static boolean hostileInfestator(Player infPlayer, Planet planet, Galaxy galaxy){
        boolean hostile = false;
        if (!planet.isPlayerPlanet()){ // neutral planet
            hostile = true;
        }else{
            if (infPlayer != planet.getPlayerInControl()){
                DiplomacyState state = getDiplomacyState(infPlayer, planet.getPlayerInControl(), galaxy.getDiplomacyStates());
                if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.CEASE_FIRE)){ // ewar, war, cease fire
                    hostile = true;
                }
            }
        }
        return hostile;
    }

    public static boolean hostileCounterSpies(Player player1, Player player2, Galaxy galaxy){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, galaxy.getDiplomacyStates());
            Logger.fine("state: " + state);
            if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.CEASE_FIRE)){ // ewar, war, cease fire
                hostile = true;
            }
        }
        return hostile;
    }

    public static boolean hostileExterminator(Player player1, Player player2, Galaxy galaxy){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, galaxy.getDiplomacyStates());
            if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.CEASE_FIRE)){ // ewar, war, cease fire
                hostile = true;
            }
        }
        return hostile;
    }

    public static boolean hostileAssassin(Player player1, Player player2, Galaxy galaxy){
        boolean hostile = false;
        if (player1 != player2){
            DiplomacyState state = getDiplomacyState(player1, player2, galaxy.getDiplomacyStates());
            if (state.getCurrentLevel().isLowerOrEqual(DiplomacyLevel.CEASE_FIRE)){ // ewar, war, cease fire
                hostile = true;
            }
        }
        return hostile;
    }

    /**
     * Returns null if maybeVassalPlayer is not a vassal
     * @param maybeVassalPlayer
     * @return
     */
    public static DiplomacyState isVassal(Player maybeVassalPlayer, Galaxy galaxy){
        DiplomacyState lordState = null;
        for (Player aPlayer : galaxy.players) {
            if (maybeVassalPlayer != aPlayer){
                DiplomacyState aState = getDiplomacyState(maybeVassalPlayer,aPlayer, galaxy.getDiplomacyStates());
                if ((aState.getCurrentLevel() == DiplomacyLevel.LORD) && (aState.getLord() == aPlayer)){
                    lordState = aState;
                }
            }
        }
        return lordState;
    }

    public static List<Player> getVassalPlayers(Player lordPlayer, Galaxy galaxy){
        List<Player> tempVassalPlayers = new LinkedList<>(); // all players in conf with player1
        for (Player aPlayer : galaxy.players) {
            if (lordPlayer != aPlayer){
                DiplomacyState aState = getDiplomacyState(lordPlayer,aPlayer, galaxy.getDiplomacyStates());
                if ((aState.getCurrentLevel() == DiplomacyLevel.LORD) && (aState.getLord() == lordPlayer)){
                    tempVassalPlayers.add(aPlayer);
                }
            }
        }
        return tempVassalPlayers;
    }

    public static List<DiplomacyState> getVassalStates(Player lordPlayer, Galaxy galaxy){
        List<DiplomacyState> vassalStates = null;
        List<Player> tempVassalPlayers = getVassalPlayers(lordPlayer, galaxy);
        if (tempVassalPlayers.size() > 0){
            vassalStates = new LinkedList<DiplomacyState>();
            for (Player vassalPlayer : tempVassalPlayers) {
                vassalStates.add(getDiplomacyState(lordPlayer, vassalPlayer, galaxy.getDiplomacyStates()));
            }
        }
        return vassalStates;
    }

    public static List<Player> getConfederacyPlayers(Player thePlayer, Galaxy galaxy){
        List<Player> tempConfPlayers = new LinkedList<Player>(); // all players in conf with player1
        for (Player aPlayer : galaxy.getPlayers()) {
            if (thePlayer != aPlayer){
                DiplomacyState aState = getDiplomacyState(thePlayer,aPlayer, galaxy.getDiplomacyStates());
                if (aState.getCurrentLevel() == DiplomacyLevel.CONFEDERACY){
                    tempConfPlayers.add(aPlayer);
                }
            }
        }
        return tempConfPlayers;
    }

    public static boolean checkAllianceWithAllInConfederacy(Player thePlayer, Player otherPlayer, Galaxy galaxy){
        boolean allAlliance = true;
        List<Player> confPlayers = getConfederacyPlayers(otherPlayer, galaxy);
        if (confPlayers.size() > 0){
            Logger.fine("confPlayers.size(): " + confPlayers.size());
            for (Player confPlayer : confPlayers) {
                Logger.fine("confPlayer: " + confPlayer);
                DiplomacyState state = getDiplomacyState(confPlayer, thePlayer, galaxy.getDiplomacyStates());
                if (state.getCurrentLevel() != DiplomacyLevel.ALLIANCE){
                    Logger.fine("Not alliance");
                    allAlliance = false;
                }
            }
        }
        return allAlliance;
    }

    /**
     * Returns true if the players have one of the levels in their current state
     */
    public static boolean isDiplomacyLevel(Galaxy galaxy, Player player1, Player player2, DiplomacyLevel... levels){
        boolean found = false;
        DiplomacyState state = getDiplomacyState(player1,player2, galaxy.getDiplomacyStates());
        DiplomacyLevel level = state.getCurrentLevel();
        for (DiplomacyLevel aLevel : levels) {
            if (level == aLevel){
                found = true;
            }
        }
        return found;
    }

    public static DiplomacyIconState getIconState(DiplomacyState diplomacyState, DiplomacyLevel iconLevel, Player thePlayer){
        Logger.finest("getIconState, iconLevel: " + iconLevel + ", thePlayer: " + thePlayer);
        DiplomacyIconState tmpIconState = null;
        Player otherPlayer = diplomacyState.getOtherPlayer(thePlayer);
        Logger.finest("otherPlayer: " + otherPlayer);
        DiplomacyOffer ownOffer = thePlayer.getOrders().findDiplomacyOffer(otherPlayer); // offers som man sj�lv har gjort detta drag
        DiplomacyOffer othersOffer = thePlayer.findDiplomacyOffer(otherPlayer); // offers som n�gon annan gjorde f�rra draget (och som man kan svara p�)
        DiplomacyChange aChange = thePlayer.getOrders().findDiplomacyChange(otherPlayer); // changes som man sj�lv har gjort detta drag
        boolean ownOfferExist = false;
        if (ownOffer != null){
            ownOfferExist = ownOffer.isLevel(iconLevel);
        }
        boolean othersOfferExist = false;
        if (othersOffer != null){
            othersOfferExist = othersOffer.isLevel(iconLevel);
        }
        boolean changeExist = false;
        if (aChange != null){
            changeExist = aChange.isLevel(iconLevel);
        }
        boolean isVassal = isVassal(thePlayer, thePlayer.getGalaxy()) != null;
        boolean isLord = getVassalPlayers(thePlayer, thePlayer.getGalaxy()).size() > 0;
        boolean isInConf = getConfederacyPlayers(thePlayer, thePlayer.getGalaxy()).size() > 0;
        // set icon state
        if (otherPlayer.isDefeated()){
            tmpIconState = DiplomacyIconState.DISABLED;
        }else
            // vid DiplomacyLevel.VASSAL måste man även kolla på om det finnns några
            if (iconLevel == DiplomacyLevel.LORD){
                // Kan påverkas av
                // - ownOffer på VASSAL
                // - aChange till VASSAL
                // - otherOffer på LORD
                boolean ownVassalOfferExist = false;
                if (ownOffer != null){
                    ownVassalOfferExist = ownOffer.isLevel(DiplomacyLevel.VASSAL);
                }
                boolean changeVassalExist = false;
                if (aChange != null){
                    changeVassalExist = aChange.isLevel(DiplomacyLevel.VASSAL);
                }
                if ((diplomacyState.getCurrentLevel() == DiplomacyLevel.ETERNAL_WAR) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.CONFEDERACY)){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if ((diplomacyState.getCurrentLevel() == DiplomacyLevel.LORD) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.VASSAL)){
                    Logger.fine("iconLevel == currentLevel " + thePlayer.getName() + " " + diplomacyState.getLord());
                    if (thePlayer == diplomacyState.getLord()){
                        tmpIconState = DiplomacyIconState.ACTIVE;
                    }else{
                        tmpIconState = DiplomacyIconState.DISABLED;
                    }
                }else
                if (isInConf | isVassal){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if (othersOfferExist){
                    if (changeVassalExist){
                        tmpIconState = DiplomacyIconState.PASSIVE_AND_SELECTED_AND_SUGGESTED;
                    }else{
                        tmpIconState = DiplomacyIconState.PASSIVE_AND_SUGGESTED;
                    }
                }else
                if (ownVassalOfferExist){
                    tmpIconState = DiplomacyIconState.PASSIVE_AND_SELECTED;
                }else{
                    tmpIconState = DiplomacyIconState.PASSIVE;
                }
            }else
            if (iconLevel == DiplomacyLevel.VASSAL){
                // Kan påverkas av
                // - ownOffer på LORD
                // - aChange till LORD
                // - otherOffer på VASSAL
                boolean ownLordOfferExist = false;
                if (ownOffer != null){
                    ownLordOfferExist = ownOffer.isLevel(DiplomacyLevel.LORD);
                }
                boolean changeLordExist = false;
                if (aChange != null){
                    changeLordExist = aChange.isLevel(DiplomacyLevel.LORD);
                }
                if ((diplomacyState.getCurrentLevel() == DiplomacyLevel.ETERNAL_WAR) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.CONFEDERACY)){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if ((diplomacyState.getCurrentLevel() == DiplomacyLevel.LORD) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.VASSAL)){
                    if (thePlayer != diplomacyState.getLord()){
                        tmpIconState = DiplomacyIconState.ACTIVE;
                    }else{
                        tmpIconState = DiplomacyIconState.DISABLED;
                    }
                }else
                if (isInConf | isVassal | isLord){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if (othersOfferExist){
                    if (changeLordExist){
                        tmpIconState = DiplomacyIconState.PASSIVE_AND_SELECTED_AND_SUGGESTED;
                    }else{
                        tmpIconState = DiplomacyIconState.PASSIVE_AND_SUGGESTED;
                    }
                }else
                if (ownLordOfferExist){
                    tmpIconState = DiplomacyIconState.PASSIVE_AND_SELECTED;
                }else{
                    tmpIconState = DiplomacyIconState.PASSIVE;
                }
            }else{ // annan level �n lord/vasall
                if (iconLevel == diplomacyState.getCurrentLevel()){
                    tmpIconState = DiplomacyIconState.ACTIVE;
                }else
                if ((diplomacyState.getCurrentLevel() == DiplomacyLevel.LORD) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.VASSAL) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.ETERNAL_WAR) | (diplomacyState.getCurrentLevel() == DiplomacyLevel.CONFEDERACY)){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if ((iconLevel == DiplomacyLevel.CONFEDERACY) & confNotPossible(thePlayer, otherPlayer)){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if ((iconLevel == DiplomacyLevel.CONFEDERACY) & (isVassal | isLord)){
                    tmpIconState = DiplomacyIconState.DISABLED;
                }else
                if (othersOfferExist){
                    if (changeExist){
                        tmpIconState = DiplomacyIconState.PASSIVE_AND_SELECTED_AND_SUGGESTED;
                    }else{
                        tmpIconState = DiplomacyIconState.PASSIVE_AND_SUGGESTED;
                    }
                }else
                if (ownOfferExist | changeExist){
                    tmpIconState = DiplomacyIconState.PASSIVE_AND_SELECTED;
                }else{ // check between...
                    if (iconLevel.isBetweenInclusive(diplomacyState.getRelation().getLowestRelation(), diplomacyState.getRelation().getHighestRelation())){
                        tmpIconState = DiplomacyIconState.PASSIVE;
                    }else{
                        tmpIconState = DiplomacyIconState.DISABLED;
                    }
                }
            }
        return tmpIconState;
    }

    private static boolean confNotPossible(Player thePlayer, Player otherPlayer){
        Logger.finer("thePlayer, otherPlayer: " + thePlayer.getGovernorName() + ", " + otherPlayer.getGovernorName());
        boolean confImpossible = false;
        Galaxy g = thePlayer.getGalaxy();
        List<DiplomacyState> confStates1 = getConfederacyStates(thePlayer, thePlayer.getGalaxy());
        List<DiplomacyState> confStates2 = getConfederacyStates(otherPlayer, thePlayer.getGalaxy());
        // check if both players are in a confederacy
        if ((confStates1.size() > 0) & (confStates2.size() > 0)){ // both players are in a confederacy
            confImpossible = true;
        }else
            // check if thePlayer is a member of a confederacy, and something hinders that thePlayer joins that confederacy
            if (confStates1.size() > 0){ // thePlayer is in a confederacy
                for (DiplomacyState confState : confStates1) {
                    Player confPlayer = confState.getOtherPlayer(thePlayer);
                    DiplomacyState otherState = getDiplomacyState(confPlayer, otherPlayer, g.getDiplomacyStates());
                    if (otherState.getCurrentLevel() == DiplomacyLevel.ETERNAL_WAR){ // confPlayer is in eternal war with otherPlayer and can never join the confederacy
                        confImpossible = true;
                    }else
                    if (otherState.getRelation().getHighestRelation() != DiplomacyLevel.CONFEDERACY){ // confPlayer can never conf with otherPlayer
                        confImpossible = true;
                    }
                }
            }else
                // check if otherPlayer is a member of a confederacy, and something hinders that thePlayer joins that confederacy
                if (confStates2.size() > 0){ // otherPlayer is in a confederacy
                    Logger.finer("confStates2.size(): " + confStates2.size());
                    for (DiplomacyState confState : confStates2) {
                        Player confPlayer = confState.getOtherPlayer(otherPlayer);
                        DiplomacyState otherState = getDiplomacyState(confPlayer, thePlayer, g.getDiplomacyStates());
                        if (otherState.getCurrentLevel() == DiplomacyLevel.ETERNAL_WAR){ // confPlayer is in eternal war with thePlayer and can never join the confederacy
                            confImpossible = true;
                            Logger.finer("otherState.getCurrentLevel() == DiplomacyLevel.ETERNAL_WAR");
                        }else
                        if (otherState.getRelation().getHighestRelation() != DiplomacyLevel.CONFEDERACY){ // confPlayer can never conf with thePlayer
                            confImpossible = true;
                            Logger.finer("otherState.relation.getHighestRelation() != DiplomacyLevel.CONFEDERACY");
                        }
                    }
                }
        return confImpossible;
    }

    public static List<DiplomacyState> getConfederacyStates(Player thePlayer, Galaxy galaxy){
        List<DiplomacyState> confStates = new LinkedList<>();
        List<Player> tempConfPlayers = getConfederacyPlayers(thePlayer, galaxy);
        if (tempConfPlayers.size() > 0){
            for (Player confPlayer : tempConfPlayers) {
                confStates.add(getDiplomacyState(thePlayer, confPlayer, galaxy.getDiplomacyStates()));
            }
        }
        return confStates;
    }

}
