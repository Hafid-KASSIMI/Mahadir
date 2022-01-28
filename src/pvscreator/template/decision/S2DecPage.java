/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator.template.decision;

import org.sicut.util.pdf.LARectangle;

/**
 *
 * @author Sicut
 */
public class S2DecPage extends DecisionPage {
    protected LARectangle average, s1, s2;

    public S2DecPage() {
        super();
    }
    
    @Override
    public void prepareRows(long size) {
        super.prepareRows(size);
        s1.setHeight(code.getHeight());
        s2.setHeight(code.getHeight());
        average.setHeight(code.getHeight());
    }
    
    @Override
    public void prepareRow(int index) {
        super.prepareRow(index);
        s1.setY(code.getY());
        s2.setY(code.getY());
        average.setY(code.getY());
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
    
}
