import flask
from flask import request, Response
from xml.etree import ElementTree


"""
Postman was used to test this Web Service.
XML should be passed in the body of the request.
"""
app = flask.Flask(__name__)
app.config["DEBUG"] = True

@app.route('/webservice/postman', methods = ["POST"]) #Allow only POST Methods
def webservicePostman():
    if(flask.request.get_data().strip() != b''): #Check if data is empty, if yes, returns an error response
        root = ElementTree.fromstring(flask.request.get_data()) #Converts body data
        com = root.find("DeclarationList/Declaration") #Finds and stores the required Declaration Tag
        site = com.find("DeclarationHeader/SiteID") #Finds and stores the required SiteID Tag
        if com != None and site != None: #Checks if Declaration and SiteID tags exist, if they don't, an error response is returned
            if com.get("Command") == "DEFAULT": #Checks if the command attribute for the Declaration tag is correct, if yes, continues, if no, returns a status code of -1
                if site.text == "DUB": #Checks if the value of the SiteID text, if yes, returns a status code of 0, if no, returns a status code of -2
                    return Response("<response><statuscode>0</statuscode><reason>Correctly Structured Document</reason></response>", mimetype='text/xml')
                return Response("<response><statuscode>-2</statuscode><reason>Invalid Site Specified</reason></response>", mimetype='text/xml')
            return Response("<response><statuscode>-1</statuscode><reason>Invalid Command Specified</reason></response>", mimetype='text/xml')
        return Response("<response>Error, Invalid XML provided</response>", mimetype='text/xml')
    return Response("<response>Error, No XML Provided</response>", mimetype='text/xml')
app.run() #Starts a server and runs the app