# Written by Aida Radu
# July 12, 2018

# Last Updated: July 18, 2018

# requirements:
# * pyYAML: https://pyyaml.org/wiki/PyYAMLDocumentation
# or 
# * ruamel.yaml: https://pypi.org/project/ruamel.yaml 

import os
import datetime
import yaml
import sys
    
class DataBox:
    # Handles extraction of data from smr repository
    
    def __init__(self,date):
        # initializes a new DataBox object
        # - date is a datetime object representing the date and time of creation
        
        self.date = date
        
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
        
    
    def getStatDistribution(self,directory,stat):
        # calculates the number of project stars, watches, or forks 
        # within a range in the given directory
        # - directory is a string of the path to search (e.x., "py-data")
        # - stat is a string of the stat to search for ("e.x., "stars")
        # - returns a dictionary with fields range:frequency 
        
        if stat not in ("stars","watches","forks"):
            print("Invalid stat. Aborting command.", file=sys.stderr)
            return
        
        # the ranges we have chosen for our project
        # [NOTE] should we make these customizable as a parameter?
        ranges = {"0":0,"<50":0,"<200":0,"<400":0, \
                  "<600":0,"<800":0,"800+":0}
        
        
        for (dirname, dirs, files) in os.walk(directory): 
            for filename in files:
                if filename.endswith('project.yml'):
                    
                    # get count from the relevant field in yaml object
                    data = yaml.load(open(dirname+"/"+filename,"r").read())
                    count = int(data["repository"]["stats"][stat])
                                                                
                    if (count == 0):
                        ranges["0"]+=1
                    elif (count <50):
                        ranges["<50"]+=1   
                    elif (count < 200):
                        ranges["<200"]+=1
                    elif (count <400):
                        ranges["<400"]+=1
                    elif (count < 600):
                        ranges["<600"]+=1
                    elif (count <800):
                        ranges["<800"]+=1
                    elif (count >800):
                        ranges["800+"]+=1 
                    break
                            
        return ranges
    
        
    
    def getTagDistribution(self,directory):
        # calculates the number of project by tag 
        # - directory is a string of the path to search (e.x., "py-data")
        # - returns a dictionary with fields tag:frequency 
        
        tag_nums = {"security":0,"performance":0,"memory":0,\
                    "resource management":0}
    
        for (dirname, dirs, files) in os.walk(directory):
            for filename in files:
                if filename.endswith('problem.yml'):
                    
                    code = yaml.load(open(dirname+"/"+filename,"r").read())
                    
                    # split because some have more than one tag
                    tags = code["fix"]["tag"].split(",")    
                    for tag in tags:
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
