package hugo.project.hospital;

import hugo.project.hospital.Hospital.Patient;
import hugo.util.structure.*;

public class ProjectTest {

	public static void main(String[] args) {
		Hospital hospital = new Hospital();
		Patient p1 = hospital.new Patient("p1", 1, false);
		Patient p2 = hospital.new Patient("p2", 2, false);
		Patient p3 = hospital.new Patient("p3", 1, true);
		Patient p4 = hospital.new Patient("p4", 3, false);
		Patient p5 = hospital.new Patient("p5", 0, false);
		Patient p6 = hospital.new Patient("p6", 1, true);
		Patient p7 = hospital.new Patient("p7", 1, false);
		Patient p8 = hospital.new Patient("p8", 2, false);
		Patient p9 = hospital.new Patient("p9", 1, false);
		Patient p10 = hospital.new Patient("p10", 3, true);
		Patient p11 = hospital.new Patient("p11", 0, false);
		Patient p12 = hospital.new Patient("p12", 1, true);
		Patient p13 = hospital.new Patient("p13", 1, true);
		Patient p14 = hospital.new Patient("p14", 1, true);
		hospital.signInPatientToDepartment(p1, "Neurology");
		hospital.signInPatientToDepartment(p2, "Neurology");
		hospital.signInPatientToDepartment(p3, "Neurology");
		hospital.signInPatientToDepartment(p4, "Neurology");
		hospital.signInPatientToDepartment(p5, "Neurology");
		hospital.signInPatientToDepartment(p6, "Neurology");
		hospital.signInPatientToDepartment(p7, "Neurology");
		hospital.signInPatientToDepartment(p8, "Neurology");
		hospital.signInPatientToDepartment(p9, "Neurology");
		hospital.signInPatientToDepartment(p10, "Neurology");
		hospital.signInPatientToDepartment(p14, "Cardiology");
		hospital.signInPatientToDepartment(p11, "Neurology");
		hospital.signInPatientToDepartment(p12, "Neurology");
		hospital.signInPatientToDepartment(p13, "Neurology");
		System.out.println(hospital);
		hospital.signOutPatient("p5");
		System.out.println(hospital);
		System.out.println(hospital.departmentMap.show());
		
		hospital.printRouteFromTo("Neurology", "Radiotherapy");
		hospital.printRouteFromTo("Neurology", "Oncology");
		hospital.printRouteFromToAvoiding("Neurology", "Radiotherapy", "Oncology");
		hospital.printRouteFromToAvoiding("Neurology", "Radiotherapy", "Cardiology");
		hospital.printRouteFromToVia("Neurology", "Physiotherapy", "Radiology");
		hospital.printRouteFromToVia("Neurology", "Physiotherapy", "Radiotherapy");
		
		
		System.out.println("Closest Department from Radiology with device needle is: "
				+ hospital.closestDepartmentToWithDevice("Radiology", "needle"));
		
//		EdgeGraph<Patient> graph = new EdgeGraph<Hospital.Patient>();
//		graph.addNode(p1);
//		graph.addNode(p2);
//		graph.addNode(p3);
//		graph.addNode(p4);
//		graph.addNode(p5);
//		graph.addEdge(p1, p2);
//		graph.addEdge(p1, p3);
//		graph.addEdge(p2, p3);
//		graph.addEdge(p2, p4);
//		graph.addEdge(p3, p1);
//		graph.addEdge(p4, p5);
//		System.out.println(graph.show());
////		LinkedList<Patient> path = graph.findPath(p1, p4);
////		for (Patient patient : path) {
////			System.out.println(patient.toString());
////		}
//
//		for (Patient patient : graph.bfsIteratFrom(p1)) {
//			System.out.println(patient);
//		}
//		System.out.println();
//		for (Patient patient : graph.dfsIteratFrom(p1)) {
//			System.out.println(patient);
//		}
	}

}
