import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class KMeansClustering {
    private int totalIterations;
    private int totalCenters;
    private ArrayList<Coordinate> centerCoordinates = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> pointCoordinates = new ArrayList<Coordinate>();
    private ArrayList<Coordinate.PointCenterPair> pairs = new ArrayList<Coordinate.PointCenterPair>();
    private File f = null;

    private final int CENTER_RADIUS = 12, POINT_RADIUS = 10;
    private final int WIDTH = 1200, HEIGHT = 700;
    private final Color CENTER_COLOR = Color.CYAN;
    private final Color[] colors = { Color.BLACK, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK,
            Color.RED, Color.GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY };

    public KMeansClustering(int totalIterations, int totalCenters, File f) {
        this.totalIterations = totalIterations;
        this.totalCenters = totalCenters;
        this.f = f;

        this.setPointCoordinates();
    }

    private void setPointCoordinates() {
        try {
            Scanner fileScanner = new Scanner(this.f);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                int cIndex = line.indexOf(",");
                double x = Double.parseDouble(line.substring(0, cIndex));
                double y = Double.parseDouble(line.substring(cIndex + 1, line.length()));
                this.pointCoordinates.add(new Coordinate(x, y));
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setRandomCenterCoordinates() {
        for (int i = 0; i < this.totalCenters; i++) {
            this.centerCoordinates
                    .add(new Coordinate((Math.random() * this.WIDTH), (Math.random() * this.HEIGHT)));
            this.pairs.add(new Coordinate.PointCenterPair(this.centerCoordinates.get(i), new ArrayList<Coordinate>()));
        }
    }

    public void setAverageCenterCoordinates() {
        for (int i = 0; i < this.centerCoordinates.size(); i++) {
            this.centerCoordinates.set(i, Coordinate.getAverageCoordinate(this.pairs.get(i).getPoints()));
            this.pairs.set(i,
                    new Coordinate.PointCenterPair(this.centerCoordinates.get(i), new ArrayList<Coordinate>()));
        }
    }

    public void cluster(JFrame frame) {
        this.setRandomCenterCoordinates();
        for (int i = -1; i < this.totalIterations; i++) {
            // paint centers on frame (on last iteration)
            if (i == this.totalIterations - 1) {
                for (Coordinate c : this.centerCoordinates) {
                    frame.add(new Circle(c, this.CENTER_COLOR, this.CENTER_RADIUS));
                }
            }
            for (Coordinate c1 : this.pointCoordinates) {
                // calculate the distance of the point to all centers
                ArrayList<Coordinate.DistancePair> distances = new ArrayList<Coordinate.DistancePair>();
                for (Coordinate c2 : this.centerCoordinates) {
                    distances.add(new Coordinate.DistancePair(Coordinate.evalDistance(c1, c2), c2));
                }
                // get minimum distance and center coordinate
                Coordinate.DistancePair minDistance = distances.get(0);
                for (Coordinate.DistancePair dp : distances) {
                    if (dp.getDistance() < minDistance.getDistance())
                        minDistance = dp;
                }
                // add point-center pair to the list
                for (int j = 0; j < this.pairs.size(); j++) {
                    if (this.pairs.get(j).getCenter() == minDistance.getCoordinate()) {
                        this.pairs.get(j).getPoints().add(c1);
                    }
                }
                // paint points on frame (on last iteration)
                if (i == this.totalIterations - 1)
                    frame.add(new Circle(new Coordinate(c1.getX(), c1.getY()),
                            colors[centerCoordinates.indexOf(minDistance.getCoordinate())], this.POINT_RADIUS));
            }
            this.setAverageCenterCoordinates();
            // this.resetCenterCoordinates();
        }
    }

    // public void resetCenterCoordinates() {
    // this.centerCoordinates = new ArrayList<Coordinate>();
    // }

    public ArrayList<Coordinate> getCenterCoordinates() {
        return this.centerCoordinates;
    }

    public ArrayList<Coordinate> getPointCoordinates() {
        return this.pointCoordinates;
    }
}
