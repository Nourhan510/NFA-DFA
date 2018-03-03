import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Main {
	
	static PrintWriter writer;

	public static void Read() throws IOException {

		FileReader in = new FileReader("in.in");
		BufferedReader br = new BufferedReader(in);

		String line;
		String[] DFA = new String[7];
		int i = 0;
		int DFAnum = 1;
		while ((line = br.readLine()) != null) {
				DFA[i] = line;
				i++;
				if (i == 6) {
					boolean result = DFA(DFA[0], DFA[1], DFA[2], DFA[3],
							DFA[4], DFA[5], DFAnum);
					DFAnum++;
					i = 0;
					br.readLine();
				}

		}
		writer.close();
	}

	public static boolean DFA(String State, String AcceptStates,
			String Alphabet, String q0, String transition, String inputStream,
			int DFAnum) {


//		 writer.println("State " + State);
//		 writer.println("Accept " +AcceptStates);
//		 writer.println("Alphabet " + Alphabet);
//		 writer.println("q0 " + q0);
//		 writer.println("transition "+ transition);
//		 writer.println("input " + inputStream);
//		 writer.println();
		
//		 writer.println(State);
//		 writer.println(AcceptStates);
//		 writer.println(Alphabet);
//		 writer.println(q0);
//		 writer.println( transition);
//		 writer.println(inputStream);
//		 writer.println();
		 
		int DFAConstructed = 0;

		String[] StateArr = State.split(",");
		String[] AcceptStatesArr = AcceptStates.split(",");
		String[] AlphabetArr = Alphabet.split(",");
		String[] q0Arr = q0.split(",");
		String[] transitionArr = transition.split("#");
		String[] inputStreamArr = inputStream.split("#");

		Set<String> Q = new HashSet<String>(Arrays.asList(StateArr));
		Set<String> AlphaSet = new HashSet<String>(Arrays.asList(AlphabetArr));
		Set<String> AcceptStatesSet = new HashSet<String>(
				Arrays.asList(AcceptStatesArr));

		for (int i = 0; i < AcceptStatesArr.length; i++) {
			if (!Q.contains(AcceptStatesArr[i])&& !(AcceptStatesArr[i].trim().isEmpty())) {
				writer.println("Invalid accept state "+AcceptStatesArr[i]);
			
				DFAConstructed = -1;
				break;

			}
		}

		if (!(q0Arr.length == 1)) {
			writer.println("Rejected Cause q0 length");
			DFAConstructed = -1;

		}
		if (!Q.contains(q0Arr[0]) && DFAConstructed != -1) {
			writer.println("Invalid start state");
			DFAConstructed = -1;

		}

		ArrayList<String> currentStates = new ArrayList<String>();
		ArrayList<String> currentTrans = new ArrayList<String>();
		ArrayList<String> AllTransitions = new ArrayList<String>();
		int index = 0;
		int j = 0;
		if (DFAConstructed != -1) {
	

			for (int i = 0; i < transitionArr.length; i++) {

				String[] trans = transitionArr[i].split(",");

				if (trans.length < 3) {
					writer.print("Incomplete Transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.println();
					DFAConstructed = -1;
					break;

				}
				
				if (!(AlphaSet.contains(trans[2]))) {
					writer.print("Invalid Transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.print(" input " + trans[2]
							+ " is not in the alphabet");
					writer.println();
					DFAConstructed = -1;
					break;

				}
				if (trans.length > 3) {
					writer.print("More Than 5 Transition ");
					for (int t = 0; t < trans.length; t++) {
						writer.print(trans[t] + ",");
					}
					writer.println();
					DFAConstructed = -1;
					break;

				}
				
				if (!(Q.contains(trans[0])) ) {
					writer.print("Invalid transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.print(" state " + trans[0]
							+ " does not exist");
					writer.println();
					DFAConstructed = -1;
					break;

				}

				if (!(Q.contains(trans[1]))) {
					writer.print("Invalid transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.print(" state " + trans[1]
							+ " does not exist");
					writer.println();
					DFAConstructed = -1;
					break;

				}
				
				if (i == 0) {
					currentStates.add(trans[0]);
					currentTrans.add(j, trans[2]);
					j++;
					AllTransitions.add(index++, trans[0]);
					AllTransitions.add(index++, trans[1]);
					AllTransitions.add(index++, trans[2]);
				} else {

					currentStates.add(trans[0]);
					AllTransitions.add(index++, trans[0]);

					if (trans[0].equals(currentStates.get(i - 1))) {
						currentTrans.add(j++, trans[2]);

						AllTransitions.add(index++, trans[1]);
						AllTransitions.add(index++, trans[2]);
					} else {
						if (currentTrans.size() != AlphaSet.size()) {
							writer.println("Missing transition for state "+currentStates.get(i - 1));
							DFAConstructed = -1;
							break;
			
						}
						for (int k = 0; k < currentTrans.size(); k++) {
							if (!(AlphaSet.contains(currentTrans.get(k)))) {
								writer.println("Missing transition for state "+currentTrans.get(k));
								DFAConstructed = -1;
								break;
					
							}
						}
						
						currentTrans = new ArrayList<String>();
						j = 0;
						currentTrans.add(j, trans[2]);
						AllTransitions.add(index++, trans[1]);
						AllTransitions.add(index++, trans[2]);
					}
				}
			}
		}
		
		
		
		if( AllTransitions.size() !=   (Q.size()*AlphaSet.size()) && DFAConstructed==0){
		for(int i=0;i<StateArr.length;i++){
			if(!AllTransitions.contains(StateArr[i])){
				writer.println("Missing transition for state "+StateArr[i]);
				DFAConstructed = -1;
				break;
			}
		}
			
		}

		if (DFAConstructed == 0)
			writer.println("DFA constructed");

		// input stream
		
		for(int i = 0; i < inputStreamArr.length; i++) {
			boolean flag =false;
			if (DFAConstructed == 0) {
				// first input stream
				String[] in = inputStreamArr[i].split(",");
				String currentState = q0Arr[0];
				flag=false ;
				for (int k = 0; k < in.length; k++) {
					int trans = 0;
					if (AlphaSet.contains(in[k])) {
						while (trans != AllTransitions.size() - 3) {
							if (((trans) % 3 == 0 || trans == 0)
									&& AllTransitions.get(trans).equals(
											currentState)
									&& AllTransitions.get(trans + 2).equals(
											in[k])) {
								currentState = AllTransitions.get(trans + 1);
								break;
								// another state
							}
							trans += 3;
						}
					} else{
						writer.println("Invalid input string at "+ in[k]);
						flag=true;
						break;
					}
				}
				if (AcceptStatesSet.contains(currentState) && !flag) {
					writer.println("Accepted");
				} else {
					if(!flag){
					writer.println("Rejected");
					}
				}

			} else {
				if(!flag)
					writer.println("Ignored");
			}
		}
		writer.println();
		
		return true;
	}
	
	
	public static void ReadNFA() throws IOException {

		FileReader in = new FileReader("/Users/apple/Desktop/Lab2/in.in");
		BufferedReader br = new BufferedReader(in);

		String line;
		String[] NFA = new String[7];
		int i = 0;
		int NFAnum = 1;
		while ((line = br.readLine()) != null) {
				NFA[i] = line;
				
				i++;
				if (i == 6) {
					boolean result = NFA(NFA[0], NFA[1], NFA[2], NFA[3],
							NFA[4], NFA[5], NFAnum);
					NFAnum++;
					i = 0;
					br.readLine();
				}
		}
		writer.close();
	}
	

	public static boolean NFA(String State, String AcceptStates,
			String Alphabet, String q0, String transition, String inputStream,
			int DFAnum) {
		
		int NFAConstructed = 0;

		String[] StateArr = State.split(",");
		String[] AcceptStatesArr = AcceptStates.split(",");
		String[] AlphabetArr = Alphabet.split(",");
		String[] q0Arr = q0.split(",");
		String[] transitionArr = transition.split("#");
		String[] inputStreamArr = inputStream.split("#");

		Set<String> Q = new HashSet<String>(Arrays.asList(StateArr));
		Set<String> AlphaSet = new HashSet<String>(Arrays.asList(AlphabetArr));
		Set<String> AcceptStatesSet = new HashSet<String>(
				Arrays.asList(AcceptStatesArr));

		for (int i = 0; i < AcceptStatesArr.length; i++) {
			if (!Q.contains(AcceptStatesArr[i])&& !(AcceptStatesArr[i].trim().isEmpty())) {
				writer.println("Invalid accept state "+AcceptStatesArr[i]);
				
				NFAConstructed = -1;
				break;

			}
		}

		if (!(q0Arr.length == 1)&& NFAConstructed != -1) {
			writer.println("Rejected Cause q0 length");
			NFAConstructed = -1;

		}
		if (!Q.contains(q0Arr[0]) && NFAConstructed != -1) {
			writer.println("Invalid start state");
			NFAConstructed = -1;

		}


		ArrayList<String> Transitions = new ArrayList<String>();
		ArrayList<String> ETransitions = new ArrayList<String>();

		int index = 0;
		int Eindex= 0;
		if (NFAConstructed != -1) {

			for (int i = 0; i < transitionArr.length; i++) {

				String[] trans = transitionArr[i].split(",");

				if (trans.length < 3) {
					writer.print("Incomplete Transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.println();
					NFAConstructed = -1;
					break;

				}
				
				if (!(AlphaSet.contains(trans[2])) && !(trans[2].equals("$"))) {
					writer.print("Invalid Transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.println(" input " + trans[2]
							+ " is not in the alphabet");
					
					NFAConstructed = -1;
					break;

				}
				if (trans.length > 3) {
					writer.print("More Than 5 Transition ");
					for (int t = 0; t < trans.length; t++) {
						writer.print(trans[t] + ",");
					}
					writer.println();
					NFAConstructed = -1;
					break;

				}
				
				if (!(Q.contains(trans[0])) ) {
					writer.print("Invalid transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.println(" state " + trans[0]
							+ " does not exist");
					
					NFAConstructed = -1;
					break;

				}

				if (!(Q.contains(trans[1]))) {
					writer.print("Invalid transition ");
					for (int t = 0; t < trans.length; t++) {

						if (t != trans.length - 1) {
							writer.print(trans[t] + ",");
						} else {
							writer.print(trans[t]);
						}

					}
					writer.println(" state " + trans[1]
							+ " does not exist");
			
					NFAConstructed = -1;
					break;

				}
				if(!trans[2].equals("$")){
					Transitions.add(index++, trans[0]);
					Transitions.add(index++, trans[1]);
					Transitions.add(index++, trans[2]);
				}else{
					ETransitions.add(Eindex++,trans[0]);
					ETransitions.add(Eindex++, trans[1]);					
				}
			}
		}
		if(NFAConstructed==-1){
			return false;
		}
//		writer.println("Normal Transitions");
//		for(int i=0;i<Transitions.size();i++){
//			if(i%3==0){
//				writer.print(Transitions.get(i)+", ");
//			}
//			else{
//				if(i%3==1){
//					writer.print(Transitions.get(i)+" -> ");
//				}else{
//					writer.println(Transitions.get(i));
//				}
//			}
//			
//		}
//		
//		writer.println("E Transitions");
//		for(int i=0;i<ETransitions.size();i++){
//			if(i%2==0){
//				writer.print(ETransitions.get(i)+" -> ");
//			}
//			else{
//				writer.println(ETransitions.get(i));
//			}
//			
//		}
		
	//	s1,s4,s2,s3,s3,s4,s4,s3
	//	writer.println("ETransitions size "+ETransitions.size());
		
		ArrayList<String> intial = new ArrayList<String>();
		intial.add(q0);
		EpsilionClosure(intial,ETransitions); 
		ArrayList<String> accept= new ArrayList<String>();
		
		for(int i =0;i<intial.size();i++){
			if(AcceptStatesSet.contains(intial.get(i))){
				accept.add( String.join("*", intial));
				break;
			}
		}
		
		String intialState = String.join(",", intial);
		
		ArrayList<String> DFAstates = new ArrayList<String>();
		ArrayList<String> DFATransition = new ArrayList<String>();
		
		DFAstates.add(intialState);

		for(int i=0 ; i<DFAstates.size() ;i++){
			
			for(int j =0;j<AlphabetArr.length;j++){
				ArrayList<String>CurrentState = new ArrayList<String>(Arrays.asList(DFAstates.get(i).split(",")));
				ArrayList<String> newStates=GoToState(CurrentState,Transitions,ETransitions,AlphabetArr[j]);
				for(int k=0;k<newStates.size();k++){
					if(AcceptStatesSet.contains(newStates.get(k)) && !accept.contains(String.join("*", newStates)) ){
						accept.add( String.join("*", newStates));
					}
				}
				String newState= String.join(",", newStates);
				String Transiton =  String.join("*", CurrentState)+"."+ newState+"."+AlphabetArr[j];
				DFATransition.add(Transiton);
				if(!DFAstates.contains(newState)){
					DFAstates.add(newState);
				}
			}
			
		}
		writer.println("NFA constructed");
		writer.println("Equivalent DFA:");
		if(DFAstates.contains("Dead")){
			DFAstates.remove("Dead");
			DFAstates.add("Dead");
		}
	
		for(int i=0;i<DFAstates.size();i++){
				String temp=DFAstates.get(i).replace(",", "*");
				DFAstates.remove(i);
				DFAstates.add(i,temp);
				if(i!=DFAstates.size()-1){
					writer.print(DFAstates.get(i)+",");
				}
				else{
					writer.println(DFAstates.get(i));
				}
		}
		

		for(int i=0;i<accept.size();i++){
			if(i!=accept.size()-1){
				writer.print(accept.get(i)+",");
			}
			else{
				writer.println(accept.get(i));
			}
			
		}
		
		writer.println(Alphabet);
		
		intialState= intialState.replace(",", "*");
		writer.println(intialState);
		
		for(int i=0;i<DFATransition.size();i++){
				
			String temp=DFATransition.get(i).replace(",", "*").replace(".", ",");
			DFATransition.remove(i);
			DFATransition.add(i,temp);
			if(i!=DFATransition.size()-1){
				writer.print(DFATransition.get(i)+"#");
			}else{
				writer.println(DFATransition.get(i));
			}
			
		}

		
		writer.println(inputStream);
		
		DFA(String.join(",",DFAstates), String.join(",",accept),Alphabet, intialState,String.join("#",DFATransition), inputStream,0);
		
		return true;
	}
	public static ArrayList<String> GoToState(ArrayList<String> states,ArrayList<String> Transitions,ArrayList<String> ETransitions,String trans ){
		ArrayList<String> newState= new ArrayList<String>();

			for(int j = 2; j < Transitions.size() ; j+=3){
				if(Transitions.get(j).equals(trans) && states.contains(Transitions.get(j-2)) && !(newState.contains(Transitions.get(j-1)) )){
						newState.add(Transitions.get(j-1));
						
				}	
			}
			if(newState.size()==0){
				newState.add("Dead");
			}else{
				EpsilionClosure(newState,ETransitions);
			}
			Collections.sort(newState);
			
		return newState;
	}
	public static void EpsilionClosure(ArrayList<String> states,ArrayList<String> Etransitions){
	
		for(int i = 0 ; i < states.size(); i++){
			String curState = states.get(i);
		
			for(int j = 0; j < Etransitions.size() ; j+=2){
				if(curState.equals(Etransitions.get(j)) && !(states.contains(Etransitions.get(j+1)) )){
					states.add(Etransitions.get(j+1));
				}		
			}
		}
	}
	public static void main(String[] args) throws IOException {
		writer = new PrintWriter("the-file-name.txt", "UTF-8");
		
		//Read();
		ReadNFA();
		
	}

}
