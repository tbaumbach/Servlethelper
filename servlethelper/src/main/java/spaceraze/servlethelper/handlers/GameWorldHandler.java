/*
 * Created on 2005-sep-27
 */
package spaceraze.servlethelper.handlers;

import java.util.ArrayList;
import java.util.List;

import spaceraze.util.general.Logger;
import spaceraze.world.GameWorld;
import spaceraze.world.gameworlds.SpaceOpera;
import spaceraze.world.gameworlds.SpaceRazeExpanded;
import spaceraze.world.gameworlds.TheLastGreatWar;
import spaceraze.world.gameworlds.Titanium;
import spaceraze.world.gameworlds.Universe3051;


/**
 * @author wmpabod 
 *
 * Handles saving and loading of gameworlds from disk and to produce new 
 * GameWorld instances when needed. 
 * Also produces HTML lists etc for web pages.
 */
public class GameWorldHandler{
	static private List<GameWorld> gameWorldTypes;
	
	//TODO 2020-12-03 This class is moved to the servlethelper module (for the Java client to use), but should move back to the server module and GameWorldServlet should use this this class.
	public static List<GameWorld> getGameWorldTypes(){
		if (gameWorldTypes == null){
			gameWorldTypes = new ArrayList<GameWorld>();
			// create all gameworlds
			// createGameWorlds(gameWorldTypes);
			// TODO (Paul) load from files instead (the line above)
			createHardcodedGameWorlds(gameWorldTypes);
		}
		return gameWorldTypes;	}
	
	public static void reloadGameWorldTypes(){
		gameWorldTypes = null;
		getGameWorldTypes();
	}

	/**
	 * Create three hardcoded gameworlds...
	 * @param gameWorldTypes
	 */
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
			GameWorld tmpGW = (GameWorld)allGameWorlds.get(i);
			if (tmpGW.getFileName().equals(gameWorldFileName)){
				foundGW = tmpGW;
			}else{
				i++;
			}
		}
		return foundGW;
	}
	
	public static String getGameWorldOptionsHTML(){
		StringBuffer retHTML = new StringBuffer();
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
		int nr = 0;
		List<GameWorld> GWs = getGameWorldTypes();
		if (GWs != null){
			nr = GWs.size();
		}
		return nr;
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
	 * returns html with links to battlesim for all gameworlds
	 * @return
	 */
	public static String getBattleSimListHTML(){
		StringBuffer retHTML = new StringBuffer();
		List<GameWorld> allGameWorlds = getGameWorldTypes();
		for (GameWorld tmpGW : allGameWorlds) {
			retHTML.append("<a href=\"battle_sim.jsp?gameworld=");
			retHTML.append(tmpGW.getFileName());
			retHTML.append("\">");
			retHTML.append(tmpGW.getFullName());
			retHTML.append("</a><br>");
		}
		return retHTML.toString();
	}

	public static String getBattleSimListHTMLNO(){
		StringBuffer retHTML = new StringBuffer();
		List<GameWorld> allGameWorlds = getGameWorldTypes();
		for (GameWorld tmpGW : allGameWorlds) {
			retHTML.append("<a href=\"battle_sim.jsp?gameworld=");
			retHTML.append(tmpGW.getFileName());
			retHTML.append("\">");
			retHTML.append(tmpGW.getFullName());
			retHTML.append("</a><br>");
		}
		return retHTML.toString();
	}
	
	/**
	 * returns html with links to gameworld.jsp for all gameworlds
	 * @return
	 */
	public static String getListHTML(){
		StringBuffer retHTML = new StringBuffer();
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
	 * @return
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
    	while ((found == null) & (i < gameWorlds.size())){
    		GameWorld aGameWorld = gameWorlds.get(i);
    		if (aGameWorld.getFullName().equals(aGameWorldName)){
    			found = aGameWorld;
    		}else{
    			i++;
    		}
    	}
    	return found;
    }    

}
