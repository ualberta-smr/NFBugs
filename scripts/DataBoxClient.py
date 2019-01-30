from collections import Counter
import datetime
from DataBox import DataBox

def main():
    current_time = datetime.datetime.now()
    data = DataBox()
    
    output = open("outDBC.txt","w")
    
    try:
        java_probs = data.getProblemCount("../java-data")
        py_probs = data.getProblemCount("../py-data")
        java_projects = data.getProjectCount("../java-data")
        py_projects = data.getProjectCount("../py-data")
        
        output.write(" DATA AS OF "+current_time.strftime("%d-%m-%y at %H:%M")+"\n")
    
        output.write("------------------------------\n")
        output.write("TOTAL PROBLEMS: "+str(java_probs+py_probs)+" FROM "+ str(java_projects+py_projects)+" projects \n")
        output.write("\tJAVA PROBLEMS: "+str(java_probs)+" FROM " + str(java_projects)+" projects \n")
        output.write("\tPYTHON PROBLEMS: "+str(py_probs)+" FROM " + str(py_projects)+" projects \n")
    
        output.write("------------------------------\n")
        output.write("PROBLEM TYPES: "+"\n")
        output.write("\tTOTAL"+"\n")
        jv_pt = data.getProblemTypes("../java-data")
        py_pt = data.getProblemTypes("../py-data")
        for (key, value) in dict(Counter(jv_pt)+Counter(py_pt)).items():
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")     

        output.write("\n\tJAVA"+"\n")
        for (key, value) in jv_pt.items():
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")
        output.write("\n\tPYTHON\n")
        for (key, value) in py_pt.items():
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")    

        # the ones we chose before (so we can see if custom gives same results)    
        ranges = (0,1,50,200,400,600,800) 

        output.write("\n------------------------------\n")
        output.write("JAVA POPULARITY STATS: "+"\n")

        for stat in ["stars","watches","forks"]:
            output.write("\t"+stat.upper()+": \n\t\trange\tfrequency"+"\n")
            for (key, value) in sorted(data.getStatDistribution("../java-data",stat,ranges).items()):
                output.write("\t\t "+str(key)+" : "+str(value)+"\n")

        output.write("\n------------------------------\n")
        output.write("PYTHON POPULARITY STATS: "+"\n")

        for stat in ["stars","watches","forks"]:
            output.write("\n\n\t"+stat.upper()+": \n\t\trange\tfrequency"+"\n")
            for (key, value) in sorted(data.getStatDistribution("../py-data",stat,ranges).items()):
                output.write("\t\t "+str(key)+" : "+str(value)+"\n")   


        output.write("\n\n------------------------------\n")
        output.write("JAVA TAG FREQUENCY: "+"\n")

        output.write("\t\ttag\tfrequency"+"\n")
        for (key, value) in data.getTagDistribution("../java-data").items():
            output.write("\t\t ["+str(key)+"] : "+str(value)+"\n")

        output.write("\n------------------------------\n")
        output.write("PYTHON TAG FREQUENCY: "+"\n")

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
            
       
        output.write("\n--------------------------------------\n")
        output.write("SOURCE DISTRIBUTION -- JAVA\n")

        output.write("\t\tSource\tfrequency"+"\n")
        for (key, value) in data.getSources("../java-data").items():
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")

        output.write("\nSOURCE FREQUENCY -- PYTHON\n")
        output.write("\t\tSource\tfrequency"+"\n")      
        for (key, value) in data.getSources("../py-data").items():
            output.write("\t\t "+str(key)+" : "+str(value)+"\n")
            
    finally:
        output.close()
main()
