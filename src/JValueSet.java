import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class JValueSet extends JLabel {
    private float maxValue;
    private String location;
    private String measurementUnit;
    private float lastValue;

    public JValueSet(float maxValue, String location, String measurementUnit, float lastValue)
    {
        //Implement a custom label to store sensor values
        this.maxValue = maxValue;
        this.location = location;
        this.measurementUnit = measurementUnit;
        this.lastValue = lastValue;
        this.setText("<html>" +"Location: " + location + "<br>" + "Current: " + lastValue + "<br>" + "Max: " + maxValue + "<html>");
        this.setBackground(new Color(67, 104, 80));
        this.setVisible(true);
        this.setForeground(Color.black);
        this.setBackground(new Color(173, 188, 159));
        this.setBorder(new BevelBorder(BevelBorder.RAISED));
        this.setPreferredSize(new Dimension(300, 200));
        this.setFont(new Font("Helvetica", Font.PLAIN, 30));
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
