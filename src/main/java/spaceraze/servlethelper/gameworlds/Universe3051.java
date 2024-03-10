package spaceraze.servlethelper.gameworlds;

import spaceraze.servlethelper.game.AlignmentHelper;
import spaceraze.servlethelper.game.AlignmentPureFunctions;
import spaceraze.servlethelper.game.GameWorldCreator;
import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.world.Alignment;
import spaceraze.world.BuildingType;
import spaceraze.world.Corruption;
import spaceraze.world.Faction;
import spaceraze.world.GameWorld;
import spaceraze.world.SpaceshipType;
import spaceraze.world.VIPType;
import spaceraze.world.enums.BlackMarketFrequency;
import spaceraze.world.enums.SpaceShipSize;
import spaceraze.world.enums.SpaceshipRange;
import spaceraze.world.enums.SpaceshipTargetingType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Universe3051{
	

	public static GameWorld getGameWorld(){
		// XXX Spaceraze Expanded
		GameWorld gw = new GameWorld();
        gw.setUuid(UUID.randomUUID().toString());
		
//	    gw.setCumulativeBombardment(true);
//		gw.setSquadronsSurviveOutsideCarriers(false);

	    gw.setFileName("universe");

	    gw.setFullName("Universe Year 3051");
	    

	    gw.setBattleSimDefaultShips1("crv;crv;crv;sct");
	    gw.setBattleSimDefaultShips2("Crk;Crk;Rai;cou");
	    
	    gw.setShortDescription("An Advanced beginner to intermediate world based on that all factions shall have the same ship types. The World Still have many advanced features of the Spaceraze design.");
        gw.setHistory("Corporations has taken control over the known universe, in the Year 3051 an Universal war breaks out, smaller neutral systems tries to avoid to get involved. Instead they are just considered as pawns in a bigger game, there alliances and borders are decided by the larger richer corporations");
        
	    

	    gw.setCreatedDate("2005-10-26");
		gw.setChangedDate("2005-10-27");
		gw.setCreatedByUser("Niohl");
		
		gw.setRazedPlanetChance(5);
		gw.setClosedNeutralPlanetChance(30);

		gw.setAlignments(AlignmentHelper.createDefaultAlignments(gw));
		Alignment neutral = AlignmentPureFunctions.findAlignmentByName("neutral", gw.getAlignments());
		//Alignment good = AlignmentHelper.findAlignment("good", gw.getAlignments());
		//Alignment evil = AlignmentHelper.findAlignment("evil", gw.getAlignments());
        
	    
		Corruption tmpCorruption = new Corruption();
		tmpCorruption.addBreakpoint(25, 5);
		tmpCorruption.addBreakpoint(50, 10);
		tmpCorruption.addBreakpoint(75, 15);
		tmpCorruption.addBreakpoint(100, 20);
		tmpCorruption.addBreakpoint(125, 25);
		tmpCorruption.addBreakpoint(150, 50);
//###################  GOV  ##########################################
        

        VIPType tmpVipType = new VIPType("Governor","Gov",neutral);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setOpenIncBonus(1);
        gw.addVipType(tmpVipType);

        VIPType govType = tmpVipType;
        
//    	###################  VIP  ##########################################	        
        
        

        tmpVipType = new VIPType("Master Spy","Spy",neutral);
        tmpVipType.setBuildCost(20);
        tmpVipType.setUpkeep(3);
        tmpVipType.setCounterEspionage(50);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(50);
              
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Commando Unit","CU",neutral);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(3);
        tmpVipType.setAssassination(60);
        tmpVipType.setPsychWarfareBonus(2);
        tmpVipType.setResistanceBonus(5);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);        
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Assassin","Ass",neutral);
        tmpVipType.setBuildCost(25);
        tmpVipType.setUpkeep(2);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(70);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);        
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("General","Gen",neutral);
        tmpVipType.setCounterEspionage(40);
        tmpVipType.setBuildCost(30);
        tmpVipType.setUpkeep(3);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setFrequency(BlackMarketFrequency.SOMEWHAT_UNCOMMON);        
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Astrogator","Ast",neutral);
        tmpVipType.setCounterEspionage(20);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        tmpVipType.setFTLbonus(true);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Admiral","Adm",neutral);
        tmpVipType.setCounterEspionage(30);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setInitBonus(10);
        tmpVipType.setFrequency(BlackMarketFrequency.SOMEWHAT_UNCOMMON);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Top Ace","Ace",neutral);
        tmpVipType.setInitFighterSquadronBonus(5);
        tmpVipType.setBuildCost(20);
        tmpVipType.setUpkeep(3);
        tmpVipType.setCounterEspionage(10);
        tmpVipType.setDescription("The Top Ace, gives the squadrons better inititive. A top ace is a squadron�s pilot and must be on a squadron to give bonus. He boosts all squadrons in a fleet.");
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Economic Genious","Eco",neutral);
        tmpVipType.setCounterEspionage(10);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setFrequency(BlackMarketFrequency.SOMEWHAT_UNCOMMON);
        tmpVipType.setOpenIncBonus(4);
        tmpVipType.setClosedIncBonus(2);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Spaceship Engineer","Eng",neutral);
        tmpVipType.setCounterEspionage(20);
        tmpVipType.setBuildCost(30);
        tmpVipType.setUpkeep(4);
        tmpVipType.setFrequency(BlackMarketFrequency.SOMEWHAT_UNCOMMON);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setShipBuildBonus(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Scientist","Sci",neutral);
        tmpVipType.setCounterEspionage(30);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Expert Scientist","ESci",neutral);
        tmpVipType.setCounterEspionage(40);
        tmpVipType.setBuildCost(50);
        tmpVipType.setUpkeep(5);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(15);
        tmpVipType.setFrequency(BlackMarketFrequency.SOMEWHAT_UNCOMMON);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Diplomat","Dip",neutral);
        tmpVipType.setCounterEspionage(40);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setFrequency(BlackMarketFrequency.SOMEWHAT_UNCOMMON);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        gw.addVipType(tmpVipType);
	    
//###################  Buildings  ##########################################
        List<BuildingType> tempBuildings = new ArrayList<>();
        BuildingType tempBuildingType;
        
        tempBuildingType= new BuildingType("Small Wharf", "W1", 5);
        tempBuildingType.setWharfSize(1);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setDescription("Small Wharf what can build small ships");
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Medium Wharf", "W2", 7);
        tempBuildingType.setWharfSize(2);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setDescription("Medium Wharf what can build small and medium ships");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Small Wharf"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Large Wharf", "W3", 15);
        tempBuildingType.setWharfSize(3);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Large Wharf what can build small, medium and large ships");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Medium Wharf"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Huge Wharf", "W5", 24);
        tempBuildingType.setWharfSize(5);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Huge Wharf what can build small, medium, large and huge ships");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Large Wharf"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType= new BuildingType("Military Spaceport", "MSP", 8);
        tempBuildingType.setSpaceport(true);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Build this building on the planets and long range ways will be short range between planetes whit spaceports");
        tempBuildings.add(tempBuildingType);

        tempBuildingType= new BuildingType("Commercial Spaceport", "CSP",6);
        tempBuildingType.setSpaceport(true);
        tempBuildingType.setOpenPlanetBonus(2);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Military Spaceport"));
        tempBuildingType.setDescription("Extending the military Spaceport to become a commercial hub");
        tempBuildings.add(tempBuildingType);
        

        
        
//      ###################  New Building Types  ##########################################
       
        tempBuildingType= new BuildingType("Improved Infrastructure", "IIS", 7);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Preparation to build, advanced buildings");
        tempBuildings.add(tempBuildingType);
     
        
        tempBuildingType= new BuildingType("Security Center", "SC", 20);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Assassin"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Master Spy"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Commando Unit"));
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Security Center to train undercover VIPs");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Improved Infrastructure"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Military Academy", "MAC", 20);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Admiral"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Top Ace"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("General"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Astrogator"));
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Academy to train military VIPs");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Improved Infrastructure"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType= new BuildingType("Advanced University", "AU", 20);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Scientist"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Expert Scientist"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Economic Genious"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Spaceship Engineer"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Diplomat"));
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Academy to train Civilian VIPs");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Improved Infrastructure"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType= new BuildingType("City", "City", 4);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setOpenPlanetBonus(1);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("City, Increase Open Planet Income +1.");
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Metropolis", "Met", 8);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setOpenPlanetBonus(3);
        tempBuildingType.setResistanceBonus(1);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Megatropolis, Increase Open Planet Income +3.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "City"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Capital", "Cap", 16);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.setOpenPlanetBonus(5);
        tempBuildingType.setResistanceBonus(2);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Player Capital gives 5 in income if the planet is open. Gives bonus to the defending ground force. Only one Universal Trade Center can be build each player.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Metropolis"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Faction Capital", "FCap", 32);
        tempBuildingType.setOpenPlanetBonus(8);
        tempBuildingType.setResistanceBonus(5);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setFactionUnique(true);
        tempBuildingType.setDescription("Capital that gives 8 in incom if the planet is open. Gives a big bonus to the defending ground force. Only one Capital can be build for each faction.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Capital"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType= new BuildingType("Quadrant Capital", "QCap", 64);
        tempBuildingType.setOpenPlanetBonus(12);
        tempBuildingType.setResistanceBonus(5);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setWorldUnique(true);
        tempBuildingType.setDescription("Capital that gives 8 in incom if the planet is open. Gives a big bonus to the defending ground force. Only one Capital can be build for each faction.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Faction Capital"));
        tempBuildings.add(tempBuildingType);

        
      
        
        tempBuildingType= new BuildingType("Tech Center", "TC", 10);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setTechBonus(10);        
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Tech center give better technology to units.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Company Base"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Quadrant Tech Center", "ATC", 50);
        tempBuildingType.setTechBonus(25);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setWorldUnique(true);
        tempBuildingType.setDescription("Advanced tech center gives even better technology to units than tech center do.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Tech Center"));
        tempBuildings.add(tempBuildingType);
        
        
        
        tempBuildingType= new BuildingType("Basic Shield", "BS", 10);
        tempBuildingType.setShieldCapacity(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A small shield to defend planets from bombardment.");
        tempBuildings.add(tempBuildingType);

        tempBuildingType= new BuildingType("Advanced Shield", "AS", 25);
        tempBuildingType.setShieldCapacity(3);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.setDescription("Advanced Shield, Defends planets from most bombardments.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Basic Shield"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Star Shield", "TOS", 70);
        tempBuildingType.setShieldCapacity(5);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setWorldUnique(true);
        tempBuildingType.setDescription("The One Sheild, Defends against all bombardment.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Advanced Shield"));
        tempBuildings.add(tempBuildingType);

        
        tempBuildingType= new BuildingType("Missile Silo", "MS", 15);
        tempBuildingType.setCannonDamage(100);
        tempBuildingType.setCannonHitChance(90);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based missile system to shoot down ships what besiege the planet.");
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Missile Defence", "MD", 25);
        tempBuildingType.setCannonDamage(100);
        tempBuildingType.setCannonHitChance(95);
        tempBuildingType.setCannonRateOfFire(3);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based missile system to shoot down ships in great numbers, that besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Missile Silo"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Star Missile Defence", "SMD", 70);
        tempBuildingType.setCannonDamage(1000);
        tempBuildingType.setCannonHitChance(95);
        tempBuildingType.setCannonRateOfFire(3);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setWorldUnique(true);
        tempBuildingType.setDescription("A planet based missile system to shoot down ships in great numbers, that besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Missile Defence"));
        tempBuildings.add(tempBuildingType);
        
        
        tempBuildingType= new BuildingType("Cannon", "Can", 10);
        tempBuildingType.setCannonDamage(100);
        tempBuildingType.setCannonHitChance(75);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based Cannon to shoot down small ships what besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Company Base"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Heavy Cannon", "HC", 20);
        tempBuildingType.setCannonDamage(250);
        tempBuildingType.setCannonHitChance(75);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based heavy Cannon to shoot down ships what besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Cannon"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Star Cannon", "SC", 50);
        tempBuildingType.setCannonDamage(2500);
        tempBuildingType.setCannonHitChance(75);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setWorldUnique(true);
        tempBuildingType.setDescription("A planet based Star Cannon to shoot down most ships that besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Heavy Cannon"));
        tempBuildings.add(tempBuildingType);
        
        
        
		
		
//###################  DEFENCE  ##########################################

		
		
        // Golan I
        SpaceshipType tempsst = new SpaceshipType("Defence Platform I","DPI", SpaceShipSize.SMALL,20,100,SpaceshipRange.NONE,1,7, 15,25);
        tempsst.setDescription("Defensive Plattform I, great first defence vs enemy raiders, and to complement defence vs Squadrons");
        tempsst.setAdvantages("Cheap, Initiative bonus(+2), Good vs Small, Very good vs Squadrons");
        tempsst.setDisadvantages("Cannot move, Poor vs Medium, Large and Huge capital ships");
        
        tempsst.setArmorSmall(40);
        tempsst.setWeaponsStrengthMedium(10);  
        
        tempsst.setIncreaseInitiative(2);
         
        tempsst.setCanAppearOnBlackMarket(false);
        gw.addShipType(tempsst);
        
        // Golan II
        tempsst = new SpaceshipType("Defence Platform II","DPII",SpaceShipSize.MEDIUM,50,400,SpaceshipRange.NONE,2,15, 30,15);
        tempsst.setDescription("Defence Plattform II, Very usable vs small attacking fleets");
        tempsst.setAdvantages("Cheap, Initiative bonus(+2), Very good vs Small and Medium");
        tempsst.setDisadvantages("Cannot move, Poor vs Large and Huge capital ships");
        
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setArmorSmall(70);
        tempsst.setArmorMedium(60);
        
        tempsst.setIncreaseInitiative(2);
        
        tempsst.setWeaponsStrengthMedium(30);    
        
        gw.addShipType(tempsst);
        
        // Golan III
        tempsst = new SpaceshipType("Defence Platform III","DPIII",SpaceShipSize.LARGE,100,800,SpaceshipRange.NONE,3,40, 10,10);
        tempsst.setDescription("Defence Plattform III, Usable against Medium attacking fleets");
        tempsst.setAdvantages("Cheap, Initiative bonus(+3), Very good vs Medium, Large");
        tempsst.setDisadvantages("Cannot move, Poor vs Medium, Large and Huge capital ships");
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(70);
        tempsst.setArmorLarge(20);
        
        tempsst.setIncreaseInitiative(3);
        
        tempsst.setWeaponsStrengthMedium(25);    
        tempsst.setWeaponsStrengthLarge(70);   
        tempsst.setWeaponsStrengthHuge(90);    
	
        gw.addShipType(tempsst);
	// Home Base
        tempsst = new SpaceshipType("Head Quarter","HQ",SpaceShipSize.LARGE,400,1600,SpaceshipRange.NONE,0,160, 15,15);
        tempsst.setDescription("Defence Plattform III, Usable against Medium attacking fleets");
        tempsst.setAdvantages("Initiative bonus(+4), Very good vs Medium, Large");
        tempsst.setDisadvantages("Cannot move, Poor vs Medium, Large and Huge capital ships");
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(70);
        tempsst.setArmorLarge(50);
        
        tempsst.setIncreaseInitiative(4);
        
    	tempsst.setIncOwnOpenBonus(3);
    	tempsst.setIncOwnClosedBonus(3);
    	
        tempsst.setWeaponsStrengthMedium(50);    
        tempsst.setWeaponsStrengthLarge(70);   
        tempsst.setWeaponsStrengthHuge(40);    
	
        gw.addShipType(tempsst);

//###################  SMALL  ##########################################
        
        // Scout
        tempsst = new SpaceshipType("Scout","Sct",SpaceShipSize.SMALL,10,50,SpaceshipRange.LONG,1,3, 5,5);
        tempsst.setArmorSmall(20);
        tempsst.setDescription("Scout: Useful as a small transport and to scout enemy fleets");
        tempsst.setAdvantages("Long range, Not visible on map, Cheap");
        tempsst.setDisadvantages("Very poor vs all other ships, cannot siege planets");
        
        tempsst.setVisibleOnMap(false);
        tempsst.setCanBlockPlanet(false);
        tempsst.setScreened(true);
       
        gw.addShipType(tempsst);
        
        // Corvette
        tempsst = new SpaceshipType("Corvette","Crv",SpaceShipSize.SMALL,20,100,SpaceshipRange.LONG,2,6, 15,10);
        tempsst.setArmorSmall(40);
        tempsst.setDescription("Corvette: A small capital ship and VIP transport");
        tempsst.setAdvantages("Long range, Good vs Medium ships, Cheap");
        tempsst.setDisadvantages("Poor vs most capital ships");
        tempsst.setWeaponsStrengthMedium(20);
        tempsst.setWeaponsMaxSalvosMedium(2);
        gw.addShipType(tempsst);
        
        // Frigate
        tempsst = new SpaceshipType("Frigate","Frg",SpaceShipSize.SMALL,20,150,SpaceshipRange.LONG,3,7, 25,20);
        tempsst.setArmorSmall(40);
        tempsst.setDescription("An small Escort, used to defend against squadrons and other small ship ");
        gw.addShipType(tempsst);

//###################  MEDIUM  ##########################################
        
        // Troopship
        tempsst = new SpaceshipType("Troopship","Tpt",SpaceShipSize.MEDIUM,20,75,SpaceshipRange.LONG,2,8, 10,10);
        tempsst.setDescription("Troops on troopships can lower a planets resistance by 1");
        tempsst.setArmorSmall(0);
        tempsst.setPsychWarfare(1);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);

        // Destroyer
        tempsst = new SpaceshipType("Destroyer","Des",SpaceShipSize.MEDIUM,50,250,SpaceshipRange.LONG,6,19, 25,25);
        tempsst.setDescription("Useful for long range raiding attacks, and also boost larger task forces with Anti-Squadron coverage");
        tempsst.setArmorSmall(70);
        tempsst.setWeaponsStrengthMedium(40);    
        tempsst.setWeaponsStrengthLarge(20);   
        tempsst.setWeaponsStrengthHuge(50);    
		tempsst.setWeaponsMaxSalvosLarge(1);
		tempsst.setWeaponsMaxSalvosHuge(1);
        gw.addShipType(tempsst);

//      Submarine
        tempsst = new SpaceshipType("Submarine","Sub",SpaceShipSize.MEDIUM,25,150,SpaceshipRange.LONG,5,17, 10,5);
        tempsst.setDescription("A Submarine is a Stealth Torpedo carrier");
        tempsst.setArmorSmall(70);
        tempsst.setWeaponsStrengthMedium(10);    
        tempsst.setWeaponsStrengthLarge(300);   
        tempsst.setWeaponsStrengthHuge(300);    
		tempsst.setWeaponsMaxSalvosLarge(8);
		tempsst.setWeaponsMaxSalvosHuge(8);
		tempsst.setInitDefence(20);
		tempsst.setVisibleOnMap(false);
		tempsst.setCanAttackScreenedShips(true);
        gw.addShipType(tempsst);

//      Interdictor
        tempsst = new SpaceshipType("Interdictor","Int",SpaceShipSize.MEDIUM,100,350,SpaceshipRange.LONG,6,20, 10,10);
        tempsst.setDescription("Stop Fleeing ships");
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(1);
        tempsst.setWeaponsStrengthMedium(15);    
        tempsst.setWeaponsStrengthLarge(20);   
        tempsst.setWeaponsStrengthHuge(20);    
		tempsst.setWeaponsMaxSalvosLarge(1);
		tempsst.setWeaponsMaxSalvosHuge(1);
		tempsst.setPsychWarfare(1);
        tempsst.setNoRetreat(true);
        gw.addShipType(tempsst);

        // Carrier
        tempsst = new SpaceshipType("Carrier","Car",SpaceShipSize.MEDIUM,40,300,SpaceshipRange.LONG,6,18, 10,10);
        tempsst.setDescription("Backbone in every large fleet is the carrier and its large supply of Squadrons. Keep it Screened");
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(8);
        tempsst.setScreened(true);

        gw.addShipType(tempsst);        
        
//###################  LARGE  ##########################################

        // Commandship
        tempsst = new SpaceshipType("Commandship","Com",SpaceShipSize.LARGE,50,400,SpaceshipRange.SHORT,7,27, 15,10);
        tempsst.setDescription("The Natural Leader in any major task force, with its Iniative bonus it can help a smaller fleet beat an larger one");
        tempsst.setPsychWarfare(2);
        tempsst.setIncreaseInitiative(7);
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(2); 
        tempsst.setPlanetarySurvey(true);        
        tempsst.setWeaponsStrengthMedium(25);    
        tempsst.setWeaponsStrengthLarge(30);   
        tempsst.setWeaponsStrengthHuge(25);
	    
        tempsst.setWeaponsMaxSalvosLarge(2);
        tempsst.setWeaponsMaxSalvosHuge(3);

        gw.addShipType(tempsst);
   
        // Cruiser
        tempsst = new SpaceshipType("Cruiser","Cru",SpaceShipSize.LARGE,150,600,SpaceshipRange.SHORT,9,30, 20,15);
        tempsst.setDescription("Main battle unit, Great against most enemies");
        tempsst.setBombardment(1);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(75);
        
        tempsst.setWeaponsStrengthMedium(90);    
        tempsst.setWeaponsStrengthLarge(60);   
        tempsst.setWeaponsStrengthHuge(60);    
        tempsst.setWeaponsMaxSalvosLarge(6);
        tempsst.setWeaponsMaxSalvosHuge(6);

        
        tempsst.setArmorMedium(25);
        tempsst.setSquadronCapacity(2);
        gw.addShipType(tempsst);

//###################  HUGE  ##########################################
        
        // Battleship
        tempsst = new SpaceshipType("Battleship","Bat",SpaceShipSize.HUGE,300,2200,SpaceshipRange.SHORT,19,70, 15,10);
        tempsst.setDescription("Useful as flagship in major task forces, Its bombardment is feared amongst its enemies");
        tempsst.setArmorSmall(75);
        tempsst.setArmorMedium(80);
        tempsst.setArmorLarge(60);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(1);
       
        tempsst.setWeaponsStrengthMedium(50);    
        tempsst.setWeaponsStrengthLarge(100);   
        tempsst.setWeaponsStrengthHuge(100);    
	
	
        tempsst.setSquadronCapacity(2);
        gw.addShipType(tempsst);
        
        
        // Battlestar
        tempsst = new SpaceshipType("Battlestar","Star",SpaceShipSize.HUGE,800,4500,SpaceshipRange.SHORT,36,130, 15,10);
        tempsst.setDescription("Useful as flagship in major task forces, Its bombardment is feared amongst its enemies");
        tempsst.setArmorSmall(75);
        tempsst.setArmorMedium(80);
        tempsst.setArmorLarge(60);
        tempsst.setBombardment(5);
        tempsst.setPsychWarfare(1);
        tempsst.setWeaponsStrengthMedium(40);    
        tempsst.setWeaponsStrengthLarge(200);   
        tempsst.setWeaponsStrengthHuge(200);    
	
	
        tempsst.setSquadronCapacity(2);
        gw.addShipType(tempsst);
        
        
     


//###################  SQUADRONS  ##########################################
             
        // F1 Fighter
        tempsst = new SpaceshipType("Fighter Sqd","Fgt",SpaceShipSize.SQUADRON,10,50,SpaceshipRange.LONG,2,4, 15,40);
        tempsst.setArmorSmall(70);
        tempsst.setDescription("Best way of winning Sqadron Superiority is to have the most Fighter Sqds");
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        
        // B1 Bomber
        tempsst = new SpaceshipType("Bomber Sqd","Bom",SpaceShipSize.SQUADRON,10,50,SpaceshipRange.LONG,2,4, 20,10);
        tempsst.setArmorSmall(70);
        tempsst.setDescription("A Bomber Squadron can make a big difference against an Small & Medium taskforce");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setWeaponsStrengthMedium(40);
        tempsst.setWeaponsMaxSalvosMedium(8);
        
        gw.addShipType(tempsst);
        
        // T1 Torpedo boat
        tempsst = new SpaceshipType("Torpedo Boat","Tor",SpaceShipSize.SQUADRON,10,50,SpaceshipRange.LONG,2,4, 5,5);
        tempsst.setArmorSmall(70);
        tempsst.setDescription("A Torpedo Boat Squadron can make a big difference against an Large & Huge taskforce");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setWeaponsStrengthMedium(10);    
        tempsst.setWeaponsStrengthLarge(60);   
        tempsst.setWeaponsStrengthHuge(60);
	    
        tempsst.setWeaponsMaxSalvosLarge(3);
        tempsst.setWeaponsMaxSalvosHuge(3);
        
        gw.addShipType(tempsst);
        
        

        
//###################  Corporation  ##########################################
        
//###################  SMALL  ##########################################
        
        // Courier
        tempsst = new SpaceshipType("Courier","Cou",SpaceShipSize.SMALL,10,50,SpaceshipRange.LONG,1,3, 5,10);
        tempsst.setArmorSmall(20);
        tempsst.setDescription("Useful as scout ship, and can also be used to boost long range raiding forces and to transport VIPs");
        tempsst.setPlanetarySurvey(true);
        tempsst.setCanBlockPlanet(false);
        
        gw.addShipType(tempsst);
        
        // Raider
        tempsst = new SpaceshipType("Raider","Rai",SpaceShipSize.SMALL,10,130,SpaceshipRange.LONG,2,6, 15,15);
        tempsst.setArmorSmall(40);
        tempsst.setDescription("A raider and small VIP transport");
        tempsst.setWeaponsStrengthMedium(40);
        tempsst.setWeaponsMaxSalvosMedium(2);
        gw.addShipType(tempsst);
        
        // Carrack
        tempsst = new SpaceshipType("Carrack","Crk",SpaceShipSize.SMALL,10,170,SpaceshipRange.LONG,3,7, 20,20);
        tempsst.setArmorSmall(40);
        tempsst.setDescription("An small Escort, used to defend against squadrons and other small ship ");
        gw.addShipType(tempsst);

//###################  MEDIUM  ##########################################
        
        // Troop Galley
        tempsst = new SpaceshipType("Troop Galley","TrG",SpaceShipSize.MEDIUM,20,75,SpaceshipRange.LONG,2,8, 10,10);
        tempsst.setDescription("Troops on troopships can lower a planets resistance by 1");
        tempsst.setArmorSmall(0);
        tempsst.setPsychWarfare(1);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);

        // Crusader
        tempsst = new SpaceshipType("Crusader","Csd",SpaceShipSize.MEDIUM,10,280,SpaceshipRange.LONG,5,17, 20,40);
        tempsst.setDescription("Useful for long range raiding attacks, and also boost larger task forces with Anti-Squadron coverage");
        tempsst.setArmorSmall(70);
        tempsst.setWeaponsStrengthMedium(70);    
        gw.addShipType(tempsst);

//      Attack Submarine
        tempsst = new SpaceshipType("Attack Submarine","ASub",SpaceShipSize.MEDIUM,15,130,SpaceshipRange.LONG,6,20, 10,5);
        tempsst.setDescription("A Submarine is a Stealth Torpedo carrier");
        tempsst.setArmorSmall(70);
        tempsst.setWeaponsStrengthMedium(10);    
        tempsst.setWeaponsStrengthLarge(300);   
        tempsst.setWeaponsStrengthHuge(250);    
        tempsst.setWeaponsMaxSalvosLarge(16);
        tempsst.setWeaponsMaxSalvosHuge(8);
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setInitDefence(20);
		tempsst.setVisibleOnMap(false);
        gw.addShipType(tempsst);

//      Retaliator
        tempsst = new SpaceshipType("Retaliator","Ret",SpaceShipSize.MEDIUM,50,400,SpaceshipRange.LONG,7,25, 10,10);
        tempsst.setDescription("Stop Fleeing ships");
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(1);
        tempsst.setWeaponsStrengthMedium(15);    
        tempsst.setWeaponsStrengthLarge(50);   
        tempsst.setWeaponsStrengthHuge(50);    
		tempsst.setWeaponsMaxSalvosLarge(1);
		tempsst.setWeaponsMaxSalvosHuge(1);
		tempsst.setPsychWarfare(2);
		tempsst.setInitDefence(10);		
		tempsst.setNoRetreat(true);
        tempsst.setVisibleOnMap(false);
        gw.addShipType(tempsst);

        // Carrier
      //  tempsst = new SpaceshipType("Carrier2","Car2",SpaceShipSize.MEDIUM,40,300,SpaceshipRange.SHORT,4,25,uniqueShipIdCounter,10,10);
      //  tempsst.setDescription("Backbone in every large fleet is the carrier and its large supply of Squadrons. Keep it Screened");
      //  tempsst.setArmorSmall(50);
       // tempsst.setSquadronCapacity(8);

        //gw.addShipType(tempsst);        
        
//###################  LARGE  ##########################################

      
        
        // Fleet Galleon
        tempsst = new SpaceshipType("Fleet Galleon","FGa",SpaceShipSize.LARGE,25,300,SpaceshipRange.LONG,7,25, 15,10);
        tempsst.setDescription("The Natural Leader in any major task force, with its Iniative bonus it can help a smaller fleet beat an larger one");
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(5);
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(5); 
        tempsst.setPlanetarySurvey(true);        
        tempsst.setWeaponsStrengthMedium(15);    

        gw.addShipType(tempsst);
   
        // Dreadnaught
        tempsst = new SpaceshipType("Dreadnaught","Drd",SpaceShipSize.LARGE,75,900,SpaceshipRange.SHORT,11,36, 20,15);
        tempsst.setDescription("Main battle unit, Great against most enemies");
        tempsst.setBombardment(2);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(75);
        
        tempsst.setWeaponsStrengthMedium(80);   
        
        tempsst.setWeaponsStrengthLarge(120);
        tempsst.setWeaponsMaxSalvosLarge(3);
        tempsst.setWeaponsStrengthHuge(120);    
        tempsst.setWeaponsMaxSalvosHuge(3);

        
        tempsst.setArmorMedium(25);
        tempsst.setSquadronCapacity(3);
        gw.addShipType(tempsst);

//###################  HUGE  ##########################################
        
        // Juggernaut
        tempsst = new SpaceshipType("Juggernaut","Jug",SpaceShipSize.HUGE,350,3000,SpaceshipRange.SHORT,26,90, 15,10);
        tempsst.setDescription("Useful as flagship in major task forces, Its bombardment is feared amongst its enemies");
        tempsst.setArmorSmall(75);
        tempsst.setArmorMedium(80);
        tempsst.setArmorLarge(60);
        tempsst.setBombardment(4);
        tempsst.setPsychWarfare(1);
        tempsst.setPlayerUnique(true);
        tempsst.setWeaponsStrengthMedium(60);    
        tempsst.setWeaponsStrengthLarge(160);   
        tempsst.setWeaponsStrengthHuge(160);    
	
	
        tempsst.setSquadronCapacity(4);
        gw.addShipType(tempsst);
        
        
        // ManOWar
        tempsst = new SpaceshipType("ManOWar","MoW",SpaceShipSize.HUGE,100,1900,SpaceshipRange.SHORT,17,57, 15,10);
        tempsst.setDescription("Useful as flagship in major task forces, Its bombardment is feared amongst its enemies");
        tempsst.setArmorSmall(75);
        tempsst.setArmorMedium(80);
        tempsst.setArmorLarge(60);
        tempsst.setBombardment(2);
        tempsst.setPsychWarfare(1);
        tempsst.setWeaponsStrengthMedium(80);    
        tempsst.setWeaponsStrengthLarge(240);   
        tempsst.setWeaponsStrengthHuge(90);    
	
	
        tempsst.setSquadronCapacity(3);
        gw.addShipType(tempsst);
        

//###################  SQUADRONS  ##########################################
             
        // Interceptor
        tempsst = new SpaceshipType("Interceptor","Intc",SpaceShipSize.SQUADRON,5,55,SpaceshipRange.LONG,2,4, 15,35);
        tempsst.setDescription("Best way of winning Sqadron Superiority is to have the most Fighter Sqds");
        tempsst.setArmorSmall(70);
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        
        // Gunship
        tempsst = new SpaceshipType("Gunship","Gun",SpaceShipSize.SQUADRON,0,70,SpaceshipRange.LONG,2,4, 30,15);
        tempsst.setArmorSmall(70);
        tempsst.setDescription("A Bomber Squadron can make a big difference against an Small & Medium taskforce");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(20);
       
        
        gw.addShipType(tempsst);
        
        // Hawk Eye
        tempsst = new SpaceshipType("Hawk Eye","Hwk",SpaceShipSize.SQUADRON,5,60,SpaceshipRange.LONG,2,4, 5,5);
        tempsst.setArmorSmall(70);
        tempsst.setDescription("A Torpedo Boat Squadron can make a big difference against an Large & Huge taskforce");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(10);    
        tempsst.setWeaponsStrengthLarge(50);   
        tempsst.setWeaponsStrengthHuge(50);
        tempsst.setInitSupport(true);
        tempsst.setIncreaseInitiative(5);
        tempsst.setInitDefence(10);
	    
        tempsst.setWeaponsMaxSalvosLarge(1);
        tempsst.setWeaponsMaxSalvosHuge(1);
        
        gw.addShipType(tempsst);
        
        
        
        
//###################  CIVILIANS  ##########################################

        // Merchant
        tempsst = new SpaceshipType("Merchant Fleet","Mer",SpaceShipSize.LARGE,40,80,SpaceshipRange.SHORT,1,15, 5,5);
        tempsst.setDescription("Earn Extra credits by having one of these on each planet");
        tempsst.setArmorSmall(50);
        tempsst.setCanBlockPlanet(false);
        
    	tempsst.setIncOwnOpenBonus(3);
    	tempsst.setIncOwnClosedBonus(2);
    	tempsst.setIncFriendlyOpenBonus(3);
    
    	tempsst.setIncNeutralOpenBonus(4);
    	tempsst.setIncNeutralClosedBonus(3);
    	tempsst.setIncEnemyClosedBonus(5);
    	tempsst.setIncEnemyOpenBonus(6);        
        tempsst.setScreened(true);
        gw.addShipType(tempsst);

	// Supply Ship
        tempsst = new SpaceshipType("Supply Ship","Sup",SpaceShipSize.MEDIUM,40,80,SpaceshipRange.SHORT,3,8, 5,5);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setArmorSmall(50);
        tempsst.setSupply(SpaceShipSize.HUGE);
        tempsst.setCanBlockPlanet(false);
        tempsst.setScreened(true);
       
        tempsst.setDescription("Supply ships with new weapons is a must, otherwise they will be sitting ducks");
        gw.addShipType(tempsst);
        
        
        
        // Repairship
        //tempsst = new SpaceshipType("Repair dock","Rep",SpaceShipSize.MEDIUM,40,80,SpaceshipRange.SHORT,1,10,uniqueShipIdCounter,30);
        //tempsst.setDescription("An moveble repair dock, have one in each Task force to repair damaged ships");
        //tempsst.setArmorSmall(50);
        //tempsst.setTroops(true);
        //gw.addShipType(tempsst);

//###################  FACTIONS  ##########################################

        // Faction 1
        Faction tempFaction = new Faction("House of Mongolia",Faction.getColorHexString(25,70,196),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));


        
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);

        
        
        GameWorldCreator.addFaction(tempFaction, gw);

// Faction 1
        tempFaction = new Faction("Nippon Corporation",Faction.getColorHexString(210,210,210),neutral);
       
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));


        
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Scandinavian Tech",Faction.getColorHexString(231,255,32),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("American Systems",Faction.getColorHexString(32,189,255),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Emirates Trading Co.",Faction.getColorHexString(255,150,0),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("African Union",Faction.getColorHexString(53,202,67),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
                
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Thai Song Corp.",Faction.getColorHexString(242,130,56),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("United Nations",Faction.getColorHexString(56,242,139),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Hindustan Infotech Ltd",Faction.getColorHexString(56,183,242),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Latin BioTech Co",Faction.getColorHexString(139,242,56),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battlestar"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troopship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

       
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Galinka Empire",Faction.getColorHexString(215,58,32),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Juggernaut"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("ManOWar"));
        

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Dreadnaught"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fleet Galleon"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Crusader"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troop Galley"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Retaliator"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Attack Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Courier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrack"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Raider"));
        
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interceptor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Gunship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Hawk Eye"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Courier"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troop Galley"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Raider"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Carrack"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Carrack"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
         
        GameWorldCreator.addFaction(tempFaction, gw);
        
        // Faction 1
        tempFaction = new Faction("Australian Empire",Faction.getColorHexString(142,142,142),neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Juggernaut"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("ManOWar"));
        

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Dreadnaught"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fleet Galleon"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Crusader"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troop Galley"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Retaliator"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Attack Submarine"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Courier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrack"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Raider"));
        
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interceptor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Gunship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Hawk Eye"));
        
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        
        
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Courier"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Troop Galley"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Raider"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Carrack"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Carrack"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));
        
        // Adding all buildings to the faction.
            tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        
        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        
        
        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);
        
        
        
        GameWorldCreator.addFaction(tempFaction, gw);

        // add shiptypes for neutral planets
        gw.setNeutralSize1(gw.getSpaceshipTypeByName("Defence Platform I"));
        gw.setNeutralSize2(gw.getSpaceshipTypeByName("Defence Platform II"));
        gw.setNeutralSize3(gw.getSpaceshipTypeByName("Defence Platform III"));


	return gw;
	}	
	
}