/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision;

import com.ibm.icu.text.ArabicShapingException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.sicut.util.ALIGNMENT;
import org.sicut.util.Misc;
import org.sicut.util.pdf.BasePDFFile;
import org.sicut.util.pdf.LARectangle;
import static pvscreator.Settings.PREF_BUNDLE;
import static pvscreator.Settings.STUDENTS;
import pvscreator.massar.PVStudent;
import pvscreator.template.decision.S2L3TwoPages.Page1;
import pvscreator.template.decision.S2L3TwoPages.Page2;

/**
 *
 * @author Sicut
 */
public class S2DecPDFFile extends BasePDFFile {
    
    private Page1 l3P1Metrics;
    private Page2 l3P2Metrics;
    private S2LN3DecPage ln3Metrics;
    private final DecimalFormat INTEGER, DECIMAL;
    private int i;
    Map<Integer, Double> map;
    private double treshold;
    
    public S2DecPDFFile() {
        super();
        l3P1Metrics = null;
        l3P2Metrics = null;
        ln3Metrics = null;
        DECIMAL = new DecimalFormat("00.00");
        DECIMAL.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        tpl = "/pvscreator/template/resources/S2_DEC_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        Map<String, List<PVStudent>> groups = STUDENTS.stream().collect(Collectors.groupingBy(PVStudent::getGroup));
        reset();
        try {
            treshold = Double.valueOf(PREF_BUNDLE.get("ATABA_VALUE"));
        } catch ( NumberFormatException nfe ) {
            treshold = 10;
        }
        groups.keySet().stream().sorted((g1, g2) -> Misc.getGroupWeight(g1).compareTo(Misc.getGroupWeight(g2))).forEach(group -> {
            Optional<PVStudent> op = STUDENTS.stream().filter(s -> group.equals(s.getGroup())).findFirst();
            if ( op.isPresent() ) {
                PDPage pg;
                if ( op.get().getIsGrpL3() ) {
                    if ( l3P1Metrics == null ) {
                        l3P1Metrics = new Page1();
                        l3P2Metrics = new Page2();
                    }
                    pg = clonePage(1);
                    generateL3P1(pg, group, op.get());
                    pg = clonePage(2);
                    generateL3P2(pg, group, op.get());
                }
                else {
                    if ( ln3Metrics == null )
                        ln3Metrics = new S2LN3DecPage();
                    pg = clonePage(0);
                    generateLN3(pg, group, op.get());
                } 
            }
        });
        for ( i = 0; i < 3; i++ )
            removePage(0);
        return save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("Dec-", "pdf"));
    }
    
    private void generateLN3(PDPage pg, String group, PVStudent student) {
        long nGrp;
        Iterator<PVStudent> it;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            placeMultilineString(ln3Metrics.getMINISTERY(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
            placeMultilineString(ln3Metrics.getSCHOOL_INFOS(), pcs, student.getAcademy() + "\n" + student.getDirection() + "\n" + student.getSchool(), ALIGNMENT.RIGHT);
            placeMultilineString(ln3Metrics.getSEMESTER(), pcs, "الدورة الثانية", ALIGNMENT.RIGHT);
            placeString(ln3Metrics.getGROUP(), pcs, student.getGroup(), ALIGNMENT.LEFT);
            placeString(ln3Metrics.getLEVEL(), pcs, student.getLevelFull(), ALIGNMENT.RIGHT);
            placeString(ln3Metrics.getYEAR(), pcs, student.getYear(), ALIGNMENT.RIGHT);
            
            nGrp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            ln3Metrics.prepareRows(nGrp);
            i = 1;
            it = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).sorted((s1, s2) -> s2.getAvg().compareTo(s1.getAvg())).iterator();
            while ( it.hasNext() ) {
                it.next().setRank(i++);
            }
            ln3Metrics.prepareDividers().forEach(div -> {
                try {
                    drawNFill(div, pcs);
                } catch (IOException ex) { }
            });
            i = 0;
            STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).forEach(s -> {
                try {
                    ln3Metrics.prepareRow(i++);
                    placeString(ln3Metrics.getCode(), pcs, s.getCode());
                    placeString(ln3Metrics.getName(), pcs, s.getName(), ALIGNMENT.RIGHT);
                    placeString(ln3Metrics.getGender(), pcs, s.isFemale() ? "\ue9dd" : "\ue9dc");
                    placeString(ln3Metrics.getS1(), pcs, DECIMAL.format(s.getS1()));
                    placeString(ln3Metrics.getS2(), pcs, DECIMAL.format(s.getS2()));
                    placeString(ln3Metrics.getAverage(), pcs, DECIMAL.format(s.getAvg()));
                    placeString(ln3Metrics.getRank(), pcs, INTEGER.format(s.getRank()));
                    if ( "".equals(s.getDecision()) ) {
                        placeString(ln3Metrics.getDecision(), pcs, s.getAvg() < treshold ? "" : (s.isFemale() ? "ت" : "ي") + "نتقل");
                    }
                    else {
                        placeString(ln3Metrics.getDecision(), pcs, s.getDecision());
                    }
                    for ( int j = 0; j < 3; j++ ) {
                        placeString(ln3Metrics.getYears()[j], pcs, s.getYears()[j] + "");
                    }
                } catch (IOException | IllegalArgumentException | ArabicShapingException ex) { }
            });
        } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { }
    }
    
    private void generateL3P1(PDPage pg, String group, PVStudent student) {
        long nGrp;
        Iterator<PVStudent> it;
        ArrayList<LARectangle> dividers;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            placeMultilineString(l3P1Metrics.getMINISTERY(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
            placeMultilineString(l3P1Metrics.getSCHOOL_INFOS(), pcs, student.getAcademy() + "\n" + student.getDirection() + "\n" + student.getSchool(), ALIGNMENT.RIGHT);
            placeString(l3P1Metrics.getGROUP(), pcs, student.getGroup(), ALIGNMENT.LEFT);
            placeString(l3P1Metrics.getLEVEL(), pcs, student.getLevelFull(), ALIGNMENT.RIGHT);
            placeBidirectionalString(l3P1Metrics.getYEAR(), pcs, student.getYear() + " / " + "الدورة الثانية", ALIGNMENT.RIGHT);
            
            nGrp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            l3P1Metrics.prepareRows(nGrp);
            i = 1;
            it = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).sorted((s1, s2) -> s2.getAvg().compareTo(s1.getAvg())).iterator();
            while ( it.hasNext() ) {
                it.next().setRank(i++);
            }
            l3P1Metrics.prepareDividers().forEach(div -> {
                try {
                    drawNFill(div, pcs);
                } catch (IOException ex) { }
            });
            i = 0;
            STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).forEach(s -> {
                try {
                    l3P1Metrics.prepareRow(i++);
                    placeString(l3P1Metrics.getCode(), pcs, s.getCode());
                    placeString(l3P1Metrics.getName(), pcs, s.getName(), ALIGNMENT.RIGHT);
                    placeString(l3P1Metrics.getGender(), pcs, s.isFemale() ? "\ue9dd" : "\ue9dc");
                    placeString(l3P1Metrics.getS1(), pcs, DECIMAL.format(s.getS1()));
                    placeString(l3P1Metrics.getS2(), pcs, DECIMAL.format(s.getS2()));
                    placeString(l3P1Metrics.getLoc(), pcs, DECIMAL.format(s.getLoc()));
                    placeString(l3P1Metrics.getReg(), pcs, DECIMAL.format(s.getReg()));
                    placeString(l3P1Metrics.getAverage(), pcs, DECIMAL.format(s.getAvg()));
                    placeString(l3P1Metrics.getRank(), pcs, INTEGER.format(s.getRank()));
                    for ( int j = 0; j < 3; j++ ) {
                        placeString(l3P1Metrics.getYears()[j], pcs, s.getYears()[j] + "");
                    }
                } catch (IOException | IllegalArgumentException | ArabicShapingException ex) { }
            });
        } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { }
    }
    
    private void generateL3P2(PDPage pg, String group, PVStudent student) {
        long nGrp;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            placeMultilineString(l3P2Metrics.getMINISTERY(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
            placeMultilineString(l3P2Metrics.getSCHOOL_INFOS(), pcs, student.getAcademy() + "\n" + student.getDirection() + "\n" + student.getSchool(), ALIGNMENT.RIGHT);
            placeString(l3P2Metrics.getGROUP(), pcs, student.getGroup(), ALIGNMENT.LEFT);
            placeString(l3P2Metrics.getLEVEL(), pcs, student.getLevelFull(), ALIGNMENT.RIGHT);
            placeString(l3P2Metrics.getYEAR(), pcs, student.getYear(), ALIGNMENT.RIGHT);
            
            nGrp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            l3P2Metrics.prepareRows(nGrp);
            l3P2Metrics.prepareDividers().forEach(div -> {
                try {
                    drawNFill(div, pcs);
                } catch (IOException ex) { }
            });
            i = 0;
            STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).forEach(s -> {
                try {
                    l3P2Metrics.prepareRow(i++);
                    placeString(l3P2Metrics.getCode(), pcs, s.getCode());
                    placeString(l3P2Metrics.getName(), pcs, s.getName(), ALIGNMENT.RIGHT);
                    placeNResizeMultilineString(l3P2Metrics.getChoice1(), pcs, s.getChoice1(), ALIGNMENT.RIGHT);
                    placeNResizeMultilineString(l3P2Metrics.getChoice2(), pcs, s.getChoice2(), ALIGNMENT.RIGHT);
                    if ( "".equals(s.getDecision()) && s.getAvg() < treshold ) {
                        if ( s.getYearsSum() == 3 )
                            placeString(l3P2Metrics.getDecision(), pcs,  (s.isFemale() ? "ت" : "ي") + "كرر");
                        if ( s.getYearsSum() == 5 )
                            placeString(l3P2Metrics.getDecision(), pcs,  (s.isFemale() ? "ت" : "ي") + "فصل");
                    }
                    else {
                        placeNResizeMultilineString(l3P2Metrics.getDecision(), pcs, s.getDecision(), ALIGNMENT.RIGHT);
                    }
                } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { }
            });
        } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { }
    }
    
}
