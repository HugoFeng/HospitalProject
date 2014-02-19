#Hospital Project

@Author: Xuyang Feng

@Email: hugo.fxy@gmail.com

@Last edit: 05-01-2014

----------------

## Introduction

This is a representation of a hospital with full 6 departments. 

## Code structure

Here is a hand typed code structure of this hospital. Some less important methods are omitted.

	Hospital
	┃
	┣ class Patient
	┃  ┣ String name
	┃  ┣ int severity
	┃  ┣ boolean wantsSingleRoom
	┃
	┣ class Reception
	┃  ┣ class PatientInfo
	┃  ┃  ┣ Patient patient
	┃  ┃  ┣ Department department
	┃  ┃  ┣ Room room
	┃  ┃
	┃  ┣ BinaryTree register
	┃  ┣ PatientInfo getPatientInfo(String)
	┃  ┣ void addPatientInfo(Patient, Department, Room)
	┃  ┣ void removePatientInfo(String)
	┃
	┣ abstract class Department
	┃  ┣ abstract class Room
	┃  ┃  ┣ RoomType roomType
	┃  ┃  
	┃  ┣ class WaitingRoom extends Room
	┃  ┃  ┣ String name
	┃  ┃  ┣ PriorityQueue waitingList
	┃  ┃  ┣ void addWaitingPatient(Patient)
	┃  ┃  ┣ Patient popWaitingPatient()
	┃  ┃  ┣ boolean noOneWaiting()
	┃  ┃  ┣ int numberOfPeopleWaiting()
	┃  ┃
	┃  ┣ class Ward extends Room
	┃  ┃  ┣ final int number
	┃  ┃  ┣ final int capacity
	┃  ┃  ┣ LinkedList patients
	┃  ┃  ┣ boolean isFull()
	┃  ┃  ┣ boolean isSingleRoom()
	┃  ┃  ┣ boolean hasPatient(Patient)
	┃  ┃  ┣ void addPatient(Patient)
	┃  ┃  ┣ void removePatient(Patient)
	┃  ┃
	┃  ┣ String name
	┃  ┣ WaitingRoom waitingRoom
	┃  ┣ LinkedList wardsForSingle
	┃  ┣ LinkedList wardsForMulti
	┃  ┣ LinkedList deviceList
	┃  ┣ void addPatient(Patient)
	┃  ┣ void addPatientFromWaitingRoom()
	┃  ┣ void signOutPatient(Patient)
	┃  ┣ Device hasDevice(String)
	┃  ┣ void addDevice(String, int)
	┃  ┣ void removeDevice(String, int)
	┃  
	┣ class NeurologyDepartment extends Department
	┣  ...
	┣ class OncologyDepartment extends Department
	┃  
	┣ LinkedList departmentList  
	┣ EdgeGraph departmentMap  
	┣ Reception reception
	┣ Department getDepartmentByName(String)
	┣ Device deviceFactory(String)
	┣ String getRoomNumberForPatientNamed(String)
	┣ void signInPatientToDepartment(Patient, String)
	┣ void signOutPatient(Patient)
	┣ void signOutPatient(String)
	┣ void printRouteFromTo(String, String)
	┣ void printRouteFromToAvoiding(String, String, String)
	┣ void printRouteFromToVia(String, String, String)
	┣ void closestDepartmentToWithDevice(String, String)
	┣ String showRegister()	

* Code for Hospital and code for data structures are kept in different packages
* All kinds of device classes and hospital class are in seperate source files, because the details of devices are made by the manufactures, which have nothing to do with the hospital. 
* The deviceFactory method is written in Hospital class instead of Device class. Because making a new kind of device class shouldn't modify the original Device class, instead, the Hospital is clearly acknowledged when importing new device, so I think putting the deviceFactory in Hospital is the best choice.


