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
public class S1DecPDFFile extends BasePDFFile {
    
    private S1DecPage l3Metrics, ln3Metrics;
    private final DecimalFormat INTEGER, DECIMAL;
    private final Integer[] attrMaxes = new Integer[]{21, 16, 14, 8, 6, 5};
    private final Integer[] attrMins = new Integer[]{16, 14, 12, 6, 5, 0};
    private final String[] attrs = new String[]{"تنويـــه", "تشجيـــع", "لوحة الشرف", "تنبيـــه", "إنـــذار", "توبيـــخ", ""};
    private int i;
    Map<Integer, Double> map;
    
    public S1DecPDFFile() {
        super();
        l3Metrics = null;
        ln3Metrics = null;
        DECIMAL = new DecimalFormat("00.00");
        DECIMAL.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        INTEGER = new DecimalFormat("00");
        INTEGER.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        tpl = "/pvscreator/template/resources/S1_DEC_TEMPLATE.pdf";
    }
    
    public Boolean generate() {
        Map<String, List<PVStudent>> groups = STUDENTS.stream().collect(Collectors.groupingBy(PVStudent::getGroup));
        reset();
        groups.keySet().stream().sorted((g1, g2) -> Misc.getGroupWeight(g1).compareTo(Misc.getGroupWeight(g2))).forEach(group -> {
            Optional<PVStudent> op = STUDENTS.stream().filter(s -> group.equals(s.getGroup())).findFirst();
            if ( op.isPresent() ) {
                PDPage pg;
                if ( op.get().getIsGrpL3() ) {
                    if ( l3Metrics == null )
                        l3Metrics = new S1L3DecPage();
                    pg = clonePage(1);
                    doGenerate(pg, group, op.get(), l3Metrics);
                }
                else {
                    if ( ln3Metrics == null )
                        ln3Metrics = new S1LN3DecPage();
                    pg = clonePage(0);
                    doGenerate(pg, group, op.get(), ln3Metrics);
                } 
            }
        });
        removePage(1);
        removePage(0);
        return save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("Dec-", "pdf"));
    }
    
    private void doGenerate(PDPage pg, String group, PVStudent student, S1DecPage metrics) {
        long nGrp;
        Iterator<PVStudent> it;
        try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
            placeMultilineString(metrics.getMinistery(), pcs, PREF_BUNDLE.get("KINGDOM-AR").replaceAll("%new_line%", "\n"), ALIGNMENT.LEFT);
            placeMultilineString(metrics.getSchoolInfos(), pcs, student.getAcademy() + "\n" + student.getDirection() + "\n" + student.getSchool(), ALIGNMENT.RIGHT);
            placeMultilineString(metrics.getSemester(), pcs, "الدورة الأولى", ALIGNMENT.RIGHT);
            placeString(metrics.getGroup(), pcs, student.getGroup(), ALIGNMENT.LEFT);
            placeString(metrics.getLevel(), pcs, student.getLevelFull(), ALIGNMENT.RIGHT);
            placeString(metrics.getYear(), pcs, student.getYear(), ALIGNMENT.RIGHT);
            
            nGrp = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).count();
            metrics.prepareRows(nGrp);
            i = 1;
            it = STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).sorted((s1, s2) -> s2.getS1AvgMark().compareTo(s1.getS1AvgMark())).iterator();
            while ( it.hasNext() ) {
                it.next().setRank(i++);
            }
            i = 0;
            STUDENTS.stream().filter(stu -> group.equals(stu.getGroup())).forEach(s -> {
                try {
                    metrics.prepareRow(i++);
                    placeString(metrics.getCode(), pcs, s.getCode());
                    placeString(metrics.getName(), pcs, s.getName(), ALIGNMENT.RIGHT);
                    placeString(metrics.getGender(), pcs, s.isFemale() ? "\ue9dd" : "\ue9dc");
                    for ( int j = 0, n = s.getS1MarksSize(); j < n; j++ ) {
                        placeString(metrics.getS1Mark(j), pcs, DECIMAL.format(s.getS1Mark(j)));
                    }
                    placeString(metrics.getRank(), pcs, INTEGER.format(s.getRank()));
                    resizeNplaceString(metrics.getDecision(), pcs, "".equals(s.getDecision()) ? attrs[getAttrIndex(s.getS1AvgMark())] : s.getDecision());
                    for ( int j = 0; j < 3; j++ ) {
                        placeString(metrics.getYears()[j], pcs, s.getYears()[j] + "");
                    }
                } catch (IOException | IllegalArgumentException | CloneNotSupportedException | ArabicShapingException ex) { }
            });
            metrics.prepareDividers().forEach(div -> {
                try {
                    drawNFill(div, pcs);
                } catch (IOException ex) { }
            });
        } catch (IOException | IllegalArgumentException | ArabicShapingException | CloneNotSupportedException ex) { }
    }
    
    private int getAttrIndex(double mark) {
        int x = 0;
        for ( ; x < attrMaxes.length; x++ ) {
            if ( mark < attrMaxes[x] && mark >= attrMins[x] )
                return x;
        }
        return x;
    }
    
}
