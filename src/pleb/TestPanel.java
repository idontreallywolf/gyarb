package pleb;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TestPanel(Game game) {
		setBorder(new LineBorder(Color.ORANGE));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JProgressBar healthBar = new JProgressBar();
		healthBar.setValue(100);
		GridBagConstraints gbc_healthBar = new GridBagConstraints();
		gbc_healthBar.insets = new Insets(0, 10, 5, 10);
		gbc_healthBar.anchor = GridBagConstraints.WEST;
		gbc_healthBar.gridx = 0;
		gbc_healthBar.gridy = 0;
		add(healthBar, gbc_healthBar);
		
		JButton btnQuitGame = new JButton("Quit");
		btnQuitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnSave = new JButton("Save");
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 0;
		add(btnSave, gbc_btnSave);
		GridBagConstraints gbc_btnPause = new GridBagConstraints();
		gbc_btnPause.insets = new Insets(0, 0, 5, 5);
		gbc_btnPause.gridx = 4;
		gbc_btnPause.gridy = 0;
		add(btnPause, gbc_btnPause);
		GridBagConstraints gbc_btnQuitGame = new GridBagConstraints();
		gbc_btnQuitGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuitGame.gridx = 5;
		gbc_btnQuitGame.gridy = 0;
		add(btnQuitGame, gbc_btnQuitGame);
		
		JProgressBar shieldBar = new JProgressBar();
		shieldBar.setValue(10);
		GridBagConstraints gbc_shieldBar = new GridBagConstraints();
		gbc_shieldBar.insets = new Insets(0, 10, 5, 10);
		gbc_shieldBar.gridx = 0;
		gbc_shieldBar.gridy = 1;
		add(shieldBar, gbc_shieldBar);
		
		JPanel panelGame = new JPanel();
		panelGame.setBackground(Color.WHITE);
		GridBagConstraints gbc_panelGame = new GridBagConstraints();
		gbc_panelGame.gridwidth = 7;
		gbc_panelGame.fill = GridBagConstraints.BOTH;
		gbc_panelGame.gridx = 0;
		gbc_panelGame.gridy = 2;
		add(panelGame, gbc_panelGame);
		
		
		panelGame.add(game);
		game.start();
		

	}

}
