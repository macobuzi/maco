import java.util.*;

public class Uva119 {
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			int numFriend = sc.nextInt();
			List<String> nameList = new ArrayList<>();
			Map<String, Integer> nameToIdMap = new HashMap<>();
			for (int i = 0; i < numFriend; i++) {
				String name = sc.next();
				nameToIdMap.put(name, i);
				nameList.add(name);
			}

			int[] networths = new int[numFriend];
			for (int i = 0; i < numFriend; i++) {
				String sender = sc.next();
				int senderId = nameToIdMap.get(sender);
				int money = sc.nextInt();

				int numGift = sc.nextInt();
				if (numGift > 0) {
					int giftPrice = money / numGift;
					for (int j = 0; j < numGift; j++) {
						String receiver = sc.next();
						int receiverId = nameToIdMap.get(receiver);
						networths[receiverId] += giftPrice;
						networths[senderId] -= giftPrice;
					}
				}
				
			}

			for (String name : nameList) {
				System.out.printf("%s %d\n", name, networths[nameToIdMap.get(name)]);
			}
			if (sc.hasNext()) {
				System.out.println();
			}
		}
	}
}