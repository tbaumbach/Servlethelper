package spaceraze.servlethelper.gameworlds;

import spaceraze.servlethelper.game.AlignmentHelper;
import spaceraze.servlethelper.game.GameWorldCreator;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.world.Alignment;
import spaceraze.world.BuildingType;
import spaceraze.world.Faction;
import spaceraze.world.GameWorld;
import spaceraze.world.SpaceshipType;
import spaceraze.world.UniqueIdCounter;
import spaceraze.world.VIPType;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.world.diplomacy.DiplomacyRelation;
import spaceraze.world.diplomacy.GameWorldDiplomacy;
import spaceraze.world.enums.BlackMarketFrequency;
import spaceraze.world.enums.SpaceShipSize;
import spaceraze.world.enums.SpaceshipRange;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceRazeClassic{

	public static GameWorld getGameWorld(){
		GameWorld gw = new GameWorld();
        gw.setUuid(UUID.randomUUID().toString());
        gw.setFileName("srclassic");

        gw.setFullName("SpaceRaze Classic");
        gw.setDescription("The classic gameworld used during the development of SpaceRaze");
        
        gw.setBattleSimDefaultShips1("[2]crv;nebb;stc(s)");
        gw.setBattleSimDefaultShips2("[3]gi");

		gw.setCreatedDate("2005-09-26");
		gw.setChangedDate("2005-11-08");
		gw.setCreatedByUser("pabod");

		gw.setAdjustScreenedStatus(false);

//		gw.setSquadronsSurviveOutsideCarriers(false);

		gw.setAlignments(AlignmentHelper.createDefaultAlignments(gw));
		Alignment neutral = AlignmentHelper.findAlignment("neutral", gw.getAlignments());
		Alignment good = AlignmentHelper.findAlignment("good", gw.getAlignments());
		Alignment evil = AlignmentHelper.findAlignment("evil", gw.getAlignments());

		// Spaceship types
        UniqueIdCounter uniqueShipIdCounter = new UniqueIdCounter();

        // Golan I
        SpaceshipType tempsst = new SpaceshipType("Golan I","GI", SpaceShipSize.SMALL,10,50,SpaceshipRange.NONE,1,3, 10);
        gw.addShipType(tempsst);
        // Golan II
        tempsst = new SpaceshipType("Golan II","GII",SpaceShipSize.MEDIUM,40,90,SpaceshipRange.NONE,1,6, 10);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(30);
        tempsst.setArmorSmall(0);
        gw.addShipType(tempsst);
        // Golan IIB
        tempsst = new SpaceshipType("Golan IIB","GIIB",SpaceShipSize.MEDIUM,50,120,SpaceshipRange.NONE,1,7, 10);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(20);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(20);
        tempsst.setArmorSmall(0);
        gw.addShipType(tempsst);
        // Golan III
        tempsst = new SpaceshipType("Golan III","GIII",SpaceShipSize.LARGE,60,140,SpaceshipRange.NONE,2,9, 10);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(30);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(30);
        tempsst.setWeaponsStrengthHuge(10);
        tempsst.setWeaponsMaxSalvosHuge(30);
        tempsst.setIncreaseInitiative(10);
        tempsst.setInitSupport(true);
        tempsst.setArmorSmall(0);
        tempsst.setArmorMedium(0);
        gw.addShipType(tempsst);
        // Corvette
        tempsst = new SpaceshipType("Corvette","Crv",SpaceShipSize.SMALL,5,15,SpaceshipRange.LONG,1,3, 5);
        gw.addShipType(tempsst);
        // StC
        tempsst = new SpaceshipType("Strike Cruiser","StC",SpaceShipSize.SMALL,5,20,SpaceshipRange.LONG,2,6, 5);
        tempsst.setPsychWarfare(1);
        gw.addShipType(tempsst);
        // SfS
        tempsst = new SpaceshipType("Starfighter Squadron","SfS",SpaceShipSize.SMALL,0,30,SpaceshipRange.LONG,3,4, 10);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(2);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(2);
        tempsst.setInitSupport(true);
        tempsst.setIncreaseInitiative(10);
        gw.addShipType(tempsst);
        // Nebulon A Frigate
        tempsst = new SpaceshipType("Nebulon A frigate","NebA",SpaceShipSize.SMALL,20,60,SpaceshipRange.LONG,3,7, 15);
        gw.addShipType(tempsst);
        // Lancer
        tempsst = new SpaceshipType("Lancer frigate","Lan",SpaceShipSize.SMALL,20,50,SpaceshipRange.LONG,3,8, 10);
        tempsst.setInitDefence(10);
        gw.addShipType(tempsst);
        // Supply Freighter
        tempsst = new SpaceshipType("Supply Freighter","SF",SpaceShipSize.MEDIUM,10,20,SpaceshipRange.SHORT,3,8, 5);
    	tempsst.setSupply(SpaceShipSize.HUGE);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        // Nebulon B frigate
        tempsst = new SpaceshipType("Nebulon B frigate","NebB",SpaceShipSize.MEDIUM,40,80,SpaceshipRange.LONG,4,10, 20);
        tempsst.setBombardment(1);
        tempsst.setArmorSmall(0);
        gw.addShipType(tempsst);
        // Escort Carrier
        tempsst = new SpaceshipType("Escort Carrier","EsC",SpaceShipSize.MEDIUM,30,80,SpaceshipRange.LONG,4,12, 10);
        tempsst.setIncreaseInitiative(20);
        tempsst.setInitSupport(true);
        tempsst.setArmorSmall(0);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        // Interdictor
        tempsst = new SpaceshipType("Interdictor","Int",SpaceShipSize.MEDIUM,30,70,SpaceshipRange.LONG,4,14, 10);
        tempsst.setNoRetreat(true);
        tempsst.setPsychWarfare(2);
        tempsst.setArmorSmall(0);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
       	// Dreadnaught
       	tempsst = new SpaceshipType("Dreadnaught","Drd",SpaceShipSize.MEDIUM,50,80,SpaceshipRange.LONG,4,14, 20);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(4);
        tempsst.setArmorSmall(0);
        gw.addShipType(tempsst);
        // Victory Star Destroyer
        tempsst = new SpaceshipType("Victory Star Destroyer","VSD",SpaceShipSize.LARGE,70,180,SpaceshipRange.SHORT,5,18, 20);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(10);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(10);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(10);
        tempsst.setArmorSmall(0);
        tempsst.setArmorMedium(0);
        gw.addShipType(tempsst);
        // Mon Calamari Cruiser
        tempsst = new SpaceshipType("Mon Calamari Cruiser","MCC",SpaceShipSize.LARGE,80,200,SpaceshipRange.SHORT,6,20, 20);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(15);
        tempsst.setWeaponsStrengthLarge(15);
        tempsst.setWeaponsMaxSalvosLarge(15);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(20);
        tempsst.setArmorSmall(0);
        tempsst.setArmorMedium(0);
        gw.addShipType(tempsst);
        // Imperial Star Destroyer
        tempsst = new SpaceshipType("Imperial Star Destroyer","ISD",SpaceShipSize.LARGE,100,250,SpaceshipRange.SHORT,8,24, 20);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(20);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(20);
        tempsst.setBombardment(2);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(30);
        tempsst.setArmorSmall(0);
        tempsst.setArmorMedium(0);
        gw.addShipType(tempsst);
        // Super Star Destroyer
        tempsst = new SpaceshipType("Super Star Destroyer","SSD",SpaceShipSize.HUGE,120,350,SpaceshipRange.SHORT,12,40, 25);
        tempsst.setWeaponsStrengthMedium(20);
        tempsst.setWeaponsMaxSalvosMedium(25);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(25);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(40);
        tempsst.setArmorSmall(0);
        tempsst.setArmorMedium(0);
        tempsst.setArmorLarge(0);
        gw.addShipType(tempsst);

        // add shiptypes for neutral planets
        gw.setNeutralSize1(gw.getSpaceshipTypeByName("Golan I"));
        gw.setNeutralSize2(gw.getSpaceshipTypeByName("Golan II"));
        gw.setNeutralSize3(gw.getSpaceshipTypeByName("Golan III"));

        // vip types
        UniqueIdCounter uniqueVIPIdCounter = new UniqueIdCounter();

        VIPType tmpVipType = new VIPType("Governor","Gov",neutral);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setOpenIncBonus(1);
        tmpVipType.setResistanceBonus(2);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        gw.addVipType(tmpVipType);
        
        VIPType govType = tmpVipType;

        tmpVipType = new VIPType("Dark Jedi","DJ",evil);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitBonus(10);
//        tmpVipType.setAlignment(Alignment.EVIL);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setAssassination(50);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Light Jedi","LJ",good);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitBonus(10);
//        tmpVipType.setAlignment(Alignment.GOOD);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(50);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Master Spy","Spy",neutral);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(50);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Economic Genius","Eco",neutral);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setOpenIncBonus(3);
        tmpVipType.setClosedIncBonus(1);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("General","Gen",neutral);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setResistanceBonus(3);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Admiral","Adm",neutral);
        tmpVipType.setInitBonus(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Top Ace","Ace",neutral);
        tmpVipType.setInitBonus(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Assassin","Ass",neutral);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(50);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Expert Engineer","Eng",neutral);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(10);
/*        tmpVipType.setSmallBuildBonus(1);
        tmpVipType.setMediumBuildBonus(2);
        tmpVipType.setLargeBuildBonus(3);
        tmpVipType.setHugeBuildBonus(10);
        tmpVipType.setWharfBuildBonus(2);
        tmpVipType.setWharfUpgradeBonus(2);*/
        tmpVipType.setShipBuildBonus(30);
        tmpVipType.setBuildingBuildBonus(30);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("AA Master Gunner","AA",neutral);
        tmpVipType.setInitDefence(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("FTL Master","FTL",neutral);
        tmpVipType.setFTLbonus(true);
        gw.addVipType(tmpVipType);
        
        // Buildings
        // *********
      
        List<BuildingType> tempBuildings = new ArrayList<>();
		BuildingType tmpBuildingType = null;
        
		// orbital wharfs
        tmpBuildingType = new BuildingType("Small Orbital Wharf", "W1", 5);
        tmpBuildingType.setWharfSize(1);
        tmpBuildingType.setInOrbit(true);
        BuildingType parent = tmpBuildingType;
        tempBuildings.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Medium Orbital Wharf", "W2", 10);
        tmpBuildingType.setWharfSize(2);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName(parent.getName());
        parent = tmpBuildingType;
        tempBuildings.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Large Orbital Wharf", "W3", 10);
        tmpBuildingType.setWharfSize(3);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName(parent.getName());
        parent = tmpBuildingType;
        tempBuildings.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Huge Orbital Wharf", "W5", 10);
        tmpBuildingType.setWharfSize(5);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName(parent.getName());
        tempBuildings.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Space Station", "SS", 15);
        tmpBuildingType.setOpenPlanetBonus(2);
        tmpBuildingType.setClosedPlanetBonus(1);
        tmpBuildingType.setTechBonus(10);
        tmpBuildingType.setSpaceport(true);
        tempBuildings.add(tmpBuildingType);        

        // Factions
        // empire
        Faction tempFaction = new Faction("Empire",Faction.getColorHexString(240,35,45),evil);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan III"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Lancer frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Imperial Star Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Super Star Destroyer"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Nebulon B frigate", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Golan II", gw));
        tempFaction.setResistanceBonus(2);
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Orbital Wharf"));
        tempFaction.setGovernorVIPType(govType);
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction empireFaction = tempFaction;

        // rebels
        tempFaction = new Faction("Rebel",Faction.getColorHexString(0,255,0),good);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan III"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Starfighter Squadron"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Dreadnaught"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Mon Calamari Cruiser"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Dreadnaught", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Golan II", gw));
        tempFaction.setClosedPlanetBonus(1);
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Orbital Wharf"));
        tempFaction.setGovernorVIPType(govType);
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction rebelFaction = tempFaction;

        // league
        tempFaction = new Faction("League",Faction.getColorHexString(24,66,255),neutral);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan IIB"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan III"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Escort Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Victory Star Destroyer"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Nebulon B frigate", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Golan II", gw));
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setGovernorVIPType(govType);
        tempFaction.setBuildings(tempBuildings);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Orbital Wharf"));

//        tempFaction.setOrbitalStructure(os1);
//        tempFaction.setBuildOrbitalStructureCostBase(7);
//        tempFaction.setBuildOrbitalStructureCostMulitplier(3);
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction leagueFaction = tempFaction;

        // add custom diplomacy
        // ********************
        
        GameWorldDiplomacy diplomacy = gw.getDiplomacy();
        DiplomacyRelation tempDiplomacyRelation;
        
        // Empire-Empire relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(empireFaction, empireFaction, gw);
        // highest = confederacy -> OK
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);
        // start = peace -> OK

        // Rebel-Rebel relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(rebelFaction, rebelFaction, gw);
        // highest = confederacy -> OK
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CONFEDERACY);

        // League-League relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(leagueFaction, leagueFaction, gw);
        // highest = confederacy -> OK
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);
        // start = peace -> OK

        // Rebel-Empire relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(rebelFaction, empireFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CEASE_FIRE);
        // lowest = Eternal War -> OK
        // start = War -> OK

        // Rebel-League relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(rebelFaction, leagueFaction, gw);
        // highest = confederacy -> OK
        // lowest = Eternal War -> OK
        // start = War -> OK

        // League-Empire relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(leagueFaction, empireFaction, gw);
        // highest = confederacy -> OK
        // lowest = Eternal War -> OK
        // start = War -> OK

		return gw;
	}

}
