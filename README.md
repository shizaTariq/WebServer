# 
###Author - Shiza Tariq
###Http Web server : implements 1.1 protocol

##Implements HEAD : The HEAD method is identical to GET except that the server
does not return a message-body in the response. 
##GET : The GET method means retrieve whatever information (in the form of an entity) is identified by the Request-URI. 
If the Request-URI refers to a data-producing process, it is the produced data which shall be returned as
the entity in the response and not the source text of the process
POST :


Handles HTTP Errors : 
#200 : OK
#404 : Not Found 
#302 : Moved Temporily

#class : Service.java
------ public void run() ------
checks method GET, HEAD AND POST , returns HTTP errors incase of OK 200, 404 Not found and moved temporily 302.

#class : WebServer.java
----public static void startServer(int port) throws IOException---
makes new server socket, and starts listening port.

#-----unit test ----
Multiple test cases are made to check Http methods GET AND POST, also to check HTTP error 404


Github link : https://github.com/shizaTariq/WebServer

