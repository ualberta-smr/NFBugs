
# Non-Functional Bugs Dataset

## Description
This repository contains a dataset of real-world bugs related to Non-Functional Requirements, as well as scripts to process the data. We began collecting the data in May 2018. Right now, we focus on non-functional bugs in Java and Python code.

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


## Processing the Data

To allow users to manage our data, we provide a `DataBox` python type. This type contains methods to filter the data distribution
by tags, type, star rating, and more. 

For more details, see the DataBox [documentation](/docs/DataBoxDocs.md).

We also provide a sample [client script](/scripts/DataBoxClient.py). The output is a .txt file. To run this sample:

- clone the repo: `git clone https://github.com/ualberta-smr/researchwiki-radu.git`
- run `python3 scripts/DataBoxClient.py`
         
## Dataset Size

Current TOTAL Bug Count = 138 from 67 projects

Current TOTAL projects examined = 167 


## Issues
If you experience an error using the dataset, let us know by opening a [new issue](https://github.com/ualberta-smr/researchwiki-radu/issues/new).

## License

This project is subject to the conditions of the [MIT license](/LICENSE.md).


