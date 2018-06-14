# SMR Non-Functional Bugs Project

Mining commit history of open-source projects for fixes related non-functional requirements. 


Current TOTAL Bug Count = 82 (as of June 13)

Candidate Repos:
- earlier: chosen by highest number of stars, then mined with PyDriller
- currently: chosen via github search, then mined with PyDriller 
- List of all repos examined [here](https://github.com/ualberta-smr/researchwiki-radu/blob/master/listReposMined.txt)

Mining:
- using [PyDriller](https://github.com/ishepard/pydriller)
- using word stemming and issue linking 
- current search term inventory : 
                    
                    ["fix","bug","error","secur","maintenance","maintain", \
                    "stab","portab","efficien","usab" \
                    "reliab", "testab", "changeab", "replace"\
                    "memory","resource", "runtime", "#", "crash", "leak" \
                    "attack" , "authenticat", "authoriz", "cipher","crack", \
                    "decrypt","encrypt","vulnerab","minimize","optimize",\
                    "slow"]
                    
- current terms filtered out :
                    
                    ["typo","npe","spelling"]
                    
-mining code [here](https://github.com/ualberta-smr/researchwiki-radu/blob/master/PyDrillerMining/find_NFR_commits.py)

