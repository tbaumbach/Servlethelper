package spaceraze.servlethelper.game.spaceship;

import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.util.general.Logger;
import spaceraze.world.*;
import spaceraze.world.enums.SpaceShipSize;

import java.util.Iterator;

public class SpaceshipMutator {

    private SpaceshipMutator(){}

    public static Spaceship createSpaceShip(Player player, SpaceshipType type, VIP vipWithBonus, int factionTechBonus, int buildingBonus){
        PlayerSpaceshipImprovement playerSpaceshipImprovement = PlayerPureFunctions.findSpaceshipImprovement(type.getName(), player);
        SpaceshipType spaceshipType = playerSpaceshipImprovement != null ? new SpaceshipType(type, playerSpaceshipImprovement) : type;
        int nrProduced = playerSpaceshipImprovement != null ? playerSpaceshipImprovement.updateNrProduced() : 0;

         return new Spaceship(spaceshipType, null, nrProduced, vipWithBonus,factionTechBonus,buildingBonus);
    }

    public static Spaceship createSpaceShip(SpaceshipType type){

        return new Spaceship(type, null, 0, null,0,0);
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
            SpaceShipSize spaceShipSize = SpaceshipPureFunctions.getSpaceshipTypeByKey(targetShip.getTypeKey(), gameWorld).getSize();
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
            Logger.finer( "runningTo != null: " + spaceship.getRunningTo().getName());
            spaceship.setOldLocation(spaceship.getLocation());
            spaceship.setRunningTo(spaceship.getLocation());
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
        ss.setDestroyed();
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
}
