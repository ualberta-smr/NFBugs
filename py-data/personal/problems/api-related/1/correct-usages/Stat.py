import copy, collections, logging, random, sys, time

class Stat(object):

  def main():
    
    numRounds = 14
    resultSet = [Stat(0, 0, 0)] * numRounds
  
    averageStats = [x / float(numGames) for x in resultSet]
    
