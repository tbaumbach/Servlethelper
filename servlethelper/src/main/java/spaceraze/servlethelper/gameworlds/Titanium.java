package spaceraze.servlethelper.gameworlds;

import java.util.ArrayList;
import java.util.List;

import spaceraze.servlethelper.game.AlignmentHelper;
import spaceraze.servlethelper.game.GameWorldCreator;
import spaceraze.util.general.Functions;
import spaceraze.util.general.Logger;
import spaceraze.world.Alignment;
import spaceraze.world.BuildingType;
import spaceraze.world.Corruption;
import spaceraze.world.Faction;
import spaceraze.world.GameWorld;
import spaceraze.world.ResearchAdvantage;
import spaceraze.world.SpaceshipType;
import spaceraze.world.TroopType;
import spaceraze.world.UniqueIdCounter;
import spaceraze.world.VIPType;
import spaceraze.world.diplomacy.DiplomacyLevel;
import spaceraze.world.diplomacy.DiplomacyRelation;
import spaceraze.world.diplomacy.GameWorldDiplomacy;
import spaceraze.world.enums.*;

public class Titanium {

    public static GameWorld getGameWorld() {
        GameWorld gw = new GameWorld();

        gw.setFileName("titanium");

        gw.setFullName("Titanium");
        gw.setDescription("An advanced gameworld containing five diverse factions. \n" +
                "Titanium features many types of research, troop units and planetary buildings. \n" +
                "The five factions are: \n" +
                "Orb - dynamic and flexible, relying heavily on squadrons and using different types of ground units\n" +
                "Lancer - traders, mixing civilian ships and the largest battleships, but weak in ground combat\n" +
                "Cyber - cybernetically enhanced, using powerful mechs to crush enemy ground forces\n" +
                "Ghost - secretive and defensive, using stealth and guerilla tactics\n" +
                "Templar - pround and haughty alien race, using brainwashing of subjugate populations and high tech");

        gw.setBattleSimDefaultShips1("");
        gw.setBattleSimDefaultShips2("[4]pdf");

        gw.setCreatedDate("2006-11-27");
        gw.setChangedDate("2006-11-27");
        gw.setCreatedByUser("pabod");

        gw.setAdjustScreenedStatus(false);

        String oStr = "Orb";
        String lStr = "Lancer";
        String cStr = "Cyber";
        String gStr = "Ghost";
        String tStr = "Templar";
        String mStr = "Mercenary";
        gw.getAlignments().add(new Alignment(oStr));
        gw.getAlignments().add(new Alignment(lStr));
        gw.getAlignments().add(new Alignment(cStr));
        gw.getAlignments().add(new Alignment(gStr));
        gw.getAlignments().add(new Alignment(tStr));
        gw.getAlignments().add(new Alignment(mStr));
        Alignment orb = AlignmentHelper.findAlignment(oStr, gw.getAlignments());
        Alignment lancer = AlignmentHelper.findAlignment(lStr, gw.getAlignments());
        Alignment cyber = AlignmentHelper.findAlignment(cStr, gw.getAlignments());
        Alignment ghost = AlignmentHelper.findAlignment(gStr, gw.getAlignments());
        Alignment templar = AlignmentHelper.findAlignment(tStr, gw.getAlignments());
        Alignment mercenary = AlignmentHelper.findAlignment(mStr, gw.getAlignments());
        
        orb.setDescription("Diversity and flexibility");
        lancer.setDescription("Profit and diplomacy");
        cyber.setDescription("Machine and man in symbiosis");
        ghost.setDescription("In shadows lie safety");
        templar.setDescription("No mercy, the stronger deserve to rule");
        mercenary.setDescription("Work for the one who pays best");
        
        orb.addCanHaveVip(mercenary);
        lancer.addCanHaveVip(mercenary);
        cyber.addCanHaveVip(mercenary);
        ghost.addCanHaveVip(mercenary);
        templar.addCanHaveVip(mercenary);

        Corruption tmpCorruption = createCorruption(7);

        // Bombardment have 50% chance to do 1000 damage (destroying the troop)
        //gw.setBaseBombardmentDamage(60);

        // *********
        // VIP types
        // *********
        // XXX VIP Types

        // General VIP types
        // *****************

        UniqueIdCounter uniqueVIPIdCounter = new UniqueIdCounter();

        VIPType tmpVipType = null;

        tmpVipType = new VIPType("Spy", "Spy", mercenary);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setSpying(true);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Security Chief", "Sec", mercenary);
        tmpVipType.setCounterEspionage(60);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setExterminator(60);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Economic Master", "Eco", mercenary);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setOpenIncBonus(3);
        tmpVipType.setClosedIncBonus(1);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Smuggler", "Smu", mercenary);
        tmpVipType.setClosedIncBonus(3);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Commander", "Com", mercenary);
        tmpVipType.setLandBattleGroupAttackBonus(30);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("General", "Gen", mercenary);
        tmpVipType.setLandBattleGroupAttackBonus(60);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Admiral", "Adm", mercenary);
        tmpVipType.setInitBonus(10);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Ace", "Ace", mercenary);
        tmpVipType.setInitFighterSquadronBonus(10);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Assassin", "Ass", mercenary);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(50);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Engineer", "Eng", mercenary);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setShipBuildBonus(20);
        tmpVipType.setBuildingBuildBonus(20);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Scientist", "Sci", mercenary);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(10);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Diplomat", "Dip", mercenary);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setBuildCost(20);
        gw.addVipType(tmpVipType);

        List<VIPType> mercenaryVIPs = Functions.deepClone(gw.getVipTypes());

        int buildCost1 = 20;
        int buildCost2 = 30;
        int buildCost3 = 50;

        // Orb VIPs
        // ********

        tmpVipType = new VIPType("Field Marshal", "FM", mercenary);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setLandBattleGroupAttackBonus(30);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);
        VIPType orbStart1 = tmpVipType;

        tmpVipType = new VIPType("Orb Admiral", "OA", orb);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setInitBonus(10);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);
        VIPType orbStart2 = tmpVipType;

        tmpVipType = new VIPType("Orb Scientist", "OS", orb);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(10);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Orb Expert Scientist", "OES", orb);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(20);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Orb Hero", "OHer", orb);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setHardToKill(true);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setResistanceBonus(2);
        tmpVipType.setCounterEspionage(50);
        tmpVipType.setExterminator(50);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitBonus(10);
        tmpVipType.setBuildCost(buildCost3);
        gw.addVipType(tmpVipType);

        // Lancer VIPs
        // ***********

        tmpVipType = new VIPType("Lancer Economic Master", "LEM", lancer);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setOpenIncBonus(4);
        tmpVipType.setClosedIncBonus(2);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);
        VIPType lancerStart1 = tmpVipType;

        tmpVipType = new VIPType("Lancer Economic Genious", "LEG", lancer);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setOpenIncBonus(5);
        tmpVipType.setClosedIncBonus(3);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Lancer Engineer", "LE", lancer);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setShipBuildBonus(20);
        tmpVipType.setBuildingBuildBonus(20);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);
        VIPType lancerStart2 = tmpVipType;

        tmpVipType = new VIPType("Lancer Master Engineer", "LME", lancer);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setShowOnOpenPlanet(true);
        tmpVipType.setTechBonus(10);
        tmpVipType.setShipBuildBonus(40);
        tmpVipType.setBuildingBuildBonus(40);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Lancer Champion", "LCha", lancer);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setInitFighterSquadronBonus(10);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setSpying(true);
        tmpVipType.setCounterEspionage(70);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setExterminator(80);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setFrequency(BlackMarketFrequency.UNCOMMON);
        tmpVipType.setBuildCost(buildCost3);
        gw.addVipType(tmpVipType);

        // Cyber VIPs
        // **********

        tmpVipType = new VIPType("Cyber Elite Commander", "CG", cyber);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setLandBattleGroupAttackBonus(30);
        tmpVipType.setResistanceBonus(2);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);
        VIPType cyberStart1 = tmpVipType;

        tmpVipType = new VIPType("Cyber Field Marchal", "CFM", cyber);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setLandBattleGroupAttackBonus(60);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Cyber Ace", "CA", cyber);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setInitFighterSquadronBonus(15);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);
        VIPType cyberStart2 = tmpVipType;

        tmpVipType = new VIPType("Cyber Top Ace", "CTA", cyber);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setInitFighterSquadronBonus(20);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Cybernetic Warrior", "CWar", cyber);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_MASTER);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setInitFighterSquadronBonus(10);
        tmpVipType.setHardToKill(true);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setAssassination(70);
        tmpVipType.setExterminator(40);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setBuildCost(buildCost3);
        gw.addVipType(tmpVipType);

        // Ghost VIPs
        // **********

        tmpVipType = new VIPType("Ghost Assassin", "GA", ghost);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(60);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Ghost Spy", "GS", ghost);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setSpying(true);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);
        VIPType ghostStart1 = tmpVipType;

        tmpVipType = new VIPType("Ghost Security Chief", "GSE", ghost);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCounterEspionage(70);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setClosedIncBonus(1);
        tmpVipType.setExterminator(70);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Ghost Smuggler", "GSM", ghost);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setClosedIncBonus(3);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);
        VIPType ghostStart2 = tmpVipType;

        tmpVipType = new VIPType("Ghost Fighter", "GFig", ghost);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setHardToKill(true);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_VETERAN);
        tmpVipType.setResistanceBonus(4);
        tmpVipType.setCounterEspionage(60);
        tmpVipType.setExterminator(60);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setSpying(true);
        tmpVipType.setBuildCost(buildCost3);
        gw.addVipType(tmpVipType);

        // Templar VIPs
        // ************        

        tmpVipType = new VIPType("Templar Assassin", "TA", templar);
        tmpVipType.setDescription("The Templar assassins use a form of psionic soul-walking to leave their own bodies and invade the mind of a victim in their sleep and kill him without a trace.");
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setAssassination(70);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);
        VIPType templarStart1 = tmpVipType;

        tmpVipType = new VIPType("Templar Star Navigator", "TSN", templar);
        tmpVipType.setDescription("The Templar Star Navigators use a more powerful version of psionic soul-walking than their assassin colleagues to enable a starship to jump further inte space tahn they normally can.");
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setFTLbonus(true);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Templar Counter-Spy", "TCS", templar);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCounterEspionage(70);
        tmpVipType.setResistanceBonus(2);
        tmpVipType.setBuildCost(buildCost1);
        gw.addVipType(tmpVipType);
        VIPType templarStart2 = tmpVipType;

        tmpVipType = new VIPType("Templar Infestator", "TI", templar);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setInfestate(true);
        tmpVipType.setSpying(true);
        tmpVipType.setBuildCost(buildCost2);
        gw.addVipType(tmpVipType);

        tmpVipType = new VIPType("Templar Knight", "TKni", templar);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitEnemyPlanets(true);
        tmpVipType.setHardToKill(true);
        tmpVipType.setImmuneToCounterEspionage(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setDuellistSkill(VIPType.DUELLIST_MASTER);
        tmpVipType.setResistanceBonus(2);
        tmpVipType.setPsychWarfareBonus(1);
        tmpVipType.setAssassination(70);
        tmpVipType.setInfestate(true);
        tmpVipType.setFTLbonus(true);
        tmpVipType.setBuildCost(buildCost3);
        gw.addVipType(tmpVipType);

        // ******
        // Troops
        // ******
        // XXX TroopTypes

        // Basic troop units avaliable to Orb, Lancer & Ghost (and nuetral planets)
        // ***********************************************************************

        String typeName = null;

        UniqueIdCounter uTIC = new UniqueIdCounter();

        TroopType tt = new TroopType("Militia", "Mil", 100, 2, 2, 15, 10);
        tt.setDescription("Cheap defensive infantry unit. Cannot travel in spaceships.");
        tt.setShortDescription("Cheap defensive infantry");
        tt.setAdvantages("Cheap");
        tt.setDisadvantages("Cannot travel in spaceships");
        tt.setSpaceshipTravel(false);
        gw.addTroopType(tt);

        gw.setNeutralTroopType(tt);

        tt = new TroopType("Militia Artillery", "MilA", 50, 2, 3, 5, 5);
        tt.setDescription("Light cheap artillery unit.");
        tt.setShortDescription("Light cheap artillery");
        tt.setAdvantages("Artillery attack");
        tt.setDisadvantages("None");
        tt.setTypeOfTroop(TypeOfTroop.SUPPORT);
        tt.setAttackArtillery(25);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDefaultPosition(BattleGroupPosition.SUPPORT);
        tt.setSpaceshipTravel(false);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        tt = new TroopType("Infantry", "Inf", 120, 2, 4, 20, 10);
        tt.setDescription("Basic infantry unit.");
        tt.setShortDescription("Basic infantry");
        tt.setAdvantages("None");
        tt.setDisadvantages("None");
        gw.addTroopType(tt);

        typeName = "Scout Infantry";
        tt = new TroopType(typeName, "SInf", 100, 2, 5, 25, 10);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Fast moving infantry flanking unit.");
        tt.setShortDescription("Flanking infantry");
        tt.setAdvantages("Can flank");
        tt.setDisadvantages("Weaker than normal infantry");
        //      tt.setAttackScreened(true);
        tt.setDefaultPosition(BattleGroupPosition.FLANKER);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        tt = new TroopType("Light Artillery", "LArt", 60, 2, 4, 5, 15);
        tt.setDescription("Light artillery unit. Have a decent attack against armored opponents.");
        tt.setShortDescription("Light artillery");
        tt.setAdvantages("Can attack armored units.");
        tt.setDisadvantages("None");
        tt.setTypeOfTroop(TypeOfTroop.SUPPORT);
        tt.setAttackArtillery(30);
        tt.setTargetingType(TroopTargetingType.ANTITANK);
        tt.setDefaultPosition(BattleGroupPosition.SUPPORT);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        typeName = "Light Tanks";
        tt = new TroopType(typeName, "LT", 120, 2, 6, 30, 20);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ANTIINFANTRY);
        tt.setDescription("Light Tank unit.");
        tt.setShortDescription("Light Tanks");
        tt.setAdvantages("Good versus infantry units");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        // Orb troop units
        // ***************
 /* TODO       
        tt = new TroopType("Orb Anti-Air Light Armor","OAA",100,2,6,uTIC,30,10);
        tt.setDescription("Heavy artillery unit.");
        tt.setShortDescription("Anti-Air armor");
        tt.setAdvantages("Heavy AA attack and good damage against infantry.");
        tt.setDisadvantages("Weak attack against armor.");
        tt.setAttackAntiAir(30);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ANTIINFANTRY);
        tt.setDefaultPosition(BattleGroupPosition.SUPPORT);
        tt.setCanBuild(false);
        gw.addTroopType(tt);
*/
        tt = new TroopType("Orb Heavy Artillery", "OHA", 40, 2, 8, 5, 5);
        tt.setDescription("Heavy artillery unit.");
        tt.setShortDescription("Heavy artillery");
        tt.setAdvantages("Heavy artillery damage.");
        tt.setDisadvantages("Useless in close combat");
        tt.setTypeOfTroop(TypeOfTroop.SUPPORT);
        tt.setAttackArtillery(50);
        tt.setDefaultPosition(BattleGroupPosition.SUPPORT);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        typeName = "Orb Heavy Tanks";
        tt = new TroopType(typeName, "OHT", 200, 2, 10, 30, 40);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ANTITANK);
        tt.setDescription("Heavy Tank unit.");
        tt.setShortDescription("Heavy Tanks");
        tt.setAdvantages("Good vs infantry, very good vs Tanks");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        // Cyber troop units
        // *****************

        typeName = "Cyber Mechs";
        tt = new TroopType(typeName, "CM", 200, 2, 6, 30, 30);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Cyber mech unit, consisting of 30' tall mechs piloted by cybernetically integrated pilots.");
        tt.setShortDescription("Mech Lance");
        tt.setAdvantages("Good allround mech unit");
        tt.setDisadvantages("None");
        gw.addTroopType(tt);

        typeName = "Cyber Scout Mechs";
        tt = new TroopType(typeName, "CSM", 200, 2, 8, 25, 25);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Fast-moving Cyber mech unit, consisting of 25' tall mechs piloted by cybernetically integrated pilots.");
        tt.setShortDescription("Scout Mech Lance");
        tt.setAdvantages("Fast allround mech unit");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        //      tt.setAttackScreened(true);
        tt.setDefaultPosition(BattleGroupPosition.FLANKER);
        gw.addTroopType(tt);

        typeName = "Cyber Antitank Mechs";
        tt = new TroopType(typeName, "CAtM", 250, 2, 8, 20, 50);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ANTITANK);
        tt.setDescription("Cyber mech unit, consisting of 30' tall mechs piloted by cybernetically integrated pilots. Specialized in heavy antitank weapons.");
        tt.setShortDescription("Antitank mech Lance");
        tt.setAdvantages("Good antitank weapons");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        gw.addTroopType(tt);
/*
        typeName = "Cyber Anti-air Mechs";
        tt = new TroopType(typeName,"CAaM",200,2,8,uTIC,50,15);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ANTIINFANTRY);
        tt.setDescription("Cyber mech unit, consisting of 30' tall mechs piloted by cybernetically integrated pilots. Carries heavy anti-air antitank weapons, which is also very effective against infantry.");
        tt.setShortDescription("Anti-air mech lance");
        tt.setAdvantages("Good vs air and infantry");
        tt.setDisadvantages("None");
        tt.setAttackAntiAir(40);
        tt.setCanBuild(false);
        tt.setDefaultPosition(BattleGroupPosition.SUPPORT);
        gw.addTroopType(tt);
*/
        typeName = "Cyber Artillery Mechs";
        tt = new TroopType(typeName, "CAM", 200, 2, 8, 10, 10);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Cyber mech artillery unit, consisting of 30' tall mechs piloted by cybernetically integrated pilots.");
        tt.setShortDescription("Artillery mech lance");
        tt.setAdvantages("Powerful artillery attack");
        tt.setDisadvantages("Weak close combat attacks");
        tt.setAttackArtillery(50);
        tt.setCanBuild(false);
        tt.setDefaultPosition(BattleGroupPosition.SUPPORT);
        gw.addTroopType(tt);

        typeName = "Cyber Heavy Mechs";
        tt = new TroopType(typeName, "CHM", 300, 2, 10, 40, 40);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Cyber heavy mech unit, consisting of 30' tall mechs piloted by cybernetically integrated pilots.");
        tt.setShortDescription("Heavy Mech Lance");
        tt.setAdvantages("Heavy allround mech unit");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        typeName = "Cyber Advanced Mechs";
        tt = new TroopType(typeName, "CAdM", 250, 2, 8, 30, 30);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Cyber mech unit, consisting of 30' tall mechs piloted by cybernetically integrated pilots.");
        tt.setShortDescription("Mech Lance");
        tt.setAdvantages("Good allround mech unit");
        tt.setDisadvantages("None");
        //      tt.setAttackScreened(true);

        //TODO Paul jag tog bort denna d� vi nu k�r med att alla har 3 turns
        //tt.setNrAttacks(4);
        //tt.setDropPenalty(0);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        // Ghost troop units
        // *****************

        tt = new TroopType("Ghost Guerilla Infantry", "GGI", 180, 1, 6, 30, 15);
        tt.setDescription("Ghost guerilla infantry unit, specialized in defensive combat.");
        tt.setShortDescription("Guerilla infantry");
        tt.setAdvantages("Defensive fighting");
        tt.setDisadvantages("Can't move from the planet they are build on");
        // TODO Paul jag tog bort dessa ur spellogiken. Plussade �ven p� DamageCapacity med 40. De blir v�l typ samma sak men l�ttar att f�rst�.
        //tt.setDefensiveBonus(40);
        //tt.setDefenceArtillery(20);
        tt.setSpaceshipTravel(false);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        typeName = "Ghost Mobile Infantry";
        tt = new TroopType(typeName, "GMI", 160, 2, 8, 40, 20);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Fast moving elite infantry flanking unit, where all soldiers have a powered armored suit, which allows them to carry a wide assortment of weaponry.");
        tt.setShortDescription("Elite infantry");
        tt.setAdvantages("Can flank");
        tt.setDisadvantages("None");
        //tt.setDropPenalty(0);
        //     tt.setAttackScreened(true);
        tt.setDefaultPosition(BattleGroupPosition.FLANKER);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        // Templar troop units
        // *******************

        typeName = "Templar Drone Infantry";
        tt = new TroopType(typeName, "TDI", 80, 1, 1, 15, 5);
        tt.setTargetingType(TroopTargetingType.ANTIINFANTRY);
        tt.setDescription("Templar drone (brainwashed slaves) infantry unit.");
        tt.setShortDescription("Cheap drone infantry");
        tt.setAdvantages("None");
        tt.setDisadvantages("Weak vs armored units");
        gw.addTroopType(tt);

        typeName = "Templar Drone Rocket Infantry";
        tt = new TroopType(typeName, "TDRI", 80, 1, 2, 5, 15);
        tt.setTargetingType(TroopTargetingType.ANTITANK);
        tt.setDescription("Templar drone (brainwashed slaves) anti-tank infantry unit.");
        tt.setShortDescription("Cheap drone anti-tank infantry");
        tt.setAdvantages("Good attack vs armored units");
        tt.setDisadvantages("Weak vs infantry units");
        tt.setCanBuild(false);
        gw.addTroopType(tt);
/* TODO
        typeName = "Templar Drone Missile Infantry";
        tt = new TroopType(typeName,"TDMI",80,1,2,uTIC,10,5);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Templar drone (brainwashed slaves) anti-air infantry unit.");
        tt.setShortDescription("Cheap drone anti-air infantry");
        tt.setAdvantages("Good anti-air attack");
        tt.setDisadvantages("Weak in close combat");
        tt.setAttackAntiAir(15);
        tt.setCanBuild(false);
        gw.addTroopType(tt);
*/
        typeName = "Templar Drone Heavy Infantry";
        tt = new TroopType(typeName, "TDHI", 120, 2, 5, 25, 15);
        tt.setTargetingType(TroopTargetingType.ANTITANK);
        tt.setDescription("Templar drone (brainwashed slaves) heavy infantry unit, in which the soldier use an powered exoskeleton allowing better protection and to carry haeavier weapons.");
        tt.setShortDescription("Heavy drone infantry");
        tt.setAdvantages("Good allround infantry");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        typeName = "Templar Light Armor";
        tt = new TroopType(typeName, "TLA", 150, 2, 10, 30, 30);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Templar elite light tank unit, manned by Templars.");
        tt.setShortDescription("Elite light tanks");
        tt.setAdvantages("Good allround arnament");
        tt.setDisadvantages("None");
        //    tt.setAttackScreened(true);
        tt.setDefaultPosition(BattleGroupPosition.FLANKER);
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        typeName = "Templar Heavy Armor";
        tt = new TroopType(typeName, "THA", 400, 3, 20, 50, 50);
        tt.setTypeOfTroop(TypeOfTroop.ARMORED);
        tt.setTargetingType(TroopTargetingType.ALLROUND);
        tt.setDescription("Super-heavy nuclear-driven armoured dreadnaughts, manned by Templars.");
        tt.setShortDescription("Super-heavy tanks");
        tt.setAdvantages("Powerful allround weaponry");
        tt.setDisadvantages("None");
        tt.setCanBuild(false);
        gw.addTroopType(tt);

        // *******************
        // * Spaceship Types *
        // *******************
        // XXX Spaceship Types

        // Basic Spaceship types (available from start)
        // ************************************************
        UniqueIdCounter uSIC = new UniqueIdCounter();

        SpaceshipType tempsst = null;
        int sqdBaseSh = 10;
        int sqdBaseDC = 20;
//		int sqdBugBaseDC = 15;
        int sqdBaseSmAtt = 10;
        int sqdBaseFightSqdAtt = 20;
        int sqdBaseBombSqdAtt = 10;

        // Civian ships
        // ************

        // Small Merchant Freighter
        typeName = "Small Merchant Freighter";
        tempsst = new SpaceshipType(typeName, "SMF", SpaceShipSize.SMALL, SpaceshipRange.LONG, 0, 4);
        tempsst.setDescription("Small-sized long range civilian merchant ship. Give income bonuses on frendly and neutral open planets.");
        tempsst.setCivilian(true);
        tempsst.setIncOwnOpenBonus(3);
        tempsst.setIncFriendlyOpenBonus(3);
        tempsst.setIncNeutralOpenBonus(4);
        gw.addShipType(tempsst);

        // VIP transport
        tempsst = new SpaceshipType("VIP Transport", "VT", SpaceShipSize.SMALL, SpaceshipRange.LONG, 2, 6);
        tempsst.setDescription("Small long range civilian VIP transport ship. Always retreat if it encounters enemy military ships without protection, unless enemy ships have stop retreats ability.");
        tempsst.setAlwaysRetreat(true);
        gw.addShipType(tempsst);


        // Planetary defence ships (also used by neutral planets)
        // ******************************************************

        // Planetary defence frigate
        tempsst = new SpaceshipType("PD Frigate", "PDF", SpaceShipSize.SMALL, 15, 50, SpaceshipRange.NONE, 1, 4, 20, 15);
        tempsst.setDescription("Small planetary defence (PB) ship. It can't move outside the starsystem it is built in. It is about as powerful as a normal Frigate, but cheaper.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        gw.addShipType(tempsst);

        // Planetary defence destroyer
        tempsst = new SpaceshipType("PD Destroyer", "PDD", SpaceShipSize.MEDIUM, 30, 100, SpaceshipRange.NONE, 1, 7, 30, 10);
        tempsst.setDescription("Medium planetary defence (PB) ship. It can't move outside the starsystem it is built in. It is about as powerful as a normal Destroyer, but cheaper.");
        tempsst.setArmor(40);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        gw.addShipType(tempsst);

        // Home fleet destroyer
        tempsst = new SpaceshipType("HF Destroyer", "HFD", SpaceShipSize.MEDIUM, 30, 100, SpaceshipRange.NONE, 0, 7, 30, 10);
        tempsst.setDescription("Medium planetary defence (PB) ship. The home fleet destroyer is identical to the PD destroyer, except that it is financed by the industrial base on faction home planets. It can't move outside the starsystem it is built in. It is about as powerful as a normal Destroyer, but cheaper.");
        tempsst.setArmor(40);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);

        // Planetary defence Cruiser
        tempsst = new SpaceshipType("PD Cruiser", "PDC", SpaceShipSize.LARGE, 80, 300, SpaceshipRange.NONE, 2, 10, 50, 10);
        tempsst.setDescription("Large planetary defence (PB) ship. It can't move outside the starsystem it is built in. It is about as powerful as a normal Cruiser, but cheaper.");
        tempsst.setArmor(60, 30);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(120);
        tempsst.setWeaponsStrengthLarge(40);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);

        tempsst = new SpaceshipType("PD Battleship", "PDB", SpaceShipSize.HUGE, 200, 700, SpaceshipRange.NONE, 4, 20, 50, 15);
        tempsst.setDescription("Huge planetary defence (PB) ship. It can't move outside the starsystem it is built in. It is almost as powerful as a normal battleship, and much cheaper.");
        tempsst.setArmor(80, 60, 40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(120);
        tempsst.setWeaponsStrengthLarge(240);
        tempsst.setWeaponsStrengthHuge(80);
        tempsst.setIncreaseInitiative(5);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);

        gw.setNeutralSize1(gw.getSpaceshipTypeByName("PD Frigate"));
        gw.setNeutralSize2(gw.getSpaceshipTypeByName("PD Destroyer"));
        gw.setNeutralSize3(gw.getSpaceshipTypeByName("PD Cruiser"));

        // Buildings
        // *********        
        // XXX BuildingTypes
        UniqueIdCounter uBIC = new UniqueIdCounter();
        BuildingType tmpBuildingType = null;
        List<BuildingType> buildings = new ArrayList<>();
        List<BuildingType> bOrb = new ArrayList<>();
        List<BuildingType> bLancer = new ArrayList<>();
        List<BuildingType> bCyber = new ArrayList<>();
        List<BuildingType> bGhost = new ArrayList<>();
        List<BuildingType> bTemplar = new ArrayList<>();

        // build troops - infantry
        tmpBuildingType = new BuildingType("Infantry Training Base", "Inf", 5);
        tmpBuildingType.setDescription("Can build one infantry troop unit every turn.");
        tmpBuildingType.setTroopSize(1);
        tmpBuildingType.addTypeOfTroop(TypeOfTroop.INFANTRY);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bGhost, bTemplar);
//        addBuildingToFactions(tmpBuildingType,bOrb,bLancer,bCyber,bGhost,bTemplar);

        // build troops - armor, Cyber excluded
        tmpBuildingType = new BuildingType("Armor Training Base", "Arm", 5);
        tmpBuildingType.setDescription("Can build one armoured troop unit every turn.");
        tmpBuildingType.setTroopSize(1);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.addTypeOfTroop(TypeOfTroop.ARMORED);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bGhost, bTemplar);

        // build troops - armor, Cyber only
        tmpBuildingType = new BuildingType("Mech Factory", "Arm", 5);
        tmpBuildingType.setDescription("Can build one mech unit every turn.");
        tmpBuildingType.setTroopSize(1);
        tmpBuildingType.addTypeOfTroop(TypeOfTroop.ARMORED);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bCyber);

        // build troops - support
        tmpBuildingType = new BuildingType("Artillery Training Base", "Art", 5);
        tmpBuildingType.setDescription("Can build one artillery or support troop unit every turn.");
        tmpBuildingType.setTroopSize(1);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.addTypeOfTroop(TypeOfTroop.SUPPORT);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost);

        // orbital wharfs
        tmpBuildingType = new BuildingType("Small Orbital Wharf", "W1", 5);
        tmpBuildingType.setDescription("Can build one small ship every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setWharfSize(1);
        tmpBuildingType.setInOrbit(true);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Medium Orbital Wharf", "W2", 10);
        tmpBuildingType.setDescription("Can build one medium or two small ship every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setWharfSize(2);
        tmpBuildingType.setParentBuildingTypeName("Small Orbital Wharf");
        tmpBuildingType.setInOrbit(true);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Large Orbital Wharf", "W3", 20);
        tmpBuildingType.setDescription("Can build one large ship or some smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setWharfSize(3);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName("Medium Orbital Wharf");
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Huge Orbital Wharf", "W5", 50);
        tmpBuildingType.setDescription("Can build one huge ship or several smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setWharfSize(5);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName("Large Orbital Wharf");
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // wharf on planet surface - small
        tmpBuildingType = new BuildingType("Small Planetary Wharf", "P1", 5);
        tmpBuildingType.setDescription("Can build one small ship every turn.");
        tmpBuildingType.setWharfSize(1);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // wharf on planet surface - upgrade to medium
        tmpBuildingType = new BuildingType("Medium Planetary Wharf", "P2", 10);
        tmpBuildingType.setDescription("Can build one medium or two small ship every turn.");
        tmpBuildingType.setWharfSize(2);
        tmpBuildingType.setParentBuildingTypeName("Small Planetary Wharf");
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // planet shields
        tmpBuildingType = new BuildingType("Small Planetary Shield", "PS1", 5);
        tmpBuildingType.setDescription("Give some protection against enemy bombardment.");
        tmpBuildingType.setShieldCapacity(1);
        tmpBuildingType.setPlanetUnique(true);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Medium Planetary Shield", "PS2", 10);
        tmpBuildingType.setDescription("Give good protection against enemy bombardment.");
        tmpBuildingType.setShieldCapacity(2);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setParentBuildingTypeName("Small Planetary Shield");
//        parent = tmpBuildingType;
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bTemplar);

        tmpBuildingType = new BuildingType("Ghost Medium Planetary Shield", "GPS2", 5);
        tmpBuildingType.setDescription("Give good protection against enemy bombardment, and is cheaper than the ordinary medium planetary shield.");
        tmpBuildingType.setShieldCapacity(2);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setParentBuildingTypeName("Small Planetary Shield"); // small planetary shield is parent
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bGhost);

        tmpBuildingType = new BuildingType("Ghost Large Planetary Shield", "GPS3", 5);
        tmpBuildingType.setDescription("Give very good protection against enemy bombardment.");
        tmpBuildingType.setShieldCapacity(3);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setParentBuildingTypeName("Ghost Medium Planetary Shield");
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bGhost);

        // Defence building
        tmpBuildingType = new BuildingType("Defensive Bunkers", "DB", 5);
        tmpBuildingType.setDescription("A system of defensive bunkers that will raise the planets resistance (+3) and aid defending troops engaged in land battle.");
        tmpBuildingType.setResistanceBonus(3);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // Ghost defence building
        tmpBuildingType = new BuildingType("Ghost Advanced Bunkers", "GAB", 10);
        tmpBuildingType.setDescription("A system of advanced and hidden defensive bunkers that will raise the planets resistance (+6) and aid defending troops engaged in land battle.");
        tmpBuildingType.setResistanceBonus(6);
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setParentBuildingTypeName("Defensive Bunkers");
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bGhost);

        // space stations
        tmpBuildingType = new BuildingType("Spaceport Class 1", "S1", 5);
        tmpBuildingType.setDescription("The smallest type of spaceport. It increases both open and closed incomes. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setOpenPlanetBonus(1);
        tmpBuildingType.setClosedPlanetBonus(1);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setPlanetUnique(true);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Spaceport Class 2", "S2", 10);
        tmpBuildingType.setDescription("The second smallest type of spaceport. It increases open incomes compared to the class 1 spaceport, and also adds defensive weapons that can destroy small hostile ships. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setOpenPlanetBonus(2);
        tmpBuildingType.setClosedPlanetBonus(1);
        tmpBuildingType.setCannonDamage(100);
        tmpBuildingType.setCannonRateOfFire(2);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName("Spaceport Class 1");
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Spaceport Class 3", "S3", 15);
        tmpBuildingType.setDescription("An average size type of spaceport. It increases both open and closed incomes compared to the class 2 spaceport. But most importantly is contains a stargate which enables short range ships to travel long range to other friendly planets that also have a stargate. It is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setOpenPlanetBonus(3);
        tmpBuildingType.setClosedPlanetBonus(2);
        tmpBuildingType.setCannonDamage(100);
        tmpBuildingType.setCannonRateOfFire(2);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName("Spaceport Class 2");
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Spaceport Class 4", "S4", 20);
        tmpBuildingType.setDescription("The second largest type of spaceport. It increases open income and add a +10 tech bonus. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setOpenPlanetBonus(4);
        tmpBuildingType.setClosedPlanetBonus(2);
        tmpBuildingType.setCannonDamage(100);
        tmpBuildingType.setCannonRateOfFire(2);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setTechBonus(10);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName("Spaceport Class 3");
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        tmpBuildingType = new BuildingType("Spaceport Class 5", "S5", 25);
        tmpBuildingType.setDescription("The largest type of spaceport. It increases both open and closed incomes, adds +10% tech bonus and upgrades the defensive weapons so that it can defend itself against hostile small and medium ships. Is vulnerable to enemy ships since it is in orbit around the planet.");
        tmpBuildingType.setOpenPlanetBonus(5);
        tmpBuildingType.setClosedPlanetBonus(3);
        tmpBuildingType.setCannonDamage(200);
        tmpBuildingType.setCannonRateOfFire(5);
        tmpBuildingType.setSpaceport(true);
        tmpBuildingType.setTechBonus(10);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setParentBuildingTypeName("Spaceport Class 4");
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // Home Planet Industrial base. Ger h�g inkomst och kan aldrig byggas.
        tmpBuildingType = new BuildingType("Orbital Industries", "OI", 0);
        tmpBuildingType.setDescription("The orbital industrial base of a home planet is constructed over several decades through organic expansion into the orbit of a major planet. Therefore it cannot be rebuilt if it is destroyed.");
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tmpBuildingType.setInOrbit(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setOpenPlanetBonus(10);
        tmpBuildingType.setClosedPlanetBonus(10);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // VIP buildings
        String[] longNames1 = {"Orb Academy", "Orb High Academy", "Orb University", "Orb High University", "Orb Elite School"};
        String[] shortNames1 = {"OA", "OHA", "OU", "OHU", "OES"};
        String[] vipNames1 = {"Field Marshal", "Orb Admiral", "Orb Scientist", "Orb Expert Scientist", "Orb Hero"};
        createVIPBuildings(gw, bOrb, buildings, uBIC, longNames1, shortNames1, vipNames1);

        String[] longNames2 = {"Lancer Economic School", "Lancer Advanced Economic School", "Lancer University", "Lancer High University", "Lancer Elite School"};
        String[] shortNames2 = {"LES", "LAES", "LE", "LME", "LCha"};
        String[] vipNames2 = {"Lancer Economic Master", "Lancer Economic Genious", "Lancer Engineer", "Lancer Master Engineer", "Lancer Champion"};
        createVIPBuildings(gw, bLancer, buildings, uBIC, longNames2, shortNames2, vipNames2);

        String[] longNames3 = {"Cyber Military Academy", "Cyber Advanced Military Academy", "Cyber Sky High University", "Cyber Advanced Sky High University", "Cyber Elite School"};
        String[] shortNames3 = {"CMA", "CAMA", "CSHU", "CASHU", "CES"};
        String[] vipNames3 = {"Cyber Elite Commander", "Cyber Field Marchal", "Cyber Ace", "Cyber Top Ace", "Cybernetic Warrior"};
        createVIPBuildings(gw, bCyber, buildings, uBIC, longNames3, shortNames3, vipNames3);

        String[] longNames4 = {"Ghost Covert Ops School", "Ghost Advanced Covert Ops School", "Ghost Subversion University", "Ghost Advanced Subversion University", "Ghost Elite School"};
        String[] shortNames4 = {"GCOS", "GACOS", "GSU", "GASU", "GES"};
        String[] vipNames4 = {"Ghost Spy", "Ghost Assassin", "Ghost Security Chief", "Ghost Smuggler", "Ghost Fighter"};
        createVIPBuildings(gw, bGhost, buildings, uBIC, longNames4, shortNames4, vipNames4);

        String[] longNames5 = {"Temple of Reaping", "Templar of Reaping and Pathfinding", "Temple of Harvest", "Temple of Harvest and Mind", "Templar Fortress of Power"};
        String[] shortNames5 = {"ToR", "ToRP", "ToH", "ToHM", "TFoP"};
        String[] vipNames5 = {"Templar Assassin", "Templar Star Navigator", "Templar Counter-Spy", "Templar Infestator", "Templar Knight"};
        createVIPBuildings(gw, bTemplar, buildings, uBIC, longNames5, shortNames5, vipNames5);

        tmpBuildingType = new BuildingType("Mercenary Liason Office", "MLO", 100);
        tmpBuildingType.setDescription("This unique building enables the owner to hire all the different types of mercenary VIPs.");
        for (VIPType mercenaryType : mercenaryVIPs) {
            tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(mercenaryType.getName()));
        }
        tmpBuildingType.setInOrbit(false);
        tmpBuildingType.setWorldUnique(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        addBuildingToFactions(tmpBuildingType, bOrb, bLancer, bCyber, bGhost, bTemplar);

        // Factions
        // ********

        // *******
        // * Orb *
        // *******
        // XXX Orb faction
        Faction tempFaction = new Faction("Orb", Faction.getColorHexString(255, 255, 63), orb);
        GameWorldCreator.addFaction(tempFaction, gw);
        tempFaction.setDescription("The Orb faction is a human faction. They use flexible design and tactics in both their spaceships and troops.");

        // Troop types
        // ***********

        tempFaction.addTroopType(gw.getTroopTypeByName("Militia"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Militia Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Scout Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Light Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Light Tanks"));

        //TODO
        //tempFaction.addTroopType(gw.getTroopTypeByName("Orb Anti-Air Light Armor"));        
        tempFaction.addTroopType(gw.getTroopTypeByName("Orb Heavy Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Orb Heavy Tanks"));

        // Building Types
        // **************

        tempFaction.setBuildings(bOrb);

        // Spaceship types
        // ***************

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Cruiser"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Small Merchant Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("VIP Transport"));

        // Orb Fighter
        typeName = "Orb Fighter";
        tempsst = new SpaceshipType(typeName, "O-F", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC, SpaceshipRange.NONE, 1, 3, sqdBaseSmAtt, sqdBaseFightSqdAtt);
        tempsst.setDescription("A fighter squadron specialized in attacking enemy squadrons. It can do very little damage to enemy capital ships. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        typeName = "Orb Fighter-Bomber";
        tempsst = new SpaceshipType(typeName, "O-FB", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC + 5, SpaceshipRange.NONE, 1, 5, sqdBaseSmAtt, sqdBaseFightSqdAtt - 5);
        tempsst.setDescription("An all-round pod squadron which can both do some damage against squadrons or capital ships, where its two-shot medium and large torpedo salvoes can hurt larger ships. It cannot move on it's own but has to be attached to a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(25);
        tempsst.setWeaponsMaxSalvoesMedium(2);
        tempsst.setWeaponsStrengthLarge(25);
        tempsst.setWeaponsMaxSalvoesLarge(2);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Orb Bomber
        typeName = "Orb Bomber";
        tempsst = new SpaceshipType(typeName, "O-B", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC + 5, SpaceshipRange.NONE, 1, 5, sqdBaseSmAtt, sqdBaseBombSqdAtt);
        tempsst.setDescription("Squadron specialized in attacking capital ships, especially ships larger than small where its medium and large torpedo salvoes can can do significant damage. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(50);
        tempsst.setWeaponsMaxSalvoesMedium(2);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Orb Troop carriers
        typeName = "Orb Troop Shuttles";
        tempsst = new SpaceshipType(typeName, "O-TS", SpaceShipSize.SQUADRON, 0, sqdBaseDC - 5, SpaceshipRange.NONE, 1, 4, 5, 5);
        tempsst.setDescription("A troop carrying pod squadron design. It is almost useless in combat. It cannot move on it's own but has to be attached to a pod carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setTroopCapacity(1);
        tempsst.setScreened(true);
        //       tempsst.setTroopLaunchCapacity(1);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Orb Marine Strike Force
        typeName = "Orb Marine Strike Force";
        tempsst = new SpaceshipType(typeName, "O-MS", SpaceShipSize.SQUADRON, sqdBaseSh - 5, sqdBaseDC - 5, SpaceshipRange.NONE, 1, 4, 1, 5);
        tempsst.setDescription("Squadron carrying a small marine detachment who can besiege planets and lower their resistance. Virtually useless in combat and should probably be screened. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setPsychWarfare(1);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        /*
        // Orb Ground attack
        typeName = "Orb Ground Attack";
        tempsst = new SpaceshipType(typeName,"O-GA",SpaceShipSize.SMALL,sqdBaseSh-5,sqdBaseDC-5,SpaceshipRange.NONE,1,4,uSIC,1,5);
		tempsst.setDescription("Air-to-ground attack squadron which can attack enemt troops on planet surface. Virtually useless in combat and should probably be screened. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setSquadron(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setCanPerformAirToGroundAttacks(true);
        tempsst.setWeaponsAirToGround(sqdBaseAirToGroundAtt);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));
        */

        // Orb bombardment
        typeName = "Orb Planetary Bombardment";
        tempsst = new SpaceshipType(typeName, "O-PB", SpaceShipSize.SQUADRON, sqdBaseSh - 5, sqdBaseDC - 5, SpaceshipRange.NONE, 1, 6, 1, 5);
        tempsst.setDescription("Air-to-ground attack squadron which can bombard planets. Virtually useless in combat and should probably be screened. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setBombardment(2);
        tempsst.setAvailableToBuild(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Orb Scout
        typeName = "Orb Scout";
        tempsst = new SpaceshipType(typeName, "O-S", SpaceShipSize.SQUADRON, sqdBaseSh - 5, sqdBaseDC - 10, SpaceshipRange.NONE, 1, 6, 1, 5);
        tempsst.setDescription("Reconnaissance squadron which can survey planets. Virtually useless in combat and should probably be screened. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setPlanetarySurvey(true);
        tempsst.setAvailableToBuild(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Orb Heavy Bomber
        typeName = "Orb Heavy Bomber";
        tempsst = new SpaceshipType(typeName, "O-HB", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC + 5, SpaceshipRange.NONE, 1, 7, sqdBaseSmAtt - 5, sqdBaseBombSqdAtt - 5);
        tempsst.setDescription("Squadron specialized in attacking large and huge capital ships, where its large and huge torpedo salvoes can can do significant damage. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setWeaponsStrengthHuge(50);
        tempsst.setWeaponsMaxSalvoesHuge(4);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Orb Advanced Fighter
        typeName = "Orb Advanced Fighter";
        tempsst = new SpaceshipType(typeName, "O-AF", SpaceShipSize.SQUADRON, sqdBaseSh + 5, sqdBaseDC + 5, SpaceshipRange.NONE, 1, 8, sqdBaseSmAtt, sqdBaseFightSqdAtt + 5);
        tempsst.setDescription("An advanced fighter squadron which can also do some damage against medium-sized capital ships. It cannot move on it's own but has to be attached to a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        typeName = "Orb Tripod";
        tempsst = new SpaceshipType(typeName, "O3P", SpaceShipSize.SMALL, 0, 40, SpaceshipRange.LONG, 1, 5, 5, 5);
        tempsst.setDescription("Small-sized long range squadron-carrying ship. It can carry 3 squadrons. Weak in combat, it should probably be screened at all times.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(3);
        tempsst.setCanBlockPlanet(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        typeName = "Orb Hexapod";
        tempsst = new SpaceshipType(typeName, "O6P", SpaceShipSize.MEDIUM, 0, 100, SpaceshipRange.LONG, 2, 8, 5, 5);
        tempsst.setDescription("Medium-sized long range squadron-carrying ship. It can carry 6 squadrons. Weak in combat, it should probably be screened at all times.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(6);
        tempsst.setCanBlockPlanet(false);
        tempsst.setAvailableToBuild(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        typeName = "Orb Pendecapod";
        tempsst = new SpaceshipType(typeName, "O15P", SpaceShipSize.LARGE, 0, 200, SpaceshipRange.LONG, 3, 15, 10, 10);
        tempsst.setDescription("Large-sized long range squadron-carrying ship. It can carry 15 squadrons. Weak in combat, it should probably be screened at all times.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(15);
        tempsst.setCanBlockPlanet(false);
        tempsst.setAvailableToBuild(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        typeName = "Orb Centipod";
        tempsst = new SpaceshipType(typeName, "OCP", SpaceShipSize.HUGE, 0, 400, SpaceshipRange.SHORT, 1, 5, 15, 15);
        tempsst.setDescription("Huge-sized centipede-like long range squadron-carrying ship. It can carry 100 squadrons. Weak in combat, it should probably be screened at all times.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(100);
        tempsst.setCanBlockPlanet(false);
        tempsst.setAvailableToBuild(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Research - Orb
        // **************

        ResearchAdvantage aRA = null;

        createTTresearch(gw, tempFaction, "Militia Artillery", null);
        aRA = createTTresearch(gw, tempFaction, "Scout Infantry", null);
        aRA = createTTresearch(gw, tempFaction, "Light Artillery", aRA);
        aRA = createTTresearch(gw, tempFaction, "Light Tanks", aRA);
        //TODO
        //aRA = createTTresearch(gw,tempFaction,"Orb Anti-Air Light Armor",aRA);
        aRA = createTTresearch(gw, tempFaction, "Orb Heavy Artillery", aRA);
        aRA = createTTresearch(gw, tempFaction, "Orb Heavy Tanks", aRA);

        // large and huge ship sizes
        ResearchAdvantage oLargeShips = createLargeShipsResearch(tempFaction);
        ResearchAdvantage oHugeShips = createHugeShipsResearch(tempFaction, oLargeShips);
        ;

        createSTresearch(gw, tempFaction, "PD Cruiser", oLargeShips, "Large defence shiptype: ");

        ResearchAdvantage sqd = createSTresearch(gw, tempFaction, "Orb Fighter", null, "Squadron shiptype: ");
        sqd = createSTresearch(gw, tempFaction, "Orb Bomber", sqd, "Squadron shiptype: ");
        //TODO
        //sqd = createSTresearch(gw,tempFaction,"Orb Ground Attack",sqd,"Squadron shiptype: ");
        sqd = createSTresearch(gw, tempFaction, "Orb Planetary Bombardment", sqd, "Squadron shiptype: ");
        sqd = createSTresearch(gw, tempFaction, "Orb Scout", sqd, "Squadron shiptype: ");
        sqd = createSTresearch(gw, tempFaction, "Orb Heavy Bomber", sqd, "Squadron shiptype: ");
        createSTresearch(gw, tempFaction, "Orb Advanced Fighter", sqd, "Squadron shiptype: ");
        ResearchAdvantage car = createSTresearch(gw, tempFaction, "Orb Hexapod", null, "Carrier shiptype: ");
        car = createSTresearch(gw, tempFaction, "Orb Pendecapod", car, "Carrier shiptype: ");
        car.addParent(oLargeShips);
        car = createSTresearch(gw, tempFaction, "Orb Centipod", car, "Carrier shiptype: ");
        car.addParent(oHugeShips);

        // Buildings
        createBTresearch(buildings, tempFaction, "Medium Planetary Shield", null, "Upgrade planet shield building to protect against bomberdment of 2");

        ResearchAdvantage olow = createBTresearch(buildings, tempFaction, "Large Orbital Wharf", oLargeShips, "Can build one large ship or some smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ResearchAdvantage ohow = createBTresearch(buildings, tempFaction, "Huge Orbital Wharf", olow, "Can build one huge ship or several smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ohow.addParent(oHugeShips);

        ResearchAdvantage oha = createBTresearch(buildings, tempFaction, "Orb High Academy", null, "");
        ResearchAdvantage ohu = createBTresearch(buildings, tempFaction, "Orb High University", oha, "");
        ResearchAdvantage oes = createBTresearch(buildings, tempFaction, "Orb Elite School", ohu, "");
        createBTresearch(buildings, tempFaction, "Mercenary Liason Office", oes, "");

        ResearchAdvantage oarm = createBTresearch(buildings, tempFaction, "Armor Training Base", null, "Enables the building of training bases which can produce armored troop units.");
        createBTresearch(buildings, tempFaction, "Artillery Training Base", oarm, "Enables the building of training bases which can produce artillery and support troop units.");

        createBTresearch(buildings, tempFaction, "Defensive Bunkers", null, "Enables the building of planet surface bunkers which increases a planets resistance.");

        createSpaceportResearch(buildings, tempFaction, oLargeShips, oHugeShips);

        // Corruption Research
        createCResearch(tempFaction);

        // Tech research
        createTechResearch(tempFaction, null, 4);

        // Faction bonuses
        createFactionTresearch(tempFaction, "Orb open income bonus", "Increase open income on all planets to +1.", null, 1, 0, 0);

        

        // Orb Governor
        // ************

        tmpVipType = new VIPType("Orb Governor", "OGov", orb);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setOpenIncBonus(1);
        tmpVipType.setResistanceBonus(3);
        gw.addVipType(tmpVipType);
        tempFaction.setGovernorVIPType(tmpVipType);

        // starting units
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Tripod"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Tripod"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Fighter-Bomber"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Fighter-Bomber"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Fighter-Bomber"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Troop Shuttles"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Troop Shuttles"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Orb Marine Strike Force"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Militia"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Infantry"));

        // starting buildings
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Medium Orbital Wharf"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Infantry Training Base"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Orbital Industries"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Small Planetary Shield"));

        // other faction abilities
        tempFaction.setCanReconstruct(true);
        tempFaction.setNrStartingRandomVIPs(1);
        tempFaction.addStartingVIPType(orbStart1);
        tempFaction.addStartingVIPType(orbStart2);
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        Faction orbFaction = tempFaction;


        // **********
        // * Lancer *
        // **********
        // XXX Lancer faction
        tempFaction = new Faction("Lancer", Faction.getColorHexString(140, 140, 255), lancer);
        GameWorldCreator.addFaction(tempFaction, gw);
        tempFaction.setDescription("The Lancer faction is a human faction. They believe firmly in making profits from trade and the use of diplomacy is the best way to achieve power. Especially if it is backed by some very large and very armed spaceships.");

        // Troop types
        // ***********

        tempFaction.addTroopType(gw.getTroopTypeByName("Militia"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Militia Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Scout Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Light Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Light Tanks"));

        // Building Types
        // **************

        tempFaction.setBuildings(bLancer);

        // Lancer Spaceship Types
        // **********************

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Cruiser"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Battleship"));

        // Lancer Scout
        typeName = "Lancer Scout";
        tempsst = new SpaceshipType(typeName, "LSc", SpaceShipSize.SMALL, 5, 10, SpaceshipRange.LONG, 1, 2, 5, 5);
        tempsst.setDescription("Small-sized long range military scout ship. Very small military ship useful for scouting missions and to carry VIPs. Also useful for commerce raiding. It is virtually useless in combat. Cannot besiege/block planets.");
        tempsst.setCanBlockPlanet(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Lancer Frigate
        typeName = "Lancer Frigate";
        tempsst = new SpaceshipType(typeName, "LFr", SpaceShipSize.SMALL, 20, 60, SpaceshipRange.LONG, 3, 6, 20, 15);
        tempsst.setDescription("Small-sized long range military ship. Useful for blocking enemny planets, scouting missions, commerce raiding and to carry VIPs. It can be used in combat against small enemies and maybe support against medium enemy ships, but against larger ships it is virtually useless.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Lancer Destroyer
        typeName = "Lancer Destroyer";
        tempsst = new SpaceshipType(typeName, "LDe", SpaceShipSize.MEDIUM, 50, 150, SpaceshipRange.LONG, 5, 15, 35, 10);
        tempsst.setDescription("Medium-sized long range military ship. It is most useful when attacking small or medium opponents or as support against less powerful large ships. It is too weak to do any significant damage against huge opponents.");
        tempsst.setArmor(40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(35);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Lancer Cruiser
        typeName = "Lancer Cruiser";
        tempsst = new SpaceshipType(typeName, "LCr", SpaceShipSize.LARGE, 120, 400, SpaceshipRange.SHORT, 10, 30, 40, 10);
        tempsst.setDescription("Large-sized short-range capital ship. It is very good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It has a marine detachment, bombardment and can carry one squadron.");
        tempsst.setArmor(60, 40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(130);
        tempsst.setWeaponsStrengthLarge(80);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Lancer Q-ship Cruiser
        typeName = "Lancer Q-ship Cruiser";
        tempsst = new SpaceshipType("Lancer Q-ship Cruiser", "LQCr", SpaceShipSize.LARGE, 100, 350, SpaceshipRange.SHORT, 10, 35, 40, 10);
        tempsst.setDescription("Large-sized short-range disguised capital ship. The Lancer Q-ship lokks like a civilian right until it enters battle with enemy military ships. It is good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It has a marine detachment onboard.");
        tempsst.setArmor(50, 30);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(100);
        tempsst.setWeaponsStrengthLarge(60);
        tempsst.setWeaponsMaxSalvoesLarge(4);
//        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setLookAsCivilian(true);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Lancer Battleship
        typeName = "Lancer Battleship";
        tempsst = new SpaceshipType(typeName, "LBs", SpaceShipSize.HUGE, 300, 1000, SpaceshipRange.SHORT, 25, 80, 60, 30);
        tempsst.setDescription("Huge-sized short range capital ship. Have a powerful array of weapons that make massive damage to ships of all sizes, and have very powerful armor. It has planetary bombardment, It can carry up to two troops, have a marine detachment, can carry up to four squadrons and have an advanced tactical center to increase combat efficiency.");
        tempsst.setArmor(90, 70, 50);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(120);
        tempsst.setWeaponsStrengthLarge(200);
        tempsst.setWeaponsStrengthHuge(200);
        tempsst.setWeaponsMaxSalvoesHuge(4);
//        tempsst.setSquadronCapacity(4);
        tempsst.setTroopCapacity(2);
        //      tempsst.setTroopLaunchCapacity(1);
        tempsst.setBombardment(2);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(5);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Lancer Leviathan
        typeName = "Lancer Leviathan";
        tempsst = new SpaceshipType(typeName, "LL", SpaceShipSize.HUGE, 400, 1500, SpaceshipRange.SHORT, 40, 150, 60, 30);
        tempsst.setDescription("This huge-sized short range capital ship is the most powerful capital ship in known space. It has a powerful array of weapons that make massive damage to ships of all sizes, and have very powerful armor. It can carry up to four troops, have an elite marine detachment, have powerful bombardment, carry up to four squadrons and have an advanced tactical center to increase combat efficiency.");
        tempsst.setArmor(90, 75, 60);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(300);
        tempsst.setWeaponsStrengthLarge(300);
        tempsst.setWeaponsStrengthHuge(300);
        tempsst.setWeaponsMaxSalvoesHuge(10);
//        tempsst.setSquadronCapacity(4);'
        tempsst.setTroopCapacity(4);
        //       tempsst.setTroopLaunchCapacity(2);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(2);
        tempsst.setIncreaseInitiative(10);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Civilian ships
        // **************

        typeName = "Lancer Troop Transport";
        tempsst = new SpaceshipType(typeName, "LTT", SpaceShipSize.MEDIUM, SpaceshipRange.LONG, 1, 5);
        tempsst.setDescription("Medium-sized long range civilian troop ship. It carries troop landing craft and have place for three troop units. It also carries a small marine detachment which can lower a besieged planets resistance. Note that this ship cannot besiege planets on it's own, but when in company with at least one military ship which can besiege planets, it can use it's marine detachment and troops against planets.");
        tempsst.setCivilian(true);
        tempsst.setPsychWarfare(1);
        tempsst.setTroopCapacity(3);
        //      tempsst.setTroopLaunchCapacity(1);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Small Merchant Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("VIP Transport"));

        // Medium Merchant Freighter
        typeName = "Medium Merchant Freighter";
        tempsst = new SpaceshipType(typeName, "MMF", SpaceShipSize.MEDIUM, SpaceshipRange.LONG, 0, 8);
        tempsst.setDescription("Medium-sized long range civilian merchant ship. Give income bonuses on frendly and neutral open planets.");
        tempsst.setCivilian(true);
        tempsst.setIncOwnOpenBonus(5);
        tempsst.setIncFriendlyOpenBonus(5);
        tempsst.setIncNeutralOpenBonus(6);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Large Merchant Freighter
        typeName = "Large Merchant Freighter";
        tempsst = new SpaceshipType(typeName, "LMF", SpaceShipSize.LARGE, SpaceshipRange.LONG, 0, 12);
        tempsst.setDescription("Large-sized long range civilian merchant ship. Give income bonuses on frendly and neutral open planets.");
        tempsst.setCivilian(true);
        tempsst.setIncOwnOpenBonus(7);
        tempsst.setIncFriendlyOpenBonus(7);
        tempsst.setIncNeutralOpenBonus(9);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Huge Merchant Freighter
        typeName = "Huge Merchant Freighter";
        tempsst = new SpaceshipType(typeName, "HMF", SpaceShipSize.HUGE, SpaceshipRange.LONG, 0, 16);
        tempsst.setDescription("Huge-sized short range civilian merchant ship. Give income bonuses on frendly and neutral open planets.");
        tempsst.setCivilian(true);
        tempsst.setIncOwnOpenBonus(9);
        tempsst.setIncFriendlyOpenBonus(9);
        tempsst.setIncNeutralOpenBonus(12);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);

        // Research - Lancer
        // *****************

        
        aRA = null;

        createTTresearch(gw, tempFaction, "Militia Artillery", null);
        aRA = createTTresearch(gw, tempFaction, "Scout Infantry", null);
        aRA = createTTresearch(gw, tempFaction, "Light Artillery", aRA);
        createTTresearch(gw, tempFaction, "Light Tanks", aRA);

        // large and huge ship sizes
        ResearchAdvantage lLargeShips = createLargeShipsResearch(tempFaction);
        ResearchAdvantage lHugeShips = createHugeShipsResearch(tempFaction, lLargeShips);
        ;

        // def ships
        ResearchAdvantage pdc = createSTresearch(gw, tempFaction, "PD Cruiser", lLargeShips, "Large defence shiptype: ");
        ResearchAdvantage lbs = createSTresearch(gw, tempFaction, "PD Battleship", lHugeShips, "Huge defence shiptype: ");
        lbs.addParent(pdc);

        // capital ships
        ResearchAdvantage cap = createSTresearch(gw, tempFaction, "Lancer Cruiser", lLargeShips);
        cap = createSTresearch(gw, tempFaction, "Lancer Q-ship Cruiser", cap);
        cap = createSTresearch(gw, tempFaction, "Lancer Battleship", cap);
        cap.addParent(lHugeShips);
        createSTresearch(gw, tempFaction, "Lancer Leviathan", cap);

        // civ research
        ResearchAdvantage civ = createSTresearch(gw, tempFaction, "Large Merchant Freighter", lLargeShips, "Large civilian shiptype: ");
        civ = createSTresearch(gw, tempFaction, "Huge Merchant Freighter", civ, "Huge civilian shiptype: ");
        civ.addParent(lHugeShips);

        // Buildings
        createBTresearch(buildings, tempFaction, "Medium Planetary Shield", null, "Upgrade planet shield building to protect against bomberdment of 2");

        ResearchAdvantage llow = createBTresearch(buildings, tempFaction, "Large Orbital Wharf", lLargeShips, "Can build one large ship or some smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ResearchAdvantage lhow = createBTresearch(buildings, tempFaction, "Huge Orbital Wharf", llow, "Can build one huge ship or several smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        lhow.addParent(lHugeShips);

        ResearchAdvantage ls1 = createBTresearch(buildings, tempFaction, "Lancer Advanced Economic School", null, "");
        ResearchAdvantage ls2 = createBTresearch(buildings, tempFaction, "Lancer High University", ls1, "");
        ResearchAdvantage ls3 = createBTresearch(buildings, tempFaction, "Lancer Elite School", ls2, "");
        createBTresearch(buildings, tempFaction, "Mercenary Liason Office", ls3, "");

        ResearchAdvantage larm = createBTresearch(buildings, tempFaction, "Armor Training Base", null, "Enables the building of training bases which can procude armored troop units.");
        createBTresearch(buildings, tempFaction, "Artillery Training Base", larm, "Enables the building of training bases which can procude artillery and support troop units.");

        createBTresearch(buildings, tempFaction, "Defensive Bunkers", null, "Enables the building of planet surface bunkers which increases a planets resistance.");

        createSpaceportResearch(buildings, tempFaction, lLargeShips, lHugeShips);

        // Corruption Research
        createCResearch(tempFaction);

        // Tech research
        createTechResearch(tempFaction, null, 4);

        // Faction bonuses
        ResearchAdvantage lob = createFactionTresearch(tempFaction, "Lancer open income bonus 1", "Increase open income on all planets with +2.", null, 2, 0, 0);
        lob = createFactionTresearch(tempFaction, "Lancer open income bonus 2", "Increase open income on all planets with an additional +1.", lob, 1, 0, 0);
        lob = createFactionTresearch(tempFaction, "Lancer open income bonus 3", "Increase open income on all planets with an additional +1.", lob, 1, 0, 0);
        createFactionTresearch(tempFaction, "Lancer open income bonus 4", "Increase open income on all planets with an additional +1.", lob, 1, 0, 0);

        

        // Lancer Governor
        // ***************

        tmpVipType = new VIPType("Lancer Governor", "LGov", lancer);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setOpenIncBonus(2);
        tmpVipType.setResistanceBonus(2);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        gw.addVipType(tmpVipType);
        tempFaction.setGovernorVIPType(tmpVipType);

        // starting units
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Lancer Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Lancer Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Lancer Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Medium Merchant Freighter"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Lancer Troop Transport"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Militia"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Infantry"));

        // starting buildings
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Medium Orbital Wharf"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Infantry Training Base"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Orbital Industries"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Small Planetary Shield"));

        // other faction abilities
        tempFaction.setCanReconstruct(true);
        tempFaction.setOpenPlanetBonus(1);
        tempFaction.setNrStartingRandomVIPs(2);
        tempFaction.addStartingVIPType(lancerStart1);
        tempFaction.addStartingVIPType(lancerStart2);
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        Faction lancerFaction = tempFaction;

        // *********
        // * Cyber *
        // *********
        // XXX Cyber faction
        tempFaction = new Faction("Cyber", Faction.getColorHexString(0, 255, 255), cyber);
        GameWorldCreator.addFaction(tempFaction, gw);

        // Troop types
        // ***********

        tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Mechs"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Scout Mechs"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Antitank Mechs"));
        //TODO
        //tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Anti-air Mechs"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Artillery Mechs"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Heavy Mechs"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Cyber Advanced Mechs"));

        // Building Types
        // **************

        tempFaction.setBuildings(bCyber);

        // Cyber Spaceship Types
        // *********************

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Cruiser"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Small Merchant Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("VIP Transport"));

        // Cyber Fighter
        typeName = "Cyber Fighter";
        tempsst = new SpaceshipType(typeName, "C-F", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC, SpaceshipRange.NONE, 1, 5, sqdBaseSmAtt, sqdBaseFightSqdAtt + 10);
        tempsst.setDescription("A fighter squadron specialized in attacking enemy squadrons. It can do very little damage to enemy capital ships, but it's cybernetically integrated pilot is superior in dogfighting. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Light Bomber
        typeName = "Cyber Light Bomber";
        tempsst = new SpaceshipType(typeName, "C-LB", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC, SpaceshipRange.NONE, 1, 6, sqdBaseSmAtt, sqdBaseFightSqdAtt);
        tempsst.setDescription("Squadron specialized in attacking capital ships, especially ships larger than small where its medium torpedo salvoes can can do significant damage, and it's cybernetically integrated pilot is good at dogfighting. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(50);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Bomber
        typeName = "Cyber Bomber";
        tempsst = new SpaceshipType(typeName, "C-B", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC, SpaceshipRange.NONE, 1, 6, sqdBaseSmAtt, sqdBaseBombSqdAtt);
        tempsst.setDescription("Squadron specialized in attacking capital ships, especially ships larger than medium where its large and huge torpedo salvoes can can do significant damage. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setWeaponsStrengthHuge(50);
        tempsst.setWeaponsMaxSalvoesHuge(2);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));
/*
        // Cyber Ground Attack Fighter
        typeName = "Cyber Ground Attack Fighter";
        tempsst = new SpaceshipType(typeName,"C-GA",SpaceShipSize.SMALL,sqdBaseSh,sqdBaseDC,SpaceshipRange.NONE,1,6,uSIC,sqdBaseSmAtt-5,sqdBaseFightSqdAtt);
		tempsst.setDescription("A ground attack fighter squadron, which can both do some damage against enemy squadrons and enemy ground troops during a ground battle. It cannot move on it's own but has to be attached to a carrier.");
        tempsst.setSquadron(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsAirToGround(40);
        tempsst.setCanPerformAirToGroundAttacks(true);
        tempsst.setDefaultAirToGroundAttackStatus(true);
        tempsst.setCanBlockPlanet(false);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);
*/
        // Cyber Advanced Fighter
        typeName = "Cyber Advanced Fighter";
        tempsst = new SpaceshipType(typeName, "C-AF", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC + 5, SpaceshipRange.NONE, 1, 8, sqdBaseSmAtt + 5, sqdBaseFightSqdAtt + 15);
        tempsst.setDescription("A fighter squadron specialized in attacking enemy squadrons. It can do very little damage to enemy capital ships, but it's cybernetically integrated pilot is superior in dogfighting. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setWeaponsStrengthMedium(25);
        tempsst.setWeaponsMaxSalvoesMedium(2);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Scout
        typeName = "Cyber Scout";
        tempsst = new SpaceshipType(typeName, "CSc", SpaceShipSize.SMALL, 5, 10, SpaceshipRange.LONG, 1, 4, 5, 5);
        tempsst.setDescription("Small-sized long range military scout ship. Very small military ship useful for scouting missions and surveying planets and to carry VIPs. Also useful for commerce raiding. It is virtually useless in combat. Cannot besiege/block planets.");
        tempsst.setCanBlockPlanet(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Troop Dropship
        typeName = "Cyber Troop Dropship";
        tempsst = new SpaceshipType(typeName, "CTD", SpaceShipSize.SMALL, 10, 50, SpaceshipRange.LONG, 3, 6, 5, 5);
        tempsst.setDescription("Small-sized long range military ship. Useful for blocking enemny planets, scouting missions, commerce raiding and to carry VIPs. It can carry one troop and one squadron. It is very weak in combat.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(1);
        tempsst.setTroopCapacity(1);
        tempsst.setScreened(true);
        //     tempsst.setTroopLaunchCapacity(1);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Troop Light Carrier
        typeName = "Cyber Troop Light Carrier";
        tempsst = new SpaceshipType(typeName, "CTLC", SpaceShipSize.MEDIUM, 20, 120, SpaceshipRange.LONG, 4, 12, 5, 5);
        tempsst.setDescription("Medium-sized long range military troop transport ship. It can carry 3 troops and launch one troop each turn. It also carries a small marine detachment. It is too weak to do any significant damage against anything but small opponents.");
        tempsst.setArmor(20);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setPsychWarfare(1);
        tempsst.setSquadronCapacity(2);
        tempsst.setTroopCapacity(3);
        tempsst.setScreened(true);
        //    tempsst.setTroopLaunchCapacity(1);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Troop Advanced Carrier
        typeName = "Cyber Troop Advanced Carrier";
        tempsst = new SpaceshipType(typeName, "CTLC", SpaceShipSize.MEDIUM, 10, 100, SpaceshipRange.LONG, 5, 15, 5, 5);
        tempsst.setDescription("Medium-sized long range military troop transport ship. It can carry 3 troops and launch 3 troop each turn. It also carries a small marine detachment. It is too weak to do any significant damage against anything but small opponents.");
        tempsst.setArmor(10);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setPsychWarfare(1);
        tempsst.setSquadronCapacity(3);
        tempsst.setTroopCapacity(3);
        tempsst.setScreened(true);
        //      tempsst.setTroopLaunchCapacity(3);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Cruiser
        typeName = "Cyber Cruiser";
        tempsst = new SpaceshipType(typeName, "CCr", SpaceShipSize.LARGE, 120, 400, SpaceshipRange.SHORT, 10, 30, 40, 10);
        tempsst.setDescription("Large-sized short-range capital ship. It is very good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It has a small marine detachment, bombardment and can carry two squadron.");
        tempsst.setArmor(60, 40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(150);
        tempsst.setWeaponsStrengthLarge(100);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Troop Carrier
        typeName = "Cyber Troop Carrier";
        tempsst = new SpaceshipType(typeName, "CTC", SpaceShipSize.LARGE, 50, 300, SpaceshipRange.SHORT, 8, 25, 10, 10);
        tempsst.setDescription("Large-sized long-range capital ship. It is good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It can carry up to 6 troops, has a small marine detachment, bombardment and can carry one squadron.");
        tempsst.setArmor(40, 20);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(6);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setTroopCapacity(6);
        tempsst.setScreened(true);
        //     tempsst.setTroopLaunchCapacity(2);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Cyber Battleship
        typeName = "Cyber Battleship";
        tempsst = new SpaceshipType(typeName, "CBs", SpaceShipSize.HUGE, 300, 1000, SpaceshipRange.SHORT, 25, 80, 60, 30);
        tempsst.setDescription("Huge-sized short range capital ship. Have a powerful array of weapons that make massive damage to ships of all sizes, and have very powerful armor. It has planetary bombardment, a small marine detachment, can carry up to four squadrons and have an advanced tactical center to increase combat efficiency.");
        tempsst.setArmor(90, 70, 50);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(120);
        tempsst.setWeaponsStrengthLarge(200);
        tempsst.setWeaponsStrengthHuge(200);
        tempsst.setWeaponsMaxSalvoesHuge(4);
        tempsst.setSquadronCapacity(4);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(5);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Research - Cyber
        // *****************

        
        aRA = null;

        aRA = createTTresearch(gw, tempFaction, "Cyber Scout Mechs", null);
        aRA = createTTresearch(gw, tempFaction, "Cyber Antitank Mechs", aRA);
        //TODO
        //aRA = createTTresearch(gw,tempFaction,"Cyber Anti-air Mechs",aRA);
        aRA = createTTresearch(gw, tempFaction, "Cyber Artillery Mechs", aRA);
        aRA = createTTresearch(gw, tempFaction, "Cyber Heavy Mechs", aRA);
        createTTresearch(gw, tempFaction, "Cyber Advanced Mechs", aRA);

        // large and huge ship sizes
        ResearchAdvantage cLargeShips = createLargeShipsResearch(tempFaction);
        ResearchAdvantage cHugeShips = createHugeShipsResearch(tempFaction, cLargeShips);
        ;

        // def ship
        createSTresearch(gw, tempFaction, "PD Cruiser", cLargeShips, "Large defence shiptype: ");

        // squadrons
        ResearchAdvantage csqd = createSTresearch(gw, tempFaction, "Cyber Bomber", null, "Squadron shiptype: ");
        //TODO
        //csqd = createSTresearch(gw,tempFaction,"Cyber Ground Attack Fighter",csqd,"Squadron shiptype: ");
        createSTresearch(gw, tempFaction, "Cyber Advanced Fighter", csqd, "Squadron shiptype: ");

        // capital ships
        ResearchAdvantage ccr = createSTresearch(gw, tempFaction, "Cyber Cruiser", cLargeShips);
        ResearchAdvantage ctc = createSTresearch(gw, tempFaction, "Cyber Troop Carrier", ccr);
        createSTresearch(gw, tempFaction, "Cyber Troop Advanced Carrier", ctc);
        ResearchAdvantage cbs = createSTresearch(gw, tempFaction, "Cyber Battleship", ccr);
        cbs.addParent(cHugeShips);

        // Buildings
        createBTresearch(buildings, tempFaction, "Medium Planetary Shield", null, "Upgrade planet shield building to protect against bomberdment of 2");

        ResearchAdvantage clow = createBTresearch(buildings, tempFaction, "Large Orbital Wharf", cLargeShips, "Can build one large ship or some smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ResearchAdvantage chow = createBTresearch(buildings, tempFaction, "Huge Orbital Wharf", clow, "Can build one huge ship or several smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        chow.addParent(cHugeShips);

        ResearchAdvantage cs1 = createBTresearch(buildings, tempFaction, "Cyber Advanced Military Academy", null, "");
        ResearchAdvantage cs2 = createBTresearch(buildings, tempFaction, "Cyber Advanced Sky High University", cs1, "");
        ResearchAdvantage cs3 = createBTresearch(buildings, tempFaction, "Cyber Elite School", cs2, "");
        createBTresearch(buildings, tempFaction, "Mercenary Liason Office", cs3, "");

/* Cyber can build armored training camp from beginning, and can never build any support units        
        ResearchAdvantage carm = createBTresearch(gw,tempFaction,"Armor Training Base",null,"Enables the building of training bases which can procude armored troop units.");
        createBTresearch(gw,tempFaction,"Artillery Training Base",carm,"Enables the building of training bases which can procude artillery and support troop units.");
*/
        createBTresearch(buildings, tempFaction, "Defensive Bunkers", null, "Enables the building of planet surface bunkers which increases a planets resistance.");

        createSpaceportResearch(buildings, tempFaction, cLargeShips, cHugeShips);

        // Corruption Research
        createCResearch(tempFaction);

        // Tech research
        createTechResearch(tempFaction, null, 4);

        // Faction bonuses
        createFactionTresearch(tempFaction, "Cyber open income bonus", "Increase open income on all planets to +1.", null, 1, 0, 0);

        

        // Cyber Governor
        // **************

        tmpVipType = new VIPType("Cyber Governor", "CGov", cyber);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setOpenIncBonus(1);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        gw.addVipType(tmpVipType);
        tempFaction.setGovernorVIPType(tmpVipType);

        // starting units
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Cyber Light Bomber"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Cyber Light Bomber"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Cyber Light Bomber"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Cyber Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Cyber Troop Dropship"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Cyber Troop Light Carrier"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Cyber Mechs"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Cyber Mechs"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Cyber Mechs"));

        // starting buildings
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Medium Orbital Wharf"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Mech Factory"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Orbital Industries"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Small Planetary Shield"));

        // other faction abilities
        tempFaction.setCanReconstruct(true);
        tempFaction.setTechBonus(10);
        tempFaction.setNrStartingRandomVIPs(1);
        tempFaction.addStartingVIPType(cyberStart1);
        tempFaction.addStartingVIPType(cyberStart2);
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        Faction cyberFaction = tempFaction;

        // *********
        // * Ghost *
        // *********
        // XXX Ghost faction
        tempFaction = new Faction("Ghost", Faction.getColorHexString(122, 255, 122), ghost);
        GameWorldCreator.addFaction(tempFaction, gw);

        // Ghost Troop types
        // *****************

        tempFaction.addTroopType(gw.getTroopTypeByName("Militia"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Militia Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Scout Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Light Artillery"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Light Tanks"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Ghost Guerilla Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Ghost Mobile Infantry"));

        // Building Types
        // **************

        tempFaction.setBuildings(bGhost);

        // Ghost Spaceship Types
        // *********************

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Frigate"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Destroyer"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("PD Cruiser"));

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Small Merchant Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("VIP Transport"));

        typeName = "Ghost Fighter-Bomber";
        tempsst = new SpaceshipType(typeName, "G-FB", SpaceShipSize.SQUADRON, sqdBaseSh, sqdBaseDC + 5, SpaceshipRange.NONE, 1, 4, sqdBaseSmAtt, sqdBaseFightSqdAtt - 5);
        tempsst.setDescription("An all-round squadron which can both do some damage against squadrons and capital ships, where its two-shot medium and large torpedo salvoes can hurt larger ships. It cannot move on it's own but has to be attached to a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(25);
        tempsst.setWeaponsMaxSalvoesMedium(2);
        tempsst.setWeaponsStrengthLarge(25);
        tempsst.setWeaponsMaxSalvoesLarge(2);
        tempsst.setVisibleOnMap(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);
/*
        typeName = "Ghost Advanced Attacker";
        tempsst = new SpaceshipType(typeName,"G-AA",SpaceShipSize.SMALL,sqdBaseSh,sqdBaseDC + 5,SpaceshipRange.NONE,1,6,uSIC,sqdBaseSmAtt,sqdBaseFightSqdAtt);
		tempsst.setDescription("An all-round squadron which can both do some damage against squadrons and capital ships, and also strafe ground troops, where its two-shot medium and large torpedo salvoes can hurt larger ships. It cannot move on it's own but has to be attached to a carrier.");
        tempsst.setSquadron(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsMaxSalvoesMedium(3);
        tempsst.setCanPerformAirToGroundAttacks(true);
        tempsst.setWeaponsAirToGround(30);
        tempsst.setVisibleOnMap(false);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(tempsst);
*/
        // Ghost Scout
        typeName = "Ghost Advanced Scout";
        tempsst = new SpaceshipType(typeName, "GASc", SpaceShipSize.SMALL, 5, 10, SpaceshipRange.LONG, 1, 4, 5, 5);
        tempsst.setDescription("Small-sized long range military scout ship. Very small military ship useful for scouting missions and surveying planets and to carry VIPs. Also useful for commerce raiding. It is virtually useless in combat. Cannot besiege/block planets.");
        tempsst.setVisibleOnMap(false);
        tempsst.setCanBlockPlanet(false);
        tempsst.setPlanetarySurvey(true);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Ghost Frigate
        typeName = "Ghost Frigate";
        tempsst = new SpaceshipType(typeName, "GFr", SpaceShipSize.SMALL, 20, 60, SpaceshipRange.LONG, 3, 6, 20, 15);
        tempsst.setDescription("Small-sized long range military ship. Useful for blocking enemny planets, scouting missions, commerce raiding and to carry VIPs. It can be used in combat against small enemies and maybe support against medium enemy ships, but against larger ships it is virtually useless.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setVisibleOnMap(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Ghost Destroyer
        typeName = "Ghost Destroyer";
        tempsst = new SpaceshipType(typeName, "GDe", SpaceShipSize.MEDIUM, 50, 150, SpaceshipRange.LONG, 5, 15, 35, 10);
        tempsst.setDescription("Medium-sized long range military ship. It is most useful when attacking small or medium opponents or as support against less powerful large ships. It is too weak to do any significant damage against huge opponents.");
        tempsst.setArmor(40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(35);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        tempsst.setVisibleOnMap(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Ghost Troop Transport
        typeName = "Ghost Troop Transport";
        tempsst = new SpaceshipType(typeName, "GTT", SpaceShipSize.MEDIUM, 20, 120, SpaceshipRange.LONG, 3, 10, 10, 5);
        tempsst.setDescription("Medium-sized long range military troop transport ship. It can carry 3 troops and launch one troop each turn. It is too weak to do any significant damage against anything but small opponents.");
        tempsst.setArmor(20);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setTroopCapacity(3);
        tempsst.setScreened(true);
//        tempsst.setTroopLaunchCapacity(1);
        tempsst.setVisibleOnMap(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Ghost Cruiser
        typeName = "Ghost Cruiser";
        tempsst = new SpaceshipType(typeName, "GCr", SpaceShipSize.LARGE, 120, 400, SpaceshipRange.SHORT, 10, 30, 40, 10);
        tempsst.setDescription("Large-sized short-range capital ship. It is very good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It has a small marine detachment, bombardment and can carry two squadron.");
        tempsst.setArmor(60, 40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(130);
        tempsst.setWeaponsStrengthLarge(80);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setSquadronCapacity(2);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setVisibleOnMap(false);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Ghost Light Cruiser
        typeName = "Ghost Light Cruiser";
        tempsst = new SpaceshipType(typeName, "GLCr", SpaceShipSize.LARGE, 80, 300, SpaceshipRange.SHORT, 8, 25, 40, 10);
        tempsst.setDescription("Large-sized long-range capital ship. It is good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It has a small marine detachment, bombardment and can carry one squadron.");
        tempsst.setArmor(50, 30);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(100);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setWeaponsMaxSalvoesLarge(4);
        tempsst.setSquadronCapacity(1);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setVisibleOnMap(false);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Ghost Battleship
        typeName = "Ghost Battleship";
        tempsst = new SpaceshipType(typeName, "GBs", SpaceShipSize.HUGE, 300, 1000, SpaceshipRange.SHORT, 25, 80, 60, 30);
        tempsst.setDescription("Huge-sized short range capital ship. Have a powerful array of weapons that make massive damage to ships of all sizes, and have very powerful armor. It has planetary bombardment, a small marine detachment, can carry up to four squadrons and have an advanced tactical center to increase combat efficiency.");
        tempsst.setArmor(90, 70, 50);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(120);
        tempsst.setWeaponsStrengthLarge(200);
        tempsst.setWeaponsStrengthHuge(200);
        tempsst.setWeaponsMaxSalvoesHuge(4);
        tempsst.setSquadronCapacity(4);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(5);
        tempsst.setVisibleOnMap(false);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Research - Ghost
        // ****************

        
        aRA = null;

        createTTresearch(gw, tempFaction, "Militia Artillery", null);
        aRA = createTTresearch(gw, tempFaction, "Scout Infantry", null);
        aRA = createTTresearch(gw, tempFaction, "Light Artillery", aRA);
        aRA = createTTresearch(gw, tempFaction, "Light Tanks", aRA);
        aRA = createTTresearch(gw, tempFaction, "Ghost Guerilla Infantry", aRA);
        aRA = createTTresearch(gw, tempFaction, "Ghost Mobile Infantry", aRA);

        // large and huge ship sizes
        ResearchAdvantage gLargeShips = createLargeShipsResearch(tempFaction);
        ResearchAdvantage gHugeShips = createHugeShipsResearch(tempFaction, lLargeShips);
        ;

        // def ship
        createSTresearch(gw, tempFaction, "PD Cruiser", lLargeShips, "Large defence shiptype: ");

        // capital ships
        //TODO
        //createSTresearch(gw,tempFaction,"Ghost Advanced Attacker",null,"Squadron shiptype: ");
        ResearchAdvantage gcap = createSTresearch(gw, tempFaction, "Ghost Cruiser", gLargeShips);
        gcap = createSTresearch(gw, tempFaction, "Ghost Battleship", gcap);
        gcap.addParent(gHugeShips);
        gcap = createSTresearch(gw, tempFaction, "Ghost Light Cruiser", gcap);

        // buildings
        ResearchAdvantage gms = createBTresearch(buildings, tempFaction, "Ghost Medium Planetary Shield", null, "Upgrade planet shield building to protect against bomberdment of 2");
        createBTresearch(buildings, tempFaction, "Ghost Large Planetary Shield", gms, "Upgrade planet shield building to protect against bomberdment of 3");

        ResearchAdvantage spw = createBTresearch(buildings, tempFaction, "Small Planetary Wharf", null, "A spaceship wharf placed on the planet surface, it cannot be destrpyed by enemy besieging ships");
        createBTresearch(buildings, tempFaction, "Medium Planetary Wharf", spw, "Upgrade wharf to be able to build medium ships");

        ResearchAdvantage glow = createBTresearch(buildings, tempFaction, "Large Orbital Wharf", gLargeShips, "Can build one large ship or some smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ResearchAdvantage ghow = createBTresearch(buildings, tempFaction, "Huge Orbital Wharf", glow, "Can build one huge ship or several smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ghow.addParent(gHugeShips);

        ResearchAdvantage gs1 = createBTresearch(buildings, tempFaction, "Ghost Advanced Covert Ops School", null, "");
        ResearchAdvantage gs2 = createBTresearch(buildings, tempFaction, "Ghost Advanced Subversion University", gs1, "");
        ResearchAdvantage gs3 = createBTresearch(buildings, tempFaction, "Ghost Elite School", gs2, "");
        createBTresearch(buildings, tempFaction, "Mercenary Liason Office", gs3, "");

        ResearchAdvantage garm = createBTresearch(buildings, tempFaction, "Armor Training Base", null, "Enables the building of training bases which can procude armored troop units.");
        createBTresearch(buildings, tempFaction, "Artillery Training Base", garm, "Enables the building of training bases which can procude artillery and support troop units.");

        ResearchAdvantage dbr = createBTresearch(buildings, tempFaction, "Defensive Bunkers", null, "Enables the building of planet surface bunkers which increases a planets resistance.");
        createBTresearch(buildings, tempFaction, "Ghost Advanced Bunkers", dbr, "Enables the building of advanced and hidden planet surface bunkers which dramatically increases a planets resistance.");

        createSpaceportResearch(buildings, tempFaction, gLargeShips, gHugeShips);

        // Corruption Research
        createCResearch(tempFaction);

        // Tech research
        createTechResearch(tempFaction, null, 4);

        // Faction bonuses
        ResearchAdvantage gcb = createFactionTresearch(tempFaction, "Ghost closed income bonus 1", "Increase closed income on all planets to +2.", null, 0, 2, 0);
        gcb = createFactionTresearch(tempFaction, "Ghost closed income bonus 2", "Increase closed income on all planets with an additional +1.", gcb, 0, 1, 0);

        ResearchAdvantage grb = createFactionTresearch(tempFaction, "Ghost resistance bonus 1", "Increase resistance on all newly conquered planets to +3.", null, 0, 0, 3);
        grb = createFactionTresearch(tempFaction, "Ghost resistance bonus 2", "Increase resistance on all newly conquered planets with an additional +2.", grb, 0, 0, 2);
        createFactionTresearch(tempFaction, "Ghost resistance bonus 3", "Increase resistance on all newly conquered planets with an additional +2.", grb, 0, 0, 2);

        

        // Ghost Governor
        // **************

        tmpVipType = new VIPType("Ghost Governor", "GGov", ghost);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setDiplomat(true);
        tmpVipType.setSpying(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setClosedIncBonus(1);
        tmpVipType.setResistanceBonus(4);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        gw.addVipType(tmpVipType);
        tempFaction.setGovernorVIPType(tmpVipType);

        // starting units
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Ghost Advanced Scout"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Ghost Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Ghost Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Ghost Troop Transport"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("HF Destroyer"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Militia"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Infantry"));

        // starting buildings
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Medium Orbital Wharf"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Infantry Training Base"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Orbital Industries"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Small Planetary Shield"));

        // other faction abilities
        tempFaction.setCanReconstruct(true);
        tempFaction.setResistanceBonus(1);
        tempFaction.setClosedPlanetBonus(1);
        tempFaction.setNrStartingRandomVIPs(1);
        tempFaction.addStartingVIPType(ghostStart1);
        tempFaction.addStartingVIPType(ghostStart2);
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        Faction ghostFaction = tempFaction;

        // ***********
        // * Templar *
        // ***********
        // XXX Templar faction
        tempFaction = new Faction("Templar", Faction.getColorHexString(255, 100, 100), templar);
        GameWorldCreator.addFaction(tempFaction, gw);

        // Troop types
        // ***********

        tempFaction.addTroopType(gw.getTroopTypeByName("Templar Drone Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Templar Drone Rocket Infantry"));
        //TODO
        //tempFaction.addTroopType(gw.getTroopTypeByName("Templar Drone Missile Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Templar Drone Heavy Infantry"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Templar Light Armor"));
        tempFaction.addTroopType(gw.getTroopTypeByName("Templar Heavy Armor"));

        // Building Types
        // **************

        tempFaction.setBuildings(bTemplar);

        // Ship types
        // **********

        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("Small Merchant Freighter"));
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName("VIP Transport"));

        // Templar Drone Fighter
        typeName = "Templar Drone Fighter";
        tempsst = new SpaceshipType(typeName, "T-F", SpaceShipSize.SQUADRON, sqdBaseSh + 5, sqdBaseDC - 5, SpaceshipRange.NONE, 1, 2, sqdBaseSmAtt - 5, sqdBaseFightSqdAtt - 5);
        tempsst.setDescription("A fighter squadron specialized in attacking enemy squadrons. It can do very little damage to enemy capital ships. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Templar Drone Bomber
        typeName = "Templar Drone Bomber";
        tempsst = new SpaceshipType(typeName, "T-B", SpaceShipSize.SQUADRON, sqdBaseSh + 5, sqdBaseDC - 5, SpaceshipRange.NONE, 1, 3, sqdBaseSmAtt - 5, sqdBaseBombSqdAtt - 5);
        tempsst.setDescription("Squadron specialized in attacking capital ships, especially ships larger than small where its medium and large torpedo salvoes can can do significant damage. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(30);
        tempsst.setWeaponsMaxSalvoesMedium(2);
        tempsst.setWeaponsStrengthLarge(30);
        tempsst.setWeaponsMaxSalvoesLarge(2);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));
/*
        // Templar Drone Ground attack
        typeName = "Templar Drone Ground Attack";
        tempsst = new SpaceshipType(typeName,"T-GA",SpaceShipSize.SMALL,sqdBaseSh+5,sqdBaseDC-5,SpaceshipRange.NONE,1,3,uSIC,sqdBaseSmAtt-5,sqdBaseBombSqdAtt-5);
		tempsst.setDescription("Air-to-ground attack squadron which can attack enemt troops on planet surface. Virtually useless in combat and should probably be screened. It cannot move on it's own but has to be carried inside a carrier.");
        tempsst.setSquadron(true);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIAIR);
        tempsst.setCanPerformAirToGroundAttacks(true);
        tempsst.setWeaponsAirToGround(sqdBaseAirToGroundAtt-5);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));
      */
        // Templar Light Frigate
        typeName = "Templar Light Frigate";
        tempsst = new SpaceshipType(typeName, "TLF", SpaceShipSize.SMALL, 20, 20, SpaceshipRange.LONG, 2, 4, 15, 10);
        tempsst.setDescription("Small-sized long range military scout ship. Very small military ship useful for scouting missions and to carry VIPs. Also useful for commerce raiding. It is virtually useless in combat. Cannot besiege/block planets.");
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Templar Troop Transport
        typeName = "Templar Troop Transport";
        tempsst = new SpaceshipType(typeName, "TTT", SpaceShipSize.SMALL, 20, 40, SpaceshipRange.LONG, 2, 5, 5, 5);
        tempsst.setDescription("Small-sized long range military troop ship. This troop transport can single-handedly besiege enemy planets, and can run away from enemy ships if ordered.");
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        //    tempsst.setTroopLaunchCapacity(1);
        tempsst.setTroopCapacity(2);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Templar Destroyer
        typeName = "Templar Destroyer";
        tempsst = new SpaceshipType(typeName, "TDe", SpaceShipSize.MEDIUM, 50, 100, SpaceshipRange.LONG, 5, 12, 25, 10);
        tempsst.setDescription("Medium-sized long range military ship. It is most useful when attacking small or medium opponents or as support against less powerful large ships. It is too weak to do any significant damage against huge opponents. It carries a small marine detachment which lowers the resistance of besieged planets.");
        tempsst.setArmor(30);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(25);
        tempsst.setWeaponsMaxSalvoesMedium(4);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Templar Carrier
        typeName = "Templar Carrier";
        tempsst = new SpaceshipType(typeName, "Cru", SpaceShipSize.LARGE, 100, 250, SpaceshipRange.SHORT, 8, 25, 10, 10);
        tempsst.setDescription("Large-sized short-range capital carrier ship. It can carry a large number of squadrons. It is too weak to to do very much damage to nay opponents except small ones.");
        tempsst.setArmor(40);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setSquadronCapacity(12);
        tempsst.setAvailableToBuild(false);
        tempsst.setScreened(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Templar Light Cruiser
        typeName = "Templar Light Cruiser";
        tempsst = new SpaceshipType(typeName, "TLC", SpaceShipSize.LARGE, 200, 200, SpaceshipRange.LONG, 8, 25, 30, 15);
        tempsst.setDescription("Large-sized long-range capital ship. It is very good against small or medium opponents and pretty good against large ones. It is too weak to to do very much damage to huge opponents. It has a small marine detachment, bombardment and can carry one squadron.");
        tempsst.setArmor(40, 20);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(80);
        tempsst.setBombardment(1);
        tempsst.setPsychWarfare(1);
        tempsst.setVisibleOnMap(false);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Templar Battleship
        typeName = "Templar Battleship";
        tempsst = new SpaceshipType(typeName, "TBs", SpaceShipSize.HUGE, 500, 500, SpaceshipRange.SHORT, 20, 60, 40, 20);
        tempsst.setDescription("Huge-sized short range capital ship. Have a powerful array of weapons that make massive damage to ships of all sizes, and have very powerful armor. It has planetary bombardment, a small marine detachment, can carry up to four squadrons and have an advanced tactical center to increase combat efficiency.");
        tempsst.setArmor(80, 60, 40);
        tempsst.setTargetingType(SpaceshipTargetingType.ANTIMBU);
        tempsst.setWeaponsStrengthMedium(100);
        tempsst.setWeaponsStrengthLarge(150);
        tempsst.setWeaponsStrengthHuge(100);
        tempsst.setSquadronCapacity(3);
        tempsst.setBombardment(3);
        tempsst.setPsychWarfare(1);
        tempsst.setIncreaseInitiative(10);
        tempsst.setNoRetreat(true);
        tempsst.setAvailableToBuild(false);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Planetary defence destroyer
        typeName = "Templar PD Destroyer";
        tempsst = new SpaceshipType(typeName, "TPDD", SpaceShipSize.MEDIUM, 70, 70, SpaceshipRange.NONE, 1, 10, 20, 15);
        tempsst.setDescription("Medium planetary defence (PB) ship. It can't move outside the starsystem it is built in. It is about as powerful as a normal Destroyer, but cheaper.");
        tempsst.setArmor(30);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(20);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setNoRetreat(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Home fleet destroyer
        typeName = "Templar HF Destroyer";
        tempsst = new SpaceshipType(typeName, "THFD", SpaceShipSize.MEDIUM, 70, 70, SpaceshipRange.NONE, 0, 10, 20, 15);
        tempsst.setDescription("Medium planetary defence (PB) ship. The home fleet destroyer is identical to the PD destroyer, except that it is financed by the industrial base on faction home planets. It can't move outside the starsystem it is built in. It is about as powerful as a normal Destroyer, but cheaper.");
        tempsst.setArmor(30);
        tempsst.setTargetingType(SpaceshipTargetingType.ALLROUND);
        tempsst.setWeaponsStrengthMedium(20);
        tempsst.setWeaponsStrengthLarge(50);
        tempsst.setAvailableToBuild(false);
        tempsst.setNoRetreat(true);
        gw.addShipType(tempsst);
        tempFaction.addSpaceshipType(gw.getSpaceshipTypeByName(typeName));

        // Research - Templar
        // ******************

        
        aRA = null;

        aRA = createTTresearch(gw, tempFaction, "Templar Drone Rocket Infantry", aRA);
        //TODO
        //aRA = createTTresearch(gw,tempFaction,"Templar Drone Missile Infantry",aRA);
        aRA = createTTresearch(gw, tempFaction, "Templar Drone Heavy Infantry", aRA);
        aRA = createTTresearch(gw, tempFaction, "Templar Light Armor", aRA);
        aRA = createTTresearch(gw, tempFaction, "Templar Heavy Armor", aRA);

        // large and huge ship sizes
        ResearchAdvantage tLargeShips = createLargeShipsResearch(tempFaction);
        ResearchAdvantage tHugeShips = createHugeShipsResearch(tempFaction, tLargeShips);
        ;

        // capital ships
        createSTresearch(gw, tempFaction, "Templar Drone Bomber", null, "Squadron shiptype: ");
        //TODO
        //createSTresearch(gw,tempFaction,"Templar Drone Ground Attack",tsqd,"Squadron shiptype: ");
        ResearchAdvantage tcap = createSTresearch(gw, tempFaction, "Templar Carrier", tLargeShips);
        tcap = createSTresearch(gw, tempFaction, "Templar Light Cruiser", tLargeShips);
        tcap = createSTresearch(gw, tempFaction, "Templar Battleship", tcap);
        tcap.addParent(tHugeShips);

        // Buildings
        createBTresearch(buildings, tempFaction, "Medium Planetary Shield", null, "Upgrade planet shield building to protect against bomberdment of 2");

        ResearchAdvantage tlow = createBTresearch(buildings, tempFaction, "Large Orbital Wharf", tLargeShips, "Can build one large ship or some smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        ResearchAdvantage thow = createBTresearch(buildings, tempFaction, "Huge Orbital Wharf", tlow, "Can build one huge ship or several smaller ships every turn. Is vulnerable to enemy ships since it is in orbit around the planet.");
        thow.addParent(tHugeShips);

        ResearchAdvantage ts1 = createBTresearch(buildings, tempFaction, "Templar of Reaping and Pathfinding", null, "");
        ResearchAdvantage ts2 = createBTresearch(buildings, tempFaction, "Temple of Harvest and Mind", ts1, "");
        ResearchAdvantage ts3 = createBTresearch(buildings, tempFaction, "Templar Fortress of Power", ts2, "");
        createBTresearch(buildings, tempFaction, "Mercenary Liason Office", ts3, "");

        createBTresearch(buildings, tempFaction, "Armor Training Base", null, "Enables the building of training bases which can procude armored troop units.");

        createBTresearch(buildings, tempFaction, "Defensive Bunkers", null, "Enables the building of planet surface bunkers which increases a planets resistance.");

        createSpaceportResearch(buildings, tempFaction, tLargeShips, tHugeShips);

        // Corruption Research
        createCResearch(tempFaction);

        // Tech research
        ResearchAdvantage ttr = createTechResearch(tempFaction, null, 2);
        createTechResearch(tempFaction, ttr, 4);

        // Faction bonuses
        createFactionTresearch(tempFaction, "Templar closed income bonus", "Increase closed income on all planets to +1.", null, 0, 1, 0);

        

        // Templar Governor
        // ****************

        tmpVipType = new VIPType("Templar Governor", "TGov", templar);
        tmpVipType.setCanVisitNeutralPlanets(true);
        tmpVipType.setInfestate(true);
        tmpVipType.setGovernor(true);
        tmpVipType.setWellGuarded(true);
        tmpVipType.setClosedIncBonus(1);
        tmpVipType.setResistanceBonus(3);
        tmpVipType.setFrequency(BlackMarketFrequency.NEVER);
        gw.addVipType(tmpVipType);
        tempFaction.setGovernorVIPType(tmpVipType);

        // starting units
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar Light Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar Light Frigate"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar Troop Transport"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar Troop Transport"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar HF Destroyer"));
        tempFaction.addStartingShipType(gw.getSpaceshipTypeByName("Templar HF Destroyer"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Templar Drone Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Templar Drone Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Templar Drone Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Templar Drone Infantry"));
        tempFaction.addStartingTroop(gw.getTroopTypeByName("Templar Drone Infantry"));

        // starting buildings
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Medium Orbital Wharf"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Infantry Training Base"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Orbital Industries"));
        tempFaction.addStartingBuildings(gw.getBuildingTypeByName("Small Planetary Shield"));

        // other faction abilities
        tempFaction.setResistanceBonus(1);
        tempFaction.setNrStartingRandomVIPs(1);
        tempFaction.addStartingVIPType(templarStart1);
        tempFaction.addStartingVIPType(templarStart2);
        tempFaction.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
        tempFaction.setAlien(true);
        Faction templarFaction = tempFaction;

        // ********************
        // add custom diplomacy
        // ********************
        // XXX Diplomacy

        GameWorldDiplomacy diplomacy = gw.getDiplomacy();
        DiplomacyRelation tempDiplomacyRelation;

        // Orb-Orb relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(orbFaction, orbFaction, gw);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Orb-Lancer relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(orbFaction, lancerFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Orb-Cyber relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(orbFaction, cyberFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.ALLIANCE);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Orb-Ghost relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(orbFaction, ghostFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.ETERNAL_WAR);

        // Orb-Templar relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(templarFaction, lancerFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.ETERNAL_WAR);

        // Lancer-Lancer relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(lancerFaction, lancerFaction, gw);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Lancer-Cyber relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(cyberFaction, lancerFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Lancer-Ghost relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(ghostFaction, lancerFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.ETERNAL_WAR);

        // Lancer-Templar relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(templarFaction, lancerFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CEASE_FIRE);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.ETERNAL_WAR);

        // Cyber-Cyber relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(cyberFaction, cyberFaction, gw);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Cyber-Ghost relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(ghostFaction, cyberFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.ALLIANCE);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Cyber-Templar relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(templarFaction, cyberFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.WAR);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.ETERNAL_WAR);

        // Ghost-Ghost relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(ghostFaction, ghostFaction, gw);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        // Ghost-Templar relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(ghostFaction, templarFaction, gw);
        tempDiplomacyRelation.setHighestRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CEASE_FIRE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.ETERNAL_WAR);

        // Templar-Templar relation
        tempDiplomacyRelation = GameWorldCreator.getRelation(templarFaction, templarFaction, gw);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.CONFEDERACY);
        tempDiplomacyRelation.setStartRelation(DiplomacyLevel.PEACE);
        tempDiplomacyRelation.setLowestRelation(DiplomacyLevel.WAR);

        return gw;
    }

    private static ResearchAdvantage createTTresearch(GameWorld gw, Faction faction, String aTTName, ResearchAdvantage parent) {
        ResearchAdvantage tempFactionAdvantage = new ResearchAdvantage(aTTName, "Troop type: " + aTTName);
        tempFactionAdvantage.setTimeToResearch(2);
        tempFactionAdvantage.addTroopType(gw.getTroopTypeByName(aTTName));
        if (parent != null) {
            tempFactionAdvantage.addParent(parent);
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        } else {
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        }
        return tempFactionAdvantage;
    }

    private static ResearchAdvantage createSTresearch(GameWorld gw, Faction faction, String aSTName, ResearchAdvantage parent) {
        return createSTresearch(gw, faction, aSTName, parent, "Ship type: ");
    }

    private static ResearchAdvantage createSTresearch(GameWorld gw, Faction faction, String aSTName, ResearchAdvantage parent, String textPrefix) {
        ResearchAdvantage tempFactionAdvantage = new ResearchAdvantage(aSTName, textPrefix + aSTName);
        tempFactionAdvantage.setTimeToResearch(2);
        tempFactionAdvantage.addShip(gw.getSpaceshipTypeByName(aSTName));
        if (parent != null) {
            tempFactionAdvantage.addParent(parent);
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        } else {
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        }
        return tempFactionAdvantage;
    }

    private static ResearchAdvantage createLargeShipsResearch(Faction faction) {
        ResearchAdvantage tempFactionAdvantage = new ResearchAdvantage("Large ships", "Allows building of large ships");
        tempFactionAdvantage.setTimeToResearch(2);
        faction.getResearchAdvantages().add(tempFactionAdvantage);
        return tempFactionAdvantage;
    }

    private static ResearchAdvantage createHugeShipsResearch(Faction faction, ResearchAdvantage lLargeShips) {
        ResearchAdvantage tempFactionAdvantage = new ResearchAdvantage("Huge ships", "Allows building of huge ships");
        tempFactionAdvantage.setTimeToResearch(2);
        faction.getResearchAdvantages().add(tempFactionAdvantage);
        tempFactionAdvantage.addParent(lLargeShips);
        return tempFactionAdvantage;
    }

    private static ResearchAdvantage createBTresearch(List<BuildingType> buildings, Faction faction, String aBTName, ResearchAdvantage parent, String textSuffix) {
        ResearchAdvantage tempFactionAdvantage = new ResearchAdvantage(aBTName, "Building type: " + textSuffix);
        tempFactionAdvantage.setTimeToResearch(2);
        tempFactionAdvantage.addBuildingType(getBuildingTypeByName(buildings, aBTName));
        if (parent != null) {
            tempFactionAdvantage.addParent(parent);
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        } else {
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        }
        return tempFactionAdvantage;
    }

    private static void createCResearch(Faction faction) {
        ResearchAdvantage tempFactionAdvantage = null;
        ResearchAdvantage parent = null;
        for (int i = 1; i <= 7; i++) {
            tempFactionAdvantage = new ResearchAdvantage("Effective Government " + i, "Lowers corruption.");
            tempFactionAdvantage.setTimeToResearch(4);
            Corruption tmpCorruption = createCorruption(7 - i);
            tempFactionAdvantage.setCorruptionPoint(tmpCorruption.getCorruptionPoint());
            if (parent == null) {
                faction.getResearchAdvantages().add(tempFactionAdvantage);
            } else {
                tempFactionAdvantage.addParent(parent);
                faction.getResearchAdvantages().add(tempFactionAdvantage);
            }
            parent = tempFactionAdvantage;
        }
    }

    private static void addBuildingToFactions(BuildingType aBuildingType, List<BuildingType>... buildings) {
        for (List<BuildingType> aBuildings : buildings) {
            aBuildings.add(aBuildingType);
        }
    }

    /**
     * If 7 if recieved as multiplier, max corrution will be 7*3*4=84%
     *
     * @param multiplier
     * @return
     */
    private static Corruption createCorruption(int multiplier) {
        Corruption tmpCorruption = new Corruption();
        int step = 3;
        if (multiplier > 0) {
            tmpCorruption.addBreakpoint(50, multiplier * step * 1);
            tmpCorruption.addBreakpoint(100, multiplier * step * 2);
            tmpCorruption.addBreakpoint(150, multiplier * step * 3);
            tmpCorruption.addBreakpoint(200, multiplier * step * 4);
        } else {
            tmpCorruption.addBreakpoint(50, 0);
        }
        return tmpCorruption;
    }

    private static ResearchAdvantage createTechResearch(Faction faction, ResearchAdvantage rootParent, int turns) {
        ResearchAdvantage tempFactionAdvantage = null;
        ResearchAdvantage parent = rootParent;
        for (int i = 1; i <= 5; i++) {
            tempFactionAdvantage = new ResearchAdvantage("Advanced technology " + i, "More advanced technology.");
            tempFactionAdvantage.setTimeToResearch(turns);
            int techBonus = 10 * i;
            if (rootParent != null) {
                techBonus += 50;
            }
            tempFactionAdvantage.setTechBonus(techBonus);
            if (parent == null) {
                faction.getResearchAdvantages().add(tempFactionAdvantage);
            } else {
                tempFactionAdvantage.addParent(parent);
                faction.getResearchAdvantages().add(tempFactionAdvantage);
            }
            parent = tempFactionAdvantage;
        }
        return tempFactionAdvantage;
    }

    private static ResearchAdvantage createFactionTresearch(Faction faction, String rName, String rDesc, ResearchAdvantage parent, int openBonus, int closedBonus, int resBonus) {
        ResearchAdvantage tempFactionAdvantage = null;
        tempFactionAdvantage = new ResearchAdvantage(rName, rDesc);
        tempFactionAdvantage.setTimeToResearch(2);
        if (openBonus > 0) {
            tempFactionAdvantage.setOpenPlanetBonus(openBonus);
        }
        if (closedBonus > 0) {
            tempFactionAdvantage.setClosedPlanetBonus(openBonus);
        }
        if (resBonus > 0) {
            tempFactionAdvantage.setResistanceBonus(resBonus);
        }
        if (parent == null) {
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        } else {
            tempFactionAdvantage.addParent(parent);
            faction.getResearchAdvantages().add(tempFactionAdvantage);
        }
        return tempFactionAdvantage;
    }

    private static void createVIPBuildings(GameWorld gw, List<BuildingType> factionBuildings, List<BuildingType> buildings, UniqueIdCounter uBIC, String[] longNames, String[] shortNames, String[] vipNames) {
        BuildingType tmpBuildingType = new BuildingType(longNames[0], shortNames[0], 20);
        tmpBuildingType.setDescription("Can train " + vipNames[0] + " VIPs.");
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[0]));
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        buildings.add(tmpBuildingType);
        factionBuildings.add(tmpBuildingType);

        tmpBuildingType = new BuildingType(longNames[1], shortNames[1], 50);
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[0]));
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[1]));
        tmpBuildingType.setDescription("Can train " + vipNames[0] + " and " + vipNames[1] + " VIPs.");
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setParentBuildingTypeName(longNames[0]);
        buildings.add(tmpBuildingType);
        factionBuildings.add(tmpBuildingType);

        tmpBuildingType = new BuildingType(longNames[2], shortNames[2], 20);
        tmpBuildingType.setDescription("Can train " + vipNames[2] + " VIPs.");
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[2]));
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        buildings.add(tmpBuildingType);
        factionBuildings.add(tmpBuildingType);

        tmpBuildingType = new BuildingType(longNames[3], shortNames[3], 50);
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[2]));
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[3]));
        tmpBuildingType.setDescription("Can train " + vipNames[2] + " and " + vipNames[3] + " VIPs.");
        tmpBuildingType.setPlanetUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tmpBuildingType.setDeveloped(false);
        tmpBuildingType.setParentBuildingTypeName(longNames[2]);
        buildings.add(tmpBuildingType);
        factionBuildings.add(tmpBuildingType);

        tmpBuildingType = new BuildingType(longNames[4], shortNames[4], 100);
        tmpBuildingType.addBuildVIPType(gw.getVIPTypeByName(vipNames[4]));
        tmpBuildingType.setDescription("Can train " + vipNames[4] + " VIPs.");
        tmpBuildingType.setPlayerUnique(true);
        tmpBuildingType.setAutoDestructWhenConquered(true);
        tmpBuildingType.setDeveloped(false);
        buildings.add(tmpBuildingType);
        factionBuildings.add(tmpBuildingType);
    }

    private static void createSpaceportResearch(List<BuildingType> buildings, Faction tempFaction, ResearchAdvantage largeShips, ResearchAdvantage hugeShips) {
        ResearchAdvantage s2 = createBTresearch(buildings, tempFaction, "Spaceport Class 2", null, "Enables the building of Spaceport Class 2");
        ResearchAdvantage s3 = createBTresearch(buildings, tempFaction, "Spaceport Class 3", s2, "Enables the building of Spaceport Class 3");
        s3.addParent(largeShips);
        ResearchAdvantage s4 = createBTresearch(buildings, tempFaction, "Spaceport Class 4", s3, "Enables the building of Spaceport Class 4");
        ResearchAdvantage s5 = createBTresearch(buildings, tempFaction, "Spaceport Class 5", s4, "Enables the building of Spaceport Class 5");
        s5.addParent(hugeShips);
    }

    private static BuildingType getBuildingTypeByName(List<BuildingType> buildings, String name) {
        BuildingType buildingType = buildings.stream().filter(buildingType1 -> buildingType1.getName().equals(name)).findFirst().orElse(null);
        if (buildingType != null) {
        } else { // om detta intr�ffar s� finns det antagligen en felstavning av en skeppstyp i gameworlden
            Logger.severe("Titanium.getBuildingTypeByName, btname:" + name + " -> " + buildingType);
            Thread.dumpStack();
        }
        return buildingType;
    }

}
