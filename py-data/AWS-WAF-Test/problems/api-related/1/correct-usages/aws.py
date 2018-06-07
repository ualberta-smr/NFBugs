import requests

url = 'http://www.url.com' # url or path 
f = open("payload/samos_basicfuzz.txt","r") # open payload file, "r" is read only mode
countRequests = 0 # variable used to count number of requests/payloads
countSuccessRequests = 0 # variable used to count success requests (send on uptime and)
countFailedRequests = 0 # variable used to count fail requests (send on downtime or other fail)
countAllowRequests = 0 # variable used to count allowed requests
countBlockedRequests = 0 # variable used to count blocked requests
coubtErrors = 0 # variable used to count error requests
for w in f:
    countRequests+=1
    try:
        r = requests.get(url, params=w)
        countSuccessRequests+=1
        if r.status_code == 200:
            countAllowRequests+=1
        if r.status_code == 403:
    	    print w #print payloads/requests was blocked by cloudfront
    	    countBlockedRequests+=1
        if r.status_code != 200 and r.status_code != 403 :
            print 'Fail: code='+str(r.status_code)+', Payload: '+w
    except Exception as e:
        countFailedRequests+=1

print('########## AWS WAF Testing on '+url+' ##########')
print('Total requests: '+str(countRequests)+' requests')
print('Success requests: '+str(countSuccessRequests)+' requests')
print('Failed requests: '+str(countFailedRequests)+' requests')
print('Allowed requests: '+str(countAllowRequests)+' requests')
print('Blocked by cloudfront(AWS WAF): '+str(countBlockedRequests)+' requests')
print('###################################################################')
def getResult():
    print('############ Allow: '+str(countAllowRequests)+' requests, Blocked: '+str(countBlockedRequests)+' requests #############')
if countSuccessRequests == countRequests:
    print('####################  All request was success  ####################')
    getResult()
elif countFailedRequests == countRequests:
    print('####################  All request was failed   ####################')
elif countSuccessRequests < countRequests:
    print('############ Success: '+str(countSuccessRequests)+' requests, '+'Failed: '+str(countFailedRequests)+' requests ############')
    getResult()
else :
    print('Why success requests are more than total request ?')
print('###################################################################')

f.close()
