import com.xcodiq.taterunner.entity.bound.point.BoundingPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends JPanel {

	private final List<BoundingPoint> points = new ArrayList<>();

	public Main(List<BoundingPoint> boundingPoints) {
		this.points.addAll(boundingPoints);
	}

	public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
		JFrame frame = new JFrame();
		frame.setTitle("Polygon");
		frame.setSize(250, 250);

//		List<BoundingPoint> boundingPoints = new ArrayList<>();

//		final String fileName = "test.bounds";
//		final Scanner scanner = new Scanner(Main.class.getClassLoader().getResource(fileName).openStream());
//		while (scanner.hasNext()) {
//			final String pointLine = scanner.next();
//			final String[] coords = pointLine.split(",");
//			final String xStr = coords[0], yStr = coords[1];
//			final int x = Integer.parseInt(xStr), y = Integer.parseInt(yStr);
//			System.out.println("x: " + x + ", y: " + y);
//
//			boundingPoints.add(new BoundingPoint(x, y));
//		}

//		final Path of = Path.of(Main.class.getClassLoader().getResource("test.bounds").getPath());
		ClassLoader.getSystemResource("test.bounds");
		int width = 171;
		int height = 181;

		final Path of = Paths.get(ClassLoader.getSystemResource("test.bounds").toURI());
		final List<BoundingPoint> boundingPoints = Files.readAllLines(of).stream().map(line -> {
			final String[] split = line.split(";");
			return new BoundingPoint((int) (Float.parseFloat(split[0]) * width), (int) (Float.parseFloat(split[1]) * height));
		}).peek(boundingPoint -> {
			System.out.println("x: " + boundingPoint.x() + ", y: " + boundingPoint.y());
		}).toList();


		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		Container contentPane = frame.getContentPane();
		contentPane.add(new Main(boundingPoints));
		frame.setVisible(true);
		while (true) {
			frame.repaint();
			Thread.sleep(100);
		}
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Polygon p = new Polygon();

		for (BoundingPoint point : points) {
			p.addPoint(point.x(), point.y());
		}

//		p.addPoint(112, 0);
//		p.addPoint(47, 44);
//		p.addPoint(40, 80);
//		p.addPoint(10, 116);
//		p.addPoint(0, 181);
//		p.addPoint(171, 181);
//		p.addPoint(157, 166);
//		p.addPoint(158, 114);
//		p.addPoint(145, 105);
//		p.addPoint(137, 72);
//		p.addPoint(135, 39);
//		p.addPoint(125, 40);
//		p.addPoint(124, 15);

		g.drawPolygon(p);
	}

	public record BoundingPoint(int x, int y) {
	}
}