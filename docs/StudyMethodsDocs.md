# Methodology

We constructed our dataset by searching the commit history of open source projects. We chose candidate projects via three different methods, and ran a keyword-search to find commits related to non-functional bugs.

## Candidate Repos
- methods:
    - 1: chosen by highest number of stars; `source: commit-msg-keywords`
    - 2: chosen via github search for relevant terms; `source: github-search`
    - 3: chosen from [RepoReapers](https://reporeapers.github.io/results/1.html) dataset; `source: RepoReapers-dataset`
- List of all repos examined in [ReposMined.yml](../ReposMined.yml)

## Mining
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
                    
                    ["typo","npe","spell"]

- limited to commits affecting .java and .py files
                    
- you can find the mining code [here](../PyDrillerMining)

Also see the seminar [presentation](../docs/AnonymizedPresentation.pdf)
    ) created by [Aida Radu](https://github.com/aradu12)
    
## Documenting Bugs

To understand how the data is organized, see our explanation of the [Layout of the Dataset](https://github.com/ualberta-smr/NFBugs#layout-of-the-dataset). 

Within each `project.yml` file, we document the relevant characteristics of the project:
```
name: the name of the project
repository:
    type: git
    url: address of the github repository
    stats:
        watches: the number of watches at the time of documentation 
        stars: the number of stars at the time of documentation 
        forks: the number of forks at the time of documentation 
url: address of the project home site (if one does not exist, this url is the same as the one for the github repository)
```

Within each `problem.yml` file, we document the following relevant attributes of the problem:
```
 source: 
     name: the method used to choose this project for mining
 project
     name: the name of the project
     url: address of the project's github repository
 fix:
     tag: the NFR that the fix applies to 
     description: our interpretation of the fix
     commit message: the committer's explanation of the change
     commit: url of the commit diff
 location:
     file: the file(s) where the fix was made
     method: the method(s) where the fix was made     
 [ api: the problematic api that was changed or improved ]
 [ api change: problematic api -> improved api ]
rule / suggestion:
    a generalization of the fix that other developers can use as a guide
    
```
The fileds in square brackets are not present for each problem as they only apply to `api-related` problem types.
We use `rule` for `api-related` problems and `suggestion` for `general-practise` problems. For an explanation of problem tags and types see the details of the [Data Distribution](https://github.com/ualberta-smr/NFBugs#dataset-size).

Within each `version.yml` file, we record the project version associated with each problem:

```
problems:
    - the problem numbers this version is relevant for
revision: the ID for this point in the version history
```
