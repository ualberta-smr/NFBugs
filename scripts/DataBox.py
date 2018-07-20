# Written by Aida Radu
# July 12, 2018

# Last Updated: July 20, 2018

# requirements:
# * pyYAML: https://pyyaml.org/wiki/PyYAMLDocumentation
# or 
# * ruamel.yaml: https://pypi.org/project/ruamel.yaml 

import os
import yaml
import sys
    
class DataBox:
    # Handles extraction of data from smr repository
    
    def __init__(self):
        # initializes a new DataBox object
        __self.default_tags = ("security","performance","memory","resource management")
        
    def getProblemCount(self,directory):
        # calculates the number of problems in the given directory
        # - directory is a string of the path to search (e.x., "py-data")
        # - returns an int
        
        count = 0
        for (dirname, dirs, files) in os.walk(directory):
            for filename in files:
                if filename.endswith('problem.yml'):
                    count+=1
                    
        return count
        
    
    def getStatDistribution(self,directory,stat,ranges):
            # calculates the number of project stars, watches, or forks 
            # within a range in the given directory
            # - directory is a string of the path to search (e.x., "py-data")
            # - stat is a string of the stat to search for (e.x., "stars")
            # - ranges is a tuple of numbers to separate by, right boundary excluded
            # -- e.x. if ranges = (1,50,100), the boundaries are [0],[1,49],[50,99],[100+]
            # - returns a dictionary with fields range:frequency 
            
            if stat.lower() not in ("stars","watches","forks"):
                print("Invalid stat: must be stars, watches, or forks." + \
                      " Aborting command.", file=sys.stderr)
                return
            
            
            
            dist = {}
            
            # populate the distribution
            for num in ranges:
                dist["<"+str(num)] = 0
                
            dist[str(max(ranges))+"+"] = 0
                
            
            for (dirname, dirs, files) in os.walk(directory): 
                for filename in files:
                    if filename.endswith('project.yml'):
                        
                        # get count from the relevant field in yaml object
                        data = yaml.load(open(dirname+"/"+filename,"r").read())
                        count = int(data["repository"]["stats"][stat])
                        
                        for num in sorted(ranges):
                            if (count < num):
                                dist["<"+str(num)] += 1
                                break
                            if (num == max(ranges) and count >= num):
                                dist[str(num)+"+"] += 1
                                break
                                        
                                
            return dist
    
        
    
    def getTagDistribution(self,directory,\
                             tag_requests = __self.default_tags):
        # calculates the number of project by tag 
        # - directory is a string of the path to search (e.x., "py-data")
        # - tag_requests is a tuple of tags to get the distribution of 
        # --(by default get all)
        # - returns a dictionary with fields tag:frequency 
        
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
        
        
        problem_types = {"api-related":0,"general-practise":0,"project-specific":0}
        for (dirname, dirs, files) in os.walk(directory):
            for ptype in problem_types.keys():
                if (ptype not in dirname):
                    continue
                for filename in files:
                    if filename.endswith('problem.yml'):
                        problem_types[ptype]+=1
    
        return problem_types      
