package assignment1;
//File("/Users/zhouxuexuan/AndroidStudioProjects/Lab/programming_assignment/src/main/java/assignment1");
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessManagement {

    // set the working directory
    private static File currentDirectory = new File("/Users/zhouxuexuan/AndroidStudioProjects/Lab/programming_assignment/src/main/java/assignment1");

    // set the instructions file
    private static File instructionSet = new File("testproc.txt");

    public static void main(String[] args) throws Exception {
        try{
        // Parse the instruction file and construct a data structure, stored inside ProcessGraph class
        ParseFile.generateGraph(new File(currentDirectory + "/" + instructionSet));

        // Print the graph information
        if(ParseFile.executable) {
            ProcessGraph.printGraph();

            // Using index of ProcessGraph, loop through each ProcessGraphNode, to check whether it is ready to run
            boolean allexecuted = false;

            int count = 0;
            while (!allexecuted) {
                count++;
                ArrayList<ProcessBuilder> pbs = new ArrayList<>();
                ArrayList<Process> processes = new ArrayList<>();
                ArrayList<Integer> workinglist = new ArrayList<>();
                // Using index of ProcessGraph, loop through each ProcessGraphNode, to check whether it is ready to run
                // check if all the nodes are executed
                if (ProcessGraph.nodes.get(ProcessGraph.nodes.size() - 1).isExecuted()) {
                    allexecuted = true;
                }

                //mark all the runnable nodes
                for (int i = 0; i < ProcessGraph.nodes.size(); i++) {
                    if (ProcessGraph.nodes.get(i).isRunnable()) {
                        ProcessBuilder temp = new ProcessBuilder();
                        workinglist.add(i);
                        temp.command(ProcessGraph.nodes.get(i).getCommand().split(" "));
                        temp.directory(currentDirectory);
                        if (!ProcessGraph.nodes.get(i).getInputFile().getName().equals("stdin"))
                            temp.redirectInput(new File(currentDirectory + "/" + ProcessGraph.nodes.get(i).getInputFile().getName()));
                        if (!ProcessGraph.nodes.get(i).getOutputFile().getName().equals("stdout"))
                            temp.redirectOutput(new File(currentDirectory + "/" + ProcessGraph.nodes.get(i).getOutputFile().getName()));
                        System.out.println("Node " + ProcessGraph.nodes.get(i).getNodeId() + " is going to be executed");
                        pbs.add(temp);
                    }
                }

                //run the node if it is runnable
                for (int i = 0; i < pbs.size(); i++) {
                    processes.add(pbs.get(i).start());
                    System.out.println("One task finished");
                }
                for (Integer i : workinglist) {
                    ProcessGraph.nodes.get(i).setExecuted();
                    ProcessGraph.nodes.get(i).setNotRunnable();
                    for (int p = 0; p < ProcessGraph.nodes.get(i).getChildren().size(); p++) {
                        int prsize = ProcessGraph.nodes.get(i).getChildren().get(p).getParents().size();
                        System.out.println(ProcessGraph.nodes.get(i).getNodeId() + " has parent size: " + prsize);
                        int parentfinishcount = 0;
                        for (int k = 0; k < prsize; k++) {
                            if (ProcessGraph.nodes.get(i).getChildren().get(p).getParents().get(k).isExecuted()) {
                                parentfinishcount++;
                            }
                        }
                        if (parentfinishcount == prsize) {
                            ProcessGraph.nodes.get(i).getChildren().get(p).setRunnable();
                        }
                        System.out.println(ProcessGraph.nodes.get(i).getChildren().get(p).getNodeId() + " is now runnable");
                    }
                }
            }
            System.out.println(count + " nodes has been executed");
            System.out.println("All process finished successfully");
        }
        } catch (Exception e){
            System.out.println("Error! Please input correct node number or text name");
        }

    }


}