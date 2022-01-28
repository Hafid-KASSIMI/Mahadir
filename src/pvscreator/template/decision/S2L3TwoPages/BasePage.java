/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision.S2L3TwoPages;

import java.awt.Color;
import java.util.ArrayList;
import org.sicut.util.pdf.AvailableFonts;
import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public abstract class BasePage {
    protected final LARectangle MINISTERY, SCHOOL_INFOS, YEAR, LEVEL, GROUP;
    protected LARectangle code, name;
    protected float MAX_HEIGHT, FIRST_Y, LAST_Y, HEIGHT;
    protected final LARectangle DIVIDER;
    private float rowHeight, size;

    public BasePage() {
        MINISTERY = new LARectangle();
        MINISTERY.setFormat(9f, AvailableFonts.MAGHRIBI);
        MINISTERY.reset(329.080f, 14.173f, 230.520f, 54.786f);
        SCHOOL_INFOS = new LARectangle();
        SCHOOL_INFOS.setFormat(9f, AvailableFonts.MAGHRIBI);
        SCHOOL_INFOS.reset(35.676f, 14.173f, 230.520f, 54.786f);
        YEAR = new LARectangle();
        YEAR.setFormat(11f, AvailableFonts.TIMES);
        YEAR.reset(376.534f, 104.287f, 150.007f, 15.341f);
        LEVEL = new LARectangle();
        LEVEL.setFormat(11f, AvailableFonts.TIMES);
        LEVEL.reset(120.419f, 104.287f, 210.460f, 15.341f);
        GROUP = new LARectangle();
        GROUP.setFormat(12f, AvailableFonts.TAHOMA, true);
        GROUP.reset(15.339f, 104.287f, 73.495f, 15.341f);
        
        code = new LARectangle();
        name = new LARectangle();
        DIVIDER = new LARectangle();
        DIVIDER.setColor(new Color(0x7F, 0x7F, 0x7F));
        DIVIDER.reset(14.499f, 0, 566.277f, 0.961f);
        MAX_HEIGHT = 20.329f;
        FIRST_Y = 159.260f;
        LAST_Y = 826.76f;
        HEIGHT = LAST_Y - FIRST_Y;
    }
    
    
    public void prepareRows(long size) {
        float h = ( HEIGHT - DIVIDER.getHeight() * ( size - 1 ) ) / size;
        if ( h > MAX_HEIGHT )
            h = MAX_HEIGHT;
        code.setHeight(h);
        name.setHeight(h);
        rowHeight = h + DIVIDER.getHeight();
        this.size = size;
    }
    
    public void prepareRow(int index) {
        float y = FIRST_Y + rowHeight * index;
        code.setY(y);
        name.setY(y);
        DIVIDER.setY(y - DIVIDER.getHeight());
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

    public LARectangle getMINISTERY() {
        return MINISTERY;
    }

    public LARectangle getSCHOOL_INFOS() {
        return SCHOOL_INFOS;
    }

    public LARectangle getYEAR() {
        return YEAR;
    }

    public LARectangle getLEVEL() {
        return LEVEL;
    }

    public LARectangle getGROUP() {
        return GROUP;
    }

    public LARectangle getCode() {
        return code;
    }

    public LARectangle getName() {
        return name;
    }

    public float getRowHeight() {
        return rowHeight;
    }
}
