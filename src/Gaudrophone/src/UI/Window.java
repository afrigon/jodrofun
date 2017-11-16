/*
 * The MIT License
 *
 * Copyright 2017 Olivier.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package UI;

import Instrument.Instrument;
import Instrument.Key;
import KeyUtils.RectangleKeyShape;
import KeyUtils.Vector2;
import Manager.InstrumentManager;
import Music.Sound;
import Music.SynthesizedSound;
import java.util.ArrayList;

public class Window extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public Window() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        keyTypeButtonGroup = new javax.swing.ButtonGroup();
        alterationButtonGroup = new javax.swing.ButtonGroup();
        splitWindow = new javax.swing.JSplitPane();
        instrumentPanel = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(250, 0), new java.awt.Dimension(600, 0), new java.awt.Dimension(250, 32767));
        jScrollPane1 = new javax.swing.JScrollPane();
        PropertyPanel = new javax.swing.JPanel();
        KeyProperties = new javax.swing.JPanel();
        generalProperties = new javax.swing.JPanel();
        keyNameProperty = new javax.swing.JPanel();
        keyNameLabel = new javax.swing.JLabel();
        keyNameField = new javax.swing.JTextField();
        noteNameProperty = new javax.swing.JPanel();
        noteNameLabel = new javax.swing.JLabel();
        noteNameChoice = new java.awt.Choice();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(80, 0), new java.awt.Dimension(200, 32767));
        octaveLabel = new javax.swing.JLabel();
        octaveSpinner = new javax.swing.JSpinner();
        alterationProperty = new javax.swing.JPanel();
        alterationLabel = new javax.swing.JLabel();
        sharpRadioButton = new javax.swing.JRadioButton();
        naturalRadioButton = new javax.swing.JRadioButton();
        flatRadioButton = new javax.swing.JRadioButton();
        volumeProperty = new javax.swing.JPanel();
        volumeLabel = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        volumeSpinner = new javax.swing.JSpinner();
        envelopeProperties = new javax.swing.JPanel();
        envelopeTitlePanel = new javax.swing.JPanel();
        envelopeLabel = new javax.swing.JLabel();
        envelopeGraph = new javax.swing.JPanel();
        envelopeSliders = new javax.swing.JPanel();
        attackSliderPanel = new javax.swing.JPanel();
        attackSlider = new javax.swing.JSlider();
        attackSpinner = new javax.swing.JSpinner();
        attackLabel = new javax.swing.JLabel();
        decaySliderPanel = new javax.swing.JPanel();
        decaySlider = new javax.swing.JSlider();
        decaySpinner = new javax.swing.JSpinner();
        decayLabel = new javax.swing.JLabel();
        sustainSliderPanel = new javax.swing.JPanel();
        sustainSlider = new javax.swing.JSlider();
        sustainSpinner = new javax.swing.JSpinner();
        sustainLabel = new javax.swing.JLabel();
        releaseSliderPanel = new javax.swing.JPanel();
        releaseSlider = new javax.swing.JSlider();
        releaseSpinner = new javax.swing.JSpinner();
        releaseLabel = new javax.swing.JLabel();
        showNameProperty = new javax.swing.JPanel();
        showNoteNameLabel = new javax.swing.JLabel();
        showKeyNameCheckbox = new java.awt.Checkbox();
        showNoteNameCheckbox = new java.awt.Checkbox();
        typeProperty = new javax.swing.JPanel();
        synthRadioButton = new javax.swing.JRadioButton();
        audioClipRadioButton = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        noteProperties = new javax.swing.JPanel();
        frequencyProperty = new javax.swing.JPanel();
        frequencyLabel = new javax.swing.JLabel();
        frequencySpinner = new javax.swing.JSpinner();
        tuningProperty = new javax.swing.JPanel();
        tuningLabel = new javax.swing.JLabel();
        tuningSpinner = new javax.swing.JSpinner();
        waveFormProperty = new javax.swing.JPanel();
        waveFormLabel = new javax.swing.JLabel();
        waveFormChoice = new java.awt.Choice();
        jSeparator2 = new javax.swing.JSeparator();
        audioClipProperties = new javax.swing.JPanel();
        audioClipFileProperty = new javax.swing.JPanel();
        audioClipFileLabel = new javax.swing.JLabel();
        audioClipPathLabel = new javax.swing.JLabel();
        audioClipSelectButton = new java.awt.Button();
        readSpeedProperty = new javax.swing.JPanel();
        readSpeedLabel = new javax.swing.JLabel();
        readSpeedSpinner = new javax.swing.JSpinner();
        KeyShapeProperties = new javax.swing.JPanel();
        backgroundProperty = new javax.swing.JPanel();
        backgroundLabel = new javax.swing.JLabel();
        backgroundDisplayLabel = new javax.swing.JLabel();
        backgroundColorButton = new java.awt.Button();
        backgroundImageButton = new java.awt.Button();
        backgroundSunkenProperty = new javax.swing.JPanel();
        backgroundSunkenLabel = new javax.swing.JLabel();
        backgroundSunkenDisplayLabel = new javax.swing.JLabel();
        backgroundSunkenColorButton = new java.awt.Button();
        backgroundSunkenImageButton = new java.awt.Button();
        linesColorProperty = new javax.swing.JPanel();
        linesColorLabel = new javax.swing.JLabel();
        linesColorDisplay = new javax.swing.JLabel();
        linesColorButton = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(650, 500));

        splitWindow.setDividerLocation(250);
        splitWindow.setContinuousLayout(true);
        splitWindow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        instrumentPanel.setBackground(new java.awt.Color(255, 255, 255));
        instrumentPanel.setLayout(new java.awt.BorderLayout());
        instrumentPanel.add(filler2, java.awt.BorderLayout.CENTER);

        splitWindow.setLeftComponent(instrumentPanel);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(350, 250));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(350, 226));

        PropertyPanel.setPreferredSize(new java.awt.Dimension(400, 224));
        PropertyPanel.setLayout(new javax.swing.BoxLayout(PropertyPanel, javax.swing.BoxLayout.Y_AXIS));

        KeyProperties.setLayout(new javax.swing.BoxLayout(KeyProperties, javax.swing.BoxLayout.Y_AXIS));

        generalProperties.setBackground(new java.awt.Color(153, 153, 153));
        generalProperties.setLayout(new javax.swing.BoxLayout(generalProperties, javax.swing.BoxLayout.Y_AXIS));

        keyNameProperty.setLayout(new java.awt.GridLayout(1, 0));

        keyNameLabel.setText("Nom de la touche :");
        keyNameProperty.add(keyNameLabel);

        keyNameField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        keyNameField.setText("Nom de note");
        keyNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyNameFieldActionPerformed(evt);
            }
        });
        keyNameProperty.add(keyNameField);

        generalProperties.add(keyNameProperty);

        noteNameProperty.setLayout(new javax.swing.BoxLayout(noteNameProperty, javax.swing.BoxLayout.LINE_AXIS));

        noteNameLabel.setText("Nom de note :");
        noteNameProperty.add(noteNameLabel);
        noteNameProperty.add(noteNameChoice);
        noteNameProperty.add(filler1);

        octaveLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        octaveLabel.setText("Octave :");
        noteNameProperty.add(octaveLabel);

        octaveSpinner.setModel(new javax.swing.SpinnerNumberModel(2, -2, 8, 1));
        noteNameProperty.add(octaveSpinner);

        generalProperties.add(noteNameProperty);

        alterationProperty.setLayout(new java.awt.GridLayout(1, 0));

        alterationLabel.setText("Altération :");
        alterationProperty.add(alterationLabel);

        alterationButtonGroup.add(sharpRadioButton);
        sharpRadioButton.setText("Dièse");
        alterationProperty.add(sharpRadioButton);

        alterationButtonGroup.add(naturalRadioButton);
        naturalRadioButton.setSelected(true);
        naturalRadioButton.setText("Naturelle");
        naturalRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naturalRadioButtonActionPerformed(evt);
            }
        });
        alterationProperty.add(naturalRadioButton);

        alterationButtonGroup.add(flatRadioButton);
        flatRadioButton.setText("Bémol");
        flatRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flatRadioButtonActionPerformed(evt);
            }
        });
        alterationProperty.add(flatRadioButton);

        generalProperties.add(alterationProperty);

        volumeProperty.setLayout(new java.awt.GridLayout(1, 0));

        volumeLabel.setText("Volume :");
        volumeProperty.add(volumeLabel);

        jSlider1.setValue(100);
        volumeProperty.add(jSlider1);

        volumeSpinner.setModel(new javax.swing.SpinnerNumberModel(100.0d, 0.0d, 100.0d, 1.0d));
        volumeProperty.add(volumeSpinner);

        generalProperties.add(volumeProperty);

        envelopeProperties.setLayout(new javax.swing.BoxLayout(envelopeProperties, javax.swing.BoxLayout.Y_AXIS));

        envelopeTitlePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        envelopeLabel.setText("Enveloppe :");
        envelopeLabel.setToolTipText("");
        envelopeTitlePanel.add(envelopeLabel);

        envelopeProperties.add(envelopeTitlePanel);

        envelopeGraph.setBackground(new java.awt.Color(255, 255, 255));
        envelopeGraph.setMinimumSize(new java.awt.Dimension(50, 100));
        envelopeGraph.setPreferredSize(new java.awt.Dimension(50, 125));

        javax.swing.GroupLayout envelopeGraphLayout = new javax.swing.GroupLayout(envelopeGraph);
        envelopeGraph.setLayout(envelopeGraphLayout);
        envelopeGraphLayout.setHorizontalGroup(
            envelopeGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        envelopeGraphLayout.setVerticalGroup(
            envelopeGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        envelopeProperties.add(envelopeGraph);

        envelopeSliders.setLayout(new javax.swing.BoxLayout(envelopeSliders, javax.swing.BoxLayout.X_AXIS));

        attackSliderPanel.setLayout(new java.awt.BorderLayout());

        attackSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        attackSlider.setToolTipText("Attack");
        attackSlider.setMaximumSize(new java.awt.Dimension(50, 32767));
        attackSlider.setMinimumSize(new java.awt.Dimension(50, 36));
        attackSlider.setPreferredSize(new java.awt.Dimension(50, 200));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, attackSpinner, org.jdesktop.beansbinding.ELProperty.create("${value}"), attackSlider, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        attackSliderPanel.add(attackSlider, java.awt.BorderLayout.CENTER);

        attackSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        attackSpinner.setPreferredSize(new java.awt.Dimension(20, 20));
        attackSliderPanel.add(attackSpinner, java.awt.BorderLayout.PAGE_START);

        attackLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        attackLabel.setLabelFor(attackSlider);
        attackLabel.setText("Attack");
        attackLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        attackLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        attackSliderPanel.add(attackLabel, java.awt.BorderLayout.PAGE_END);

        envelopeSliders.add(attackSliderPanel);

        decaySliderPanel.setLayout(new java.awt.BorderLayout());

        decaySlider.setOrientation(javax.swing.JSlider.VERTICAL);
        decaySlider.setToolTipText("Attack");
        decaySlider.setValue(40);
        decaySlider.setMaximumSize(new java.awt.Dimension(50, 32767));
        decaySlider.setMinimumSize(new java.awt.Dimension(50, 36));
        decaySlider.setPreferredSize(new java.awt.Dimension(50, 200));
        decaySliderPanel.add(decaySlider, java.awt.BorderLayout.CENTER);

        decaySpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        decaySpinner.setPreferredSize(new java.awt.Dimension(20, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, decaySlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), decaySpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        decaySliderPanel.add(decaySpinner, java.awt.BorderLayout.PAGE_START);

        decayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        decayLabel.setLabelFor(attackSlider);
        decayLabel.setText("Decay");
        decayLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        decayLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        decaySliderPanel.add(decayLabel, java.awt.BorderLayout.PAGE_END);

        envelopeSliders.add(decaySliderPanel);

        sustainSliderPanel.setLayout(new java.awt.BorderLayout());

        sustainSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        sustainSlider.setToolTipText("Attack");
        sustainSlider.setMaximumSize(new java.awt.Dimension(50, 32767));
        sustainSlider.setMinimumSize(new java.awt.Dimension(50, 36));
        sustainSlider.setPreferredSize(new java.awt.Dimension(50, 200));
        sustainSliderPanel.add(sustainSlider, java.awt.BorderLayout.CENTER);

        sustainSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        sustainSpinner.setPreferredSize(new java.awt.Dimension(20, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sustainSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), sustainSpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        sustainSliderPanel.add(sustainSpinner, java.awt.BorderLayout.PAGE_START);

        sustainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sustainLabel.setLabelFor(attackSlider);
        sustainLabel.setText("Sustain");
        sustainLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        sustainLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sustainSliderPanel.add(sustainLabel, java.awt.BorderLayout.PAGE_END);

        envelopeSliders.add(sustainSliderPanel);

        releaseSliderPanel.setLayout(new java.awt.BorderLayout());

        releaseSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        releaseSlider.setToolTipText("Attack");
        releaseSlider.setMaximumSize(new java.awt.Dimension(50, 32767));
        releaseSlider.setMinimumSize(new java.awt.Dimension(50, 36));
        releaseSlider.setPreferredSize(new java.awt.Dimension(50, 200));
        releaseSliderPanel.add(releaseSlider, java.awt.BorderLayout.CENTER);

        releaseSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        releaseSpinner.setPreferredSize(new java.awt.Dimension(20, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, releaseSlider, org.jdesktop.beansbinding.ELProperty.create("${value}"), releaseSpinner, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        releaseSliderPanel.add(releaseSpinner, java.awt.BorderLayout.PAGE_START);

        releaseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        releaseLabel.setLabelFor(attackSlider);
        releaseLabel.setText("Release");
        releaseLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        releaseLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        releaseSliderPanel.add(releaseLabel, java.awt.BorderLayout.PAGE_END);

        envelopeSliders.add(releaseSliderPanel);

        envelopeProperties.add(envelopeSliders);

        generalProperties.add(envelopeProperties);

        showNameProperty.setLayout(new java.awt.GridLayout(1, 0));

        showNoteNameLabel.setText("Affichage :");
        showNoteNameLabel.setToolTipText("");
        showNameProperty.add(showNoteNameLabel);

        showKeyNameCheckbox.setLabel("Nom de la touche");
        showNameProperty.add(showKeyNameCheckbox);

        showNoteNameCheckbox.setLabel("Nom de la note");
        showNameProperty.add(showNoteNameCheckbox);

        generalProperties.add(showNameProperty);

        typeProperty.setLayout(new java.awt.GridLayout(1, 0));

        keyTypeButtonGroup.add(synthRadioButton);
        synthRadioButton.setText("Synth");
        typeProperty.add(synthRadioButton);

        keyTypeButtonGroup.add(audioClipRadioButton);
        audioClipRadioButton.setText("Clip Audio");
        audioClipRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioClipRadioButtonActionPerformed(evt);
            }
        });
        typeProperty.add(audioClipRadioButton);

        generalProperties.add(typeProperty);

        KeyProperties.add(generalProperties);

        jSeparator1.setMinimumSize(new java.awt.Dimension(0, 5));
        KeyProperties.add(jSeparator1);

        noteProperties.setBackground(new java.awt.Color(204, 204, 204));
        noteProperties.setLayout(new javax.swing.BoxLayout(noteProperties, javax.swing.BoxLayout.Y_AXIS));

        frequencyProperty.setLayout(new java.awt.GridLayout(1, 2));

        frequencyLabel.setText("Fréquence :");
        frequencyProperty.add(frequencyLabel);

        frequencySpinner.setModel(new javax.swing.SpinnerNumberModel(440.0d, 10.0d, 18000.0d, 0.1d));
        frequencyProperty.add(frequencySpinner);

        noteProperties.add(frequencyProperty);

        tuningProperty.setLayout(new java.awt.GridLayout(1, 2));

        tuningLabel.setText("Tuning : ");
        tuningProperty.add(tuningLabel);

        tuningSpinner.setModel(new javax.swing.SpinnerNumberModel(0, -49, 49, 1));
        tuningProperty.add(tuningSpinner);

        noteProperties.add(tuningProperty);

        waveFormProperty.setLayout(new java.awt.GridLayout(1, 0));

        waveFormLabel.setText("Forme d'onde :");
        waveFormProperty.add(waveFormLabel);
        waveFormProperty.add(waveFormChoice);

        noteProperties.add(waveFormProperty);

        KeyProperties.add(noteProperties);

        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 5));
        KeyProperties.add(jSeparator2);

        audioClipProperties.setLayout(new javax.swing.BoxLayout(audioClipProperties, javax.swing.BoxLayout.Y_AXIS));

        audioClipFileProperty.setLayout(new java.awt.GridLayout(1, 2));

        audioClipFileLabel.setText("Fichier :");
        audioClipFileProperty.add(audioClipFileLabel);

        audioClipPathLabel.setBackground(new java.awt.Color(255, 255, 255));
        audioClipPathLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        audioClipPathLabel.setText("Aucun fichier");
        audioClipPathLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        audioClipPathLabel.setOpaque(true);
        audioClipFileProperty.add(audioClipPathLabel);

        audioClipSelectButton.setLabel("Choisir un fichier");
        audioClipFileProperty.add(audioClipSelectButton);

        audioClipProperties.add(audioClipFileProperty);

        readSpeedProperty.setLayout(new java.awt.GridLayout(1, 2));

        readSpeedLabel.setText("Pitch : ");
        readSpeedProperty.add(readSpeedLabel);

        readSpeedSpinner.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.05d, 100.0d, 0.05d));
        readSpeedProperty.add(readSpeedSpinner);

        audioClipProperties.add(readSpeedProperty);

        KeyProperties.add(audioClipProperties);

        PropertyPanel.add(KeyProperties);

        KeyShapeProperties.setLayout(new javax.swing.BoxLayout(KeyShapeProperties, javax.swing.BoxLayout.Y_AXIS));

        backgroundProperty.setLayout(new java.awt.GridLayout(1, 0));

        backgroundLabel.setText("Couleur de touche :");
        backgroundProperty.add(backgroundLabel);

        backgroundDisplayLabel.setBackground(new java.awt.Color(255, 255, 255));
        backgroundDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroundDisplayLabel.setText("(255, 255, 255)");
        backgroundDisplayLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backgroundDisplayLabel.setOpaque(true);
        backgroundProperty.add(backgroundDisplayLabel);

        backgroundColorButton.setLabel("Choisir une couleur");
        backgroundProperty.add(backgroundColorButton);

        backgroundImageButton.setLabel("Choisir une image");
        backgroundProperty.add(backgroundImageButton);

        KeyShapeProperties.add(backgroundProperty);

        backgroundSunkenProperty.setLayout(new java.awt.GridLayout(1, 0));

        backgroundSunkenLabel.setText("Couleur de touche enfoncée :");
        backgroundSunkenProperty.add(backgroundSunkenLabel);

        backgroundSunkenDisplayLabel.setBackground(new java.awt.Color(255, 255, 255));
        backgroundSunkenDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backgroundSunkenDisplayLabel.setText("(255, 255, 255)");
        backgroundSunkenDisplayLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backgroundSunkenDisplayLabel.setOpaque(true);
        backgroundSunkenProperty.add(backgroundSunkenDisplayLabel);

        backgroundSunkenColorButton.setLabel("Choisir une couleur");
        backgroundSunkenProperty.add(backgroundSunkenColorButton);

        backgroundSunkenImageButton.setLabel("Choisir une image");
        backgroundSunkenProperty.add(backgroundSunkenImageButton);

        KeyShapeProperties.add(backgroundSunkenProperty);

        linesColorProperty.setLayout(new java.awt.GridLayout(1, 0));

        linesColorLabel.setText("Couleur du contour :");
        linesColorProperty.add(linesColorLabel);

        linesColorDisplay.setBackground(new java.awt.Color(255, 255, 255));
        linesColorDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        linesColorDisplay.setText("Custom");
        linesColorDisplay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        linesColorDisplay.setOpaque(true);
        linesColorProperty.add(linesColorDisplay);

        linesColorButton.setLabel("Choisir une couleur");
        linesColorProperty.add(linesColorButton);

        KeyShapeProperties.add(linesColorProperty);

        PropertyPanel.add(KeyShapeProperties);

        jScrollPane1.setViewportView(PropertyPanel);

        splitWindow.setRightComponent(jScrollPane1);

        getContentPane().add(splitWindow, java.awt.BorderLayout.CENTER);

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void flatRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flatRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_flatRadioButtonActionPerformed

    private void keyNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyNameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keyNameFieldActionPerformed

    private void audioClipRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioClipRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_audioClipRadioButtonActionPerformed

    private void naturalRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naturalRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_naturalRadioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public void setVisible() {
        java.awt.EventQueue.invokeLater(() -> {
            this.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel KeyProperties;
    private javax.swing.JPanel KeyShapeProperties;
    private javax.swing.JPanel PropertyPanel;
    private javax.swing.ButtonGroup alterationButtonGroup;
    private javax.swing.JLabel alterationLabel;
    private javax.swing.JPanel alterationProperty;
    private javax.swing.JLabel attackLabel;
    private javax.swing.JSlider attackSlider;
    private javax.swing.JPanel attackSliderPanel;
    private javax.swing.JSpinner attackSpinner;
    private javax.swing.JLabel audioClipFileLabel;
    private javax.swing.JPanel audioClipFileProperty;
    private javax.swing.JLabel audioClipPathLabel;
    private javax.swing.JPanel audioClipProperties;
    private javax.swing.JRadioButton audioClipRadioButton;
    private java.awt.Button audioClipSelectButton;
    private java.awt.Button backgroundColorButton;
    private javax.swing.JLabel backgroundDisplayLabel;
    private java.awt.Button backgroundImageButton;
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JPanel backgroundProperty;
    private java.awt.Button backgroundSunkenColorButton;
    private javax.swing.JLabel backgroundSunkenDisplayLabel;
    private java.awt.Button backgroundSunkenImageButton;
    private javax.swing.JLabel backgroundSunkenLabel;
    private javax.swing.JPanel backgroundSunkenProperty;
    private javax.swing.JLabel decayLabel;
    private javax.swing.JSlider decaySlider;
    private javax.swing.JPanel decaySliderPanel;
    private javax.swing.JSpinner decaySpinner;
    private javax.swing.JPanel envelopeGraph;
    private javax.swing.JLabel envelopeLabel;
    private javax.swing.JPanel envelopeProperties;
    private javax.swing.JPanel envelopeSliders;
    private javax.swing.JPanel envelopeTitlePanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JRadioButton flatRadioButton;
    private javax.swing.JLabel frequencyLabel;
    private javax.swing.JPanel frequencyProperty;
    private javax.swing.JSpinner frequencySpinner;
    private javax.swing.JPanel generalProperties;
    private javax.swing.JPanel instrumentPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField keyNameField;
    private javax.swing.JLabel keyNameLabel;
    private javax.swing.JPanel keyNameProperty;
    private javax.swing.ButtonGroup keyTypeButtonGroup;
    private java.awt.Button linesColorButton;
    private javax.swing.JLabel linesColorDisplay;
    private javax.swing.JLabel linesColorLabel;
    private javax.swing.JPanel linesColorProperty;
    private javax.swing.JRadioButton naturalRadioButton;
    private java.awt.Choice noteNameChoice;
    private javax.swing.JLabel noteNameLabel;
    private javax.swing.JPanel noteNameProperty;
    private javax.swing.JPanel noteProperties;
    private javax.swing.JLabel octaveLabel;
    private javax.swing.JSpinner octaveSpinner;
    private javax.swing.JLabel readSpeedLabel;
    private javax.swing.JPanel readSpeedProperty;
    private javax.swing.JSpinner readSpeedSpinner;
    private javax.swing.JLabel releaseLabel;
    private javax.swing.JSlider releaseSlider;
    private javax.swing.JPanel releaseSliderPanel;
    private javax.swing.JSpinner releaseSpinner;
    private javax.swing.JRadioButton sharpRadioButton;
    private java.awt.Checkbox showKeyNameCheckbox;
    private javax.swing.JPanel showNameProperty;
    private java.awt.Checkbox showNoteNameCheckbox;
    private javax.swing.JLabel showNoteNameLabel;
    private javax.swing.JSplitPane splitWindow;
    private javax.swing.JLabel sustainLabel;
    private javax.swing.JSlider sustainSlider;
    private javax.swing.JPanel sustainSliderPanel;
    private javax.swing.JSpinner sustainSpinner;
    private javax.swing.JRadioButton synthRadioButton;
    private javax.swing.JLabel tuningLabel;
    private javax.swing.JPanel tuningProperty;
    private javax.swing.JSpinner tuningSpinner;
    private javax.swing.JPanel typeProperty;
    private javax.swing.JLabel volumeLabel;
    private javax.swing.JPanel volumeProperty;
    private javax.swing.JSpinner volumeSpinner;
    private java.awt.Choice waveFormChoice;
    private javax.swing.JLabel waveFormLabel;
    private javax.swing.JPanel waveFormProperty;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
