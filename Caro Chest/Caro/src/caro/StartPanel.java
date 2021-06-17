package caro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
public class StartPanel extends JPanel {


    public StartPanel() {
        setLayout(null);
        setBounds(0, 0, 800, 600);

        /* hình nền cho Game : 
         đối tượng tự tạo ImagePanel - một panel có chức năng load ảnh */
        ImagePanel background = new ImagePanel("picture/StartGame.png", 0, 0, 800, 600);

        //Thêm các button chức năng
        JButton LANButton = new JButton("Vào chơi");
        JButton exitButton = new JButton("Thoát");

        // định vị trí các button
        LANButton.setBounds(350, 400, 100, 30);
        exitButton.setBounds(350, 450, 100, 30);


        /* thêm hành động cho LAN GAME button : 
         2 người chơi kết nối 2 máy qua mạng LAN  */
        LANButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                /* chương trình gỡ bỏ chế độ  START MENU, 
                 thay vào là menu kết nối mạng LAN 
                 */
                Main.myFrame.remove(Main.myStartPanel);
                NetworkPanel.joinButton.setEnabled(true);
                Main.myFrame.add(Main.myNetworkPanel);
                Main.myFrame.repaint();

            }
        });

        // thêm hành động cho EXIT button 
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(LANButton);
        add(exitButton);

        this.add(background);

    }

}
