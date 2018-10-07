import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Team implements Comparable<Team>{
	private int team_no;
	private int score;
	private int time;

	Team(int no){
		this.team_no = no;
		this.score = 0;
		this.time = 0;
	}

	public int getTeam_no() {
		return team_no;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int compareTo(Team t) {
		if (this.getScore() == t.getScore()){
			if (this.getTime() == this.getTime()) {
				return this.getTeam_no() - t.getTeam_no();
			}
			else {
				return -(this.getTime() - this.getTime());
			}
		}
		return -(this.getScore() - t.getScore());
	}

	@Override
	public String toString() {
		return "Team : " + this.getTeam_no() + " Score : " + this.getScore() + " Time : " + this.getTime();
	}
	
	//Team's mergeSort. Average case is O(n log(n))
	public static void merge(ArrayList<Team> arr, int left, int mid, int right) {
		int i;
		int j;
		int index;
		int l_len = mid + 1 - left;
		int r_len = right - mid;

		//Initial two arrays
		ArrayList<Team> l_arr = new ArrayList<Team>();
		ArrayList<Team> r_arr = new ArrayList<Team>();
		for (i = 0; i < l_len; i++) {
			l_arr.add(arr.get(left + i));
		}
		for (j = 0; j < r_len; j++) {
			r_arr.add(arr.get(mid + 1 + j));
		}

		i = 0;
		j = 0;
		index = left;

		while(i < l_len && j < r_len) {
			if(l_arr.get(i).compareTo(r_arr.get(j)) < 0) {
				arr.set(index, l_arr.get(i));
				i++;
			}
			else {
				arr.set(index, r_arr.get(j));
				j++;
			}
			index++;
		}

		while (i < l_len) {
			arr.set(index, l_arr.get(i));
			i++;
			index++;
		}
		while (j < r_len) {
			arr.set(index, r_arr.get(j));
			j++;
			index++;
		}
	}

	public static void mergeSort(ArrayList<Team> arr, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;

			mergeSort(arr, left, mid);
			mergeSort(arr, mid+1, right);
			merge(arr, left, mid, right);
		}
	}

	//Update a team's score and time
	public static void updateTeam (int time, String status, Team temp){
		if (status.equals("C")){
			temp.setScore(temp.getScore() + 1);
			temp.setTime(temp.getTime() + time);
		}
		else{
			temp.setTime(temp.getTime() + 5);
		}
	}

	public static void main (String[] args) throws IOException{
		HashMap <Integer, Team> teamMap = new HashMap <Integer, Team>();
		File file = new File ("input.txt");
		Scanner sc = new Scanner (file).useDelimiter("\\s+");

		while (sc.hasNextLine()){
			//input variable from each row
			int no = sc.nextInt();
			sc.next ();
			int time = sc.nextInt();
			String status = sc.next();

			//add Team to the hashMap if this team is not in the hashMap
			//then update their score and time
			if (status.equals("C") || status.equals("I")){
				if (!teamMap.containsKey(no)){
					teamMap.put(no, new Team(no));
				}
				updateTeam(time, status, teamMap.get(no));
			}
		}
		sc.close();

		//Convert HashMap to ArrayList
		Collection<Team> ValueSet = teamMap.values();
		ArrayList<Team> teamArray = new ArrayList<Team>(ValueSet);
		for(Team temp : teamArray) {
			System.out.println(temp);
		}

		System.out.println();

		//Sort the ArrayList of Team with mergeSort
		mergeSort(teamArray, 0, teamArray.size() - 1);
		for(Team temp : teamArray) {
			System.out.println(temp);
		}
		
		System.out.println();

		//Sort the ArrayList. Control group for test
		Collections.sort(teamArray);
		for(Team temp : teamArray) {
			System.out.println(temp);
		}
		
		//write the output to output.txt
		FileWriter filewriter = new FileWriter("output.txt");
		PrintWriter printWriter = new PrintWriter("output.txt");
		for (Team tmp : teamArray) {
			printWriter.println(tmp.getTeam_no() + " " + tmp.getScore() + " " + tmp.getTime());
		}
		printWriter.close();
	}
}
