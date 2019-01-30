# Written by Aida Radu 
# aradu@ualberta.ca
# July 12, 2018

# Last Updated: Aug 31, 2018

# requirements:
# * pyYAML: https://pyyaml.org/wiki/PyYAMLDocumentation
# or 
# * ruamel.yaml: https://pypi.org/project/ruamel.yaml 

import os
import yaml
import sys
import math
import numpy as np
    
class DataBox:
  # Handles extraction of data from smr repository
    
  def __init__(self):
    # initializes a new DataBox object
    self.__default_tags = ("security","performance","memory","resource management","determinism")
  
  def getProjectCount(self,directory):
    # calculates the number of projects in the  given directory
    # - directory is a string of the path to search (e.x., "py-data")
    # - returns an int

    if not self.__validate(directory):
        return

    count = 0
    for (dirname, dirs, files) in os.walk(directory):
        for filename in files:
            if filename.endswith('project.yml'):
                count+=1

    return count
    
  def getProblemCount(self,directory):
    # calculates the number of problems in the given directory
    # - directory is a string of the path to search (e.x., "py-data")
    # - returns an int
    
    if not self.__validate(directory):
        return
    
    count = 0
    for (dirname, dirs, files) in os.walk(directory):
        for filename in files:
            if filename.endswith('problem.yml'):
                count+=1
                
    return count
      
  def _getStatList(self,directory,stat):
    # get a list of all stat counts in the directory
    # - directory is a string of the path to search (e.x., "py-data")
    # - stat is a string of the stat to search for (e.x., "stars")
    # - returns a list object
    
    if not self.__validate(directory,string = stat):
      return
    stats = []
    for (dirname, dirs, files) in os.walk(directory):
      for filename in files:
        if filename.endswith('project.yml'):
          data = yaml.load(open(dirname+"/"+filename,"r").read())
          my_stat = int(data["repository"]["stats"][stat])
          stats.append(my_stat)
    return stats

  def getStatDistribution(self,directory,stat,ranges):
    # calculates the number of project stars, watches, or forks 
    # within a range in the given directory
    # - directory is a string of the path to search (e.x., "py-data")
    # - stat is a string of the stat to search for (e.x., "stars")
    # - ranges is a tuple of bins to separate into, right boundary excluded
    # -- e.x. if ranges = (0,1,50,100), the boundaries are [0,1),[1,50),[50,100),[100, inf)
    # - returns a dictionary with fields range:frequency 
    
    if not self.__validate(directory,string = stat,int_tup = ranges):
        return
          
    dist = {}
    stat_list = self._getStatList(directory,stat)
    list_rg = list(ranges)
    list_rg.append(math.inf)
    range_bins = np.array(list_rg)
    hist = list(np.histogram(stat_list,bins=range_bins))

    # populate the distribution
    
    for index in range(len(ranges)-1):
      dist["["+str(ranges[index])+", "+str(ranges[index+1])+")"] = hist[0][index]
    dist["["+str(max(ranges))+", "+str(math.inf)+")"] = hist[0][len(hist[0])-1]
    
    return dist
  
      
  
  def getTagDistribution(self,directory, tag_requests = None):
    # calculates the number of project by tag 
    # - directory is a string of the path to search (e.x., "py-data")
    # - tag_requests is a tuple of tags to get the distribution of 
    # --(by default get all)
    # - returns a dictionary with fields tag:frequency 
    
    if tag_requests is None:
        tag_requests = self.__default_tags
        
    if not self.__validate(directory,str_tup = tag_requests):
        return
    
    tag_nums = {}
    
    # populate dictionary
    for tag in tag_requests:
        tag_nums[tag.lower().strip()] = 0


    for (dirname, dirs, files) in os.walk(directory):
        for filename in files:
            if filename.endswith('problem.yml'):
                
                code = yaml.load(open(dirname+"/"+filename,"r").read())
                
                # split because some have more than one tag
                tags = code["fix"]["tag"].split(",")    
                for tag in tags:
                    if tag.strip() in tag_nums.keys():
                        tag_nums[tag.strip()]+=1
                break
    return tag_nums   
  
  def getAPIs(self,directory):
    # collects the frequency of problematic (prior to fix) APIs
    # - directory is a string of the path to search (e.x., "py-data")
    # - returns a dictionary with fields API:frequency 
    
    if not self.__validate(directory):
        return
    
    apis = {}
    
    for (dirname, dirs, files) in os.walk(directory):
        if ("api-related" not in dirname):
            continue
        for filename in files:
            if filename.endswith('problem.yml'):
                code = yaml.load(open(dirname+"/"+filename,"r").read())
                for api in code["api"].split():
                    api = api.strip()
                    if api in apis.keys():
                        apis[api]+=1
                    else:
                        apis[api] = 1
            
    return apis
  
  
  def getProblemTypes(self,directory):
    # counts the frequency of problem types
    # - directory is a string of the path to search (e.x., "py-data")
    # - returns a dictionary with fields type:frequency  
    
    if not self.__validate(directory):
        return
    
    problem_types = {"api-related":0,"general-practise":0,"project-specific":0}
    for (dirname, dirs, files) in os.walk(directory):
        for ptype in problem_types.keys():
            if (ptype not in dirname):
                continue
            for filename in files:
                if filename.endswith('problem.yml'):
                    problem_types[ptype]+=1

    return problem_types  
  
  def getSources(self,directory):
    # counts the number of problems from each source
    # - directory is a string of the path to search (e.x., "py-data")
    # - returns a dictionary with fields source:frequency 

    if not self.__validate(directory):
        return

    sources = {}
    
    for (dirname, dirs, files) in os.walk(directory):
        for filename in files:
            if filename.endswith('problem.yml'):
                code = yaml.load(open(dirname+"/"+filename,"r").read())
                source = code["source"]["name"].strip()
                
                if source in sources.keys():
                    sources[source]+=1
                else:
                    sources[source] = 1
        
    return sources
  
  
  def __validate(self,directory,string="stars",int_tup=(0,),str_tup=("",)):
    
    if not isinstance(directory,str):
        print("Error: directory should be a string. Aborting command.",file=sys.stderr)
        return False
    
    if not isinstance(string,str) or string.lower() not in ("stars","watches","forks"):
        print("Error: stat must be 'stars', 'watches', or 'forks'." + \
                  " Aborting command.", file=sys.stderr)
        return False
        
    if not isinstance(int_tup,tuple) or not all([isinstance(num,int) for num in int_tup]):
        print("Error: ranges must be a tuple of ints. Aborting command.",file=sys.stderr)
        return False
    
    if not isinstance(str_tup,tuple) or not all([isinstance(tag,str) for tag in str_tup]):
        print("Error: tags must be a tuple of strings. Aborting command.",file=sys.stderr)
        return False
    
    return True
        
