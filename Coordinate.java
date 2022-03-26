import java.util.ArrayList;

public class Coordinate {
    public static class DistancePair {
        private double distance;
        private Coordinate coordinate;

        public DistancePair(double distance, Coordinate coordinate) {
            this.distance = distance;
            this.coordinate = coordinate;
        }

        public double getDistance() {
            return this.distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public Coordinate getCoordinate() {
            return this.coordinate;
        }

        public void setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
        }
    }

    public static class PointCenterPair {
        private Coordinate center;
        private ArrayList<Coordinate> points;

        public PointCenterPair(Coordinate center, ArrayList<Coordinate> point) {
            this.center = center;
            this.points = point;
        }

        public Coordinate getCenter() {
            return this.center;
        }

        public void setCenter(Coordinate center) {
            this.center = center;
        }

        public ArrayList<Coordinate> getPoints() {
            return this.points;
        }

        public void setPoints(ArrayList<Coordinate> points) {
            this.points = points;
        }
    }

    private double x, y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double evalDistance(Coordinate c1, Coordinate c2) {
        return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2));
    }

    public static Coordinate getAverageCoordinate(ArrayList<Coordinate> cList) {
        double sumX = 0, sumY = 0;
        for (Coordinate c : cList) {
            sumX += c.x;
            sumY += c.y;
        }
        return new Coordinate((sumX / cList.size()), (sumY / cList.size()));
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
