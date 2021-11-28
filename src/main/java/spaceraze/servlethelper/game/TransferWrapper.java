/*
 * Created on 2005-mar-05
 */
package spaceraze.servlethelper.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import spaceraze.world.*;
import spaceraze.world.orders.Orders;
import spaceraze.world.spacebattle.ReportLevel;

/**
 * @author WMPABOD
 *
 *         Wrapper for data transferred to and from tunnel servlet. Also
 *         contains some logic for that data and the tunnel.
 */
public class TransferWrapper implements Serializable {
	static final long serialVersionUID = 1L;
	private Object returnObject;
	private int port; // = gameid
	private String message;
	private int turn;
	private int contentSize; // when getTurn, server should return the byte size of the game specified in
								// port
	private GameWorld gameWorld;

	// ny kod för att koppla loss Player objektet när ett drag görs.
	private List<PlanetOrderStatus> planetOrderStatuses = new ArrayList<>();
	private Orders orders;
	private String playerName = null;
	private String notes;
	private Message mailMessage;
	boolean finishedThisTurn = false;
	private int messageId;
	private int latestReadMessage;

	private ReportLevel reportLevel;

	private int mapInfoFromTurn; // used if caller only wants to have MapInfoTurn-objects from a certain turn.
									// Default will return all turns

	public ReportLevel getReportLevel() {
		return reportLevel;
	}

	public TransferWrapper(Player aPlayer, String message, int port) {
		this.port = port;
		this.message = message;

		// ny kod för att koppla loss Player objektet när ett drag görs.
		if (aPlayer != null) {
			orders = aPlayer.getOrders();
			playerName = aPlayer.getName();
			notes = aPlayer.getNotes();
			finishedThisTurn = aPlayer.isFinishedThisTurn();
			turn = aPlayer.getGalaxy().getTurn();
			latestReadMessage = aPlayer.getLatestMessageIdFromServer();
			messageId = aPlayer.getMessageId();
			reportLevel = aPlayer.getReportLevel();
			planetOrderStatuses = aPlayer.getPlanetOrderStatuses();
		}
	}

	public TransferWrapper(Message aMessage, Player aPlayer, String message, int port) {
		this(aPlayer, message, port);
		this.mailMessage = aMessage;

	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String aMessage) {
		message = aMessage;
	}

	public int getPort() {
		return port;
	}

	public boolean isGetPlayer() {
		return (playerName == null);
	}

	public boolean isGetTurn() {
		return (playerName == null) && (message == null);
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getTurn() {
		return turn;
	}

	public int getContentSize() {
		return contentSize;
	}

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isFinishedThisTurn() {
		return finishedThisTurn;
	}

	public Message getMailMessage() {
		return mailMessage;
	}

	public int getMessageId() {
		return messageId;
	}

	public int getLatestReadMessage() {
		return latestReadMessage;
	}

	public List<PlanetOrderStatus> getPlanetOrderStatuses() {
		return planetOrderStatuses;
	}

	public int getMapInfoFromTurn() {
		return mapInfoFromTurn;
	}

}
