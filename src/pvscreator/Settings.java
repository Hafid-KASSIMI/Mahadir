/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator;

import java.util.ArrayList;
import org.sicut.db.Preferences;
import org.sicut.util.EnvVariable;
import pvscreator.massar.PVStudent;

/**
 *
 * @author Sicut
 */
public class Settings {
    public static final String DB_FOLDER_PATH = EnvVariable.APPDATADirectory() + "/PvsCreator/";
    public static final String PREF_DB_NAME = "preferences.pvs";
    public static final String PREF_DB_PATH = DB_FOLDER_PATH + PREF_DB_NAME;
    
    public static final String APP_NAME = "محاضر";
    public static final String APP_TITLE = "إعداد محاضر وقرارات مجالس الأقسام";
    public static final String APP_YEAR = "2022";
    public static final String APP_VERSION = "0.0.1";
    public static final String APP_DATE = "04/01/2022";
    
    public static Preferences PREF_BUNDLE;
    public static final ArrayList<PVStudent> STUDENTS = new ArrayList();
}
