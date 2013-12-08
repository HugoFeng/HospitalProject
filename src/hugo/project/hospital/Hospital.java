package hugo.project.hospital;

import hugo.project.hospital.Hospital.Department.Room;
import hugo.project.hospital.Hospital.Reception.PatientInfo;
import hugo.util.structure.DoubleLinkedList;
import hugo.util.structure.LinkedList;
import hugo.util.structure.PriorityQueue;

enum RoomType {
	WAITINGROOM, WARD
}

public class Hospital {
	protected class Patient implements Comparable<Patient>{
		private final String name;
		private int severity;
		private boolean wantsSingleRoom;
		
		public Patient(String name, int severity, boolean wantsSingleRoom) {
			this.name = name;
			this.severity = severity;
			this.wantsSingleRoom = wantsSingleRoom;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getSeverity() {
			return this.severity;
		}
		
		public boolean wantsSingleRoom() {
			return this.wantsSingleRoom;
		}

		@Override
		public int compareTo(Patient o) {
			return name.compareTo(o.getName());
		}
		
		public String toString() {
			return getName();
		}
	}

	protected class Reception {
		protected class PatientInfo implements Comparable<PatientInfo>{
			protected Patient patient;
			protected Department department;
			protected Room room;
			
			public PatientInfo(Patient p, Department dep, Room room) {
				this.patient = p;
				this.department = dep;
				this.room = room;
			}
			public String getName() {
				return patient.getName();
			}
			public Patient getPatient() {
				return patient;
			}
			
			public Department getDepartment() {
				return department;
			}
			
			public Room getRoom() {
				return room;
			}

			@Override
			public int compareTo(PatientInfo o) {
				return patient.name.compareTo(o.getPatient().getName());
			}

		}
		
		protected LinkedList<PatientInfo> register;
		
		public Reception(){
			register = new LinkedList<PatientInfo>();
		}

		public PatientInfo getPatientInfo(String name) {
			for (PatientInfo info : register) {
				if(info.getName().equals(name)) return info;
			}
			return null;
		}
		
		public PatientInfo getPatientInfo(Patient p) {
			return getPatientInfo(p.getName());
		}
		
		public void addPatientInfo(Patient p, Department dep, Room room) {
			register.addFirst(new PatientInfo(p, dep, room));
		}
		
		public void removePatientInfo(PatientInfo info) {
			register.remove(info);
		}
		
		public void removePatientInfo(String name) {
			register.remove(getPatientInfo(name));
		}
		
		public void removePatientInfo(Patient p) {
			register.remove(getPatientInfo(p));
		}

		public String show() {
			String output = "";
			for (PatientInfo info : register) {
				output += info.getName() + ":\t"
						+ info.getDepartment() + ",\t"
						+ info.getRoom() +"\n";;
			}
			return output;
		}
		
	}

	protected abstract class Department implements Comparable<Department>{
		protected abstract class Room {
			protected RoomType roomType;
			public RoomType getRoomType() {
				return roomType;
			}
			
			public abstract void removePatient(Patient p);
		}

		protected class WaitingRoom extends Room{
			private String name;
			protected PriorityQueue<Patient> waitingList;
			
			public WaitingRoom(String name) {
				this.name = name;
				waitingList = new PriorityQueue<Hospital.Patient>();
				this.roomType = RoomType.WAITINGROOM;
			}
			
			public void addWaitingPatient(Patient p) {
				waitingList.push(p, p.severity);
			}
			
			public Patient popWaitingPatient() {
				return waitingList.pop();
			}
			
			public boolean noOneWaiting() {
				return waitingList.empty();
			}
			
			public int numberOfPeopleWaiting() {
				return waitingList.size();
			}
			
			public String toString() {
				return name;
			}
			
			public String info() {
				String patientListString = "<< ";
				for (Patient patient : waitingList) {
					patientListString += patient.toString() + " << ";
				}
				return "  -> "
						+this.toString()
						+" (" + numberOfPeopleWaiting() + ") "
						+"[ "
						+patientListString
						+" ]";
			}

			@Override
			public void removePatient(Patient p) {
				waitingList.remove(p);
			}
			
		}

		protected class Ward extends Room implements Comparable<Ward>{
			private final int number;
			private final int capacity;
			protected LinkedList<Patient> patients;
			
			public Ward(int number, int capacity) {
				this.number = number;
				this.capacity = capacity;
				patients = new LinkedList<Hospital.Patient>();
				this.roomType = RoomType.WARD;
			}
			
			public boolean isFull() {
				return patients.size() == capacity;
			}
			
			public boolean isSingleRoom() {
				return capacity == 1;
			}
			
			public boolean hasPatient(Patient p) {
				return patients.contains(p);
			}
			
			public void addPatient(Patient p) {
				if(patients.size() < capacity) patients.addFirst(p);
			}
			
			public void removePatient(Patient p) {
				patients.remove(p);
			}

			@Override
			public int compareTo(Ward o) {
				return new Integer(number).compareTo(o.number);
			}
			
			public String info() {
				String patientListString = "";
				for (Patient patient : patients) {
					patientListString += patient.toString() + " ";
				}
				return "  -> "
						+this.toString()
						+" (" + patients.size() +"/" + capacity + ") "
						+"[ "
						+patientListString
						+" ]\n";
			}
			
			public String toString() {
				return "Room " + number;
			}
		}
		
		protected String name;
		protected WaitingRoom waitingRoom;
		protected DoubleLinkedList<Ward> wards;
		
		public boolean isFull() {
			for (Ward room : wards) {
				if(!room.isFull()) return false;
			}
			return true;
		}
		
		public void addPatient(Patient p) {
			if (isFull()) {
				waitingRoom.addWaitingPatient(p);
				reception.addPatientInfo(p, this, waitingRoom);
			} else {
				for (Ward room : (p.wantsSingleRoom()?wards.reversedIterator():wards)) {
					if(!room.isFull()) {
						room.addPatient(p);
						reception.addPatientInfo(p, this, room);
						return;
					}
				}
			}
		}

		
		public void signOutPatient(PatientInfo info) {
			Room room = info.getRoom();
			room.removePatient(info.getPatient());
			if(!waitingRoom.noOneWaiting()) addPatient(waitingRoom.popWaitingPatient());
		}
		
		public void signOutPatient(Patient p) {
			signOutPatient(reception.getPatientInfo(p));
		}

		public int compareTo(Department dep) {
			return this.name.compareTo(dep.name);
		}
		
		public String toString() {
			return name;
		}
		
		public String info() {
			String roomInfoListString = "";
			for (Ward room : wards) {
				roomInfoListString += room.info();
			}
			return this.toString() +":\n" 
			+ waitingRoom.info() + "\n"
			+ roomInfoListString + "\n";
		}
	}
	
	protected class NeurologyDepartment extends Department {
		public NeurologyDepartment() {
			name = "Neurology";
			waitingRoom = new WaitingRoom("WR " + name);
			wards = new DoubleLinkedList<Ward>();
			wards.addLast(new Ward(107, 2));
			wards.addLast(new Ward(108, 2));
			wards.addLast(new Ward(109, 2));
			wards.addLast(new Ward(101, 1));
			wards.addLast(new Ward(102, 1));

		}
	}

	protected class CardiologyDepartment extends Department {
		public CardiologyDepartment() {
			name = "Cardiology";
			waitingRoom = new WaitingRoom("WR " + name);
			wards = new DoubleLinkedList<Ward>();
			wards.addLast(new Ward(111, 3));
			wards.addLast(new Ward(110, 2));
			wards.addLast(new Ward(103, 1));
			wards.addLast(new Ward(104, 1));

		}
	}
	
	protected LinkedList<Department> departmentList;
	protected Reception reception;
	
	public Hospital() {
		departmentList = new LinkedList<Hospital.Department>();
		departmentList.addLast(new NeurologyDepartment());
		departmentList.addLast(new CardiologyDepartment());
		reception = new Reception();
	}
	public Department getDepartmentByName(String depName) {
		for (Department department : departmentList) {
			if(department.toString().equals(depName)) return department;
		}
		return null;
	}
	
	public String getRoomNumberForPatientNamed(String name) {
		return reception.getPatientInfo(name).getRoom().toString();
	}
	
	public void signInPatientToDepartment(Patient p, String depName) {
		Department department = getDepartmentByName(depName);
		department.addPatient(p);
	}
	
	
	public void signOutPatient(Patient p) {
		signOutPatient(p.getName());
	}
	
	public void signOutPatient(String name) {
		PatientInfo info = reception.getPatientInfo(name);
		Department department = info.getDepartment();
		department.signOutPatient(info.getPatient());
		reception.removePatientInfo(info);
	}
	
	public String showRegister() {
		return reception.show();
	}
	
	public String toString() {
		String output = "";
		output += "\n******************* PATIENTS ********************\n";
		output += reception.show();
		output += "\n****************** DEPARTMENTS ******************\n";
		for (Department department : departmentList) {
			output += department.info() + "\n";
		}
		return output;
	}
}
