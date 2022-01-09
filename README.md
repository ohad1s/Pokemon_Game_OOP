# Pokemon_Game_OOP
![Pokemon_logo](https://user-images.githubusercontent.com/92723105/148658959-558eb7eb-2f70-47a2-a237-fb29ae84363f.jpg)
# About This Project:

In this project we implemented a Pokemon Game based on directed weighted graph and graph algorithms.
The Game include Server (jar file) that place the pokemons on the graph and give us all the data we need about them.
In "StudentCode" we implemented an algorithm to place the Agents and then chhose their next steps in order to catch the pokemons.

# More about the game:
As i said before, there is a server that "manage the game".
In order to play, you should connect to the server from the cmd with the command: "java -jar Ex4_Server_v0.0.jar <LEVEL>"
The game is played "offline"- it means we wrote the algorithm in java and than we see the outcome of my code.
The game has 16 levels (0-15), every level include a different number of ageants and pokemons, and a different graph.
The game screen contains a directed weighted graph, the pokemons are located on the graph edges.
Every pokemon has a different value and - when you catch one- his value is added to your score.
Durations of the games are between 30-60 seconds, and there is an important rule - you can only do 10 moves (of the agants) per second.
The Game is finished when the time is over and then you can see your score and moves.

# Algorithms That Are Implemented on the graph in order to show good performence:

Shortest Path between two vertices - Finding the path and distance using Dijkstra's algorithm.

Finding the Graph Center - Finding the vertex in the graph, that has the minimal distance to the farthest vertex using Dijkstra's algorithm.

Finding Shortest Path For List of Vertices - Using a greedy algorithm and Dijkstra's algorithm in order to find the shortest path that goes through all the vertices in the list
  
Finding next destination for agent - using greedy algorthm, find the next closest pokemon for the agent to catch.
  
  
 
  
![d7clisu-c2b6b1eb-84f1-4c3b-9a3d-ce392366320e](https://user-images.githubusercontent.com/92723105/148658976-9a336fea-52f9-4ba0-83a9-7ad0fb7e8819.jpg)
