## Setup


To run `find_NFR_commits.py`, you must install PyDriller using pip. If you use python 3.4+, you should already have pip installed. Otherwise, run the following commands:
```
$ curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
$ python get-pip.py
```
For more details, see the pip [documentation](https://pip.pypa.io/en/stable/installing/). 

Install PyDriller using:
```
$ pip install pydriller
```
or you can clone directly from the source and install the requirments yourself using the following commands:

```
$ git clone https://github.com/ishepard/pydriller.git
$ cd pydriller
$ pip install -r requirements
$ unzip test-repos.zip
$ pytest
```
For more details, see the [PyDriller](https://github.com/ishepard/pydriller) project

`find_NFR_commits.py` mines projects given as command line arguments. Run the program using:
```
python3 find_NFR_commits.py <paths to repositories>
```
