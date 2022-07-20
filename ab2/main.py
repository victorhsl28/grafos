import numpy as np
from shapely import geometry


class Tree:
    def __init__(self):
        self.adjacenceList = []
        self.edges = []

    def prim(self, vertexes, connections, cost):
        for i in range(0, len(connections)):
            if connections[i] != -1:
                edge = (vertexes[i], vertexes[connections[i]], cost[i])
                self.edges.append(edge)

        for i in range(0, len(vertexes)):
            adjacence = []
            for edge in self.edges:
                if edge[0] == vertexes[i]:
                    adjacence.append((edge[1], edge[2]))
                if edge[1] == vertexes[i]:
                    adjacence.append((edge[0], edge[2]))
            self.adjacenceList.append(adjacence)

    def print_path(self, start, goal, vertexes):
        path = []
        list_states = [State(start)]

        for state in list_states:
            if state.point == goal:
                while state.father is not None:
                    path.insert(0, state)
                    state = state.father
                break
            state.generateChilds(vertexes, self.adjacenceList)
            for child in state.childs:
                list_states.append(child)

        print("Path: ")
        for vertex in path:
            print("[" + str(vertex.point.x) + ", " + str(vertex.point.y) + "]")


class State:
    def __init__(self, vertex):
        self.point = vertex
        self.father = None
        self.childs = []

    def generateChilds(self, vertexes, adjacencelist):
        self.childs = []
        for i in range(0, len(vertexes)):
            if vertexes[i] == self.point:
                for edge in adjacencelist[i]:
                    child = State(edge[0])
                    child.father = self
                    self.childs.append(child)


def generate_neighbours(vertexes, current, g):
    neighbours = []
    for edge in g:
        if vertexes[current] == edge[0] or vertexes[current] == edge[1]:
            neighbours.append(edge)
    return neighbours


def get_i(vertexes, current):
    for i in range(0, len(vertexes)):
        if current == vertexes[i]:
            return i


def get_lighter(line, weights):
    lighter = -1
    w = -np.inf
    for i in range(0, len(line)):
        if line[i] == 0:
            if lighter == -1 or weights[i] < w:
                lighter = i
                w = weights[i]
    return lighter


def mst(g, vertexes):
    line = []
    for i in range(0, len(vertexes)):
        line.append(0)

    weights = []
    connections = []

    for i in range(0, len(vertexes)):
        weights.append(np.inf)
        connections.append(-1)

    weights[0] = 0

    while 0 in line:
        lighter = get_lighter(line, weights)
        neighbours = generate_neighbours(vertexes, lighter, g)

        for edge in neighbours:
            current = 0
            if edge[0] == vertexes[lighter]:
                current = get_i(vertexes, edge[1])
            if edge[1] == vertexes[lighter]:
                current = get_i(vertexes, edge[0])
            if weights[current] > edge[2] and line[current] == 0:
                connections[current] = lighter
                weights[current] = edge[2]
        line[lighter] = 1

    t = Tree()
    t.prim(vertexes, connections, weights)
    return t


def vertexes_list(start, goal, vertexes):
    v = [start, goal]
    for obj in vertexes:
        for point in obj:
            v.append(point)
    return v


def right_visada(p1, p2, v):
    s = 0.0
    while s <= 1.0:
        for obj in v:
            if geometry.Polygon(obj).contains(
                    geometry.Point((p1.x * s) + (p2.x * (1.0 - s)), (p1.y * s) + (p2.y * (1.0 - s)))):
                return False
        s += 0.1
    return True


def visibilityGraph(lis, v):
    g = []
    for i in range(0, len(lis)):
        for j in range(0, len(lis)):
            if i != j and right_visada(lis[i], lis[j], v):
                edge = (lis[i], lis[j], lis[i].distance(lis[j]))
                if (edge[1], edge[0], edge[2]) not in g:
                    g.append(edge)
    return g


def read_point(file):
    line = file.readline().split(',')
    return geometry.Point(float(line[0]), float(line[1]))


def read_obstacles(file, obstacles):
    v = []
    for i in range(obstacles):
        obstacle = []
        for j in range(int(file.readline())):
            obstacle.append(read_point(file))
        v.append(obstacle)
    return v


def Main():
    file = open("mapa.txt", 'r')

    start = read_point(file)
    goal = read_point(file)

    obstacles_vertexes = read_obstacles(file, int(file.readline()))

    vertexes = vertexes_list(start, goal, obstacles_vertexes)
    print("Vetexes:")
    for vertex in vertexes:
        print("[" + str(vertex.x) + ", " + str(vertex.y) + "]")

    g = visibilityGraph(vertexes, obstacles_vertexes)
    g.sort(key=lambda x: x[2])

    t = mst(g, vertexes)

    print("\nConnections:")
    for verte in t.adjacenceList:
        print("For vertex: [" + str(vertex.x) + ", " + str(vertex.y) + "]")
        for connections in verte:
            print("[" + str(connections[0].x) + ", " + str(connections[0].y) + "] →",
                  str("{:.2f}".format(connections[1])))
        print("\n")

    t.print_path(start, goal, vertexes)

    xstart = float(input("\nInsert start 'x' cordinate: "))
    ystart = float(input("Insert start 'y' cordinate: "))
    xgoal = float(input("Insert goal 'x' cordinate: "))
    ygoal = float(input("Insert goal 'y' cordinate: "))

    start = geometry.Point(xstart, ystart)
    goal = geometry.Point(xgoal, ygoal)

    t.print_path(start, goal, vertexes)


Main()
