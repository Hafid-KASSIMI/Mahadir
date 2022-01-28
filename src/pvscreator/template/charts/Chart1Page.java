/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.charts;


import java.awt.Color;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class Chart1Page {
    protected final LARectangle SCHOOL_INFOS, LEVEL;
    protected final LARectangle[] pies, labels;
    protected final Color UNDER, UPPER;
    private final int SIZE = 12;

    public Chart1Page() {
        float vGap, hGap;
        LEVEL = new LARectangle();
        LEVEL.setFormat(20f, AvailableFonts.TRAD_AR);
        LEVEL.reset(155.829f, 14.173f, 411.100f, 19.136f);
        SCHOOL_INFOS = new LARectangle();
        SCHOOL_INFOS.setFormat(10f, AvailableFonts.TRAD_AR);
        SCHOOL_INFOS.reset(155.829f, 33.309f, 411.100f, 13.276f);
        
        pies = new LARectangle[12];
        labels = new LARectangle[12];
        vGap = 196.754f;
        hGap = 184.291f;
        for ( int i = 0, div = 0, mod = 0; i < 12; i++, div = i / 3, mod = i % 3 ) {
            pies[i] = new LARectangle();
            pies[i].reset(396.929f - hGap * mod, 46.585f + vGap * div, 170f, 170f);
            labels[i] = new LARectangle();
            labels[i].setFormat(14f, AvailableFonts.TIMES);
            labels[i].reset(396.929f - hGap * mod, 216.585f + vGap * div, 170f, 20.870f);
        }
        
        UPPER = new Color(0x9E9E9E);
        UNDER = new Color(0xF1F1F1);
    }

    public LARectangle getSCHOOL_INFOS() {
        return SCHOOL_INFOS;
    }

    public LARectangle getLEVEL() {
        return LEVEL;
    }

    public LARectangle[] getPies() {
        return pies;
    }

    public LARectangle[] getLabels() {
        return labels;
    }

    public Color getUNDERColor() {
        return UNDER;
    }

    public Color getUPPERColor() {
        return UPPER;
    }
    
}
