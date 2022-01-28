/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision.S2L3TwoPages;

import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class Page1 extends BasePage {
    protected final LARectangle gender, average, s1, s2, loc, reg, rank;
    protected final LARectangle[] years;

    public Page1() {
        super();
        code.setFormat(11f, AvailableFonts.TIMES);
        name.setFormat(12f, AvailableFonts.TIMES);
        code.setXW(514.720f, 65.096f);
        name.setXW(357.578f, 156.181f);
        gender = new LARectangle();
        gender.setFormat(12f, AvailableFonts.ICOMOON);
        gender.setXW(328.278f, 28.339f);
        float tmp = -24.8f;
        years = new LARectangle[3];
        for ( int i = 0; i < 3; i++ ) {
            years[i] = new LARectangle();
            years[i].setFormat(12f, AvailableFonts.TIMES);
            years[i].setXW(303.478f + tmp * i, 23.839f);
        }
        s1 = new LARectangle();
        s1.setFormat(12f, AvailableFonts.TIMES);
        s1.setXW(211.642f, 41.275f);
        s2 = new LARectangle();
        s2.setFormat(12f, AvailableFonts.TIMES);
        s2.setXW(169.406f, 41.275f);
        loc = new LARectangle();
        loc.setFormat(12f, AvailableFonts.TIMES);
        loc.setXW(127.169f, 41.275f);
        reg = new LARectangle();
        reg.setFormat(12f, AvailableFonts.TIMES);
        reg.setXW(84.933f, 41.275f);
        average = new LARectangle();
        average.setFormat(12f, AvailableFonts.TIMES);
        average.setXW(42.696f, 41.275f);
        rank = new LARectangle();
        rank.setFormat(12f, AvailableFonts.TIMES);
        rank.setXW(15.460f, 26.275f);
    }
    
    @Override
    public void prepareRows(long size) {
        super.prepareRows(size);
        s1.setHeight(code.getHeight());
        s2.setHeight(code.getHeight());
        loc.setHeight(code.getHeight());
        reg.setHeight(code.getHeight());
        average.setHeight(code.getHeight());
        rank.setHeight(code.getHeight());
        gender.setHeight(code.getHeight());
        for ( int i = 0; i < 3; i++ ) {
            years[i].setHeight(code.getHeight());
        }
    }
    
    @Override
    public void prepareRow(int index) {
        super.prepareRow(index);
        s1.setY(code.getY());
        s2.setY(code.getY());
        loc.setY(code.getY());
        reg.setY(code.getY());
        average.setY(code.getY());
        rank.setY(code.getY());
        gender.setY(code.getY());
        for ( int i = 0; i < 3; i++ ) {
            years[i].setY(code.getY());
        }
    }
    
    public LARectangle getS1() {
        return s1;
    }

    public LARectangle getS2() {
        return s2;
    }

    public LARectangle getAverage() {
        return average;
    }

    public LARectangle getGender() {
        return gender;
    }

    public LARectangle getLoc() {
        return loc;
    }

    public LARectangle getReg() {
        return reg;
    }

    public LARectangle getRank() {
        return rank;
    }

    public LARectangle[] getYears() {
        return years;
    }
    
}
