package openworld.gui;

import openworld.SoundEffects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class EnvironmentPanel extends JPanel implements ActionListener {
	
	private GameWorld gameWorld;
	
	private JButton respawnButton, startButton, stopButton;
	private boolean moving = false;
	
	public EnvironmentPanel(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(new TitledBorder("Monster and NPC controls"));
		
		respawnButton = new JButton("Respawn all");
		respawnButton.addActionListener(this);
		startButton = new JButton("Start moving");
		startButton.addActionListener(this);
		stopButton = new JButton("Stop moving");
		stopButton.addActionListener(this);
		
		add(respawnButton);
		add(startButton);
		add(stopButton);
	}


		@Override
		public void actionPerformed (ActionEvent e) {

            Object source = e.getSource();

			if (source == respawnButton) {
                gameWorld.respawnWorld();

            } else if (source == startButton) {
                if (!moving) {
                    this.gameWorld.gameThread.start();
                    moving = true;
					SoundEffects startGame = new SoundEffects("SFX/startGame.wav");
					try {
						startGame.playSound();
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}

            } else if (source == stopButton) {

				if (moving){
					this.gameWorld.gameThread.interrupt();
					try {
						this.gameWorld.gameThread.join();
					} catch (InterruptedException ignored) {}
					moving = false;
                    this.gameWorld.gameThread = new Thread(this.gameWorld);
				}
            }
        }

	public void disableAll() {
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		respawnButton.setEnabled(false);
	}

}
