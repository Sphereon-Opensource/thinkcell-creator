# Thinkcell creator

This program converts a comma seperated values file (CSV) from for instance a database export and creates a 
Thinkcell compatible output according to a user-defined template.

Please note that this program has been written in a few hours, so it lacks a lot of features and is quite crude.
Having said that it does allow you to use all kinds of different input CSV files together with template files to create the ThinkCell output.


Several configuration properties can be set in the file config/application.properties
The template(s) can be found in the config folder as well

## Running the program
To run the program execute the following command from the directory where you extracted the program:

 ```
 thinkcellOutput.bat <input-csv-file>
 ```
 
 So for instance
 
 ```
 thinkcellOutput.bat /Users/nklomp/Example-Input.csv
 ```
 
 If you want to run it yourself without the cmd script because you are using another Operating System then Windows, execute:
 
 ```
 java -jar think-cell.*.jar <input-csv-file>
 ```
 
 
 ## Instalation
 You can extract the zip file in any folder you like. No additional installation is required.
 This application is written in Java. So a Java Runtime Environment (https://java.com) version 8 is required.
 
 Once you have extracted the application into a folder of your choice, you will have to start the application in a command prompt
 in that folder.
 
 
 ## Template
 A sample template:
 
 ```
 [{
     "template": "template.pptx",
     "data": [{
         "name": "Chart5",
         "table":
             [
 
                 [null, <#list headers as header>{"string": "${header}"}<#sep>,</#list>],
                 [],
 <#list records as record>               [<#list record as recordValue>{"<#if recordValue?is_first>string<#else>number</#if>": <#if recordValue?is_first>"${recordValue}"<#else>${recordValue}</#if>}<#sep>, </#list>]<#sep>,
 </#list>
 
             ]
         }
     ]
 }]
```

The header variable will be replaced by all the headers in the input CSV file on the first line as long as the following
configuration file property is set:

```
# Whether a header line is present in the CSV file
thinkcell.csv.header-line-present=true
```

The record variable will be replaced by a single line of values from the CSV file.
The example above showcases a distinction for the first column, where the key is the word "string" whilst the rest of the collumns have "number" as a key.

## Output Example
In the current version the output will be printed on the console. A future version will be able to write an output file as well.
Since we print to the console it means you should open a CMD prompt before running the thinkcellOutput.bat file

You can copy the values between the  ------ CUT ------ lines

```
[{
    "template": "template.pptx",
    "data": [{
        "name": "Chart5",
        "table":
            [

                [null, {"string": "﻿CAPITAL_PROJECT_NAME"},{"string": "CAPEX"},{"string": "OPEX"},{"string": "Feedgas"},{"string": "Shipping"},{"string": "Gov''t Take"},{"string": "Non LNG Revenue (condensate, domgas)"},{"string": "LNG Delivered Cost"}],
                [],
                [{"string": "Brown"}, {"number": 1,00}, {"number": 1,30}, {"number": }, {"number": 0,89}, {"number": }, {"number": 1,98}, {"number": 6,93}],
                [{"string": "Fair"}, {"number": 1,79}, {"number": 2,00}, {"number": }, {"number": 0,44}, {"number": }, {"number": 1,10}, {"number": 2,20}],
                [{"string": "Neo"}, {"number": 1,20}, {"number": 0,91}, {"number": 4,00}, {"number": 0,58}, {"number": }, {"number": 0,84}, {"number": 3,00}],
                [{"string": "Gogo"}, {"number": 1,00}, {"number": 1,00}, {"number": }, {"number": 1,20}, {"number": }, {"number": 0,44}, {"number": 3,00}],
                [{"string": "Quick win"}, {"number": 2,00}, {"number": 1,22}, {"number": 2,00}, {"number": 1,00}, {"number": }, {"number": 0,76}, {"number": 5,05}],
                [{"string": "Triumph"}, {"number": 1,00}, {"number": 0,83}, {"number": 1,00}, {"number": 0,59}, {"number": }, {"number": 0,54}, {"number": 4,79}]
            ]
        }
    ]
}]
```