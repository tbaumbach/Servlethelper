/*
 * Created on 2005-sep-27
 */
package spaceraze.servlethelper.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spaceraze.util.general.Logger;
import spaceraze.world.Faction;
import spaceraze.world.Galaxy;
import spaceraze.world.GameWorld;
import spaceraze.servlethelper.gameworlds.SpaceOpera;
import spaceraze.servlethelper.gameworlds.SpaceRazeExpanded;
import spaceraze.servlethelper.gameworlds.TheLastGreatWar;
import spaceraze.servlethelper.gameworlds.Titanium;
import spaceraze.servlethelper.gameworlds.Universe3051;
import spaceraze.world.Planet;


/**
 * @author wmpabod 
 *
 * Handles saving and loading of gameworlds from disk and to produce new 
 * GameWorld instances when needed. 
 * Also produces HTML lists etc for web pages.
 */
public class GameWorldHandler{
	private static List<GameWorld> gameWorldTypes;

	private GameWorldHandler() {}

	//TODO 2020-12-03 This class is moved to the servlethelper module (for the Java client to use), but should move back to the server module and GameWorldServlet should use this this class.
	public static List<GameWorld> getGameWorldTypes(){
		if (gameWorldTypes == null){
			gameWorldTypes = new ArrayList<>();
			// create all gameWorlds
			createHardcodedGameWorlds(gameWorldTypes);
		}
		return gameWorldTypes;	}
	
	public static void reloadGameWorldTypes(){
		gameWorldTypes = null;
		getGameWorldTypes();
	}

	private static void createHardcodedGameWorlds(List<GameWorld> gameWorldTypes){
		gameWorldTypes.add(getHardcodedGameWorld("srexpanded"));
		gameWorldTypes.add(getHardcodedGameWorld("universe"));
		gameWorldTypes.add(getHardcodedGameWorld("spaceopera"));
		gameWorldTypes.add(getHardcodedGameWorld("titanium"));
		gameWorldTypes.add(getHardcodedGameWorld("greatwar"));
		// unused gameworlds...
		//gameWorldTypes.add(getHardcodedGameWorld("corporatespace"));
		//gameWorldTypes.add(getHardcodedGameWorld("greatwarclassic"));
		//gameWorldTypes.add(getHardcodedGameWorld("srclassic"));
		//gameWorldTypes.add(getHardcodedGameWorld("sw"));		
	}
	
	public static GameWorld getGameWorld(String gameWorldFileName){
		GameWorld foundGW = null;
		List<GameWorld> allGameWorlds = getGameWorldTypes();
		int i = 0;
		while ((i < allGameWorlds.size()) && (foundGW == null)) {
			GameWorld tmpGW = allGameWorlds.get(i);
			if (tmpGW.getFileName().equals(gameWorldFileName)){
				foundGW = tmpGW;
			}else{
				i++;
			}
		}
		return foundGW;
	}
	
	public static String getGameWorldOptionsHTML(){
		StringBuilder retHTML = new StringBuilder();
		List<GameWorld> allGameWorlds = getGameWorldTypes();
		for (GameWorld tmpGW : allGameWorlds) {
			Logger.finer(tmpGW.getFileName());
			retHTML.append("<option value=\"");
			retHTML.append(tmpGW.getFileName());
			retHTML.append("\">");
			retHTML.append(tmpGW.getFullName());
			retHTML.append("</option>");
		}
		return retHTML.toString();
	}
	
	public static int getGameWorldsNr(){
		return getGameWorldTypes().size();
	}

	private static GameWorld getHardcodedGameWorld(String fileName){
		GameWorld tmpGW = null;
		if (fileName.equals("srexpanded")){
			tmpGW = SpaceRazeExpanded.getGameWorld();
		}else
		if (fileName.equals("universe")){
			tmpGW = Universe3051.getGameWorld();
		}else
		if (fileName.equals("spaceopera")){
			tmpGW = SpaceOpera.getGameWorld();
		}else
		if (fileName.equals("titanium")){
			tmpGW = Titanium.getGameWorld();
		}else
		if (fileName.equals("greatwar")){
			tmpGW = TheLastGreatWar.getGameWorld();
		}
		/*
		 * Unused gameworlds...
		if (fileName.equals("greatwarclassic")){
			tmpGW = TheLastGreatWarClassic.getGameWorld();
		}else
		if (fileName.equals("corporatespace")){
			tmpGW = CorporateSpace.getGameWorld();
		}else
		if (fileName.equals("srclassic")){
			tmpGW = SpaceRazeClassic.getGameWorld();
		}else
		if (fileName.equals("sw")){
			tmpGW = StarWars.getGameWorld();
		}else
		if (fileName.equals("Universe")){
			tmpGW = Universe3051.getGameWorld();
		}
		*/
	
		return tmpGW;
	}

	/**
	 * returns html with links to gameworld.jsp for all gameworlds
	 */
	public static String getListHTML(){
		StringBuilder retHTML = new StringBuilder();
		List<GameWorld> allGameWorlds = getGameWorldTypes();
		Logger.finer("getGameWorldTypes()" + allGameWorlds.size());
		for (GameWorld tmpGW : allGameWorlds) {
			retHTML.append("<a href=\"gameworld.jsp?gameworldfilename=");
			Logger.finest(tmpGW.toString());
			Logger.finest(tmpGW.getFileName());
			retHTML.append(tmpGW.getFileName());
			retHTML.append("\">");
			retHTML.append(tmpGW.getFullName());
			retHTML.append("</a><br>");
		}
		return retHTML.toString();
	}


	/**
	 * Returns the first of the gameworlds with the highest counter
	 */
	/*TODO 2020-01-03 Changed the statistics logic or activate this to be used by ?
	public static GameWorld getMostUsedGameWorld(){
		List<GameWorld> gameWorlds = getGameWorldTypes();
		GameWorld foundGW = null;
		int foundCounter = -1;
		for (GameWorld aGameWorld : gameWorlds) {
			int tmpCounter = PHash.getCounter("game.finished.gameworld." + aGameWorld.getFileName());
			if (foundGW == null){
				foundGW = aGameWorld;
				foundCounter = tmpCounter;
			}else
			if(tmpCounter > foundCounter){
				foundGW = aGameWorld;
				foundCounter = tmpCounter;
			}
		}
		return foundGW;
	}
	
	public static String getMostUsedGameWorldName(){
		GameWorld foundGameWorld = getMostUsedGameWorld();
		String gwName = "No GameWorlds found";
		if (foundGameWorld != null){
			gwName = foundGameWorld.getFullName();
		}
		return gwName;
	}*/
	
    public static GameWorld findGameWorld(String aGameWorldName){
    	GameWorld found = null;
    	int i = 0;
    	List<GameWorld> gameWorlds = getGameWorldTypes();
    	while ((found == null) && (i < gameWorlds.size())){
    		GameWorld aGameWorld = gameWorlds.get(i);
    		if (aGameWorld.getFullName().equals(aGameWorldName)){
    			found = aGameWorld;
    		}else{
    			i++;
    		}
    	}
    	return found;
    }

	public static Faction getFactionByName(String name, GameWorld gameWorld){
		return gameWorld.getFactions().stream().filter(faction -> faction.getName().equalsIgnoreCase(name)).findAny().orElseThrow();
	}

	public static Faction getFactionByKey(String key, GameWorld gameWorld){
		return gameWorld.getFactions().stream().filter(faction -> faction.getKey().equalsIgnoreCase(key)).findAny().orElseThrow();
	}

	// check if 1 faction has at least factionVictory(65) % of the total pop of all
	// planets in the game
	public static Faction checkWinningFaction(Galaxy galaxy, int factionVictoryLimit) {

		Map<String, Integer> factionPoints = new HashMap<>();
		String winner = null;
		for (Faction faction : galaxy.getGameWorld().getFactions()) {
			factionPoints.put(faction.getKey(), 0);
		}
		int neutralPop = 0; // räkna popen på alla neutrala planeter
		// räkna popen på alla factioner
		for (int j = 0; j < galaxy.getPlanets().size(); j++) {
			Planet tempPlanet = galaxy.getPlanets().get(j);
			if (tempPlanet.getPlayerInControl() != null) {
				if (GameWorldHandler.getFactionByKey(tempPlanet.getPlayerInControl().getFactionKey(), galaxy.getGameWorld()).isAlien()) {
					factionPoints.replace(tempPlanet.getPlayerInControl().getFactionKey(), factionPoints.get(tempPlanet.getPlayerInControl().getFactionKey()) + tempPlanet.getResistance());
				} else {
					factionPoints.replace(tempPlanet.getPlayerInControl().getFactionKey(), factionPoints.get(tempPlanet.getPlayerInControl().getFactionKey()) + tempPlanet.getPopulation());
				}
			} else {
				neutralPop = neutralPop + tempPlanet.getPopulation();
			}
		}
		// summera all pop i sectorn
		int totalPop = neutralPop;
		for(Map.Entry<String, Integer> entry : factionPoints.entrySet()){
			totalPop = totalPop + entry.getValue();
		}

		for(Map.Entry<String, Integer> entry : factionPoints.entrySet()){
			if (winner == null) {
				if (((entry.getValue() * 1.0) / totalPop * 1.0) > (factionVictoryLimit / 100.0)) {
					winner = entry.getKey();
				}
			}
		}

		return winner != null ? GameWorldHandler.getFactionByKey(winner, galaxy.getGameWorld()) : null;

	}

	public static List<Faction> getGreatestFactions(Galaxy galaxy) {

		Map<String, Integer> factionPoints = new HashMap<>();
		int winnerPoint = 0;
		List<Faction> winners = new ArrayList<>();
		for (Faction faction : galaxy.getGameWorld().getFactions()) {
			factionPoints.put(faction.getKey(), 0);
		}
		// räkna popen på alla factioner
		for (int j = 0; j < galaxy.getPlanets().size(); j++) {
			Planet tempPlanet = galaxy.getPlanets().get(j);
			if (tempPlanet.getPlayerInControl() != null) {
				if (GameWorldHandler.getFactionByKey(tempPlanet.getPlayerInControl().getFactionKey(), galaxy.getGameWorld()).isAlien()) {
					factionPoints.replace(tempPlanet.getPlayerInControl().getFactionKey(), factionPoints.get(tempPlanet.getPlayerInControl().getFactionKey()) + tempPlanet.getResistance());
				} else {
					factionPoints.replace(tempPlanet.getPlayerInControl().getFactionKey(), factionPoints.get(tempPlanet.getPlayerInControl().getFactionKey()) + tempPlanet.getPopulation());
				}
			}
		}

		for(Map.Entry<String, Integer> entry : factionPoints.entrySet()){
			if (winnerPoint == 0) {
				winnerPoint = entry.getValue();
			}else if(winnerPoint < entry.getValue()){
				winnerPoint = entry.getValue();
			}
		}

		for(Map.Entry<String, Integer> entry : factionPoints.entrySet()){
			if(entry.getValue() == winnerPoint){
				winners.add(GameWorldHandler.getFactionByKey(entry.getKey(), galaxy.getGameWorld()));
			}
		}

		return winners;
	}
}
