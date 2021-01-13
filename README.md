# TrainRoutes
This program utilizes Djikstra's algorithm and A* to find the ideal train route between any two cities.

“rrNodes.txt” contains a list of nodes representing the Chicago Train Authority’s dataset of train junctions in the US, Canada, and Mexico, with an id tag and then latitude and longitude location.
• “rrEdges.txt” contains a list of connected junctions as pairs of id tags, thus describing all available train routes.
• “rrNodeCity.txt” identifies certain junctions with names (these are the junctions located in major cities). These
cities will be the available options for starting and ending points you might be given.

The data from these files will be read to create a weighted grap representing it. Robert Sedgewick and Kevin Wayne's implementation of great circle distance in used as a consistent, admissible heuristic.  
