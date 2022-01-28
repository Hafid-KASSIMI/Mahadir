/*
 * Copyright (C) 2020 Sicut
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package pvscreator.massar;


import java.io.*;
import java.util.ArrayList;
import java.util.function.BiFunction;
import org.apache.poi.hssf.util.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sicut.util.Misc;

/**
 *
 * @author Surfer
 * @updated by Sicut
 */
public final class PV {
    private final String codeClmn, dataSheetName, nameClmn, genderClmn, s1Clmn, s2Clmn, locClmn, regClmn, choice1Clmn, choice2Clmn;
    private String decisionClmn, avgClmn;
    private final String[] yearClmns;
    private final String groupCell, yearCell, directionCell, academyCell, levelCell, schoolCell;
    private final int firstRow;
        
    private XSSFWorkbook wb;
    private XSSFSheet sht;
    private boolean valid;
    private String tmpCode;
    private String group, year, level, direction, academy, school;
    private BiFunction<XSSFSheet, String, Double> examsFunc;
    private BiFunction<XSSFSheet, String, String> orientFunc;
    //private File tmpFile;
            
    public PV(){
        yearClmns = new String[]{"E", "F", "G"};
        codeClmn = "B";
        nameClmn = "C";
        genderClmn = "D";
        s1Clmn = "H";
        s2Clmn = "I";
        locClmn = "J";
        regClmn = "K";
        choice1Clmn = "M";
        choice2Clmn = "N";
        firstRow = 12;
        dataSheetName = "Data";
        groupCell = "J7";
        yearCell = "K5";
        directionCell = "F5";
        academyCell = "C5";
        levelCell = "F7";
        schoolCell = "C7";
        valid = false;
        sht = null;
    }
    
    public boolean setWorkbook(File f){
        boolean res = false;
        try {
            wb = new XSSFWorkbook(f);
            //tmpFile = f;
            sht = wb.getSheet(dataSheetName);
            if ( isItFromMassar() ) {
                group = getStringValue(sht, groupCell);
                year = getStringValue(sht, yearCell);
                level = getStringValue(sht, levelCell);
                direction = getStringValue(sht, directionCell);
                academy = getStringValue(sht, academyCell);
                school = getStringValue(sht, schoolCell);
                if ( group.startsWith("3A") ) {
                    decisionClmn = "O";
                    avgClmn = "L";
                    examsFunc = (sheet, ref) -> getNumericValue(sheet, ref);
                    orientFunc = (sheet, ref) -> getStringValue(sheet, ref);
                }
                else {
                    decisionClmn = "K";
                    avgClmn = "J";
                    examsFunc = (sheet, ref) -> {return -1.0;};
                    orientFunc = (sheet, ref) -> {return "";};
                }
                res = true;
            }
        }
        catch(IOException | InvalidFormatException e){
            wb = null;
        }
        valid = res;
        return res;
    }
    
    public boolean isItFromMassar(){
        return ( sht == null ) ? false : (getStringValue(sht, "E2").contains("قرار مجلس القسم") && getStringValue(sht, "B10").contains("رقم التلميذ") && 
                getStringValue(sht, "I7").contains("القسم") && getStringValue(sht, "I5").contains("السنة الدراسية") &&
                getStringValue(sht, "C10").contains("الاسم و النسب"));
    }

    public boolean isValid() {
        return valid;
    }
    
    public boolean isNull(){
        return (wb == null);
    }
    
    private String getStringValue(XSSFSheet sht, String cellRef){
        CellReference cr;
        DataFormatter df;
        cr = new CellReference(cellRef);
        df = new DataFormatter();
        try {
            return df.formatCellValue(sht.getRow(cr.getRow()).getCell(cr.getCol()));
        } catch ( NullPointerException npe ) {
            return "";
        }
    }
    
    private Double getNumericValue(XSSFSheet sht, String cellRef){
        CellReference cr = new CellReference(cellRef);
        try {
            return sht.getRow(cr.getRow()).getCell(cr.getCol()).getNumericCellValue();
        } catch ( NullPointerException npe ) {
            return 0.0;
        }
        catch ( IllegalStateException ise ) {
            try {
                return Double.parseDouble(getStringValue(sht, cellRef));
            } catch ( NumberFormatException nfe ) {
                return 0.0;
            }
        }
    }
    
    public void load(ArrayList<PVStudent> students) {
        int r = firstRow;
        while( true ) {
            try{
                if ( !( tmpCode = getStringValue(sht, codeClmn + r)).isEmpty() ) {
                    if ( !students.stream().anyMatch(stu -> tmpCode.equals(stu.getCode())) ) {
                        PVStudent stu = new PVStudent(getNumericValue(sht, s1Clmn + r), 
                                getNumericValue(sht, s2Clmn + r), getNumericValue(sht, avgClmn + r), 
                                getNumericValue(sht, yearClmns[0] + r).intValue(), getNumericValue(sht, yearClmns[1] + r).intValue(), 
                                getNumericValue(sht, yearClmns[2] + r).intValue(), getStringValue(sht, decisionClmn + r), 
                                tmpCode, Misc.justLetters(getStringValue(sht, nameClmn + r)), getStringValue(sht, genderClmn + r), 
                                group, level);
                        stu.setSchoolInfos(academy, direction, school, year);
                        stu.setLoc(examsFunc.apply(sht, locClmn + r));
                        stu.setReg(examsFunc.apply(sht, regClmn + r));
                        stu.setChoice1(orientFunc.apply(sht, choice1Clmn + r));
                        stu.setChoice2(orientFunc.apply(sht, choice2Clmn + r));
                        stu.calculateS13A();
                        students.add(stu);
                    }
                    r++;
                }
                else {
                    break;
                }
            }
            catch(NullPointerException e){
                break;
            }
        }
        try {
            wb.close();
            //tmpFile.renameTo(new File(tmpFile.getParent() + "\\" + group));
        } catch (IOException ex) { 
        }
    }
    
}
