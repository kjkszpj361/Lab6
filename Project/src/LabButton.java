import javax.swing.*;
import java.util.TreeSet;

/**
 * Created by Денис on 25.04.2017.
 */
public class LabButton extends JButton{
    private JPanel ButtonPanel;

    private JTextField ButtonTextField;

    LabButton(String title, TreeSet<Human> col,LabTable table,String type){
        super(title);
        switch (type){
            case "Rm":{ButtonTextField=new JTextField("Введите объект в формате JSON",50);
                addActionListener(new RemoveListener(ButtonTextField,col,table));
            }break;
            case "RmL":{ButtonTextField=new JTextField("Введите объект в формате JSON",50);
                addActionListener(new RemoveLowerListener(ButtonTextField,col,table));
            }break;
            case "Imp":{ButtonTextField=new JTextField("Введите адрес файла в фигурных скобках",50);
                addActionListener(new ImportListener(ButtonTextField,col,table));}
        }
        ButtonPanel=new JPanel();
        ButtonPanel.add(ButtonTextField);
        ButtonPanel.add(this);
    }

    public JPanel getButtonPanel() {
        return ButtonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        ButtonPanel = buttonPanel;
    }

    public JTextField getButtonTextField() {
        return ButtonTextField;
    }

    public void setButtonTextField(JTextField buttonTextField) {
        ButtonTextField = buttonTextField;
    }
}