package spaceraze.servlethelper.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import spaceraze.servlethelper.game.gameworld.GameWorldPureFunction;
import spaceraze.servlethelper.game.spaceship.SpaceshipPureFunctions;
import spaceraze.servlethelper.game.troop.TroopPureFunctions;
import spaceraze.util.general.Functions;
import spaceraze.world.*;

public class ResearchPureFunctions implements Serializable {
	static final long serialVersionUID = 1L;

	private ResearchPureFunctions() {
	}

	public static List<ResearchAdvantage> getAllAdvantagesThatIsReadyToBeResearchOn(Player player, Faction faction) {

		List<ResearchAdvantage> readyResearchAdvantages = faction.getResearchAdvantages().stream()
				.filter(researchAdvantage -> isReadyToBeResearchedOn(researchAdvantage.getUuid(), player, faction)).collect(Collectors.toList());

		List<ResearchAdvantage> sortedAdvantages = new ArrayList<>();
		sortedAdvantages.addAll(readyResearchAdvantages.stream()
				.filter(researchAdvantage -> !player.getResearchProgress(researchAdvantage.getName()).isDeveloped()).collect(Collectors.toList()));
		sortedAdvantages.addAll(readyResearchAdvantages.stream()
				.filter(researchAdvantage -> player.getResearchProgress(researchAdvantage.getName()).isDeveloped()).collect(Collectors.toList()));

		return sortedAdvantages;
	}

	public static boolean isReadyToBeResearchedOn(String uuid, Player player, Faction faction){
		return GameWorldPureFunction.getParent(uuid, faction).stream().allMatch(parent -> isDeveloped(player, parent));
	}

	public static boolean isDeveloped(Player player, ResearchAdvantage researchAdvantage){
		//TODO 2024-02-28 byt ut jämförelsen med namn mod uuid
		return player.getResearchProgress(researchAdvantage.getName()) != null && player.getResearchProgress(researchAdvantage.getName()).isDeveloped();
	}


	public static ResearchAdvantage getAdvantage(Faction faction, String name) {
		return faction.getResearchAdvantages().stream().filter(researchAdvantage -> researchAdvantage.getName().equalsIgnoreCase(name)).findFirst().orElseThrow();
	}

	public static String getResearchText(ResearchAdvantage researchAdvantage, GameWorld gameWorld){
		StringBuilder text;

		text = new StringBuilder();

		if(researchAdvantage.getOpenPlanetBonus() > 0){
			text.append("Open planet bonus: ").append(addPlus(researchAdvantage.getOpenPlanetBonus())).append("\n");
		}
		if(researchAdvantage.getClosedPlanetBonus() > 0){
			text.append("Closed planet bonus: ").append(addPlus(researchAdvantage.getClosedPlanetBonus())).append("\n");
		}
		if(researchAdvantage.getResistanceBonus() > 0){
			text.append("Resistance bonus: ").append(addPlus(researchAdvantage.getResistanceBonus())).append("\n");
		}
		if(researchAdvantage.getTechBonus() > 0){
			text.append("Tech bonus: ").append(addPlus(researchAdvantage.getTechBonus())).append("%\n");
		}
		if(researchAdvantage.isCanReconstruct()){
			text.append("Can reconstruct planets\n");
		}
		if(researchAdvantage.getReconstructCostBase() > 0){
			text.append("Reconstruct cost base: ").append(addPlus(researchAdvantage.getReconstructCostBase())).append("\n");
		}
		if(researchAdvantage.getReconstructCostMultiplier() > 0){
			text.append("Reconstruct multiplier cost: ").append(addPlus(researchAdvantage.getReconstructCostMultiplier())).append("\n");
		}
		if(researchAdvantage.getCorruptionPoint() != null){
			text.append("Corruption: ").append(researchAdvantage.getCorruptionPoint().getDescription()).append("\n");
		}

		for(ResearchUpgradeShip researchUpgradeShip : researchAdvantage.getResearchUpgradeShip()){
			text.append("\n").append(getResearchText(researchUpgradeShip, gameWorld));
		}

		for (ResearchUpgradeTroop aResearchTroopType: researchAdvantage.getResearchUpgradeTroop()) {
			text.append("\n").append(getResearchText(aResearchTroopType, gameWorld));
		}

		for (ResearchUpgradeBuilding aResearchBuildingType: researchAdvantage.getResearchUpgradeBuilding()) {
			text.append("\n").append(getResearchText(aResearchBuildingType, gameWorld));
		}

		return text.toString();
	}

	private static String getResearchText(ResearchUpgradeTroop researchTroopType, GameWorld gameWorld){
		String text;

		text= "Change properties for the troop type: " + TroopPureFunctions.getTroopTypeByUuid(researchTroopType.getTypeUuid(), gameWorld).getName();

		if(researchTroopType.getAttackInfantry() != 0){
			text += "\n-Attack Infantry: " + addPlus(researchTroopType.getAttackInfantry());
		}
		if(researchTroopType.getAttackArmored() != 0){
			text += "\n-Attack Armored: " + addPlus(researchTroopType.getAttackArmored());
		}
		if(researchTroopType.getAttackArtillery() != 0){
			text += "\n-Attack Artillery: " + addPlus(researchTroopType.getAttackArtillery());
		}
		if(researchTroopType.getDamageCapacity() != 0){
			text += "\n-Damage Capacity: " + addPlus(researchTroopType.getDamageCapacity());
		}
		if(researchTroopType.getCostBuild() != 0){
			text += "\n-Cost Build: " + addPlus(researchTroopType.getCostBuild());
		}
		if(researchTroopType.getCostSupport() != 0){
			text += "\n-Cost Support: " + addPlus(researchTroopType.getCostSupport());
		}
		if (researchTroopType.isChangeSpaceshipTravel()){
			text += "\n-Spaceship Travel: " + Functions.getYesNo(researchTroopType.isSpaceshipTravel());
		}
    /*	if (changeAttackScreened){
    		text += "\n-Attack Screened: " + Functions.getYesNo(attackScreened);
    	}*/
		if (researchTroopType.isChangeVisible()){
			text += "\n-Visible on map: " + Functions.getYesNo(researchTroopType.isVisible());
		}

		return text;
	}

	public static String getResearchText(ResearchUpgradeBuilding aResearchBuildingType, GameWorld gameWorld){
		String text;

		text= "Change properties for the Building type: " + BuildingPureFunctions.getBuildingTypeByUuid(aResearchBuildingType.getTypeUuid(), gameWorld).getName();

		if(aResearchBuildingType.getBuildCost() > 0){
			text+="\n-Build cost: " + addPlus(aResearchBuildingType.getBuildCost());
		}
		if(aResearchBuildingType.getWharfSize() > 0){
			text+="\n-Wharf size: " + aResearchBuildingType.getWharfSize();
		}
		if(aResearchBuildingType.getTroopSize() > 0){
			text+="\n-Troop size: " + aResearchBuildingType.getTroopSize();
		}
		if(aResearchBuildingType.getCounterEspionage() > 0){
			text+="\n-CounterEspionage: " + addPlus(aResearchBuildingType.getCounterEspionage()) + "%";
		}
		if(aResearchBuildingType.getExterminator() > 0){
			text+="\n-Exterminator: " + addPlus(aResearchBuildingType.getExterminator()) + "%";
		}
		if(aResearchBuildingType.getShieldCapacity() > 0){
			text+="\n-Shield Capacity: " + addPlus(aResearchBuildingType.getShieldCapacity());
		}
		if(aResearchBuildingType.getCannonDamage() > 0){
			text+="\n-Cannon Damage: " + addPlus(aResearchBuildingType.getCannonDamage());
		}
		if(aResearchBuildingType.getCannonRateOfFire() > 0){
			text+="\n-Cannon Rate Of Fire: " + addPlus(aResearchBuildingType.getCannonRateOfFire());
		}
		if(aResearchBuildingType.getResistanceBonus() > 0){
			text+="\n-Resistance Bonus: " + addPlus(aResearchBuildingType.getResistanceBonus());
		}
		if(aResearchBuildingType.getTechBonus() > 0){
			text+="\n-Tech Bonus: " + addPlus(aResearchBuildingType.getTechBonus()) + "%";
		}
		if(aResearchBuildingType.getOpenPlanetBonus() > 0){
			text+="\n-Open Planet Income Bonus: " + addPlus(aResearchBuildingType.getOpenPlanetBonus());
		}
		if(aResearchBuildingType.getClosedPlanetBonus() > 0){
			text+="\n-Closed Planet Income Bonus: " + addPlus(aResearchBuildingType.getClosedPlanetBonus());
		}
		if(aResearchBuildingType.isChangeVisibleOnMap()){
			text+="\n-Visible On Map: " + Functions.getYesNo(aResearchBuildingType.isVisibleOnMap());
		}
		if(aResearchBuildingType.isChangeSpaceport()){
			text+="\n-Spaceport: " + Functions.getYesNo(aResearchBuildingType.isSpaceport());
		}
    	/*
    	if(aimBonus > 0){
    		text+="\n-Aim Bonus: " + addPlus(aimBonus);
    	}
    	if(troopAttacksBonus > 0){
    		text+="\n-Troop Attacks Bonus: " + addPlus(troopAttacksBonus);
    	}
    	if(landBattleGroupAttacksBonus > 0){
    		text+="\n-Land Battle Group Attacks Bonus: " + addPlus(landBattleGroupAttacksBonus) + "%";
    	}*/

		return text;
	}

	public static String getResearchText(ResearchUpgradeShip researchUpgradeShip, GameWorld gameWorld){
		String text;

		text= "Change properties for the ship model: " + SpaceshipPureFunctions.getSpaceshipTypeByUuid(researchUpgradeShip.getTypeUuid(), gameWorld).getName();

		if(researchUpgradeShip.getShields() != 0){
			text+="\n-Shields: " + addPlus(researchUpgradeShip.getShields());
		}
		if(researchUpgradeShip.getUpkeep() != 0){
			text+="\n-Upkeep: " + addPlus(researchUpgradeShip.getUpkeep());
		}
		if(researchUpgradeShip.getBuildCost() != 0){
			text+="\n-Build cost: " + addPlus(researchUpgradeShip.getBuildCost());
		}
		if(researchUpgradeShip.getBombardment() != 0){
			text+="\n-Bombardment: " + addPlus(researchUpgradeShip.getBombardment());
		}
		if(researchUpgradeShip.getIncreaseInitiative() != 0){
			text+="\n-IncreaseInitiative: " + addPlus(researchUpgradeShip.getIncreaseInitiative());
		}
		if(researchUpgradeShip.getInitDefence() != 0){
			text+="\n-InitDefence: " + addPlus(researchUpgradeShip.getInitDefence());
		}
		if(researchUpgradeShip.getPsychWarfare() != 0){
			text+="\n-psychWarfare: " + addPlus(researchUpgradeShip.getPsychWarfare());
		}
		if(researchUpgradeShip.getRange() != null){
			text+="\n-Range: " + researchUpgradeShip.getRange().toString();
		}
		if(researchUpgradeShip.isChangeNoRetreat()){
			text+="\n-No Retreat: " + addYesOrNo(researchUpgradeShip.isNoRetreat());
		}
		if(researchUpgradeShip.isChangeInitSupport()){
			text+="\n-InitSupport: " + addYesOrNo(researchUpgradeShip.isInitSupport());
		}
		if(researchUpgradeShip.getWeaponsStrengthSquadron() != 0){
			text+="\n-Weapons strength squadron: " + addPlus(researchUpgradeShip.getWeaponsStrengthSquadron());
		}
		if(researchUpgradeShip.getWeaponsStrengthSmall() != 0){
			text+="\n-Weapons strength small: " + addPlus(researchUpgradeShip.getWeaponsStrengthSmall());
		}
		if(researchUpgradeShip.getWeaponsStrengthMedium() != 0){
			text+="\n-Weapons strength medium: " + addPlus(researchUpgradeShip.getWeaponsStrengthMedium());
		}
		if(researchUpgradeShip.getWeaponsStrengthLarge() != 0){
			text+="\n-Weapons strength large: " + addPlus(researchUpgradeShip.getWeaponsStrengthLarge());
		}
		if(researchUpgradeShip.getWeaponsStrengthHuge() != 0){
			text+="\n-Weapons strength huge: " + addPlus(researchUpgradeShip.getWeaponsStrengthHuge());
		}
		if(researchUpgradeShip.getArmorSmall() != 0){
			text+="\n-Armor Small: " + addPlus(researchUpgradeShip.getArmorSmall());
		}
		if(researchUpgradeShip.getArmorMedium() != 0){
			text+="\n-Armor Medium: " + addPlus(researchUpgradeShip.getArmorMedium());
		}
		if(researchUpgradeShip.getArmorLarge() != 0){
			text+="\n-Armor Large: " + addPlus(researchUpgradeShip.getArmorLarge());
		}
		if(researchUpgradeShip.getArmorHuge() != 0){
			text+="\n-Armor Huge: " + addPlus(researchUpgradeShip.getArmorHuge());
		}
		if(researchUpgradeShip.getSupply() != null){
			text+="\n-Supply ships size: " + researchUpgradeShip.getSupply().getDescription();
		}

		if(researchUpgradeShip.getSquadronCapacity() != 0){
			text+="\n-Squadron carrier capacity: " + addPlus(researchUpgradeShip.getSquadronCapacity());
		}
		if(researchUpgradeShip.getIncEnemyClosedBonus() != 0){
			text+="\n-Incom enemy closed bonus: " + addPlus(researchUpgradeShip.getIncEnemyClosedBonus());
		}
		if(researchUpgradeShip.getIncNeutralClosedBonus() != 0){
			text+="\n-Incom neutral closed bonus: " + addPlus(researchUpgradeShip.getIncNeutralClosedBonus());
		}
		if(researchUpgradeShip.getIncFriendlyClosedBonus() != 0){
			text+="\n-Incom frendly closed bonus: " + addPlus(researchUpgradeShip.getIncFriendlyClosedBonus());
		}
		if(researchUpgradeShip.getIncOwnClosedBonus() != 0){
			text+="\n-Incom own closed bonus: " + addPlus(researchUpgradeShip.getIncOwnClosedBonus());
		}
		if(researchUpgradeShip.getIncEnemyOpenBonus() != 0){
			text+="\n-Incom enemy open bonus: " + addPlus(researchUpgradeShip.getIncEnemyOpenBonus());
		}
		if(researchUpgradeShip.getIncNeutralOpenBonus() != 0){
			text+="\n-Incom neutral open bonus: " + addPlus(researchUpgradeShip.getIncNeutralOpenBonus());
		}
		if(researchUpgradeShip.getIncFriendlyOpenBonus() != 0){
			text+="\n-Incom frendly open Bbnus: " + addPlus(researchUpgradeShip.getIncFriendlyOpenBonus());
		}
		if(researchUpgradeShip.getIncOwnOpenBonus() != 0){
			text+="\n-Incom own open bonus: " + addPlus(researchUpgradeShip.getIncOwnOpenBonus());
		}
		if(researchUpgradeShip.isChangePlanetarySurvey()){
			text+="\n-Planetary survey: " + addYesOrNo(researchUpgradeShip.isPlanetarySurvey());
		}
		if(researchUpgradeShip.isChangeCanAttackScreenedShips()){
			text+="\n-Can attack screened ships: " + addYesOrNo(researchUpgradeShip.isCanAttackScreenedShips());
		}
		if(researchUpgradeShip.isChangeLookAsCivilian()){
			text+="\n-Look as civilian: " + addYesOrNo(researchUpgradeShip.isLookAsCivilian());
		}
		if(researchUpgradeShip.isChangeCanBlockPlanet()){
			text+="\n-Can block planet: " + addYesOrNo(researchUpgradeShip.isCanBlockPlanet());
		}
		if(researchUpgradeShip.isChangeVisibleOnMap()){
			text+="\n-Visible on nap: " + addYesOrNo(researchUpgradeShip.isVisibleOnMap());
		}
		if(researchUpgradeShip.getTroopCarrier() != 0){
			text+="\n-Troop Capacity: " + addPlus(researchUpgradeShip.getTroopCarrier());
		}


		return text;
	}

	/* TODO kolla att vi inte behöver lägga till - när talet är negativt
	private static String addplus(int number){
		StringBuilder retVal = new StringBuilder();
		if(number > 0){
			retVal.append("+");
		}else if (number < 0){
			retVal.append("-");
		}
		retVal.append(number);
		return retVal.toString();
	}*/

	private static String addPlus(int number){
		if(number > 0){
			return "+" +number;
		}
		return Integer.toString(number);
	}

	@SuppressWarnings("unused")
	private static String addYesOrNo(boolean test){
		if(test){
			return "Yes";
		}
		return "No";
	}
}
