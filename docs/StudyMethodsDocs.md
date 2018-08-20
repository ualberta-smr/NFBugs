# Methodology

## Candidate Repos
- methods:
    - 1: chosen by highest number of stars; `source: commit-msg-keywords`
    - 2: chosen via github search for relevant terms; `source: github-search`
    - 3: chosen from [RepoReapers](https://reporeapers.github.io/results/1.html) dataset; `source: RepoReapers-dataset`
- List of all repos examined in [ReposMined.yml](https://github.com/ualberta-smr/researchwiki-radu/blob/master/ReposMined.yml)

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
                    
                    ["typo","npe","spelling"]

- limited to commits affecting .java and .py files
                    
- you can find the mining code [here](PyDrillerMining/find_NFR_commits.py)

Also see our seminar [presentation](
      https://github.com/ualberta-smr/researchwiki-radu/blob/master/docs/PLSE%20Presentation.pdf
    ) created by [Aida Radu](https://github.com/aradu12)
