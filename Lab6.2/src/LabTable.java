import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Created by Денис on 25.04.2017.
 */
public class LabTable extends AbstractTableModel implements TableModel {

    private TreeSet<Human> Humans;

    LabTable(TreeSet<Human> Humans){
        super();
        this.Humans=Humans;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex!=0){
        return true;
        }else return false;
    }

    @Override
    public String getColumnName(int column) {
        String result="";
        switch (column) {
            case 0:
                result="№";
                break;
            case 1:
                result = "Name";
                break;
            case 2:
                result = "Age";
                break;
            case 3:
                result = "Location";
                break;
        }
          //  case 3:return("Gender");
            return result;
    }

    @Override
    public int getRowCount() {
        return Humans.size();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(aValue!=null && aValue.toString().length()>0) {
                Human[] arr = Humans.toArray(new Human[Humans.size()]);
                switch (columnIndex) {
                    case 1:
                        if (Pattern.compile("[A-zА-я']+").matcher(aValue.toString()).matches()) {
                            makeUpdateCall(arr[(int) getValueAt(rowIndex, 0) - 1],1,aValue);
                            /*Humans.remove(arr[(int) getValueAt(rowIndex, 0) - 1]);
                            Humans.add(new Human((String) aValue, arr[(int) getValueAt(rowIndex, 0) - 1].getAge(), arr[(int) getValueAt(rowIndex, 0) - 1].getLocation()));*/
                        }else{System.out.print("Поле \"Имя\" не может являться пустым.В имени могут содержаться только символы кириллицы и латинского алфавита");}
                        break;
                    case 2:
                        if((int)aValue>=0 && (int) aValue<=120) {
                            makeUpdateCall(arr[(int) getValueAt(rowIndex, 0) - 1],2,aValue);
                            /*Humans.remove(arr[(int) getValueAt(rowIndex, 0) - 1]);
                            Humans.add(new Human(arr[(int) getValueAt(rowIndex, 0) - 1].getName(), (int) aValue, arr[(int) getValueAt(rowIndex, 0) - 1].getLocation()));*/
                        }
                        else
                        {
                            System.out.print("Возраст может быть в пределах от 0 до 120");
                        }
                        break;
                    case 3:
                        makeUpdateCall(arr[(int) getValueAt(rowIndex, 0) - 1],3,aValue);
                        if (Pattern.compile("[A-zА-я0-9\\-_]+").matcher(aValue.toString()).matches()) {
                            /*Humans.remove(arr[(int) getValueAt(rowIndex, 0) - 1]);
                            Humans.add(new Human(arr[(int) getValueAt(rowIndex, 0) - 1].getName(), arr[(int) getValueAt(rowIndex, 0) - 1].getAge(), (String) aValue));*/
                        }else{System.out.print("Поле \"Имя\" не может являться пустым.В имени могут содержаться только символы кириллицы и латинского алфавита");}
                        break;
                }
                fireTableDataChanged();
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        int i=0;
        Human[] arr=new Human[Humans.size()];
        for(Human a:Humans){
            arr[i]=new Human();
            arr[i].setAge(a.getAge());
            arr[i].setName(a.getName());
            arr[i].setLocation(a.getLocation());
            //arr[i].setGender(a.getGender());
            i++;
        }
        if(getHumans().size()!=0)
        switch(columnIndex){
            case 0:{return rowIndex+1;}
            case 1:{return arr[rowIndex].getName();}
            case 2:{return arr[rowIndex].getAge();}
            case 3:{return arr[rowIndex].getLocation();}
           // case 3:{return arr[rowIndex].getGender()? "Male":"Female";}
            default:{return null;}
        }else return(null);
    }

    @Override
    public int getColumnCount() {
        return 4; //4;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class returnValue;
        switch (columnIndex){
            case 0: returnValue=Integer.class; break;
            case 1: returnValue=String.class; break;
            case 2: returnValue=Integer.class; break;
            case 3: returnValue=String.class; break;
            default:returnValue=String.class; break;
        }
        return returnValue;
    }

    public TreeSet<Human> getHumans() {
        return Humans;
    }

    protected void makeUpdateCall(Human object,int attributeNumber,Object attributeValue){
        try {
            SocketAddress address = new InetSocketAddress(ConsoleApp.HOSTNAME, 8885);
            DatagramSocket clientSocket = new DatagramSocket();
            byte[] sendData;
            byte[] receiveData = new byte[1024];
            String sentence = "update " + object.toString()+" "+attributeNumber+" "+attributeValue;
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.print("FROM SERVER:" + modifiedSentence);
            clientSocket.close();
        }catch(Exception e){e.printStackTrace();}
    }
}
