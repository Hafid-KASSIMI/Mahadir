/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision;

import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class S2L3DecPage extends S2DecPage {
    private final LARectangle loc, reg, choice1, choice2, semesterNYear;

    public S2L3DecPage() {
        super();
        MINISTERY.reset(645.881f, 14.173f, 181.836f, 38.853f);
        SCHOOL_INFOS.reset(421.425f, 14.173f, 181.836f, 38.853f);
        LEVEL.reset(128.686f, 37.685f, 217.894f, 15.341f);
        GROUP.reset(14.173f, 34.420f, 85.854f, 18.606f);
        
        DIVIDER.setWidth(813.543f);
        MAX_HEIGHT = 14.922f;
        FIRST_Y = 84.060f;
        LAST_Y = 580.141f;
        HEIGHT = LAST_Y - FIRST_Y;
        
        semesterNYear = new LARectangle();
        semesterNYear.setFormat(LEVEL.getFontSize(), LEVEL.getFont());
        semesterNYear.reset(14.173f, 14.831f, 158.849f, 16.658f);
        
        code = new LARectangle();
        code.setFormat(10f, AvailableFonts.TIMES);
        code.setXW(767.660f, 59.096f);
        name = new LARectangle();
        name.setFormat(11f, AvailableFonts.TIMES);
        name.setXW(651.740f, 114.959f);
        gender = new LARectangle();
        gender.setFormat(9f, AvailableFonts.ICOMOON);
        gender.setXW(640.554f, 10.225f);
        
        float tmp = -19.342f;
        years = new LARectangle[3];
        for ( int i = 0; i < 3; i++ ) {
            years[i] = new LARectangle();
            years[i].setFormat(10f, AvailableFonts.TIMES);
            years[i].setXW(621.211f + tmp * i, 18.381f);
        }
        
        s1 = new LARectangle();
        s1.setFormat(10f, AvailableFonts.TIMES);
        s1.setXW(551.984f, 29.582f);
        
        s2 = new LARectangle();
        s2.setFormat(10f, AvailableFonts.TIMES);
        s2.setXW(521.441f, 29.582f);
        
        loc = new LARectangle();
        loc.setFormat(10f, AvailableFonts.TIMES);
        loc.setXW(490.898f, 29.582f);
        
        reg = new LARectangle();
        reg.setFormat(10f, AvailableFonts.TIMES);
        reg.setXW(460.356f, 29.582f);
        
        average = new LARectangle();
        average.setFormat(10f, AvailableFonts.TIMES);
        average.setXW(429.813f, 29.582f);
        
        choice1 = new LARectangle();
        choice1.setFormat(10f, AvailableFonts.TIMES);
        choice1.setXW(291.585f, 137.267f);
        
        choice2 = new LARectangle();
        choice2.setFormat(10f, AvailableFonts.TIMES);
        choice2.setXW(153.358f, 137.267f);
        
        decision = new LARectangle();
        decision.setFormat(10f, AvailableFonts.TIMES);
        decision.setXW(15.130f, 137.267f);
        
    }
    
    @Override
    public void prepareRows(long size) {
        super.prepareRows(size);
        loc.setHeight(code.getHeight());
        reg.setHeight(code.getHeight());
        choice1.setHeight(code.getHeight());
        choice2.setHeight(code.getHeight());
    }
    
    @Override
    public void prepareRow(int index) {
        super.prepareRow(index);
        loc.setY(code.getY());
        reg.setY(code.getY());
        choice1.setY(code.getY());
        choice2.setY(code.getY());
    }

    public LARectangle getLoc() {
        return loc;
    }

    public LARectangle getReg() {
        return reg;
    }

    public LARectangle getChoice1() {
        return choice1;
    }

    public LARectangle getChoice2() {
        return choice2;
    }

    public LARectangle getSemesterNYear() {
        return semesterNYear;
    }
    
}
