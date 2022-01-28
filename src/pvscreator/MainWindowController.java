/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pvscreator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import static org.sicut.util.EnvVariable.HOMEDirectory;
import static pvscreator.Settings.*;
import pvscreator.massar.PV;
import pvscreator.template.charts.Chart1PDFFile;
import pvscreator.template.decision.S1DecPDFFile;
import pvscreator.template.decision.S2DecPDFFile;
import pvscreator.template.pv.S1PVPDFFile;
import pvscreator.template.pv.S2PVPDFFile;

/**
 *
 * @author Sicut
 */
public class MainWindowController implements Initializable {
    
    @FXML private TextField nameTF;
    @FXML private DatePicker dateDP;
    @FXML private ComboBox<String> timeCB, semesterCB, levCB;
    @FXML private Spinner<Double> atabaSP;
    @FXML private CheckBox mafsCHB, pvsCHB, decsCHB, chartsCHB;
    @FXML private Button generateBtn, addPvsBtn, addMatterBtn, delMatterBtn;
    @FXML private Label pvsCountLbl, stuCountLbl, appName, buildInfos;
    @FXML private MenuItem outputMI, quitMI, mattersMI, aboutMI;
    @FXML private Pane overlayP;
    @FXML private VBox mattersVB, loadVB, genVB, aboutVB;
    @FXML private ComboBox<String> matter1CB, matter2CB, matter3CB, matter4CB;
    @FXML private ComboBox<String> matter5CB, matter6CB, matter7CB, matter8CB;
    @FXML private ComboBox<String> matter9CB, matter10CB, matter11CB, matter12CB;
    @FXML private ComboBox<String> matter13CB, matter14CB, editMatCB;
    @FXML private ProgressIndicator loadPI;
    
    private final FileChooser fc = new FileChooser();
    private final DirectoryChooser dc = new DirectoryChooser();
    private int pvsCounter;
    private final SimpleIntegerProperty WAITING_FILES = new SimpleIntegerProperty();
    private final SimpleBooleanProperty GENERATION_STATE = new SimpleBooleanProperty();
    private final S1PVPDFFile s1pv;
    private final S2PVPDFFile s2pv;
    private final S1DecPDFFile s1dec;
    private final S2DecPDFFile s2dec;
    private final Chart1PDFFile chart1;
    private VBox currentNotifier;
    private final Duration SM_ANIMATION_DURATION = Duration.seconds(0.7);
    private Boolean mattersShown;
    private EventHandler eh;

    public MainWindowController() {
        s1pv = new S1PVPDFFile();
        s2pv = new S2PVPDFFile();
        s1dec = new S1DecPDFFile();
        s2dec = new S2DecPDFFile();
        chart1 = new Chart1PDFFile();
        mattersShown = false;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Double> svf = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 20, 10, 0.25);
        TextFormatter tf = new TextFormatter(svf.getConverter(), svf.getValue());
        ComboBox<String>[] matsCBs = new ComboBox[] { matter1CB, matter2CB, matter3CB, matter4CB,
                                matter5CB, matter6CB, matter7CB, matter8CB,
                                matter9CB, matter10CB, matter11CB, matter12CB, matter13CB, matter14CB, editMatCB };
        svf.valueProperty().bindBidirectional(tf.valueProperty());
        atabaSP.setValueFactory(svf);
        atabaSP.getEditor().setTextFormatter(tf);
        File output_dir = new File(PREF_BUNDLE.get("OUTPUT_DIR"));
        if ( output_dir.exists() ) {
            dc.setInitialDirectory(output_dir);
        }
        else {
            PREF_BUNDLE.update("OUTPUT_DIR", HOMEDirectory());
        }
        Locale.setDefault(new Locale("ar", "AR"));
        nameTF.setText(PREF_BUNDLE.get("PRESIDENT_NAME"));
        try {
            dateDP.setValue(LocalDate.parse(PREF_BUNDLE.get("COUNCIL_DATE")));
        } catch ( DateTimeParseException dtpe ) {}
        fillCouncilTime();
        timeCB.setValue(PREF_BUNDLE.get("COUNCIL_TIME"));
        try {
            svf.setValue(Double.valueOf(PREF_BUNDLE.get("ATABA_VALUE")));
        } catch ( NumberFormatException nfe ) {}
        mafsCHB.setSelected("1".equals(PREF_BUNDLE.get("COUNT_MAFSOULINE")));
        pvsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_PVS")));
        decsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_DECS")));
        chartsCHB.setSelected("1".equals(PREF_BUNDLE.get("GENERATE_CHARTS")));
        atabaSP.disableProperty().bind(mafsCHB.disableProperty());
        semesterCB.getItems().addAll("الدورة الأولـــى", "الدورة الثانيــــة");
        semesterCB.getSelectionModel().selectedItemProperty().addListener((obs, old, cur) -> {
            PREF_BUNDLE.update("SEMESTER", cur);
            mafsCHB.setDisable(cur.contains("ى"));
        });
        semesterCB.getSelectionModel().select(PREF_BUNDLE.get("SEMESTER"));
        mattersVB.visibleProperty().addListener((obs, old, cur) -> {
            if ( old && !levCB.getSelectionModel().isEmpty() ) {
                for ( int i = 0; i < matsCBs.length; i++ ) {
                    PREF_BUNDLE.update(levCB.getSelectionModel().getSelectedIndex() + "MATTER_" + i, matsCBs[i].getValue());
                }
            }
            else if ( !mattersShown ) {
                mattersShown = true;
                String[] arr = PREF_BUNDLE.get("MATTERS").split("_MAT_");
                for (ComboBox<String> matsCB : matsCBs) {
                    matsCB.getItems().add("");
                    matsCB.getItems().addAll(arr);
                    //matsCBs[i].getSelectionModel().select(PREF_BUNDLE.get("MATTER_" + i));
                }
                levCB.getItems().addAll(PREF_BUNDLE.get("LEVELS").split("_LEV_"));
                levCB.setValue(PREF_BUNDLE.get("SELECTED_LEVEL"));
            }
                
        });
        levCB.getSelectionModel().selectedIndexProperty().addListener((obs, old, cur) -> {
            if ( cur.intValue() >= 0 ) {
                for ( int i = 0, n = cur.intValue(); i < matsCBs.length; i++ ) {
                    matsCBs[i].getSelectionModel().select(PREF_BUNDLE.get(n + "MATTER_" + i));
                }
            }
            PREF_BUNDLE.update("SELECTED_LEVEL", levCB.getSelectionModel().getSelectedItem());
        });
        levCB.getSelectionModel().select(PREF_BUNDLE.get("SELECTED_LEVEL"));
        
        generateBtn.setOnAction(evt -> {
            if ( !pvsCHB.isSelected() && !decsCHB.isSelected() && !chartsCHB.isSelected() )
                return;
            PREF_BUNDLE.update("PRESIDENT_NAME", nameTF.getText());
            PREF_BUNDLE.update("COUNCIL_DATE", dateDP.getValue().format(DateTimeFormatter.ISO_DATE));
            PREF_BUNDLE.update("COUNCIL_TIME", timeCB.getValue());
            PREF_BUNDLE.update("ATABA_VALUE", svf.getValue().toString());
            PREF_BUNDLE.update("COUNT_MAFSOULINE", mafsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_PVS", pvsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_DECS", decsCHB.isSelected() ? "1" : "0");
            PREF_BUNDLE.update("GENERATE_CHARTS", chartsCHB.isSelected() ? "1" : "0");
            GENERATION_STATE.set(false);
            showOverlay(true);
            genVB.setVisible(true);
            new Thread(() -> {
                if ( pvsCHB.isSelected() ) {
                    if ( semesterCB.getSelectionModel().getSelectedIndex() == 0 )
                        s1pv.generate();
                    else {
                        s2pv.setTreshold(svf.getValue());
                        s2pv.generate();
                    }
                }
                if ( decsCHB.isSelected() ) {
                    if ( semesterCB.getSelectionModel().getSelectedIndex() == 0 )
                        s1dec.generate();
                    else {
                        s2dec.generate();
                    }
                }
                if ( chartsCHB.isSelected() ) {
//                    if ( semesterCB.getSelectionModel().getSelectedIndex() == 0 )
//                        s1dec.generate();
//                    else {
                        chart1.generate(semesterCB.getSelectionModel().getSelectedIndex());
//                    }
                }
                GENERATION_STATE.set(true);
            }).start();
        });
        
        GENERATION_STATE.addListener((obs, old, cur) -> {
            if ( cur ) {
                showOverlay(false);
                genVB.setVisible(false);
                try {
                    Desktop.getDesktop().open(dc.getInitialDirectory());
                } catch(IOException ioe) {}
            }
        });
        addPvsBtn.setOnAction(evt -> {
            File ini_pv_dir = new File(PREF_BUNDLE.get("PVS_DIR"));
            fc.setTitle("تحديد محاضر أقسام");
            if ( ini_pv_dir.exists() && ini_pv_dir.isDirectory() )
                fc.setInitialDirectory(ini_pv_dir);
            fc.getExtensionFilters().clear();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("مرتبات إكسيل", "*.xlsx"));
            List<File> f = fc.showOpenMultipleDialog(generateBtn.getScene().getWindow());
            if ( f == null )
                return;
            if ( f.get(0).getParentFile() != ini_pv_dir )
                PREF_BUNDLE.update("PVS_DIR", f.get(0).getParent());
            generateBtn.setDisable(false);
            WAITING_FILES.set(f.size());
            pvsCounter = 0;
            loadPI.setProgress(0);
            loadPI.setUserData(WAITING_FILES.get());
            showOverlay(true);
            loadVB.setVisible(true);
            new Thread(() -> {
                f.forEach(item -> {
                    PV pv = new PV();
                    pv.setWorkbook(item);
                    if ( pv.isValid() ) {
                        pv.load(Settings.STUDENTS);
                        pvsCounter++;
                    }
                    WAITING_FILES.set(WAITING_FILES.get() - 1);
                });
            }).start();
        });
        
        WAITING_FILES.addListener((obs, old, cur) -> {
            Platform.runLater(() -> {
                pvsCountLbl.setText(pvsCounter + "");
                stuCountLbl.setText(ARStudentsCounter(Settings.STUDENTS.size()) + "");
                loadPI.setProgress(1 - WAITING_FILES.get() * 1.0 / (int) loadPI.getUserData());
            });
            
            if ( WAITING_FILES.get() < 1 ) {
                showOverlay(false);
                loadVB.setVisible(false);
            }
        });
        
        outputMI.setOnAction(evt -> {
            File f = dc.showDialog(generateBtn.getScene().getWindow());
            if ( f != null ) {
                PREF_BUNDLE.update("OUTPUT_DIR", f.getPath());
                dc.setInitialDirectory(f);
            }
        });
        
        quitMI.setOnAction(evt -> {
            PREF_BUNDLE.commit();
            generateBtn.getScene().getWindow().hide();
        });
        
        mattersMI.setOnAction(evt -> {
            showOverlayMessage(mattersVB, true);
        });
        
        aboutMI.setOnAction(evt -> {
            showOverlayMessage(aboutVB, true);
        });
        
        eh = evt -> {
            showOverlayMessage(currentNotifier, false);
        };
        overlayP.addEventHandler(MouseEvent.MOUSE_RELEASED, eh);
        overlayP.addEventHandler(TouchEvent.TOUCH_RELEASED, eh);
        
        addMatterBtn.setOnAction(evt -> {
            String mat = editMatCB.getValue();
            if ( matter1CB.getItems().contains(mat) ) {
                
            }
            else {
                for (ComboBox<String> matsCB : matsCBs) {
                    matsCB.getItems().add(mat);
                }
                PREF_BUNDLE.update("MATTERS", PREF_BUNDLE.get("MATTERS") + "_MAT_" + mat);
            }
        });
        
        delMatterBtn.setOnAction(evt -> {
            String mat = editMatCB.getValue();
            if ( matter1CB.getItems().contains(mat) ) {
                for (ComboBox<String> matsCB : matsCBs) {
                    matsCB.getItems().remove(mat);
                }
                PREF_BUNDLE.update("MATTERS", PREF_BUNDLE.get("MATTERS").replaceAll("(" + "_MAT_" + mat + ")|(" + mat + "_MAT_" + ")", "") );
            }
            else {
            }
        });
        
        appName.setText(Settings.APP_NAME);
        buildInfos.setText(buildInfos.getText().replace("%version%", Settings.APP_VERSION).replace("%date%", Settings.APP_DATE));
    }
    
    private String ARStudentsCounter(int n) {
        if ( n == 1 )
            return "تلميذ واحد";
        else if ( n == 2 )
            return "تلميذان اثنان";
        else if ( n > 2 && n < 11 )
            return String.format("%02d تلاميذ", n);
        else if ( n % 100 == 0 )
            return n + " تلميذ";
        else
            return String.format("%02d تلميذا", n);
    }
    
    private void fillCouncilTime() {
        for ( int i = 420; i < 1261; i += 10 ) {
            timeCB.getItems().add(String.format("%02d:%02d", i / 60, i % 60));
        }
    }

    private void showOverlayMessage(VBox vb, boolean show) {
        if ( show && currentNotifier == vb )
            return;
        Timeline t = new Timeline();
        if ( show ) {
            overlayP.setVisible(true);
            vb.setVisible(true);
            if ( currentNotifier != null ) {
                currentNotifier.setVisible(false);
                currentNotifier.setOpacity(0);
                currentNotifier.setTranslateY(-500);
            }
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.translateYProperty(), 6, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.opacityProperty(), 1, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 1, Interpolator.EASE_IN)));
            currentNotifier = vb;
        }
        else {
            currentNotifier = null;
            t.setOnFinished(evt -> {
                overlayP.setVisible(false);
                vb.setVisible(false);
            });
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.translateYProperty(), -600, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(vb.opacityProperty(), 0, Interpolator.EASE_IN)));
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 0, Interpolator.EASE_IN)));
        }
        t.play();
    }

    private void showOverlay(boolean show) {
        Timeline t = new Timeline();
        if ( show ) {
            overlayP.removeEventHandler(MouseEvent.MOUSE_RELEASED, eh);
            overlayP.removeEventHandler(TouchEvent.TOUCH_RELEASED, eh);
            overlayP.setVisible(true);
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 1, Interpolator.EASE_IN)));
           
        }
        else {
            t.setOnFinished(evt -> {
                overlayP.setVisible(false);
                overlayP.addEventHandler(MouseEvent.MOUSE_RELEASED, eh);
                overlayP.addEventHandler(TouchEvent.TOUCH_RELEASED, eh);
            });
            t.getKeyFrames().add(new KeyFrame(SM_ANIMATION_DURATION, new KeyValue(overlayP.opacityProperty(), 0, Interpolator.EASE_IN)));
        }
        t.play();
    }
    
}
