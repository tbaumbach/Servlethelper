package spaceraze.servlethelper.game;

import spaceraze.util.general.Logger;
import spaceraze.world.*;

import java.util.*;

/**
 * This class handles all statistics within a single game
 * The data is organized like this
 *
 */
public class StatisticsHandler{

	private static final int WIN_LOSE_PROD_PERCENTAGE = 66;

	private StatisticsHandler(){}
	
	public static void createStatistics(Galaxy galaxy, StatisticGameType statisticGameType){
		galaxy.setStatisticGameType(statisticGameType);
		Logger.fine("statisticGameType: " + statisticGameType);
		for (StatisticType aStatisticType : StatisticType.values()) {
			galaxy.getAllStatistics().add(new Statistics(aStatisticType));
		}
	}
	
	public static Statistics findStatistics(StatisticType aStatisticType, Galaxy galaxy){
		Statistics foundStatistics = null;
		int i = 0;
		while ((foundStatistics == null) & (i < galaxy.getAllStatistics().size())){
			Statistics tmpStatistics = galaxy.getAllStatistics().get(i);
			if (tmpStatistics.getStatisticType() == aStatisticType){
				foundStatistics = tmpStatistics;
			}else{
				i++;
			}
		}
		return foundStatistics;
	}
	
	/**
	 * Adds a statistics value to the right type.
	 * Turnnumber will be set automatically.
	 */
	public static void addStatistics(StatisticType statisticType, String aPlayerName, int value, boolean cumulative, Galaxy galaxy){
		Logger.finest(statisticType + " " + aPlayerName + " " + value);
		Statistics foundStatistics = StatisticsHandler.findStatistics(statisticType, galaxy);
		StatisticsHandler.addStatistics(aPlayerName, value, cumulative, foundStatistics);
	}

	public static void addStatistics(String key, int value, boolean cumulative, Statistics statistics){
		String playerName = key;
		StatisticPost post = findPost(playerName, statistics);
		if (post == null){ // assumes this is turn 1 and a new player should be added
			post = StatisticPost.builder().uuid(key).build();
			post.getValues().add(value);
			statistics.getStatisticPosts().add(post);
		}else{
			if (cumulative){
				int lastValue = post.getValues().get(post.getValues().size()-1);
				post.getValues().add(lastValue + value);
			}else{
				post.getValues().add(value);
			}
		}
		Logger.finest(statistics.getStatisticType().toString() + " " + key + " " + value);
	}

	public static StatisticPost findPost(String key, Statistics statistics){
		return statistics.getStatisticPosts().stream().filter(statisticPost -> statisticPost.getUuid().equals(key)).findFirst().orElse(null);
	}

	public static int getLastTurn(Statistics statistics){
		return statistics.getStatisticPosts().get(0).getValues().size();
	}

	public static List<Integer> getWinLimit(Statistics statistics){
		List<Integer> winLimitList = new ArrayList<>();

		for (int i = 0; i < getLastTurn(statistics); i++){
			int index = i;
			int sum = statistics.getStatisticPosts().stream().map(statisticPost -> statisticPost.getValues().get(index)).reduce(0, (integer, integer2) -> integer +integer2);
			//TODO 2020-12-23 Is the use of WIN_LOSE_PROD_PERCENTAGE correct? think we should use the Galaxy.singleVictory or factionVictory?
			winLimitList.add((int)Math.round(sum*WIN_LOSE_PROD_PERCENTAGE/100.0));
		}
		return winLimitList;
	}

	/**
	 * Does not count the "Neutral" player
	 * @return
	 */
	public static int getMaxValue(Statistics statistics){
		int maxValue = 0;

		for(StatisticPost statisticPost :statistics.getStatisticPosts()){
			if (!statisticPost.getUuid().equalsIgnoreCase("Neutral")) {
				for (Integer value : statisticPost.getValues()) {
					if (value > maxValue) {
						maxValue = value;
					}
				}
			}
		}

		return maxValue;
		/*
		List<Integer> values = statistics.getStatisticPosts().stream()
				.filter(statisticPost -> !statisticPost.getKey().equalsIgnoreCase("Neutral"))
				.map(StatisticPost::getValues)
				.reduce((integers, integers2) -> {
					integers.addAll(integers2);
					return integers;
				}).orElse(new ArrayList<>());

		Integer integer = values.stream().reduce(Integer::compareTo).orElse(0);
		return integer;
		*/
	}
}
