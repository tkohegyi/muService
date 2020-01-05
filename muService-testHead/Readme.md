Test Heads
====================

In this folder you may find lots of Test Heads for muService.


Common Capabilities of Test Heads
====

Level 0:
 
 Implemented methods:
 
 - getInformation / example response: 
 ```
 { 
   "id": "....",             //ID of the specific Test Head 
   "software": "...",        //Information about the test head software
   "version": "...",         //information about the test head software version
   "capabilityCode": 0,      //Capability code (Level X) of the Test Head
   "type": "..."             //Type of the Test Head
 }
 ```

 - getStatus / example response: 
 ```
 { 
   "id": "....",             //ID of the specific Test Head 
   "comment": "myComment",   //comment added to the Test Head
   "status": "OK",           //Test head status. Can be "OK", or other, which means problem with the test head
   "information": "105"      //Optional field, may contain special Test head specific information 
 }
 ```

