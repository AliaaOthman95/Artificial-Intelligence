# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    """
    "*** YOUR CODE HERE ***"
    """ initialize frontier list with the start state """
    frontier = util.Stack()
    """ initialize state for the search problem """
    state = {}
    state["node"] = problem.getStartState()
    state["path"] = []
    frontier.push(state)
    """ initialize the explored set """
    explored = set()
    """loop on frontier list """
    while not frontier.isEmpty():
        """ get the node with smallest cost """
        temp_state = frontier.pop()
        current_state = temp_state["node"]
        current_path = temp_state["path"]

        """ if it is the goal just return the path """
        if (problem.isGoalState(current_state)):
            return current_path
        """ if the explored set doesn't have the current_state  just add it """
        if (not current_state in explored):
            explored.add(current_state)
        """ for each successor of the current state """
        for neighbour in problem.getSuccessors(current_state):
            if not neighbour[0] in explored:
                result_node = {}
                result_node["node"] = neighbour[0]
                result_node["path"] = current_path + [neighbour[1]]
                frontier.push(result_node)

    return current_path


def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"
    frontier = util.Queue()
    """ initialize state for the search problem """
    state = {}
    state["node"] = problem.getStartState()
    state["path"] = []
    frontier.push(state)
    """ initialize the explored set """
    explored = set()
    """loop on frontier list """
    while not frontier.isEmpty():
        """ get the node with smallest cost """
        temp_state = frontier.pop()
        current_state = temp_state["node"]
        current_path = temp_state["path"]

        """ if it is the goal just return the path """
        if (problem.isGoalState(current_state)):
            "print current_path"
            return current_path
        """ if the explored set doesn't have the current_state  just add it """
        if (not current_state in explored):
            explored.add(current_state)
        """ for each successor of the current state """
        for neighbour in problem.getSuccessors(current_state):
            if neighbour[0] not in explored:
                isInFrontier = False
                for node in frontier.list:  # not in frontier
                    if neighbour[0] == node["node"]:
                        isInFrontier = True
                if not isInFrontier:
                    result_node = {}
                    result_node["node"] = neighbour[0]
                    result_node["path"] = current_path + [neighbour[1]]
                    frontier.push(result_node)

    return current_path


def uniformCostSearch(problem):

    return aStarSearch(problem)

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def getActions(state, actions):
    if state == actions[state][0]:
        return []
    return  getActions(actions[state][0], actions) + [actions[state][1]]


def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    if problem is None:
        raise ValueError("Search Problem cannot be None")

    priorityQueue = util.PriorityQueue()
    initState = problem.getStartState()

    if initState is None:
        raise ValueError("Initial State cannot be None")

    # Initialization
    isExplored = set()
    inQueue = set()
    currCost = {}
    # For each state (pos + direction) store action to achieve this state
    actions = {initState: (initState, None)}
    # Heuristic cost to reach goal from init state = 0 + g(n)
    initCost = heuristic(initState, problem)
    # Prioritize the state with accumlated cost on total path cost = (g(n)+f(n))
    priorityQueue.push(initState, initCost)
    inQueue.add(initState)
    currCost[initState] = 0

    # A* search logic
    while not priorityQueue.isEmpty():
        currState = priorityQueue.pop()
        isExplored.add(currState)

        # Goal is achieved
        if problem.isGoalState(currState):
            return getActions(currState, actions)

        # For each (nextstate, action, cost)
        for (nextState, action, cost) in problem.getSuccessors(currState):
            # If not explored and not currently in queue to be explored
            if not ((nextState in inQueue) or (nextState in isExplored)):
                # Add candidate node to the priority queue with cost = g(n) + f(n)
                priorityQueue.push(nextState, currCost[currState] + cost + heuristic(nextState, problem))
                inQueue.add(nextState)
                # Set cost to reach successor = cost to reach parent + cost to reach successor from parent
                currCost[nextState] = currCost[currState] + cost
                # Store actions taken from parent to reach successor
                actions[nextState] = (currState, action)
            # Else if successor is visited and explored yet
            elif nextState in inQueue:
                # Update successor (cost & parent & action taken) only incase reached with less cost from another parent
                if (currCost[currState] + cost) < currCost[nextState]:
                    priorityQueue.update(nextState, currCost[currState] + cost + heuristic(nextState, problem))
                    actions[nextState] = (currState, action)
    return []

# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
