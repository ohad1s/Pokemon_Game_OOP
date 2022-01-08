# Pokemon_Game_OOP

# About This Project:

In this project we implemented a Pokemon Game based on directed weighted graph and graph algorithms.
The Game include Server (jar file) that place the pokemons on the graph and give us all the data we need about them.
In "StudentCode" I implemented a algorithm to place the Agents and them next steps in order to catch the pokemons.

# More about the game:
As i said before, there is a server that "manage the game".
In order to start play, you should connect it from the cmd with the command: "java -jar Ex4_Server_v0.0.jar <LEVEL>"
The game is played "offline"- it means i wrote the algorithm in java and than i see the outcome of my code.
The game has 16 levels (0-15), every level include a different number of ageants and pokemons, and a different graph.
The game screen contains a directed weighted graph, the pokemons are located on the graph edges.
Every pokemon has a different value and - when you catch one- you got his value to your score.
Durations of the games are between 30-60 seconds, and there is an important rule- you can only do 10 moves (of the agants) per second.
The Game finished when the time is finished and then you can see your score and moves.

# Algorithms That Are Implemented on the graph in order to show good performence:

Shortest Path between two vertices - Finding the path and distance using Dijkstra's algorithm.

Finding the Graph Center - Finding the vertex in the graph, that has the minimal distance to the farthest vertex using Dijkstra's algorithm.

Finding Shortest Path For List of Vertices - Using a greedy algorithm and Dijkstra's algorithm in order to find the shortest path that goes through all the vertices in the list
