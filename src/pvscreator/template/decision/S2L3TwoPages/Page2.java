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
public class Page2 extends BasePage {
    protected final LARectangle choice1, choice2, decision;

    public Page2() {
        super();
        code.setFormat(10f, AvailableFonts.TIMES);
        name.setFormat(11f, AvailableFonts.TIMES);
        code.setXW(520.720f, 59.096f);
        name.setXW(404.800f, 114.959f);
        choice1 = new LARectangle();
        choice1.setFormat(12f, AvailableFonts.TIMES);
        choice1.setXW(275.020f, 128.819f);
        choice2 = new LARectangle();
        choice2.setFormat(12f, AvailableFonts.TIMES);
        choice2.setXW(145.240f, 128.819f);
        decision = new LARectangle();
        decision.setFormat(12f, AvailableFonts.TIMES);
        decision.setXW(15.460f, 128.819f);
    }
    
    @Override
    public void prepareRows(long size) {
        super.prepareRows(size);
        choice1.setHeight(code.getHeight());
        choice2.setHeight(code.getHeight());
        decision.setHeight(code.getHeight());
    }
    
    @Override
    public void prepareRow(int index) {
        super.prepareRow(index);
        choice1.setY(code.getY());
        choice2.setY(code.getY());
        decision.setY(code.getY());
    }

    public LARectangle getChoice1() {
        return choice1;
    }

    public LARectangle getChoice2() {
        return choice2;
    }

    public LARectangle getDecision() {
        return decision;
    }
}
