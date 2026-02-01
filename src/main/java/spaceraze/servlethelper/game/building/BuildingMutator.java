package spaceraze.servlethelper.game.building;

import spaceraze.game.Building;
import spaceraze.game.Planet;
import spaceraze.game.PlayerBuildingImprovement;
import spaceraze.world.BuildingType;

import java.util.ArrayList;
import java.util.UUID;

public class BuildingMutator {
    
    private BuildingMutator(){}

    public static Building createBuilding(BuildingType buildingType, int productionNumber, Planet location) {
        Building building = new Building();
        building.setUuid(UUID.randomUUID().toString());
        building.setTypeUuid(buildingType.getUuid());
        building.setProductionNumber(productionNumber);
        building.setLocation(location);

        building.setOpenPlanetBonus(buildingType.getOpenPlanetBonus());
        building.setClosedPlanetBonus(buildingType.getClosedPlanetBonus());
        building.setTechBonus(buildingType.getTechBonus());
        building.setWharfSize(buildingType.getWharfSize());
        building.setTroopSize(buildingType.getTroopSize());
        building.setSpaceport(buildingType.isSpaceport());
        building.setVisibleOnMap(buildingType.isVisibleOnMap());
        building.setResistanceBonus(buildingType.getResistanceBonus());
        building.setShieldCapacity(buildingType.getShieldCapacity());
        building.setCannonDamage(buildingType.getCannonDamage());
        building.setCannonRateOfFire(buildingType.getCannonRateOfFire());
        building.setAlienKiller(buildingType.isAlienKiller());
        building.setCounterEspionage(buildingType.getCounterEspionage());
        building.setExterminator(buildingType.getExterminator());

        return building;
    }

    public static BuildingType createImprovedBuildingType(BuildingType original, PlayerBuildingImprovement improvement) {
        BuildingType mutated = new BuildingType(original.getName(), original.getShortName(), original.getBuildCost());

        mutated.setUuid(original.getUuid());
        mutated.setVipTypes(new ArrayList<>(original.getVipTypes()));
        mutated.getTypeOfTroop().addAll(original.getTypeOfTroop());
        mutated.setDescription(original.getDescription());
        mutated.setAdvantages(original.getAdvantages());
        mutated.setInOrbit(original.isInOrbit());
        mutated.setAutoDestructWhenConquered(original.isAutoDestructWhenConquered());
        mutated.setSelfDestructible(original.isSelfDestructible());
        mutated.setDeveloped(improvement.isDeveloped());
        mutated.setWorldUnique(original.isWorldUnique());
        mutated.setFactionUnique(original.isFactionUnique());
        mutated.setPlayerUnique(original.isPlayerUnique());
        mutated.setPlanetUnique(original.isPlanetUnique());

        mutated.setOpenPlanetBonus(original.getOpenPlanetBonus() + improvement.getOpenPlanetBonus());
        mutated.setClosedPlanetBonus(original.getClosedPlanetBonus() + improvement.getClosedPlanetBonus());
        mutated.setTechBonus(original.getTechBonus() + improvement.getTechBonus());
        mutated.setWharfSize(improvement.getWharfSize() > 0 ? improvement.getWharfSize() : original.getWharfSize());
        mutated.setTroopSize(improvement.getTroopSize() > 0 ? improvement.getTroopSize() : original.getTroopSize());
        mutated.setBuildCost(improvement.getBuildCost() > 0 ? improvement.getBuildCost() : original.getBuildCost());
        mutated.setSpaceport(improvement.isChangeSpaceport() ? improvement.isSpaceport() : original.isSpaceport());
        mutated.setVisibleOnMap(improvement.isChangeVisibleOnMap() ? improvement.isVisibleOnMap() : original.isVisibleOnMap());

        mutated.setResistanceBonus(original.getResistanceBonus() + improvement.getResistanceBonus());
        mutated.setShieldCapacity(original.getShieldCapacity() + improvement.getShieldCapacity());
        mutated.setCannonDamage(original.getCannonDamage() + improvement.getCannonDamage());
        mutated.setCannonRateOfFire(original.getCannonRateOfFire() + improvement.getCannonRateOfFire());
        mutated.setCannonHitChance(original.getCannonHitChance());

        mutated.setParentBuildingType(original.getParentBuildingType());

        mutated.setAlienKiller(improvement.isChangeAlienKiller() ? improvement.isAlienKiller() : original.isAlienKiller());
        mutated.setCounterEspionage(original.getCounterEspionage() + improvement.getCounterEspionage());
        mutated.setExterminator(original.getExterminator() + improvement.getExterminator());

        return mutated;
    }

}
