package spaceraze.servlethelper.game;

import spaceraze.servlethelper.handlers.GameWorldHandler;
import spaceraze.world.Alignment;
import spaceraze.world.GameWorld;
import spaceraze.world.Player;

import java.util.List;

public class AlignmentPureFunctions {

    private AlignmentPureFunctions(){}

    public static Alignment findAlignmentByUuid(String uuid, List<Alignment> alignments){
        return alignments.stream()
                .filter(alignment -> alignment.getUuid().equals(uuid))
                .findAny().orElse(null);
    }

    public static boolean canHaveVip(String uuid, Alignment alignment){
        return alignment.getCanHaveVips().contains(uuid);
    }

    public static List<Alignment> getAlignments(List<String> uuids, GameWorld gameWorld){
        return gameWorld.getAlignments().stream().filter(alignment -> uuids.contains(alignment.getUuid())).toList();
    }

    public static boolean hateDuellist(String uuid, Alignment alignment){
        return alignment.getHateDuellists().contains(uuid);
    }

    public static Alignment findAlignmentByName(String name, List<Alignment> alignments){
        return alignments.stream()
                .filter(alignment -> alignment.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public static Alignment getPlayerAlignment(Player player, GameWorld gameWorld){
        return AlignmentPureFunctions.findAlignmentByUuid(GameWorldHandler.getFactionByUuid(player.getFactionUuid(), gameWorld).getAlignment(), gameWorld.getAlignments());
    }
}
