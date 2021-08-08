import java.util.*;


/**
   -- Requirement
   Three type of employee: responder, manager, director
   Call: responder -> manager -> director

   -- Class
   Employee, Call, CallCenter

   -- Relationship
   CallCenter:
      + List<Employee> employees
	  + Queue<Call> waitList
   Call
      + atLeastRank
      + assignedEmployee
   Employee 
      + rank
	  + currentCall

   -- Action
   
      + handle call ? Caller will indicate which problem they need to talk, 
	    based on problem select least rank for the call. If can PICK EMPLOYEE
		to handle call then assign call to employee. If not then add to queue.

      + queue processing ? whenever call end, employee will RELEASE call, and
	    signal back to call center. Call center will try to PICK CALL by fifo
		for the free employee. 

   -- Implement
      
*/
public class CallCenter {
	public static enum Rank {
		RESPONDER, MANAGER, DIRECTOR
	}

	public static class Center {
		private Map<Rank, List<Employee>> employees;
		private Map<Rank, Queue<Call>> waitLists;

		public Center() {
			employees = new HashMap<>();
			waitLists = new HashMap<>();

			for (Rank rank : Rank.values()) {
				employees.put(rank, new ArrayList<>());
				waitLists.put(rank, new LinkedList<>());
			}
			System.out.printf("Center: Initialized\n");
		}

		public void addEmployee(Employee employee) {
			employees.get(employee.rank).add(employee);
			System.out.printf("Center: Added employee %s, rank %s\n", employee.getName(), employee.getRank());
		}

		public boolean dispatch(Call call) {
			System.out.printf("Center: Received call %d with rank %s \n", call.getId(), call.getRank());
			Employee assignee = findAssignee(call);
			if (assignee != null) {
				transferCall(assignee, call);
				return true;
			} else {
				System.out.printf("Center: Busy, push call %d to queue\n", call.getId());
				call.reply("Please wait ...");
				waitLists.get(call.getRank()).offer(call);
				return false;
			}
		}

		public void onRelease(Employee employee) {
			System.out.printf("Center: Employee %s is free, try to pickup new call\n", employee.getName());
			for (Rank rank : waitLists.keySet()) {
				if (!waitLists.get(rank).isEmpty()
					&& employee.rank.ordinal() >= rank.ordinal()) {
					Employee assignee = findAssignee(waitLists.get(rank).peek());
					if (assignee != null) {
						Call call = waitLists.get(rank).poll();
						transferCall(assignee, call);
						break;
					}
				}
			}
		}

		private Employee findAssignee(Call call) {
			for (Rank rank : Rank.values()) {
				if (call.getRank().ordinal() > rank.ordinal())
					continue;
				for (Employee employee : employees.get(rank)) {
					if (employee.isFree())
						return employee;
				}
			}
			return null;
		}

		private void transferCall(Employee assignee, Call call) {
			System.out.printf("Center: Transfer call %d to %s\n", call.getId(), assignee.getName());
			call.connect(assignee);
			assignee.handle(call);
		}
	}

	public static class Call {
		private int id;
		private Rank rank;
		private Employee assignee;

		public Call(int id, Rank rank) {
			this.id = id;
			this.rank = rank;
		}

		public int getId() {
			return id;
		}

		public Rank getRank() {
			return rank;
		}

		public void connect(Employee employee) {
			System.out.printf("Call %d: Connected to %s\n", id, employee.getName());
			assignee = employee;
		}

		public void finish() {
			System.out.printf("Call %d: Finished\n", id);
			assignee.releaseCall();
		}

		public void reply(String message) {
			System.out.printf("Call %d: %s\n", id, message);
		}
	}
	
	public static abstract class Employee {
		protected String name;
		protected Rank rank;
		protected Call inCall;
		protected Center center;

		public Employee(String name, Center center, Rank rank) {
			this.name = name;
			this.center = center;
			this.rank = rank;
			this.inCall = null;
		}

		public Rank getRank() {
			return rank;
		}

		public String getName() {
			return name;
		}
		
		public void handle(Call call) {
			System.out.printf("Employee %s: Handle call %d\n", name, call.getId());
			inCall = call;
		}

		public void releaseCall() {
			System.out.printf("Employee %s: Released call %d\n", name, inCall.getId());
			inCall = null;
			center.onRelease(this);
		}

		public boolean isFree() {
			return inCall == null;
		}
	}

	public static class Responder extends Employee {
		public Responder(String name, Center center) {
			super(name, center, Rank.RESPONDER);
		}
	}

	public static class Manager extends Employee {
		public Manager(String name, Center center) {
			super(name, center, Rank.MANAGER);
		}
	}

	public static class Director extends Employee {
		public Director(String name, Center center) {
			super(name, center, Rank.DIRECTOR);
		}
	}
	
	public static void main(String[] argv) {
		Center center = new Center();
		center.addEmployee(new Director("John", center));
		center.addEmployee(new Manager("Sarah", center));
		center.addEmployee(new Responder("Peter", center));
		center.addEmployee(new Responder("Linh", center));
		center.addEmployee(new Responder("Alex", center));


		Call[] calls = new Call[10];
		for (int i=0; i<7; i++) {
			calls[i] = new Call(i+1, Rank.RESPONDER);
		}
		for (int i=7; i<9; i++) {
			calls[i] = new Call(i+1, Rank.MANAGER);
		}
		for (int i=9; i<10; i++) {
			calls[i] = new Call(i+1, Rank.DIRECTOR);
		}

		// Simulation
		center.dispatch(calls[0]);
		center.dispatch(calls[1]);
		center.dispatch(calls[9]);
		center.dispatch(calls[3]);
		calls[0].finish();
		center.dispatch(calls[5]);
		calls[5].finish();
		center.dispatch(calls[4]);
		center.dispatch(calls[8]);
		center.dispatch(calls[6]);
		calls[6].finish();
		calls[3].finish();
		center.dispatch(calls[7]);
		calls[4].finish();
		center.dispatch(calls[2]);
		calls[8].finish();
		calls[7].finish();
		calls[9].finish();
		calls[1].finish();
		calls[2].finish();
	}
}
