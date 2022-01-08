import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author oliviaye
 * Food Sorting Application
 * 
 */
public class UI extends javax.swing.JFrame {

    // Loads the files into a file variable for future use
    File appetizersFile = new File("appetizers.txt"); 
    File entreesFile = new File("entrees.txt"); 
    File dessertsFile = new File("desserts.txt"); 
    File misspellingsFile = new File("misspellings.txt"); 
    
    
    String[] appetizers, entrees, desserts, misspellings; // Arrays containing the list of foods, sorted by type
    String[] foodList = new String[20]; // Array containing list of all foods
    int num = 0; // Global number counter

    /**
     * Loads the contents of the file into arrays and loads all the foods into the food list
     */
    private void begin() {
        loadFile(appetizersFile, 'A');
        loadFile(entreesFile, 'E');
        loadFile(dessertsFile, 'D');
        loadFile(misspellingsFile, 'M');
        foodList = sortWordsAlphabetically(foodList);
    }

    /**
     * Loads the content of the text file into a sorted linked list -> sorted array and food list array
     * if the file is of food names or into an array list of misspellings
     * @param file              imported file
     * @param type              type of food or misspelling list
     */
    private void loadFile(File file, char type) {
        boolean load = true;
        Queue<String> foodFromFile = new LinkedList<>();
        int num;
        
        // Try-catch statement to catch if there is an error in loading the file or all the names have been loaded into the queue
        try {
            num = this.num;
            Scanner input = new Scanner(file);
            for (int i = num; i < 40; i++) {
                String foods = input.nextLine().trim();
                foodFromFile.add(foods);
                this.num = i;
                if (type != 'M') {
                    foodList[i] = foods;
                }
            }
        } catch (FileNotFoundException ex) {
            load = false;
            errorLabel.setText("Error, files cannot be loaded.");
        } catch (NoSuchElementException e) {
            load = true;
        }
        
        // Loads the names into the array if they were successfully loaded into the queue
        if (load != false) {
            loadArray(foodFromFile, type);
        }
        this.num++;
    }

    /**
     * Loads the foods from the sorted queue into their respective arrays
     * @param foodList          queue
     * @param type              type of food/misspellings
     */
    private void loadArray(Queue<String> foodList, char type) {
        int size = foodList.size();
        switch (type) {
            case 'A':
                appetizers = new String[size];
                for (int i = 0; i < size; i++) {
                    appetizers[i] = foodList.remove();
                }
                break;
            case 'E':
                entrees = new String[size];
                for (int i = 0; i < size; i++) {
                    entrees[i] = foodList.remove();
                }
                break;
            case 'D':
                desserts = new String[size];
                for (int i = 0; i < size; i++) {
                    desserts[i] = foodList.remove();
                }
                break;
            case 'M':
                misspellings = new String[size];
                for (int i = 0; i < size; i++) {
                    misspellings[i] = foodList.remove();
                }
                break;
        }
    }
    
    /**
     * Checks the GUI input to see if the food directly corresponds to a food in the list and prints out result
     * @param input             input object (contains input name, verifying variables)
     * @param type              click vs manual input
     * @param audience          audience name input
     * @return 
     */
    private boolean checkFieldInput(Check input, char type, String audience) {
        boolean success = false;
        input.checkCourse(appetizers, entrees, desserts, type);
        // A(n) for vowel consideration
        if (input.getCourse().compareTo("Appetizer") == 0 || input.getCourse().compareTo("Entree") == 0) {
            resultLabel.setText(audience + " is an " + input.getCourse());
            errorLabel.setText("");
            success = true;
        } else if (input.getCourse().compareTo("Dessert") == 0) {
            resultLabel.setText(audience + " is a " + input.getCourse());
            errorLabel.setText("");
            success = true;
        }
        return success;
    }

    /**
     * Sorts the words alphabetically with Insertion Sort
     * @param list              list of words
     * @return                  returns the sorted list
     */
    private static String[] sortWordsAlphabetically(String[] list) {
        int pos = 0;
        String temp;
        // While loop to run while position is less than the number of names
        while (pos < 20) {
            int pos2 = pos;
            // While loop to inset the name in the proper position if the name in the position index comes later in the alphabet then the next name
            while (pos2 > 0 && list[pos2 - 1].compareTo(list[pos2]) > 0) {
                temp = list[pos2];
                list[pos2] = list[pos2 - 1];
                list[pos2 - 1] = temp;
                pos2--;
            }
            pos++;
        }
        return list;
    }

    /**
     * Result/error displays if the manual input does not directly correspond to a food on the list
     * @param type              type of error (contains vs misspelling)
     * @param course            guessed matching course
     * @param audienceInput     audience text field input
     * @param correctedInput    if misspelling, the guessed corrected food name
     */
    private void displayTextIfFieldInputInvalid(String type, String course, String audienceInput, String correctedInput) {
        if (type.compareTo("contains") == 0) { // Display if the word contained the original food
            // A(n) to account for vowels
            if (course.compareTo("Appetizer") == 0 || course.compareTo("Entree") == 0) {
                errorLabel.setText("No exact match, but " + audienceInput + " may be an " + course + ".");
            } else if (course.compareTo("Dessert") == 0) {
                errorLabel.setText("No exact match, but " + audienceInput + " may be a " + course + ".");
            }
        } else { // Display if the word was a common misspelling
            // A(n) to account for vowels
            if (course.compareTo("Appetizer") == 0 || course.compareTo("Entree") == 0) {
                errorLabel.setText("No exact match, but " + audienceInput + " (guess: " + correctedInput + ") may be an " + course + ".");
                resultLabel.setText("");
            } else if (course.compareTo("Dessert") == 0) {
                errorLabel.setText("No exact match, but " + audienceInput + " (guess: " + correctedInput + ") may be a " + course + ".");
                resultLabel.setText("");
            }
        }
        resultLabel.setText("");
    }

    /**
     * Creates new form UI
     */
    private UI() {
        initComponents();
        scaleImage();
        begin();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * Code Author: Generated code by the GUI and modified code of specific properties for VISUAL reasons (done through individual components in the design tab)
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new RoundedPanel(45);
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new RoundedPanel(55);
        resultLabel = new javax.swing.JLabel();
        inputField = new javax.swing.JTextField();
        errorLabel = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Food Sorter by Course - Olivia Ye");
        setBackground(new java.awt.Color(41, 65, 129));

        jPanel1.setBackground(new java.awt.Color(36, 37, 38));
        jPanel1.setMaximumSize(new java.awt.Dimension(5, 5));

        jLabel1.setFont(new java.awt.Font("Geneva", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Enter Food Here:");

        jPanel3.setBackground(new java.awt.Color(149, 200, 216));

        jLabel2.setFont(new java.awt.Font("Galvji", 1, 12)); // NOI18N
        jLabel2.setText("Food Sorting Application by Olivia Ye");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("PingFang SC", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("<html>"
            + "Food courses."
            + "<br>Let's sort."
            + "</html>");

        jList1.setBackground(new java.awt.Color(36, 37, 38));
        jList1.setFont(new java.awt.Font("Geneva", 1, 14)); // NOI18N
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            public int getSize() { return foodList.length; }
            public String getElementAt(int i) { return foodList[i]; }
        });
        jList1.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jList1.setFixedCellHeight(20);
        jList1.setFixedCellWidth(82);
        jList1.setAlignmentX(0.0F);
        jList1.setAlignmentY(0.0F);
        jList1.setFocusable(false);
        jList1.setIgnoreRepaint(true);
        jList1.setSelectionBackground(new java.awt.Color(149, 200, 216));
        jList1.setVisibleRowCount(5);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel5.setFont(new java.awt.Font("Geneva", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("<html>"
            + "Instructions: A list of 20 foods is displayed below."
            + "<br>Click on the foods in the box below or enter the"
            + "<br>name in the box (not case sensitive) to verify"
            + "<br>whether it is an appetizer, entree, or dessert."
            + "</html>");

        jPanel4.setBackground(new java.awt.Color(149, 200, 216));

        resultLabel.setFont(new java.awt.Font("Geneva", 1, 14)); // NOI18N
        resultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        inputField.setFont(new java.awt.Font("Geneva", 0, 14)); // NOI18N
        inputField.setSelectionColor(new java.awt.Color(149, 200, 216));
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        errorLabel.setFont(new java.awt.Font("Geneva", 1, 14)); // NOI18N
        errorLabel.setForeground(new java.awt.Color(255, 255, 255));
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(errorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * If a list value is clicked
     * @param evt               event occurred 
     */
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (evt.getValueIsAdjusting() == false) {
            String selection = "";
            if (jList1.getSelectedIndex() == -1) {
            } else { // If the selected is not index 0 on the list
                selection = foodList[jList1.getSelectedIndex()];
            }
            // New food object
            Check click = new Check(selection);
            // Calls the checkFieldInput to check and display which course type it is
            boolean success = checkFieldInput(click, 'c', click.getName());
        }
    }//GEN-LAST:event_jList1ValueChanged

    /**
     * If the audience has manually inputted a food
     * @param evt               event occurred
     */
    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFieldActionPerformed
        // Deselects the jList, if the user has previously selected a food
        jList1.clearSelection();
        String audience = inputField.getText().trim();
        // Sets the input to lower case as the input is not case sensitive
        Check input = new Check(audience.toLowerCase());
        // Calls the validInput method to check if input is only characters/spaces
        input.validInput();
        if (input.isValid() == false) {
            errorLabel.setText("Input must only contain letters and white spaces. Please try again.");
        } else { // Checks if the audience input directly matches a food in the list
            boolean success = checkFieldInput(input, 'i', audience);
            if (success != true) { // If doesn't directly match, check if the audience input contains a food in the list
                input.checkIfContains(appetizers, entrees, desserts);
                if (input.isContains() == true) { // If matches, displays the corresponding message
                    displayTextIfFieldInputInvalid("contains", input.getCourse(), audience, "");
                } else { // If doesn't contain a food in the list, checks if the word is a common misspelling of any foods on the list
                    input.checkMisspellings(appetizers, entrees, desserts, misspellings);
                    if (input.isMisspelling() == true) {  // If common misspelling, displays the corresponding message
                        input.checkIfContains(appetizers, entrees, desserts);
                        displayTextIfFieldInputInvalid("misspelling", input.getCourse(), audience, input.getName());
                    } else { // If all checks fail, displays that the food is not on the list and asks the audience to try again
                        errorLabel.setText(audience + " is not on the list. Please try again.");
                        resultLabel.setText("");
                    }
                }
            }
            inputField.setText("");
        }
    }//GEN-LAST:event_inputFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorLabel;
    private javax.swing.JTextField inputField;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel resultLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * Scales the image to fit within the jLabel container dimensions
     */
    private void scaleImage() {
        Image image = null;
        try {
            Image newImage = ImageIO.read(getClass().getResource("/images/Graphic.png"));
            image = newImage.getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_SMOOTH);
        } catch (IOException e) {
        }
        jLabel3.setIcon(new ImageIcon(image));
    }

}
