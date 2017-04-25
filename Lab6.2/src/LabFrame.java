import javax.swing.*;
import java.awt.*;
import java.util.TreeSet;

/**
 * Created by Денис on 24.04.2017.
 */
public class LabFrame extends JFrame {
    //JPanel panel=new JPanel();
    LabTable table;

    public LabTable getTable() {
        return table;
    }

    public void setTable(LabTable table) {
        this.table = table;
    }

    LabFrame(String title, LabCollection labCollection){
        super(title);
        setLayout(new FlowLayout());
        JTextPane textPane = new JTextPane();
        table=new LabTable(labCollection.getUselessData());
        RemoveButton rButton=new RemoveButton("Remove",labCollection.getUselessData(),table, textPane);
        JButton saveButton=new JButton("Save");
        textPane.setText("Здесь будет вывод ошибок и результатов обработок команд");
        saveButton.addActionListener(new SaveListener(labCollection));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel=new JPanel();
        panel.add(rButton.rmTextField);
        panel.add(rButton);
        add(panel);
        add(saveButton);
        add(new JScrollPane(new JTable(table)));
        add(textPane);
        pack();

    }

}