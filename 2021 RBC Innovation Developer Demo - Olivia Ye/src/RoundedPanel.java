
import java.awt.*;
import javax.swing.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author oliviaye
 */
public class RoundedPanel extends JPanel {

        protected int strokeSize = 1;
        protected boolean highQuality = true;
        // Double values for Horizontal and Vertical radius of corner arcs
        protected Dimension arcs;

        /**
         * Constructor
         * @param number            arc radius
         */
        public RoundedPanel(int number) {
            super();
            setOpaque(false);
            arcs = new Dimension(number, number);
        }

        // Overrides the original code to create the component
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;

            //Sets antialiasing if high quality
            if (highQuality) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
            }

            //Draws the rounded opaque panel with borders
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width,
                    height, arcs.width, arcs.height);
            graphics.setColor(getForeground());
            graphics.setStroke(new BasicStroke(strokeSize));
            graphics.drawRoundRect(0, 0, width,
                    height, arcs.width, arcs.height);

            // Sets strokes to default
            graphics.setStroke(new BasicStroke());
        }

    }
