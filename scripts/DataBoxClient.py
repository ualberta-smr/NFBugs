from collections import Counter
import datetime
from DataBox import DataBox

def main():
    current_time = datetime.datetime.now()
    data = DataBox(current_time)
    
    output = open("outDBC.txt","w")
    
    try:
        java_probs = data.getProblemCount("../java-data")
        py_probs = data.getProblemCount("../py-data")
    
        output.write("------------------------------")
        output.write("TOTAL PROBLEMS: "+str(java_probs+py_probs))
        output.write("\tJAVA PROBLEMS: "+str(java_probs))
        output.write("\tPYTHON PROBLEMS: "+str(py_probs))
    
        output.write("------------------------------")
        output.write("PROBLEM TYPES: ")
        output.write("\TOTAL")
        jv_pt_items = data.getProblemTypes("../java-data").items()
        py_pt_items = data.getProblemTypes("../py-data").items()
        for (key, value) in dict(Counter(jv_pt_items)+Counter(py_pt_items)).items():
            output.write("\t\t "+str(key)+" : "+str(value))     

        output.write("\n\tJAVA")
        for (key, value) in jv_pt_items:
            output.write("\t\t "+str(key)+" : "+str(value))
        output.write("\n\tPYTHON")
        for (key, value) in py_pt_items:
            output.write("\t\t "+str(key)+" : "+str(value))    



        output.write("\n------------------------------")
        output.write("JAVA POPULARITY STATS: ")

        for stat in ["stars","watches","forks"]:
            output.write("\t"+stat.upper()+": \n\t\trange\tfrequency")
            for (key, value) in data.getStatDistribution("../java-data",stat).items():
                output.write("\t\t ["+str(key)+"] : "+str(value))

        output.write("\n------------------------------")
        output.write("PYTHON POPULARITY STATS: ")

        for stat in ["stars","watches","forks"]:
            output.write("\n\n\t"+stat.upper()+": \n\t\trange\tfrequency")
            for (key, value) in data.getStatDistribution("../py-data",stat).items():
                output.write("\t\t ["+str(key)+"] : "+str(value))   


        output.write("\n\n------------------------------")
        output.write("JAVA TAG FREQUENCY: ")

        output.write("\t\ttag\tfrequency")
        for (key, value) in data.getTagDistribution("../java-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value))

        output.write("\n------------------------------")
        output.write("JAVA TAG FREQUENCY: ")

        output.write("\t\ttag\tfrequency")
        for (key, value) in data.getTagDistribution("../py-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value))


        ourput.write("\n--------------------------------------")
        output.write("API FREQUENCY -- JAVA\n")

        output.write("\t\tAPI\tfrequency")
        for (key, value) in data.getAPIs("../java-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value))

        output.write("\nAPI FREQUENCY -- PYTHON\n")
        output.write("\t\tAPI\tfrequency")      
        for (key, value) in data.getAPIs("../py-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value))
            
    finally:
        output.close()
main()
