# API Reference

### DataBox provides the following customizable data processing methods:

* `getProblemCount(directory)`
    -  `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - return the total number of problems of all types in the given directory
    
* `_getStatList(self,directory,stat)`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - `stat`: provide the popularity stat to filter by (ex: `"stars"`, `"watches"`, or `"forks"`)
    - return a list of all values of stat



* `getStatDistribution(directory,stat,ranges)`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - `stat`: provide the popularity stat to filter by (ex: `"stars"`, `"watches"`, or `"forks"`)
    - `ranges`: provide a tuple of bins to separate into, right boundary excluded. 
    - return a dictionary containing the number of projects whose stat falls into each range

      Examples for ``ranges`` and corresponding boundaries:

        - `(0,100,500,1000)` => stat is separated into bins [0,100) [100,500) [500, 1000) [1000, inf)
        - `(0,1000)` => stat is separated into bins [0,1000) [1000,inf)
        - `(0,1)` => stat is separated into bins [0,1) [1,inf)
        
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
         
* `getSources(directory)`
    - `directory`: provide the part of the dataset to process (ex: `"java-data"`, or `"py-data"`)
    - return a dictionary containing the disribution of problems by source (ex: `RepoReapers-dataset`)
    
        Example output:
        
         - `getSources("java-data")` => {"RepoReapers-dataset" : 30, "github-search" : 24, "commit-msg-keywords " : 40}
         - interpretation: there are 30 Java problems that came from projects chosen via the RepoReapers dataset

