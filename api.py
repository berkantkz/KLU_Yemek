import json
from pyquery import PyQuery as pq

url = "https://sks.klu.edu.tr/Takvimler/73-yemek-takvimi.klu"
#webpage = pq(url = url)
webpage = ""

import urllib3
from urllib3.util.ssl_ import create_urllib3_context

ctx = create_urllib3_context()
ctx.load_default_certs()
ctx.options |= 0x4  # ssl.OP_LEGACY_SERVER_CONNECT

with urllib3.PoolManager(ssl_context=ctx) as http:
    webpage = http.request("GET", url)
    print(webpage.status)

data = py(webpage)
jsonIn = webpage.find("textarea").html()
jsonIn = jsonIn.replace('\\n', '')

getDay = json.loads(jsonIn)
currentMonth = getDay[0]["start"].replace(' 00:00:00 ', '').split('-')[1]
counter = 0
out = []

while currentMonth in getDay[counter]['start']:
    out.append({'day' : getDay[counter]['title'],
        'date': getDay[counter]['start'].replace(' 00:00:00 ', ''),
        'content': getDay[counter]['aciklama']})
    #print(getDay[counter]['title'])
    counter = counter + 1

f = open("list.json", "w")
f.write(json.dumps(out, indent=2))
f.close()