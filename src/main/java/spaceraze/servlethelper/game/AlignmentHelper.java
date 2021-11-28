package spaceraze.servlethelper.game;

import spaceraze.world.Alignment;

import java.util.ArrayList;
import java.util.List;

public class AlignmentHelper {

    private AlignmentHelper(){}

    public static List<Alignment> createDefaultAlignments(){
        List<Alignment> alignments = new ArrayList<>();
        alignments.add(new Alignment("Good"));
        alignments.add(new Alignment("Neutral"));
        alignments.add(new Alignment("Evil"));

        canHaveVIP("good","neutral", alignments);
        canHaveVIP("neutral","good", alignments);
        canHaveVIP("neutral","evil", alignments);
        canHaveVIP("evil","neutral", alignments);
        hateDuellist("good","evil", alignments);
        duelOwnAlignment("good",false, alignments);

        return alignments;
    }

    public static void canHaveVIP(String factionAlignmentName, String vipAlignmentName, List<Alignment> alignments){
        Alignment factionAlignment = findAlignment(factionAlignmentName, alignments);
        Alignment vipAlignment = findAlignment(vipAlignmentName, alignments);
        factionAlignment.addCanHaveVip(vipAlignment);
    }

    public static void hateDuellist(String duellistAlignmentName1, String duellistAlignmentName2, List<Alignment> alignments){
        Alignment duellistAlignment1 = findAlignment(duellistAlignmentName1, alignments);
        Alignment duellistAlignment2 = findAlignment(duellistAlignmentName2, alignments);
        duellistAlignment1.addHateDuellist(duellistAlignment2);
        duellistAlignment2.addHateDuellist(duellistAlignment1);
    }

    public static void duelOwnAlignment(String duellistAlignmentName, boolean duelOwn, List<Alignment> alignments){
        Alignment duellistAlignment = findAlignment(duellistAlignmentName, alignments);
        duellistAlignment.setDuelOwnAlignment(duelOwn);
    }

    public static Alignment findAlignment(String findName, List<Alignment> alignments){
        Alignment found = null;
        int index = 0;
        while ((found == null) & (index < alignments.size())) {
            Alignment anAlignment = alignments.get(index);
            if (anAlignment.isAlignment(findName)){
                found = anAlignment;
            }else{
                index++;
            }
        }
        return found;
    }

}
