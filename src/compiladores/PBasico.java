package compiladores;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.FontUIResource;

public class PBasico extends JFrame implements ActionListener{
    JLabel inst;
    JTextField Caracter;
    JButton aceptar, cancelar;
    JPanel arriba, abajo;
    AFN a = new AFN();
    int index;
    
    public PBasico(int n){
        index=n;
        inst = new JLabel("AFN No." + index + ": \n Coloca tu caracter");
        inst.setFont(new Font("Serif", Font.BOLD, 25));
        Caracter = new JTextField(20);
        aceptar = new JButton("Aceptar");
        cancelar = new JButton("Cancelar");
        aceptar.addActionListener(this);
        cancelar.addActionListener(this);
        arriba=new JPanel();
        abajo=new JPanel();
        arriba.add(inst);arriba.add(Caracter);
        abajo.add(aceptar);abajo.add(cancelar);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center",arriba);
        getContentPane().add("South",abajo);
        setSize(700,500);
	    setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e ){
        if(e.getSource()==aceptar){
            a = a.CrearAFNBasico(Caracter.getText().charAt(0));
            JOptionPane.showMessageDialog(this, "Se creo el AFN Básico");
            setVisible(false);
            dispose();
        }
        if(e.getSource()==cancelar){
            setVisible(false);
            dispose();
        }
    }
}
