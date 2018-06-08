# SMR Non-Functional Bugs Project

Mining commit history of open-source projects for fixes related non-functional requirements. 


Current TOTAL Bug Count = 72 (as of June 7)

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
                    "memory","resource", "runtime", "#", "crash", "leak"]
- mining code [here](https://github.com/ualberta-smr/researchwiki-radu/blob/master/PyDrillerMining/find_NFR_commits.py)
