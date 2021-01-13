public class GreatCircle { 
    public static void main(String[] args) { 
    
    
        double x1 = Math.toRadians(Double.parseDouble(args[0]));
        double y1 = Math.toRadians(Double.parseDouble(args[1]));
        double x2 = Math.toRadians(Double.parseDouble(args[2]));
        double y2 = Math.toRadians(Double.parseDouble(args[3]));

       /*************************************************************************
        * Compute using law of cosines
        *************************************************************************/
        // great circle distance in radians
        double angle1 = Math.acos(Math.sin(x1) * Math.sin(x2)
                      + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));

        // convert back to degrees
        angle1 = Math.toDegrees(angle1);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance1 = 60 * angle1;

        System.out.println(distance1 + " nautical miles");

        }
       /*************************************************************************
        * Compute using Haversine formula
        *************************************************************************/
        public static double getDistance(double x1, double y1, double x2, double y2)
        {
        
        x1 = Math.toRadians((x1));
        y1 = Math.toRadians((y1));
        x2 = Math.toRadians((x2));
        y2 = Math.toRadians((y2));

        
        double a = Math.pow(Math.sin((x2-x1)/2), 2)
                 + Math.cos(x1) * Math.cos(x2) * Math.pow(Math.sin((y2-y1)/2), 2);

        // great circle distance in radians
        double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

        // convert back to degrees
        angle2 = Math.toDegrees(angle2);

        // each degree on a great circle of Earth is 60 nautical miles
        double distance2 = 60 * angle2;

        //System.out.println(distance2 + " nautical miles");
        
        return distance2; 
        
        }
    }

