package GameState;

/**
 *
 * @author Gama
 */
import Manager.LanguageManager;
import Manager.TextManager;
import Manager.TextureManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LDialog extends JDialog {
    
    public LDialog() {
        super(new JFrame(), true);
        setSize(300, 130);
        setBackground(Color.white);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        JPanel panel = new JPanel();
        add(panel);
        
        placeComponents(panel);
        
        setLocationRelativeTo(null);
        setVisible(true);

    }

    
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel lblMessage = new JLabel(LanguageManager.getInstance().get("fail"));
        lblMessage.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
        lblMessage.setBounds(130,0, 100, 40);
        panel.add(lblMessage);
        
        JLabel lblMessage1 = new JLabel(LanguageManager.getInstance().get("fail_message"));
        lblMessage1.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
        lblMessage1.setBounds(40,40, 300, 40);
        panel.add(lblMessage1);
        
//        JTextField txtName = new JTextField();
//        txtName.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
//        txtName.setBounds(105, 7, 180, 25);
//        panel.add(txtName);
        
        JButton btnOk = new JButton("OK");
        btnOk.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
        btnOk.setBounds(20, 80, 260, 30);
        
        btnOk.setBorderPainted(false);
        btnOk.setOpaque(false);
        
        btnOk.setIcon(new ImageIcon(TextureManager.b1));
        btnOk.setRolloverIcon(new ImageIcon(TextureManager.b2));
        btnOk.setPressedIcon(new ImageIcon(TextureManager.b3));
        btnOk.setHorizontalTextPosition(SwingConstants.CENTER);
        
        btnOk.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                dispose();
            } 
        });
        panel.add(btnOk);
    }

    public static void main(String[] args) {
        new LDialog();
    }
}
