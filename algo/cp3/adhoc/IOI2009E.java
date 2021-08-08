import java.util.*;

public class IOI2009E {
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);
		
		int slotNum = sc.nextInt();
		int carNum = sc.nextInt();
		int[] rates = new int[slotNum];
		int[] weights = new int[carNum];
		
		for (int i=0; i<slotNum; i++) {
			rates[i] = sc.nextInt();
		}
		
		for (int j=0; j<carNum; j++) {
			weights[j] = sc.nextInt();
		}
		
		PriorityQueue<Integer> slotMinQueue = new PriorityQueue<>();
		for (int i=0; i<slotNum; i++) {
			slotMinQueue.offer(i);
		}
		Queue<Integer> carQueue = new LinkedList<>();
		int[] carToSlot = new int[carNum];
		Arrays.fill(carToSlot, -1);
		long revenue = 0;
		for (int k=0; k<2*carNum; k++) {
			int car = sc.nextInt();
			boolean driveIn = true;
			if (car < 0) {
				driveIn = false;
				car = -car;
			}
			car--;
			
			if (driveIn) {
				if (slotMinQueue.isEmpty()) {
					carQueue.offer(car);
				} else {
					int slot = slotMinQueue.poll();
					carToSlot[car] = slot;
					revenue += weights[car] * rates[slot];
				}
			} else {
				int freeSlot = carToSlot[car];
				carToSlot[car] = -1;
				if (!carQueue.isEmpty()) {
					int waitCar = carQueue.poll();
					carToSlot[waitCar] = freeSlot;
					revenue += weights[waitCar] * rates[freeSlot];
				} else {
					slotMinQueue.offer(freeSlot);
				}
			}
		}
		System.out.println(revenue);
	}
}