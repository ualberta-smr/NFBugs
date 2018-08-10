# Credit to: 
    # A. Hindle, N. Ernst, M. W. Godfrey, R. C. Holt, and J. Mylopoulos. 
        # Whats in a name? on the automated topic naming of software maintenance
        # activities. 
        # submission: http://softwareprocess. es/whats-in-a-name, 125:150–155, 2010.
    # https://github.com/ishepard/pydriller


# This program takes the names of repo directories as command line arguments
# and searches for commits related to NFRs
# Output for each repo is stored in a csv file

# May 4th, 2018
# Last Updated: Aug 10th, 2018 -- Aida Radu

from pydriller import GitRepository
from pydriller import RepositoryMining
import sys

def main():
    # mine for non-functional fixes in commit messages -- stem words to catch more commits
    search_terms = ["fix","bug","error","secur","maint", \
                    "stab","portab","efficien","usab", "perf" \
                    "reliab", "testab", "changeab", "replac"\
                    "memory","resource", "runtime", "#", "crash", "leak" \
                    "attack" , "authenticat", "authoriz", "cipher","crack", \
                    "decrypt","encrypt","vulnerab","minimize","optimize",\
                    "slow", "fast"]
    
    # the program is run with command line arguments representing
    # github repos
    for repo in range(1,len(sys.argv)):
        
        # NB: using the with keyword will close the file automatically
        with open(sys.argv[repo].replace('../', '').replace('/','')+".csv","w") as new_file:
            new_file.write('{:^40},{:^40}\n'.format('Commit ID:','Commit Message:')) 
            
            for commit in RepositoryMining(sys.argv[repo],only_modifications_with_file_types=['.java','.py']).traverse_commits():
                # bool written avoids duplication if more than one word matches
                written = False 
                msg = commit.msg.lower()
                for term in search_terms:
                    if term.lower() in msg and filter(msg) and not written:
                        written = True
                        # print the commit ID and committer message
                        new_file.write('{:^40},{:^40}\n'.format(commit.hash,msg))
                        
                        
def filter(message):
    # message is a string
    # returns a boolean
    filters = ["typo","npe","spelling"]
    safe = True
    for word in filters:
        if word in message:
            safe = False
            break
    return safe


main()
