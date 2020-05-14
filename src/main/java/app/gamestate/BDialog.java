package app.gamestate;

/**
 *
 * @author Gama
 */
import app.manager.LanguageManager;
import app.manager.TextManager;
import app.manager.TextureManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BDialog extends JDialog {
    
    private int score;

    public BDialog(int score) {
        super(new JFrame(), true);
        setSize(300, 85);
        this.score = score;
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

        JLabel lblMessage = new JLabel(LanguageManager.getInstance().get("name"));
        lblMessage.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
        lblMessage.setBounds(10,0, 100, 40);
        panel.add(lblMessage);
        
        JTextField txtName = new JTextField();
        txtName.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
        txtName.setBounds(105, 7, 180, 25);
        panel.add(txtName);
        
        JButton btnOk = new JButton("OK");
        btnOk.setFont(TextManager.getFont(TextManager.BLOCKTOPIA, 20));
        btnOk.setBounds(20, 40, 260, 30);
        
        btnOk.setBorderPainted(false);
        btnOk.setOpaque(false);
        
        btnOk.setIcon(new ImageIcon(TextureManager.b1));
        btnOk.setRolloverIcon(new ImageIcon(TextureManager.b2));
        btnOk.setPressedIcon(new ImageIcon(TextureManager.b3));
        btnOk.setHorizontalTextPosition(SwingConstants.CENTER);
        
        btnOk.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (!txtName.getText().trim().isEmpty()){
                    addScore(txtName.getText().trim());
                    dispose();
                }
            } 
        });
        panel.add(btnOk);
    }

    public static void main(String[] args) {
        new BDialog(3);
    }
    
    private void addScore(String name){
        try {
            ArrayList<String[]> lines = new ArrayList<>();
            String aux[];
            PrintWriter pw;
            File file = new File(".score");
            if (file.exists()){
                Scanner sc = new Scanner(file);
                while(sc.hasNext()){
                    lines.add(sc.nextLine().split("="));
                }
                sc.close();
            }
            else{
                pw = new PrintWriter(file);
                pw.println(name+"="+score);
                pw.close();
                return;
            }
            lines.add(new String[]{name, String.valueOf(score)});
            for (int i = 0; i < lines.size()-1; i++) {
                for (int j = 0; j < lines.size()-1; j++) {
                    if (Integer.parseInt(lines.get(j)[1]) <= Integer.parseInt(lines.get(j+1)[1])){
                        aux = lines.get(j);
                        lines.set(j, lines.get(j+1));
                        lines.set(j+1, aux);
                    }
                }
            }
            pw = new PrintWriter(file);
            for (int i = 0; i < (lines.size()<=6? lines.size(): 6); i++) {
                pw.println(lines.get(i)[0]+"="+lines.get(i)[1]);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
