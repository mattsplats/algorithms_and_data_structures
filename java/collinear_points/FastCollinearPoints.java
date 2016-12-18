import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
   private final Point[]       pts;           // copy of input array
   private final LineSegment[] lineSegments;  // set of lines to be drawn
   
   // finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
      if (points == null) throw new NullPointerException();
      
      ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
      
      pts = points.clone();
      Point[] ptsCopy = new Point[pts.length - 1];
      
      Arrays.sort(pts);
      
      for (int i = 0; i < pts.length; i++) {
         int j = 0, k = 0;
         while (j < pts.length - 1) {
            if (i != k) {
               ptsCopy[j] = pts[k];
               j++;
            }
            k++;
         }
         
         Arrays.sort(ptsCopy, pts[i].slopeOrder());
         
         for (j = 0; j < ptsCopy.length - 1; ) {  // No ++ here, see end of loop
            double slopeToJ = pts[i].slopeTo(ptsCopy[j]);
            int count = 1;
            
            if (slopeToJ == Double.NEGATIVE_INFINITY)
               throw new IllegalArgumentException();
            
            // If next element in ptsCopy is in order and has same slope
            k = j + 1;
            if (pts[i].compareTo(ptsCopy[j]) <= 0 &&
                slopeToJ == pts[i].slopeTo(ptsCopy[k])) {
//               StdOut.println(pts[i].toString());
//               StdOut.println(ptsCopy[j].toString());
//               StdOut.println(ptsCopy[k].toString());
//               StdOut.println("");
               
               // While there are points left, points are in order, same slope
               while (k < ptsCopy.length &&
                   ptsCopy[j].compareTo(ptsCopy[k]) <= 0 &&
                   slopeToJ == pts[i].slopeTo(ptsCopy[k])) {
                  count++;
                  k++;
               }
            
            // If there are 4 or more contiguous same-slope points
               if (count >= 3) {
//                  StdOut.println("Pre-sort:");
//                  StdOut.println(pts[i].toString());
//                  for (int x = 0; x < count; x++) {
//                     StdOut.println(ptsCopy[j + x].toString());
//                  }
                  
//                  Point[] testPoints = new Point[count + 1];
//                  int y = 1;
                  
//                  testPoints[0] = pts[i];
//                  for (int x = 0; x < count; x++) {
//                     testPoints[y] = ptsCopy[j + x];
//                     y++;
//                  }
                  
//                  Arrays.sort(testPoints);
                  
//                  StdOut.println("Post-sort:");
//                  for (Point p : testPoints) {
//                     StdOut.println(p.toString());
//                  }
//                  StdOut.println("");
                  
//                  StdOut.println("New line!");
//                  StdOut.println("");
                  lines.add(new LineSegment(pts[i], ptsCopy[k - 1]));
                  
//                  testPoints = null;
               }
            }
            // Jump past this set of contiguous same-slope points to next point
            j = k;
         }
      }
      
      lineSegments = lines.toArray(new LineSegment[lines.size()]);
   }
   
   // the number of line segments
   public int numberOfSegments() {
      return lineSegments.length;
   }
   
   // the line segments
   public LineSegment[] segments() {
      return lineSegments.clone();
   }
   
   // test client
   public static void main(String[] args) {
      // read the n points from a file
      In in = new In(args[0]);
      int n = in.readInt();
      Point[] points = new Point[n];
      for (int i = 0; i < n; i++) {
          int x = in.readInt();
          int y = in.readInt();
          points[i] = new Point(x, y);
      }

      // draw the points
      StdDraw.enableDoubleBuffering();
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      for (Point p : points) {
          p.draw();
      }
      StdDraw.show();

      // print and draw the line segments
      FastCollinearPoints collinear = new FastCollinearPoints(points);
      for (LineSegment segment : collinear.segments()) {
//          StdOut.println(segment);
          segment.draw();
      }
      StdDraw.show();
   }
}
