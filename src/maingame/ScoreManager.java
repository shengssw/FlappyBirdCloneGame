package maingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import maingame.Game.STATE;

public class ScoreManager extends MouseAdapter{
	
	private Game game;
	private String user;
	private int score;
	private HashMap<String, Integer> topTen;
	private BufferedImage back;
	
	
	public ScoreManager(Game game) {
		this.game = game;
		this.score = 0;
		this.topTen = new HashMap<>();
		loadImage();
		readScoreFromFile();
	}
	
	private void loadImage() {
		try {
			back = ImageIO.read(getClass().getResourceAsStream("/button/back.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void saveCurrentScore() {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		this.user = timestamp;
		AddCurrentScore();
		writeScoreToFile();
	}
	
	public void AddCurrentScore() {
		if(topTen.size() < 10) {
			topTen.put(user, Integer.valueOf(score));
		} else {
			// Find smallest top Ten
			String smallestKey = " ";
			int lowestScore = 0;
			boolean first = true;
			for(String key: topTen.keySet()) {
				if(first) {
					smallestKey = key;
					lowestScore = topTen.get(key).intValue();
					first = false;
				}
				if( lowestScore < topTen.get(key).intValue()) {
					smallestKey = key;
					lowestScore = topTen.get(key).intValue();
				}
			}
			
			// Check if current score is larger than the smallest
			if (this.score > lowestScore) {
				topTen.remove(smallestKey);
				topTen.put(user, Integer.valueOf(score));
			}
		}
	}
	
	public void writeScoreToFile() {
		try {
			File file = game.getFile();
			FileWriter output = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(output);
			//writer.write(user+" "+score+"\n");
			if (topTen.size() <= 10) {
				for(String key: topTen.keySet()) {
					Integer score = topTen.get(key);
					writer.write(key+" "+score.intValue()+"\n");
				}
			} else {
				System.out.println("Something wrong with Top Ten Scores");
			}
			writer.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readScoreFromFile() {
		try {
			File file = game.getFile();
			
			if(file.length()!=0) {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				while(line != null) {
					String[] splited = line.split("\\s+");
					String username = splited[0];
					Integer score =  Integer.valueOf(splited[1]);
					topTen.put(username, score);
					line = reader.readLine();
				}
				
				reader.close();
				System.out.println("Top Ten has " + topTen.size());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 450, 450, 100, 30)) {
			if(game.gameState == STATE.Score) {
				game.gameState = STATE.Menu;
			}
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void increaseScore() {
		this.score += 1;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void resetScore() {
		this.score = 0;
	}
	
	public HashMap<String, Integer> sortByValue() {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(this.topTen.entrySet());
		
		Collections.sort(
				list,
				(i1, i2)-> i1.getValue().compareTo(i2.getValue()));

		HashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry: list) {
			result.put(entry.getKey(), entry.getValue());
		}
		
		return result;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		if (game.gameState == STATE.Game) {
			g.setColor(Color.white);
			g.drawString("SCORE: " + score, 30, 30);	
		} else if (game.gameState == STATE.Score) {
			Graphics2D g2 = (Graphics2D) g;
			
			if(topTen.size() > 0) {
				HashMap<String, Integer> r = sortByValue();
				g2.setColor(Color.white);
				g2.setFont(new Font("TimesRoman", Font.PLAIN, 30));
				
				int count = 0;
				for(String key: r.keySet()) {
					g2.drawString(key+"   "+r.get(key).intValue(), 100, 150+count*50);
					count++;
				}
				
				
			} else {
				g2.setColor(Color.white);
				g2.drawString("No records available", 200, 240);
			}
			
			g2.drawImage(back, 450, 450, 100, 30, null);
			
		}
		
	}

}
