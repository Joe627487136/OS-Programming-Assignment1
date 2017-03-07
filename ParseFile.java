package assignment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseFile {
    // this method generates a ProcessGraph and store in ProcessGraph Class
    public static boolean executable = true;

    static class AlsCustomException extends Exception
    {
        public AlsCustomException()
        {
            super();
        }
    }
    public static void generateGraph(File inputFile) {
        try {
            // Find out the number of nodes and add the nodes to the graph
            int numOfNodes = Countnodes(inputFile);
            for (int i = 0; i < numOfNodes; i++) {
                ProcessGraph.addNode(i);
            }

            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            int index = 0;
            String line = br.readLine();

            while (line != null) {
                String[] quartiles = line.split(":");
                if (quartiles.length != 4) {
                    System.out.println("Wrong input format!");
                    executable = false;
                    throw new AlsCustomException();
                }

                // handle Children
                if (!quartiles[1].equals("none")) {
                    String[] childrenStringArray = quartiles[1].split(" ");
                    int[] childrenId = new int[childrenStringArray.length];
                    for (int i = 0; i < childrenId.length; i++) {
                        childrenId[i] = Integer.parseInt(childrenStringArray[i]);
                        ProcessGraph.nodes.get(index).addChild(ProcessGraph.nodes.get(childrenId[i]));
                    }
                }

                // setup command
                ProcessGraph.nodes.get(index).setCommand(quartiles[0]);
                // setup input
                ProcessGraph.nodes.get(index).setInputFile(new File(quartiles[2]));
                // setup output
                ProcessGraph.nodes.get(index).setOutputFile(new File(quartiles[3]));
                // setup parent
                for (ProcessGraphNode node : ProcessGraph.nodes) {
                    for (ProcessGraphNode childNode : node.getChildren()) {
                        ProcessGraph.nodes.get(childNode.getNodeId()).addParent(ProcessGraph.nodes.get(node.getNodeId()));
                    }
                }

                // mark initial runnable
                for (ProcessGraphNode node : ProcessGraph.nodes) {
                    if (!node.getParents().isEmpty()) {
                        node.setNotRunnable();
                    }
                    if (node.getParents().isEmpty()) {
                        node.setRunnable();
                    }
                }

                line = br.readLine();
                index++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (AlsCustomException e) {
            System.out.println("Please correct your instruction file first!");
        } catch (IOException e){
            System.out.println("IOException!");
            e.printStackTrace();
        }
    }
    public static int Countnodes(File inputFile) throws IOException {
        int Lines = 0;
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        try {
            String line = br.readLine();

            // if there is a line, we increment the line count
            while (line != null) {
                Lines += 1;
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        br.close();
        return Lines;
    }
}

