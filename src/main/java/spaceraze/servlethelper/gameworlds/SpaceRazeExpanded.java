package spaceraze.servlethelper.gameworlds;

import spaceraze.servlethelper.game.AlignmentHelper;
import spaceraze.servlethelper.game.GameWorldCreator;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.world.Alignment;
import spaceraze.world.BuildingType;
import spaceraze.world.Corruption;
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
import spaceraze.world.enums.SpaceshipTargetingType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpaceRazeExpanded{
	
	public static GameWorld getGameWorld(){
		GameWorld gw = new GameWorld();
        gw.setUuid(UUID.randomUUID().toString());
//		gw.setInitMethod(GameWorld.INIT_METHOD_FIFTY_FIFTY);
//        gw.setCumulativeBombardment(false);
//		gw.setSquadronsSurviveOutsideCarriers(false);

        gw.setFileName("srexpanded");

        gw.setFullName("SpaceRaze Expanded");
        gw.setDescription("An expanded 4-faction version of the SpaceRaze Classic gameworld");

        gw.setBattleSimDefaultShips1("[2]crv;nebb;stc(s)");
        gw.setBattleSimDefaultShips2("[4]gi");

        gw.setCreatedDate("2005-09-26");
		gw.setChangedDate("2009-02-18");
		gw.setCreatedByUser("pabod");

		gw.setAdjustScreenedStatus(false);

		gw.setAlignments(AlignmentHelper.createDefaultAlignments(gw));
		Alignment neutral = AlignmentHelper.findAlignment("neutral", gw.getAlignments());
		Alignment good = AlignmentHelper.findAlignment("good", gw.getAlignments());
		Alignment evil = AlignmentHelper.findAlignment("evil", gw.getAlignments());

		Corruption tmpCorruption = new Corruption();
		tmpCorruption.addBreakpoint(50, 30);
		tmpCorruption.addBreakpoint(100, 60);
		tmpCorruption.addBreakpoint(150, 75);

		// Spaceship types
        UniqueIdCounter uniqueShipIdCounter = new UniqueIdCounter();

        // Defence platforms
        // -----------------
        // Golan I
        SpaceshipType tempsst = new SpaceshipType("Golan I","GI", SpaceShipSize.SMALL,10,50,SpaceshipRange.NONE,1,3, 10,15);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        gw.addShipType(tempsst);
        // Golan II
        tempsst = new SpaceshipType("Golan II","GII",SpaceShipSize.MEDIUM,40,90,SpaceshipRange.NONE,1,6, 10,15);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(30);
        gw.addShipType(tempsst);
        // GIIB
        tempsst = new SpaceshipType("Golan IIB","GIIB",SpaceShipSize.MEDIUM,50,120,SpaceshipRange.NONE,1,7, 10,20);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(20);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(20);
        gw.addShipType(tempsst);
        // Golan III
        tempsst = new SpaceshipType("Golan III","GIII",SpaceShipSize.LARGE,60,140,SpaceshipRange.NONE,2,9, 10,30);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(30);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(30);
        tempsst.setWeaponsStrengthHuge(10);
        tempsst.setWeaponsMaxSalvosHuge(30);
        gw.addShipType(tempsst);
        
        // Squadrons
        // ---------
        // Z-95
        tempsst = new SpaceshipType("Z-95","Z95",SpaceShipSize.SQUADRON,5,20,SpaceshipRange.NONE,1,2, 10,20);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(1);
        gw.addShipType(tempsst);
        // X-Wing
        tempsst = new SpaceshipType("X-Wing","X-W",SpaceShipSize.SQUADRON,10,20,SpaceshipRange.LONG,2,4, 10,30);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(1);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(1);
        gw.addShipType(tempsst);
        // Y-Wing
        tempsst = new SpaceshipType("Y-Wing","Y-W",SpaceShipSize.SQUADRON,5,20,SpaceshipRange.SHORT,2,3, 10,10);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(4);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(4);
        gw.addShipType(tempsst);
        // A-Wing
        tempsst = new SpaceshipType("A-Wing","A-W",SpaceShipSize.SQUADRON,10,20,SpaceshipRange.LONG,2,5, 10,40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(1);
        gw.addShipType(tempsst);
        // B-Wing
        tempsst = new SpaceshipType("B-Wing","B-W",SpaceShipSize.SQUADRON,15,25,SpaceshipRange.LONG,2,6, 15,15);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(3);
        tempsst.setWeaponsStrengthLarge(15);
        tempsst.setWeaponsMaxSalvosLarge(3);
        tempsst.setWeaponsStrengthHuge(20);
        tempsst.setWeaponsMaxSalvosHuge(2);
        gw.addShipType(tempsst);
        // TIE-fighter
        tempsst = new SpaceshipType("TIE-Fighter","T-F",SpaceShipSize.SQUADRON,0,15,SpaceshipRange.NONE,1,1, 10,25);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        // TIE-interceptor
        tempsst = new SpaceshipType("TIE-Interceptor","T-I",SpaceShipSize.SQUADRON,0,20,SpaceshipRange.NONE,1,3, 15,30);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        // TIE-bomber
        tempsst = new SpaceshipType("TIE-Bomber","T-B",SpaceShipSize.SQUADRON,0,20,SpaceshipRange.NONE,1,2, 10,10);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(2);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(2);
        gw.addShipType(tempsst);
        // TIE-advanced
        tempsst = new SpaceshipType("TIE-Advanced","T-A",SpaceShipSize.SQUADRON,10,20,SpaceshipRange.NONE,1,5, 10,30);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(1);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        // TIE-defender
        tempsst = new SpaceshipType("TIE-Defender","T-D",SpaceShipSize.SQUADRON,15,20,SpaceshipRange.LONG,2,8, 10,40);
        tempsst.setWeaponsStrengthMedium(10);
        tempsst.setWeaponsMaxSalvosMedium(2);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);

        // Capital ships
        // -------------
        // Scout
        tempsst = new SpaceshipType("Scout","Sct",SpaceShipSize.SMALL,5,10,SpaceshipRange.LONG,1,5, 5,5);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setPlanetarySurvey(true);
        gw.addShipType(tempsst);
        // Corvette
        tempsst = new SpaceshipType("Corvette","Crv",SpaceShipSize.SMALL,5,15,SpaceshipRange.LONG,1,3, 5,10);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        gw.addShipType(tempsst);
        // StC
        tempsst = new SpaceshipType("Strike Cruiser","StC",SpaceShipSize.SMALL,5,20,SpaceshipRange.LONG,2,6, 5,5);
        tempsst.setPsychWarfare(1);
        gw.addShipType(tempsst);
        // Nebulon A frigate
        tempsst = new SpaceshipType("Nebulon A Frigate","NebA",SpaceShipSize.SMALL,20,60,SpaceshipRange.LONG,3,7, 20,10);
        gw.addShipType(tempsst);
        // Lancer
        tempsst = new SpaceshipType("Lancer Frigate","Lan",SpaceShipSize.SMALL,20,50,SpaceshipRange.LONG,3,8, 10,50);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        // Long range freighter
        tempsst = new SpaceshipType("Long Range Freighter","Lrf",SpaceShipSize.MEDIUM,10,20,SpaceshipRange.LONG,2,8, 5,5);
        tempsst.setSupply(SpaceShipSize.MEDIUM);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        // Supply Freighter
        tempsst = new SpaceshipType("Supply Freighter","SF",SpaceShipSize.MEDIUM,10,20,SpaceshipRange.SHORT,2,8, 5,5);
    	tempsst.setSupply(SpaceShipSize.HUGE);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        // Liberator Cruiser
        tempsst = new SpaceshipType("Liberator Cruiser","LC",SpaceShipSize.MEDIUM,10,60,SpaceshipRange.LONG,3,10, 5,5);
        tempsst.setSquadronCapacity(4);
        gw.addShipType(tempsst);
        // Nebulon B frigate
        tempsst = new SpaceshipType("Nebulon B Frigate","NebB",SpaceShipSize.MEDIUM,40,80,SpaceshipRange.LONG,4,10, 20,20);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setBombardment(1);
        gw.addShipType(tempsst);
        // Escort Carrier
        tempsst = new SpaceshipType("Escort Carrier","EsC",SpaceShipSize.MEDIUM,20,60,SpaceshipRange.LONG,4,12, 5,10);
        tempsst.setSquadronCapacity(6);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        // Interdictor
        tempsst = new SpaceshipType("Interdictor","Int",SpaceShipSize.MEDIUM,30,70,SpaceshipRange.LONG,4,14, 10,10);
        tempsst.setNoRetreat(true);
        tempsst.setPsychWarfare(2);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
       	// Dreadnaught
       	tempsst = new SpaceshipType("Dreadnaught","Drd",SpaceShipSize.MEDIUM,50,80,SpaceshipRange.LONG,4,14, 20,10);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(4);
        gw.addShipType(tempsst);
        // Pirate Raider
        tempsst = new SpaceshipType("Pirate Raider","PR",SpaceShipSize.MEDIUM,40,80,SpaceshipRange.LONG,4,16, 20,10);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(2);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(4);
        gw.addShipType(tempsst);
        // Victory Star Destroyer
        tempsst = new SpaceshipType("Victory Star Destroyer","VSD",SpaceShipSize.LARGE,70,180,SpaceshipRange.SHORT,5,18, 20,10);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(10);
        tempsst.setWeaponsStrengthLarge(10);
        tempsst.setWeaponsMaxSalvosLarge(10);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(5);
        tempsst.setSquadronCapacity(1);
        gw.addShipType(tempsst);
        // Mon Calamari Cruiser
        tempsst = new SpaceshipType("Mon Calamari Cruiser","MCC",SpaceShipSize.LARGE,80,200,SpaceshipRange.SHORT,6,20, 20,10);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(12);
        tempsst.setWeaponsStrengthLarge(15);
        tempsst.setWeaponsMaxSalvosLarge(8);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(10);
        tempsst.setSquadronCapacity(2);
        gw.addShipType(tempsst);
        // Imperial Star Destroyer
        tempsst = new SpaceshipType("Imperial Star Destroyer","ISD",SpaceShipSize.LARGE,100,250,SpaceshipRange.SHORT,8,24, 20,10);
        tempsst.setWeaponsStrengthMedium(15);
        tempsst.setWeaponsMaxSalvosMedium(15);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(10);
        tempsst.setBombardment(2);
        tempsst.setPsychWarfare(1);
//        tempsst.setSiegeBonus(1);
        tempsst.setIncreaseInitiative(10);
        tempsst.setSquadronCapacity(3);
        gw.addShipType(tempsst);
        // Super Star Destroyer
        tempsst = new SpaceshipType("Super Star Destroyer","SSD",SpaceShipSize.HUGE,120,350,SpaceshipRange.SHORT,12,40, 25,10);
        tempsst.setWeaponsStrengthMedium(20);
        tempsst.setWeaponsMaxSalvosMedium(15);
        tempsst.setWeaponsStrengthLarge(20);
        tempsst.setWeaponsMaxSalvosLarge(15);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(2);
        tempsst.setIncreaseInitiative(15);
        tempsst.setSquadronCapacity(4);
        gw.addShipType(tempsst);
        // Death Star
        tempsst = new SpaceshipType("Death Star","DS",SpaceShipSize.HUGE,200,1000,SpaceshipRange.SHORT,60,400, 20,10);
        tempsst.setWeaponsStrengthMedium(10000);
        tempsst.setBombardment(1000);
        tempsst.setPsychWarfare(3);
//        tempsst.setIncreaseInitiative(20);
        tempsst.setCanAppearOnBlackMarket(false);
        tempsst.setSquadronCapacity(6);
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
        tmpVipType.setInitFighterSquadronBonus(10);
//        tmpVipType.setAlignment(Alignment.EVIL);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setAssassination(50);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Light Jedi","LJ",good);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitFighterSquadronBonus(10);
//        tmpVipType.setAlignment(Alignment.GOOD);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(50);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
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

       	tmpVipType = new VIPType("Smuggler","Smu",neutral);
        tmpVipType.setClosedIncBonus(3);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("General","Gen",neutral);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setResistanceBonus(3);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Sky Marshal","Sky",neutral);
        tmpVipType.setInitBonus(20);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Admiral","Adm",neutral);
        tmpVipType.setInitBonus(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Top Ace","Ace",neutral);
        tmpVipType.setInitFighterSquadronBonus(10);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Assassin","Ass",neutral);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(50);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Expert Engineer","Eng",neutral);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(20);
        tmpVipType.setShipBuildBonus(20);
        tmpVipType.setBuildingBuildBonus(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Master Astrogator","Ast",neutral);
        tmpVipType.setFTLbonus(true);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Diplomat","Dip",neutral);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        gw.addVipType(tmpVipType);
        VIPType dip = tmpVipType;
        
        tmpVipType = new VIPType("Field Marshal","Fie",neutral);
        tmpVipType.setPsychWarfareBonus(2);
        tmpVipType.setResistanceBonus(5);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Master Assassin","MA",neutral);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(80);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Gray Jedi","GJ",neutral);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setInitBonus(10);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setCounterEspionage(70);
        tmpVipType.setFTLbonus(true);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        tmpVipType.setWellGuarded(true);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Padawan","Pad",good);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_APPRENTICE);
        tmpVipType.setHardToKill(true);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(30);
        tmpVipType.setInitFighterSquadronBonus(5);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        tmpVipType.setWellGuarded(true);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Bounty Hunter","BH",evil);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(80);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_APPRENTICE);
        tmpVipType.setHardToKill(true);
        tmpVipType.setSpying(true);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        gw.addVipType(tmpVipType);

        VIPType bhType = tmpVipType;

        tmpVipType = new VIPType("Dark Jedi Master","DJM",evil);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_MASTER);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitFighterSquadronBonus(10);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setAssassination(80);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        tmpVipType.setWellGuarded(true);
        gw.addVipType(tmpVipType);
        
        tmpVipType = new VIPType("Light Jedi Master","LJM",good);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_MASTER);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitFighterSquadronBonus(10);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(80);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setFrequency(BlackMarketFrequency.RARE);
        tmpVipType.setWellGuarded(true);
        gw.addVipType(tmpVipType);
        
        // Buildings
        // *********        
		UniqueIdCounter uBIC = new UniqueIdCounter();
      
        List<BuildingType> tempBuildingsLeague = new ArrayList<>();
        List<BuildingType> tempBuildingsEmpire = new ArrayList<>();
        List<BuildingType> tempBuildingsRebel = new ArrayList<>();
        List<BuildingType> tempBuildingsPirate = new ArrayList<>();
		BuildingType tmpBuildingType = null;
        
		// orbital wharfs
        tmpBuildingType = new BuildingType("Small Orbital Wharf", "W1", 5);
        tmpBuildingType.setWharfSize(1);
        tmpBuildingType.setInOrbit(true);
        BuildingType parent = tmpBuildingType;
        tempBuildingsLeague.add(tmpBuildingType);
        tempBuildingsEmpire.add(tmpBuildingType);
        tempBuildingsPirate.add(tmpBuildingType);
        tempBuildingsRebel.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Medium Orbital Wharf", "W2", 10);
        tmpBuildingType.setWharfSize(2);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName(parent.getName());
        parent = tmpBuildingType;
        tempBuildingsLeague.add(tmpBuildingType);
        tempBuildingsEmpire.add(tmpBuildingType);
        tempBuildingsPirate.add(tmpBuildingType);
        tempBuildingsRebel.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Large Orbital Wharf", "W3", 10);
        tmpBuildingType.setWharfSize(3);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName(parent.getName());
        parent = tmpBuildingType;
        tempBuildingsLeague.add(tmpBuildingType);
        tempBuildingsEmpire.add(tmpBuildingType);
        tempBuildingsPirate.add(tmpBuildingType);
        tempBuildingsRebel.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("Huge Orbital Wharf", "W5", 10);
        tmpBuildingType.setWharfSize(5);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName(parent.getName());
        tempBuildingsLeague.add(tmpBuildingType);
        tempBuildingsEmpire.add(tmpBuildingType);
        tempBuildingsPirate.add(tmpBuildingType);
        tempBuildingsRebel.add(tmpBuildingType);
        
        tmpBuildingType = new BuildingType("League Space Station", "LSS", 15);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setOpenPlanetBonus(2);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingsLeague.add(tmpBuildingType);        

        tmpBuildingType = new BuildingType("Empire Space Station", "ESS", 15);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setOpenPlanetBonus(2);
        tmpBuildingType.setClosedPlanetBonus(1);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingsEmpire.add(tmpBuildingType);        

        tmpBuildingType = new BuildingType("Rebel Space Station", "RSS", 12);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setClosedPlanetBonus(1);
        tmpBuildingType.setTechBonus(10);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingsRebel.add(tmpBuildingType);        

        tmpBuildingType = new BuildingType("Pirate Space Station", "PSS", 15);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setClosedPlanetBonus(2);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tempBuildingsPirate.add(tmpBuildingType);        

        // Factions
        // empire
        Faction tempFaction = new Faction("Empire",Faction.getColorHexString(240,35,45),evil);
        tempFaction.setDescription("The Empire have their gratest strength in their heavy short-range ships, and these ships also have powerful bombardment capacity with which the Empire can crush opposition. Their long range capacity is limited. Their elite troops gives a reisitance bonus on all their planets and makes it difficult for enemies to conquer them.");
        tempFaction.setShortDescription("Largest short range ships with and powerful bombardment capacity.");
        tempFaction.setAdvantages("Powerful short range ships, resistance bonus, powerful bombardment, start with medium wharf");
        tempFaction.setDisadvantages("Long range capacity");
        tempFaction.setHistory("As the empire have grown larger and richer, it have slowly become mote corrupt and powerhungry. It have allocated more and more recources to expand its army and fleet. The oppression of it's own peripheral planets ultimately led to the rebellion and conflict with the other two factions.");
        tempFaction.setHowToPlay("Try to build ISD and use them to quickly expand. Use bombardment to quickly conquer planets, but be careful not to raze them (unless you want to). When building larger fleets, have at least one SSD to gain the high initiative bonus, and remember that a screened Interdictor in a fleet stops enemy ships from retreating and also increase siege capacity. Preferably open your planets and build spacestations to gain the full open planet bonus income.");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan III"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("TIE-Fighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("TIE-Interceptor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("TIE-Bomber"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("TIE-Advanced"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("TIE-Defender"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Lancer frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Escort Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Interdictor"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Imperial Star Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Super Star Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Death Star"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Strike Cruiser", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Nebulon B frigate", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Golan II", gw));
        tempFaction.setResistanceBonus(2);
        tempFaction.setBuildings(tempBuildingsEmpire);
        tempFaction.setGovernorVIPType(govType);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Medium Orbital Wharf"));
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Empire Space Station"));
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction empireFaction = tempFaction;

        // rebels
        tempFaction = new Faction("Rebel",Faction.getColorHexString(0,255,0),good);
        tempFaction.setDescription("The rebels have have a balanced mix between long and short range capacity, and have the most powerful starfighters. They have an income bonus for closed planets and with a space stations closed income bonus they have the same income as an open planet.");
        tempFaction.setShortDescription("Balanced ships with best starfighters.");
        tempFaction.setAdvantages("Powerful starfighters, closed planet bonus, start with diplomat");
        tempFaction.setDisadvantages("No huge ships");
        tempFaction.setHistory("The rebellion against the galactic empire started as an attempt for a number of oppressed peripheral world to break free of the bonds of the Galactic Empire, but has grown to a conflict for life and death between the rebels and the Empire.");
        tempFaction.setHowToPlay("Unless you quickly need income have your planets closed and build spacestations instead. Outfit your squadron-carrying ships with starfighters to boost their power. If you can afford it, use B-wing (against enemy capital ships) and A-wings (if the enemy have starfighters).");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan III"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("X-Wing"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Y-Wing"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("A-Wing"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("B-Wing"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Liberator Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Dreadnaught"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Mon Calamari Cruiser"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Dreadnaught", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Golan II", gw));
        tempFaction.setClosedPlanetBonus(1);
        tempFaction.setBuildings(tempBuildingsRebel);
        tempFaction.setGovernorVIPType(govType);
        tempFaction.addStartingVIPType(dip);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Orbital Wharf"));
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Rebel Space Station"));
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction rebelFaction = tempFaction;
        
        // league
        tempFaction = new Faction("League",Faction.getColorHexString(24,66,255),neutral);
        tempFaction.setDescription("League is somewhat weak in both long and short range capacity, and have weak starfighters. They have an big income bonus for open planets (+2) and with their space stations open income bonus they can have a larger income than any other faction. League is not involved in the old conflict between the Empire and the Rebels, so it is free to ally itself with any of them, and also use both light and dark jedis.");
        tempFaction.setShortDescription("Somewhat weak ships and big open planet income bonuses.");
        tempFaction.setAdvantages("Open planet bonus, diplomatic options");
        tempFaction.setDisadvantages("No huge ships, weak ships and starfighters");
        tempFaction.setHistory("The League have always been a small faction compared to the dominant Empire, but since the rebellion started it have been able to grow more powerful by playing the other factions against each other.");
        tempFaction.setHowToPlay("Unless you need to stay hidden, open planets to gain the big open planet income bunus. If you can afford it, build space stations to increase your open planet income. For long range combat use Nebulon A Frigates mixed with the other shiptypes. Try to make up for your weaker ships by having more of them.");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan I"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan II"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan IIB"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Golan III"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Z-95"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Y-Wing"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Scout"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon A frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Supply Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Escort Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Victory Star Destroyer"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Strike Cruiser", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Nebulon B frigate", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Golan II", gw));
        tempFaction.setOpenPlanetBonus(2);
        tempFaction.setBuildings(tempBuildingsLeague);
        tempFaction.setGovernorVIPType(govType);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Orbital Wharf"));
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("League Space Station"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction leagueFaction = tempFaction;
        
        // pirate faction
        tempFaction = new Faction("Pirate",Faction.getColorHexString(240,231,16),evil);
        tempFaction.setDescription("The pirates do not build large or huge ships, they exclusively use raider-type ships and prefer to use the Pirate raider medium shiptype. If forced to big battles they are at a big disadvantage compared to all other factions, with no big ships and weak starfighters. Also the Pirates lack any defensive ships, which makes it harder to fight battles defensively. The pirates work best in secret and have a big closed planet bonus (+2) which means that they gain no extra income by opening their planets.");
        tempFaction.setShortDescription("No big powerful ships, powerful raider ships, big closed planet income bonuses.");
        tempFaction.setAdvantages("Closed planet bonus, best raider ships");
        tempFaction.setDisadvantages("No huge or large ships, weak starfighters, no defence ships");
        tempFaction.setHistory("The pirates have grown out of chaos and corruption that have been ever increasing since the empire and its neighbours started to fight for dominion. The Pirates grew bolder and bolder until they started to capture whole planets for themselves and became a power to be reckoned with.");
        tempFaction.setHowToPlay("Always have all your planets closed, you gain no extra income by opening them. Use \"Raider\" ships to raid enemy players behind their lines. If defending your own planets you can use Y-wings (and Z-95:s if your enemy uses starfighters on his own) to boost your defence.");
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Z-95"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Y-Wing"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Corvette"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Strike Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Long Range Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Nebulon B frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Escort Carrier"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Pirate Raider"));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Corvette", gw));
        tempFaction.addStartingShipType(SpaceshipPureFunctions.getSpaceshipTypeByName("Pirate Raider", gw));
        tempFaction.setClosedPlanetBonus(2);
            tempFaction.setBuildings(tempBuildingsPirate);
        tempFaction.setGovernorVIPType(govType);
        tempFaction.setNrStartingRandomVIPs(1);
//        tempFaction.addStartingVIPType(assType);
        tempFaction.addStartingVIPType(bhType);
        tempFaction.addStartingBuildings(tempFaction.getBuildingTypeByName("Small Orbital Wharf"));

        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        GameWorldCreator.addFaction(tempFaction, gw);
        Faction pirateFaction = tempFaction;
        
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
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.ALLIANCE);

        // Pirate-Pirate relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(pirateFaction, pirateFaction, gw);
        // highest = confederacy -> OK
        // lowest = Eternal War -> OK
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);

        // Rebel-Empire relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(rebelFaction, empireFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CEASE_FIRE);
        // lowest = Eternal War -> OK
        // start = War -> OK

        // Rebel-League relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(rebelFaction, leagueFaction, gw);
        // highest = confederacy -> OK
        // lowest = Eternal War -> OK
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);

        // Rebel-Pirate relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(rebelFaction, pirateFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CEASE_FIRE);
        // lowest = Eternal War -> OK
        // start = War -> OK

        // League-Empire relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(leagueFaction, empireFaction, gw);
        // highest = confederacy -> OK
        // lowest = Eternal War -> OK
        // start = War -> OK

        // League-Pirate relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(leagueFaction, pirateFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.PEACE);
        // lowest = Eternal War -> OK
        // start = War -> OK

        // Empire-Pirate relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(empireFaction, pirateFaction, gw);
        // highest = confederacy -> OK
        // lowest = Eternal War -> OK
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);

		return gw;
	}

}
