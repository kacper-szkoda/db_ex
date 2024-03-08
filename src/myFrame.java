import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class myFrame extends JFrame implements ActionListener
{
    private JScrollPane scrollPane;
    private JPanel jPanel2;
    private JTextField to_seek;
    private ArrayList<JValueSet> data;
    private JButton update_button;
    public myFrame()
    {
        data = new ArrayList<JValueSet>();
        //Set up frame with 2 panels, one for control, one for display
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1080, 1080);
        this.setTitle("Sensor Overview");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        this.getContentPane().setBackground(new Color(173, 188, 159));
        //Note, WrapLayout is a piece of open-source code. FlowLayout would try to put all components
        //in one row, which is not suited for a scrollable panel, WrapLayout specifically addresses that issue
        JPanel jPanel = new JPanel(new WrapLayout(FlowLayout.CENTER,400, 40));
        jPanel.setBackground(new Color(156, 169, 143));
        jPanel.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        this.add(jPanel, BorderLayout.SOUTH);
        jPanel2 = new JPanel(new WrapLayout(FlowLayout.CENTER, 100, 40));
        jPanel2.setBackground(new Color(173, 188, 159));
        this.add (jPanel2, BorderLayout.CENTER);

        //Set up button for updating
        update_button = new JButton();
        update_button.addActionListener(this);
        update_button.setFocusable(false);
        update_button.setText("Update");
        update_button.setFont(new Font("Helvetica", Font.PLAIN, 30));
        update_button.setForeground(new Color(18, 55, 42));
        update_button.setBackground(new Color(140, 152, 129));
        update_button.setBorder(BorderFactory.createEtchedBorder(new Color(67, 104, 80),new Color(18, 55, 42)));
        update_button.setPreferredSize(new Dimension(200, 75));

        //Set up a text field for user input of the sought after measurement unit
        to_seek = new JTextField();
        to_seek.setPreferredSize(new Dimension(200,80));
        to_seek.setFont(new Font("Helvetica", Font.PLAIN, 30));
        to_seek.setHorizontalAlignment(JTextField.CENTER);

        //Make the panel scrollable, so that the app does not break with
        //larger number of sensors stored in the database
        // (can be tested with hPa or by adding more to the db)
        scrollPane = new JScrollPane(jPanel2);
        scrollPane.setPreferredSize(new Dimension(jPanel2.getSize()));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.add(scrollPane);

        //Add all components and prepare frame for being shown
        jPanel.add(to_seek);
        jPanel.add(update_button);
        this.pack();
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void update()
    {
        //Call control layer to update the data based on textfield
        String key = to_seek.getText();
        jPanel2.removeAll();
        data.clear();
        data = DB_ex.parseJSON(DB_ex.makeGETRequest("https://studev.groept.be/api/a23ib2a01/dbtask/" + key), key );
        for (JValueSet point : data)
        {
            jPanel2.add(point);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Call update and refresh frame
        if (e.getSource() == update_button)
        {
            update();
            jPanel2.revalidate();
            scrollPane.revalidate();
        }
    }
}
