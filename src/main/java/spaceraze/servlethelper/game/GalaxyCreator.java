package spaceraze.servlethelper.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spaceraze.game.*;
import spaceraze.map.GalaxyMap;
import spaceraze.map.MapPlanet;
import spaceraze.map.MapPlanetConnection;
import spaceraze.servlethelper.game.planet.PlanetMutator;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.spaceship.SpaceshipMutator;
import spaceraze.servlethelper.game.troop.TroopMutator;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.util.general.Functions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.diplomacy.DiplomacyRelation;
import spaceraze.game.diplomacy.DiplomacyState;
import spaceraze.game.diplomacy.GameDiplomacyRelation;

public class GalaxyCreator{
	
	public static Galaxy createGalaxy(String nameOfGame, GalaxyMap map, int steps, GameWorld aGameWorld, int singleVictory, int factionVictory, int endTurn, int numberOfStartPlanet, StatisticGameType statisticGameType){
    	Logger.fine("createGalaxy called: " + nameOfGame + " " + map.getName());
    	Galaxy g = createGalaxy(nameOfGame,map, steps, aGameWorld, statisticGameType);
    	if(singleVictory > 0){
    		g.setSingleVictory(singleVictory);
    	}
    	if(factionVictory > 0){
    		g.setFactionVictory(factionVictory);
    	}
    	if(endTurn > 0){
    		g.setEndTurn(endTurn);
    	}
    	if(numberOfStartPlanet > 0){
    		g.setNumberOfStartPlanet(numberOfStartPlanet);
    	}
    	return g;
    }

    public Galaxy createGalaxy(String nameOfGame, GalaxyMap aMap, int steps, GameWorld aGameWorld){
    	return createGalaxy(nameOfGame,aMap,steps,aGameWorld,StatisticGameType.ALL);
    }
    	
    private static Galaxy createGalaxy(String nameOfGame, GalaxyMap map, int steps, GameWorld aGameWorld, StatisticGameType statisticGameType){
    	Logger.fine("createGalaxy #2 called: " + nameOfGame + " " + map.getFileName());
//    	Map theMap = MapHandler.getMap(nameOfMap);

        List<Planet> planets = new ArrayList<>();
        for (MapPlanet aPlanet : map.getPlanets()) {
            planets.add(new Planet(aPlanet.getUuid()));
        }

        List<PlanetConnection> planetConnections = new ArrayList<>();
        for (MapPlanetConnection aConnection : map.getConnections()) {
            Planet p1 = PlanetPureFunctions.getPlanet(aConnection.getPlanetOneUuid(), planets);
            Planet p2 = PlanetPureFunctions.getPlanet(aConnection.getPlanetTwoUuid(), planets);
            planetConnections.add(new PlanetConnection(p1, p2, aConnection.isLongRange()));
        }

    	Galaxy g = new Galaxy(planets, planetConnections, map.getUuid(), map.getVersionId(), map.getFileName(), nameOfGame, steps, aGameWorld.getUuid(), map.getMaxNrStartPlanets());
        Logger.fine("statisticGameType: " + statisticGameType.toString());
    	StatisticsHandler.createStatistics(g, statisticGameType);
    	// randomize planets order
    	Collections.shuffle(g.getPlanets());
    	// randomize planet data
        randomizeNeutralPlanets(g.getPlanets(),g, true, aGameWorld);
        Logger.fine("Galaxy created.");
        return g;
    }

    public static void randomizeNeutralPlanets(List<Planet> allPlanets, Galaxy g, boolean couldBeRazed, GameWorld gameWorld){
      for (int i = 0; i < allPlanets.size(); i++){
        Planet tempp = allPlanets.get(i);
        if (!tempp.isStartPlanet()){
          int tmpProd = Functions.getRandomInt(1,3) + Functions.getRandomInt(1,4) - 1;
            tempp.setProd(tmpProd);
            tempp.setBasePopulation(tmpProd);
          if (tempp.getPopulation() > 3){
            tempp.setResistance(Functions.getRandomInt(1,3) + Functions.getRandomInt(1,3));
          }else{
            tempp.setResistance(Functions.getRandomInt(1,4));
          }
          int temp = Functions.getRandomInt(1,100);
          if (temp <= gameWorld.getClosedNeutralPlanetChance()){  // �ndrar s� att �ppna blir st�ngda
            PlanetMutator.reverseVisibility(tempp);
          }
          SpaceshipType sst1 = gameWorld.getNeutralSize1();
          SpaceshipType sst2 = gameWorld.getNeutralSize2();
          SpaceshipType sst3 = gameWorld.getNeutralSize3();
          TroopType tt = gameWorld.getNeutralTroopType();
          if (couldBeRazed && Functions.getRandomInt(1,100) <= gameWorld.getRazedPlanetChance()){
        	  PlanetMutator.setRazed(tempp);
          }else 
          if (tempp.getPopulation() < 3){ // pop 1-2
            temp = Functions.getRandomInt(1,3) - 1;
            createNeutralShips(temp,sst1,tempp,g);
            if (tt != null){
            	temp = Functions.getRandomInt(1,2) - 1;
            	createNeutralTroops(temp, tt, tempp, g, gameWorld);
            }
          }else
          if ((tempp.getPopulation() >= 3) & (tempp.getPopulation() < 5)){ // pop 3-4
            temp = Functions.getRandomInt(1,3);
            if (temp < 3){ // build some small ships
              temp = Functions.getRandomInt(2,4);
              createNeutralShips(temp,sst1,tempp,g);
              if (tt != null){
              	temp = Functions.getRandomInt(1,3) - 1;
              	createNeutralTroops(temp, tt, tempp, g, gameWorld);
              }
            }else{ // build some medium ships
              temp = Functions.getRandomInt(1,2);
              createNeutralShips(temp,sst2,tempp,g);
              if (tt != null){
              	temp = Functions.getRandomInt(1,2);
              	createNeutralTroops(temp, tt, tempp, g, gameWorld);
              }
            }
          }else{  // pop 5+
            temp = Functions.getRandomInt(1,3);
            if (temp > 1){ // build some medium ships
              temp = Functions.getRandomInt(2,4);
              createNeutralShips(temp,sst2,tempp,g);
              if (tt != null){
              	temp = Functions.getRandomInt(1,3);
              	createNeutralTroops(temp, tt, tempp, g, gameWorld);
              }
            }else{ // build some large ships
              temp = Functions.getRandomInt(1,3);
              createNeutralShips(temp,sst3,tempp,g);
              if (tt != null){
              	temp = Functions.getRandomInt(2,4);
              	createNeutralTroops(temp, tt, tempp, g, gameWorld);
              }
            }
          }
        }
      }
    }

    private static void createNeutralShips(int nr, SpaceshipType sst, Planet aPlanet, Galaxy g){
      for (int i = 0; i < nr; i++){
        addNeutralShip(aPlanet,sst,g);
      }
    }

    private static  void addNeutralShip(Planet aPlanet,SpaceshipType sstTemp, Galaxy g){
      Spaceship ssTemp = SpaceshipMutator.createSpaceShip(sstTemp);
      ssTemp.setLocation(aPlanet);
      g.getSpaceships().add(ssTemp);
    }

    private static void createNeutralTroops(int nr, TroopType tt, Planet aPlanet, Galaxy g, GameWorld gameWorld){
        for (int i = 0; i < nr; i++){
          addNeutralTroop(aPlanet,tt,g, gameWorld);
        }
    }

    private static void addNeutralTroop(Planet aPlanet,TroopType ttTemp, Galaxy g, GameWorld gameWorld){
        Troop tTemp = TroopMutator.createTroop(ttTemp, g, gameWorld);
        tTemp.setPlanetLocation(aPlanet);
        g.addTroop(tTemp);
    }

    static public void createInitialDiplomaticRelations(Player p, GameWorld gw, Galaxy galaxy){
        // create new diplomacy states to all other players (that have already joined this game)
        //GameWorldDiplomacy diplomacy = gw.getDiplomacy();
        for (Player aPlayer : galaxy.getPlayers()) {
            DiplomacyRelation tmpRelation = GameWorldCreator.getRelation(GameWorldHandler.getFactionByUuid(aPlayer.getFactionUuid(), gw), GameWorldHandler.getFactionByUuid(p.getFactionUuid(), gw), gw);
            GameDiplomacyRelation gameDiplomacyRelation = GameDiplomacyRelation.builder()
                    .factionOne(tmpRelation.getFactionOne())
                    .factionTwo(tmpRelation.getFactionTwo())
                    .lowestRelation(tmpRelation.getLowestRelation())
                    .highestRelation(tmpRelation.getHighestRelation())
                    .startRelation(tmpRelation.getStartRelation())
                    .build();
            DiplomacyState tmpState = new DiplomacyState(gameDiplomacyRelation,aPlayer,p);
            galaxy.getDiplomacyStates().add(tmpState);
        }

    }
    
}
