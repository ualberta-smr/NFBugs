
# SMR Non-Functional Bugs Project

## Description
This repository contains a dataset of real-world bugs related to Non-Functional Requirements, as well as scripts to process the data. We began collecting the data in May 2018.

## Contributors
- [Aida Radu](https://github.com/aradu12)
- [Sarah Nadi](http://sarahnadi.org/)

## Layout of the Dataset 

Our data is separated into two main folders: one for [Java data](/java-data) and one for [Python data](py-data).
Each project has its own subfolder in the directory corresponding to its language. Within each project's folder you will find:

- a `project.yml` file containing the project metadata
- a `problems` folder 
    - these folders have Non-Functional Bugs organized into the subdirectories `general-practise`, `api-related`, or `project-specific`
    - within these subdirectories each problem has a its own numbered folder containing
        - a `problem.yml` file describing the bug and fix data
        - api related problems have a `correct-usages` folder containing examples of the code improved by the fix. NOTE: We are currently still reviewing these examples. Please use with caution.
- a `versions` folder
    - these folders contain subfolders for the point in the project history where a problem was found. The `version.yml` files in these subdirectories specify which problem(s) the version applies to.


## Using the Scripts

To allow users to process the data, we provide a DataBox python type. This type contains methods to filter the data distribution
by tags, type, star rating, and more. 

We also provide a sample [client script](/scripts/DataBoxClient.py). The output is a .txt file. To run this sample:

- clone the repo: `git clone https://github.com/ualberta-smr/researchwiki-radu.git`
- run `python3 scripts/DataBoxClient.py`

User client scripts can customize method arguments in the following ways:

* `getProblemCount(directory)`
    -  `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - return the total number of problems of all types in the given directory
    
* `getStatDistribution(directory,stat,ranges)`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - `stat`: provide the popularity stat to filter by (ex: `"stars"`, `"watches"`, or `"forks"`)
    - `ranges`: provide a tuple of integers to separate by, right boundary excluded. 
    - return a dictionary containing the number of projects whose stat falls into each range

      Examples for ``ranges`` and corresponding boundaries:

        - `(1,100,500,1000)` => stat is separated into bins [0] [1,99] [100, 499] [500, 999] [1000+]
        - `(1000)` => stat is separated into bins [0,999] [1000+]
        - `(1)` => stat is separated into bins [0] [1+]
        
* `getTagDistribution(directory[,tag_requests])`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - `tag_requests`: [optional] provide a tuple of strings that defines which problem tags to count. By default, this method gets the distribution across all problem tags ("security","performance","memory","resource management","determinism").
    - return a dictionary containing the number of problems that fall under each tag
    
       Example for `tag_distribution` input and return value:
       
         - `getTagDistribution("py-data",("security"))` => {"security": 10}
         - interpretation: there are 10 python problems labelled `security`

* `getAPIs(directory)`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - return a dictionary containing the distirubution of problems by problematic API 
    
        Partial Example for return value of `getAPIs`:
        
         - `getAPIs("java-data")` => {"java.lang.String": 8, ... }
         - interpretation: there are 8 problems for which java.lang.String was the problematic API
          
* `getProblemTypes(directory)`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - return a dictionary containing the disribution of problems by problem type
    
        Example output:
        
         - `getProblemTypes("java-data")` => {"api-related" : 71, "project-specific" : 4, "general-practise" : 19}
         - interpretation: there are 71 api related Java problems in the dataset
         
## Data Collection

Current TOTAL Bug Count = 138 from 67 projects

Current TOTAL projects examined = 161 

Candidate Repos:
- methods:
    - 1: chosen by highest number of stars; `source: commit-msg-keywords`
    - 2: chosen via github search for relevant terms; `source: github-search`
    - 3: chosen from [RepoReapers](https://reporeapers.github.io/results/1.html) dataset; `source: RepoReapers-dataset`
- List of all repos examined in [ReposMined.yml](https://github.com/ualberta-smr/researchwiki-radu/blob/master/ReposMined.yml)

Mining:
- done using a [PyDriller](https://github.com/ishepard/pydriller) based program
- searching for stemmed keywords in commit message history
- current search term inventory : 
                    
                    ["fix","bug","error","secur","maint", "refactor",\
                    "stab","portab","efficien","usab", "perform", \
                    "reliab", "testab", "changeab", "replac",\
                    "memory","resource", "runtime", "#", "crash", "leak", \
                    "attack" , "authenticat", "authoriz", "cipher","crack", \
                    "decrypt","encrypt","vulnerab","minimiz","optimiz",\
                    "slow","fast"]
                    
- current terms filtered out :
                    
                    ["typo","npe","spelling"]

- limited to commits affecting .java and .py files
                    
- you can find the mining code [here](PyDrillerMining/find_NFR_commits.py)

## Issues
If you experience an error using the dataset, let us know by opening a [new issue](https://github.com/ualberta-smr/researchwiki-radu/issues/new).


