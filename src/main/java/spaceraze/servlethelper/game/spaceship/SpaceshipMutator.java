package spaceraze.servlethelper.game.spaceship;

import spaceraze.game.*;
import spaceraze.map.GalaxyMap;
import spaceraze.servlethelper.game.planet.PlanetPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.SpaceShipSize;

import java.util.Iterator;

public class SpaceshipMutator {

    private SpaceshipMutator(){}

    public static Spaceship createSpaceShip(Player player, SpaceshipType type, int vipTechBonus, int factionTechBonus, int buildingBonus){
        PlayerSpaceshipImprovement playerSpaceshipImprovement = PlayerPureFunctions.findSpaceshipImprovement(type.getUuid(), player);
        SpaceshipType spaceshipType = playerSpaceshipImprovement != null ? createSpaceshipTypeWithImprovements(type, playerSpaceshipImprovement) : type;
        int nrProduced = playerSpaceshipImprovement != null ? playerSpaceshipImprovement.updateNrProduced() : 0;

         return new Spaceship(spaceshipType, null, nrProduced, vipTechBonus, factionTechBonus, buildingBonus);
    }

    public static SpaceshipType createSpaceshipTypeWithImprovements(SpaceshipType originSpaceshipType, PlayerSpaceshipImprovement playerSpaceshipImprovement){
        SpaceshipType newType = new SpaceshipType();
        newType.setUuid(originSpaceshipType.getUuid());
        newType.setName(originSpaceshipType.getName());
        newType.setShortName(originSpaceshipType.getShortName());
        newType.setSize(originSpaceshipType.getSize());
        newType.setSpaceshipRange(playerSpaceshipImprovement.getRange() != null ? playerSpaceshipImprovement.getRange() : originSpaceshipType.getRange());
        newType.setShields(originSpaceshipType.getShields() + playerSpaceshipImprovement.getShields());
        newType.setUpkeep(originSpaceshipType.getUpkeep() + playerSpaceshipImprovement.getUpkeep());
        newType.setBuildCost(originSpaceshipType.getBuildCost() + playerSpaceshipImprovement.getBuildCost());
        newType.setBombardment(originSpaceshipType.getBombardment() + playerSpaceshipImprovement.getBombardment());
        newType.setNoRetreat(playerSpaceshipImprovement.isNoRetreat());

        //Why can't we research hitpoints?
        newType.setHits(originSpaceshipType.getHits());

        newType.setInitSupport(originSpaceshipType.isInitSupport());
        newType.setIncreaseInitiative(originSpaceshipType.getIncreaseInitiative() + playerSpaceshipImprovement.getIncreaseInitiative());
        newType.setInitDefence(originSpaceshipType.getInitDefence() + playerSpaceshipImprovement.getInitDefence());
        newType.setWeaponsStrengthSquadron(originSpaceshipType.getWeaponsStrengthSquadron() + playerSpaceshipImprovement.getWeaponsStrengthSquadron());
        newType.setWeaponsStrengthSmall(originSpaceshipType.getWeaponsStrengthSmall() + playerSpaceshipImprovement.getWeaponsStrengthSmall());
        newType.setWeaponsStrengthMedium(originSpaceshipType.getWeaponsStrengthMedium() + playerSpaceshipImprovement.getWeaponsStrengthMedium());
        newType.setWeaponsStrengthLarge(originSpaceshipType.getWeaponsStrengthLarge() + playerSpaceshipImprovement.getWeaponsStrengthLarge());
        newType.setWeaponsStrengthHuge(originSpaceshipType.getWeaponsStrengthHuge() + playerSpaceshipImprovement.getWeaponsStrengthHuge());
        newType.setWeaponsMaxSalvosMedium(originSpaceshipType.getWeaponsMaxSalvosMedium() + playerSpaceshipImprovement.getWeaponsMaxSalvosMedium());
        newType.setWeaponsMaxSalvosLarge(originSpaceshipType.getWeaponsMaxSalvosLarge() + playerSpaceshipImprovement.getWeaponsMaxSalvosLarge());
        newType.setWeaponsMaxSalvosHuge(originSpaceshipType.getWeaponsMaxSalvosHuge() + playerSpaceshipImprovement.getWeaponsMaxSalvosHuge());
        newType.setSupply(playerSpaceshipImprovement.getSupply()  != null ? playerSpaceshipImprovement.getSupply() : originSpaceshipType.getSupply());
        newType.setArmorSmall(originSpaceshipType.getArmorSmall() + playerSpaceshipImprovement.getArmorSmall());
        newType.setArmorMedium(originSpaceshipType.getArmorMedium() + playerSpaceshipImprovement.getArmorMedium());
        newType.setArmorLarge(originSpaceshipType.getArmorLarge() + playerSpaceshipImprovement.getArmorLarge());
        newType.setArmorHuge(originSpaceshipType.getArmorHuge() + playerSpaceshipImprovement.getArmorHuge());
        newType.setPlanetarySurvey(playerSpaceshipImprovement.isChangePlanetarySurvey() ? playerSpaceshipImprovement.isPlanetarySurvey() : originSpaceshipType.isPlanetarySurvey());
//        newType.siegeBonus = oldsst.getSiegeBonus();
//        newType.troops = oldsst.getTroops();
        newType.setPsychWarfare(originSpaceshipType.getPsychWarfare() + playerSpaceshipImprovement.getPsychWarfare());
        newType.setTargetingType(originSpaceshipType.getTargetingType());
        newType.setSquadronCapacity(originSpaceshipType.getSquadronCapacity() + playerSpaceshipImprovement.getSquadronCapacity());
        newType.setDescription(playerSpaceshipImprovement.getDescription() != null ? playerSpaceshipImprovement.getDescription() : originSpaceshipType.getDescription());
        newType.setHistory(playerSpaceshipImprovement.getHistory() != null ? playerSpaceshipImprovement.getHistory() : originSpaceshipType.getHistory());
        newType.setIncEnemyClosedBonus(originSpaceshipType.getIncEnemyClosedBonus() + playerSpaceshipImprovement.getIncEnemyClosedBonus());
        newType.setIncEnemyOpenBonus(originSpaceshipType.getIncEnemyOpenBonus() + playerSpaceshipImprovement.getIncEnemyOpenBonus());
        newType.setIncFriendlyClosedBonus(originSpaceshipType.getIncFriendlyClosedBonus() + playerSpaceshipImprovement.getIncFriendlyClosedBonus());
        newType.setIncFriendlyOpenBonus(originSpaceshipType.getIncFriendlyOpenBonus() + playerSpaceshipImprovement.getIncFriendlyOpenBonus());
        newType.setIncNeutralClosedBonus(originSpaceshipType.getIncNeutralClosedBonus() + playerSpaceshipImprovement.getIncNeutralClosedBonus());
        newType.setIncNeutralOpenBonus(originSpaceshipType.getIncNeutralOpenBonus() + playerSpaceshipImprovement.getIncNeutralOpenBonus());
        newType.setIncOwnClosedBonus(originSpaceshipType.getIncOwnClosedBonus() + playerSpaceshipImprovement.getIncOwnClosedBonus());
        newType.setIncOwnOpenBonus(originSpaceshipType.getIncOwnOpenBonus() + playerSpaceshipImprovement.getIncOwnOpenBonus());
        newType.setCanAttackScreenedShips(playerSpaceshipImprovement.isChangeCanAttackScreenedShips() ? playerSpaceshipImprovement.isCanAttackScreenedShips() : originSpaceshipType.isCanAttackScreenedShips());
        newType.setCivilian(originSpaceshipType.isCivilian());
        newType.setLookAsCivilian(playerSpaceshipImprovement.isChangeLookAsCivilian() ? playerSpaceshipImprovement.isLookAsCivilian() : originSpaceshipType.isLookAsCivilian());
        newType.setCanBlockPlanet(playerSpaceshipImprovement.isChangeCanBlockPlanet() ? playerSpaceshipImprovement.isCanBlockPlanet() : originSpaceshipType.isCanBlockPlanet());
        newType.setVisibleOnMap(playerSpaceshipImprovement.isChangeVisibleOnMap() ? playerSpaceshipImprovement.isVisibleOnMap() : originSpaceshipType.isVisibleOnMap());
        newType.setAvailableToBuild(playerSpaceshipImprovement.isAvailableToBuild());
        newType.setTroopCapacity(originSpaceshipType.getTroopCapacity() + playerSpaceshipImprovement.getTroopCarrier());
        newType.setWorldUnique(originSpaceshipType.isWorldUnique());
        newType.setFactionUnique(originSpaceshipType.isFactionUnique());
        newType.setPlayerUnique(originSpaceshipType.isPlayerUnique());
        newType.setAlwaysRetreat(originSpaceshipType.isAlwaysRetreat());
        newType.setScreened(originSpaceshipType.isScreened());

        newType.setAdvantages(originSpaceshipType.getAdvantages());
        newType.setDisadvantages(originSpaceshipType.getDisadvantages());
        newType.setCanAppearOnBlackMarket(originSpaceshipType.isCanAppearOnBlackMarket());
        newType.setBlackMarketFrequency(originSpaceshipType.getBlackMarketFrequency());
        newType.setBlackmarketFirstTurn(originSpaceshipType.getBlackmarketFirstTurn());
        newType.setBluePrintFirstTurn(originSpaceshipType.getBluePrintFirstTurn());
        newType.setBluePrintFrequency(originSpaceshipType.getBluePrintFrequency());

        return newType;
    }

    public static Spaceship createSpaceShip(SpaceshipType type){

        return new Spaceship(type, null, 0, 0,0,0);
    }

    public static int getMediumSalvo(Spaceship spaceship, GameWorld gameWorld, boolean fireASalvo) {
        int salvoeStrength = 0;
        if (spaceship.getWeaponsStrengthMedium() > 0) {
            if (spaceship.getWeaponsSalvoesMedium() > 0) {
                salvoeStrength =  SpaceshipPureFunctions.getWeaponsStrengthMedium(spaceship, gameWorld);
                if (fireASalvo) {
                    spaceship.setWeaponsSalvoesMedium(spaceship.getWeaponsSalvoesMedium() -1);
                }
            }
        }
        return salvoeStrength;
    }

    public static int getLargeSalvo(Spaceship spaceship, GameWorld gameWorld, boolean fireASalvo) {
        int salvoeStrength = 0;
        if (spaceship.getWeaponsStrengthLarge() > 0) {
            if (spaceship.getWeaponsSalvoesLarge() > 0) {
                salvoeStrength =  SpaceshipPureFunctions.getWeaponsStrengthLarge(spaceship, gameWorld);
                if (fireASalvo) {
                    spaceship.setWeaponsSalvoesLarge(spaceship.getWeaponsSalvoesLarge() -1);
                }
            }
        }
        return salvoeStrength;
    }

    public static int getHugeSalvo(Spaceship spaceship, GameWorld gameWorld, boolean fireASalvo) {
        int salvoeStrength = 0;
        if (spaceship.getWeaponsStrengthHuge() > 0) {
            if (spaceship.getWeaponsSalvoesHuge() > 0) {
                salvoeStrength = SpaceshipPureFunctions.getWeaponsStrengthHuge(spaceship, gameWorld);
                if (fireASalvo) {
                    spaceship.setWeaponsSalvoesHuge(spaceship.getWeaponsSalvoesHuge() -1);
                }
            }
        }
        return salvoeStrength;
    }

    public static int getActualDamage(Spaceship spaceship, GameWorld gameWorld, Spaceship targetShip, int multiplier, double shieldsMultiplier) {
        double tmpDamage = 0;
        if (targetShip.getSize() == SpaceShipSize.SQUADRON) {
            tmpDamage = SpaceshipPureFunctions.getWeaponsStrengthSquadron(spaceship, gameWorld) * (1.0 - targetShip.getArmorSmall());
        } else {
            tmpDamage = SpaceshipPureFunctions.getWeaponsStrengthSmall(spaceship, gameWorld) * (1.0 - targetShip.getArmorSmall());
            SpaceShipSize spaceShipSize = SpaceshipPureFunctions.getSpaceshipTypeByUuid(targetShip.getTypeUuid(), gameWorld).getSize();
            //TODO 2020-12-20 Check this out, the should not the damage be all sizes <= the target size? As it work now only the small + huge damage will be used against a huge target.
            if (spaceShipSize == SpaceShipSize.MEDIUM) {
                tmpDamage = tmpDamage + getMediumSalvo(spaceship, gameWorld, true) * (1.0 - targetShip.getArmorMedium());
            }else if (spaceShipSize == SpaceShipSize.LARGE) {
                tmpDamage = tmpDamage + getLargeSalvo(spaceship, gameWorld, true) * (1.0 - targetShip.getArmorLarge());
            }else if (spaceShipSize == SpaceShipSize.HUGE) {
                tmpDamage = tmpDamage + getHugeSalvo(spaceship, gameWorld, true) * (1.0 - targetShip.getArmorHuge());
            }
        }
        Logger.finer( "Damage before shieldsmodifier: " + tmpDamage);
        tmpDamage = tmpDamage * shieldsMultiplier;
        Logger.finer( "Damage after shieldsmodifier: " + tmpDamage);
        double baseDamage = tmpDamage * ((spaceship.getCurrentDc() * 1.0) / spaceship.getDamageCapacity());
        Logger.finer( "Damage after hull damage effect: " + baseDamage);
        // randomize damage
        int actualDamage = (int) Math.round(baseDamage * (multiplier / 10.0));
        Logger.finest("Damage after multiplier: " + actualDamage + " ship hit: " + targetShip.getName() + " firing ship: " + spaceship.getName());
        if (actualDamage < 1) {
            actualDamage = 1;
        }
        return actualDamage;
    }

    public static void supplyWeapons(Spaceship spaceship, SpaceShipSize size) {
        if (spaceship.getWeaponsMaxSalvosMedium() < Integer.MAX_VALUE) {
            if (size.getCompareSize() >= SpaceShipSize.MEDIUM.getCompareSize()) {
                spaceship.setWeaponsSalvoesMedium(spaceship.getWeaponsMaxSalvosMedium());
            }
        }
        if (spaceship.getWeaponsMaxSalvosLarge() < Integer.MAX_VALUE) {
            if (size.getCompareSize() >= SpaceShipSize.LARGE.getCompareSize()) {
                spaceship.setWeaponsSalvoesLarge(spaceship.getWeaponsMaxSalvosLarge());
            }
        }
        if (spaceship.getWeaponsMaxSalvosHuge() < Integer.MAX_VALUE) {
            if (size.getCompareSize() > SpaceShipSize.HUGE.getCompareSize()) {
                spaceship.setWeaponsSalvoesHuge(spaceship.getWeaponsMaxSalvosHuge());
            }
        }
    }

    public static void restoreShields(Spaceship spaceship, GameWorld gameWorld) {
        spaceship.setCurrentShields(SpaceshipPureFunctions.getShields(spaceship, gameWorld));
    }

    // Check if ship gets away, and set new destination if it does
    // returnera true om flykten lyckades
    // returnera false om skeppet förstörs
    public static boolean retreat(Spaceship spaceship, Planet planetToRetreatTo, GameWorld gameWorld) {
        Logger.finer( "retreat() called");
        boolean gotAway = true;
        spaceship.setRunningTo(planetToRetreatTo);
        Logger.finer( "runningTo: " + spaceship.getRunningTo());
        if (spaceship.getRunningTo() == null) {
            Logger.finer( "runningTo == null");
            spaceship.setOldLocation(spaceship.getLocation());
            gotAway = false;
        } else {
            Logger.finer( "runningTo != null: " + spaceship.getRunningTo().getMapPlanetUuid());
            spaceship.setOldLocation(spaceship.getLocation());
            spaceship.setRunningFrom(spaceship.getLocation());
            spaceship.setLocation(null);
            spaceship.setRetreating(true);
            restoreShields(spaceship, gameWorld);
        }
        Logger.finer( "return: " + gotAway);
        return gotAway;
    }

    public static void squadronInRetreatingCarrier(Spaceship spaceship, GameWorld gameWorld) {
        Logger.finer( "squadronInRetreatingCarrier() called");
        spaceship.setOldLocation(spaceship.getLocation());
        spaceship.setRunningFrom(spaceship.getLocation());
        spaceship.setLocation(null);
        spaceship.setRetreating(true);
        restoreShields(spaceship, gameWorld);
    }

    public static void removeShip(Spaceship ss, Galaxy galaxy) {
        boolean ok;
        ss.setCurrentDc(0);
        if (ss.getSquadronCapacity() > 0) {
            removeSquadronsFromCarrier(ss, galaxy);
        }
        ok = galaxy.getSpaceships().remove(ss);
        if (!ok) {
            Logger.severe("Couldn't find spaceship to delete!!!");
        } // spårutskrift
    }

    private static void removeSquadronsFromCarrier(Spaceship aCarrier, Galaxy galaxy) {
        for (Iterator<Spaceship> iter = galaxy.getSpaceships().iterator(); iter.hasNext();) {
            Spaceship aShip = iter.next();
            if (aShip.getSize() == SpaceShipSize.SQUADRON) {
                if (aShip.getCarrierLocation() == aCarrier) {
                    aShip.setCarrierLocation(null);
                }
            }
        }
    }

    public static void performRepairs(Spaceship spaceship, GalaxyMap galaxyMap) {
        spaceship.setCurrentDc(spaceship.getDamageCapacity());
        if (spaceship.getOwner() != null) {
            spaceship.getOwner().addToGeneral("Your ship " + spaceship.getName() + " at "	+ PlanetPureFunctions.getPlanetName(galaxyMap, spaceship.getLocation().getMapPlanetUuid()) + " has been repaired up to full damage capacity.");
        }
    }

    public static void setArmor(SpaceshipType spaceshipType, int... armorValues){
        spaceshipType.setArmorSmall(armorValues[0]);
        if (armorValues.length > 1){
            spaceshipType.setArmorMedium(armorValues[1]);
            if (armorValues.length > 2){
                spaceshipType.setArmorLarge(armorValues[2]);
                if (armorValues.length > 3){
                    spaceshipType.setArmorHuge(armorValues[3]);
                }
            }
        }
    }

    public static void setStandardArmorLevels(SpaceshipType spaceshipType){
        if (spaceshipType.getSize() == SpaceShipSize.MEDIUM){
            spaceshipType.setArmorSmall(25);
        }else
        if (spaceshipType.getSize() == SpaceShipSize.LARGE){
            spaceshipType.setArmorSmall(50);
            spaceshipType.setArmorMedium(25);
        }else
        if (spaceshipType.getSize() == SpaceShipSize.HUGE){
            spaceshipType.setArmorSmall(75);
            spaceshipType.setArmorMedium(50);
            spaceshipType.setArmorLarge(25);
        }
    }
}
