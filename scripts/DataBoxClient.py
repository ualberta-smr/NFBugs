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
    
        output.write("------------------------------\n")
        output.write("TOTAL PROBLEMS: "+str(java_probs+py_probs)+"\n")
        output.write("\tJAVA PROBLEMS: "+str(java_probs)+"\n")
        output.write("\tPYTHON PROBLEMS: "+str(py_probs)+"\n")
    
        output.write("------------------------------\n")
        output.write("PROBLEM TYPES: "+"\n")
        output.write("\TOTAL"+"\n")
        jv_pt_items = data.getProblemTypes("../java-data").items()
        py_pt_items = data.getProblemTypes("../py-data").items()
        for (key, value) in dict(Counter(jv_pt_items)+Counter(py_pt_items)).items():
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")     

        output.write("\n\tJAVA"+"\n")
        for (key, value) in jv_pt_items:
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")
        output.write("\n\tPYTHON")
        for (key, value) in py_pt_items:
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")    



        output.write("\n------------------------------\n")
        output.write("JAVA POPULARITY STATS: "+"\n")

        for stat in ["stars","watches","forks"]:
            output.write("\t"+stat.upper()+": \n\t\trange\tfrequency"+"\n")
            for (key, value) in data.getStatDistribution("../java-data",stat).items():
                output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")

        output.write("\n------------------------------\n")
        output.write("PYTHON POPULARITY STATS: "+"\n")

        for stat in ["stars","watches","forks"]:
            output.write("\n\n\t"+stat.upper()+": \n\t\trange\tfrequency"+"\n")
            for (key, value) in data.getStatDistribution("../py-data",stat).items():
                output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")   


        output.write("\n\n------------------------------\n")
        output.write("JAVA TAG FREQUENCY: "+"\n")

        output.write("\t\ttag\tfrequency"+"\n")
        for (key, value) in data.getTagDistribution("../java-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")

        output.write("\n------------------------------\n")
        output.write("JAVA TAG FREQUENCY: "+"\n")

        output.write("\t\ttag\tfrequency"+"\n")
        for (key, value) in data.getTagDistribution("../py-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")


        output.write("\n--------------------------------------\n")
        output.write("API FREQUENCY -- JAVA\n")

        output.write("\t\tAPI\tfrequency"+"\n")
        for (key, value) in data.getAPIs("../java-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")

        output.write("\nAPI FREQUENCY -- PYTHON\n")
        output.write("\t\tAPI\tfrequency"+"\n")      
        for (key, value) in data.getAPIs("../py-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")
            
    finally:
        output.close()
main()
