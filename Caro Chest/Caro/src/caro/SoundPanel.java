package caro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SoundPanel extends JPanel {

    JButton soundButton;

    public SoundPanel() {
        setLayout(null);
        setBounds(720, 10, 60, 60);

        Icon bug1 = new ImageIcon(getClass().getResource("picture/OnSound.png"));
        soundButton = new JButton(bug1);
        soundButton.setBorderPainted(false);
        soundButton.setBounds(0, 0, 60, 60);
        soundButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });

        add(soundButton);

    }

}
