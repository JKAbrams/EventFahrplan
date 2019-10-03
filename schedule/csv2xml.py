#!/usr/bin/env python3

import sys
import csv
import os.path
import datetime
from datetime import timedelta

# Simple parser that reads a csv file in the following format:
# Day,Time,Speaker,Title,Description,Room
# and produces an XML file compatible with the fahrplan android application

# The CSV file has to be sorted according to: day, room, time

xmlOpening = '''<schedule>
  <version>Voltron 3.1 2.1 1.5 MemoryRefreshV1.3 1.2.4 {}</version>'''
xmlEnding = '''</schedule>'''
dayOpening = '''  <day index="{}" date="{}" start="{}" end="{}">''' # <day index="1" date="2018-12-27" start="2018-12-27T11:00:00+01:00" end="2018-12-28T03:00:00+01:00">
dayEnding = '''  </day>'''
roomOpening = '''    <room name="{}">''' # <room name="Adams">
roomEnding = '''    </room>'''
eventOpening = '''      <event id="{}" guid="{}">
        <url>{}</url>
        <logo/>
        <date>{}</date>
        <start>{}</start>
        <duration>{}</duration>
        <room>{}</room>
        <slug>{}</slug>
        <title>{}</title>
        <subtitle>{}</subtitle>
        <track>{}</track>
        <type>{}</type>
        <language>{}</language>
        <abstract>{}</abstract>
        <description/>
         <recording>
          <license/>
          <optout>false</optout>
        </recording>
        <persons>'''
eventEnding = '''
        </persons>
        <links/>
        <attachments/>
      </event>'''
person = '''          <person id="{}">{}</person>'''


days = 3
startDate = "2019-10-02"

idd = ''
guid = ''
url = ''
datum = ''
start = ''
end = ''
duration = ''
room = ''
slug = ''
title = ''
subtitle = ''
typ = ''
language = ''
abstract = ''
persons = ['','']



# Metadata:
conferance = '''
  <conference>
    <acronym>HCPP19</acronym>
    <title>Hackers Congress Paralelni Polis 2019</title>
    <start>2019-10-04</start>
    <end>2019-10-05</end>
    <days>3</days>
    <timeslot_duration>00:10</timeslot_duration>
    <base_url>https://opt-out.hcpp.cz/schedule</base_url>
  </conference>'''


def printXML(reader):
    
    #                     roomEnd dayEnd dayOpen roomOpen            day   lastDay diff room  lastRoom  diff first
    #  <s>                                                                                         
    #  <d><r><e>          -       -      X       X       <-- case1   1     '       X    a     '         X    X
    #  </r><r><e>         X       -      -       X       <-- case2   1     1       -    b     a         X    - 
    #  <e>                -       -      -       -       <-- case3   1     1       -    b     b         -    - 
    #  <e>                -       -      -       -                   1     1       -    b     b         -    - 
    #  </r><r><e>         X       -      -       X                   1     1       -    c     b         X    -
    #  <e>                -       -      -       -                   1     1       -    c     c         -    -
    #  <e>                -       -      -       -                   1     1       -    c     c         -    -  
    #  </r></d><d><r><e>  X       X      X       X      <-- case4    2     1       X    a     c         X    -  
    #  </r><r><e>         X       -      -       X                   2     2       -    b     a         X    -  
    #  <e>                -       -      -       -                   2     2       -    b     b         -    -  
    #  <e>                -       -      -       -                   2     2       -    b     b         -    -    
    #  </r></d></s>        
                                                                 
    #  XXX = Case 1
    #  -X- = Case 2
    #  --- = Case 3
    #  XX- = Case 4



    print(xmlOpening.format(datetime.datetime.now().strftime('%Y-%m-%d %H:%M')))

    dayIndex = 0

    startDate = "2019-10-04"
    timezone = "+02:00"


    #datetime.strptime("2018-05-25T12:16:14+00:00", "%Y-%m-%d %H:%M:%S")


    StartDate = datetime.datetime.strptime(startDate, "%Y-%m-%d")




    firstLine = True
    firstInSchedule = True
    firstInDay = True
    firstInRoom = True

    dayChange = True
    roomChange = True
    
    
    # Contains the state for the first line
    roomEnd = False
    dayEnd = False
    dayOpen = True
    roomOpen = True
    
    
    lastDay = 0
    lastRoom = ''

    
    for r in reader:
        
        # First line contains headings, skip it
        if firstLine:
            firstLine = False
            continue

        # Read data
        day = int(r[0])
        time = r[1]
        persons = r[2]
        title = r[3]
        description = r[4]
        room = r[5]

        # Calculate dates:
        thisDate = StartDate + timedelta(days=dayIndex-1)
        datumStrStart = thisDate.strftime('%Y-%m-%d')
        dateStop = thisDate + datetime.timedelta(0,3600)
        datumStrStop = dateStop.strftime('%Y-%m-%d')
        timeStrStop = dateStop.strftime('%H:%M:%S')
        
        start = datumStrStart + 'T' + time + timezone
        end = datumStrStop + 'T' + timeStrStop + timezone


        if not day == lastDay:
            dayChange = True
        else:
            dayChange = False

        if not room == lastRoom:
            roomChange = True
        else:
            roomChange = False


        if firstInSchedule:
            firstInSchedule = False
            roomEnd = False
            dayEnd = False
            dayOpen = True
            roomOpen = True

        elif not dayChange and roomChange:
            roomEnd = True
            dayEnd = False
            dayOpen = False
            roomOpen = True
            
        elif not dayChange and not roomChange:
            roomEnd = False
            dayEnd = False
            dayOpen = False
            roomOpen = False

        elif not dayChange and not roomChange:
            roomEnd = True
            dayEnd = True
            dayOpen = True
            roomOpen = True
            
        
        if roomEnd:
            print(roomEnding)
        
        if dayEnd:
            print(dayEnding)
        
        if dayOpen:
            dayIndex = dayIndex + 1
            print(dayOpening.format(dayIndex, datumStrStart, start, end))
            
        if roomOpen:
            print(roomOpening.format(room))
        
        idd = "1"
        guid = "1"
        url = ""
        duration = "01:00"
        slug = ""
        track = ""
        typ = ""
        subtitle = ""
        language = "en"
        print(eventOpening.format(idd, guid, url, datumStrStart, start, duration, room, slug, title, subtitle, track, typ, language, description, persons))

        #for each person:
        #    print(person.format(personId, personName))
        print(eventEnding)

        lastDay = day
        lastRoom = room
    
    print(roomEnding)
    print(dayEnding)
    print(xmlEnding)

def csvReader(argv):
    
    # open file for reading
    if len(argv) > 0 and len(argv[0]) > 0:
        if argv[0] in ["--help", "-h"]:
            printUsage()
        else:

            filename = argv[0]

            if os.path.isfile(filename):
                with open(filename) as csv_data:
                    reader = csv.reader(csv_data, skipinitialspace=True)
                    printXML(reader)
            else:
                print("Error: File " + filename + " not found.")
                printUsage()
    else:
        print("Error: No schedule file given")


def printUsage():
    print("Usage: python csv2xml.py filename")
    print("Where filename is a the file .csv file to be converted to XML format.")


if __name__ == '__main__':
    csvReader(sys.argv[1:])
