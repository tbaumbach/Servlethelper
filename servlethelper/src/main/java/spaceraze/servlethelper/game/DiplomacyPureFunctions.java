package spaceraze.servlethelper.game;

import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.Galaxy;
import spaceraze.world.Planet;
import spaceraze.world.Player;
import spaceraze.world.diplomacy.*;

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
            if (PlanetPureFunctions.isPlanetOwner(planet, player1) || PlanetPureFunctions.isPlanetOwner(planet, player2)){
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
        if (planet.getPlayerInControl() == null){ // neutral planet
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

}
