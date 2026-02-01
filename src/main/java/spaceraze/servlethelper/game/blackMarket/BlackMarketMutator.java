package spaceraze.servlethelper.game.blackMarket;

import spaceraze.game.BlackMarketBid;
import spaceraze.game.BlackMarketOffer;
import spaceraze.game.Galaxy;
import spaceraze.servlethelper.game.BlackMarketPureFunctions;
import spaceraze.servlethelper.game.player.PlayerPureFunctions;
import spaceraze.world.GameWorld;

public class BlackMarketMutator {

    private BlackMarketMutator(){}

    public static void addBlackMarketMessages(Galaxy galaxy, String playerUuid, String aMessage) {
        galaxy.getPlayers().stream().filter(player -> !player.getUuid().equalsIgnoreCase(playerUuid)).forEach(player -> player.getTurnInfo().addToLatestBlackMarketReport(aMessage));
    }

    public static void createRemovedOldMessage(Galaxy galaxy, GameWorld gameWorld, BlackMarketOffer blackMarketOffer){
        // create turn info row for all undefeated players
        galaxy.addBlackMarketMessages(null, BlackMarketPureFunctions.getDescription(blackMarketOffer, gameWorld) + " removed from Black market due to lack of bids for 3 turns.");
    }

    public static void sendDrawMessages(Galaxy galaxy, BlackMarketOffer blackMarketOffer, GameWorld gameWorld){
        for (int i = 0; i < blackMarketOffer.getBlackMarketBids().size(); i++){
            BlackMarketBid aBid = blackMarketOffer.getBlackMarketBids().get(i);
            PlayerPureFunctions.getPlayer(galaxy, aBid.getPlayerUuid()).addToLatestBlackMarketMessages("The bidding for a " + BlackMarketPureFunctions.getDescription(blackMarketOffer, gameWorld) + " ended in a draw at the cost of " + BlackMarketMutator.getHighestBid(blackMarketOffer) + ".");
            PlayerPureFunctions.getPlayer(galaxy, aBid.getPlayerUuid()).addToLatestBlackMarketMessages("The " + BlackMarketPureFunctions.getDescription(blackMarketOffer, gameWorld) + " will remain for sale.");
        }
    }

    private static int getHighestBid(BlackMarketOffer blackMarketOffer){
        int maxBid = 0;
        for (int i = 0; i < blackMarketOffer.getBlackMarketBids().size(); i++){
            BlackMarketBid aBid = blackMarketOffer.getBlackMarketBids().get(i);
            if (maxBid == 0){
                maxBid = aBid.getCost();
            }else
            if (aBid.getCost() > maxBid){
                maxBid = aBid.getCost();
            }
        }
        return maxBid;
    }
}
