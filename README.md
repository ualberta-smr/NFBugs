# SMR Non-Functional Bugs Project

Mining commit history of open-source projects for fixes related non-functional requirements. 


Current TOTAL Bug Count = TBD

Candidate Repos:
- earlier: chosen by highest number of stars, then mined with PyDriller
- currently: chosen via github search, then mined with PyDriller 
- List of all repos examined [here](https://github.com/ualberta-smr/researchwiki-radu/blob/master/listReposMined.txt) but currently being moved to [ReposMined.yml](https://github.com/ualberta-smr/researchwiki-radu/blob/master/ReposMined.yml)

Mining:
- using [PyDriller](https://github.com/ishepard/pydriller)
- using word stemming and issue linking 
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
                    
-mining code [here](https://github.com/ualberta-smr/researchwiki-radu/blob/master/PyDrillerMining/find_NFR_commits.py)

