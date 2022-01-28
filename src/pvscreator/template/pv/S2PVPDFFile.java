/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.pv;

import com.ibm.icu.text.ArabicShapingException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import static pvscreator.Settings.PREF_BUNDLE;
import static pvscreator.Settings.STUDENTS;
import pvscreator.massar.PVStudent;

/**
 *
 * @author Sicut
 */
public class S2PVPDFFile extends BasePDFFile {
    
    private S2PVPage l3Metrics, ln3Metrics;
    private final DecimalFormat INTEGER, DECIMAL, PERCENTAGE;
    private final String PATTERN1, PATTERN2;
    private final Integer[] attrMaxes = new Integer[]{21, 16, 14, 8, 6, 5};
    private final Integer[] attrMins = new Integer[]{16, 14, 12, 6, 5, 0};
    private final Integer[] cntrlMaxes = new Integer[]{21, 12, 10};
    private final Integer[] cntrlMins = new Integer[]{12, 10, 0};
    private int i;
    Map<Integer, Double> map;
    private double treshold;
    
    public S2PVPDFFile() {
        super();
        l3Metrics = null;
        ln3Metrics = null;
        DECIMAL = new DecimalFormat("00.00");
        DECIMAL.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        PERCENTAGE = new DecimalFormat("00.##%");
        PERCENTAGE.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        PATTERN1 = "%s من %s | %s";
        PATTERN2 = "%s | %s";
        tpl = "/pvscreator/template/resources/S2_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        Map<String, List<PVStudent>> groups = STUDENTS.stream().collect(Collectors.groupingBy(PVStudent::getGroup));
        reset();
        treshold = Double.valueOf(PREF_BUNDLE.get("ATABA_VALUE"));
        groups.keySet().stream().sorted((g1, g2) -> Misc.getGroupWeight(g1).compareTo(Misc.getGroupWeight(g2))).forEach(group -> {
            Optional<PVStudent> op = STUDENTS.stream().filter(s -> group.equals(s.getGroup())).findFirst();
            if ( op.isPresent() ) {
                PDPage pg;
                if ( op.get().getIsGrpL3() ) {
                    if ( l3Metrics == null )
                        l3Metrics = new S2L3PVPage();
                    pg = clonePage(1);
                    doGenerate(pg, group, op.get(), l3Metrics);
                }
                else {
                    if ( ln3Metrics == null )
                        ln3Metrics = new S2LN3PVPage();
                    pg = clonePage(0);
                    doGenerate(pg, group, op.get(), ln3Metrics);
                } 
            }
        });
        removePage(1);
        removePage(0);
        return save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("PV-", "pdf"));
    }
    
    private void doGenerate(PDPage pg, String group, PVStudent student, S2PVPage metrics) {
        double tmp, nGrp, nLev, fired;
        Iterator<PVStudent> it;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            placeMultilineString(metrics.getMinistery(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
            placeMultilineString(metrics.getSchoolInfos(), pcs, student.getAcademy() + "\n" + student.getDirection() + "\n" + student.getSchool(), ALIGNMENT.RIGHT);
            placeMultilineString(metrics.getYearNSemester(), pcs, student.getYear() + "\n" + "الدورة الثانية", ALIGNMENT.RIGHT);
            placeString(metrics.getPresident(), pcs, PREF_BUNDLE.get("PRESIDENT_NAME"));
            placeBidirectionalString(metrics.getCouncilDate(), pcs, LocalDate.parse(PREF_BUNDLE.get("COUNCIL_DATE")).format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")), ALIGNMENT.RIGHT);
            placeString(metrics.getCouncilTime(), pcs, PREF_BUNDLE.get("COUNCIL_TIME"), ALIGNMENT.RIGHT);
            placeString(metrics.getGroup(), pcs, student.getGroup(), ALIGNMENT.RIGHT);
            placeString(metrics.getLevelName(), pcs, student.getLevelAbrev());
            for ( i = 0; i < 14; i++ ) {
                placeMultilineString(metrics.getMatter()[i], pcs, prepareMutiline(metrics.getMatter()[i], PREF_BUNDLE.get(Misc.getLevelWeight(group) + "MATTER_" + i)));
            }
            
            nGrp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            nLev = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).count();
            i = 0;
            for ( int n = metrics.getMarksSize(); i < n; i++ ) {
                placeString(metrics.getGrp1stMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(s -> group.equals(s.getGroup())).max((s1, s2) -> s1.getMark(i).compareTo(s2.getMark(i))).get().getMark(i)));
                placeString(metrics.getGrpLstMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(s -> group.equals(s.getGroup())).min((s1, s2) -> s1.getMark(i).compareTo(s2.getMark(i))).get().getMark(i)));
                placeString(metrics.getGrpAvgMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(s -> group.equals(s.getGroup())).collect(Collectors.averagingDouble(s -> s.getMark(i)))));
                tmp = STUDENTS.stream().filter(s -> group.equals(s.getGroup()) && s.getMark(i) >= 10).count();
                placeBidirectionalString(metrics.getGrpGotAvgN()[i], pcs, String.format(PATTERN1, INTEGER.format(tmp), INTEGER.format(nGrp), PERCENTAGE.format(tmp / nGrp)));
                
                placeString(metrics.getLev1stMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(s -> student.getLevelAbrev().equals(s.getLevelAbrev())).max((s1, s2) -> s1.getMark(i).compareTo(s2.getMark(i))).get().getMark(i)));
                placeString(metrics.getLevLstMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(s -> student.getLevelAbrev().equals(s.getLevelAbrev())).min((s1, s2) -> s1.getMark(i).compareTo(s2.getMark(i))).get().getMark(i)));
                placeString(metrics.getLevAvgMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(s -> student.getLevelAbrev().equals(s.getLevelAbrev())).collect(Collectors.averagingDouble(s -> s.getMark(i)))));
                tmp = STUDENTS.stream().filter(s -> student.getLevelAbrev().equals(s.getLevelAbrev()) && s.getMark(i) >= 10).count();
                placeBidirectionalString(metrics.getLevGotAvgN()[i], pcs, String.format(PATTERN1, INTEGER.format(tmp), INTEGER.format(nLev), PERCENTAGE.format(tmp / nLev)));
            }
            
            for ( i = 0; i < 3; i++) {
                tmp = STUDENTS.stream().filter(s -> group.equals(s.getGroup()) && s.getAvg() >= cntrlMins[i] && s.getAvg() < cntrlMaxes[i]).count();
                placeBidirectionalString(metrics.getGrpCntrl()[i], pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nGrp)));
                
                tmp = STUDENTS.stream().filter(s -> student.getLevelAbrev().equals(s.getLevelAbrev()) && s.getAvg() >= cntrlMins[i] && s.getAvg() < cntrlMaxes[i]).count();
                placeBidirectionalString(metrics.getLevCntrl()[i], pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nLev)));
            }
            
            i = 0;
            it = STUDENTS.stream().filter(s -> group.equals(s.getGroup())).sorted((s1, s2) -> s2.getAvg().compareTo(s1.getAvg())).limit(metrics.getBestsSize()).iterator();
            while ( it.hasNext() ) {
                PVStudent stu = it.next();
                placeString(metrics.getGrpBestName()[i], pcs, stu.getName());
                placeString(metrics.getGrpBestMark()[i++], pcs, DECIMAL.format(stu.getAvg()));
            }
            
            for ( i = 0; i < 6; i++ ) {
                tmp = STUDENTS.stream().filter(s -> group.equals(s.getGroup()) && s.getAvg() >= attrMins[i] && s.getAvg() < attrMaxes[i]).count();
                placeBidirectionalString(metrics.getGrpAttr()[i], pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nGrp)));
                
                tmp = STUDENTS.stream().filter(s -> student.getLevelAbrev().equals(s.getLevelAbrev()) && s.getAvg() >= attrMins[i] && s.getAvg() < attrMaxes[i]).count();
                placeBidirectionalString(metrics.getLevAttr()[i], pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nLev)));
            }

            i = 0;
            it = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).sorted((stu1, stu2) -> stu2.getAvg().compareTo(stu1.getAvg())).limit(metrics.getBestsSize()).iterator();
            while ( it.hasNext() ) {
                PVStudent stu = it.next();
                placeString(metrics.getLevBestName()[i], pcs, stu.getName());
                placeString(metrics.getLevBestMark()[i++], pcs, DECIMAL.format(stu.getAvg()));
            }
            placeString(metrics.getThreshold(), pcs, DECIMAL.format(treshold));
            
            tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getAvg() >= treshold).count();
            placeBidirectionalString(metrics.getGrpSucceeded(), pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nGrp)));
            tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getAvg() < treshold).count();
            placeBidirectionalString(metrics.getGrpUnsucceeded(), pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nGrp)));
            fired = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && ( stu.getDecision().contains("فصل") || ( stu.getIsGrpL3() && stu.getYearsSum() == 5) )).count();
            placeBidirectionalString(metrics.getGrpFired(), pcs, String.format(PATTERN2, INTEGER.format(fired), PERCENTAGE.format(fired / nGrp)));
            tmp -= fired;
            placeBidirectionalString(metrics.getGrpRepeaters(), pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nGrp)));
            
            tmp = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getAvg() >= treshold).count();
            placeBidirectionalString(metrics.getLevSucceeded(), pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nLev)));
            tmp = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getAvg() < treshold).count();
            placeBidirectionalString(metrics.getLevUnsucceeded(), pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nLev)));
            fired = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getDecision().contains("فصل") || ( stu.getIsGrpL3() && stu.getYearsSum() == 5)).count();
            placeBidirectionalString(metrics.getLevFired(), pcs, String.format(PATTERN2, INTEGER.format(fired), PERCENTAGE.format(fired / nLev)));
            tmp -= fired;
            placeBidirectionalString(metrics.getLevRepeaters(), pcs, String.format(PATTERN2, INTEGER.format(tmp), PERCENTAGE.format(tmp / nLev)));
            i = 0;
            map = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).collect(Collectors.groupingBy(PVStudent::getGroupIndex, Collectors.averagingDouble(PVStudent::getAvg)));
            map.keySet().stream().sorted().forEach(key -> {
                try {
                    placeString(metrics.getGrp()[i], pcs, key + "");
                    placeString(metrics.getGrpAvg()[i++], pcs, DECIMAL.format(map.get(key)));
                } catch (IOException | ArabicShapingException ex) { }
            });
        } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { }
    }
    
    public double getTreshold() {
        return treshold;
    }

    public void setTreshold(double treshold) {
        this.treshold = treshold;
    }
    
}
