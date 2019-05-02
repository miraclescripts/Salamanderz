package scripts;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HunterUI extends JFrame {

	private JPanel contentPane;
	private JTextField colorField;

	/**
	 * Create the frame.
	 */
	public HunterUI(final HunterHelper hhp) {
		setTitle("Trap Star Pro - Miracle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 345, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectTheLizard = new JLabel("Enter the Color:");
		lblSelectTheLizard.setBounds(122, 12, 159, 14);
		contentPane.add(lblSelectTheLizard);
		
		colorField = new JTextField();
		colorField.setBounds(10, 36, 309, 20);
		contentPane.add(colorField);
		colorField.setColumns(10);
		
		JButton start = new JButton("START TRAPPIN'");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hhp.lizardType = hhp.determineType(colorField.getText());
				
				dispose();
			}
		});
		start.setBounds(10, 59, 309, 39);
		contentPane.add(start);
	}
}
