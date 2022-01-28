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
public class S1PVPDFFile extends BasePDFFile {
    
    private final S1PVPage metrics;
    private final DecimalFormat DECIMAL, INTEGER, PERCENTAGE;
    private final String[] genders = new String[]{"ث", "ذ", ""};
    private final Integer[] attrMaxes = new Integer[]{21, 16, 14, 8, 6, 5};
    private final Integer[] attrMins = new Integer[]{16, 14, 12, 6, 5, 0};
    private final Integer[] cntrlMaxes = new Integer[]{21, 12, 10};
    private final Integer[] cntrlMins = new Integer[]{12, 10, 0};
    private final String PATTERN;
    private int i;
    Map<Integer, Double> map;
    
    public S1PVPDFFile() {
        super();
        metrics = new S1PVPage();
        INTEGER = new DecimalFormat();
        PERCENTAGE = new DecimalFormat();
        DECIMAL = new DecimalFormat();
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        INTEGER.applyPattern("00");
        PERCENTAGE.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        PERCENTAGE.applyPattern("00.##%");
        DECIMAL.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        DECIMAL.applyPattern("00.00");
        PATTERN = "%s من %s";
        tpl = "/pvscreator/template/resources/S1_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        Map<String, List<PVStudent>> groups = STUDENTS.stream().collect(Collectors.groupingBy(PVStudent::getGroup));
        reset();
        groups.keySet().stream().sorted((g1, g2) -> Misc.getGroupWeight(g1).compareTo(Misc.getGroupWeight(g2))).forEach(group -> {
            Optional<PVStudent> op = STUDENTS.stream().filter(s -> group.equals(s.getGroup())).findFirst();
            if ( op.isPresent() ) {
                PDPage pg;
                if ( op.get().getIsGrpL3() ) {
                    pg = clonePage(1);
                    generateL3(pg, group, op.get());
                }
                else {
                    pg = clonePage(0);
                    generateLN3(pg, group, op.get());
                }
                try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    placeMultilineString(metrics.getMinistery(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
                    placeMultilineString(metrics.getSchoolInfos(), pcs, op.get().getAcademy() + "\n" + op.get().getDirection() + "\n" + op.get().getSchool(), ALIGNMENT.RIGHT);
                    placeString(metrics.getYear(), pcs, op.get().getYear(), ALIGNMENT.RIGHT);
                    placeString(metrics.getSemester(), pcs, "الدورة الأولى", ALIGNMENT.RIGHT);
                    placeString(metrics.getPresident(), pcs, PREF_BUNDLE.get("PRESIDENT_NAME"));
                    placeBidirectionalString(metrics.getCouncilDate(), pcs, LocalDate.parse(PREF_BUNDLE.get("COUNCIL_DATE")).format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")), ALIGNMENT.RIGHT);
                    placeString(metrics.getCouncilTime(), pcs, PREF_BUNDLE.get("COUNCIL_TIME"), ALIGNMENT.RIGHT);
                    placeString(metrics.getGroup(), pcs, op.get().getGroup(), ALIGNMENT.RIGHT);
                    placeString(metrics.getLevelName(), pcs, op.get().getLevelAbrev());
                    for ( i = 0; i < 14; i++ ) {
                        placeMultilineString(metrics.getMatter()[i], pcs, prepareMutiline(metrics.getMatter()[i], PREF_BUNDLE.get(Misc.getLevelWeight(group) + "MATTER_" + i)));
                    }
                } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { } 
            }
        });
        removePage(1);
        removePage(0);
        return save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("PV-", "pdf"));
    }
    
    private void generateLN3(PDPage pg, String group, PVStudent student) {
        long tmp, tmp1, n;
        Iterator<PVStudent> it;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            n = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            for ( i = 0; i < 3; i++ ) {
                placeString(metrics.getGrp1stMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getGender().contains(genders[i])).max((stu1, stu2) -> stu1.getS1().compareTo(stu2.getS1())).get().getS1()));
                placeString(metrics.getGrpLstMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getGender().contains(genders[i])).min((stu1, stu2) -> stu1.getS1().compareTo(stu2.getS1())).get().getS1()));
                placeString(metrics.getGrpAvgMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getGender().contains(genders[i])).collect(Collectors.averagingDouble(stu -> stu.getS1()))));
                tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getGender().contains(genders[i]) && stu.getS1() >= 10).count();
                tmp1 = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getGender().contains(genders[i])).count();
                placeBidirectionalString(metrics.getGrpGotAvgN()[i], pcs, String.format(PATTERN, INTEGER.format(tmp), INTEGER.format(tmp1)));
                placeString(metrics.getGrpGotAvgP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / tmp1));
                tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getS1() >= cntrlMins[i] && stu.getS1() < cntrlMaxes[i]).count();
                placeString(metrics.getCntrlN()[i], pcs, INTEGER.format(tmp));
                placeString(metrics.getCntrlP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / n));
                placeString(metrics.getLev1stMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getGender().contains(genders[i])).max((stu1, stu2) -> stu1.getS1().compareTo(stu2.getS1())).get().getS1()));
                placeString(metrics.getLevLstMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getGender().contains(genders[i])).min((stu1, stu2) -> stu1.getS1().compareTo(stu2.getS1())).get().getS1()));
                placeString(metrics.getLevAvgMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getGender().contains(genders[i])).collect(Collectors.averagingDouble(stu -> stu.getS1()))));
                tmp = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getGender().contains(genders[i]) && stu.getS1() >= 10).count();
                tmp1 = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getGender().contains(genders[i])).count();
                placeBidirectionalString(metrics.getLevGotAvgN()[i], pcs, String.format(PATTERN, INTEGER.format(tmp), INTEGER.format(tmp1)));
                placeString(metrics.getLevGotAvgP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / tmp1));
            }

            i = 0;
            it = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).sorted((stu1, stu2) -> stu2.getS1().compareTo(stu1.getS1())).limit(3).iterator();
            while ( it.hasNext() ) {
                PVStudent stu = it.next();
                placeString(metrics.getGrpBestName()[i], pcs, stu.getName());
                placeString(metrics.getGrpBestMark()[i++], pcs, DECIMAL.format(stu.getS1()));
            }

            for ( i = 0; i < 6; i++ ) {
                tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getS1() >= attrMins[i] && stu.getS1() < attrMaxes[i]).count();
                placeString(metrics.getAttrN()[i], pcs, INTEGER.format(tmp));
                placeString(metrics.getAttrP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / n));
            }

            i = 0;            
            it = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).sorted((stu1, stu2) -> stu2.getS1().compareTo(stu1.getS1())).limit(3).iterator();
            while ( it.hasNext() ) {
                PVStudent stu = it.next();
                placeString(metrics.getLevBestName()[i], pcs, stu.getName());
                placeString(metrics.getLevBestMark()[i++], pcs, DECIMAL.format(stu.getS1()));
            }

            i = 0;
            map = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).collect(Collectors.groupingBy(PVStudent::getGroupIndex, Collectors.averagingDouble(PVStudent::getS1)));
            map.keySet().stream().sorted().forEach(key -> {
                try {
                    placeString(metrics.getGrp()[i], pcs, key + "");
                    placeString(metrics.getGrpAvg()[i++], pcs, DECIMAL.format(map.get(key)));
                } catch (IOException | ArabicShapingException ex) { }
            });

        } catch (IOException | IllegalArgumentException | ArabicShapingException ex) { } 
    }
    
    private void generateL3(PDPage pg, String group, PVStudent student) {
        long tmp, nGrp, nLev;
        Iterator<PVStudent> it;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            nGrp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            nLev = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).count();
            for ( i = 0; i < 3; i++ ) {
                placeString(metrics.getGrp1stMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).max((stu1, stu2) -> stu1.getS1Mark(i).compareTo(stu2.getS1Mark(i))).get().getS1Mark(i)));
                placeString(metrics.getGrpLstMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).min((stu1, stu2) -> stu1.getS1Mark(i).compareTo(stu2.getS1Mark(i))).get().getS1Mark(i)));
                placeString(metrics.getGrpAvgMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).collect(Collectors.averagingDouble(stu -> stu.getS1Mark(i)))));
                tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getS1Mark(i) >= 10).count();
                placeBidirectionalString(metrics.getGrpGotAvgN()[i], pcs, String.format(PATTERN, INTEGER.format(tmp), INTEGER.format(nGrp)));
                placeString(metrics.getGrpGotAvgP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / nGrp));
                tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getS13A() >= cntrlMins[i] && stu.getS13A() < cntrlMaxes[i]).count();
                placeString(metrics.getCntrlN()[i], pcs, INTEGER.format(tmp));
                placeString(metrics.getCntrlP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / nGrp));
                placeString(metrics.getLev1stMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).max((stu1, stu2) -> stu1.getS1Mark(i).compareTo(stu2.getS1Mark(i))).get().getS1Mark(i)));
                placeString(metrics.getLevLstMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).min((stu1, stu2) -> stu1.getS1Mark(i).compareTo(stu2.getS1Mark(i))).get().getS1Mark(i)));
                placeString(metrics.getLevAvgMark()[i], pcs, DECIMAL.format(STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).collect(Collectors.averagingDouble(stu -> stu.getS1Mark(i)))));
                tmp = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev()) && stu.getS1Mark(i) >= 10).count();
                placeBidirectionalString(metrics.getLevGotAvgN()[i], pcs, String.format(PATTERN, INTEGER.format(tmp), INTEGER.format(nLev)));
                placeString(metrics.getLevGotAvgP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / nLev));
            }

            i = 0;
            it = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).sorted((stu1, stu2) -> stu2.getS13A().compareTo(stu1.getS13A())).limit(3).iterator();
            while ( it.hasNext() ) {
                PVStudent stu = it.next();
                placeString(metrics.getGrpBestName()[i], pcs, stu.getName());
                placeString(metrics.getGrpBestMark()[i++], pcs, DECIMAL.format(stu.getS13A()));
            }

            for ( i = 0; i < 6; i++ ) {
                tmp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup()) && stu.getS13A() >= attrMins[i] && stu.getS13A() < attrMaxes[i]).count();
                placeString(metrics.getAttrN()[i], pcs, INTEGER.format(tmp));
                placeString(metrics.getAttrP()[i], pcs, PERCENTAGE.format(tmp * 1.0 / nGrp));
            }

            i = 0;
            it = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).sorted((stu1, stu2) -> stu2.getS13A().compareTo(stu1.getS13A())).limit(3).iterator();
            while ( it.hasNext() ) {
                PVStudent stu = it.next();
                placeString(metrics.getLevBestName()[i], pcs, stu.getName());
                placeString(metrics.getLevBestMark()[i++], pcs, DECIMAL.format(stu.getS13A()));
            }

            i = 0;
            map = STUDENTS.stream().filter(stu -> student.getLevelAbrev().equals(stu.getLevelAbrev())).collect(Collectors.groupingBy(PVStudent::getGroupIndex, Collectors.averagingDouble(PVStudent::getS13A)));
            map.keySet().stream().sorted().forEach(key -> {
                try {
                    placeString(metrics.getGrp()[i], pcs, key + "");
                    placeString(metrics.getGrpAvg()[i++], pcs, DECIMAL.format(map.get(key)));
                } catch (IOException | ArabicShapingException ex) { }
            });

        } catch (IOException | IllegalArgumentException | ArabicShapingException ex) { } 
    }
    
}
