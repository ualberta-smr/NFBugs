
# Non-Functional Bugs Dataset

[![DOI](https://zenodo.org/badge/130276895.svg)](https://zenodo.org/badge/latestdoi/130276895)

## Description
This repository contains a dataset of real-world bugs related to Non-Functional Requirements, as well as scripts to process the data. We began collecting the data in May 2018. Right now, we focus on non-functional bugs in Java and Python code. 

## Contributors

This data set has been developed in the [Software Maintenance and Reuse lab](https://sarahnadi.org/smr/) at the University of Alberta. Current contributors are:

- [Aida Radu](https://github.com/aradu12)
- [Sarah Nadi](http://sarahnadi.org/)

## Layout of the Dataset 

Our data is separated into two main folders: one for [Java data](/java-data) and one for [Python data](/py-data).
Each project has its own subfolder in the directory corresponding to its language. Within each project's folder you will find:

- a `project.yml` file containing the project metadata
- a `problems` folder 
    - these folders have Non-Functional Bugs organized into the subdirectories `general-practise`, `api-related`, or `project-specific`
    - within these subdirectories each problem has a its own numbered folder containing
        - a `problem.yml` file describing the bug and fix data
        - API related problems have a `correct-usages` folder containing examples of the code improved by the fix. NOTE: We are currently still reviewing these examples. Please use with caution.
- a `versions` folder
    - these folders contain subfolders for the point in the project history where a problem was found. The `version.yml` files in these subdirectories specify which problem(s) the version applies to.

## Methods

For a detailed description of how we collected our data, see the [Methodology](./docs/StudyMethodsDocs.md) documentation.

## Processing the Data

To allow users to manage our data, we provide a `DataBox` python type. This type contains methods to filter the data distribution
by tags, type, star rating, and more. 

For more details, see the DataBox [documentation](/docs/DataBoxDocs.md).

We also provide a sample [client script](/scripts/DataBoxClient.py). The output is a .txt file. To run this sample:

- clone the repo: `git clone https://github.com/ualberta-smr/researchwiki-radu.git`
- run `python3 scripts/DataBoxClient.py`

We have also experimented with using [GumTreeDiff](https://github.com/GumTreeDiff/gumtree) to detect API changes. You can find this code in the [GumCode](./GumCode/src/main/java) folder.   
         
## Dataset Size

As of 21/08/18, the dataset contains 138 bugs from 67 open source projects. In total, we examined 167 projects.  
We also provide the [list](/ReposMined.yml) of all the projects mined, along with the number of hits generated. 

Please note that we include a variety of projects, both from novice and experienced developers. If you are using the data in your analysis, you may want to filter projects based on your needs (ex. by star rating, etc).

The dataset consists of 44 non-functional bugs in Python and 94 non-functional bugs in Java. The following charts show the distribution of the data.

![type graph](/docs/Distribution%20of%20Problem%20Types.png "Frequency for Each Problem Type")

Problem types represent the domain that a problem applies to. 
* The `api related` type refers to problems that developers fixed by switching to a more optimal method or API. 
* The `general practise` problems refer to broad practices that improve the code without reference to a specific API.
* `Project specific` problems fix non-functional bugs, but cannot be generalized beyond the scope of their own project.    

Currently, the majority of our problems are API related.

![tag graph](/docs/Distribution%20of%20Problem%20Tags.png "Frequency for Each Problem Tag")

Problem tags represent the non-functional requirement that a fix improves. Some examples for improvements under each tag are:
* `security`: improving encryption, restricting access to sensitive data, etc.
* `performance`: using a faster method or algorithm
* `resource management`: adding code to close files and streams
* `memory`: fixing a memory leak or overflow
* `determinism`: using an algorithm that gives more consistent or well-defined results

Problems can have more than one tag.

## Issues
If you experience an error using the dataset, let us know by opening a [new issue](https://github.com/ualberta-smr/researchwiki-radu/issues/new).

## License

This project is subject to the conditions of the [MIT license](/LICENSE.md).


