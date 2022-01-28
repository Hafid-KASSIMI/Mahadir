/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision;

import java.awt.Color;
import java.util.ArrayList;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class S1DecPage {
    private final LARectangle ministery, schoolInfos, year, semester, level, group;
    private final LARectangle code, name, gender;
    protected LARectangle average, decision, rank, s1, loc;
    protected LARectangle[] years;
    private final float MAX_HEIGHT, FIRST_Y, LAST_Y, HEIGHT;
    private final LARectangle DIVIDER;
    private float rowHeight, size;

    public S1DecPage() {
        ministery = new LARectangle();
        ministery.setFormat(9f, AvailableFonts.MAGHRIBI);
        ministery.reset(329.079f, 14.173f, 230.520f, 54.786f);
        schoolInfos = new LARectangle();
        schoolInfos.setFormat(9f, AvailableFonts.MAGHRIBI);
        schoolInfos.reset(35.676f, 14.173f, 230.520f, 54.786f);
        semester = new LARectangle();
        semester.setFormat(10f, AvailableFonts.TIMES);
        semester.reset(74.820f, 77.155f, 138.359f, 16.658f);
        year = new LARectangle();
        year.setFormat(12f, AvailableFonts.TIMES);
        year.reset(430.212f, 104.287f, 93.428f, 15.341f);
        level = new LARectangle();
        level.setFormat(12f, AvailableFonts.TIMES);
        level.reset(116.461f, 104.287f, 273.878f, 15.341f);
        group = new LARectangle();
        group.setFormat(12f, AvailableFonts.TAHOMA, true);
        group.reset(15.339f, 104.287f, 73.495f, 15.341f);
        code = new LARectangle();
        code.setFormat(12f, AvailableFonts.TIMES);
        code.setXW(495.819f, 83.996f);
        name = new LARectangle();
        name.setFormat(12f, AvailableFonts.TIMES);
        name.setXW(339.339f, 155.520f);
        gender = new LARectangle();
        gender.setFormat(11f, AvailableFonts.ICOMOON);
        gender.setXW(310.417f, 27.961f);
        
        DIVIDER = new LARectangle();
        DIVIDER.setColor(new Color(0x7F, 0x7F, 0x7F));
        DIVIDER.reset(14.499f, 0, 566.277f, 0.961f);
        MAX_HEIGHT = 20.329f;
        FIRST_Y = 167.721f;
        LAST_Y = 826.76f;
        HEIGHT = LAST_Y - FIRST_Y;
    }

    public LARectangle getMinistery() {
        return ministery;
    }

    public LARectangle getSchoolInfos() {
        return schoolInfos;
    }

    public LARectangle getYear() {
        return year;
    }

    public LARectangle getSemester() {
        return semester;
    }

    public LARectangle getLevel() {
        return level;
    }

    public LARectangle getGroup() {
        return group;
    }

    public LARectangle getCode() {
        return code;
    }

    public LARectangle getName() {
        return name;
    }

    public LARectangle getGender() {
        return gender;
    }

    public LARectangle getAverage() {
        return average;
    }

    public LARectangle getDecision() {
        return decision;
    }

    public LARectangle getRank() {
        return rank;
    }

    public LARectangle[] getYears() {
        return years;
    }
    
    public void prepareRows(long size) {
        float h = ( HEIGHT - DIVIDER.getHeight() * ( size - 1 ) ) / size;
        if ( h > MAX_HEIGHT )
            h = MAX_HEIGHT;
        code.setHeight(h);
        name.setHeight(h);
        gender.setHeight(h);
        s1.setHeight(h);
        rank.setHeight(h);
        decision.setHeight(h);
        for ( int i = 0; i < 3; i++ ) {
            years[i].setHeight(h);
        }
        rowHeight = h + DIVIDER.getHeight();
        this.size = size;
    }
    
    public void prepareRow(int index) {
        float y = FIRST_Y + rowHeight * index;
        code.setY(y);
        name.setY(y);
        gender.setY(y);
        s1.setY(y);
        rank.setY(y);
        decision.setY(y);
        DIVIDER.setY(y - DIVIDER.getHeight());
        for ( int i = 0; i < 3; i++ ) {
            years[i].setY(y);
        }
    }
    
    public ArrayList<LARectangle> prepareDividers() throws CloneNotSupportedException {
        ArrayList<LARectangle> arr = new ArrayList();
        LARectangle tmp;
        if ( size < 31 )
            size = 31;
        for ( int i = 1; i < size; i++ ) {
            tmp = (LARectangle) DIVIDER.clone();
            tmp.setY(FIRST_Y + rowHeight * i - DIVIDER.getHeight());
            arr.add(tmp);
        }
        return arr;
    }

    public LARectangle getDIVIDER() {
        return DIVIDER;
    }

    public LARectangle getS1() {
        return s1;
    }

    public void setS1(LARectangle s1) {
        this.s1 = s1;
    }

    public LARectangle getLoc() {
        return loc;
    }

    public void setLoc(LARectangle loc) {
        this.loc = loc;
    }
    
    public LARectangle getS1Mark(int index) {
        switch ( index ) {
            case 0:
                return s1;
            case 1:
                return loc;
            default:
                return average;
        }
    }
    
}
