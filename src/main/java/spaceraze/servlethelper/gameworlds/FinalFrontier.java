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

public class FinalFrontier {


    public static GameWorld getGameWorld() {
        // XXX Spaceraze Expanded
        GameWorld gw = new GameWorld();

        gw.setUuid(UUID.randomUUID().toString());

        gw.setFileName("FFrontier");

        gw.setFullName("Final Frontier");


        gw.setBattleSimDefaultShips1("crv;crv;crv;sct");
        gw.setBattleSimDefaultShips2("[4]DPI");

        gw.setShortDescription("An Advanced beginner to intermediate world based on that all factions shall have the same ship types. The World Still have many advanced features of the Spaceraze design.");
        gw.setHistory("Corporations has taken control over the known universe, in the Year 3051 an Universal war breaks out, smaller neutral systems tries to avoid to get involved. Instead they are just considered as pawns in a bigger game, there alliances and borders are decided by the larger richer corporations");


        gw.setCreatedDate("2005-10-26");
        gw.setChangedDate("2005-10-27");
        gw.setCreatedByUser("Niohl");

        gw.setRazedPlanetChance(5);
        gw.setClosedNeutralPlanetChance(30);

        gw.setAlignments(AlignmentHelper.createDefaultAlignments(gw));
        Alignment neutral = AlignmentPureFunctions.findAlignmentByName("neutral", gw.getAlignments());

        Corruption tmpCorruption = new Corruption();
        tmpCorruption.addBreakpoint(25, 5);
        tmpCorruption.addBreakpoint(50, 10);
        tmpCorruption.addBreakpoint(75, 15);
        tmpCorruption.addBreakpoint(100, 20);
        tmpCorruption.addBreakpoint(125, 25);
        tmpCorruption.addBreakpoint(150, 50);
//###################  GOV  ##########################################


        VIPType tmpVipType = new VIPType("Governor", "Gov", neutral);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setOpenIncBonus(1);
        gw.addVipType(tmpVipType);

        VIPType govType = tmpVipType;

//    	###################  VIP  ##########################################	        


        tmpVipType = new VIPType("Master Spy", "Spy", neutral);
        tmpVipType.setBuildCost(20);
        tmpVipType.setUpkeep(3);
        tmpVipType.setAssassination(50);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(50);

        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Commando Unit", "CU", neutral);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(1);
        tmpVipType.setAssassination(60);
        tmpVipType.setPsychWarfareBonus(2);
        tmpVipType.setResistanceBonus(5);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Assassin", "Ass", neutral);
        tmpVipType.setBuildCost(30);
        tmpVipType.setUpkeep(2);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(70);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("General", "Gen", neutral);
        tmpVipType.setAssassination(20);
        tmpVipType.setBuildCost(30);
        tmpVipType.setUpkeep(3);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Astrogator", "Ast", neutral);
        tmpVipType.setAssassination(20);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        tmpVipType.setFTLbonus(true);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Admiral", "Adm", neutral);
        tmpVipType.setAssassination(20);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setInitBonus(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Top Ace", "Ace", neutral);
        tmpVipType.setInitFighterSquadronBonus(5);
        tmpVipType.setBuildCost(20);
        tmpVipType.setUpkeep(3);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setDescription("The Top Ace, gives the squadrons better inititive. A top ace is a squadron�s pilot and must be on a squadron to give bonus. He boosts all squadrons in a fleet.");
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Economic Genious", "Eco", neutral);
        tmpVipType.setAssassination(20);
        tmpVipType.setUpkeep(1);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setOpenIncBonus(4);
        tmpVipType.setClosedIncBonus(2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Spaceship Engineer", "Eng", neutral);
        tmpVipType.setAssassination(30);
        tmpVipType.setBuildCost(30);
        tmpVipType.setUpkeep(4);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setShipBuildBonus(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Scientist", "Sci", neutral);
        tmpVipType.setAssassination(30);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Expert Scientist", "ESci", neutral);
        tmpVipType.setAssassination(20);
        tmpVipType.setBuildCost(50);
        tmpVipType.setUpkeep(5);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(20);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Diplomat", "Dip", neutral);
        tmpVipType.setAssassination(30);
        tmpVipType.setBuildCost(40);
        tmpVipType.setUpkeep(4);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        gw.addVipType(tmpVipType);

//###################  Buildings  ##########################################
        List<BuildingType> tempBuildings = new ArrayList<>();
        BuildingType tempBuildingType;

        tempBuildingType = new BuildingType("Small Wharf", "W1", 5);
        tempBuildingType.setWharfSize(1);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setDescription("Small Wharf what can build small ships");
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Medium Wharf", "W2", 10);
        tempBuildingType.setWharfSize(2);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setDescription("Medium Wharf what can build small and medium ships");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Small Wharf"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Large Wharf", "W3", 20);
        tempBuildingType.setWharfSize(3);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Large Wharf what can build small, medium and large ships");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Medium Wharf"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Huge Wharf", "W5", 30);
        tempBuildingType.setWharfSize(5);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.setDescription("Huge Wharf what can build small, medium, large and huge ships");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Large Wharf"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Military Spaceport", "MSP", 12);
        tempBuildingType.setSpaceport(true);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Build this building on the planets and long range ways will be short range between planetes whit spaceports");
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Commercial Spaceport", "CSP", 12);
        tempBuildingType.setSpaceport(true);
        tempBuildingType.setOpenPlanetBonus(2);
        tempBuildingType.setInOrbit(true);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Military Spaceport"));
        tempBuildingType.setDescription("Extending the military Spaceport to become a commercial hub");
        tempBuildings.add(tempBuildingType);


//      ###################  New Building Types  ##########################################

        tempBuildingType = new BuildingType("Improved Infrastructure", "IIS", 12);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Preparation to build, advanced buildings");
        tempBuildings.add(tempBuildingType);


        tempBuildingType = new BuildingType("Security Center", "SC", 20);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Assassin"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Master Spy"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Commando Unit"));
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Security Center to train undercover VIPs");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Improved Infrastructure"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Military Academy", "MAC", 20);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Admiral"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Top Ace"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("General"));
        tempBuildingType.addBuildVIPType(gw.getVIPTypeByName("Astrogator"));
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Academy to train military VIPs");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Improved Infrastructure"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Advanced University", "AU", 20);
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


        tempBuildingType = new BuildingType("Universal Trade Center", "UTC", 30);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.setOpenPlanetBonus(6);
        tempBuildingType.setResistanceBonus(2);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Universal Trade Center gives 6 in income if the planet is open. Gives bonus to the defending ground force. Only one Universal Trade Center can be build each player.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Improved Infrastructure"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Faction Capital", "FCAP", 30);
        tempBuildingType.setOpenPlanetBonus(8);
        tempBuildingType.setResistanceBonus(5);
        tempBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setFactionUnique(true);
        tempBuildingType.setDescription("Capital that gives 8 in incom if the planet is open. Gives a big bonus to the defending ground force. Only one Capital can be build for each faction.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Universal Trade Center"));
        tempBuildings.add(tempBuildingType);
        /*  
        tempBuildingType= new BuildingType("Barracks", "Bar", 10, uniqueBuildingIdCounter);
        tempBuildingType.setTroopSize(1);
        tempBuildingType.addTypeOfTroop(TypeOfTroop.INFANTRY);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Barracks to train infantary");
        tempBuildings.add(tempBuildingType);
        
       
        tempBuildingType= new BuildingType("Large Barracks", "LB", 10, uniqueBuildingIdCounter);
        tempBuildingType.setTroopSize(3);
        tempBuildingType.addTypeOfTroop(TypeOfTroop.INFANTRY);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("Barracks to train up to 3 infantarys");
        tempBuildingType.setParentBuildingType(tempBuildings.getBuildingType("Barracks"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("China Military Center", "CMC", 10, uniqueBuildingIdCounter);
        tempBuildingType.setTroopSize(5);
        tempBuildingType.addTypeOfTroop(TypeOfTroop.INFANTRY);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.setDescription("Military center to train up to 5 infantarys");
        tempBuildingType.setParentBuildingType(tempBuildings.getBuildingType("Large Barracks"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Tech Center", "TC", 10, uniqueBuildingIdCounter);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setTechBonus(6);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Tech center give better technology to units.");
        tempBuildingType.setParentBuildingType(tempBuildings.getBuildingType("Company Base"));
        tempBuildings.add(tempBuildingType);
        
        tempBuildingType= new BuildingType("Advanced Tech Center", "ATC", 12, uniqueBuildingIdCounter);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setTechBonus(12);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setDescription("Advanced tech center gives even better technology to units than tech center do.");
        tempBuildingType.setParentBuildingType(tempBuildings.getBuildingType("Tech Center"));
        tempBuildings.add(tempBuildingType);
        
        */

        tempBuildingType = new BuildingType("Basic Shield", "BS", 20);
        tempBuildingType.setShieldCapacity(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A small shield to defend planets from bombardment.");
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Advanced Shield", "AS", 60);
        tempBuildingType.setShieldCapacity(2);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlayerUnique(true);
        tempBuildingType.setDescription("Advanced Shield, Defends planets from all bombardments.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Basic Shield"));
        tempBuildings.add(tempBuildingType);


        tempBuildingType = new BuildingType("Missile Silo", "MS", 15);
        tempBuildingType.setCannonDamage(100);
        tempBuildingType.setCannonHitChance(75);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based missile system to shoot down ships what besiege the planet.");
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Missile Defence", "MD", 25);
        tempBuildingType.setCannonDamage(100);
        tempBuildingType.setCannonHitChance(95);
        tempBuildingType.setCannonRateOfFire(3);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based missile system to shoot down ships in great numbers, that besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Missile Silo"));
        tempBuildings.add(tempBuildingType);


        tempBuildingType = new BuildingType("Cannon", "Can", 10);
        tempBuildingType.setCannonDamage(50);
        tempBuildingType.setCannonHitChance(50);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based Cannon to shoot down small ships what besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Company Base"));
        tempBuildings.add(tempBuildingType);

        tempBuildingType = new BuildingType("Heavy Cannon", "HC", 25);
        tempBuildingType.setCannonDamage(250);
        tempBuildingType.setCannonHitChance(75);
        tempBuildingType.setCannonRateOfFire(1);
        tempBuildingType.setInOrbit(false);
        tempBuildingType.setPlanetUnique(true);
        tempBuildingType.setDescription("A planet based heavy Cannon to shoot down ships what besiege the planet.");
        tempBuildingType.setParentBuildingType(GameWorldHandler.getBuildingTypeUuid(tempBuildings, "Cannon"));
        tempBuildings.add(tempBuildingType);


//###################  DEFENCE  ##########################################


        // Golan I
        SpaceshipType tempsst = new SpaceshipType("Defence Platform I", "DPI", SpaceShipSize.SMALL, 20, 100, SpaceshipRange.NONE, 1, 3, 15, 25);
        tempsst.setDescription("Small non-mobile defence unit");
        tempsst.setArmorSmall(40);
        tempsst.setWeaponsStrengthMedium(10);

        tempsst.setIncreaseInitiative(2);

        tempsst.setCanAppearOnBlackMarket(false);
        gw.addShipType(tempsst);

        // Golan II
        tempsst = new SpaceshipType("Defence Platform II", "DPII", SpaceShipSize.MEDIUM, 50, 400, SpaceshipRange.NONE, 2, 8, 30, 25);
        tempsst.setDescription("Medium non-mobile defence unit");
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setArmorSmall(70);
        tempsst.setArmorMedium(60);

        tempsst.setIncreaseInitiative(2);

        tempsst.setWeaponsStrengthMedium(30);

        gw.addShipType(tempsst);

        // Golan III
        tempsst = new SpaceshipType("Defence Platform III", "DPIII", SpaceShipSize.LARGE, 100, 800, SpaceshipRange.NONE, 3, 16, 10, 25);
        tempsst.setDescription("Large non-mobile defence unit");
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(70);
        tempsst.setArmorLarge(70);

        tempsst.setIncreaseInitiative(3);

        tempsst.setWeaponsStrengthMedium(50);
        tempsst.setWeaponsStrengthLarge(70);
        tempsst.setWeaponsStrengthHuge(90);

        gw.addShipType(tempsst);
        // Home Base
        tempsst = new SpaceshipType("Head Quarter", "HQ", SpaceShipSize.LARGE, 400, 1600, SpaceshipRange.NONE, 0, 160, 20, 25);
        tempsst.setDescription("Defence and trade plattform ");
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(70);
        tempsst.setArmorLarge(70);

        tempsst.setIncreaseInitiative(4);

        tempsst.setIncOwnOpenBonus(3);
        tempsst.setIncOwnClosedBonus(3);

        tempsst.setWeaponsStrengthMedium(50);
        tempsst.setWeaponsStrengthLarge(70);
        tempsst.setWeaponsStrengthHuge(90);

        gw.addShipType(tempsst);

//###################  SMALL  ##########################################

        // Scout
        tempsst = new SpaceshipType("Scout", "Sct", SpaceShipSize.SMALL, 10, 50, SpaceshipRange.LONG, 1, 3, 5, 5);
        tempsst.setDescription("Useful as scout ship, and can also be used to boost long range raiding forces and to transport VIPs");
        tempsst.setVisibleOnMap(false);
        tempsst.setPlanetarySurvey(true);
        tempsst.setCanBlockPlanet(false);

        gw.addShipType(tempsst);

        // Corvette
        tempsst = new SpaceshipType("Corvette", "Crv", SpaceShipSize.SMALL, 20, 100, SpaceshipRange.LONG, 2, 6, 15, 10);
        tempsst.setDescription("A raider and small VIP transport");
        tempsst.setWeaponsStrengthMedium(20);
        tempsst.setWeaponsMaxSalvosMedium(2);
        gw.addShipType(tempsst);

//###################  MEDIUM  ##########################################

        // Troopship
        tempsst = new SpaceshipType("Troopship", "Tpt", SpaceShipSize.MEDIUM, 20, 75, SpaceshipRange.LONG, 2, 12, 10, 10);
        tempsst.setDescription("Troops on troopships can lower a planets resistance by 1");
        tempsst.setArmorSmall(0);
        tempsst.setPsychWarfare(1);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);

        // Destroyer
        tempsst = new SpaceshipType("Destroyer", "Des", SpaceShipSize.MEDIUM, 50, 200, SpaceshipRange.LONG, 4, 20, 25, 25);
        tempsst.setDescription("Useful for long range raiding attacks, and also boost larger task forces with Anti-Squadron coverage");
        tempsst.setArmorSmall(70);
        tempsst.setSquadronCapacity(1);
        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setWeaponsStrengthHuge(50);
        tempsst.setWeaponsMaxSalvosLarge(1);
        tempsst.setWeaponsMaxSalvosHuge(1);
        gw.addShipType(tempsst);

//###################  LARGE  ##########################################

        // Carrier
        tempsst = new SpaceshipType("Carrier", "Car", SpaceShipSize.LARGE, 40, 150, SpaceshipRange.SHORT, 4, 25, 10, 10);
        tempsst.setDescription("Backbone in every large fleet is the carrier and its large supply of Squadrons. Keep it Screened");
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(8);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);

        // Cruiser
        tempsst = new SpaceshipType("Cruiser", "Cru", SpaceShipSize.LARGE, 150, 600, SpaceshipRange.SHORT, 5, 30, 20, 15);
        tempsst.setDescription("Main battle unit, Great against most enemies");
        tempsst.setBombardment(1);
        tempsst.setArmorSmall(90);
        tempsst.setArmorMedium(75);

        tempsst.setWeaponsStrengthMedium(60);
        tempsst.setWeaponsStrengthLarge(60);
        tempsst.setWeaponsStrengthHuge(50);

        tempsst.setWeaponsMaxSalvosHuge(2);


        tempsst.setArmorMedium(25);
        tempsst.setSquadronCapacity(2);
        gw.addShipType(tempsst);

//###################  HUGE  ##########################################

        // Battleship
        tempsst = new SpaceshipType("Battleship", "Bat", SpaceShipSize.HUGE, 300, 2200, SpaceshipRange.SHORT, 10, 70, 15, 10);
        tempsst.setDescription("Useful as flagship in major task forces, Its bombardment is feared amongst its enemies");
        tempsst.setArmorSmall(75);
        tempsst.setArmorMedium(80);
        tempsst.setArmorLarge(60);
        tempsst.setBombardment(3);

        tempsst.setWeaponsStrengthMedium(50);
        tempsst.setWeaponsStrengthLarge(100);
        tempsst.setWeaponsStrengthHuge(100);


        tempsst.setSquadronCapacity(2);
        gw.addShipType(tempsst);

        // Commandship
        tempsst = new SpaceshipType("Commandship", "Com", SpaceShipSize.HUGE, 50, 400, SpaceshipRange.SHORT, 10, 50, 15, 10);
        tempsst.setDescription("The Natural Leader in any major task force, with its Iniative bonus it can help a smaller fleet beat an larger one");
        tempsst.setPsychWarfare(2);
        tempsst.setIncreaseInitiative(7);
        tempsst.setArmorSmall(50);
        tempsst.setSquadronCapacity(4);

        tempsst.setPlanetarySurvey(true);
        tempsst.setNoRetreat(true);

        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsStrengthLarge(70);
        tempsst.setWeaponsStrengthHuge(100);

        tempsst.setWeaponsMaxSalvosLarge(2);
        tempsst.setWeaponsMaxSalvosHuge(3);

        gw.addShipType(tempsst);


//###################  SQUADRONS  ##########################################

        // F1 Fighter
        tempsst = new SpaceshipType("Fighter Sqd", "Fgt", SpaceShipSize.SQUADRON, 10, 50, SpaceshipRange.LONG, 2, 4, 15, 40);
        tempsst.setDescription("Best way of winning Sqadron Superiority is to have the most Fighter Sqds");
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);

        // B1 Bomber
        tempsst = new SpaceshipType("Bomber Sqd", "Bom", SpaceShipSize.SQUADRON, 10, 50, SpaceshipRange.LONG, 2, 4, 20, 10);
        tempsst.setDescription("A Bomber Squadron can make a big difference against an Small & Medium taskforce");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setWeaponsStrengthMedium(40);
        tempsst.setWeaponsMaxSalvosMedium(8);

        gw.addShipType(tempsst);

        // T1 Torpedo boat
        tempsst = new SpaceshipType("Torpedo Boat", "Tor", SpaceShipSize.SQUADRON, 10, 50, SpaceshipRange.LONG, 2, 4, 5, 5);
        tempsst.setDescription("A Torpedo Boat Squadron can make a big difference against an Large & Huge taskforce");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setCanAttackScreenedShips(true);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsStrengthLarge(60);
        tempsst.setWeaponsStrengthHuge(60);

        tempsst.setWeaponsMaxSalvosLarge(3);
        tempsst.setWeaponsMaxSalvosHuge(3);

        gw.addShipType(tempsst);

//###################  CIVILIANS  ##########################################

        // Merchant
        tempsst = new SpaceshipType("Merchant Fleet", "Mer", SpaceShipSize.LARGE, 40, 80, SpaceshipRange.SHORT, 1, 15, 5, 5);
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
        tempsst = new SpaceshipType("Supply Ship", "Sup", SpaceShipSize.MEDIUM, 40, 80, SpaceshipRange.SHORT, 3, 8, 5, 5);
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
        Faction tempFaction = new Faction("United Euro Industries", Faction.getColorHexString(25, 70, 196), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));


        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));


        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);

        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

// Faction 1
        tempFaction = new Faction("Nippon Corporation", Faction.getColorHexString(210, 210, 210), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Scandinavian Tech", Faction.getColorHexString(231, 255, 32), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("American Systems", Faction.getColorHexString(32, 189, 255), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Emirates Trading Co.", Faction.getColorHexString(255, 150, 0), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("African Union", Faction.getColorHexString(53, 202, 67), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);

        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Thai Song Corp.", Faction.getColorHexString(242, 130, 56), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("United Nations", Faction.getColorHexString(56, 242, 139), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Hindustan Infotech Ltd", Faction.getColorHexString(56, 183, 242), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Latin BioTech Co", Faction.getColorHexString(139, 242, 56), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);


        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);


        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Galinka Russian Tech", Faction.getColorHexString(215, 58, 32), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());

        tempFaction.setResistanceBonus(1);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);

        tempFaction.setCanReconstruct(true);
        tempFaction.setReconstructCostBase(4);

        GameWorldCreator.addFaction(tempFaction, gw);

        // Faction 1
        tempFaction = new Faction("Australengineers", Faction.getColorHexString(142, 142, 142), neutral);
        tempFaction.setDescription("Bla bla blaaah....");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Defence Platform III"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Battleship"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Commandship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Carrier"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Troopship"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Fighter Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Bomber Sqd"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Torpedo Boat"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Merchant Fleet"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Ship"));
        //tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Repair dock"));

        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Head Quarter"));

        // Adding all buildings to the faction.
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Wharf"));

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