/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.charts;

import com.ibm.icu.text.ArabicShapingException;
import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
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
public class Chart1PDFFile extends BasePDFFile {
    
    private final Chart1Page METRICS;
    private int i;
    private final DecimalFormat PERCENTAGE;
    Map<Integer, Double> map;
    private PDPage pg;
    private final ArrayList<Long> VALUES = new ArrayList();
    private final ArrayList<Color> COLORS = new ArrayList();
    private double treshold;
    
    public Chart1PDFFile() {
        super();
        METRICS = new Chart1Page();
        VALUES.add(0l);
        VALUES.add(0l);
        COLORS.add(METRICS.getUPPERColor());
        COLORS.add(METRICS.getUNDERColor());
        PERCENTAGE = new DecimalFormat();
        PERCENTAGE.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.FRANCE));
        PERCENTAGE.applyPattern("00.##%");
        tpl = "/pvscreator/template/resources/CHART1_TEMPLATE.pdf";
    }
    
    public Boolean generate(int semester) {
        reset();
        treshold = Double.valueOf(PREF_BUNDLE.get("ATABA_VALUE"));
        STUDENTS.stream().collect(Collectors.groupingBy(PVStudent::getLevelAbrev)).keySet().stream().sorted((l1, l2) -> Misc.getLevelWeight(l1).compareTo(Misc.getLevelWeight(l2))).forEach(level -> {
            doGenerate(level, semester);
        });
        removePage(0);
        return save(PREF_BUNDLE.get("OUTPUT_DIR") + generateFileName("Gra-", "pdf"));
    }
    
    private void doGenerate(String level, int semester) {
        i = 0;
        STUDENTS.stream().filter(stu -> level.compareTo(stu.getLevelAbrev()) == 0).collect(Collectors.groupingBy(PVStudent::getGroupIndex)).forEach((index, lst) -> {
            if ( semester == 0 ) {
                VALUES.set(1, lst.stream().filter(stu -> stu.getS1AvgMark() < 10).count());
            }
            else {
                VALUES.set(1, lst.stream().filter(stu -> stu.getAvg() < treshold).count());
            }
            VALUES.set(0, lst.size() - VALUES.get(1));
            if ( i % 12 == 0 ) {
                pg = clonePage(0);
                try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    placeString(METRICS.getLEVEL(), pcs, lst.get(0).getLevelFull(), ALIGNMENT.RIGHT);
                    placeBidirectionalString(METRICS.getSCHOOL_INFOS(), pcs, lst.get(0).getSchool() + " - " + lst.get(0).getYear() + " - " + (semester == 0 ? "الدورة الأولى" : "الدورة الثانية"), ALIGNMENT.RIGHT);
                } catch (IOException | IllegalArgumentException | ArabicShapingException ex) { } 
                i = 0;
            }
            try (PDPageContentStream pcs = new PDPageContentStream(doc, pg, PDPageContentStream.AppendMode.APPEND, true, true)) {
                placeString(METRICS.getLabels()[i], pcs, lst.get(0).getGroup() + " [" + PERCENTAGE.format(VALUES.get(0) * 1.0 / lst.size()) + "]");
                drawPieChart(pcs, METRICS.getPies()[i], VALUES, COLORS);
            } catch (IOException | IllegalArgumentException | ArabicShapingException ex) { } 
            i++;
        });
    }    
}
