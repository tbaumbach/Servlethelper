package spaceraze.servlethelper.game;

import spaceraze.world.Alignment;
import spaceraze.world.GameWorld;

import java.util.ArrayList;
import java.util.List;

public class AlignmentHelper {

    private AlignmentHelper(){}

    public static List<Alignment> createDefaultAlignments(GameWorld gameWorld){
        List<Alignment> alignments = new ArrayList<>();
        alignments.add(new Alignment("Good", gameWorld));
        alignments.add(new Alignment("Neutral", gameWorld));
        alignments.add(new Alignment("Evil", gameWorld));

        canHaveVIP("Good","Neutral", alignments);
        canHaveVIP("Neutral","Good", alignments);
        canHaveVIP("Neutral","Evil", alignments);
        canHaveVIP("Evil","Neutral", alignments);
        hateDuellist("Good","Evil", alignments);
        duelOwnAlignment("Good",false, alignments);

        return alignments;
    }

    public static void canHaveVIP(String factionAlignmentName, String vipAlignmentName, List<Alignment> alignments){
        Alignment factionAlignment = AlignmentPureFunctions.findAlignmentByName(factionAlignmentName, alignments);
        Alignment vipAlignment = AlignmentPureFunctions.findAlignmentByName(vipAlignmentName, alignments);
        factionAlignment.addCanHaveVip(vipAlignment);
    }

    public static void hateDuellist(String duellistAlignmentName1, String duellistAlignmentName2, List<Alignment> alignments){
        Alignment duellistAlignment1 = AlignmentPureFunctions.findAlignmentByName(duellistAlignmentName1, alignments);
        Alignment duellistAlignment2 = AlignmentPureFunctions.findAlignmentByName(duellistAlignmentName2, alignments);
        duellistAlignment1.addHateDuellist(duellistAlignment2);
        duellistAlignment2.addHateDuellist(duellistAlignment1);
    }

    public static void duelOwnAlignment(String duellistAlignmentName, boolean duelOwn, List<Alignment> alignments){
        Alignment duellistAlignment = AlignmentPureFunctions.findAlignmentByName(duellistAlignmentName, alignments);
        duellistAlignment.setDuelOwnAlignment(duelOwn);
    }

}
